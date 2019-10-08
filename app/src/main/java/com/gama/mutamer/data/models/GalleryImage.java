package com.gama.mutamer.data.models;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by mustafa on 8/8/17.
 * Release the GEEK
 */

public class GalleryImage extends RealmObject {

    @PrimaryKey
    private int Id;

    private Date CaptureDate;

    private double Latitude;

    private double Longitude;

}
