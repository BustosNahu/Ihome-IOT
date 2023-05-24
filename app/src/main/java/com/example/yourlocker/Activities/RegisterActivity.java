package com.example.yourlocker.Activities;

import static com.example.yourlocker.Utils.Utils.USER_PATH;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.yourlocker.Activities.HomeActivity;
import com.example.yourlocker.Model.Room;
import com.example.yourlocker.Model.UserDto;
import com.example.yourlocker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    ImageView iv_back;
    TextView et_name,et_email, et_password, et_password_confirm, et_address,et_NumberAdress, et_floor_dpto;
    Button bt_singin;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        iv_back = findViewById(R.id.iv_back);
        et_name = findViewById(R.id.et_name);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_password);
        et_password_confirm = findViewById(R.id.et_password_confirm);
        et_address = findViewById(R.id.et_address);
        et_NumberAdress = findViewById(R.id.et_NumberAdress);
        et_floor_dpto = findViewById(R.id.et_floor_dpto);
        bt_singin = findViewById(R.id.bt_singin);
        



        bt_singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameUser = et_name.getText().toString().trim();
                String emailUser = et_email.getText().toString().trim();
                String passUser = et_password.getText().toString().trim();
                String confirmPassUser = et_password_confirm.getText().toString().trim();
                String addressUser = et_address.getText().toString().trim();
                String numberAdressUser = et_NumberAdress.getText().toString().trim();
                String floorDptoUser = et_floor_dpto.getText().toString().trim();
                String profileUrl = "";
                String rooms = "";

                if(nameUser.isEmpty() && emailUser.isEmpty() && passUser.isEmpty() && confirmPassUser.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "Complete fields",Toast.LENGTH_SHORT).show();
                }else if (passUser.equals(confirmPassUser)){
                    //funcion para registro
                    registerUser(nameUser,emailUser , passUser, addressUser, numberAdressUser, rooms, profileUrl);
                }

            }
        });


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void registerUser(String nameUser, String emailUser, String passUser, String addressUser, String numberAdressUser,  String rooms, String profileUrl ) {
        Log.d("USER_DETAILS", "name: " + nameUser);
        Log.d("USER_DETAILS", "email: " + emailUser);
        Log.d("USER_DETAILS", "password: " + passUser);
        Log.d("USER_DETAILS", "address: " + addressUser);
        Log.d("USER_DETAILS", "numberAdress: " + numberAdressUser);
        Log.d("USER_DETAILS", "rooms: " + rooms);
        Log.d("USER_DETAILS", "url: " + profileUrl);


        mAuth.createUserWithEmailAndPassword
                        (emailUser,
                                passUser)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                Log.d("USER_DETAILS", "Data base result:" + task);
                if(task.isSuccessful()) {
                    FirebaseUser firebaseUser = mAuth.getCurrentUser();

                    //Ingresar datos en firebase realtime database
                    UserDto writeUserDetails = new UserDto(nameUser, emailUser, passUser, addressUser, numberAdressUser, rooms, profileUrl);

                    //Extrayendo referencia de usuario de la base de datos "Usuarios registrados"
                    DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference(USER_PATH);
                    referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()) {
                                //enviar mail de verificacion
                                firebaseUser.sendEmailVerification();
                                Toast.makeText(RegisterActivity.this, "Succesfull register, please verify your email", Toast.LENGTH_SHORT).show();

//                                Open home's activity, after register == successful
                                Intent intent = new Intent(RegisterActivity.this, HomeActivity.class);

                                intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP
                                        | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                        | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();

                            }else{
                                Toast.makeText(RegisterActivity.this, "Register failed. Please try again", Toast.LENGTH_SHORT).show();
                            }



                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "Error de base de datos", Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}