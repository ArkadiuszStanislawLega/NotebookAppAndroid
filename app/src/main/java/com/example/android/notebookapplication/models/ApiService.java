package com.example.android.notebookapplication.models;

import java.util.List;


import io.reactivex.Observable;

import retrofit2.Response;
import retrofit2.http.GET;

public interface ApiService {
    @GET("/todos")
    Observable<Response<List<Job>>> getJobs();
}
