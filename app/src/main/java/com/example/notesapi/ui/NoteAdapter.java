package com.example.notesapi.ui;

import android.content.Context;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapi.R;
import com.example.notesapi.response.Note;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteHolder> {
    Context context;
    List<Note> notes;
    NoteInterface noteInterface;

    public NoteAdapter(Context context, List<Note> notes, NoteInterface noteInterface) {
        this.context = context;
        this.notes = notes;
        this.noteInterface = noteInterface;
    }

    @NonNull
    @Override
    public NoteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NoteHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NoteHolder holder, int position) {
        Note note = notes.get(position);
        holder.textViewTitle.setText(note.getTitle());
        holder.textViewBody.setText(note.getBody());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteInterface.onNoteClick(note);
                noteInterface.onUpdateNote(note);
            }
        });
        holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add("Delete Note").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        noteInterface.onNoteDelete(note);
                        return false;
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NoteHolder extends RecyclerView.ViewHolder  {
        TextView textViewTitle, textViewBody;

        public NoteHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.item_note_tv_title);
            textViewBody = itemView.findViewById(R.id.item_note_tv_body);
        }

    }
    public interface NoteInterface{
    void onNoteClick(Note note);
    void onNoteDelete(Note note);
    void onUpdateNote(Note note);
    }
}
