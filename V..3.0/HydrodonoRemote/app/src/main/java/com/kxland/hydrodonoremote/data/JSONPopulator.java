package com.kxland.hydrodonoremote.data;

/**
 * Created by Asus on 14/09/2017.
 */
import org.json.JSONObject;

public interface JSONPopulator {
    void populate(JSONObject data);

    JSONObject toJSON();
}