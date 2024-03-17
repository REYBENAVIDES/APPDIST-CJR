package com.uteq.dispositivos.ApiService;

import com.uteq.dispositivos.Modelo.Facultad;

import java.util.List;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiFacultad {
    @GET("/api/facultades") // Reemplazar url
    List<Facultad> get();

    @GET("/api/facultades") // Reemplazar url
    Call<List<Facultad>> getCall();

    @POST("/api/facultades")
    Call<ResponseBody> post(@Body RequestBody requestBody);

    @DELETE("/api/facultades/delete/{id}")
    Call<Void> delete(@Path("id") int id);
}
