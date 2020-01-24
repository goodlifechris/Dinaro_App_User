package com.dinaro.activities;

import android.content.SharedPreferences;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dinaro.R;
import com.dinaro.databinding.ActivityRecieptRestaurantBinding;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CardType;
import com.squareup.picasso.Picasso;


public class RecieptActivityRestaurant extends AppCompatActivity implements View.OnClickListener {
    private String name, title, date, amount, rating,image,meterNumber,getdate,transactionId;
    private String paymentType;
    public SharedPreferences preferences;
    float ratingStars;
    public SharedPreferences.Editor editor;
    private ActivityRecieptRestaurantBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding= DataBindingUtil.   setContentView(this,R.layout.activity_reciept_restaurant);
        preferences = getApplication().getSharedPreferences(AppConstant.SHARED_PREF, MODE_PRIVATE);
        editor = preferences.edit();


        setistener();
       getIntentData();
       setData();

    }
    private void setData() {

        String[] dat = date.split("-");
        String finalYear = dat[0];
        String month = dat[1];
        String tempYear = dat[2];
        String[] time=tempYear.split(" ");
        String allTime=time[1];

        String[] d = tempYear.split(" ");
        String allDate = d[0] + "-" + month + "-" + finalYear;

       binding. tvDate1Res.setText(allDate);
        binding.tvTime.setText(allTime);

        binding. tvAmount.setText("~ KES "+amount);
        Picasso.with(getApplication()).load(image).into(binding.ivPaidToRes);
        binding. tvPaidRes.setText(title);
        binding. tvRate.setText("Rating: " + rating);
        binding. tvId.setText(transactionId);

        if(paymentType!=null) {
            binding.ivPaidToCard.setVisibility(View.VISIBLE);
            if(paymentType!=null) {
              binding.ivPaidToCard.setVisibility(View.VISIBLE);
                binding.ivPaidToCard.setImageResource(CardType.getDrawable(paymentType));
            }

        }

        if (rating != null) {
            ratingStars = Float.parseFloat(rating);
            binding.RatingBar.setRating(ratingStars);
        }

    }

    private void getIntentData() {
        transactionId=getIntent().getStringExtra("transaction_id_res");
        title = getIntent().getStringExtra("title_res");
        amount = getIntent().getStringExtra("amount_res");
        date = getIntent().getStringExtra("date_res");
        image = getIntent().getStringExtra("image_res");
        rating=getIntent().getStringExtra("rating_res");
        paymentType=getIntent().getStringExtra("payment_type");

    }

    private void setistener() {
       binding. btnBackReceipt.setOnClickListener(this);
       binding.RatingBar .setEnabled(false);
    }

    @Override
    public void onClick(View v) {
        finish();

    }
}
