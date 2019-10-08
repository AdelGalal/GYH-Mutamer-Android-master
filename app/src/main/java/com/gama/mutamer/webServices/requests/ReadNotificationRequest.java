package com.gama.mutamer.webServices.requests;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gama.mutamer.R;
import com.gama.mutamer.helpers.gson.GsonHelper;
import com.gama.mutamer.utils.Constants;

/**
 * Created by mustafa on 8/12/16.
 * Release the GEEK
 */
public class ReadNotificationRequest extends BaseRequest {

    @Expose
    @SerializedName(Constants.ID)
    private int mId;

    @Expose
    @SerializedName(Constants.USER_TOKEN)
    private String Token;


    public ReadNotificationRequest(String userToken, int id) {
        setId(id);
        setToken(userToken);
    }

    @Override
    public int getServiceUrl() {
        return R.string.url_read_notification;
    }

    @Override
    public String getData() {
        return GsonHelper.getGson().toJson(this);
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }


    public int getmId() {
        return mId;
    }

    public void setmId(int mId) {
        this.mId = mId;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }
}
