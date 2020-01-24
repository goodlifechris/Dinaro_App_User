package com.dinaro.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextPaint;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.dinaro.R;
import com.dinaro.mpesa.MPESAPayment;
import com.dinaro.mpesa.utils.Utils;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.PrefManager;

import static com.dinaro.mpesa.utils.AppConstants.BUSINESS_SHORT_CODE;
import static com.dinaro.mpesa.utils.AppConstants.PASSKEY;


public class SplashActivity extends AppCompatActivity {

    int isVerified;
    Intent intent;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private String firstTime, userId = null, emailId = null, pin = null;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        preferences = getApplication().getSharedPreferences(AppConstant.SHARED_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        Context context = SplashActivity.this;
        Log.e("Password", Utils.getPassword(BUSINESS_SHORT_CODE, PASSKEY, Utils.getTimestamp()));
        MPESAPayment.getInstance().getAccessToken();
        firstTime = PrefManager.getFirstLogin(context);

        emailId = preferences.getString(AppConstant.EMAIL, "");
        pin = preferences.getString(AppConstant.PIN, "");
        isVerified = preferences.getInt(AppConstant.VERIFIED_TYPE, 0);

       /* textView = (TextView) findViewById(R.id.tvDinaro);
        textView.setText("Dinaro".toUpperCase());

        TextPaint paint = textView.getPaint();
        float width = paint.measureText("Dinaro");

        Shader textShader = new LinearGradient(0, 0, width, textView.getTextSize(),
                new int[]{
                        Color.parseColor("#000000"),
                        Color.parseColor("#D81B60"),
                        Color.parseColor("#e41995"),
                        Color.parseColor("#DD05F5"),
                        Color.parseColor("#E21993"),
                }, null, Shader.TileMode.CLAMP);
        textView.getPaint().setShader(textShader);

*/
        int SPLASH_TIME_OUT = 3000;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                if (firstTime != null) {


                    if (firstTime.equalsIgnoreCase("")) {
                        if (emailId.equalsIgnoreCase("")) {
                            Intent i = new Intent(SplashActivity.this, TutorialActivity.class);
                            startActivity(i);
                            finish();
                        } else if (pin.equalsIgnoreCase("")) {
                            Intent i = new Intent(SplashActivity.this, TutorialActivity.class);
                            startActivity(i);
                            finish();
                        } else {
                            Intent i = new Intent(SplashActivity.this, TutorialActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putString("123", "abc");
//                            i.putExtras(bundle);
                            startActivity(i);
                            finish();
                        }


                    } else {
                        if (!emailId.equalsIgnoreCase("") && isVerified == 1) {
                            Intent i = new Intent(SplashActivity.this, DashBoardActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("1234", "abcd");
                            i.putExtras(bundle);
                            startActivity(i);
                            finish();
                        } else if (!pin.equalsIgnoreCase("") && isVerified == 1) {
                            Intent i = new Intent(SplashActivity.this, DashBoardActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("1234", "abcd");
                            i.putExtras(bundle);
                            startActivity(i);
                            finish();
                        } else {
                            Intent i = new Intent(SplashActivity.this, TutorialActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("1234", "abcd");
                            i.putExtras(bundle);
                            startActivity(i);
                            finish();
                        }
                    }


                }


            }
        }, SPLASH_TIME_OUT);
    }
}
