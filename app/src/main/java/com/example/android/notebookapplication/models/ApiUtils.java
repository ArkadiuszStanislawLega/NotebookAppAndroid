package com.example.android.notebookapplication.models;

import java.util.List;

import retrofit2.Response;

public class ApiUtils {
    public static ApiService getAPIService() {
        return ApiClient.getClient().create(ApiService.class);
    }
    public static int getResponseStatusCode(Response<List<Job>> response) {
        if (response == null) {
            return 404;
        }
        return response.code();
    }
}
