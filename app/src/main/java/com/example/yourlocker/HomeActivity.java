package com.example.yourlocker;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

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

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {


    Menu menu;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;



    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentContainerView);

        bottomNavigationView = findViewById(R.id.bottomNavView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.navHome);


    }/////////////////////////////////////////////////////////////////////////////////FIN DEL ONCREATE/////////////////////////////////////////////////////////////////////////////////////

    HomeFragment homeFragment = new HomeFragment();
    FavouriteFragment favouriteFragment = new FavouriteFragment();
    AccountFragment accountFragment = new AccountFragment();

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.navHome:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, homeFragment).commit();
                return true;

            case R.id.navAccount:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, accountFragment).commit();
                return true;

            case R.id.navFavourite:

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, favouriteFragment).commit();
                return true;


        }
        return false;
    }
}