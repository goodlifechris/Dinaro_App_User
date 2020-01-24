package com.dinaro.fragments;


import androidx.fragment.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dinaro.R;
import com.dinaro.activities.HelpSupportActivity;
import com.dinaro.activities.LoginActivity;
import com.dinaro.activities.NotificationActivity;
import com.dinaro.activities.ProfileActivity;
import com.dinaro.activities.ResetPasswordActivity;
import com.dinaro.activities.SavedCardActivity;
import com.dinaro.databinding.FragmentSettingBinding;
import com.dinaro.service.Api;
import com.dinaro.service.BaseCallback;
import com.dinaro.service.BaseResponse;
import com.dinaro.service.RequestController;
import com.dinaro.service.apiRequest.LogoutRequest;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.PrefManagerNew;
import com.dinaro.utils.ProgressDialogUtils;

import retrofit2.Call;


public class FragementSetting extends Fragment implements View.OnClickListener {
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    private FragmentSettingBinding binding;
    private Context context;


    public FragementSetting() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_setting, container, false);
context=getActivity();
        setListener();
        sharedPreferences = context.getSharedPreferences(AppConstant.SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();


        return binding.getRoot();


    }

    private void setListener() {
        binding.llProfile.setOnClickListener(this);
        binding.llCarddetail.setOnClickListener(this);
        binding.llFaq.setOnClickListener(this);
        binding.llHelp.setOnClickListener(this);
        binding.llLegal.setOnClickListener(this);
        binding.llLogout.setOnClickListener(this);
        binding.llSettings.setOnClickListener(this);
        binding.llSecurity.setOnClickListener(this);
        binding.llNotification.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llProfile:
                startActivity(new Intent(context, ProfileActivity.class));
                break;
            case R.id.ll_carddetail:

                Intent intent=new Intent(context,SavedCardActivity.class);
                intent.putExtra("setting", "setting value");
                startActivity(intent);

               // startActivity(newback Intent(context, SavedCardActivity.class));
                break;
            case R.id.llSecurity:
                startActivity(new Intent(context, ResetPasswordActivity.class));
                break;
            case R.id.llNotification:
                startActivity(new Intent(getActivity(), NotificationActivity.class));
                break;
            case R.id.llHelp:
                startActivity(new Intent(context, HelpSupportActivity.class));
                break;
            case R.id.llFaq:
                Intent intent1 = new Intent(Intent.ACTION_VIEW);
                intent1.setData(Uri.parse(AppConstant.FAQ_URL));
                startActivity(intent1);
               // startActivity(new Intent(context, FAQActivity.class));
                break;
            case R.id.llLegal:
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(AppConstant.LEGAL_URL));
                startActivity(i);
             //   startActivity(new Intent(getActivity(), LegalContentActivity.class));
                break;
            case R.id.llLogout:
                showLogoutDialog();
                break;

            default:
                break;
        }
    }

    private void showLogoutDialog() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
        builder1.setMessage(R.string.logout_message);
        builder1.setCancelable(false);

        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Toast.makeText(context, "Logout successfully.", Toast.LENGTH_SHORT).show();

                        dialog.cancel();
                        goToLoginScreen();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void goToLoginScreen() {
        if (CommonUtils.isOnline(context)) {
            try {
                ProgressDialogUtils.show(context);

                LogoutRequest logoutRequest = new LogoutRequest();
                String user_id = sharedPreferences.getString(AppConstant.USER_ID_BY_SIGNUP, "");
                sharedPreferences.getString(AppConstant.EMAIL, "");
                sharedPreferences.getString(AppConstant.PIN, "");
                System.out.println("user id is=" + user_id);

                logoutRequest.setDeviceToken(PrefManagerNew.getStringPreferences(context, AppConstant.DEVICE_TOKEN));
                logoutRequest.setDeviceType(AppConstant.Device_Type);
                logoutRequest.setUserId(user_id);


                Api api = RequestController.createService(Api.class);
                api.getLogoutData(logoutRequest).enqueue(new BaseCallback<BaseResponse>(context) {
                    @Override
                    public void onSuccess(BaseResponse response) {
                        if (response.getStatus() == 0) {
                            ProgressDialogUtils.dismiss();

                            Intent intent = new Intent(context, LoginActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("2", "xyz");
                            intent.putExtras(bundle);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            editor.remove(AppConstant.EMAIL);
                            editor.remove(AppConstant.PIN);
                            editor.remove(AppConstant.VERIFIED_TYPE);
                            editor.commit();

                            startActivity(intent);
                            getActivity().finishAffinity();
                            Toast.makeText(context, response.getMessage(), Toast.LENGTH_SHORT).show();

                        } else {
                            ProgressDialogUtils.dismiss();

                            Toast.makeText(context,  response.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onFail(Call<BaseResponse> call, BaseResponse baseResponse) {
                        ProgressDialogUtils.dismiss();
                        String msg = baseResponse.getMessage();

                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ProgressDialogUtils.dismiss();

            Toast.makeText(context, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }


    }
}
