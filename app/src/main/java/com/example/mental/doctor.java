package com.example.mental;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.ui.AppBarConfiguration;

import com.google.android.material.navigation.NavigationView;

public class doctor extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,  profileDoctor.OnFragmentInteractionListener {

    private AppBarConfiguration mAppBarConfiguration;
    //private ActivityDoctorBinding binding;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            setContentView(R.layout.activity_doctor);            // ... rest of body of onCreateView() ...
        } catch (Exception e) {
            Log.e("route", "onCreateView", e);
            throw e;
        }
       /* binding = ActivityDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());*/

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
           <fragment
            android:id="@+id/nav_host_fragment_content_doctor"
            android:name="androidx.navigation.fragment.NavHostFragment"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toTopOf="parent"
            app:defaultNavHost="true"
            app:navGraph="@navigation/mobile_navigation2" />
         */
/*
        setSupportActionBar(binding.appBarDoctor.toolbar);
        binding.appBarDoctor.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        //DrawerLayout drawer = binding.drawerLayout;
        //NavigationView navigationView = binding.navView;

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new profileDoctor()).commit();
            navigationView.setCheckedItem(R.id.profile_doctor);
        }

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
/*        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.profile_doctor, R.id.patients_doctor, R.id.sign_out_doctor)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_doctor);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.doctor, menu);
        return true;
    }

/*    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_doctor);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
*/

    @SuppressLint("WrongConstant")
    @Override
    public boolean onNavigationItemSelected( MenuItem item) {
        //getChildFragmentManager
        switch (item.getItemId()) {
            case R.id.profile_doctor:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new profileDoctor()).commit();
                break;
            case R.id.patients_doctor:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new patientsDoctor()).commit();
                break;
            case R.id.cases_doctor:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

}