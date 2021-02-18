package com.example.notesapi.network;

import com.example.notesapi.request.AddNoteRequest;
import com.example.notesapi.request.LoginRequest;
import com.example.notesapi.request.RegisterRequest;
import com.example.notesapi.response.LoginResponse;
import com.example.notesapi.response.NotesResponse;
import com.example.notesapi.response.RegisterResponse;
import com.example.notesapi.response.StateResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface NotesApis {
    @POST("public/api/register")
    Call<RegisterResponse> register(@Body RegisterRequest registerRequest);

    @POST("public/api/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("public/api/notes")
    Call<NotesResponse> getNotes(@Header("Authorization") String token);

    @POST("public/api/notes/add")
    Call<StateResponse> addNote(@Body AddNoteRequest addNoteRequest,
                                @Header("Authorization") String token);
@POST("public/api/notes/edit/{noteId}")
Call<StateResponse> editNote(@Path("noteId") int noteId,
                             @Body AddNoteRequest addNoteRequest,
                             @Header("Authorization") String token);
@GET("public/api/notes/delete/{noteId}")
Call<StateResponse> deleteNote(@Path("noteId") int noteId,
                               @Header("Authorization") String token);
}
