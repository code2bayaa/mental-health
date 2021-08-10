package com.example.mental;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class consultDoctor extends AppCompatActivity {

    private static final String ROOT_URL = "http://172.16.3.90/mental/";
    private TextInputEditText symptoms,height,weight;
    private Button addSymptom,consultD;
    private String symptomsAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_doctor);

        addSymptom = (Button) findViewById(R.id.add_symptom);
        consultD = (Button) findViewById(R.id.goConsult);
        symptoms = (TextInputEditText) findViewById(R.id.patient_symptom_edit);
        weight = (TextInputEditText) findViewById(R.id.patient_weight_edit);
        height = (TextInputEditText) findViewById(R.id.patient_height_edit);

        addSymptom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                symptomsAll += symptoms.getText().toString();
                if(!symptomsAll.isEmpty())
                    symptomsAll += "^";
                symptoms.setText("");
            }
        });
        consultD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((!weight.getText().toString().isEmpty()) && (!height.getText().toString().isEmpty()) && (symptomsAll != null)){

                    SharedPreferences pref = getSharedPreferences("doctor", Context.MODE_PRIVATE);
                    String doctor = pref.getString("doctor", null);

                    SharedPreferences prefUser = getSharedPreferences("user", Context.MODE_PRIVATE);
                    String user = prefUser.getString("user", null);

                    Toast.makeText(getApplicationContext(), doctor, Toast.LENGTH_SHORT).show();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(ROOT_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .build();
                    consultAPI consult = retrofit.create(consultAPI.class);
                    Call<String> call = consult.postTreatment(user,doctor,symptomsAll,weight.getText().toString(),height.getText().toString());
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            //progressBar.setVisibility(View.GONE);
                            if (response.isSuccessful()) {
                                if (response.body() != null) {
                                    Toast.makeText(getApplicationContext(), response.body(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "No response from the server", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "Response not successful "+response.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Error occurred!", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else {
                    if (symptomsAll == null)
                        Toast.makeText(getApplicationContext(), "Please add any symptom", Toast.LENGTH_SHORT).show();

                    if (weight.getText().toString().isEmpty())
                        Toast.makeText(getApplicationContext(), "Please add the your weight", Toast.LENGTH_SHORT).show();

                    if (height.getText().toString().isEmpty())
                        Toast.makeText(getApplicationContext(), "Please add the your height", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
}