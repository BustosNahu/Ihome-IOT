package com.example.yourlocker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourlocker.Adapter.EspacioAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements EspacioAdapter.ItemClicked {

    TextView iv_name;
    String uid;
    FirebaseAuth mAuth;
    RecyclerView recyclerView;
    RecyclerView.Adapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    Button bt_add;

    ArrayList<Espacio> place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        iv_name = findViewById(R.id.iv_name);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
        bt_add = findViewById(R.id.bt_add);

        recyclerView = findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);

        //layoutManager = new LinearLayoutManager(this);
        //layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManager = new GridLayoutManager(this,2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        place = new ArrayList<Espacio>();
        place.add(new Espacio("LivingRoom"));
        place.add(new Espacio("BedRoom"));
        place.add(new Espacio("Garage"));
        place.add(new Espacio("UpStairs"));


        myAdapter = new EspacioAdapter(this, place);

        recyclerView.setAdapter(myAdapter);

        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                place.add(new Espacio("BedRoom"));
                myAdapter.notifyDataSetChanged();
            }
        });

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Usuarios registrados").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child(uid).child("nameUser").getValue().toString();
                iv_name.setText("Welcome to Ihome" + " " + name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }/////////////////////////FIN DEL ONCREATE/////////////////////////////

    /**
     * FUNCION para identificar que item es el que se esta clickeando
     * @param index
     */
    @Override
    public void onItemClick(int index) {
        Toast.makeText(this, "Lugar" + " " + place.get(index).getLugar(), Toast.LENGTH_SHORT).show();
    }
}