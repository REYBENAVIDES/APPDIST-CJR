package com.uteq.dispositivos;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.thingclips.smart.android.user.api.ILoginCallback;
import com.thingclips.smart.android.user.bean.User;
import com.thingclips.smart.home.sdk.ThingHomeSdk;
import com.uteq.dispositivos.ApiService.ApiUrl;
import com.uteq.dispositivos.ApiService.ApiUsuario;
import com.uteq.dispositivos.Modelo.Usuario;
import com.google.android.material.textfield.TextInputLayout;
import com.uteq.dispositivos.R;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class Activity_IniciarSesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiUrl.urlUbicMedic)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiUsuario apiService = retrofit.create(ApiUsuario.class);

        TextView txtProbar = findViewById(R.id.txtProbar);

        TextView txtCorreo = findViewById(R.id.txtCorreo);
        TextView txtClave = findViewById(R.id.txtClave);
        TextInputLayout correoTextInputLayout = findViewById(R.id.correo_text_input_layout);
        TextInputLayout claveTextInputLayout = findViewById(R.id.clave_text_input_layout);

        Button btnIniciar = findViewById(R.id.btnIniciarSesion);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtCorreo.getText().toString().isEmpty() || txtClave.getText().toString().isEmpty()) {
                    if (txtCorreo.getText().toString().isEmpty()) {
                        correoTextInputLayout.setError("El campo no puede estar vacio");
                        correoTextInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#E91E63")));
                    } else {
                        correoTextInputLayout.setError(null);
                        correoTextInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#007224")));
                    }
                    //clave
                    if (txtClave.getText().toString().isEmpty()) {
                        claveTextInputLayout.setError("El campo no puede estar vacio");
                        claveTextInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#E91E63")));
                    } else {
                        claveTextInputLayout.setError(null);
                        claveTextInputLayout.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#007224")));
                    }
                } else {
                    //Iniciar sesion
                    ThingHomeSdk.getUserInstance().loginWithEmail("593", txtCorreo.getText().toString(), txtClave.getText().toString(), new ILoginCallback() {
                        @Override
                        public void onSuccess(User user) {
                            Call<List<Usuario>> call = apiService.get();
                            call.enqueue(new Callback<List<Usuario>>() {
                                @Override
                                public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                                    if (response.isSuccessful()) {
                                        try {
                                            List<Usuario> usuarios = response.body();
                                            boolean comprobar = false;
                                            int id_usuario = 0;
                                            String n_usuario = "";
                                            String correo = "";
                                            for (Usuario usuario : usuarios) {
                                                if(usuario.getEmail().equals(txtCorreo.getText().toString()) && usuario.getContraseña().equals(txtClave.getText().toString())){
                                                    id_usuario = usuario.getIdUsuario();
                                                    n_usuario = usuario.getUsuario();
                                                    correo = usuario.getEmail();
                                                    comprobar = true;
                                                }
                                            }
                                            if (comprobar){
                                                Toast.makeText(getApplicationContext(),"Bienvenido", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(getApplicationContext(), Activity_Facultades.class);
                                                intent.putExtra("id_cliente", id_usuario);
                                                intent.putExtra("usuario", n_usuario);
                                                intent.putExtra("correo", correo);
                                                startActivity(intent);
                                            }else{
                                                TextView txtError = findViewById(R.id.txterror);
                                                txtError.setVisibility(View.VISIBLE);
                                            }
                                            txtProbar.setText(usuarios.toString());
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    } else {
                                        // Manejar errores de respuesta
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<Usuario>> call, Throwable t) {
                                    // Manejar errores de red o de conexión
                                }
                            });
                        }

                        @Override
                        public void onError(String code, String error) {
                            Toast.makeText(getApplicationContext(), "code: " + code + "error:" + error, Toast.LENGTH_SHORT).show();
                        }
                    });


                }
            }
        });
    }
}