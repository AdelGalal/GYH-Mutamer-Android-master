package com.gama.mutamer.viewModels.shared;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by mustafa on 8/21/17.
 * Release the GEEK
 */

public class CommonStatementViewModel {

    @Expose
    @SerializedName("Id")
    private long Id;

    @Expose
    @SerializedName("Statement")
    private String Statement;

    @Expose
    @SerializedName("Translation")
    private String Translation;

    @Expose
    @SerializedName("ReadableStatement")
    private String ReadableStatement;

    public long getId() {
        return Id;
    }

    public String getStatement() {
        return Statement;
    }

    public String getTranslation() {
        return Translation;
    }

    public String getReadableStatement() {
        return ReadableStatement;
    }
}
