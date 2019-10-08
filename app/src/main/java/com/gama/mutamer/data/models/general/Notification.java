package com.gama.mutamer.data.models.general;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/***
 * App Notification Database Model
 */
public class Notification extends RealmObject implements Serializable {

    /***
     * Primary Key
     */
    @PrimaryKey
    private long Id;

    /***
     * Notification's Title/Name
     */
    private String Title;

    /***
     * Notification's Body
     */
    private String Body;


    private int Type;

    /***
     * Notification's Issue Date
     */
    private Date Date;

    /***
     * Notification's Last Update Date
     */
    private Date LastUpdateDate;

    /***
     * Does This notification had been seen?
     */
    private boolean IsRead;


    public long getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getBody() {
        return Body;
    }

    public void setBody(String body) {
        Body = body;
    }

    public java.util.Date getDate() {
        return Date;
    }

    public void setDate(java.util.Date date) {
        Date = date;
    }

    public java.util.Date getLastUpdateDate() {
        return LastUpdateDate;
    }


    public boolean isRead() {
        return IsRead;
    }

    public void setRead(boolean read) {
        IsRead = read;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}
