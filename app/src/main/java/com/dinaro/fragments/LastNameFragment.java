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
public class LastNameFragment extends Fragment {


    public LastNameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_last_name, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initiate the navController and make it reference the navhost
        NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);


        //initialize the buttons
        Button button=view.findViewById(R.id.next_btn);
        Button button1=view.findViewById(R.id.cancel_btn);


        //set Onclick listeners to the buttons
//        button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_lastNameFragment_to_emailAddressFragment));

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }
}
