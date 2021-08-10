package com.example.mental;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface consultAPI {
    @FormUrlEncoded
    @POST("consultDoctor.php")
    Call<String> postTreatment(
            @Field("user") String user,
            @Field("doctor") String doctor,
            @Field("symptoms") String symptoms,
            @Field("height") String height,
            @Field("weight") String weight
    );
}

