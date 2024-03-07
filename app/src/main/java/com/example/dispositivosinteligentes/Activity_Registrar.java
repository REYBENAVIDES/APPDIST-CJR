package com.example.dispositivosinteligentes;

import com.example.dispositivosinteligentes.ApiService.ApiUrl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dispositivosinteligentes.ApiService.ApiUsuario;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Registrar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        TextInputLayout txtInputUsuario = findViewById(R.id.usuario_text_input_layout);
        TextInputLayout txtInputCorreo = findViewById(R.id.correo_text_input_layout);
        TextInputLayout txtInputClave = findViewById(R.id.clave_text_input_layout);
        TextInputLayout txtInputConfirmar = findViewById(R.id.claveConfirmar_text_input_layout);
        TextView txtUsuario = findViewById(R.id.txtUsuario);
        TextView txtCorreo = findViewById(R.id.txtCorreo);
        TextView txtClave = findViewById(R.id.txtClave);
        TextView txtClaveConfirmar = findViewById(R.id.txtClaveConfirmar);
        Button btnRegistrar = findViewById(R.id.btnRegistrarse);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtUsuario.getText().toString().isEmpty() || txtCorreo.getText().toString().isEmpty() || txtClave.getText().toString().isEmpty() || txtClaveConfirmar.getText().toString().isEmpty()) {
                    if (txtUsuario.getText().toString().isEmpty()) {
                        txtInputUsuario.setError("El campo no puede estar vacio");
                        txtInputUsuario.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#E91E63")));
                    } else {
                        txtInputCorreo.setError(null);
                        txtInputCorreo.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#007224")));
                    }

                    if (txtCorreo.getText().toString().isEmpty()) {
                        txtInputCorreo.setError("El campo no puede estar vacio");
                        txtInputCorreo.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#E91E63")));
                    } else {
                        txtInputCorreo.setError(null);
                        txtInputCorreo.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#007224")));
                    }

                    // Clave
                    if (txtClave.getText().toString().isEmpty()) {
                        txtInputClave.setError("El campo no puede estar vacio");
                        txtInputClave.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#E91E63")));
                    } else {
                        txtInputClave.setError(null);
                        txtInputClave.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#007224")));
                    }

                    // Confirmar
                    if (txtClaveConfirmar.getText().toString().isEmpty()) {
                        txtInputConfirmar.setError("El campo no puede estar vacio");
                        txtInputConfirmar.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#E91E63")));
                    } else {
                        txtInputConfirmar.setError(null);
                        txtInputConfirmar.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#007224")));
                    }
                } else {
                    if (!txtClave.getText().toString().equals(txtClaveConfirmar.getText().toString())) {
                        TextView txtError = findViewById(R.id.txterror);
                        txtError.setVisibility(View.VISIBLE);
                    } else {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("usuario", txtUsuario.getText().toString());
                            jsonObject.put("email", txtCorreo.getText().toString());
                            jsonObject.put("contraseña", txtClave.getText().toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(ApiUrl.urlUbicMedic)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        ApiUsuario apiService = retrofit.create(ApiUsuario.class);

                        Call<ResponseBody> call = apiService.post(requestBody);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),"Se registro correctamente",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getApplicationContext(), Activity_IniciarSesion.class);
                                    startActivity(intent);
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

                    }
                }
            }
        });
    }
}