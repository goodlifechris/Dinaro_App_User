package com.dinaro.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.dinaro.R;
import com.dinaro.SharePre;
import com.dinaro.activities.DashBoardActivity;
import com.dinaro.activities.ForgotPinActivity;
import com.dinaro.activities.LoginActivity;
import com.dinaro.databinding.FragmentPinBinding;
import com.dinaro.models.RequestModel.loginPin.Datapin;
import com.dinaro.models.RequestModel.loginPin.Userpin;
import com.dinaro.service.Api;
import com.dinaro.service.BaseCallback;
import com.dinaro.service.BaseResponse;
import com.dinaro.service.RequestController;
import com.dinaro.service.apiRequest.LoginRequest;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.PrefManagerNew;
import com.dinaro.utils.ProgressDialogUtils;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PinFragment extends Fragment implements View.OnClickListener {
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    private String getpin, userId, pin;
    private Context context;
    private FragmentPinBinding binding;
    private EditText pinEntry;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pin, container, false);
        context = getActivity();
        preferences = Objects.requireNonNull(getActivity()).getSharedPreferences(AppConstant.SHARED_PREF, Context.MODE_PRIVATE);
        editor = preferences.edit();
        getpin = preferences.getString(AppConstant.PIN_PREF, "");
        System.out.println("pin is=" + getpin);

        userId = preferences.getString(AppConstant.USER_ID_BY_SIGNUP, "");
        System.out.println("user id is=" + userId);

        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setListener();
    }

    private void setListener() {
        ((LoginActivity) getActivity()).binding.btnfLogin.setOnClickListener(this);
        binding.tvForgotpinPf.setOnClickListener(this);
//        binding.tvSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnfLogin:
                if (checkValidation()) {
                    if (userId == "") {

                        showLogoutDialog();
                    } else {
                        getLoginData();

                    }

                }

                break;
/*            case R.id.tv_Signup:
                SharePre.SaveId(context, "pin", "1");
                startActivity(new Intent(getContext(), SignUpActivity.class));

                break;*/
            case R.id.tvForgotpinPf:
                SharePre.SaveId(context, "pin", "2");
                startActivity(new Intent(getContext(), ForgotPinActivity.class));


        }
    }

    private boolean checkValidation() {
        pin = binding.pinentrypin.getText().toString();
        clearError();
        if (pin.isEmpty()) {
            binding.tvPinError.setVisibility(View.VISIBLE);
            binding.tvPinError.setText(R.string.empty_pin);
            binding.tvPinError.setTextColor(getResources().getColor(R.color.colorRed));
            return false;
        } else if (!(pin.length() == 4)) {
            binding.tvPinError.setVisibility(View.VISIBLE);
            binding.tvPinError.setText(R.string.invalid_pin);
            binding.tvPinError.setTextColor(getResources().getColor(R.color.colorRed));
            return false;
        }/* else if (!pin.equals(getpin)) {
            binding.tvPinError.setVisibility(View.VISIBLE);
            binding.tvPinError.setText(R.string.pinNotExist);
            binding.tvPinError.setTextColor(getResources().getColor(R.color.colorRed));
            return false;
        }*/

        return true;
    }


    private void showLogoutDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(R.string.login_again);
        builder1.setCancelable(false);

       /* builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Toast.makeText(context, "Logout successfully.", Toast.LENGTH_SHORT).show();

                        dialog.cancel();
                        //goToLoginScreen();
                    }
                });
*/
        builder1.setNegativeButton(
                "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();


                        Intent i = new Intent(context, LoginActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("123", "abc");
                        i.putExtras(bundle);
                        startActivity(i);
                        getActivity().finish();

                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void clearError() {
        binding.tvPinError.setVisibility(View.GONE);
    }


    private void getLoginData() {
        if (CommonUtils.isOnline(context)) {
            try {
                ProgressDialogUtils.show(context);

                LoginRequest loginPinRequest = new LoginRequest();
                loginPinRequest.setPin(pin);
                loginPinRequest.setUserId(userId);
                loginPinRequest.setType(AppConstant.TYPE_PIN);
                loginPinRequest.setDeviceType(AppConstant.Device_Type);
                loginPinRequest.setDeviceToken(PrefManagerNew.getStringPreferences(context, AppConstant.DEVICE_TOKEN));
                Api api = RequestController.createService(Api.class);

                api.getLoginDataByPin(loginPinRequest).enqueue(new BaseCallback<Userpin>(context) {
                    @Override
                    public void onSuccess(Userpin response) {
                        ProgressDialogUtils.dismiss();
                        if ( response != null) {
                            if (response.getStatus() == 1) {
                                Datapin datapin = response.getResult().getData();
                                String pin = datapin.getPin();

                                preferences.edit().putString(AppConstant.PIN, "" + pin).apply();

                                Toast.makeText(context, "" + response.getMessage(), Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(context, DashBoardActivity.class));
                                getActivity().finish();


                            } else {


                                Toast.makeText(context, "" + response.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFail(Call<Userpin> call, BaseResponse baseResponse) {
                        ProgressDialogUtils.dismiss();

                        String msg = baseResponse.getMessage();
                        Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
                    }
                });


               /* api.getLoginDataByPin(loginPinRequest).enqueue(new Callback<Userpin>() {
                    @Override
                    public void onResponse(Call<Userpin> call, Response<Userpin> response) {
                        ProgressDialogUtils.dismiss();
                        if (response.body() != null && response != null) {
                            if (response.body().getStatus() == 1) {
                                Datapin datapin = response.body().getResult().getData();
                                String pin = datapin.getPin();

                                preferences.edit().putString(AppConstant.PIN, "" + pin).apply();

                                Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(context, DashBoardActivity.class));
                                getActivity().finish();


                            } else {


                                Toast.makeText(context, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Userpin> call, Throwable t) {
                        ProgressDialogUtils.dismiss();

                        String msg = t.getMessage();
                        Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();

                    }
                });*/






            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ProgressDialogUtils.dismiss();

            Toast.makeText(context, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }

    }

}