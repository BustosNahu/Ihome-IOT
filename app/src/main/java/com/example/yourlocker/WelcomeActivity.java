package com.example.yourlocker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.yourlocker.LoginActivity;
import com.example.yourlocker.R;
import com.example.yourlocker.RegisterActivity;

public class WelcomeActivity extends AppCompatActivity {

    Button btn_login_screen, btn_singin_screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btn_login_screen = findViewById(R.id.btn_login_screen);
        btn_singin_screen = findViewById(R.id.btn_singin_screen);

        btn_login_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        btn_singin_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), RegisterActivity.class);
                startActivity(i);
            }
        });


    }
}