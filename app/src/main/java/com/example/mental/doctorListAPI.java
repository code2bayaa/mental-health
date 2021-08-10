package com.example.mental;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface doctorListAPI {
    String BASE_URL = "http://172.16.3.90/mental/";
    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST("getDoctors.php")
    Call<String> getStrings(
            @Field("permit") String permit
    );
}
