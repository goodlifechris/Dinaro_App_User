package com.dinaro.activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dinaro.R;
import com.dinaro.utils.AppConstant;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ViewReceiptActivity extends AppCompatActivity {

    @BindView(R.id.buttonBack)
    Button buttonBack;
    @BindView(R.id.textViewID)
    TextView textViewID;
    @BindView(R.id.textViewTime)
    TextView textViewTime;
    @BindView(R.id.textViewName)
    TextView textViewName;
    @BindView(R.id.textViewAmount)
    TextView textViewAmount;

//    @BindView(R.id.imageViewIcon)
//    ImageView imageViewIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_receipt);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorTransparent)));
        getSupportActionBar().setElevation(0);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Intent intent = getIntent();
        textViewID.setText(Objects.requireNonNull(intent.getExtras()).getString(AppConstant.TRANSACTION_ID));
        textViewName.setText(Objects.requireNonNull(intent.getExtras()).getString(AppConstant.TRANSACTION_NAME));
//        textViewTime.setText(Objects.requireNonNull(intent.getExtras()).getString(AppConstant.TRANSACTION_TIME));
        textViewAmount.setText(Objects.requireNonNull(intent.getExtras()).getString(AppConstant.TRANSACTION_AMOUNT));
        textViewID.setText(Objects.requireNonNull(intent.getExtras()).getString(AppConstant.TRANSACTION_ID));
//        imageViewIcon.setImageResource(Integer.parseInt(Objects.requireNonNull(intent.getExtras()).getString(AppConstant.TRANSACTION_ICON)));

    }

    @OnClick(R.id.buttonBack)
    public void onViewClicked() {
        onBackPressed();
    }
}
