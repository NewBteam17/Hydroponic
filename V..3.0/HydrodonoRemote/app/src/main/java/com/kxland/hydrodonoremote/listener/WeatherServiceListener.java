package com.kxland.hydrodonoremote.listener;

import com.kxland.hydrodonoremote.data.Channel;

public interface WeatherServiceListener {
    void serviceSuccess(Channel channel);

    void serviceFailure(Exception exception);
}
