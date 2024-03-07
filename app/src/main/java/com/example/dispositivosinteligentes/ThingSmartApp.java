package com.example.dispositivosinteligentes;

import android.app.Application;
import android.widget.Toast;

import com.thingclips.smart.android.user.api.ILoginCallback;
import com.thingclips.smart.android.user.api.IWhiteListCallback;
import com.thingclips.smart.android.user.bean.User;
import com.thingclips.smart.android.user.bean.WhiteList;
import com.thingclips.smart.home.sdk.ThingHomeSdk;

public class ThingSmartApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ThingHomeSdk.init(this); // Inicializa el SDK

        ThingHomeSdk.getUserInstance().loginWithEmail("593", "ereyb@uteq.edu.ec", "1207337328Rey", new ILoginCallback() {
            @Override
            public void onSuccess(User user) {
                Toast.makeText(getApplicationContext(), "Login success:" +ThingHomeSdk.getUserInstance().getUser().getUsername(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(String code, String error) {
                Toast.makeText(getApplicationContext(), "code: " + code + "error:" + error, Toast.LENGTH_SHORT).show();
            }
        });


    }
}
