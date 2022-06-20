package com.example.leadnurturing;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.leadnurturing.Data.Contract;
import com.example.leadnurturing.Data.DbHelper;


public class LoginActivity extends AppCompatActivity {
    SharedPreferences mSharedPref;
    String username;
    String password;
    private static final int Counselor_Loader = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginid);
        Button btn = findViewById(R.id.login_btn);
        EditText mUsername = findViewById(R.id.UserName);
        EditText mPassword = findViewById(R.id.Password);
        mSharedPref = getSharedPreferences("MyPref", MODE_PRIVATE);
        String Username="Rakesh Das";
        String Userpassword="1234";
        btn.setOnClickListener(view -> {
            username = mUsername.getText().toString().trim();
            password = mPassword.getText().toString().trim();
            if (username.equals("")) {
                Toast.makeText(LoginActivity.this, "Email cant be blank", Toast.LENGTH_LONG).show();
            } else if (password.equals("")) {
                Toast.makeText(LoginActivity.this, "Password cant be blank", Toast.LENGTH_LONG).show();
            } else
            {
                if(username.equals(Username) && password.equals(Userpassword))
                {
                    SharedPreferences.Editor editor= mSharedPref.edit();
                    editor.putInt("login",1);
                    editor.apply();
                    Toast.makeText(LoginActivity.this,"Login Successful",Toast.LENGTH_LONG).show();
                    Intent intent= new Intent(LoginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(LoginActivity.this,"Login Unsuccessful",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
