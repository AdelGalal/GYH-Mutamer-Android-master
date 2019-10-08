package com.gama.mutamer.viewModels.webServices;


import android.content.Context;

import com.gama.mutamer.R;
import com.gama.mutamer.interfaces.IViewModel;


/***
 * User login param
 */
public class LoginViewParam implements IViewModel {


    private String mUserName, mPassword;

    public LoginViewParam() {

    }

    public LoginViewParam(String _userName, String _password, boolean saveData, Context context) {
        setUserName(_userName);
        setPassword(_password);
    }


    @Override
    public ValidationResult validate() {
        if (getUserName() == null || getUserName().length() < 3)
            return new ValidationResult(false, R.string.please_enter_passport_number);
        if (getPassword() == null || getPassword().length() < 3)
            return new ValidationResult(false, R.string.please_enter_mobile_number);

        return new ValidationResult(true, R.string.success);
    }

    @Override
    public String serviceFormat() {
        return "username=" + getUserName() + "&password=" + getPassword() + "&grant_type=password";

    }


    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

}
