package com.uteq.dispositivos.ApiService;

import com.uteq.dispositivos.Modelo.Usuario;

import java.util.List;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiUsuario {
    @GET("/api/usuarios")
    Call<List<Usuario>> get();

    @POST("/api/usuarios")
    Call<ResponseBody> post(@Body RequestBody requestBody);
}
