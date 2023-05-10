package com.example.yourlocker.Fragments;

import static com.example.yourlocker.Utils.Utils.ROOM_ID;
import static com.example.yourlocker.Utils.Utils.USER_PATH;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourlocker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RoomFragment extends Fragment {
    private int mId;
    private String roomId;

    private DatabaseReference ref;
    private FirebaseAuth mAuth;
    private String uid;
    private View view;

    TextView tv_name;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle myBundle = getArguments();
        if (myBundle != null) {
         roomId = myBundle.getString(ROOM_ID);
            Log.e("ROOM_ID_LOLO", "ID: " + myBundle.getString(ROOM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_room, container, false);

        initDB();
        initUi();
        showRoomName();


        return view;
    }

    private void showRoomName() {
        ref.child(USER_PATH).child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try {
                    String roomName = snapshot.child("rooms")
                            .child(roomId)
                            .child("room")
                            .getValue()
                            .toString();

                    tv_name.setText(roomName);
                    Log.d("ROOM_NAME", "NAME: " + roomName);
                }catch (Exception e){
                    Toast.makeText(requireActivity(), "Error en la base de datos", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initUi() {
        tv_name = view.findViewById(R.id.tv_name);


    }

    private void initDB() {
        mAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        uid = mAuth.getCurrentUser().getUid();
    }
}