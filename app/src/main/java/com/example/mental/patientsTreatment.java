package com.example.mental;

import android.content.Context;
import android.content.Intent;
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

public class patientsTreatment extends AppCompatActivity {

    private static final String ROOT_URL = "http://172.16.3.90/mental/";
    private TextInputEditText medicine,sickness,analysis;
    private Button addRecord,treatP,recordP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_treatment);

        addRecord = (Button) findViewById(R.id.treat_record_button);
        recordP = (Button) findViewById(R.id.goRecord);
        treatP = (Button) findViewById(R.id.goTreat);
        medicine = (TextInputEditText) findViewById(R.id.treat_medication_edit);
        analysis = (TextInputEditText) findViewById(R.id.treat_analysis_edit);
        sickness = (TextInputEditText) findViewById(R.id.treat_sickness_edit);

        recordP.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),patientsRecords.class)));
        treatP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Click add records", Toast.LENGTH_SHORT).show();
            }
        });
        addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if((!medicine.getText().toString().isEmpty()) && (!sickness.getText().toString().isEmpty()) && (!analysis.getText().toString().isEmpty())){

                    SharedPreferences pref = getSharedPreferences("patient", Context.MODE_PRIVATE);
                    String record = pref.getString("record", null);

                    Toast.makeText(getApplicationContext(), record, Toast.LENGTH_SHORT).show();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(ROOT_URL)
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .build();
                    PatientTreatment patientTreatment = retrofit.create(PatientTreatment.class);
                    Call<String> call = patientTreatment.postTreatment(medicine.getText().toString(),analysis.getText().toString(),sickness.getText().toString(),record);
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
                    if (medicine.getText().toString().isEmpty())
                        Toast.makeText(getApplicationContext(), "Please add the patient's medication", Toast.LENGTH_SHORT).show();

                    if (analysis.getText().toString().isEmpty())
                        Toast.makeText(getApplicationContext(), "Please add the patient's analysis", Toast.LENGTH_SHORT).show();

                    if (sickness.getText().toString().isEmpty())
                        Toast.makeText(getApplicationContext(), "Please add the patient's sickness", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }
}