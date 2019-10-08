package com.gama.mutamer.helpers;


import com.gama.mutamer.viewModels.webServices.GooglePlaceResult;

import java.util.Comparator;

/**
 * Created by mustafa on 8/11/17.
 * Release the GEEK
 */

public class CustomComparator implements Comparator<GooglePlaceResult> {
    @Override
    public int compare(GooglePlaceResult o1, GooglePlaceResult o2) {
        return (int) (o1.getDistance() * 100 - o2.getDistance() * 100);
    }
}
