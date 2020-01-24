package com.dinaro.activities;


import android.content.SharedPreferences;
import androidx.databinding.DataBindingUtil;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.dinaro.R;

import com.dinaro.databinding.ActivityReceiptBinding;
import com.dinaro.utils.AppConstant;

import com.dinaro.utils.CardType;
import com.squareup.picasso.Picasso;


public class ReceiptActivity extends AppCompatActivity implements View.OnClickListener {
    private String name, title, date, amount, rating, image, meterNumber, getdate, transactionId;
    public SharedPreferences preferences;
    float ratingStars;
    public SharedPreferences.Editor editor;
    private String paymentType;
    private ActivityReceiptBinding binding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

       binding= DataBindingUtil. setContentView(this,R.layout.activity_receipt);

        preferences = getApplication().getSharedPreferences(AppConstant.SHARED_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        setListener();
        getIntentData();
        setData();


    }

    private void setListener() {
        binding. btnBackReceipt.setOnClickListener(this);
        binding.RatingBar .setEnabled(false);


    }


    private void setData() {


        String[] dat = date.split("-");
        String finalYear = dat[0];
        String month = dat[1];
        String tempYear = dat[2];

        String[] time=tempYear.split(" ");
        String allTime=time[1];

        String[] d = tempYear.split(" ") ;

        String allDate = d[0] + "-" + month + "-" + finalYear;




        binding.tvTime.setText(allTime);
       binding. tvName.setText(name);
       binding. tvDate1.setText(allDate);
        binding.tvAmount.setText("~ KES "+amount);
      binding.tvMeter .setText(meterNumber);
        Picasso.with(getApplication()).load(image).into(binding.ivPaidTo);
        binding.tvPaid.setText(title);
       binding. tvRate.setText("Rating: " + rating==null?"0":rating);
       binding. tvIdUtil.setText(transactionId);


        if (rating != null) {
            ratingStars = Float.parseFloat(rating);
            binding.RatingBar.setRating(ratingStars);
        }

        if(paymentType!=null) {
           binding. ivPaidToCard.setVisibility(View.VISIBLE);
            binding. ivPaidToCard.setImageResource(CardType.getDrawable(paymentType));
        }

    }

    private void getIntentData() {
        transactionId = getIntent().getStringExtra("transaction_id_utility");
        name = getIntent().getStringExtra("name_utility");
        title = getIntent().getStringExtra("title_utility");
        amount = getIntent().getStringExtra("amount_utility");
        date = getIntent().getStringExtra("date_utility");
        image = getIntent().getStringExtra("image_utility");
        meterNumber = getIntent().getStringExtra("bill_number_utility");
        rating = getIntent().getStringExtra("rating_utility");
        paymentType=getIntent().getStringExtra("payment_type");
    }





    @Override
    public void onClick(View view) {
        finish();

    }
}
