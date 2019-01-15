package com.example.alexeyz.fragmenttesting;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface SelinaApi {

    String ENDPOINT = "https://apiqa.selina.com/" + "api/";

    String LOCATIONS_URL = "locations";

    @GET(LOCATIONS_URL)
    Observable<List<Location>> getLocationsRx();
}