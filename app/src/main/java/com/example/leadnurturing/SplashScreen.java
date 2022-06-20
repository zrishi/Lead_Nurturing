package com.example.leadnurturing;

import static com.example.leadnurturing.R.layout.activity_splash_screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import com.example.leadnurturing.Data.Contract;

public class SplashScreen extends AppCompatActivity {

    SharedPreferences mSharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_splash_screen);
      mSharedPref=getSharedPreferences("MyPref",MODE_PRIVATE);
      new Handler().postDelayed(() -> {
          int login=mSharedPref.getInt("login",0);
          Intent intent;
          if (login == 1) {
              intent = new Intent(SplashScreen.this, MainActivity.class);
          } else {
              intent = new Intent(SplashScreen.this, LoginActivity.class);
          }
          startActivity(intent);
          finish();
      },1500);
    }
}