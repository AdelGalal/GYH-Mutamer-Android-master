package com.gama.mutamer.data.repositories;

import android.content.Context;

import com.gama.mutamer.data.models.general.Setting;
import com.gama.mutamer.helpers.SharedPrefHelper;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;

import java.util.Date;

import io.realm.Realm;
//TODO: Documentation
public class SettingsRepository {

    //TODO: Documentation
    public void updatePrayerCalculation(Context context, int calculationMethod) {
        try (Realm realm = Realm.getDefaultInstance()) {
            Setting result = realm.where(Setting.class).findFirst();
            if (result != null) {
                realm.beginTransaction();
                result.setPrayerMethod(calculationMethod);
                realm.commitTransaction();
                SharedPrefHelper.setSharedInt(context, SharedPrefHelper.PRAYER_CALC, calculationMethod);
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    //TODO: Documentation
    public void updateUnit(int unit) {
        try (Realm realm = Realm.getDefaultInstance()) {
            Setting result = realm.where(Setting.class).findFirst();
            if (result != null) {
                realm.beginTransaction();
                result.setUnitType(unit);
                realm.commitTransaction();
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    //TODO: Documentation
    public void updatePrayerNotification(Context context, int notificationType) {
        try (Realm realm = Realm.getDefaultInstance()) {
            Setting result = realm.where(Setting.class).findFirst();
            if (result != null) {
                realm.beginTransaction();
                result.setPrayerNotificationType(notificationType);
                realm.commitTransaction();
                SharedPrefHelper.setSharedInt(context, SharedPrefHelper.PRAYER_NOTIFICATIONS, notificationType);
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    //TODO: Documentation
    public void updateLanguage(int newLanguage) {
        try (Realm realm = Realm.getDefaultInstance()) {
            Setting result = realm.where(Setting.class).findFirst();
            if (result != null) {
                realm.beginTransaction();
                result.setLanguage(newLanguage);
                realm.commitTransaction();
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    //TODO: Documentation
    public void updateLastUpdateDate(Date lastUpdateDate) {
        try (Realm realm = Realm.getDefaultInstance()) {
            Setting result = realm.where(Setting.class).findFirst();
            if (result != null) {
                realm.beginTransaction();
                result.setLastUpdateDate(lastUpdateDate);
                realm.commitTransaction();
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    //TODO: Documentation
    public Date getLastUpdateDate() {
        try (Realm realm = Realm.getDefaultInstance()) {
            Setting result = realm.where(Setting.class).findFirst();
            if (result != null) {
                return result.getLastUpdateDate();
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
        return new Date();
    }
}
