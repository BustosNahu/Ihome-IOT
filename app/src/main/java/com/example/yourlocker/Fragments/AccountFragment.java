package com.example.yourlocker.Fragments;

import static com.example.yourlocker.Utils.Utils.USER_PATH;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yourlocker.Model.UserDto;
import com.example.yourlocker.R;
import com.example.yourlocker.Activities.WelcomeActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountFragment extends Fragment {

    View view;
    Button button;
    String uid;
    FirebaseAuth mAuth;

    UserDto user;
    TextView tvfullName;
    LinearLayout edit_profile, l_layout_terms, l_layout_logOut;
    DatabaseReference ref;

    private ImageView imProfile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_account, container, false);

        NavHostFragment navHostFragment =
                (NavHostFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_container);
        NavController navController = navHostFragment.getNavController();

        initView();

        ref = FirebaseDatabase.getInstance().getReference();
        ref.child(USER_PATH).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                user =  snapshot.child(uid).getValue(UserDto.class);
                tvfullName.setText(user.getNameUser());

                Glide.with(requireContext()).load(user.getProfileUrl()).circleCrop().
                        into(imProfile);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //navController.navigate(R.id.editProfileFragment);
                //Intent i = new Intent(getActivity(), EditProfileActivity.class);
                navController.navigate(R.id.editProfileActivity);

            }
        });
        l_layout_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.termsAndConditionsFragment);
            }
        });

        l_layout_logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), WelcomeActivity.class));
            }
        });

//        getUserInfo();


        return view;
    }

    /**
     * Metodo inicializa las vistas
     */
    private void initView() {
        edit_profile = view.findViewById(R.id.l_layout_edit);
        imProfile =  view.findViewById(R.id.imProfile);
        l_layout_terms = view.findViewById(R.id.l_layout_terms);
        l_layout_logOut = view.findViewById(R.id.l_layout_logOut);
        tvfullName = view.findViewById(R.id.tvfullName);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getCurrentUser().getUid();
    }


}