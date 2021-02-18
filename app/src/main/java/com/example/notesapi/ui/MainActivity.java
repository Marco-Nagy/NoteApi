package com.example.notesapi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.notesapi.R;
import com.example.notesapi.network.NotesApis;
import com.example.notesapi.request.AddNoteRequest;
import com.example.notesapi.request.LoginRequest;
import com.example.notesapi.request.RegisterRequest;
import com.example.notesapi.response.LoginResponse;
import com.example.notesapi.response.NotesResponse;
import com.example.notesapi.response.RegisterResponse;
import com.example.notesapi.response.StateResponse;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    NotesApis notesApis;
    EditText editTextEmail, editTextPassword;
    private static final String TAG = "MainActivity";
    static String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextEmail=findViewById(R.id.login_et_email);
        editTextPassword =findViewById(R.id.login_et_password);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://notes.amirmohammed.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        notesApis =retrofit.create(NotesApis.class);
        login();
    }

    private void login(){
       String email= editTextEmail.getText().toString();
       String password= editTextPassword.getText().toString();

        LoginRequest loginRequest =new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setPassword(password);
        notesApis.login(loginRequest).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.body().getState()) {
                    Log.i(TAG, "onResponse:" +response.body().toString());
                    token = "Bearer "+ response.body().getAccessToken();
                    SharedPreferences preferences = getSharedPreferences("userData", MODE_PRIVATE);
                    preferences.edit().putString("token",token).apply();

                    startActivity(new Intent(MainActivity.this,NotesMainActivity.class));
                }else {
                    Log.i(TAG, "onResponse: "+response.body().toString());
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    public void login(View view) {
        login();

    }
    
    public void openRegister(View view) {
        startActivity(new Intent(MainActivity.this,RegisterMainActivity.class));
    }
}