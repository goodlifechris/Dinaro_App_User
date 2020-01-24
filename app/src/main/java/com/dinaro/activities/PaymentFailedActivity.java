package com.dinaro.activities;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.dinaro.R;

public class PaymentFailedActivity extends AppCompatActivity {

    private TextView textView;
    private Button btnDone;
    private String text1 = "Your Payment has failed!";
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_payment_failed);

        context = PaymentFailedActivity.this;
        textView = findViewById(R.id.tv_successtext);
        btnDone = findViewById(R.id.btnDone);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        SpannableString ss = new SpannableString(text1);
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
            }

            @Override
            public void updateDrawState(TextPaint text) {
                text.setColor(context.getResources().getColor(R.color.colorPink));

            }
        };
        ss.setSpan(clickableSpan, 17, 23, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        textView.setText(ss);


    }
}
