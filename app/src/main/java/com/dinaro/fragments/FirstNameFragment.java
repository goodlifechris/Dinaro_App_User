package com.dinaro.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.dinaro.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FirstNameFragment extends Fragment {


    public FirstNameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_name, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //initiate the Navigation Controller and make it have a reference to the nav_host
        NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);


        //initiate th buttons
        Button button=view.findViewById(R.id.next_btn);
        Button button1=view.findViewById(R.id.cancel_btn);

        //attach onclick listeners to the buttons

//        button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_firstNameFragment2_to_lastNameFragment));

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

    }
}
