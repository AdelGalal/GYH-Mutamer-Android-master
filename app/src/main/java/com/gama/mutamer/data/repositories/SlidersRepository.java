package com.gama.mutamer.data.repositories;

import com.gama.mutamer.data.models.content.Slider;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.utils.Constants;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

//TODO: Documentation
public class SlidersRepository {
    //TODO: Documentation
    public void updateItems(ArrayList<Slider> sliders) {
        if (sliders == null || sliders.size() == 0) {
            return;
        }
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            for (Slider item : sliders) {
                realm.copyToRealmOrUpdate(item);
            }
            realm.commitTransaction();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    //TODO: Documentation
    public ArrayList<Slider> getData() {
        ArrayList<Slider> result = new ArrayList<>();
        try (Realm realm = Realm.getDefaultInstance()) {
            RealmResults<Slider> data = realm.where(Slider.class)
                    .equalTo(Constants.IS_DELETED,false)
                    .findAll();
            for (Slider item : data) {
                result.add(realm.copyFromRealm(item));
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
        return result;
    }

    //TODO: Documentation
    public Slider getSliderById(Long sliderId) {
        try (Realm realm = Realm.getDefaultInstance()) {
            Slider data = realm.where(Slider.class)
                    .equalTo(Constants.ID, sliderId)
                    .findFirst();
            if (data != null) {
                return realm.copyFromRealm(data);
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
        return null;
    }
}
