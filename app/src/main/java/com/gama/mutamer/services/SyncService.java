package com.gama.mutamer.services;

import android.app.IntentService;
import android.content.Intent;

import com.gama.mutamer.data.models.User;
import com.gama.mutamer.data.repositories.IssuesRepository;
import com.gama.mutamer.data.repositories.NotificationsRepository;
import com.gama.mutamer.data.repositories.NumbersRepository;
import com.gama.mutamer.data.repositories.PlacesRepository;
import com.gama.mutamer.data.repositories.PrayersRepository;
import com.gama.mutamer.data.repositories.SettingsRepository;
import com.gama.mutamer.data.repositories.SlidersRepository;
import com.gama.mutamer.data.repositories.SosesRepository;
import com.gama.mutamer.data.repositories.UsersRepository;
import com.gama.mutamer.helpers.BroadcastHelper;
import com.gama.mutamer.helpers.DateDeserializer;
import com.gama.mutamer.helpers.DateHelper;
import com.gama.mutamer.helpers.firebase.FirebaseErrorEventLog;
import com.gama.mutamer.helpers.webService.ServicePost;
import com.gama.mutamer.helpers.webService.ServiceResult;
import com.gama.mutamer.utils.Constants;
import com.gama.mutamer.utils.MutamerApp;
import com.gama.mutamer.webServices.requests.SyncAdapterRequest;
import com.gama.mutamer.webServices.responses.SyncAdapterResponse;
import com.google.gson.GsonBuilder;

import java.util.Date;
import java.util.Locale;


/***
 * Intent Service to Sync App Data
 * Using Sync Adapter Last Update Approach
 */
public class SyncService extends IntentService {

    public static boolean isWorking = false;

    public SyncService() {
        super(Constants.SYNC_SERVICE_TAG);

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (!isWorking)
            SyncUserData();
    }

    /***
     * Sync User Specific Related Data
     */
    private void SyncUserData() {
        Date result = new SettingsRepository()
                .getLastUpdateDate();
        User user = new UsersRepository()
                .getLoggedUser();
        Thread t = new Thread(() -> {
            try {
                isWorking = true;

                MutamerApp.FORCE_USER_LOGIN = false;
                String lastUpdateString = DateHelper.ToDotNetDate(DateHelper.getDateWithExtraMillisecond(result), Locale.UK);

                SyncAdapterRequest request = new SyncAdapterRequest("", lastUpdateString);
                if(user != null){
                    request.setAccessToken(user.getAccessToken());
                }

                ServiceResult serviceResult = new ServicePost()
                        .DoPost(request, false, this);
                if (serviceResult.isSuccess()) {
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.registerTypeAdapter(Date.class, new DateDeserializer());
                    SyncAdapterResponse response = gsonBuilder.create().fromJson(serviceResult.getResult(), SyncAdapterResponse.class);

                    new SettingsRepository()
                            .updateLastUpdateDate(response.getLastUpdateDate());

                    new SosesRepository()
                            .updateData(response.getSos());

                    new IssuesRepository()
                            .updateIssues(response.getIssues());

                    new NotificationsRepository()
                            .updateNotifications(response.getNotifications());

                    new NumbersRepository()
                            .updateCategories(response.getImportantNumberCategories());

                    new NumbersRepository()
                            .updateNumbers(response.getImportantNumbers());

                    new PlacesRepository()
                            .updateCategories(response.getPlacesCategories());

                    new PlacesRepository()
                            .updatePlaces(response.getPlaces());

                    new PrayersRepository()
                            .updateCategories(response.getDuaaCategories());

                    new PrayersRepository()
                            .updateItems(response.getDuaa());

                    new SlidersRepository()
                            .updateItems(response.getSliders());

                }
                isWorking = false;
                BroadcastHelper.sendInform(this,Constants.BROARD_CAST_DATA_CHANGE);
            } catch (Exception ex) {
                FirebaseErrorEventLog.SaveEventLog(ex);
            }
        });
        t.start();

    }


}
