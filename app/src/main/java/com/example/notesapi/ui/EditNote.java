package com.example.notesapi.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesapi.R;
import com.example.notesapi.network.NotesApis;
import com.example.notesapi.request.AddNoteRequest;
import com.example.notesapi.response.Note;
import com.example.notesapi.response.NotesResponse;
import com.example.notesapi.response.StateResponse;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class EditNote extends AppCompatActivity {
    EditText editTextTitle, editTextBody;
    MaterialButton updateButton;
    NotesApis notesApis;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        editTextTitle = findViewById(R.id.edit_note_title);
        editTextBody = findViewById(R.id.edit_note_body);
        updateButton = findViewById(R.id.materialButton);
        Note note = (Note) getIntent().getSerializableExtra("note");
        editTextTitle.setText(note.getTitle());
        editTextBody.setText(note.getBody());
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNotes(note);
                finish();
            }

        });
    }



    private void editNotes(Note note) {

        SharedPreferences preferences = getSharedPreferences("userData", MODE_PRIVATE);
        token = preferences.getString("token", "");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://notes.amirmohammed.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        notesApis = retrofit.create(NotesApis.class);
        AddNoteRequest addNoteRequest = new AddNoteRequest();
        String title = editTextTitle.getText().toString();
        String body = editTextBody.getText().toString();
        addNoteRequest.setTitle(title);
        addNoteRequest.setBody(body);
        notesApis.editNote(note.getId(), addNoteRequest, token).enqueue(new Callback<StateResponse>() {
            @Override
            public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {
                if (response != null && response.body() != null && response.body().isState()) {

                    Toast.makeText(EditNote.this, "Note Updated", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<StateResponse> call, Throwable t) {

            }
        });

    }


}


