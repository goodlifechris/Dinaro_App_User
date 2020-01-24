package com.dinaro.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.dinaro.R;
import com.dinaro.firebaseauth.FirebaseAuthentication;
import com.dinaro.models.RequestModel.login.Data;
import com.dinaro.models.RequestModel.login.User;
import com.dinaro.service.Api;
import com.dinaro.service.BaseCallback;
import com.dinaro.service.BaseResponse;
import com.dinaro.service.RequestController;
import com.dinaro.service.apiRequest.RegisterRequest;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.PrefManager;
import com.dinaro.utils.PrefManagerNew;
import com.dinaro.utils.ProgressDialogUtils;
import com.dinaro.utils.ValidationUtils;

import retrofit2.Call;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private static String EMAIL_PATTERN = "[a-zA-Z0-9_-]+@[a-z]{2,50}+\\.+[a-z]{2,3}+";
    private static String EMAIL_PATTERN_With_LIMIT = "[a-zA-Z0-9_-]+@[a-z]{2,50}+\\.+[a-z]{2,3}+\\.+[a-z]{2,3}+";
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    private TextView tv1;
    // String email_pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String email_pattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private String text = "By submitting this form,you accept Dinaro's Terms and Condition and Privacy Policy";
    private CheckBox checkBox;
    private TextView textView;
    private EditText firstName, middleName, lastName, ed1, ed2, ed3, ed4;
    private Button btn1;
    private boolean check;
    private String first_name;
    private String middle_Name;
    private String last_Name;
    private String Mobile_number;
    private String Passsword;
    private String email;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_signup_activty);
        context = SignUpActivity.this;
        init();
        btn1.setOnClickListener(this);
        spannable();

        preferences = getApplication().getSharedPreferences(AppConstant.SHARED_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        FirebaseAuthentication.getInstance(this).initialize();


    }

    private void spannable() {


        SpannableString SpanString = new SpannableString(getResources().getString(R.string.policy));

        ClickableSpan teremsAndCondition = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent intent = new Intent(SignUpActivity.this, TermsAndCondition.class);
                startActivity(intent);


            }
        };

        ClickableSpan privacy = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                Intent intent = new Intent(SignUpActivity.this, PrivacyPolicy.class);
                startActivity(intent);


            }
        };

        SpanString.setSpan(teremsAndCondition, 43, 63, 0);
        SpanString.setSpan(privacy, 67, 82, 0);
        SpanString.setSpan(new ForegroundColorSpan(Color.MAGENTA), 43, 63, 0);
        SpanString.setSpan(new ForegroundColorSpan(Color.MAGENTA), 67, 82, 0);
        SpanString.setSpan(new UnderlineSpan(), 43, 63, 0);
        SpanString.setSpan(new UnderlineSpan(), 67, 82, 0);

        tv1.setMovementMethod(LinkMovementMethod.getInstance());
        tv1.setText(SpanString, TextView.BufferType.SPANNABLE);
        tv1.setSelected(true);

    }


    private void init() {
        firstName = findViewById(R.id.ed_first_name);
        middleName = findViewById(R.id.ed_middle_name);
        lastName = findViewById(R.id.ed_last_name);
        ed2 = findViewById(R.id.ed_mobilenumber);
        ed3 = findViewById(R.id.edemail);
        ed4 = findViewById(R.id.ed_password);
        btn1 = findViewById(R.id.btn_signup);
        tv1 = findViewById(R.id.tv_spantext);
        checkBox = findViewById(R.id.check);
        textView = findViewById(R.id.tv_check_error);
        ed4.setFilters(new InputFilter[]{
                ValidationUtils.BLOCK_EMOJI,
                ValidationUtils.BLOCK_SPACE,
                new InputFilter.LengthFilter(64)});
        ed3.setFilters(new InputFilter[]{
                ValidationUtils.BLOCK_EMOJI,
                ValidationUtils.BLOCK_SPACE,
                new InputFilter.LengthFilter(64)});

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_signup:
                if (checkValidation()) {
                    if(checkBox.isChecked())
                    getRegister();
                    else
                    {
                        Toast.makeText(context,"Please Select the Terms & Conditions",Toast.LENGTH_SHORT).show();
                    }

                }

                break;
        }

    }

    private void getRegister() {
        if (CommonUtils.isOnline(context)) {

            try {
                ProgressDialogUtils.show(context);

                RegisterRequest registerRequest = new RegisterRequest();
                registerRequest.setName(first_name);
                registerRequest.setMiddleName(middle_Name);
                registerRequest.setLastName(last_Name);
                registerRequest.setEmail(email.toLowerCase());
                // registerRequest.setCountryCode(AppConstant.Country_Code);
                registerRequest.setMobile(Mobile_number);
                registerRequest.setPassword(Passsword);
                registerRequest.setDeviceType(AppConstant.Device_Type);
                registerRequest.setDeviceToken(PrefManagerNew.getStringPreferences(context, AppConstant.DEVICE_TOKEN));

                Api api = RequestController.createService(Api.class);
                api.getData(registerRequest).enqueue(new BaseCallback<User>(context) {
                    @Override
                    public void onSuccess(User response) {
                        ProgressDialogUtils.dismiss();

                        if (response != null) {
                            if (response.getStatus() == 1) {
                                ProgressDialogUtils.dismiss();
                                Data user = response.getResult().getData();
                                String phone = user.getMobile();
                                String id = user.getId();
                                String email=user.getEmail();


                                preferences.edit().putString(AppConstant.USER_ID_BY_SIGNUP, id).apply();
                                PrefManagerNew.saveStringPreferences(context, AppConstant.MOBILE, phone);
                                PrefManagerNew.saveStringPreferences(context,AppConstant.EMAIL,email);

                                Intent intent = new Intent(SignUpActivity.this, PintActivity.class);
                                Bundle bundle = new Bundle();
                                bundle.putString(AppConstant.FROM, AppConstant.SIGN_UP_ACTIVITY);
                                intent.putExtras(bundle);
                                intent.putExtra("phone", "" + AppConstant.Country_Code + phone);
                                startActivity(intent);


                                //Toast.makeText(SignUpActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SignUpActivity.this, response.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        } else {
                            Toast.makeText(context, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onFail(Call<User> call, BaseResponse baseResponse) {
                        ProgressDialogUtils.dismiss();

                        Toast.makeText(SignUpActivity.this, baseResponse.getMessage()
                                , Toast.LENGTH_SHORT).show();


                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                ProgressDialogUtils.dismiss();
            }

        } else {
            ProgressDialogUtils.dismiss();

            Toast.makeText(context, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }
    }


    private boolean checkValidation() {
        clearAll();
        Mobile_number = ed2.getText().toString();
        Passsword = ed4.getText().toString();
        email = ed3.getText().toString();
        first_name = firstName.getText().toString();
        middle_Name = middleName.getText().toString();
        last_Name = lastName.getText().toString();


        String mobile_pattern = "((\\+*)((0[ -]+)*|(91)*(\\d{7,14}))|\\d{5}([- ]*)\\d{6})";
        if (first_name.length() == 0) {
            firstName.requestFocus();
            firstName.setError(getResources().getString(R.string.empty_first_name));
            return false;
        } else if (first_name.length() <= 2) {
            firstName.requestFocus();
            firstName.setError(getResources().getString(R.string.invalid_fname));
            return false;


        } else if (last_Name.length() == 0) {
            lastName.requestFocus();
            lastName.setError(getResources().getString(R.string.empty_last_name));
            return false;
        } else if (last_Name.length() <= 1) {
            lastName.requestFocus();
            lastName.setError(getResources().getString(R.string.invalid_fname));
            return false;


        } else if (Mobile_number.equalsIgnoreCase("")) {
            ed2.requestFocus();
            ed2.setError(getResources().getString(R.string.empty_phone));
            return false;

        } else if (ed2.getText().toString().startsWith("0")) {
            ed2.requestFocus();
            ed2.setError("Please enter valid mobile number like +254 7.......");

            return false;
        } else if (!(Mobile_number.matches(mobile_pattern))) {
            ed2.requestFocus();
            ed2.setError(getResources().getString(R.string.invalid_mobile));
            return false;
        } else if (email.length() == 0) {
            ed3.requestFocus();
            ed3.setError(getResources().getString(R.string.empty_email));
            return false;
        } else if (!ValidationUtils.validEmail(email)) {
            ed3.requestFocus();
            ed3.setError(getResources().getString(R.string.invalid_email));
            return false;
        } else if (Passsword.length() == 0) {
            ed4.requestFocus();
            ed4.setError(getResources().getString(R.string.empty_password));
            return false;
        } else if (!(Passsword.length() >= 6 && Passsword.length() <= 16)) {
            ed4.requestFocus();
            ed4.setError(getResources().getString(R.string.invalid_password));
            return false;
        }


        return true;
    }

    private void clearAll() {

        textView.setVisibility(View.GONE);

    }


}
