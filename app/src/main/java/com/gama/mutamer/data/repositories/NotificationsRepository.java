package com.gama.mutamer.data.repositories;

import com.gama.mutamer.data.models.general.Notification;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.utils.Constants;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.Sort;

/***
 * Notifications Data repository
 */
public class NotificationsRepository {


    //TODO: Documentation
    public ArrayList<Notification> getAll() {
        ArrayList<Notification> result = new ArrayList<>();
        try (Realm realm = Realm.getDefaultInstance()) {
            RealmResults<Notification> notifications = realm.where(Notification.class)
                    .sort(Constants.ID, Sort.DESCENDING)
                    .findAll();
            for (Notification item : notifications) {
                result.add(realm.copyFromRealm(item));
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
        return result;
    }

    //TODO: Documentation
    public ArrayList<Notification> getFiltered(Constants.FilterType filterType) {
        ArrayList<Notification> result = new ArrayList<>();
        try (Realm realm = Realm.getDefaultInstance()) {
            RealmQuery<Notification> query = realm.where(Notification.class);
            switch (filterType) {
                case Unread:
                    query = query.equalTo(Constants.ISREAD, false);
                    break;
                case Read:
                    query = query.equalTo(Constants.ISREAD, true);
                    break;
            }
            RealmResults<Notification> notifications = query
                    .sort(Constants.ID, Sort.DESCENDING).findAll();
            for (Notification item : notifications) {
                result.add(realm.copyFromRealm(item));
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
        return result;
    }

    /***
     * Get Notification By Id and change Read Status to true
     * @param id Notification Id
     * @return Notification
     */
    public Notification getNotificationById(long id) {
        Notification result = null;
        try (Realm realm = Realm.getDefaultInstance()) {
            Notification item = realm.where(Notification.class).equalTo(Constants.ID, id).findFirst();
            if (item != null) {
                result = realm.copyFromRealm(item);
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
        return result;
    }

    /***
     * Get Unread Notifications Count
     * @return Un-read notifications count
     */
    public long getUnreadNotificationsCount() {

        long result = 0;
        try (Realm realm = Realm.getDefaultInstance()) {
            result = realm.where(Notification.class).equalTo(Constants.ISREAD, false).count();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
        return result;
    }

    //TODO: Documentation
    public void updateNotifications(ArrayList<Notification> notifications) {
        if (notifications == null || notifications.size() == 0) {
            return;
        }
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            for (Notification notification : notifications) {
                realm.copyToRealmOrUpdate(notification);
            }
            realm.commitTransaction();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }
}
