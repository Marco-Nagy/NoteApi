package com.example.notesapi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesapi.R;
import com.example.notesapi.network.NotesApis;
import com.example.notesapi.request.RegisterRequest;
import com.example.notesapi.response.NotesResponse;
import com.example.notesapi.response.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterMainActivity extends AppCompatActivity {
    NotesApis notesApis;
    private static final String TAG = "RegisterMainActivity";
    EditText editTextName,editTextEmail,editTextPassword,editTextConfirmPassword;
    static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_main);
        editTextName = findViewById(R.id.register_et_name);
        editTextEmail = findViewById(R.id.register_et_email);
        editTextPassword = findViewById(R.id.register_et_password);
        editTextConfirmPassword = findViewById(R.id.register_et_confirm_password);


    }

    public void register(View view) {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();
        RegisterRequest registerRequest = new RegisterRequest();
        registerRequest.setName(name);
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        registerRequest.setConfirmPassword(confirmPassword);



        notesApis.register(registerRequest).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.body().isState()){
                    Log.i(TAG, "onResponse: account Created");
                    Toast.makeText(RegisterMainActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterMainActivity.this,NotesMainActivity.class));
                }else {
                    Log.i(TAG, "onResponse: "+response.body().getErrors());
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getLocalizedMessage());

            }
        });
    }
    private void getNotes() {

        notesApis.getNotes(token).enqueue(new Callback<NotesResponse>() {
            @Override
            public void onResponse(Call<NotesResponse> call, Response<NotesResponse> response) {
                if (response.body().isState()){
                    Log.i(TAG, "onResponse: "+response.body().getNotes().size());
                    Log.i(TAG, "onResponse: "+response.body().getNotes().get(0).getTitle());
                }
            }

            @Override
            public void onFailure(Call<NotesResponse> call, Throwable t) {

            }
        });
    }
}