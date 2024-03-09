package com.uteq.dispositivos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.thingclips.smart.android.user.api.ILoginCallback;
import com.thingclips.smart.android.user.bean.User;
import com.thingclips.smart.home.sdk.ThingHomeSdk;
import com.thingclips.smart.home.sdk.bean.WiFiInfoBean;
import com.thingclips.smart.home.sdk.builder.ThingApActivatorBuilder;
import com.thingclips.smart.home.sdk.callback.IThingResultCallback;
import com.thingclips.smart.sdk.api.IThingDataCallback;
import com.thingclips.smart.sdk.api.IThingOptimizedActivator;
import com.thingclips.smart.sdk.bean.ApQueryBuilder;
import com.uteq.dispositivos.R;

import java.util.List;

public class Activity_Bienvenida extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        ThingHomeSdk.init(getApplication(), "fqkatyqm49kdjn5u8t9w", "sfa3qsqvh9fs987ahc345qj4ej9u8yfm");

        //obtener codigo de verificacion
        /*ThingHomeSdk.getUserInstance().sendVerifyCodeWithUserName("eduinrey12@gmail.com", "", "593", 1, new IResultCallback() {
            @Override
            public void onError(String code, String error) {
                Toast.makeText(getApplicationContext(), "code: " + code + "error:" + error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess() {
                Toast.makeText(getApplicationContext(), "Verification code returned successfully.", Toast.LENGTH_SHORT).show();
            }
        });*/

        //crear cuenta
        /*ThingHomeSdk.getUserInstance().registerAccountWithEmail("593", "eduinrey12@gmail.com","1207337328Rey","41248", new IRegisterCallback() {
            @Override
            public void onSuccess(User user) {
                Toast.makeText(getApplicationContext(), "Registered successfully.", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String code, String error) {
                Toast.makeText(getApplicationContext(), "code: " + code + "error:" + error, Toast.LENGTH_SHORT).show();
            }
        });*/

        //Iniciar sesion
        /*ThingHomeSdk.getUserInstance().loginWithEmail("593", "eduinrey12@gmail.com", "1207337328Rey", new ILoginCallback() {
            @Override
            public void onSuccess(User user) {
                Toast.makeText(getApplicationContext(), "Logged in with Username: " + user.getUid(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String code, String error) {
                Toast.makeText(getApplicationContext(), "code: " + code + "error:" + error, Toast.LENGTH_SHORT).show();
            }
        });*/

        //crear hogar
        /*ThingHomeSdk.getHomeManagerInstance().createHome("Facultad 1", 0, 0, "", Arrays.asList("Aulas"), new IThingHomeResultCallback() {
            @Override
            public void onSuccess(HomeBean bean) {
                Toast.makeText(getApplicationContext(), "El ID del hogar creado es: " + bean.getHomeId(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(String errorCode, String errorMsg) {
                // do something
            }
        });*/

        //Listado de hogares
        /*ThingHomeSdk.getHomeManagerInstance().queryHomeList(new IThingGetHomeListCallback() {
            @Override
            public void onSuccess(List<HomeBean> homeBeans) {
                for (HomeBean home : homeBeans) {
                    System.out.println("Nombre del hogar: " + home.getName());
                    System.out.println("ID del hogar: " + home.getHomeId());
                }
            }

            @Override
            public void onError(String errorCode, String error) {
                // Manejar el error si la consulta de la lista de hogares falla
            }
        });*/

        //Obtener token del hogar


        /*ThingHomeSdk.getActivatorInstance().getActivatorToken(190655349,
                new IThingActivatorGetToken() {

                    @Override
                    public void onSuccess(String token) {
                        Toast.makeText(getApplicationContext(),"Token: " + token, Toast.LENGTH_LONG).show();
                        ThingHomeSdk.getActivatorInstance().getDeviceSecurityConfigs(new IThingDataCallback<String>() {
                            @Override
                            public void onSuccess(String securityConfig) {
                                ThingApActivatorBuilder builder = new ThingApActivatorBuilder().setContext(getApplicationContext());
                                IThingOptimizedActivator mThingActivator = ThingHomeSdk.getActivatorInstance().newOptimizedActivator(builder);
                                Toast.makeText(getApplicationContext(),"securityConfig: " + securityConfig, Toast.LENGTH_LONG).show();
                                Log.i("securityConfig: ",securityConfig);

                                ApActivatorBuilder builderAp = new ApActivatorBuilder.Builder()
                                        .setSsid("NETLIFE-BENAVIDES")
                                        .setPwd("#HAEDLUJALU2020")
                                        .setTimeout(300)
                                        .setToken(token)
                                        .setSecurityConfig(securityConfig)
                                        .setListener(new IThingSmartActivatorListener() {
                                            @Override
                                            public void onError(String errorCode, String errorMsg) {
                                                Toast.makeText(getApplicationContext(),"errorCode: " + errorCode + "errorMsg: " + errorMsg, Toast.LENGTH_LONG).show();
                                            }

                                            @Override
                                            public void onActiveSuccess(DeviceBean devResp) {
                                                Toast.makeText(getApplicationContext(),"DevId: " + devResp.devId, Toast.LENGTH_LONG).show();
                                            }

                                            @Override
                                            public void onStep(String step, Object data) {
                                                Toast.makeText(getApplicationContext(),"step: " + step + "data: " + data, Toast.LENGTH_LONG).show();
                                            }
                                        }).build();
                                mThingActivator.startActivator(builderAp);

                            }

                            @Override
                            public void onError(String errorCode, String errorMessage) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(String s, String s1) {

                    }
                });*/

        ThingHomeSdk.getActivatorInstance().getDeviceSecurityConfigs(new IThingDataCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(getApplicationContext(),"securityConfig: " + result, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(String errorCode, String errorMessage) {

            }
        });

        ThingApActivatorBuilder builder = new ThingApActivatorBuilder().setContext(this);
        IThingOptimizedActivator mThingActivator = ThingHomeSdk.getActivatorInstance().newOptimizedActivator(builder);

        ApQueryBuilder queryBuilder = new ApQueryBuilder.Builder().setContext(getApplicationContext()).setTimeout(30000).build();
        mThingActivator.queryDeviceConfigState(queryBuilder, new IThingResultCallback<List<WiFiInfoBean>>() {
            @Override
            public void onSuccess(List<WiFiInfoBean> result) {
                for (WiFiInfoBean home : result) {
                    Toast.makeText(getApplicationContext(),"SSID: " + home.getSsid(), Toast.LENGTH_LONG).show();
                    Log.i("SSID",home.getSsid());
                }
            }

            @Override
            public void onError(String errorCode, String errorMessage) {
                Toast.makeText(getApplicationContext(),"errorCode: " + errorCode + "errorMsg: " + errorMessage, Toast.LENGTH_LONG).show();
                ThingHomeSdk.closeService();
            }
        });

        Button btnIniciar = findViewById(R.id.btnIniciarSesion);
        Button btnRegistrar = findViewById(R.id.btnRegistrarse);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Bienvenida.this, Activity_IniciarSesion.class);
                startActivity(intent);
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Bienvenida.this, Activity_Registrar.class);
                startActivity(intent);
            }
        });
    }
}