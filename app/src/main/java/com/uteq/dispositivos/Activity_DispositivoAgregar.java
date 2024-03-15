package com.uteq.dispositivos;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.thingclips.smart.home.sdk.ThingHomeSdk;
import com.thingclips.smart.home.sdk.builder.ActivatorBuilder;
import com.thingclips.smart.sdk.api.IThingActivator;
import com.thingclips.smart.sdk.api.IThingActivatorGetToken;
import com.thingclips.smart.sdk.api.IThingSmartActivatorListener;
import com.thingclips.smart.sdk.bean.DeviceBean;
import com.thingclips.smart.sdk.enums.ActivatorModelEnum;
import com.uteq.dispositivos.Adaptador.DispositivoAdapter;
import com.uteq.dispositivos.ApiService.ApiDispositivos;
import com.uteq.dispositivos.ApiService.ApiUrl;
import com.uteq.dispositivos.Modelo.Dispositivo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_DispositivoAgregar extends AppCompatActivity {

    Integer paso = 1;
    String devId = "";
    String modelo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivo_agregar);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        Button btnContinuar = findViewById(R.id.btnContinuar);
        ImageButton btnAtras = findViewById(R.id.btnAtras);
        TextView txtpaso = findViewById(R.id.txtvPasoTitulo);
        TextView txtpasoSub = findViewById(R.id.txtvPasoSubTitulo);
        TextView txtssid = findViewById(R.id.txtssid);
        TextView txtclave = findViewById(R.id.txtClave);
        TextView txtCategoria = findViewById(R.id.txtdevid);
        TextView txtNombre = findViewById(R.id.txtNombre);
        LinearLayout llWifi = findViewById(R.id.llDatosWifi);
        LinearLayout llEscanear = findViewById(R.id.llReiniciarDispo);
        ImageView imagen = findViewById(R.id.imgRegistro);

        LottieAnimationView animationView = findViewById(R.id.animationView);
        animationView.setAnimation(R.raw.carga);
        animationView.loop(true);
        animationView.playAnimation();

        llEscanear.setVisibility(View.GONE);
        animationView.setVisibility(View.GONE);
        btnAtras.setVisibility(View.GONE);
        llWifi.setVisibility(View.VISIBLE);
        imagen.setVisibility(View.VISIBLE);



        btnContinuar.setOnClickListener(view -> {
            if(paso == 1){
                btnAtras.setVisibility(View.VISIBLE);
                txtpaso.setText("Reinicie el dispositivo");
                txtpasoSub.setText("Mantenga pulsado el botón REINICIAR durante 5 segundos hasta que el indicador parpadee");
                imagen.setImageResource(R.drawable.breakewifi);
                llWifi.setVisibility(View.GONE);
                paso = 2;
            }else if (paso == 2) {
                txtpaso.setText("Escaneando dispositivo");
                txtpasoSub.setText("Durante este proceso de escaneo no se desconecte del wifi. Esto puede tardar varios segundos");
                imagen.setVisibility(View.GONE);
                animationView.setVisibility(View.VISIBLE);
                paso = 0;
                ThingHomeSdk.getActivatorInstance().getActivatorToken(190971144,
                        new IThingActivatorGetToken() {
                            @Override
                            public void onSuccess(String token) {
                                ActivatorBuilder builder = new ActivatorBuilder()
                                        .setSsid(txtssid.getText().toString())
                                        .setContext(getApplicationContext())
                                        .setPassword(txtclave.getText().toString())
                                        .setActivatorModel(ActivatorModelEnum.THING_EZ)
                                        .setTimeOut(60)
                                        .setToken(token)
                                        .setListener(new IThingSmartActivatorListener() {

                                                         @Override
                                                         public void onError(String errorCode, String errorMsg) {
                                                             Toast.makeText(getApplicationContext(),"Error: " + errorCode, Toast.LENGTH_LONG).show();
                                                             animationView.setVisibility(View.GONE);
                                                             paso = 2;
                                                             btnContinuar.setText("Escanear de nuevo");
                                                         }

                                                         @Override
                                                         public void onActiveSuccess(DeviceBean devResp) {
                                                             try {
                                                                 Retrofit retrofit = new Retrofit.Builder()
                                                                         .baseUrl(ApiUrl.urlUbicMedic)
                                                                         .addConverterFactory(GsonConverterFactory.create())
                                                                         .build();

                                                                 ApiDispositivos apiService = retrofit.create(ApiDispositivos.class);
                                                                 Call<Void> call_put = apiService.deleteDevId(devResp.devId);
                                                                 call_put.enqueue(new Callback<Void>() {
                                                                     @Override
                                                                     public void onResponse(Call<Void> call, Response<Void> response) {
                                                                         animationView.setVisibility(View.GONE);
                                                                         txtpaso.setText("Dispositivo dectectado");
                                                                         txtpasoSub.setText("Se dectecto un dispositivo. Verifique la informacion para continuar");
                                                                         txtCategoria.setText(devResp.getCategoryCode());
                                                                         devId = devResp.devId;
                                                                         imagen.setVisibility(View.VISIBLE);
                                                                         if("wf_ble_cz".equals(txtCategoria.getText().toString())){
                                                                             imagen.setImageResource(R.drawable.dispositivo_switch);
                                                                         }else if("wf_ble_kg".equals(txtCategoria.getText().toString())){ //wf_ble_kg
                                                                             imagen.setImageResource(R.drawable.dispositivo_touch);
                                                                         }else if("wf_cz".equals(txtCategoria.getText().toString())) {
                                                                             imagen.setImageResource(R.drawable.dispositivo_tomacorriente);
                                                                         }
                                                                         llEscanear.setVisibility(View.VISIBLE);
                                                                         btnContinuar.setText("Agregar");
                                                                         paso = 3;
                                                                     }
                                                                     @Override
                                                                     public void onFailure(Call<Void> call, Throwable t) {
                                                                         // Ocurrió un error de red o de conexión
                                                                         // Aquí puedes manejar el error si es necesario
                                                                     }
                                                                 });

                                                             } catch (Exception e) {
                                                                 // Handle exception
                                                             }

                                                         }

                                                         @Override
                                                         public void onStep(String step, Object data) {

                                                         }
                                                     }
                                        );
                                IThingActivator mThingActivator = ThingHomeSdk.getActivatorInstance().newMultiActivator(builder);
                                mThingActivator.start();

                            }

                            @Override
                            public void onFailure(String s, String s1) {

                            }
                        });
            } else if (paso == 3) {
                try {
                    int idAula = getIntent().getIntExtra("idAula", 0);
                    JSONObject aulaJson = new JSONObject();
                    JSONObject jsonObject = new JSONObject();
                    aulaJson.put("id_aula", idAula);
                    jsonObject.put("aula", aulaJson);
                    jsonObject.put("nombre", txtNombre.getText().toString());
                    jsonObject.put("marca", "Tuya");
                    jsonObject.put("modelo", txtCategoria.getText().toString());
                    jsonObject.put("devId", devId);
                    jsonObject.put("estado", true);
                    jsonObject.put("id_aula", idAula);
                    String jsonString = jsonObject.toString();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(ApiUrl.urlUbicMedic)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ApiDispositivos apiService = retrofit.create(ApiDispositivos.class);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
                    Call<ResponseBody> call = apiService.post(requestBody);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Se registro correctamente", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                // Handle unsuccessful response
                            }
                        }
                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            // Ocurrió un error de red o de conexión
                            // Aquí puedes manejar el error si es necesario
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        btnAtras.setOnClickListener(view -> {
            if (paso == 2){
                btnAtras.setVisibility(View.GONE);
                txtpaso.setText("Selecciona una red WiFi");
                txtpasoSub.setText("Necesitamos detalles de la red WiFi. Completa la informacion para continuar");
                imagen.setImageResource(R.drawable.no_connection_pana);
                llWifi.setVisibility(View.VISIBLE);
                btnContinuar.setText("Continuar");
                paso = 1;
            } else if (paso == 3 )  {
                btnAtras.setVisibility(View.VISIBLE);
                txtpaso.setText("Reinicie el dispositivo");
                txtpasoSub.setText("Mantenga pulsado el botón REINICIAR durante 5 segundos hasta que el indicador parpadee");
                imagen.setImageResource(R.drawable.breakewifi);
                llWifi.setVisibility(View.GONE);
                animationView.setVisibility(View.GONE);
                llEscanear.setVisibility(View.GONE);

                paso = 2;
            }
        });
    }
}