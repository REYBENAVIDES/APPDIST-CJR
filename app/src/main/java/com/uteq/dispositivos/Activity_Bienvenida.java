package com.uteq.dispositivos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.thingclips.sdk.home.bean.InviteMessageBean;
import com.thingclips.smart.android.user.api.ILoginCallback;
import com.thingclips.smart.android.user.bean.User;
import com.thingclips.smart.home.sdk.ThingHomeSdk;
import com.thingclips.smart.home.sdk.bean.HomeBean;
import com.thingclips.smart.home.sdk.bean.WiFiInfoBean;
import com.thingclips.smart.home.sdk.builder.ActivatorBuilder;
import com.thingclips.smart.home.sdk.builder.ThingApActivatorBuilder;
import com.thingclips.smart.home.sdk.callback.IThingGetHomeListCallback;
import com.thingclips.smart.home.sdk.callback.IThingHomeResultCallback;
import com.thingclips.smart.home.sdk.callback.IThingResultCallback;
import com.thingclips.smart.interior.device.bean.DevResp;
import com.thingclips.smart.sdk.api.IResultCallback;
import com.thingclips.smart.sdk.api.IThingActivator;
import com.thingclips.smart.sdk.api.IThingActivatorGetToken;
import com.thingclips.smart.sdk.api.IThingDataCallback;
import com.thingclips.smart.sdk.api.IThingOptimizedActivator;
import com.thingclips.smart.sdk.api.IThingSmartActivatorListener;
import com.thingclips.smart.sdk.api.IThingUser;
import com.thingclips.smart.sdk.bean.ApActivatorBuilder;
import com.thingclips.smart.sdk.bean.ApQueryBuilder;
import com.thingclips.smart.sdk.bean.DeviceBean;
import com.thingclips.smart.sdk.enums.ActivatorModelEnum;
import com.uteq.dispositivos.Modelo.Usuario;
import com.uteq.dispositivos.R;

import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Activity_Bienvenida extends AppCompatActivity {

    Context context = this;

   @Override
    public void onDestroy() {
        super.onDestroy();
        ThingHomeSdk.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_STATE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.OVERRIDE_WIFI_CONFIG)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_WIFI_MULTICAST_STATE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_WIFI_INTERFACES)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.MANAGE_WIFI_NETWORK_SELECTION)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.NEARBY_WIFI_DEVICES)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.LOCATION_HARDWARE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_MEDIA_LOCATION)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CONTROL_LOCATION_UPDATES)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.INSTALL_LOCATION_PROVIDER)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.CHANGE_NETWORK_STATE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.BIND_DEVICE_ADMIN)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.BIND_COMPANION_DEVICE_SERVICE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.UPDATE_DEVICE_STATS)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.BIND_MIDI_DEVICE_SERVICE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.REQUEST_OBSERVE_COMPANION_DEVICE_PRESENCE)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.USE_ICC_AUTH_WITH_DEVICE_IDENTIFIER)
                        != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            // Si no están concedidos, solicita los permisos
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.LOCATION_HARDWARE,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                            Manifest.permission.ACCESS_MEDIA_LOCATION,
                            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                            Manifest.permission.CONTROL_LOCATION_UPDATES,
                            Manifest.permission.INSTALL_LOCATION_PROVIDER,
                            Manifest.permission.ACCESS_WIFI_STATE,
                            Manifest.permission.CHANGE_WIFI_STATE,
                            Manifest.permission.OVERRIDE_WIFI_CONFIG,
                            Manifest.permission.CHANGE_WIFI_MULTICAST_STATE,
                            Manifest.permission.MANAGE_WIFI_INTERFACES,
                            Manifest.permission.MANAGE_WIFI_NETWORK_SELECTION,
                            Manifest.permission.NEARBY_WIFI_DEVICES,
                            Manifest.permission.ACCESS_NETWORK_STATE,
                            Manifest.permission.CHANGE_NETWORK_STATE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.BIND_COMPANION_DEVICE_SERVICE,
                            Manifest.permission.UPDATE_DEVICE_STATS,
                            Manifest.permission.BIND_MIDI_DEVICE_SERVICE,
                            Manifest.permission.REQUEST_OBSERVE_COMPANION_DEVICE_PRESENCE,
                            Manifest.permission.USE_ICC_AUTH_WITH_DEVICE_IDENTIFIER,
                            Manifest.permission.BIND_DEVICE_ADMIN},
                    1);
        }

        ThingHomeSdk.init(getApplication(), "fqkatyqm49kdjn5u8t9w", "sfa3qsqvh9fs987ahc345qj4ej9u8yfm");

        /*ThingHomeSdk.getMemberInstance().getInvitationMessage(190971144, new IThingDataCallback<InviteMessageBean>() {
            @Override
            public void onSuccess(InviteMessageBean result) {
                Toast.makeText(getApplicationContext(),"code: " + result.getInvitationCode(), Toast.LENGTH_LONG).show();
                Log.i("code: ",result.getInvitationCode());
            }

            @Override
            public void onError(String errorCode, String errorMessage) {
                // onErr
            }
        });*/

        /*Enviar_Correo enviar = new Enviar_Correo("ereyb@uteq.edu.ec","eduinrey12@gmail.com","Verificacion","Probando el contenido");
        enviar.execute();*/




        //crear hogar
        /*ThingHomeSdk.getHomeManagerInstance().createHome("Facultad 2", 0, 0, "", Arrays.asList("Aulas"), new IThingHomeResultCallback() {
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
                Toast.makeText(getApplicationContext(), "Todo correcto2", Toast.LENGTH_SHORT).show();
                for (HomeBean home : homeBeans) {
                    Log.i("ID del hogar: ", String.valueOf(home.getHomeId()));
                }
            }

            @Override
            public void onError(String errorCode, String error) {
                Toast.makeText(getApplicationContext(),"Error: " + errorCode,Toast.LENGTH_LONG).show();
            }
        });*/







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