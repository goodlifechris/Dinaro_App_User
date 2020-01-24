package com.dinaro.fragments;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dinaro.R;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class FirstNewFragment extends Fragment {


    public FirstNewFragment() {
        // Required empty public constructor
    }


    ImageView imageViewBg;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_first_new, container, false);

        imageViewBg=view.findViewById(R.id.imageViewBg);

//        Picasso.with(view.getContext()).load().into(imageViewBg);




        return view;
    }


    @Override
    public void onStart() {
        super.onStart();
        Picasso
                .with(getContext())
                .load(R.drawable.img_intro_one)
                .resize(6000, 2000)
                .onlyScaleDown() // the image will only be resized if it's bigger than 6000x2000 pixels.
                .into(imageViewBg);
    }
}
