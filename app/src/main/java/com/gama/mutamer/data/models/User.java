package com.gama.mutamer.data.models;

import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/***
 * Logged User Details Model
 */
public class User extends RealmObject {




    /***
     * Primary Key
     */
    @PrimaryKey
    private String Id;

    /***
     * User Logged Name - Email Address
     */
    @Expose
    @SerializedName(Constants.USER_NAME_FIELD)
    private String UserName;

    /***
     * Logged User Password
     */
    @Expose
    @SerializedName(Constants.PASSWORD_FIELD)
    private String Password;

    /***
     * Logged User's Display name
     */
    @Expose
    @SerializedName(Constants.DISPLAY_NAME)
    private String DisplayName;

    /***
     * Logged User Access Token
     */
    @Expose
    @SerializedName(Constants.ACCESS_TOKEN_FIELD)
    private String AccessToken;

    @Expose
    @SerializedName(Constants.TYPE)
    private int Type;


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getDisplayName() {
        return DisplayName;
    }

    public void setDisplayName(String displayName) {
        DisplayName = displayName;
    }
}


