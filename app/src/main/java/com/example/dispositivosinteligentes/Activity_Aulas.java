package com.example.dispositivosinteligentes;

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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dispositivosinteligentes.Adaptador.AulaAdapter;
import com.example.dispositivosinteligentes.Adaptador.FacultadAdapter;
import com.example.dispositivosinteligentes.ApiService.ApiAula;
import com.example.dispositivosinteligentes.ApiService.ApiUrl;
import com.example.dispositivosinteligentes.Modelo.Aula;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Aulas extends AppCompatActivity {

    private RecyclerView rcvAula;
    private AulaAdapter adapterAula;
    private int id_cliente = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aulas);

        id_cliente = getIntent().getIntExtra("id_cliente", 0);

        rcvAula = findViewById(R.id.rcvDispositivos);
        rcvAula.setLayoutManager(new GridLayoutManager(this, 3));

        actualizarDatos();

        ImageButton btnAgregar = findViewById(R.id.btnAgregarAula);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAgregarAula();
            }
        });

        ImageButton btnActualizar = findViewById(R.id.btnActualizar);
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDatos();
            }
        });
    }

    public void actualizarDatos() {
        int idFacultad = getIntent().getIntExtra("idFacultad", 0);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.urlUbicMedic)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiAula apiService = retrofit.create(ApiAula.class);
        Call<List<Aula>> call = apiService.getCall();

        call.enqueue(new Callback<List<Aula>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<Aula>> call, Response<List<Aula>> response) {
                if (response.isSuccessful()) {
                    List<Aula> datos = response.body();
                    List<Aula> datosNuevo = new ArrayList<>();
                    if (datos != null) {
                        for (Aula itemDato : datos) {
                            if (itemDato.getId_facultad() == idFacultad) {
                                datosNuevo.add(itemDato);
                            }
                        }
                    }
                    if (datosNuevo != null) {
                        adapterAula = new AulaAdapter(datosNuevo, (id, precionado, it) -> {
                            Aula aula = datosNuevo.stream().filter(a -> a.getId_aula() == id).findAny().orElse(null);
                            if (aula != null) {
                                if (precionado) {
                                    PopupMenu popupMenu = new PopupMenu(getApplicationContext(), it);
                                    popupMenu.inflate(R.menu.menu_facultad);
                                    popupMenu.setOnMenuItemClickListener(menuItem -> {
                                        if (menuItem.getItemId() == R.id.op_detalle) {
                                            showDialogDetalle(aula.getNombre(), aula.getCantidad_dispositivos(), aula.getCantidad_dispositivos_activo());
                                            return true;
                                        } else if (menuItem.getItemId() == R.id.op_eliminar) {
                                            showDialogEliminar(aula.getId_aula(), aula.getNombre());
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    });
                                    popupMenu.show();
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), Activity_Dispositivos.class);
                                    intent.putExtra("idAula", aula.getId_aula());
                                    intent.putExtra("id_cliente", id_cliente);
                                    startActivity(intent);

                                }

                            }
                        });
                        rcvAula.setAdapter(adapterAula);
                        Toast.makeText(getApplicationContext(), "Se actualizaron los datos", Toast.LENGTH_SHORT).show();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<List<Aula>> call, Throwable t) {

            }
        });
    }

    private void showDialogAgregarAula() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_facultades);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView txtEncabezado = dialog.findViewById(R.id.txtEncabezadoDialog);
        ImageView imgLogo = dialog.findViewById(R.id.imgLogo);
        TextView txtNombre = dialog.findViewById(R.id.txtMotivoDialog);
        TextInputLayout txtInputNombre = dialog.findViewById(R.id.motivo_text_input_layout);
        LinearLayout llfoto = dialog.findViewById(R.id.llFoto);
        Button btnAgregar = dialog.findViewById(R.id.btnAgregarDialog);
        TextInputLayout txt_i_nombre = dialog.findViewById(R.id.motivo_text_input_layout);
        llfoto.setVisibility(View.GONE);
        txtEncabezado.setText("Agregar Aula");
        imgLogo.setImageResource(R.drawable.logo_uteq);

        btnAgregar.setOnClickListener(view -> {
            if (txtNombre.getText().toString().isEmpty()) {
                txt_i_nombre.setError("El campo no puede estar vacio");
                txt_i_nombre.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#E91E63")));
            } else {
                try {
                    int idFacultad = getIntent().getIntExtra("idFacultad", 0);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("nombre", txtNombre.getText().toString());
                    jsonObject.put("id_facultad", idFacultad);
                    String jsonString = jsonObject.toString();

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(ApiUrl.urlUbicMedic)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ApiAula apiService = retrofit.create(ApiAula.class);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
                    Call<ResponseBody> call = apiService.post(requestBody);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getApplicationContext(),"Se registro correctamente",Toast.LENGTH_LONG).show();
                            } else {
                                // La solicitud no fue exitosa
                                // Aquí puedes manejar el error si es necesario
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
        txtEncabezado.setText("¿Quieres eliminar la " + nombre + "?");
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

                ApiAula apiService = retrofit.create(ApiAula.class);

                Call<Void> call = apiService.delete(idEliminar);

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.isSuccessful()) {
                            dialog.dismiss();
                            actualizarDatos();
                            Toast.makeText(getApplicationContext(), "Se elimino la facultad", Toast.LENGTH_LONG).show();
                        } else {
                            // Manejar la respuesta no exitosa
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

    private void showDialogDetalle(String aula, int dispositivo, int activo) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_detalle_facultad);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView txtEncabezado = dialog.findViewById(R.id.txtvEncabezado);
        LinearLayout llAulas = dialog.findViewById(R.id.llAulas);
        TextView txtAvisoDispositivos = dialog.findViewById(R.id.txtAvisoDispositivo);
        TextView txtAvisoDispositivosActivo = dialog.findViewById(R.id.txtAvisoDispositivoActivo);
        llAulas.setVisibility(View.GONE);
        txtEncabezado.setText(aula);
        txtAvisoDispositivos.setText("Cuenta con " + dispositivo + " dispositivos");
        txtAvisoDispositivosActivo.setText("Cuenta con " + activo + " dispositivos activos");

        dialog.show();
    }

}