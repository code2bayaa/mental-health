package com.example.mental;

import android.annotation.SuppressLint;
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

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public  class patientsDoctor extends Fragment {
    private OnFragmentInteractionListener mListener;
    ListView list;
    TextView mentalPatients;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_patients_doctor, container, false);

        list = (ListView) rootView.findViewById(R.id.profile_patients_list);
        mentalPatients = (TextView) rootView.findViewById(R.id.mental_patients);

        teleportData();
        return rootView;
    }

    private void teleportData(){
        String permit = "patients";
        String user = MainActivity.userSession.getString("user");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(patientsAPI.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        patientsAPI api = retrofit.create(patientsAPI.class);
        Call<String> call = api.getStrings(permit,user);
        call.enqueue(new Callback<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mentalPatients.setText(response.body());
                        JSONObject data = null;
                        /*try {
                            //data = new JSONObject(response.body());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            String message = data.getString("message");
                            if(message.equals("Success!")) {
                                JSONObject patients = new JSONObject(data.getString("data"));

                                //ArrayList<SubjectData> arrayList = new ArrayList<SubjectData>();
                                //plotValues(data);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }*/

                        //plotValues(data);
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

    /*
           JSONObject jsonRootObject = new JSONObject(strJson);
         JSONArray jsonArray = jsonRootObject.optJSONArray("Employee");
         for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            int id = Integer.parseInt(jsonObject.optString("ID"));
            String name = jsonObject.optString("Name");
            float salary = Float.parseFloat(jsonObject.optString("Salary"));
            data.append("Employee ").append(i).append(" : \n ID= ")
               .append(id).append(" \n " + "Name= ")
               .append(name).append(" \n Salary= ")
               .append(salary).append(" \n\n ");
         }

             JSONObject json = new JSONObject(content);
    JSONArray jArray = json.getJSONArray("rows");
                JSONObject json_data = null;
                for (int i = 0; i < jArray.length(); i++) {
                    json_data = jArray.getJSONObject(i);
                    String fname = json_data.getString("Fname");
    String lname = json_data.getString("Lname");
                    HashMap<String, String>map=new HashMap<String, String>();
                    map.put("Fname",Fname);
                    map.put("LName", Lname);
                    alist.add(map);
                }
     */

    /*  private void plotValues(ArrayList<profile_POJO.data> data){

      CustomAdapter customAdapter = new CustomAdapter(getContext(), data);
      //ListAdapter customAdapter = new ListAdapter(this, R.layout.fragment_profile_doctor, data);

      list.setAdapter(customAdapter);
  }
*/

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }
}