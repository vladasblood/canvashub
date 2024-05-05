package com.example.canvashub;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.appcompat.app.AppCompatActivity;

import com.example.canvashub.session.SessionManager;

public class splashscreen extends AppCompatActivity {

    private Handler handler;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        handler = new Handler(Looper.getMainLooper());
        sessionManager = new SessionManager(this);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkSession();
                //getstartedactivity();
            }
        }, 2500);
    }

    private void checkSession() {
        if(sessionManager.isLoggedIn()) {
            Intent intent = new Intent(getApplicationContext(), home.class);
//            intent.putExtra("customer_id", sessionManager.getCustomerId());
            startActivity(intent);
        } else {
            //Intent intent = new Intent(getApplicationContext(), login.class);
            Intent intent = new Intent(getApplicationContext(), welcomescreen.class);
            startActivity(intent);
        }
        finish();
    }

    public void getstartedactivity() {
        Intent intent = new Intent(getApplicationContext(), welcomescreen.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Remove all callbacks and messages
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}