package com.example.yourlocker;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.yourlocker.Fragments.AccountFragment;
import com.example.yourlocker.Fragments.FavouriteFragment;
import com.example.yourlocker.Fragments.HomeFragment;
import com.example.yourlocker.databinding.ActivityHomeBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
//implements BottomNavigationView.OnNavigationItemSelectedListener

    Menu menu;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    NavController navController;

    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment_container);
        bottomNavigationView = findViewById(R.id.bottomNavView);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


    }/////////////////////////////////////////////////////////////////////////////////FIN DEL ONCREATE/////////////////////////////////////////////////////////////////////////////////////
}