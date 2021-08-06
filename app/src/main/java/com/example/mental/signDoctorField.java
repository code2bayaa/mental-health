package com.example.mental;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface signDoctorField {
    @FormUrlEncoded
    @POST("sign_up_doctor.php")
    Call<String> getData(
            @Field("nameImg") String nameR,
            @Field("image") String imgR,
            @Field("email") String mEmail,
            @Field("name") String Name,
            @Field("password") String mPassword,
            @Field("address") String mAddress,
            @Field("age") String mAge,
            @Field("telephone") String mTelephone,
            @Field("special") String Special,
            @Field("biography") String biography,
            @Field("wAddress") String w_address,
            @Field("certification") String certification
    );
}