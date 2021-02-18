package com.example.notesapi.response;

import com.google.gson.annotations.SerializedName;

public class RegisterResponse {
@SerializedName("state")
private boolean state;
@SerializedName("errors")
String errors;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }
}
