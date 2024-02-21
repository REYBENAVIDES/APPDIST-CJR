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
    @GET("aulas/") // Reemplazar url
    List<Aula> get();

    @GET("aulas/") // Reemplazar url
    Call<List<Aula>> getCall();

    @POST("aulas/")
    Call<ResponseBody> post(@Body RequestBody requestBody);

    @DELETE("aulas/{id}/")
    Call<Void> delete(@Path("id") int id);
}
