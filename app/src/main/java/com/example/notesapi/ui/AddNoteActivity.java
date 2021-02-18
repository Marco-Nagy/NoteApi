package com.example.notesapi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesapi.R;
import com.example.notesapi.network.NotesApis;
import com.example.notesapi.request.AddNoteRequest;
import com.example.notesapi.response.StateResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddNoteActivity extends AppCompatActivity {
    NotesApis notesApis;
    String token;
    private static final String TAG = "AddNoteActivity";
    EditText editTextTitle,editTextBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        editTextTitle=findViewById(R.id.add_note_title);
        editTextBody=findViewById(R.id.add_note_body);



    }

    public void addNote(View view) {
        SharedPreferences preferences = getSharedPreferences("userData", MODE_PRIVATE);
        token = preferences.getString("token", "");

        String titleNote = editTextTitle.getText().toString();
        String bodyNote = editTextBody.getText().toString();
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        addNoteRequest.setTitle(titleNote);
        addNoteRequest.setBody(bodyNote);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://notes.amirmohammed.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        notesApis =retrofit.create(NotesApis.class);

        notesApis.addNote(addNoteRequest, token).enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
                if (response!=null && response.body()!=null&&response.body().isState()) {
                    Log.i(TAG, "onResponse: Note Add");

                    Toast.makeText(AddNoteActivity.this, "Note Added", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {

            }

        });
        finish();
    }

}