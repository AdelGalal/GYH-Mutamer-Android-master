package com.gama.mutamer.webServices.responses;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/***
 * Base Service Result
 */
public class BaseServiceResponse {

    @Expose
    @SerializedName("Success")
    private boolean mSuccess;


    public boolean isSuccess() {
        return mSuccess;
    }

    public void setmSuccess(boolean mSuccess) {
        this.mSuccess = mSuccess;
    }
}
