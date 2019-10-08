package com.gama.mutamer.data.repositories;

import com.gama.mutamer.data.models.content.Duaa;
import com.gama.mutamer.data.models.content.DuaaCategory;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.utils.Constants;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;

/***
 * Prayers/Duaa Data repository
 */
public class PrayersRepository {

    //TODO: Documentation
    public void updateDuaaAlert(long duaaId, boolean isAlert) {
        try (Realm realm = Realm.getDefaultInstance()) {
            Duaa place = realm.where(Duaa.class).equalTo(Constants.ID, duaaId).findFirst();
            if (place != null) {
                realm.beginTransaction();
                place.setAlert(isAlert);
                realm.commitTransaction();
            }

        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    //TODO: Documentation
    public void updateBookmark(long duaaId, boolean isFavorites) {
        try (Realm realm = Realm.getDefaultInstance()) {
            Duaa place = realm.where(Duaa.class).equalTo(Constants.ID, duaaId).findFirst();
            if (place != null) {
                realm.beginTransaction();
                place.setFavorites(isFavorites);
                realm.commitTransaction();
            }

        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    //TODO: Documentation
    public ArrayList<DuaaCategory> getAllCategories() {
        try (Realm realm = Realm.getDefaultInstance()) {
            ArrayList<DuaaCategory> duaas = new ArrayList<>();
            RealmResults<DuaaCategory> data = realm.where(DuaaCategory.class)
                    .equalTo(Constants.IS_DELETED, false)
                    .sort(Constants.ID, Sort.ASCENDING)
                    .findAll();
            for (DuaaCategory duaa : data) {
                duaas.add(realm.copyFromRealm(duaa));
            }
            return duaas;
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
            return new ArrayList<>();
        }
    }

    //TODO: Documentation
    public ArrayList<Duaa> getListByCategory(long categoryId) {
        try (Realm realm = Realm.getDefaultInstance()) {
            ArrayList<Duaa> duaas = new ArrayList<>();
            RealmResults<Duaa> data = realm.where(Duaa.class)
                    .equalTo(Constants.IS_DELETED, false)
                    .equalTo(Constants.CATEGORY_ID, categoryId)
                    .findAll();
            for (Duaa duaa : data) {
                duaas.add(realm.copyFromRealm(duaa));
            }
            return duaas;
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
            return new ArrayList<>();
        }
    }

    //TODO: Documentation
    public void updateCategories(ArrayList<DuaaCategory> categories) {
        if (categories == null || categories.size() == 0) {
            return;
        }
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            for (DuaaCategory category : categories) {
                realm.copyToRealmOrUpdate(category);
            }
            realm.commitTransaction();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    //TODO: Documentation
    public void updateItems(ArrayList<Duaa> items) {
        if (items == null || items.size() == 0) {
            return;
        }
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            for (Duaa item : items) {
                realm.copyToRealmOrUpdate(item);
            }
            realm.commitTransaction();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    //TODO: Documentation
    public Duaa getDuaaById(Long id) {
        try (Realm realm = Realm.getDefaultInstance()) {
            Duaa duaa = realm.where(Duaa.class)
                    .equalTo(Constants.ID, id)
                    .findFirst();
            if (duaa != null) {
                return realm.copyFromRealm(duaa);
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
        return null;
    }
}
