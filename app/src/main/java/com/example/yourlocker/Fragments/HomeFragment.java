package com.example.yourlocker.Fragments;

import static com.example.yourlocker.Utils.Utils.USER_PATH;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourlocker.Adapter.RoomAdapter;
import com.example.yourlocker.Model.Room;
import com.example.yourlocker.Model.StringList;
import com.example.yourlocker.Model.UserDto;
import com.example.yourlocker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements RoomAdapter.ItemClicked {


    View view;

    Menu menu;

    TextView tv_name;
    String uid;
    FirebaseAuth mAuth;
    DatabaseReference ref;


    private RecyclerView recyclerView;

    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    Button bt_add;
    List<Room> placeList = new ArrayList<>();

    private HomeFragment binding;

    AlertDialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        NavHostFragment navHostFragment =
                (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container);
        NavController navController = navHostFragment.getNavController();

        init();
        nameDataBaseRequest();

        //boton de añadir dispositivo
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.accountFragment);
            }
        });

//        recyclerView = view.findViewById(R.id.list);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//
//



        return view;
    }////////////////////////////////////////////////////////////////////////////////FIN DEL ONCREATEVIEW/////////////////////////////////////////////////////////////////////////////


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataInitialice();
        alertDialog();

        recyclerView = view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);
//      layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new RoomAdapter(requireActivity(), placeList);
        recyclerView.setAdapter(myAdapter);

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

            }
        });
    }

    /**
     * Method to initialice data to rooms recycler view
     */
    private void dataInitialice() {

        ref.child(USER_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                placeList.clear();

                try {
                    Log.e("Room_PLACENAME", snapshot.child(uid).child("espacioDispositivos").getValue().toString());
                    for (DataSnapshot dataSnapshot : snapshot.child(uid).child("espacioDispositivos").getChildren()) {

                        String room = dataSnapshot.getValue(String.class);
                        Log.e("Room_ROOMS", room);

                        Room Room = new Room(room);
                        Log.e("Room", Room.toString());
                        placeList.add(Room);
                    }
                    recyclerView.setAdapter(new RoomAdapter(requireActivity(), placeList));

                }catch (Exception e){
                    Toast.makeText(getActivity(), "Error en base de datos", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error en base de datos", Toast.LENGTH_SHORT).show();

            }
        });


    }

    /**
     * This method init views and instance database
     */
    private void init() {
        ref = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        tv_name = view.findViewById(R.id.tv_name);
        bt_add = (Button) view.findViewById(R.id.bt_add);
    }

    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());

        //inflate new_room_custom_dialog view
        view = getLayoutInflater().inflate(R.layout.new_room_dialog, null);
        EditText et_new_room = (EditText) view.findViewById(R.id.et_new_room);
        Button bt_confirm = (Button) view.findViewById(R.id.bt_confirm);

        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newRoomName = et_new_room.getText().toString().trim();
                ref.child(USER_PATH).child(uid).child("espacioDispositivos")
                        .push()
                        .child("room")
                        .setValue(newRoomName);


                dialog.dismiss();
            }
        });
        builder.setView(view);
        dialog = builder.create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    /**
     * This method make a database request to get the user name, and it shows in main screen
     */
    private void nameDataBaseRequest() {
        ref.child(USER_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String name = snapshot.child(uid).child("nameUser").getValue().toString();
                tv_name.setText("Welcome" + " " + name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    /**
     * FUNCION para identificar que item del RECYCLER VIEW se esta clickeando
     *
     * @param index
     */
    @Override
    public void onItemClick(int index) {
        Toast.makeText(getContext(), "Lugar" + " " + placeList.get(index).getLugar(), Toast.LENGTH_SHORT).show();
    }
}
