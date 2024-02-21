package com.example.dispositivosinteligentes.ApiService;
import com.example.dispositivosinteligentes.Modelo.Facultad;
import java.util.List;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiFacultad {
    @GET("facultades/") // Reemplazar url
    List<Facultad> get();

    @GET("facultades/") // Reemplazar url
    Call<List<Facultad>> getCall();

    @POST("facultades/")
    Call<ResponseBody> post(@Body RequestBody requestBody);

    @DELETE("facultades/{id}/")
    Call<Void> delete(@Path("id") int id);
}
