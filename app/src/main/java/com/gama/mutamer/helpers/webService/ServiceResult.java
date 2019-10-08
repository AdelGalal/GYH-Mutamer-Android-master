package com.gama.mutamer.helpers.webService;


public class ServiceResult {
    /***
     * Vars
     */
//    @Expose
//    @SerializedName("Success")
    private boolean mSuccess = false;
    private String mResult = "";

    public ServiceResult() {

    }

    public ServiceResult(String result) {
        this.setSuccess(true);
        this.setResult(result);
    }

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }

    public boolean isSuccess() {
        return mSuccess;
    }

    public void setSuccess(boolean success) {
        mSuccess = success;
    }
}
