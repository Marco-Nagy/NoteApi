package com.example.notesapi.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.notesapi.R;
import com.example.notesapi.network.NotesApis;
import com.example.notesapi.request.AddNoteRequest;
import com.example.notesapi.response.Note;
import com.example.notesapi.response.NotesResponse;
import com.example.notesapi.response.StateResponse;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotesMainActivity extends AppCompatActivity {
    private static final String TAG = "NotesMainActivity";
    NotesApis notesApis;
    RecyclerView recyclerView;
    String token;
    NoteAdapter noteAdapter;
    List<Note> noteList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_main);
        recyclerView = findViewById(R.id.recycler_view);
        getNotes();
    }

    private void getNotes() {
        SharedPreferences preferences = getSharedPreferences("userData", MODE_PRIVATE);
        token = preferences.getString("token", "");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://notes.amirmohammed.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        notesApis = retrofit.create(NotesApis.class);
        notesApis.getNotes(token).enqueue(new Callback<NotesResponse>() {
            @Override
            public void onResponse(Call<NotesResponse> call, Response<NotesResponse> response) {
                if (response != null && response.body() != null && response.body().isState()) {
                    noteList = response.body().getNotes();
                    noteAdapter = new NoteAdapter(getBaseContext(), noteList, noteInterface);
                    recyclerView.setAdapter(noteAdapter);
                    noteAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<NotesResponse> call, Throwable t) {

            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        getNotes();
    }

    public void navigate(View view) {
        startActivity(new Intent(NotesMainActivity.this, AddNoteActivity.class));
    }

    NoteAdapter.NoteInterface noteInterface = new NoteAdapter.NoteInterface() {
        @Override
        public void onNoteClick(Note note) {
            Intent intent = new Intent(NotesMainActivity.this, EditNote.class);
            intent.putExtra("note", note);
            startActivity(intent);
        }

        @Override
        public void onNoteDelete(Note note) {
            notesApis.deleteNote(note.getId(), token).enqueue(new Callback<StateResponse>() {
                @Override
                public void onResponse(Call<StateResponse> call, Response<StateResponse> response) {

                    Toast.makeText(NotesMainActivity.this, "note deleted", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<StateResponse> call, Throwable t) {

                }
            });
            getNotes();
        }

        @Override
        public void onUpdateNote(Note note) {

        }
    };
}