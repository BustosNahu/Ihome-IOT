package com.example.yourlocker.Fragments;

import static com.example.yourlocker.Utils.Utils.ROOM_ID;
import static com.example.yourlocker.Utils.Utils.USER_PATH;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yourlocker.Activities.RoomActivity;
import com.example.yourlocker.Adapter.RoomAdapter;
import com.example.yourlocker.Interface.JsonWeatherApiService;
import com.example.yourlocker.Model.Room;

import com.example.yourlocker.Model.ApiWeather.WeatherTemperature;
import com.example.yourlocker.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment implements RoomAdapter.ItemClickListener {


    View view;

    Menu menu;

    TextView tv_name;
    String uid;
    FirebaseAuth mAuth;
    DatabaseReference ref;



    private RoomAdapter.ItemClickListener onClickCallBack;


    private String[] item = {"Bed Room","Living Room","Kitchen", "Garage", "Home Outside"};

    private AutoCompleteTextView autoCompleteTextView;

    private TextInputLayout textInputLayout;

    private ArrayAdapter<String> itemAdapter;

    private TextView tx_temperature;


    private RecyclerView recyclerView;

    RoomAdapter myAdapter;
    RecyclerView.LayoutManager layoutManager;
    FloatingActionButton bt_add;
    List<Room> placeList = new ArrayList<>();

    NavHostFragment navHostFragment;
    NavController navController;

    AlertDialog dialog;

    private String roomType;

    private String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q={city name}&appid={API key}",
            apiKey = "cd112c72caa5a8db7501d5fdbac1fcba" ;

    private String cityName = "Buenos Aires";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        navHostFragment = (NavHostFragment) getActivity()
                .getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_container);
        navController = navHostFragment.getNavController();

        init();
        nameDataBaseRequest();
        getTemperature();

        return view;
    }////////////////////////////////////////////////////////////////////////////////FIN DEL ONCREATEVIEW/////////////////////////////////////////////////////////////////////////////


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        dataForRecyclerView();
        alertDialog();

        recyclerView = view.findViewById(R.id.list);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);

        recyclerView.setLayoutManager(layoutManager);
        myAdapter = new RoomAdapter(placeList, onClickCallBack);
        recyclerView.setAdapter(myAdapter);
        bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.show();

            }
        });


        myAdapter.setItemClickListener(new RoomAdapter.ItemClickListener() {
            @Override
            public void onItemClick(Room room) {

                Log.d("ROOM", "name: " + room.getRoom());
                Log.d("ROOM", "id: " + room.getId());
                Bundle myBundle = new Bundle();
                myBundle.putString(ROOM_ID, room.getId());
//                navController.navigate(R.id.roomFragment, myBundle);
                Intent i = new Intent(requireActivity(), RoomActivity.class);
                startActivity(i);
            }
        });

    }


    private void getTemperature(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonWeatherApiService weatherApiService = retrofit.create(JsonWeatherApiService.class);
        try {
            Call<WeatherTemperature> call = weatherApiService.getTemperature(cityName, apiKey, "metric");
            call.enqueue(new Callback<WeatherTemperature>() {
                @Override
                public void onResponse(Call<WeatherTemperature> call, Response<WeatherTemperature> response) {
                    if (response.isSuccessful()) {
                        WeatherTemperature weatherTemperature = response.body();
                        if (weatherTemperature != null) {
                            // Aquí puedes realizar las acciones necesarias con los datos del clima
                            Log.d("TEMPERATURE_REQUEST", "Temperature: " + weatherTemperature.main.temp);
                            double temp = weatherTemperature.main.temp;
                            int tempInt = (int) temp;
                            tx_temperature.setText(tempInt + "º");

                        }
                    } else {
                        // Aquí puedes manejar la respuesta no exitosa
                        Log.e("TEMPERATURE_REQUEST", "Response not successful. Code: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<WeatherTemperature> call, Throwable t) {
                    // Aquí puedes manejar el fallo de la solicitud
                    Log.e("TEMPERATURE_REQUEST", "Request failed. Error: " + t.getMessage());
                }
            });
        } catch (Exception e) {
            // Aquí puedes manejar cualquier excepción que se produzca
            Log.e("TEMPERATURE_REQUEST", "Exception: " + e.getMessage());
        }
    }


    /**
     * Method to initialice data to rooms recycler view
     */
    private void dataForRecyclerView() {
        ref.child(USER_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                placeList.clear();
                try {
                    Log.e("Room_PLACENAME", snapshot.child(uid).child("rooms").getValue().toString());

                    for (DataSnapshot dataSnapshot : snapshot.child(uid).child("rooms").getChildren()) {

                        String room = dataSnapshot.child("room").getValue(String.class);
                        String roomId = dataSnapshot.child("id").getValue(String.class);
                        String roomType = dataSnapshot.child("type").getValue(String.class);
                        Log.e("Room_ROOMS", room);

                        Room Room = new Room(room, roomId, roomType);
                        Log.e("RoomName", Room.toString());
                        placeList.add(Room);
                    }

                    recyclerView.setAdapter(myAdapter);


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
        bt_add = (FloatingActionButton) view.findViewById(R.id.bt_add);
        tx_temperature = view.findViewById(R.id.tx_temperature);

    }

    /**
     * Method to show an alert dialog and complete an editText to
     * create new room, with an Id objet.
     */
    private void alertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());


//        inflate new_room_custom_dialog view
        view = getLayoutInflater().inflate(R.layout.new_room_dialog, null);
        EditText et_new_room = (EditText) view.findViewById(R.id.et_new_room);
        Button bt_confirm = (Button) view.findViewById(R.id.bt_confirm);
        textInputLayout = view.findViewById(R.id.tx_input);
        autoCompleteTextView = view.findViewById(R.id.items_menu);


        itemAdapter = new ArrayAdapter<>(requireActivity(), R.layout.list_item_drop_down_menu, item);
        autoCompleteTextView.setAdapter(itemAdapter);



        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String item = parent.getItemAtPosition(position).toString();
                roomType = item.trim();


            }
        });

        bt_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String roomName = et_new_room.getText().toString().trim();
                String roomId = UUID.randomUUID().toString().trim();

                if(!roomName.isEmpty() && !roomType.isEmpty()){

                    Room myRoom = new Room(roomName , roomId, roomType);


                    ref.child(USER_PATH).child(uid).child("rooms")
                            .child(roomId)
                            .setValue(myRoom);

                    dialog.dismiss();
                }else {
                    Toast.makeText(requireActivity(), "Complete fields", Toast.LENGTH_SHORT).show();
                }


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
                tv_name.setText("Hi" + " " + name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public void onItemClick(Room room) {

    }
}
