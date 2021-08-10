package com.example.mental;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class patientFiles extends Fragment {

    private patientFiles.OnFragmentInteractionListener mListener;
    private ListView list;
    private TextView mentalPatient;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_patient_files, container, false);

        list = (ListView) rootView.findViewById(R.id.patient_files);
        teleportData();

        return rootView;
    }

    private void teleportData(){
        String permit = "999";
        SharedPreferences pref = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String user = pref.getString("user", null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(medicalAPI.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        medicalAPI api = retrofit.create(medicalAPI.class);
        Call<String> call = api.getMedical(permit,user);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        try {
                            JSONObject data = new JSONObject(response.body());
                            String message = data.getString("message");
                            if(message.equals("Success!")) {
                                JSONArray patients = data.getJSONArray("data");

                                ArrayList<patientData> arrayList = new ArrayList<patientData>();

                                for (int i = 0; i < patients.length(); i++) {

                                    JSONObject jsonObject = patients.getJSONObject(i);

                                    String doctorName = jsonObject.optString("name");
                                    String analysis = jsonObject.optString("analysis");
                                    String medicine = jsonObject.optString("medicine");
                                    String doctorImage = jsonObject.optString("image");
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
                                    Toast.makeText(getContext(), "No Cases Solved", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getContext(), "No response from the server", Toast.LENGTH_SHORT).show();

                    }
                }else{
                    Toast.makeText(getContext(), "Response not successful "+response.toString(), Toast.LENGTH_SHORT).show();

                }
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Error occurred during upload", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void plotValues(ArrayList<patientData> data){

        PatientAdapter casesAdapter = new PatientAdapter(getContext(), data);

        list.setAdapter(casesAdapter);
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}