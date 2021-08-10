package com.example.mental;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class profileDoctor extends Fragment {

    private OnFragmentInteractionListener mListener;
    TextView emailTxt,nameTxt,telephoneTxt,ageTxt,specialTxt,biographyTxt,workTxt,homeTxt;
    ListView list;
    ImageView passport;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile_doctor, container, false);

        //INITIALIZE VIEWS
        emailTxt = (TextView) rootView.findViewById(R.id.emailTxt);
        biographyTxt = (TextView) rootView.findViewById(R.id.biographyTxt);
        nameTxt = (TextView) rootView.findViewById(R.id.nameTxt);
        telephoneTxt = (TextView) rootView.findViewById(R.id.telephoneTxt);
        ageTxt = (TextView) rootView.findViewById(R.id.ageTxt);
        specialTxt = (TextView) rootView.findViewById(R.id.specialTxt);
        homeTxt = (TextView) rootView.findViewById(R.id.homeTxt);
        workTxt = (TextView) rootView.findViewById(R.id.workTxt);
        passport = (ImageView) rootView.findViewById(R.id.passport);
        teleportData();
        return rootView;
    }

    private void teleportData(){
        String permit = "999";

        String user = MainActivity.userSession.getString("user");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(profileAPI.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        profileAPI api = retrofit.create(profileAPI.class);
        Call<String> call = api.getStrings(permit,user);
        call.enqueue(new Callback<String>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        JSONObject data = null;
                        try {
                            data = new JSONObject(response.body());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            String message = data.getString("message");
                            if(message.equals("Success!")) {
                                JSONObject profile = new JSONObject(data.getString("data"));
                                emailTxt.setText(profile.getString("email"));
                                specialTxt.setText(profile.getString("certification"));
                                nameTxt.setText(profile.getString("name"));
                                biographyTxt.setText(profile.getString("biography"));
                                telephoneTxt.setText(profile.getString("telephone"));
                                ageTxt.setText(profile.getString("age"));
                                homeTxt.setText(profile.getString("address"));
                                workTxt.setText(profile.getString("w_address"));

                                LoadImage loadImage = new LoadImage(passport);
                                loadImage.execute("http://172.16.3.90/mentalImgs/" + profile.getString("image"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    } else {
                        emailTxt.setText("No response from the server");
                    }
                }else{
                    emailTxt.setText("Response not successful "+response.toString());
                }
            }
            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //progressBar.setVisibility(View.GONE);
                emailTxt.setText("Error occurred during upload");
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

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(Uri uri);
    }
}