package com.kxland.hydrodonoremote.listener;

import com.kxland.hydrodonoremote.data.LocationResult;

public interface GeocodingServiceListener {
    void geocodeSuccess(LocationResult location);

    void geocodeFailure(Exception exception);
}
