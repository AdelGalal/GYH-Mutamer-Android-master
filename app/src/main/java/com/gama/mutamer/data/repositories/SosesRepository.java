package com.gama.mutamer.data.repositories;

import com.gama.mutamer.data.models.Sos;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;

import java.util.ArrayList;

import io.realm.Realm;

//TODO: Documentation
public class SosesRepository {

    //TODO: Documentation
    public void updateData(ArrayList<Sos> sos) {
        if (sos == null || sos.size() == 0) {
            return;
        }
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            for (Sos item : sos) {
                realm.copyToRealmOrUpdate(item);
            }
            realm.commitTransaction();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }
}
