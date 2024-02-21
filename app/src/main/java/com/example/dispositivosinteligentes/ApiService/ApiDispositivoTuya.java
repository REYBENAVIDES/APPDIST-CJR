package com.example.dispositivosinteligentes.ApiService;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.POST;

public interface ApiDispositivoTuya {

    @POST("breake_encendido/")
    Response<ResponseBody> offBreake();

    @POST("breake_apagado/")
    Response<ResponseBody> onBreake();

    @POST("switch_1_encendido/")
    Response<ResponseBody> offSwitch1();

    @POST("switch_1_apagado/")
    Response<ResponseBody> onSwitch1();

    @POST("switch_2_encendido/")
    Response<ResponseBody> offSwitch2();

    @POST("switch_2_apagado/")
    Response<ResponseBody> onSwitch2();

    @POST("tomacorriente_1_encendido/")
    Response<ResponseBody> offToma1();

    @POST("tomacorriente_2_encendido/")
    Response<ResponseBody> offToma2();

    @POST("tomacorriente_1_apagado/")
    Response<ResponseBody> onToma1();

    @POST("tomacorriente_2_apagado/")
    Response<ResponseBody> onToma2();

    @POST("smart_touch_apagado/")
    Response<ResponseBody> onWifi();

    @POST("smart_touch_encendido/")
    Response<ResponseBody> offWifi();
}
