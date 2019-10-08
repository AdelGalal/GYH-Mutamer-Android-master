package com.gama.mutamer.webServices.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.gama.mutamer.viewModels.webServices.CurrencyRates;

/**
 * Created by mustafa on 8/23/17.
 * Release the GEEK
 */

public class ExchangeResponse extends BaseServiceResponse {

    @Expose
    @SerializedName("rates")
    private CurrencyRates mResult;

    public ExchangeResponse() {
        setmSuccess(true);
    }

    public CurrencyRates getResult() {
        return mResult;
    }
}
