package com.example.dispositivosinteligentes.ApiService;

import com.example.dispositivosinteligentes.Modelo.Aula;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import java.util.List;

public interface ApiAula {
    @GET("/api/aulas") // Reemplazar url
    List<Aula> get();

    @GET("/api/aulas") // Reemplazar url
    Call<List<Aula>> getCall();

    @POST("/api/aulas")
    Call<ResponseBody> post(@Body RequestBody requestBody);

    @DELETE("/api/aulas/{id}")
    Call<Void> delete(@Path("id") int id);
}
