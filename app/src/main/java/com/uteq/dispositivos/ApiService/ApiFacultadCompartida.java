package com.uteq.dispositivos.ApiService;

import com.uteq.dispositivos.Modelo.FacultadCompartida;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiFacultadCompartida {
    @GET("/api/fcompartidos") // Reemplazar url
    Call<List<FacultadCompartida>> getCall();

    @POST("/api/fcompartidos")
    Call<ResponseBody> post(@Body RequestBody requestBody);

    @DELETE("/api/fcompartidos/{id}")
    Call<Void> delete(@Path("id") int id);

}
