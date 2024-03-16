package com.uteq.dispositivos;

import com.thingclips.sdk.home.bean.InviteMessageBean;
import com.thingclips.smart.android.user.api.ILoginCallback;
import com.thingclips.smart.android.user.api.ILogoutCallback;
import com.thingclips.smart.android.user.api.IRegisterCallback;
import com.thingclips.smart.android.user.bean.User;
import com.thingclips.smart.home.sdk.ThingHomeSdk;
import com.thingclips.smart.sdk.api.IResultCallback;
import com.thingclips.smart.sdk.api.IThingDataCallback;
import com.uteq.dispositivos.ApiService.ApiUrl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uteq.dispositivos.ApiService.ApiUsuario;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Activity_Registrar extends AppCompatActivity {

    String code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        //Declarar variables
        LinearLayout llDatos = findViewById(R.id.llDatosRegistro);
        LinearLayout llVerificar = findViewById(R.id.llVerificacion);
        TextInputLayout txtInputUsuario = findViewById(R.id.usuario_text_input_layout);
        TextInputLayout txtInputCorreo = findViewById(R.id.correo_text_input_layout);
        TextInputLayout txtInputClave = findViewById(R.id.clave_text_input_layout);
        TextInputLayout txtInputConfirmar = findViewById(R.id.claveConfirmar_text_input_layout);
        TextView txtUsuario = findViewById(R.id.txtUsuario);
        TextView txtError = findViewById(R.id.txterror);
        TextView txtCorreo = findViewById(R.id.txtCorreo);
        TextView txtClave = findViewById(R.id.txtClave);
        TextView txtClaveConfirmar = findViewById(R.id.txtClaveConfirmar);
        TextView txtTitulo = findViewById(R.id.txtTituloRegistro);
        TextView txtNotificar = findViewById(R.id.txtnotificacioEnvio);
        TextView txtVerificar = findViewById(R.id.txtVerificar);
        Button btnRegistrar = findViewById(R.id.btnRegistrarse);
        Button btnVerificar = findViewById(R.id.btnVerificar);
        ImageButton btnAtras = findViewById(R.id.btnAtras);

        llDatos.setVisibility(View.VISIBLE);
        llVerificar.setVisibility(View.GONE);


        ThingHomeSdk.getUserInstance().loginWithEmail("593", "ereyb@uteq.edu.ec", "12345", new ILoginCallback() {
            @Override
            public void onSuccess(User user) {
                ThingHomeSdk.getMemberInstance().getInvitationMessage(190971144, new IThingDataCallback<InviteMessageBean>() {
                    @Override
                    public void onSuccess(InviteMessageBean result) {
                        Log.i("Codigo: ",result.getInvitationCode());
                        code = result.getInvitationCode();
                        ThingHomeSdk.getUserInstance().logout(new ILogoutCallback() {
                            @Override
                            public void onSuccess() {
                                Toast.makeText(getApplicationContext(),"cerro sesion", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(String errorCode, String errorMsg) {
                            }
                        });
                    }

                    @Override
                    public void onError(String errorCode, String errorMessage) {
                        Toast.makeText(getApplicationContext(), "code: " + errorCode + "error:" + errorMessage, Toast.LENGTH_SHORT).show();
                        Log.i("Error: ",errorMessage);
                    }
                });
            }

            @Override
            public void onError(String code, String error) {
                Toast.makeText(getApplicationContext(), "code: " + code + "error:" + error, Toast.LENGTH_SHORT).show();
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitulo.setText("Registrar");
                llDatos.setVisibility(View.VISIBLE);
                llVerificar.setVisibility(View.GONE);
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtUsuario.getText().toString().isEmpty() || txtCorreo.getText().toString().isEmpty() || txtClave.getText().toString().isEmpty() || txtClaveConfirmar.getText().toString().isEmpty()) {
                    if (txtUsuario.getText().toString().isEmpty()) {
                        txtInputUsuario.setError("El campo no puede estar vacio");
                        txtInputUsuario.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#E91E63")));
                    } else {
                        txtInputUsuario.setError(null);
                        txtInputUsuario.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#007224")));
                        txtInputUsuario.setErrorEnabled(false);
                    }

                    if (txtCorreo.getText().toString().isEmpty()) {
                        txtInputCorreo.setError("El campo no puede estar vacio");
                        txtInputCorreo.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#E91E63")));
                    } else {
                        txtInputCorreo.setError(null);
                        txtInputCorreo.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#007224")));
                        txtInputCorreo.setErrorEnabled(false);
                    }

                    // Clave
                    if (txtClave.getText().toString().isEmpty()) {
                        txtInputClave.setError("El campo no puede estar vacio");
                        txtInputClave.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#E91E63")));
                    } else {
                        txtInputClave.setError(null);
                        txtInputClave.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#007224")));
                        txtInputClave.setErrorEnabled(false);
                    }

                    // Confirmar
                    if (txtClaveConfirmar.getText().toString().isEmpty()) {
                        txtInputConfirmar.setError("El campo no puede estar vacio");
                        txtInputConfirmar.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#E91E63")));
                    } else {
                        txtInputConfirmar.setError(null);
                        txtInputConfirmar.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#007224")));
                        txtInputConfirmar.setErrorEnabled(false);
                    }
                } else {
                    if (!txtClave.getText().toString().equals(txtClaveConfirmar.getText().toString())) {
                        txtError.setVisibility(View.VISIBLE);
                    } else {
                        try {
                            txtTitulo.setText("Introducir código de verificación");
                            txtNotificar.setText("El código de verificación se ha enviado a su correo: " + txtCorreo.getText());
                            llDatos.setVisibility(View.GONE);
                            llVerificar.setVisibility(View.VISIBLE);
                            txtError.setVisibility(View.GONE);

                            ThingHomeSdk.getUserInstance().sendVerifyCodeWithUserName(txtCorreo.getText().toString(), "", "593", 1, new IResultCallback() {
                                @Override
                                public void onError(String code, String error) {
                                    Toast.makeText(getApplicationContext(), "code: " + code + "error:" + error, Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onSuccess() {
                                    Toast.makeText(getApplicationContext(), "El código de verificación se envio exitosamente.", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });

        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThingHomeSdk.getUserInstance().checkCodeWithUserName(txtCorreo.getText().toString(), "", "593", txtVerificar.getText().toString(), 1, new IResultCallback() {
                    @Override
                    public void onError(String code, String error) {
                        Toast.makeText(getApplicationContext(), "code: " + code + "error:" + error, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess() {
                        ThingHomeSdk.getUserInstance().registerAccountWithEmail("593", txtCorreo.getText().toString(),txtClave.getText().toString(),txtVerificar.getText().toString(), new IRegisterCallback() {
                            @Override
                            public void onSuccess(User user) {
                                try{
                                    JSONObject jsonObject = new JSONObject();
                                    jsonObject.put("usuario", txtUsuario.getText().toString());
                                    jsonObject.put("email", txtCorreo.getText().toString());
                                    jsonObject.put("contraseña", txtClave.getText().toString());
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
                                                ThingHomeSdk.getHomeManagerInstance().joinHomeByInviteCode(code , new IResultCallback() {
                                                    @Override
                                                    public void onError(String code, String error) {
                                                        Toast.makeText(getApplicationContext(),"Error",Toast.LENGTH_LONG).show();
                                                        Log.i("Error: ", code + ", " + error);
                                                    }

                                                    @Override
                                                    public void onSuccess() {
                                                        Toast.makeText(getApplicationContext(),"Se registro correctamente",Toast.LENGTH_LONG).show();
                                                        Intent intent = new Intent(getApplicationContext(), Activity_IniciarSesion.class);
                                                        startActivity(intent);
                                                    }
                                                });

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
                                }catch (JSONException e){
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(String code, String error) {
                                Toast.makeText(getApplicationContext(), "code: " + code + "error:" + error, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });

        txtCorreo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Este método se llama antes de que el texto cambie
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Este método se llama cuando el texto está cambiando
                // Puedes utilizar 's' para obtener el texto actual en el EditText
            }

            @Override
            public void afterTextChanged(Editable s) {
                String expresionRegular = "^[a-zA-Z0-9._%+-]+@(gmail\\.com|uteq\\.edu\\.ec)$";

                // Compilar la expresión regular en un patrón
                Pattern pattern = Pattern.compile(expresionRegular);

                // Crear un objeto Matcher para comparar el correo con el patrón
                Matcher matcher = pattern.matcher(s);

                // Verificar si el correo coincide con el patrón
                if (!matcher.matches()) {
                    // El correo es válido
                    txtInputCorreo.setError("Correo no valido.");
                    txtInputCorreo.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#E91E63")));
                } else {
                    txtInputCorreo.setError(null);
                    txtInputCorreo.setDefaultHintTextColor(ColorStateList.valueOf(Color.parseColor("#007224")));
                    txtInputCorreo.setErrorEnabled(false);
                }
            }
        });
    }
}