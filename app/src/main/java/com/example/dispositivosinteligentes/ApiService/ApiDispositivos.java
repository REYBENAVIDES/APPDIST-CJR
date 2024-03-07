package com.example.dispositivosinteligentes.ApiService;

import com.example.dispositivosinteligentes.Modelo.Dispositivo;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiDispositivos {
    @GET("/api/dispositivos") // Reemplazar url
    List<Dispositivo> get();

    @GET("/api/dispositivos") // Reemplazar url
    Call<List<Dispositivo>> getCall();

    @POST("/api/dispositivos")
    Call<ResponseBody> post(@Body RequestBody requestBody);

    @DELETE("/api/dispositivos/{id}/")
    Call<Void> delete(@Path("id") int id);

    @PUT("/api/dispositivos/{id}/estado")
    Call<Dispositivo> put(@Path("id") Long id, @Body boolean nuevoEstado);
}
