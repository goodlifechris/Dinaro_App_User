package com.dinaro.activities;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dinaro.R;
import com.squareup.picasso.Picasso;

public class OnClickPaymentActivity extends AppCompatActivity {


   CardView cardViewAccountNo;
           TextView textViewName;
                   ImageView imageIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_click_payment);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorTransparent)));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cardViewAccountNo =(CardView) findViewById(R.id.cardViewAccountNo);
        textViewName =(TextView) findViewById(R.id.textViewName);
        imageIcon =(ImageView) findViewById(R.id.imageIcon);

        String type = getIntent().getStringExtra("TYPE");

        if (type.equalsIgnoreCase("one")){
            textViewName.setText("Steers");

            Picasso.with(this).load(R.drawable.steers)
                    .into(imageIcon);
            cardViewAccountNo.setVisibility(View.GONE);
        }else{
            textViewName.setText("Airtel");
            Picasso.with(this).load(R.drawable.airtel)

                    .into(imageIcon);
            cardViewAccountNo.setVisibility(View.VISIBLE);

        }




    }

}
