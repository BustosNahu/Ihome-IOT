package com.example.yourlocker.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.yourlocker.R;


public class FavouriteFragment extends Fragment {



     private ImageView iv_people;
      View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favourite, container, false);

        //initViews();
        //initUi();

        return view;
    }

    private void initUi() {
        iv_people.setBackgroundResource(R.drawable.ic_userpc);

    }

    private void initViews() {
        iv_people = view.findViewById(R.id.iv_people);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //NavController navController = Navigation.findNavController(view);

    }
}