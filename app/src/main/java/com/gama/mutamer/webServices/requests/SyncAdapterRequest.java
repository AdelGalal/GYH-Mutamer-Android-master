package com.gama.mutamer.webServices.requests;


import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gama.mutamer.R;
import com.gama.mutamer.utils.Constants;

import java.io.Serializable;

/**
 * Created by mustafa on 10/22/16.
 * Release the GEEK
 */

public class SyncAdapterRequest extends BaseRequest {

    @Expose
    @SerializedName("LastUpdateDate")
    private String LastUpdateDate;

    @Expose
    @SerializedName(Constants.USER_TOKEN)
    private String UserToken;


    public SyncAdapterRequest(String userToken, String lastUpdateDate) {
        setLastUpdateDate(lastUpdateDate);
        setUserToken(userToken);
    }

    @Override
    public int getServiceUrl() {
        return R.string.url_update_data;
    }

    @Override
    public String getData() {
        return new Gson().toJson(this);
    }

    public String getLastUpdateDate() {
        return LastUpdateDate;
    }

    public void setLastUpdateDate(String lastUpdateDate) {
        LastUpdateDate = lastUpdateDate;
    }

    public String getUserToken() {
        return UserToken;
    }

    public void setUserToken(String userToken) {
        UserToken = userToken;
    }
}
