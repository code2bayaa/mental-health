package com.example.mental;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class patients extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_patients);            // ... rest of body of onCreateView() ...
        } catch (Exception e) {
            Log.e("route", "onCreateView", e);
            throw e;
        }

        Toolbar toolbar = findViewById(R.id.toolbar_patient);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout_patient);
        NavigationView navigationView = findViewById(R.id.nav_view_patient);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_patient,
                    new profilePatient()).commit();
            navigationView.setCheckedItem(R.id.profile_patient);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.patient, menu);
        return true;
    }

    @SuppressLint("WrongConstant")
    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        //getChildFragmentManager
        switch (item.getItemId()) {
            case R.id.profile_patient:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_patient,
                        new profilePatient()).commit();
                break;
            case R.id.doctors_list:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_patient,
                        new doctorList()).commit();
                break;
            case R.id.medical_patient:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_patient,
                        new patientCases()).commit();
                break;
            case R.id.sign_out_doctor:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


}