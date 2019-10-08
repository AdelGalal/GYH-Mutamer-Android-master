package com.gama.mutamer.interfaces;


public interface IResponse {


    boolean isSuccess();

    void setSuccess(boolean success);

    boolean isNetworkSuccess();

    void setNetworkSuccess(boolean networkSuccess);
}
