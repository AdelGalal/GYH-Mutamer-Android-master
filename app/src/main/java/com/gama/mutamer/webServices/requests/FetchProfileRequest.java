package com.gama.mutamer.webServices.requests;

import com.gama.mutamer.R;

/**
 * Created by mustafa on 12/10/18.
 * Release the GEEK
 */
public class FetchProfileRequest extends BaseRequest {

    public FetchProfileRequest(String accessToken){
        setAccessToken(accessToken);

    }

    @Override public int getServiceUrl() {
        return R.string.url_fetch_profile;
    }

    @Override public String getData() {
        return "";
    }
}
