package com.example.alexeyz.fragmenttesting;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Location extends LocationExperienceInterface implements Serializable {

    public Long _id;

    @SerializedName("id")
    public String locationId;

    @SerializedName("value")
    public String value;

    /**
     * No args constructor for use in serialization
     *
     */
    public Location() {
    }

    @Override
    public String getId() {
        return locationId;
    }

    @Override
    public String getValue() {
        return this.value;
    }

}