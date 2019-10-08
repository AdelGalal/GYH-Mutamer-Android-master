package com.gama.mutamer.data.models.PilgrimData;

import com.gama.mutamer.utils.Constants;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import io.realm.RealmObject;

/**
 * Created by mustafa on 12/10/18.
 * Release the GEEK
 */
public class AirLine extends RealmObject implements Serializable {

    @Expose
    @SerializedName(Constants.CARRIER)
    private Carrier Carrier;

    @Expose
    @SerializedName(Constants.TERMINAL)
    private NameModel Terminal;


    public com.gama.mutamer.data.models.PilgrimData.Carrier getCarrier() {
        return Carrier;
    }

    public NameModel getTerminal() {
        return Terminal;
    }
}
