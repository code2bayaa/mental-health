package com.example.mental;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class signup extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String ROOT_URL = "http://192.168.0.22/mental/";
    private TextInputEditText mEmail;
    private TextInputEditText mPassword;
    private String mCheck, clickedImg;
    private TextInputEditText mFirstName;
    private TextInputEditText mLastName;
    private TextInputEditText mAddress;
    private TextInputEditText mAge;
    private TextInputEditText mTelephone;
    private Button m_signup_button;
    ImageView imageView;
    private static final int STORAGE_PERMISSION_CODE = 4655;
    private final int PICK_IMAGE_REQUEST = 1;
    private Uri filepath;
    private Bitmap bitmap;
    TextView tv, m_logIn;
    public static Bundle signBundle = new Bundle();

    String[] Special = {"Doctor", "Patient"};

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mEmail = findViewById(R.id.email_sign);
        mPassword = findViewById(R.id.password_sign);
        mFirstName = findViewById(R.id.first_name_sign);
        mLastName = findViewById(R.id.last_name_sign);
        mAddress = findViewById(R.id.address_sign);
        mAge = findViewById(R.id.age_sign);
        mTelephone = findViewById(R.id.telephone_sign);
        m_signup_button = findViewById(R.id.sign_button);
        m_logIn = findViewById(R.id.sign_log);

        requestStoragePermission();

        imageView = (ImageView) findViewById(R.id.imageView);
        tv=(TextView)findViewById(R.id.tv4);

        Spinner spinner = findViewById(R.id.specialization);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Special);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        m_logIn.setOnClickListener(v -> startActivity(new Intent(signup.this,MainActivity.class)));
        m_signup_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((clickedImg != null) && (!mFirstName.getText().toString().isEmpty()) && (!mLastName.getText().toString().isEmpty()) && (!mEmail.getText().toString().isEmpty()) && (!mAddress.getText().toString().isEmpty()) && (!mTelephone.getText().toString().isEmpty()) && (!mAge.getText().toString().isEmpty()) && (!mPassword.getText().toString().isEmpty()) && (!mCheck.isEmpty())) {
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
                    String imgR = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
                    String nameR = String.valueOf(Calendar.getInstance().getTimeInMillis());

                    if (mCheck.equals("Doctor")) {
                        signup.signBundle.putString("email", mEmail.getText().toString());
                        signup.signBundle.putString("password", mPassword.getText().toString());
                        signup.signBundle.putString("name", mFirstName.getText().toString() + "," + mLastName.getText().toString());
                        signup.signBundle.putString("special", mCheck);
                        signup.signBundle.putString("address", mAddress.getText().toString());
                        signup.signBundle.putString("age", mAge.getText().toString());
                        signup.signBundle.putString("telephone", mTelephone.getText().toString());
                        signup.signBundle.putString("image", imgR);
                        signup.signBundle.putString("nameR", nameR);

                        Intent intent = new Intent(signup.this, sign_up_doctor.class);
                        startActivity(intent);
                    }
                    if (mCheck.equals("Patient"))
                        signUp(mFirstName.getText().toString()+","+mLastName.getText().toString(), mEmail.getText().toString(), mPassword.getText().toString(), mAddress.getText().toString(), mAge.getText().toString(), mTelephone.getText().toString(), mCheck);
                } else {
                    if (clickedImg == null)
                        Toast.makeText(signup.this, "Please upload your passport", Toast.LENGTH_SHORT).show();

                    if (mEmail.getText().toString().isEmpty())
                        Toast.makeText(signup.this, "Please input your email", Toast.LENGTH_SHORT).show();

                    if (mPassword.getText().toString().isEmpty())
                        Toast.makeText(signup.this, "Please input your password", Toast.LENGTH_SHORT).show();

                    if (mFirstName.getText().toString().isEmpty())
                        Toast.makeText(signup.this, "Please input your first name", Toast.LENGTH_SHORT).show();

                    if (mLastName.getText().toString().isEmpty())
                        Toast.makeText(signup.this, "Please input your last name", Toast.LENGTH_SHORT).show();

                    if (mAddress.getText().toString().isEmpty())
                        Toast.makeText(signup.this, "Please input your address", Toast.LENGTH_SHORT).show();

                    if (mAge.getText().toString().isEmpty())
                        Toast.makeText(signup.this, "Please input your age", Toast.LENGTH_SHORT).show();

                    if (mTelephone.getText().toString().isEmpty())
                        Toast.makeText(signup.this, "Please input your telephone", Toast.LENGTH_SHORT).show();

                    if (mCheck.isEmpty())
                        Toast.makeText(signup.this, "Please choose your user type", Toast.LENGTH_SHORT).show();

                }

            }
        });

    }

    private void requestStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        //if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
       // }
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }
    /*
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }
    */
    private void ShowFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && data != null && data.getData() != null) {

            filepath = data.getData();
            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                imageView.setImageBitmap(bitmap);
                //tv.setText(filepath.toString());
                // Toast.makeText(getApplicationContext(),getPath(filepath),Toast.LENGTH_LONG).show();
            } catch (Exception ex) {
                Toast.makeText(getApplicationContext(),"Error. Try again",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void selectImage(View view)
    {
        clickedImg = "clicked";
        ShowFileChooser();
    }

    private String getPath(Uri uri) {

        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, MediaStore.Images.Media._ID + "=?", new String[]{document_id}, null
        );
        cursor.moveToFirst();
        @SuppressLint("Range") String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }


    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(), "Selected User: " + Special[position], Toast.LENGTH_SHORT).show();
        mCheck = Special[position];
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO - Custom Code
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    protected void signUp(String name, final String email, final String password, final String address, final String age, final String telephone, final String special) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        String imgR = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        String nameR = String.valueOf(Calendar.getInstance().getTimeInMillis());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        MyImageInterface myImageInterface = retrofit.create(MyImageInterface.class);
        Call<String> call = myImageInterface.getImageData(nameR,imgR,email,name,password,address,age,telephone,special);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                //progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        Toast.makeText(signup.this, response.body(), Toast.LENGTH_SHORT).show();
                        tv.setText("Image Uploaded Successfully!!");
                        startActivity(new Intent(signup.this, MainActivity.class));
                    } else {
                        tv.setText("No response from the server");
                    }
                }else{
                    tv.setText("Response not successful "+response.toString());
                    Toast.makeText(getApplicationContext(), "Response not successful "+response.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                //progressBar.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), "Error occurred!", Toast.LENGTH_SHORT).show();
                tv.setText("Error occurred during upload");
            }
        });
    }

}