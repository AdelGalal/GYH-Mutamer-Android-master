package com.gama.mutamer.webServices.responses;

/**
 * Created by mustafa on 5/15/18.
 * Release the GEEK
 */
public interface IResponse {


    boolean isSuccess();
    boolean isNetworkSuccess();

    void setSuccess(boolean success);
    void setNetworkSuccess(boolean networkSuccess);
}
