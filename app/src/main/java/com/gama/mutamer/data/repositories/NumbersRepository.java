package com.gama.mutamer.data.repositories;

import com.gama.mutamer.data.models.content.ImportantNumber;
import com.gama.mutamer.data.models.content.ImportantNumberCategory;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.utils.Constants;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
//TODO: Documentation
public class NumbersRepository {

    //TODO: Documentation
    public ArrayList<ImportantNumberCategory> getData() {
        ArrayList<ImportantNumberCategory> result = new ArrayList<>();
        try (Realm realm = Realm.getDefaultInstance()) {
            RealmResults<ImportantNumberCategory> results = realm.where(ImportantNumberCategory.class)
                    .equalTo(Constants.IS_DELETED,false)
                    .findAll();
            for (ImportantNumberCategory category : results) {
                result.add(realm.copyFromRealm(category));
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
        return result;
    }

    //TODO: Documentation
    public ArrayList<ImportantNumber> getNumbersByCategory(Long categoryId) {
        ArrayList<ImportantNumber> result = new ArrayList<>();
        try (Realm realm = Realm.getDefaultInstance()) {
            RealmResults<ImportantNumber> results = realm.where(ImportantNumber.class)
                    .equalTo(Constants.IS_DELETED,false)
                    .equalTo(Constants.CATEGORY_ID, categoryId)
                    .findAll();
            for (ImportantNumber number : results) {
                result.add(realm.copyFromRealm(number));
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
        return result;
    }

    //TODO: Documentation
    public void updateCategories(ArrayList<ImportantNumberCategory> categories) {
        if (categories == null || categories.size() == 0) {
            return;
        }
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            for (ImportantNumberCategory category : categories) {
                realm.copyToRealmOrUpdate(category);
            }
            realm.commitTransaction();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    //TODO: Documentation
    public void updateNumbers(ArrayList<ImportantNumber> numbers) {
        if (numbers == null || numbers.size() == 0) {
            return;
        }
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            for (ImportantNumber number : numbers) {
                realm.copyToRealmOrUpdate(number);
            }
            realm.commitTransaction();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }
}
