package com.example.mental;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PatientTreatment {
    @FormUrlEncoded
    @POST("treatPatient.php")
    Call<String> postTreatment(
            @Field("medicine") String medicine,
            @Field("analysis") String analysis,
            @Field("sickness") String sickness,
            @Field("record") String record
    );
}

