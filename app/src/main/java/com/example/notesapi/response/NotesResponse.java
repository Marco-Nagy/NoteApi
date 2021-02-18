package com.example.notesapi.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NotesResponse {
    @SerializedName("state")
    private boolean state;
    @SerializedName("notes")
    private List<Note> notes;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public List<Note> getNotes() {
        return notes;
    }

    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }
}
