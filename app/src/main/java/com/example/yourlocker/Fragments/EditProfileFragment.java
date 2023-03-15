//package com.example.yourlocker.Fragments;
//
//import static android.app.Activity.RESULT_OK;
//
//import android.app.ProgressDialog;
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.fragment.app.Fragment;
//import androidx.navigation.NavController;
//import androidx.navigation.fragment.NavHostFragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.example.yourlocker.Model.ReadWriteUserDetails;
//import com.example.yourlocker.R;
//import com.example.yourlocker.RegisterActivity;
//import com.example.yourlocker.saveNewDataUser;
//import com.google.android.gms.tasks.Continuation;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;
//import com.google.firebase.storage.StorageTask;
//import com.google.firebase.storage.UploadTask;
//import com.squareup.picasso.Picasso;
//import com.theartofdev.edmodo.cropper.CropImage;
//
//import java.net.URI;
//import java.util.HashMap;
//import java.util.Map;
//
//import de.hdodenhof.circleimageview.CircleImageView;
//
//public class EditProfileFragment extends Fragment {
//
//
//    String uid;
//    FirebaseAuth mAuth;
//    EditText etName, etEmail, etPassword, etAdress, etNumberAdress;
//
//    Button btnSaveChanges;
//    LinearLayout arrow_back, l_layout_save, l_layout_delete;
//
//    TextView tvChangeProfile;
//    View view;
//    DatabaseReference databaseReference;
//
//    private CircleImageView imProfile;
//
//    private StorageReference mStorage;
//
//    private static final int GALLERY_INTENT = 1;
//
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
//
//        //Init-----------
//        //Navegation component
//        NavHostFragment navHostFragment =
//                (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container);
//        NavController navController = navHostFragment.getNavController();
//
//        //Storage
//
//
//        //DataBase
//        mAuth = FirebaseAuth.getInstance();
//        uid = mAuth.getCurrentUser().getUid();
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//
//        //layout components
//        arrow_back = view.findViewById(R.id.l_layout_back);
//        etName = view.findViewById(R.id.etName);
//        etEmail = view.findViewById(R.id.etEmail);
//        etPassword = view.findViewById(R.id.etPassword);
//        etAdress = view.findViewById(R.id.etAdress);
//        etNumberAdress = view.findViewById(R.id.etNumberAdress);
//        btnSaveChanges = view.findViewById(R.id.btnSaveChanges);
//        l_layout_save = view.findViewById(R.id.l_layout_save);
//        l_layout_delete = view.findViewById(R.id.l_layout_delete);
//        tvChangeProfile = view.findViewById(R.id.tvChangeProfile);
//        imProfile = view.findViewById(R.id.imProfile);
//
//        //Storage
//        mStorage = FirebaseStorage.getInstance().getReference();
//
//
//        l_layout_save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //uploadProfileImage();
//            }
//        });
//
//        tvChangeProfile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               Intent intent = new Intent(Intent.ACTION_PICK);
//               intent.setType("image/*");
//               getActivity().startActivityForResult(intent, GALLERY_INTENT);
//
//
//            }
//        });
//
//
//
//        //--------------------------ImageView para retroceder a la pantalla anterior----------------
//        arrow_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                navController.navigate(R.id.accountFragment);
//            }
//        });
//
//
//        //----------------------------------Toma datos del realtimeDB--------------------------------
//        databaseReference.child("Usuarios registrados").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String name = snapshot.child(uid).child("nameUser").getValue().toString();
//                String email = snapshot.child(uid).child("emailUser").getValue().toString();
//                String pass = snapshot.child(uid).child("passUser").getValue().toString();
//                String adress = snapshot.child(uid).child("addressUser").getValue().toString();
//                String numberAdress = snapshot.child(uid).child("numberAdressUser").getValue().toString();
//
//                etName.setText(name);
//                etEmail.setText(email);
//                etPassword.setText(pass);
//                etAdress.setText(adress);
//                etNumberAdress.setText(numberAdress);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        //-----------------------------Cambiar datos al editar el perfil----------------------------
//        btnSaveChanges.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String nameUser = etName.getText().toString().trim();
//                String emailUser = etEmail.getText().toString().trim();
//                String passUser = etPassword.getText().toString().trim();
//                String addressUser = etAdress.getText().toString().trim();
//                String numberAdressUser = etNumberAdress.getText().toString().trim();
//
//                if (nameUser.isEmpty() && emailUser.isEmpty() && passUser.isEmpty() && addressUser.isEmpty() && numberAdressUser.isEmpty()) {
//                    Toast.makeText(getContext(), "Complete all fields", Toast.LENGTH_SHORT).show();
//                } else {
//                    //funcion para registro
//                    saveNewDataUser(nameUser, emailUser, passUser, addressUser, numberAdressUser);
//                    navController.navigate(R.id.accountFragment);
//                }
//            }
//        });
//
//        return view;
//    }//-------------------------------------------FIN DEL ONCREATEVIEW------------------------------
//
////    @Override
////    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
////        super.onActivityResult(requestCode, resultCode, data);
////
////
////
////
////        //chequeamos que la foto haya sido tomada correctamente
////        if(requestCode == GALLERY_INTENT && requestCode == RESULT_OK){
////
////            Uri uri = data.getData();
////
////            StorageReference filePath = mStorage.child("profilePicture").child(uri.getLastPathSegment());
////
////            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
////                @Override
////                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
////                    Toast.makeText(getContext(), "Subida correctamente", Toast.LENGTH_SHORT).show();
////                }
////            });
////
////        }
////    }
//
//
//
//    /**
//     * FUNCION para guardar nuevos datos del usuario al momento de editarlos
//     *
//     * @param nameUser
//     * @param emailUser
//     * @param passUser
//     * @param addressUser
//     * @param numberAdressUser
//     */
//    private void saveNewDataUser(String nameUser, String emailUser, String passUser, String addressUser, String numberAdressUser) {
//
//        saveNewDataUser saveNewDataUser = new saveNewDataUser(nameUser, emailUser, passUser, addressUser, numberAdressUser);
//        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Usuarios registrados");
//
//        //Cargar los nuevos datos en el usuario correspondiente, mediante su id
//        referenceProfile.child(uid).setValue(saveNewDataUser).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    //enviar mail de verificacion
//                    Toast.makeText(getContext(), "Save it with successful", Toast.LENGTH_SHORT).show();
//
//                } else {
//                    Toast.makeText(getContext(), "Save data failed", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//    }
//}