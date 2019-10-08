package com.gama.mutamer.webServices.requests;


public abstract class BaseRequest {
    protected String mAccessToken;

    public abstract int getServiceUrl();

    public abstract String getData();

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }
}
