package com.example.mental;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public  class doctorList extends Fragment {
    private OnFragmentInteractionListener mListener;
    private ListView list;
    private TextView Doctors;
    private ArrayList<String> recordArr;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_doctor_list, container, false);

        list = (ListView) rootView.findViewById(R.id.doctor_patients_list);
        Doctors = (TextView) rootView.findViewById(R.id.doctor_lists);

        teleportData();

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                String recordE = recordArr.get(position);

                SharedPreferences pref = getActivity().getSharedPreferences("doctor", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.putString("doctor", recordE);
                edit.commit();

                Toast.makeText(getContext(),"Please wait...",Toast.LENGTH_SHORT).show();

                startActivity(new Intent(getContext(), consultDoctor.class));

            }
        });

        return rootView;
    }

    private void teleportData(){
        String permit = "999";

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(doctorListAPI.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        doctorListAPI api = retrofit.create(doctorListAPI.class);
        Call<String> call = api.getStrings(permit);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //progressBar.setVisibility(View.GONE);
                //Doctors.setText(response.body());

                if (response.isSuccessful()) {
                    if (response.body() != null) {

                        try {
                            JSONObject data = new JSONObject(response.body());
                           String message = data.getString("message");
                            if(message.equals("Success!")) {
                                JSONArray doctors = data.getJSONArray("data");

                                recordArr = new ArrayList<String>();
                                ArrayList<doctorsData> arrayList = new ArrayList<doctorsData>();

                                for (int i = 0; i < doctors.length(); i++) {

                                    JSONObject jsonObject = doctors.getJSONObject(i);

                                   String address = jsonObject.optString("address");
                                   String name = jsonObject.optString("name");
                                    String telephone = jsonObject.optString("telephone");
                                    int age = Integer.parseInt(jsonObject.optString("age"));
                                    String image = jsonObject.optString("image");
                                    String email = jsonObject.optString("email");
                                    String wAddress = jsonObject.optString("wAddress");
                                    String biography = jsonObject.optString("biography");
                                    String certification = jsonObject.optString("certification");

                                    recordArr.add(email);
                                    arrayList.add(new doctorsData(address,name,telephone,age,image,email,wAddress,biography,certification));
                                }

                                if(arrayList.size() > 0) {
                                    plotValues(arrayList);
                                }else {
                                    Toast.makeText(getContext(), "No Doctors Available", Toast.LENGTH_SHORT).show();
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

    private void plotValues(ArrayList<doctorsData> data){

      CustomAdapterDoctor customAdapter = new CustomAdapterDoctor(R.layout.doctors_structure, getContext(), data);

      list.setAdapter(customAdapter);
    }


    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }
}