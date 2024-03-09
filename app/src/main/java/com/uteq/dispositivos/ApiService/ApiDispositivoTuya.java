package com.uteq.dispositivos.ApiService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;

public interface ApiDispositivoTuya {

    @POST("breake_encendido/")
    Call<ResponseBody> offBreake();

    @POST("breake_apagado/")
    Call<ResponseBody> onBreake();

    @POST("switch_1_encendido/")
    Call<ResponseBody> offSwitch1();

    @POST("switch_1_apagado/")
    Call<ResponseBody> onSwitch1();

    @POST("switch_2_encendido/")
    Call<ResponseBody> offSwitch2();

    @POST("switch_2_apagado/")
    Call<ResponseBody> onSwitch2();

    @POST("tomacorriente_1_encendido/")
    Call<ResponseBody> offToma1();

    @POST("tomacorriente_2_encendido/")
    Call<ResponseBody> offToma2();

    @POST("tomacorriente_1_apagado/")
    Call<ResponseBody> onToma1();

    @POST("tomacorriente_2_apagado/")
    Call<ResponseBody> onToma2();

    @POST("smart_touch_apagado/")
    Call<ResponseBody> onWifi();

    @POST("smart_touch_encendido/")
    Call<ResponseBody> offWifi();
}
