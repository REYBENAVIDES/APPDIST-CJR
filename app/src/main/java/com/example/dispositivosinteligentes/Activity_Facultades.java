package com.example.dispositivosinteligentes;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dispositivosinteligentes.Adaptador.FacultadAdapter;
import com.example.dispositivosinteligentes.ApiService.ApiFacultad;
import com.example.dispositivosinteligentes.ApiService.ApiUrl;
import com.example.dispositivosinteligentes.ApiService.ApiUsuario;
import com.example.dispositivosinteligentes.Modelo.Facultad;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Facultades extends AppCompatActivity {

    private RecyclerView rcvFacultad;
    private FacultadAdapter adapterFacultad;
    private String rutaImagen = "";
    private ImageView foto;
    private int id_usuario = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facultades);

        id_usuario = getIntent().getIntExtra("id_cliente", 0);

        rcvFacultad = findViewById(R.id.rcvDispositivos);
        rcvFacultad.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        actualizarDatos();

        ImageButton addButton = findViewById(R.id.btnAgregarFacultad);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogAgregarFacultad();
            }
        });

        ImageButton updateButton = findViewById(R.id.btnActualizar);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarDatos();
            }
        });

    }

    public void actualizarDatos() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.urlUbicMedic)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiFacultad apiService = retrofit.create(ApiFacultad.class);
        Call<List<Facultad>> call = apiService.getCall();

        call.enqueue(new Callback<List<Facultad>>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<List<Facultad>> call, Response<List<Facultad>> response) {
                if (response.isSuccessful()) {
                    List<Facultad> datos = response.body();
                    if (datos != null) {
                        adapterFacultad = new FacultadAdapter(datos, (id, precionado, it) -> {
                            Facultad facultad = datos.stream().filter(a -> a.getId_facultad() == id).findAny().orElse(null);
                            if (facultad != null) {
                                if (precionado) {
                                    PopupMenu popupMenu = new PopupMenu(getApplicationContext(), it);
                                    popupMenu.inflate(R.menu.menu_facultad);
                                    popupMenu.setOnMenuItemClickListener(menuItem -> {
                                        if (menuItem.getItemId() == R.id.op_detalle) {
                                            showDialogDetalle(facultad.getNombre(), facultad.getCantidad_aula(), facultad.getCantidad_dispositivos(),
                                                    facultad.getCantidad_dispositivos_activo());
                                            return true;
                                        } else if (menuItem.getItemId() == R.id.op_eliminar) {
                                            showDialogEliminar(facultad.getId_facultad(), facultad.getNombre());
                                            return true;
                                        } else if (menuItem.getItemId() == R.id.op_compartir) {
                                            showDialogCompartir();
                                            return true;
                                        } else {
                                            return false;
                                        }
                                    });
                                    popupMenu.show();
                                } else {
                                    Intent intent = new Intent(getApplicationContext(), Activity_Aulas.class);
                                    intent.putExtra("idFacultad", facultad.getId_facultad());
                                    intent.putExtra("id_cliente", id_usuario);
                                    startActivity(intent);
                                }
                            }
                        });
                        rcvFacultad.setAdapter(adapterFacultad);
                        Toast.makeText(getApplicationContext(), "Se actualizaron los datos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<List<Facultad>> call, Throwable t) {
                // Handle failure
            }
        });
    }

    private void showDialogAgregarFacultad() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_facultades);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView txtEncabezado = dialog.findViewById(R.id.txtEncabezadoDialog);
        ImageView imgLogo = dialog.findViewById(R.id.imgLogo);
        TextView txtNombre = dialog.findViewById(R.id.txtMotivoDialog);
        Button btnAgregar = dialog.findViewById(R.id.btnAgregarDialog);
        Button btnFoto = dialog.findViewById(R.id.btnSelecionarFoto);
        TextInputLayout txt_i_nombre = dialog.findViewById(R.id.motivo_text_input_layout);
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
                JSONObject jsonObject = new JSONObject();
                try {
                    JSONObject usuarioJson = new JSONObject();
                    usuarioJson.put("id_usuario", id_usuario);
                    jsonObject.put("nombre", txtNombre.getText().toString());
                    jsonObject.put("usuario", usuarioJson);

                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(ApiUrl.urlUbicMedic)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    ApiFacultad apiService = retrofit.create(ApiFacultad.class);

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

    private void showDialogEliminar(int idEliminar, String facultad) {
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

        txtEncabezado.setText("¿Quieres eliminar la facultad de " + facultad + "?");
        txtEliminar.setText("Cuando eliminas una facultad se eliminaran las aulas y dispositivos que tengan");

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

                ApiFacultad apiService = retrofit.create(ApiFacultad.class);

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

    private void showDialogDetalle(String facultad, int aula, int dispositivo, int activo) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_detalle_facultad);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView txtEncabezado = dialog.findViewById(R.id.txtvEncabezado);
        TextView txtAvisoAula = dialog.findViewById(R.id.txtAvisoAulas);
        TextView txtAvisoDispositivos = dialog.findViewById(R.id.txtAvisoDispositivo);
        TextView txtAvisoDispositivosActivo = dialog.findViewById(R.id.txtAvisoDispositivoActivo);
        txtEncabezado.setText(facultad);
        txtAvisoAula.setText("Cuenta con " + aula + " aulas");
        txtAvisoDispositivos.setText("Cuenta con " + dispositivo + " dispositivos");
        txtAvisoDispositivosActivo.setText("Cuenta con " + activo + " dispositivos activos");

        dialog.show();
    }

    private void showDialogCompartir() {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_facultad_compartir);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        TextView txtEncabezado = dialog.findViewById(R.id.txtEncabezadoDialog);

        dialog.show();
    }

}