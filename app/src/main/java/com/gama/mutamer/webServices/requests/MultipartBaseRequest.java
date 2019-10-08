package com.gama.mutamer.webServices.requests;

import com.gama.mutamer.viewModels.webServices.PostParam;

import java.util.ArrayList;

/**
 * Created by mustafa on 8/8/17.
 * Release the GEEK
 */

public abstract class MultipartBaseRequest {
    protected String mAccessToken;

    public abstract int getServiceUrl();

    public abstract ArrayList<PostParam> getData();

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }
}
