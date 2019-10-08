package com.gama.mutamer.webServices.responses;

import com.gama.mutamer.data.models.Issue;
import com.gama.mutamer.data.models.Sos;
import com.gama.mutamer.data.models.content.Duaa;
import com.gama.mutamer.data.models.content.DuaaCategory;
import com.gama.mutamer.data.models.content.ImportantNumber;
import com.gama.mutamer.data.models.content.ImportantNumberCategory;
import com.gama.mutamer.data.models.content.Place;
import com.gama.mutamer.data.models.content.PlaceCategory;
import com.gama.mutamer.data.models.content.Slider;
import com.gama.mutamer.data.models.general.Notification;
import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by mustafa on 6/12/16.
 * Release the GEEK
 */
public class SyncAdapterResponse extends BaseServiceResponse {

    /***
     * Data last update date to sent and keep in Mutamer App
     * for next Sync Adapter Request
     * for solving Timezone issue
     */
    @Expose
    @SerializedName(Constants.LAST_UPDATE_DATE)
    private Date mLastUpdateDate;

    /***
     * List of unread notifications
     */
    @Expose
    @SerializedName(Constants.NOTIFICATIONS)
    private ArrayList<Notification> mNotifications;

    @Expose
    @SerializedName(Constants.SOS)
    private ArrayList<Sos> mSos;

    @Expose
    @SerializedName(Constants.DUAA_CATEGORIES)
    private ArrayList<DuaaCategory> mDuaaCategories;

    @Expose
    @SerializedName(Constants.DUAA)
    private ArrayList<Duaa> mDuaa;

    @Expose
    @SerializedName(Constants.PLACES_CATEGORIES)
    private ArrayList<PlaceCategory> mPlacesCategories;

    @Expose
    @SerializedName(Constants.PLACES)
    private ArrayList<Place> mPlaces;

    @Expose
    @SerializedName(Constants.ISSUES)
    private ArrayList<Issue> mIssues;

    @Expose
    @SerializedName(Constants.NUMBERS_CATEGORIES)
    private ArrayList<ImportantNumberCategory> mImportantNumberCategories ;

    @Expose
    @SerializedName(Constants.NUMBERS)
    private ArrayList<ImportantNumber> mImportantNumbers ;

    @Expose
    @SerializedName(Constants.SLIDERS)
    private ArrayList<Slider> mSliders;


    public ArrayList<Notification> getNotifications() {
        return mNotifications;
    }

    public ArrayList<Sos> getSos() {
        return mSos;
    }

    public ArrayList<DuaaCategory> getDuaaCategories() {
        return mDuaaCategories;
    }

    public ArrayList<Duaa> getDuaa() {
        return mDuaa;
    }

    public ArrayList<PlaceCategory> getPlacesCategories() {
        return mPlacesCategories;
    }

    public ArrayList<Place> getPlaces() {
        return mPlaces;
    }

    public ArrayList<Issue> getIssues() {
        return mIssues;
    }



    public ArrayList<ImportantNumberCategory> getImportantNumberCategories() {
        return mImportantNumberCategories;
    }

    public ArrayList<ImportantNumber> getImportantNumbers() {
        return mImportantNumbers;
    }

    public Date getLastUpdateDate() {
        return mLastUpdateDate;
    }

    public ArrayList<Slider> getSliders() {
        return mSliders;
    }
}
