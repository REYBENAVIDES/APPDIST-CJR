package com.example.dispositivosinteligentes.ApiService;

import com.example.dispositivosinteligentes.Modelo.HistorialDispositivo;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiDispositivoHistorial {
    @GET("/api/historial") // Reemplazar url
    List<HistorialDispositivo> get();

    @POST("/api/historial")
    Call<ResponseBody> post(@Body RequestBody requestBody);

    @DELETE("/api/historial/{id}/")
    Call<Void> delete(@Path("id") int id);

    @PUT("/api/historial/{id}/")
    Call<ResponseBody> put(@Path("id") int id, @Body RequestBody requestBody);
}
