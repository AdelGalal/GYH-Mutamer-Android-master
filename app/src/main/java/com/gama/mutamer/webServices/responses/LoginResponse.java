package com.gama.mutamer.webServices.responses;

import com.gama.mutamer.data.models.User;
import com.google.gson.Gson;

/**
 * Created by mustafa on 11/22/18.
 * Release the GEEK
 */
public class LoginResponse implements IResponse {

    private User mLoginInfo;
    private boolean mSuccess = false, mNetworkSuccess = false;

    private String mResult;

    public LoginResponse() {

    }

    public LoginResponse(String response) {
        try {
            mResult = response;
            mLoginInfo = new Gson().fromJson(response, User.class);
            if (mLoginInfo.getAccessToken() != null && mLoginInfo.getAccessToken().length() > 10)
                setSuccess(true);
            setNetworkSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            setSuccess(false);
        }
    }


    @Override
    public boolean isNetworkSuccess() {
        return mNetworkSuccess;
    }

    @Override
    public void setNetworkSuccess(boolean networkSuccess) {
        mNetworkSuccess = networkSuccess;
    }

    @Override
    public boolean isSuccess() {
        return mSuccess;
    }

    @Override
    public void setSuccess(boolean success) {
        mSuccess = success;
    }

    public User getLoginInfo() {
        return mLoginInfo;
    }

    public void setLoginInfo(User loginInfo) {
        mLoginInfo = loginInfo;
    }

    public String getResult() {
        return mResult;
    }
}

