package com.example.yourlocker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    ImageView iv_back;
    EditText et_email, et_password;
    Button bt_log_in;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        iv_back = findViewById(R.id.iv_back);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        bt_log_in = findViewById(R.id.bt_log_in);

        et_email.setText("nbustosalexander@gmail.com");
        et_password.setText("123456");



        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        bt_log_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailUser = et_email.getText().toString().trim();
                String passUser = et_password.getText().toString().trim();

                if (emailUser.isEmpty() && passUser.isEmpty()) {
                    Toast.makeText(LoginActivity.this,"Please complete", Toast.LENGTH_SHORT).show();

                }else{
                    loginUser(emailUser, passUser);

                }

            }
        });





    }

    private void loginUser(String emailUser, String passUser) {
        mAuth.signInWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));

                }else{
                    Toast.makeText(LoginActivity.this,"Error", Toast.LENGTH_SHORT).show();

                }
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
             Toast.makeText(LoginActivity.this, "Error in logging in", Toast.LENGTH_SHORT).show();

            }
        });

    }

}