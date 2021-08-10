package com.example.mental;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class patientsRecords extends AppCompatActivity {
    private ListView list;
    private TextView patientName,patientAge;
    private Button treatP,recordP;
    private ImageView patientImg;
    private JSONArray patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_records);

        list = (ListView) findViewById(R.id.profile_patients_list);
        patientName = (TextView) findViewById(R.id.treat_patient_name);
        patientAge = (TextView) findViewById(R.id.treat_patient_age);
        treatP = (Button) findViewById(R.id.treat_patient_button);
        recordP = (Button) findViewById(R.id.record_button);
        patientImg = (ImageView) findViewById(R.id.passportPatient);

        teleportData();

        treatP.setOnClickListener(v -> startActivity(new Intent(this,patientsTreatment.class)));
        recordP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(patient != null){
                    Toast.makeText(getApplicationContext(), "Please scroll down to view patient records", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(), "Patient has no previous records", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void teleportData(){
        SharedPreferences pref = getSharedPreferences("patient", Context.MODE_PRIVATE);
        String user = pref.getString("patient", null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(patientAPI.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        patientAPI api = retrofit.create(patientAPI.class);
        Call<String> call = api.getStrings(user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //progressBar.setVisibility(View.GONE);
                //mentalPatients.setText(response.body());

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        try {
                            JSONObject data = new JSONObject(response.body());
                            String message = data.getString("message");
                            if(message.equals("Success!")) {
                                patient = data.optJSONArray("records");

                                ArrayList<patientData> arrayList = new ArrayList<patientData>();

                                for (int i = 0; i < patient.length(); i++) {

                                    JSONObject jsonObject = patient.getJSONObject(i);

                                    String doctorName = jsonObject.optString("doctorName");
                                    String analysis = jsonObject.optString("analysis");
                                    String doctorImage = jsonObject.optString("doctorImage");
                                    String medicine = jsonObject.optString("medicine");
                                    String sickness = jsonObject.optString("sickness");
                                    String symptoms = jsonObject.optString("symptoms");
                                    float height = Float.parseFloat(jsonObject.optString("height"));
                                    float weight = Float.parseFloat(jsonObject.optString("weight"));
                                    String date = jsonObject.optString("date");

                                    arrayList.add(new patientData(sickness,symptoms,doctorImage,doctorName,medicine,analysis,height,weight,date));
                                }
                                if(arrayList.size() > 0) {
                                    plotValues(arrayList);
                                }else {
                                    Toast.makeText(getApplicationContext(), "Patient has no prior medical records", Toast.LENGTH_SHORT).show();
                                }

                                JSONObject profile = new JSONObject(data.getString("info"));

                                patientName.setText(Html.fromHtml("<b>NAME</b><br>")+""+profile.getString("name"));
                                patientAge.setText(Html.fromHtml("<b>AGE</b><br>")+""+profile.getString("age"));
                                patientsRecords.LoadImage loadImage = new patientsRecords.LoadImage(patientImg);
                                loadImage.execute("http://192.168.0.22/mentalImgs/" + profile.getString("image"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No response from the server", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(getApplicationContext(), "Response not successful "+response.toString(), Toast.LENGTH_SHORT).show();

                }
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Error occurred during upload", Toast.LENGTH_SHORT).show();

            }
        });


    }

    @SuppressLint("StaticFieldLeak")
    private class LoadImage extends AsyncTask<String, Void, Bitmap> {
        ImageView passport;
        public LoadImage(ImageView passport){

            this.passport = passport;
        }
        @Override
        protected Bitmap doInBackground(String...strings){
            String urlLink = strings[0];
            Bitmap bitmap = null;

            try {
                InputStream inputStream = new java.net.URL(urlLink).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }
        @Override
        protected void onPostExecute(Bitmap bitmap){
            passport.setImageBitmap(bitmap);
        }
    }

    private void plotValues(ArrayList<patientData> data){

        PatientAdapter customAdapter = new PatientAdapter(getApplicationContext(), data);

        list.setAdapter(customAdapter);
    }

}