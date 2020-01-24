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
public class CheckRegisteredFragment extends Fragment {


    public CheckRegisteredFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_registered, container, false);
    }


    //navigation process
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize the NavController and make it refence the navhost
        NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);


        //initialize the buttons
        Button button=view.findViewById(R.id.is_registered_btn);
        Button button1=view.findViewById(R.id.not_registered_btn);

        Bundle bundle = new Bundle();
        bundle.putString("title", "Welcome back! Start enjoying smooooth payments again!");
        bundle.putInt("type",2);

        button.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_checkRegisteredFragment_to_passwordFragment,bundle));


        button1.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_checkRegisteredFragment_to_OTPFragment));
    }
}
