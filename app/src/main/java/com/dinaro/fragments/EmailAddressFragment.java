package com.dinaro.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.dinaro.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class EmailAddressFragment extends Fragment {


    public EmailAddressFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_email_address, container, false);
    }


    //navigation process
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //initialize the navController and make it refence the navhost
        NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);


        //attach onclicklisteners to the buttons
//        button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_emailAddressFragment_to_passwordFragment));


    }
}
