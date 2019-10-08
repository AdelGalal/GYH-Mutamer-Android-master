package com.gama.mutamer.data.repositories;

import com.gama.mutamer.data.models.content.Place;
import com.gama.mutamer.data.models.content.PlaceCategory;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
//TODO: Documentation
public class PlacesRepository {



    //TODO: Documentation
    public void updatePlaceAlert(long placeId, boolean isAlert) {
        try (Realm realm = Realm.getDefaultInstance()) {
            Place place = realm.where(Place.class).equalTo(Constants.ID, placeId).findFirst();
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
    public void updatePlaceBookmark(long placeId, boolean isBookmarked) {
        try (Realm realm = Realm.getDefaultInstance()) {
            Place place = realm.where(Place.class).equalTo(Constants.ID, placeId).findFirst();
            if (place != null) {
                realm.beginTransaction();
                place.setFavorites(isBookmarked);
                realm.commitTransaction();
            }

        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    //TODO: Documentation
    public List<PlaceCategory> getCategories() {
        try (Realm realm = Realm.getDefaultInstance()) {
            ArrayList<PlaceCategory> cities = new ArrayList<>();
            RealmResults<PlaceCategory> data = realm.where(PlaceCategory.class)
                    .equalTo(Constants.IS_DELETED,false)
                    .findAll();
            for (PlaceCategory category : data) {
                cities.add(realm.copyFromRealm(category));
            }
            return cities;
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
            return new ArrayList<>();
        }
    }

    //TODO: Documentation
    public ArrayList<Place> getPlacesByCategory(Long categoryId) {
        try (Realm realm = Realm.getDefaultInstance()) {
            ArrayList<Place> places = new ArrayList<>();
            RealmResults<Place> data = realm.where(Place.class)
                    .equalTo(Constants.IS_DELETED,false)
                    .equalTo(Constants.CATEGORY_ID, categoryId)
                    .findAll();
            for (Place place : data) {
                places.add(realm.copyFromRealm(place));
            }
            return places;
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
            return new ArrayList<>();
        }
    }

    //TODO: Documentation
    public void updateCategories(ArrayList<PlaceCategory> categories) {
        if (categories == null || categories.size() == 0) {
            return;
        }
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            for (PlaceCategory record : categories) {
                realm.copyToRealmOrUpdate(record);
            }
            realm.commitTransaction();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    //TODO: Documentation
    public void updatePlaces(ArrayList<Place> places) {
        if (places == null || places.size() == 0) {
            return;
        }
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            for (Place record : places) {
                realm.copyToRealmOrUpdate(record);
            }
            realm.commitTransaction();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    //TODO: Documentation
    public Place getPlaceById(Long id) {
        try (Realm realm = Realm.getDefaultInstance()) {
            Place place = realm.where(Place.class)
                    .equalTo(Constants.ID, id)
                    .findFirst();
            if (place != null) {
                return realm.copyFromRealm(place);
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
        return null;
    }
}
