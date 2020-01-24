package com.dinaro.fragments;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.dinaro.R;
import com.dinaro.activities.HomeActivity;
import com.dinaro.utils.AppConstant;


/**
 * A simple {@link Fragment} subclass.
 */
public class PasswordFragment extends Fragment {

    TextView textviewTitle,textviewForgotPass;

    public PasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_password, container, false);

        textviewTitle = (TextView) view.findViewById(R.id.textviewTitle);
        textviewForgotPass = (TextView) view.findViewById(R.id.textviewForgotPass);


        return view;
    }



    public void updateTitle(){

        if (getArguments().getInt("type")== 1){
            Spannable wordtoSpan = new SpannableString("Start enjoying smooooth payments!");

//            "Start enjoying smooooth payments!
            wordtoSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 15, 23 , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textviewTitle.setText(wordtoSpan);
            textviewForgotPass.setVisibility(View.GONE);

        }else{
            Spannable wordtoSpan = new SpannableString("Start enjoying smooooth payments again!");
//            "Start enjoying smooooth payments again!
            wordtoSpan.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorAccent)), 15, 23 , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textviewTitle.setText(wordtoSpan);
            textviewForgotPass.setVisibility(View.VISIBLE);

        }

    }

    //navigation process
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//initialize the navController and make it reference the navhost
        NavController navController= Navigation.findNavController(getActivity(),R.id.nav_host_fragment);

        updateTitle();


    }
}
