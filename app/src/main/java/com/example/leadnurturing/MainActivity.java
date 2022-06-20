package com.example.leadnurturing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.leadnurturing.ui.applicant.ApplicantFragment;
import com.example.leadnurturing.ui.dashboard.DashBoardFragment;
import com.example.leadnurturing.ui.profile.ProfileFragment;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.FragmentTransaction;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

       private DrawerLayout drawerLayout;
       private MaterialToolbar toolbar;
       private NavigationView navigationView;
      SharedPreferences mSharedPref;
       @Override
       protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
           setContentView(R.layout.activity_main);

           init();
           buttonClick();
       }

       private void buttonClick() {
           SharedPreferences.Editor editor=mSharedPref.edit();
           toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
           navigationView.setNavigationItemSelectedListener(item -> {
               drawerLayout.closeDrawer(GravityCompat.START);
               FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
               switch (item.getItemId()) {
                   case R.id.nav_applicant:
                       transaction.replace(R.id.nav_host_fragment_content_nav, new ApplicantFragment());
                       toolbar.setTitle("Applicant");
                       break;
                   case R.id.nav_profile:
                       transaction.replace(R.id.nav_host_fragment_content_nav, new ProfileFragment());
                       toolbar.setTitle("Profile");
                       break;
                   case R.id.nav_dashboard:
                       transaction.replace(R.id.nav_host_fragment_content_nav, new DashBoardFragment());
                       toolbar.setTitle("DashBoard");
                       break;
                   case R.id.logout:
                       editor.putInt("login",0);
                       editor.apply();
                       Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                       startActivity(intent);
                       finish();
               }
               transaction.commit();
               return true;
           });
       }
       private void init()
       {
           drawerLayout =(DrawerLayout) findViewById(R.id.drawer_layout);
           navigationView = findViewById(R.id.nav_view);
           toolbar =(MaterialToolbar)findViewById(R.id.toolbar);
           mSharedPref=getSharedPreferences("MyPref",MODE_PRIVATE);

           FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
           transaction.replace(R.id.nav_host_fragment_content_nav, new ApplicantFragment());
           toolbar.setTitle("Applicant");
           transaction.commit();
           navigationView.setCheckedItem(R.id.nav_applicant);
       }
       @Override
       public void onBackPressed() {
           if(drawerLayout.isDrawerOpen(GravityCompat.START)){
               drawerLayout.closeDrawer(GravityCompat.START);
           }else{
               super.onBackPressed();
           }

       }
   }
