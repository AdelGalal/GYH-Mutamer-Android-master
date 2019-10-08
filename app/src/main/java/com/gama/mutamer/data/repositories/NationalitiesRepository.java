package com.gama.mutamer.data.repositories;

import com.gama.mutamer.helpers.LanguageHelper;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.utils.Constants;
import com.gama.mutamer.viewModels.utils.NameViewModel;

import java.util.ArrayList;
import java.util.Arrays;

import io.realm.Realm;
import io.realm.RealmResults;
//TODO: Documentation
public class NationalitiesRepository {

    //TODO: Documentation
    private static ArrayList<NameViewModel> cacheCountriesList;

    //TODO: Documentation
    public ArrayList<NameViewModel> getExchangeList(String lang) {
        if (cacheCountriesList != null && cacheCountriesList.size() > 0) {
            return cacheCountriesList;
        }

        ArrayList<NameViewModel> result = new ArrayList<>();
        try (Realm realm = Realm.getDefaultInstance()) {
            RealmResults<NameViewModel> data = realm.where(NameViewModel.class)
                    .sort(lang.equalsIgnoreCase(LanguageHelper.LANGUAGE_ARABIC) ? Constants.ARABIC_NAME : Constants.ENGLISH_NAME)
                    .findAll();

            for (NameViewModel item : data) {
                if (Arrays.asList(Constants.currencyCodes).contains(item.getCurrencyCode())) {
                    result.add(realm.copyFromRealm(item));
                }
            }
            cacheCountriesList = result;
        } catch (Exception e) {
            FirebaseErrorEventLog.SaveEventLog(e);
        }
        return result;
    }
}
