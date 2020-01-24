package com.dinaro.fragments;


import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.dinaro.R;
import com.dinaro.activities.OnClickPaymentActivity;
import com.google.android.material.appbar.AppBarLayout;

import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    public HomeFragment() {
        // Required empty public constructor
    }

    ImageView imageViewAirtel,imageViewSteers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        AppBarLayout appBarLayout=view.findViewById(R.id.appBarLayout);
        LinearLayout linearlayoutMajor=view.findViewById(R.id.linearlayoutMajor);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

                linearlayoutMajor.setAlpha(1.0f - Math.abs(verticalOffset / (float)
                        appBarLayout.getTotalScrollRange()));


            }
        });

        imageViewSteers=view.findViewById(R.id.imageViewSteers);
        imageViewSteers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OnClickPaymentActivity.class);
                intent.putExtra("TYPE", "one");
                startActivity(intent);



            }
        });
        imageViewAirtel=view.findViewById(R.id.imageViewAirtel);
        imageViewAirtel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OnClickPaymentActivity.class);
                intent.putExtra("TYPE", "two");
                startActivity(intent);

            }
        });





        return view;
    }


}
