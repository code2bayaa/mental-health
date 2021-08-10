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
public class patientCases extends Fragment {

    private patientCases.OnFragmentInteractionListener mListener;
    private ListView list;
    private TextView mentalPatients;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_patient_cases, container, false);

        list = (ListView) rootView.findViewById(R.id.patient_cases);
        teleportData();

        return rootView;
    }

    private void teleportData(){
        String permit = "cases";
        SharedPreferences pref = this.getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        String user = pref.getString("user", null);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(patientsAPI.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        patientsAPI api = retrofit.create(patientsAPI.class);
        Call<String> call = api.getStrings(permit,user);
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
                                JSONArray patients = data.getJSONArray("data");

                                ArrayList<patientsData> arrayList = new ArrayList<patientsData>();

                                for (int i = 0; i < patients.length(); i++) {

                                    JSONObject jsonObject = patients.getJSONObject(i);

                                    String name = jsonObject.optString("name");
                                    String telephone = jsonObject.optString("telephone");
                                    int age = Integer.parseInt(jsonObject.optString("age"));
                                    String image = jsonObject.optString("image");
                                    String email = jsonObject.optString("email");
                                    String sickness = jsonObject.optString("sickness");
                                    String symptoms = jsonObject.optString("symptoms");
                                    float height = Float.parseFloat(jsonObject.optString("height"));
                                    float weight = Float.parseFloat(jsonObject.optString("weight"));
                                    String date = jsonObject.optString("date");

                                    arrayList.add(new patientsData(name,telephone,age,image,email,symptoms,height,weight,sickness,date));
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

    private void plotValues(ArrayList<patientsData> data){

        CasesAdapter casesAdapter = new CasesAdapter(getContext(), data);
        //ListAdapter customAdapter = new ListAdapter(this, R.layout.fragment_profile_doctor, data);

        list.setAdapter(casesAdapter);
    }

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }

}