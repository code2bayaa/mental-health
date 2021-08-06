package com.example.mental;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface MyImageInterface {
    @FormUrlEncoded
    @POST("signup.php")
    Call<String> getImageData(
            @Field("nameImg") String nameR,
            @Field("image") String imgR,
            @Field("email") String email,
            @Field("name") String name,
            @Field("password") String password,
            @Field("address") String address,
            @Field("age") String age,
            @Field("telephone") String telephone,
            @Field("special") String special
    );
}

