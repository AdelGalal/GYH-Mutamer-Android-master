package com.gama.mutamer.models;

import android.content.ContentValues;
import android.database.Cursor;

/**
 * Created by mustafa on 6/5/16.
 * Release the GEEK
 */
public abstract class BaseModel {

    public String ID_FIELD = "id";

    public String getSqlList() {
        return "SELECT * FROM " + getTableName();
    }


    public String getSqlDelete(int id) {
        return "DELETE FROM " + getTableName() + " WHERE " + ID_FIELD + " = " + id;
    }


    public String getSqlSingle(int id) {
        return "SELECT * FROM " + getTableName() + " WHERE " + ID_FIELD + " = " + id;
    }


    public String getSqlClear() {
        return "DELETE  FROM " + getTableName();
    }


    public abstract BaseModel fromCursor(Cursor cursor);


    public abstract ContentValues toContentValues();


    public abstract String getTableName();

    public abstract String getSqlCreation();
}
