package com.example.yourlocker.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourlocker.Adapter.EspacioAdapter;
import com.example.yourlocker.Model.Espacio;
import com.example.yourlocker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements EspacioAdapter.ItemClicked {




    Menu menu;

    TextView tv_name;
    String uid;
    FirebaseAuth mAuth;
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    Button bt_add;
    ArrayList<Espacio> place;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);








//        bt_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NavController navController = Navigation.   findNavController(v);
//                navController.navigate(R.id.accountFragment);
//            }
//        });

//        recyclerView = v.findViewById(R.id.list);
//        recyclerView.setHasFixedSize(true);
//        layoutManager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
//        recyclerView.setLayoutManager(layoutManager);
//
//
//
//        place = new ArrayList<Espacio>();
//        place.add(new Espacio("LivingRoom"));
//        place.add(new Espacio("BedRoom"));
//        place.add(new Espacio("Garage"));
//        place.add(new Espacio("UpStairs"));
//        place.add(new Espacio("LivingRoom"));
//        place.add(new Espacio("BedRoom"));
//        place.add(new Espacio("Garage"));
//        place.add(new Espacio("UpStairs"));
//
//        myAdapter = new EspacioAdapter(requireContext(), place);
//        recyclerView.setAdapter(myAdapter);


//        bt_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                place.add(new Espacio("BedRoom"));
//                myAdapter.notifyDataSetChanged();
//            }
//        });




        return v;
    }////////////////////////////////////////////////////////////////////////////////FIN DEL ONCREATEVIEW//////////////////////////////////////////////////////////////////////////////

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        tv_name = view.findViewById(R.id.tv_name);







        ///////////////////////////////Tomar nombre de la DB y escribirlo en pantalla///////////////////////////////////
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Usuarios registrados").addValueEventListener(new ValueEventListener() {
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
     * @param index
     */
    @Override
    public void onItemClick(int index) {
        Toast.makeText(getContext(), "Lugar" + " " + place.get(index).getLugar(), Toast.LENGTH_SHORT).show();
    }
}
