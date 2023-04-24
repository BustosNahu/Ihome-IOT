package com.example.yourlocker.Activities;

import static com.example.yourlocker.Utils.Utils.USER_PATH;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yourlocker.Model.UserDto;
import com.example.yourlocker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class EditProfileActivity extends AppCompatActivity {

    String uid;
    FirebaseAuth mAuth;
    EditText etName, etEmail, etPassword, etAdress, etNumberAdress;

    // Uri indicates, where the image will be picked from
    Uri profilePhotoUri;
    Button btnSaveChanges;
    LinearLayout arrow_back, l_layout_save, l_layout_delete;

    TextView tvChangeProfile;
    View view;

    private ImageView imProfile;

    private StorageReference storageReference;

    FirebaseStorage storage;
    DatabaseReference databaseReference;

    UserDto user;

    // request code
    private static final int PICK_IMAGE_REQUEST = 22;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        initViews();
        initDB();
        getUserProfile();

        tvChangeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        l_layout_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });

        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UpdateUserProfile();
            }
        });


        arrow_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TERMINAR FLECHA PARA REGRESAR AL ACCOUNT FRAGMENT
            }
        });


    }//-------------------------------------------FIN DEL ONCREATE---------------------------------------------------------------

    /**
     * Metodo para actualizar datos del usuario
     */
    private void UpdateUserProfile() {
        String nameUser = etName.getText().toString().trim();
        String emailUser = etEmail.getText().toString().trim();
        String passUser = etPassword.getText().toString().trim();
        String addressUser = etAdress.getText().toString().trim();
        String numberAdressUser = etNumberAdress.getText().toString().trim();
        HashMap hashMap = new HashMap<>();
        hashMap.put("nameUser", nameUser);
        hashMap.put("emailUser", emailUser);
        hashMap.put("passUser", passUser);
        hashMap.put("addressUser", addressUser);
        hashMap.put("numberAdressUser", numberAdressUser);

        databaseReference.child(USER_PATH).child(uid).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    Toast.makeText(EditProfileActivity.this, "Save it with successful", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(EditProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /**
     * Metodo para guardar imagen seleccionada desde galeria en foto del perfil del usuario
     */
    private void saveImage() {

        if (profilePhotoUri != null) {

            // Code for showing progressDialog while uploading
            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            StorageReference ref
                    = storageReference.child(
                    "images/"
                            + UUID.randomUUID().toString());

            // adding listeners on upload
            // or failure of image
            ref.putFile(profilePhotoUri).addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {


                                    // Image uploaded successfully
                                    // Dismiss dialog
                                    progressDialog.dismiss();
                                    Toast.makeText(EditProfileActivity.this,
                                            "Image Uploaded!!",
                                            Toast.LENGTH_SHORT).show();


                                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            HashMap hashMap = new HashMap<>();
                                            hashMap.put("profileUrl", uri.toString());
                                            databaseReference.child(USER_PATH).child(uid).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                                @Override
                                                public void onSuccess(Object o) {
                                                    getUserProfile();
                                                }
                                            });

                                        }
                                    });

                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            // Error, Image not uploaded
                            progressDialog.dismiss();
                            Toast.makeText(EditProfileActivity.this,
                                    "Failed " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                // Progress Listener for loading
                                // percentage on the dialog box
                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int) progress + "%");
                                }
                            });
        }
    }


    /**
     * Metodo para obtener y cargar datos del usuario en perfil
     * @return retorna un objeto llamado user, instanciado de la clase UserDto, la cual contiene todos los datos
     * del usuario, getters and setters
     */
    private UserDto getUserProfile() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(USER_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user = snapshot.child(uid).getValue(UserDto.class);
                etName.setText(user.getNameUser());
                etEmail.setText(user.getEmailUser());
                etPassword.setText(user.getPassUser());
                etAdress.setText(user.getAddressUser());
                etNumberAdress.setText(user.getNumberAdressUser());

                Glide.with(EditProfileActivity.this).load(user.getProfileUrl()).circleCrop().into(imProfile);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return user;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        // checking request code and result code
        // if request code is PICK_IMAGE_REQUEST and
        // resultCode is RESULT_OK
        // then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            // Get the Uri of data
            profilePhotoUri = data.getData();
            try {

                // Setting image on image view using Bitmap
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                profilePhotoUri);
                imProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Log the exception
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo para abrir la galeria y seleccionar una imagen
     */
    private void openGallery() {
        // Defining Implicit Intent to mobile gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    /**
     * Metodo crea instancia con base de datos
     */
    private void initDB() {
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();


    }

    /**
     * Metodo para iniciar las vistas del layout
     */
    private void initViews() {
        arrow_back = findViewById(R.id.l_layout_back);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etAdress = findViewById(R.id.etAdress);
        etNumberAdress = findViewById(R.id.etNumberAdress);
        btnSaveChanges = findViewById(R.id.btnSaveChanges);
        l_layout_save = findViewById(R.id.l_layout_save);
        l_layout_delete = findViewById(R.id.l_layout_delete);
        tvChangeProfile = findViewById(R.id.tvChangeProfile);
        imProfile = findViewById(R.id.imProfile);
    }

}