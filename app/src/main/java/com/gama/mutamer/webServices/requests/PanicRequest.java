package com.gama.mutamer.webServices.requests;

import com.google.gson.Gson;
import com.gama.mutamer.R;
import com.gama.mutamer.viewModels.webServices.LocationViewModel;

/**
 * Created by mustafa on 8/23/16.
 * Release the GEEK
 */
public class PanicRequest extends BaseRequest {
    private LocationViewModel mLocationViewModel;

    public PanicRequest(LocationViewModel model) {
        setLocationViewModel(model);
    }

    @Override
    public int getServiceUrl() {
        return R.string.url_panic;
    }

    @Override
    public String getData() {
        return new Gson().toJson(getLocationViewModel());
    }

    public LocationViewModel getLocationViewModel() {
        return mLocationViewModel;
    }

    public void setLocationViewModel(LocationViewModel locationViewModel) {
        mLocationViewModel = locationViewModel;
    }
}
