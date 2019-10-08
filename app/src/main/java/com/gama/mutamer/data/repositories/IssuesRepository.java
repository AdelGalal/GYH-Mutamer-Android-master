package com.gama.mutamer.data.repositories;

import com.gama.mutamer.data.models.Issue;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.utils.Constants;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

//TODO: Documentation
public class IssuesRepository {

    //TODO: Documentation
    public void updateIssues(ArrayList<Issue> issues) {
        if (issues == null || issues.size() == 0) {
            return;
        }
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            for (Issue issue : issues) {
                realm.copyToRealmOrUpdate(issue);
            }
            realm.commitTransaction();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    public ArrayList<Issue> getAllPendingIssues() {
        ArrayList<Issue> result = new ArrayList<>();
        try (Realm realm = Realm.getDefaultInstance()) {
            RealmResults<Issue> issues = realm.where(Issue.class)
                    .equalTo(Constants.STATUS, 1)
                    .sort(Constants.CREATED_DATE, Sort.DESCENDING)
                    .findAll();
            if (issues != null && issues.size() > 0) {
                for (Issue issue : issues) {
                    result.add(realm.copyFromRealm(issue));
                }
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
        return result;

    }
}
