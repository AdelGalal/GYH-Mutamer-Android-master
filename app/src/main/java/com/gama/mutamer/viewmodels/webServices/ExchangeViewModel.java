package com.gama.mutamer.viewModels.webServices;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mustafa on 8/23/17.
 * Release the GEEK
 */

public class ExchangeViewModel {

    @Expose
    @SerializedName("base")
    private String BaseCurrancy;

    @Expose
    @SerializedName("symbols")
    private String ToCurrancy;


    public ExchangeViewModel(String baseCurrancy,String toCurrancy) {
        setBaseCurrancy(baseCurrancy);
        setToCurrancy(toCurrancy);
    }

    public void setBaseCurrancy(String baseCurrancy) {
        BaseCurrancy = baseCurrancy;
    }

    public String getBaseCurrancy() {
        return BaseCurrancy;
    }

    public String getToCurrancy() {
        return ToCurrancy;
    }

    public void setToCurrancy(String toCurrancy) {
        ToCurrancy = toCurrancy;
    }
}
