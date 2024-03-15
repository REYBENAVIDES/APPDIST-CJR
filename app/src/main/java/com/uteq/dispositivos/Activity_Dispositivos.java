package com.uteq.dispositivos;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.thingclips.smart.sdk.api.IDevListener;
import com.thingclips.smart.sdk.api.IResultCallback;
import com.thingclips.smart.sdk.api.IThingDevice;
import com.uteq.dispositivos.Adaptador.DispositivoAdapter;
import com.uteq.dispositivos.ApiService.ApiDispositivoHistorial;
import com.uteq.dispositivos.ApiService.ApiDispositivoTuya;
import com.uteq.dispositivos.ApiService.ApiDispositivos;
import com.uteq.dispositivos.ApiService.ApiUrl;
import com.uteq.dispositivos.Modelo.Dispositivo;
import com.google.android.material.textfield.TextInputLayout;
import com.thingclips.smart.home.sdk.ThingHomeSdk;
import com.uteq.dispositivos.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;



public class Activity_Dispositivos extends AppCompatActivity {


    private RecyclerView rcvDispositivo;
    private DispositivoAdapter adapterDispositivo;
    private String rutaImagen = "";
    private ImageView foto;
    private LottieAnimationView animationView;
    private int id_cliente = 0;

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos);

        int idAula = getIntent().getIntExtra("idAula", 0);
        int clientId = getIntent().getIntExtra("id_cliente", 0);

        rcvDispositivo = findViewById(R.id.rcvDispositivos);
        rcvDispositivo.setLayoutManager(new GridLayoutManager(this, 2));

        actualizarDatos();

        ImageButton btnAgregar = findViewById(R.id.btnAgregarDispositivo);
        btnAgregar.setOnClickListener(view -> {
            //showDialogAgregarDispositivos();
            Intent intent = new Intent(getApplicationContext(), Activity_DispositivoAgregar.class);
            intent.putExtra("idAula", idAula);
            startActivity(intent);
        });

        ImageButton btnActualizar = findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDatos();
            }
        });

        LottieAnimationView animationView = findViewById(R.id.animationView);
        animationView.setAnimation(R.raw.candadopepa);
        animationView.loop(true);
        animationView.playAnimation();
        animationView.setVisibility(View.GONE);

        //IThingDevice mDevice = ThingHomeSdk.newDeviceInstance(deviceBean.getDevId());

    }

    public void actualizarDatos() {
        int idAula = getIntent().getIntExtra("idAula", 0);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.urlUbicMedic)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Retrofit retrofit2 = new Retrofit.Builder()
                .baseUrl("https://69d7-157-100-113-136.ngrok-free.app")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiDispositivos apiService = retrofit.create(ApiDispositivos.class);
        ApiDispositivoHistorial apiServiceX = retrofit.create(ApiDispositivoHistorial.class);
        ApiDispositivoTuya apiServiceTuya = retrofit2.create(ApiDispositivoTuya.class);
        Call<List<Dispositivo>> call = apiService.getCall();

        call.enqueue(new Callback<List<Dispositivo>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<Dispositivo>> call, Response<List<Dispositivo>> response) {
                if (response.isSuccessful()) {
                    List<Dispositivo> datos = response.body();
                    List<Dispositivo> datosNuevo = new ArrayList<>();
                    if (datos != null) {
                        for (Dispositivo itemDato : datos) {
                            if (itemDato.getIdAula().getId_aula() == idAula) {
                                datosNuevo.add(itemDato);
                            }
                        }
                    }
                    if (datosNuevo != null) {
                        adapterDispositivo = new DispositivoAdapter(datosNuevo, (id, posicion, op, it) -> {
                            Dispositivo dispositivo = datosNuevo.stream().filter(d -> d.getIdDispositivo() == id).findFirst().orElse(null);
                            if (dispositivo != null) {
                                switch (op) {
                                    case 0:
                                        PopupMenu popupMenu = new PopupMenu(getApplicationContext(), it);
                                        popupMenu.inflate(R.menu.menu_facultad);
                                        popupMenu.setOnMenuItemClickListener(menuItem -> {
                                            if (menuItem.getItemId() == R.id.op_detalle) {
                                                return true;
                                            } else if (menuItem.getItemId() == R.id.op_eliminar) {
                                                showDialogEliminar(dispositivo.getIdDispositivo(), dispositivo.getNombre());
                                                return true;
                                            } else {
                                                return false;
                                            }
                                        });
                                        popupMenu.show();
                                        break;
                                    case 1:
                                        IThingDevice mDevice = ThingHomeSdk.newDeviceInstance(dispositivo.getDevId());

                                        if (dispositivo.isEstado()) {
                                            try {
                                                dispositivo.setEstado(!dispositivo.isEstado());
                                                mDevice.publishDps("{\"1\": false}", new IResultCallback() {
                                                    @Override
                                                    public void onError(String code, String error) {
                                                        //Toast.makeText(getApplicationContext(), "Failed to switch on the light.", Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onSuccess() {
                                                        //Toast.makeText(getApplicationContext(), "The light is switched on successfully.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                            } catch (Exception e) {
                                                Toast.makeText(getApplicationContext(), e.getMessage().toString(), Toast.LENGTH_LONG).show();
                                            }
                                            try {
                                                Call<Dispositivo> call_put = apiService.put((long)dispositivo.getIdDispositivo(), dispositivo.isEstado());
                                                call_put.enqueue(new Callback<Dispositivo>() {
                                                    @Override
                                                    public void onResponse(Call<Dispositivo> call, Response<Dispositivo> response) {
                                                        if (response.isSuccessful()) {

                                                        } else {
                                                            // Handle unsuccessful response
                                                        }
                                                    }
                                                    @Override
                                                    public void onFailure(Call<Dispositivo> call, Throwable t) {
                                                        // Ocurrió un error de red o de conexión
                                                        // Aquí puedes manejar el error si es necesario
                                                    }
                                                });

                                            } catch (Exception e) {
                                                // Handle exception
                                            }
                                        }else{
                                            try {
                                                dispositivo.setEstado(!dispositivo.isEstado());
                                                mDevice.publishDps("{\"1\": true}", new IResultCallback() {
                                                    @Override
                                                    public void onError(String code, String error) {
                                                        //Toast.makeText(getApplicationContext(), "Failed to switch on the light.", Toast.LENGTH_SHORT).show();
                                                    }

                                                    @Override
                                                    public void onSuccess() {
                                                        //Toast.makeText(getApplicationContext(), "The light is switched on successfully.", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            } catch (Exception e) {
                                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                            try {
                                                Call<Dispositivo> call_put = apiService.put((long)dispositivo.getIdDispositivo(), dispositivo.isEstado());
                                                call_put.enqueue(new Callback<Dispositivo>() {
                                                    @Override
                                                    public void onResponse(Call<Dispositivo> call, Response<Dispositivo> response) {
                                                        if (response.isSuccessful()) {

                                                        } else {
                                                            // Handle unsuccessful response
                                                        }
                                                    }
                                                    @Override
                                                    public void onFailure(Call<Dispositivo> call, Throwable t) {
                                                        // Ocurrió un error de red o de conexión
                                                        // Aquí puedes manejar el error si es necesario
                                                    }
                                                });
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        adapterDispositivo.notifyItemChanged(posicion);

                                        break;
                                    case 2:
                                        IThingDevice mDevice2 = ThingHomeSdk.newDeviceInstance(dispositivo.getDevId());
                                        //animationView.setVisibility(View.VISIBLE);
                                        if (dispositivo.getModelo().equals("wf_cz")) {
                                            try {
                                                dispositivo.setEstado(!dispositivo.isEstado());
                                                if (dispositivo.isEstado()) {
                                                    mDevice2.publishDps("{\"1\": true}", new IResultCallback() {
                                                        @Override
                                                        public void onError(String code, String error) {
                                                            //Toast.makeText(getApplicationContext(), "Failed to switch on the light.", Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void onSuccess() {
                                                            //Toast.makeText(getApplicationContext(), "The light is switched on successfully.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                } else {
                                                    mDevice2.publishDps("{\"1\": false}", new IResultCallback() {
                                                        @Override
                                                        public void onError(String code, String error) {
                                                            //Toast.makeText(getApplicationContext(), "Failed to switch on the light.", Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void onSuccess() {
                                                            //Toast.makeText(getApplicationContext(), "The light is switched on successfully.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            } catch (Exception e) {
                                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        //animationView.setVisibility(View.GONE);
                                        adapterDispositivo.notifyItemChanged(posicion);
                                        break;
                                    case 3:
                                        IThingDevice mDevice3 = ThingHomeSdk.newDeviceInstance(dispositivo.getDevId());
                                        //animationView.setVisibility(View.VISIBLE);
                                        if (dispositivo.getModelo().equals("wf_cz")) {
                                            try {
                                                dispositivo.setEstado2(!dispositivo.isEstado2());
                                                if (dispositivo.isEstado2()) {
                                                    mDevice3.publishDps("{\"2\": true}", new IResultCallback() {
                                                        @Override
                                                        public void onError(String code, String error) {
                                                            //Toast.makeText(getApplicationContext(), "Failed to switch on the light.", Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void onSuccess() {
                                                            //Toast.makeText(getApplicationContext(), "The light is switched on successfully.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                } else {
                                                    mDevice3.publishDps("{\"2\": false}", new IResultCallback() {
                                                        @Override
                                                        public void onError(String code, String error) {
                                                            //Toast.makeText(getApplicationContext(), "Failed to switch on the light.", Toast.LENGTH_SHORT).show();
                                                        }

                                                        @Override
                                                        public void onSuccess() {
                                                            //Toast.makeText(getApplicationContext(), "The light is switched on successfully.", Toast.LENGTH_SHORT).show();
                                                        }
                                                    });
                                                }
                                            } catch (Exception e) {
                                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        }

                                        //animationView.setVisibility(View.GONE);
                                        adapterDispositivo.notifyItemChanged(posicion);
                                        break;
                                }
                            }

                        });
                        rcvDispositivo.setAdapter(adapterDispositivo);
                        Toast.makeText(getApplicationContext(), "Se actualizaron los datos", Toast.LENGTH_SHORT).show();
                    }

                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Dispositivo>> call, Throwable t) {

            }
        });
    }

    private void showDialogAgregarDispositivos() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_dispositivos);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView txtEncabezado = dialog.findViewById(R.id.txtEncabezadoDialog);
        ImageView imgLogo = dialog.findViewById(R.id.imgLogo);
        TextView txtNombre = dialog.findViewById(R.id.txtMotivoDialog);
        Button btnAgregar = dialog.findViewById(R.id.btnAgregarDialog);
        TextInputLayout txt_i_nombre = dialog.findViewById(R.id.motivo_text_input_layout);
        Spinner spnMarca = dialog.findViewById(R.id.spnMarca);
        List<String> itemsMarca = Arrays.asList("Meross", "Tuya");
        ArrayAdapter<String> adapterMarca = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemsMarca);
        adapterMarca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnMarca.setAdapter(adapterMarca);
        Spinner spnModelo = dialog.findViewById(R.id.spnModelo);
        List<String> itemsModelo = Arrays.asList("ON_I_OFF_1", "ON_I_OFF_2", "IOT-BASED", "Smart Touch", "Tomacorriente", "mss110");
        ArrayAdapter<String> adapterModelo = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, itemsModelo);
        adapterModelo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnModelo.setAdapter(adapterModelo);
        txtEncabezado.setText("Agregar Dispositivo");
        Button btnFoto = dialog.findViewById(R.id.btnSelecionarFoto);
        foto = dialog.findViewById(R.id.imgFotoSelecion);
        imgLogo.setImageResource(R.drawable.logo_uteq);

        btnFoto.setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1001); // Iniciar la actividad de selección de imágenes
        });

        btnAgregar.setOnClickListener(view -> {
            if (txtNombre.getText().toString().isEmpty()) {
                txt_i_nombre.setError("El campo no puede estar vacio");
                txt_i_nombre.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#E91E63")));
            } else {
                try {
                    int idAula = getIntent().getIntExtra("idAula", 0);
                    JSONObject aulaJson = new JSONObject();
                    JSONObject jsonObject = new JSONObject();
                    aulaJson.put("id_aula", idAula);
                    jsonObject.put("aula", aulaJson);
                    jsonObject.put("nombre", txtNombre.getText().toString());
                    jsonObject.put("marca", spnMarca.getSelectedItem().toString());
                    jsonObject.put("modelo", spnModelo.getSelectedItem().toString());
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
                    actualizarDatos();
                    dialog.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        dialog.show();
    }

    private void showDialogEliminar(int idEliminar, String nombre) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_eliminar);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView txtEncabezado = dialog.findViewById(R.id.txtvEncabezado);
        TextView txtEliminar = dialog.findViewById(R.id.txtAvisoBloquear);
        Button btnEliminar = dialog.findViewById(R.id.btnFinalizarSi);
        Button btnCancelar = dialog.findViewById(R.id.btnFinalizarNo);
        txtEncabezado.setText("¿Quieres eliminar el dispositivo: " + nombre + "?");
        txtEliminar.setText("Cuando eliminas una aula se eliminaran los dispositivos que tengan");

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(ApiUrl.urlUbicMedic)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiDispositivos apiService = retrofit.create(ApiDispositivos.class);

                Call<Void> call = apiService.delete(idEliminar);
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        try {
                            dialog.dismiss();
                            actualizarDatos();
                            Toast.makeText(getApplicationContext(), "Se elimino el dispositivo", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        // Manejar el error de la solicitud
                    }

                });

            }
        });

        dialog.show();
    }

    private void showDialogBloqueo(String dispositivo, String usuario, String fecha) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_bloqueo_dispositivo);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView txtEncabezado = dialog.findViewById(R.id.txtvEncabezado);
        TextView txtAvisoUsuario = dialog.findViewById(R.id.txtAvisoUsuario);
        TextView txtAvisoFecha = dialog.findViewById(R.id.txtAvisoFecha);
        txtEncabezado.setText(dispositivo);
        txtAvisoUsuario.setText(usuario + " bloqueo este dispositivo");
        txtAvisoFecha.setText("Fue bloqueado el " + fecha);

        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ThingHomeSdk.onDestroy();
    }

}