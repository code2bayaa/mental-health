package com.example.mental;

import android.content.Intent;
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

public class sign_up_doctor extends AppCompatActivity {

    private TextInputEditText w_address, biography, certification;
    Button m_sign_med_button, m_back;
    private static final String ROOT_URL = "http://192.168.0.22/mental/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_doctor);

        w_address = findViewById(R.id.med_address);
        biography = findViewById(R.id.med_biography);
        certification = findViewById(R.id.med_certification);
        m_sign_med_button = findViewById(R.id.sign_med_button);
        m_back = findViewById(R.id.sign_back_button);

        m_back.setOnClickListener(v -> startActivity(new Intent(sign_up_doctor.this,signup.class)));
        m_sign_med_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!w_address.getText().toString().isEmpty()) && (!biography.getText().toString().isEmpty()) && (!certification.getText().toString().isEmpty())) {
                    signDoctor(w_address.getText().toString(), biography.getText().toString(), certification.getText().toString());
                } else {
                    if(w_address.getText().toString().isEmpty())
                        Toast.makeText(sign_up_doctor.this, "Please input your work address", Toast.LENGTH_SHORT).show();

                    if(biography.getText().toString().isEmpty())
                        Toast.makeText(sign_up_doctor.this, "Please add your biography", Toast.LENGTH_SHORT).show();

                    if(certification.getText().toString().isEmpty())
                        Toast.makeText(sign_up_doctor.this, "Please add your certification", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void signDoctor( final String w_address, final String biography, final String certification) {

        String mEmail = signup.signBundle.getString("email");
        String mPassword = signup.signBundle.getString("password");
        String Name = signup.signBundle.getString("name");
        String Special = signup.signBundle.getString("special");
        String mAddress = signup.signBundle.getString("address");
        String mAge = signup.signBundle.getString("age");
        String mTelephone = signup.signBundle.getString("telephone");
        String nameR = signup.signBundle.getString("nameR");
        String imgR = signup.signBundle.getString("image");

        Toast.makeText(sign_up_doctor.this, nameR, Toast.LENGTH_SHORT).show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        signDoctorField sign_doctor = retrofit.create(signDoctorField.class);
        Call<String> call = sign_doctor.getData(nameR,imgR,mEmail,Name,mPassword,mAddress,mAge,mTelephone,Special,biography,w_address,certification);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Toast.makeText(sign_up_doctor.this, response.body(), Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(sign_up_doctor.this, MainActivity.class));
                    } else {
                        Toast.makeText(sign_up_doctor.this, "No response from the server", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(sign_up_doctor.this, "Response not successful "+response.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //progressBar.setVisibility(View.GONE);
                Toast.makeText(sign_up_doctor.this, "Error occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}