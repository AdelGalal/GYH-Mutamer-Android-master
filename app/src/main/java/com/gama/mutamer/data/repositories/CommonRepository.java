package com.gama.mutamer.data.repositories;

import android.content.Context;

import com.gama.mutamer.data.models.PilgrimData.Agent;
import com.gama.mutamer.data.models.PilgrimData.Arrival;
import com.gama.mutamer.data.models.PilgrimData.Departure;
import com.gama.mutamer.data.models.PilgrimData.Hotel;
import com.gama.mutamer.data.models.PilgrimData.MutamerInfo;
import com.gama.mutamer.data.models.PilgrimData.Pilgrim;
import com.gama.mutamer.data.models.PilgrimData.Transportation;
import com.gama.mutamer.data.models.PilgrimData.UmrahCompany;
import com.gama.mutamer.data.models.general.Setting;
import com.gama.mutamer.helpers.AssetsHelper;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.viewModels.uiRelated.MutamerProgram;
import com.gama.mutamer.viewModels.utils.NameViewModel;
import com.gama.mutamer.viewModels.utils.NationalitiesList;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

/***
 * Common Data repository for common data access needs
 */
public class CommonRepository {

    //TODO: Documentation
    public Setting getSettings() {
        Setting result = null;
        try (Realm realm = Realm.getDefaultInstance()) {

            Setting setting = realm.where(Setting.class).findFirst();
            result = setting != null ? realm.copyFromRealm(setting) : null;
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
        return result;
    }

    //TODO: Documentation
    public void setDefaultSetting() {
        try (Realm realm = Realm.getDefaultInstance()) {
            Setting setting = new Setting();
            setting.setPrayerMethod(0);
            setting.setId(1);
            setting.setLastUpdateDate(new Date(0));
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(setting);
            realm.commitTransaction();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    //TODO: Documentation
    public void loadNationalities(Context context) {
        try (Realm realm = Realm.getDefaultInstance()) {
            NationalitiesList nationalities = new Gson().fromJson(AssetsHelper.getNationalities(context), NationalitiesList.class);
            realm.beginTransaction();
            for (NameViewModel item : nationalities.getNationalties()) {
                if (item.getCurrencyCode() != null && item.getCurrencyCode().length() > 0) {
                    realm.copyToRealmOrUpdate(item);
                }
            }
            realm.commitTransaction();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    //TODO: Documentation
    public MutamerInfo getInfo() {
        try (Realm realm = Realm.getDefaultInstance()) {
            MutamerInfo info = new MutamerInfo();

            Pilgrim pilgrim = realm.where(Pilgrim.class)
                    .findFirst();

            if (pilgrim == null) {
                return null;
            }

            info.setPilgrim(realm.copyFromRealm(pilgrim));

            Agent agent = realm.where(Agent.class)
                    .findFirst();
            if (agent != null) {
                info.setAgent(realm.copyFromRealm(agent));
            }

            UmrahCompany company = realm.where(UmrahCompany.class)
                    .findFirst();
            if (company != null) {
                info.setCompany(realm.copyFromRealm(company));
            }
            return info;
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
        return null;
    }

    //TODO: Documentation
    public MutamerProgram getProgram() {
        try (Realm realm = Realm.getDefaultInstance()) {
            MutamerProgram program = new MutamerProgram();

            Pilgrim pilgrim = realm.where(Pilgrim.class).findFirst();
            if (pilgrim == null) {
                return null;
            }

            Arrival arrival = realm.where(Arrival.class)
                    .findFirst();
            if (arrival != null) {
                program.setArrival(realm.copyFromRealm(arrival));
            }

            Departure departure = realm.where(Departure.class)
                    .findFirst();
            if (departure != null) {
                program.setDeparture(realm.copyFromRealm(departure));
            }

            RealmResults<Hotel> hotels = realm.where(Hotel.class)
                    .findAll();
            if (hotels != null && hotels.size() > 0) {
                program.setHotels(new ArrayList<>());
                for (Hotel hotel : hotels) {
                    program.getHotels().add(realm.copyFromRealm(hotel));
                }
            }

            RealmResults<Transportation> transportation = realm.where(Transportation.class)
                    .findAll();
            if (transportation != null && transportation.size() > 0) {
                program.setTransportations(new ArrayList<>());
                for (Transportation trans : transportation) {
                    program.getTransportations().add(realm.copyFromRealm(trans));
                }
            }


            return program;
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
        return null;
    }
}
