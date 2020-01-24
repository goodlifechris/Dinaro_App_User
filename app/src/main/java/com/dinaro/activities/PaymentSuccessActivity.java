package com.dinaro.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dinaro.R;
import com.dinaro.utils.AppConstant;

public class PaymentSuccessActivity extends AppCompatActivity {
    private TextView textView;
    private Button llSuccess;
    public RatingBar ratingBar;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_payment_suucess);
        preferences = getApplication().getSharedPreferences(AppConstant.SHARED_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        final MediaPlayer sound=MediaPlayer.create( this,R.raw.success );
        sound.start();
        textView = findViewById(R.id.tv_successtext);
        ratingBar = findViewById(R.id.RatingBar);
        llSuccess = findViewById(R.id.btnDone);
        llSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            //    getRating = String.valueOf(ratingBar.getRating());
                Intent intent = new Intent(PaymentSuccessActivity.this, DashBoardActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("1", "second fragment");
                intent.putExtras(bundle);
                startActivity(intent);
                finishAffinity();
               // setRating();
//                Toast.makeText(context, "Please Wait.", Toast.LENGTH_SHORT).show();

            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Log.e("Rating","Rating: "+v);
            }
        });


//        SpannableString ss = new SpannableString(text1);
//        ClickableSpan clickableSpan = new ClickableSpan() {
//            @Override
//            public void onClick(View widget) {
//            }
//
//            @Override
//            public void updateDrawState(TextPaint text) {
//                text.setColor(context.getResources().getColor(R.color.colorPink));
//
//            }
//        };
//        ss.setSpan(clickableSpan, 16, 26, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        textView.setText(ss);


    }

  /*  private void setRating() {

        if (CommonUtils.isOnline(getApplication())) {

            try {
                ProgressDialogUtils.show(getApplication());

                Api api= RequestController.createService(Api.class);
                api.setRating(transactionId,getRating).enqueue(new Callback<RatingBase>() {
                    @Override
                    public void onResponse(Call<RatingBase> call, Response<RatingBase> response) {
                        ProgressDialogUtils.dismiss();
                        if(response!=null&&response.body()!=null)
                        {
                            if(response.body().getStatus()==1)
                            {

                                String msg=response.body().getMessage();
                                Toast.makeText(PaymentSuccessActivity.this, msg, Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(PaymentSuccessActivity.this, DashBoardActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString("1", "second fragment");
                                intent.putExtras(bundle);
                                startActivity(intent);
                                finishAffinity();
                            }
                            else
                            {
                                String msg=response.body().getMessage();
                                Toast.makeText(PaymentSuccessActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                            Toast.makeText(PaymentSuccessActivity.this, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<RatingBase> call, Throwable t) {
                        ProgressDialogUtils.dismiss();
                        String msg=t.getMessage();
                        Toast.makeText(PaymentSuccessActivity.this, msg, Toast.LENGTH_SHORT).show();

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            Toast.makeText(this, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }
    }*/
}
