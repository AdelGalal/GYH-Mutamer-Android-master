package com.gama.mutamer.data.repositories;

import com.gama.mutamer.data.models.PilgrimData.Agent;
import com.gama.mutamer.data.models.PilgrimData.Arrival;
import com.gama.mutamer.data.models.PilgrimData.Departure;
import com.gama.mutamer.data.models.PilgrimData.Hotel;
import com.gama.mutamer.data.models.PilgrimData.Pilgrim;
import com.gama.mutamer.data.models.PilgrimData.Transportation;
import com.gama.mutamer.data.models.PilgrimData.UmrahCompany;
import com.gama.mutamer.data.models.User;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.viewModels.webServices.LoginViewParam;
import com.gama.mutamer.webServices.responses.LoginResponse;
import com.gama.mutamer.webServices.responses.PilgrimProfileResponse;

import io.realm.Realm;
import io.realm.RealmResults;

/***
 * User related database operations
 */
public class UsersRepository {

    /***
     * User login info static caching for quick access
     */
    private static User USER;

    /***
     * Save Login Info
     * @param model User login info including username and password
     * @param loginResponse User Login response including access token
     */
    public void saveLoginInfo(LoginViewParam model, LoginResponse loginResponse) {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            User user = new User();
            user.setAccessToken(loginResponse.getLoginInfo().getAccessToken());
            user.setPassword(model.getPassword());
            user.setUserName(model.getUserName());
            user.setDisplayName(loginResponse.getLoginInfo().getDisplayName());
            realm.copyToRealmOrUpdate(user);
            realm.commitTransaction();
            getLoggedUser();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    /***
     * Get current user login info if any
     * @return User info if logged otherwise null
     */
    public User getLoggedUser() {
        if (USER != null) {
            return USER;
        }
        try (Realm realm = Realm.getDefaultInstance()) {
            User user = realm.where(User.class).findFirst();
            if (user != null) {
                USER = realm.copyFromRealm(user);
                return USER;
            }
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
        return null;
    }

    //TODO: Documentation
    public void logOut() {
        try (Realm realm = Realm.getDefaultInstance()) {
            realm.beginTransaction();
            User user = realm.where(User.class).findFirst();
            if (user != null) {
                user.deleteFromRealm();
                USER = null;
            }

            // Delete Pilgrim
            Pilgrim pilgrim = realm.where(Pilgrim.class).findFirst();
            if (pilgrim != null) {
                pilgrim.deleteFromRealm();
            }

            // Delete Agent
            Agent agent = realm.where(Agent.class).findFirst();
            if (agent != null) {
                agent.deleteFromRealm();
            }

            // Delete Company
            UmrahCompany company = realm.where(UmrahCompany.class).findFirst();
            if (company != null) {
                company.deleteFromRealm();
            }

            // Delete Hotels
            RealmResults<Hotel> hotels = realm.where(Hotel.class).findAll();
            if (hotels != null && hotels.size() > 0) {
                for (Hotel hotel : hotels) {
                    hotel.deleteFromRealm();
                }
            }

            // Delete Transportation
            RealmResults<Transportation> transportation = realm.where(Transportation.class).findAll();
            if (transportation != null && transportation.size() > 0) {
                for (Transportation trans : transportation) {
                    trans.deleteFromRealm();
                }
            }

            // Delete Arrival
            Arrival arrival = realm.where(Arrival.class).findFirst();
            if (arrival != null) {
                arrival.deleteFromRealm();
            }

            // Delete Departure
            Departure departure = realm.where(Departure.class).findFirst();
            if (departure != null) {
                departure.deleteFromRealm();
            }
            realm.commitTransaction();
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }

    //TODO: Documentation
    public void savePilgrimProfile(PilgrimProfileResponse response) {
        try (Realm realm = Realm.getDefaultInstance()) {
            if (response == null) {
                return;
            }

            realm.beginTransaction();

            // Save Pilgrim
            if (response.getPilgrim() != null) {
                realm.copyToRealmOrUpdate(response.getPilgrim());
            }

            // Save Agent
            if (response.getAgent() != null) {
                realm.copyToRealmOrUpdate(response.getAgent());
            }

            // Save Company
            if (response.getCompany() != null) {
                realm.copyToRealmOrUpdate(response.getCompany());
            }

            // Save Hotels
            if (response.getHotels() != null && response.getHotels().size() > 0) {
                for (Hotel hotel : response.getHotels()) {
                    realm.copyToRealmOrUpdate(hotel);
                }
            }

            // Save Transportation
            if (response.getTransportations() != null && response.getTransportations().size() > 0) {
                for (Transportation transportation : response.getTransportations()) {
                    realm.copyToRealmOrUpdate(transportation);
                }
            }

            // Save Arrival
            if (response.getArrival() != null) {
                realm.copyToRealmOrUpdate(response.getArrival());
            }

            // Save Departure
            if (response.getDeparture() != null) {
                realm.copyToRealmOrUpdate(response.getDeparture());
            }


            realm.commitTransaction();

        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
    }
}
