package com.gama.mutamer.helpers;

import com.gama.mutamer.data.models.general.Notification;
import com.gama.mutamer.utils.Constants;

import java.util.Date;

import io.realm.Realm;

public class DummyHelper {

    public static void FillDummyNotifications() {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        for (int i = 1; i <= 10; i++) {
            Notification notification = new Notification();
            notification.setId(i);
            notification.setDate(new Date());
            notification.setTitle("Notification Title " + String.valueOf(i));
            notification.setRead(false);
            if (i % 5 == 0) {
                notification.setType(Constants.NOTIFICATION_TYPE_IMAGE);
                notification.setBody("https://upload.wikimedia.org/wikipedia/commons/thumb/9/97/The_Earth_seen_from_Apollo_17.jpg/450px-The_Earth_seen_from_Apollo_17.jpg");
            } else if (i % 4 == 0) {
                notification.setType(Constants.NOTIFICATION_TYPE_URL);
                notification.setBody("https://www.almowaten.net/2018/06/%D9%83%D8%A8%D8%A7%D8%B1-%D8%A7%D9%84%D8%B9%D9%84%D9%85%D8%A7%D8%A1-%D8%A7%D9%84%D8%A3%D8%B5%D9%84-%D9%81%D9%8A-%D9%82%D9%8A%D8%A7%D8%AF%D8%A9-%D8%A7%D9%84%D9%85%D8%B1%D8%A3%D8%A9-%D8%A7%D9%84%D8%A5/");
            } else {
                notification.setType(Constants.NOTIFICATION_TYPE_TEXT);
                notification.setBody("Demo Notification Text Body.");
            }
            realm.copyToRealmOrUpdate(notification);
        }
        realm.commitTransaction();
        realm.close();
    }
}
