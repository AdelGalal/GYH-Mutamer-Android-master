package com.gama.mutamer.webServices.requests;

import com.gama.mutamer.R;
import com.gama.mutamer.viewModels.webServices.LoginViewParam;

/**
 * Created by mustafa on 11/22/18.
 * Release the GEEK
 */
public class LoginRequest extends BaseRequest {
    private LoginViewParam mModel;

    public LoginRequest(){
        setModel(new LoginViewParam());
    }

    @Override
    public int getServiceUrl() {
        return R.string.url_login;
    }

    @Override
    public String getData() {
        return getModel().serviceFormat();
    }

    public LoginViewParam getModel() {
        return mModel;
    }

    public void setModel(LoginViewParam model) {
        mModel = model;
    }
}
