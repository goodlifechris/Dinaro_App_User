package com.dinaro.mpesa;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.dinaro.R;
import com.dinaro.activities.PaymentMethodActivity;
import com.dinaro.activities.PaymentSuccessActivity;
import com.dinaro.models.RequestModel.paymentDetails.DetailsBase;
import com.dinaro.mpesa.api.ApiClient;
import com.dinaro.mpesa.api.model.AccessToken;
import com.dinaro.mpesa.api.model.STKPush;
import com.dinaro.mpesa.utils.MPesaPaymentListener;
import com.dinaro.mpesa.utils.Utils;
import com.dinaro.service.Api;
import com.dinaro.service.RequestController;
import com.dinaro.service.apiRequest.paymentRequest;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.PrefManagerNew;
import com.dinaro.utils.ProgressDialogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dinaro.mpesa.utils.AppConstants.BUSINESS_SHORT_CODE;
import static com.dinaro.mpesa.utils.AppConstants.CALLBACKURL;
import static com.dinaro.mpesa.utils.AppConstants.PARTYB;
import static com.dinaro.mpesa.utils.AppConstants.PASSKEY;
import static com.dinaro.mpesa.utils.AppConstants.TRANSACTION_TYPE;

public class MPESAPayment {


    private static final MPESAPayment ourInstance = new MPESAPayment();
    private static ApiClient mApiClient;
    private static String accessToken;
    private MPesaPaymentListener mPesaPaymentListener;
    private Context context;
    private int AMOUNT = 3;
    private PaymentMethodActivity paymentMethodActivity;

    private MPESAPayment() {
    }

    public static MPESAPayment getInstance() {
        mApiClient = new ApiClient();
        return ourInstance;
    }

    public void setListener(MPesaPaymentListener mPesaPaymentListener) {

        this.mPesaPaymentListener = mPesaPaymentListener;
    }

    public void performSTKPush(String phoneNumber, paymentRequest paymentRequest, final Context context, double price) {
        this.context = context;
        mApiClient.setIsDebug(true); //Set True to enable logging, false to disable.
        final String timestamp = Utils.getTimestamp();
        STKPush stkPush = new STKPush(
                BUSINESS_SHORT_CODE,
                Utils.getPassword(BUSINESS_SHORT_CODE, PASSKEY, timestamp),
                timestamp,
                TRANSACTION_TYPE,
                String.valueOf(Integer.parseInt(paymentRequest.getAmount())+AMOUNT),
                "254708374149",
               // Utils.sanitizePhoneNumber(phoneNumber),
                PARTYB,
                "254708374149",
                //Utils.sanitizePhoneNumber(phoneNumber),
//                CALLBACKURL + PrefManagerNew.getStringPreferences(context, AppConstant.DEVICE_TOKEN),
                String.format(CALLBACKURL, PrefManagerNew.getStringPreferences(context, AppConstant.DEVICE_TOKEN), paymentRequest.getUserId(), paymentRequest.getCardId(), paymentRequest.getType(), paymentRequest.getAmount()+AMOUNT, paymentRequest.getBillNumber()),
                "test", //The account reference
                "test"  //The transaction description
        );

                        

        mApiClient.setGetAccessToken(false);

        mApiClient.mpesaService().sendPush(stkPush).enqueue(new Callback<STKPush>() {
            @Override
            public void onResponse(@NonNull Call<STKPush> call, @NonNull Response<STKPush> response) {

                try {
                    if (response.isSuccessful()) {
                        ProgressDialogUtils.dismiss();
                        Toast.makeText(context, "Payment requested successfully.", Toast.LENGTH_LONG).show();
                        Log.d("post submitted", "post submitted : " + response.body());
                        if (mPesaPaymentListener != null)
                            mPesaPaymentListener.onStkpushSuccess("" + timestamp);

                    } else {
                        ProgressDialogUtils.dismiss();
                        Log.e("Response %s", response.errorBody().string());
                        Toast.makeText(context, "Payment request failed.", Toast.LENGTH_LONG).show();
                     /*   if (mPesaPaymentListener != null)
                            mPesaPaymentListener.onStkpushSuccess("" + timestamp);*/

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(@NonNull Call<STKPush> call, @NonNull Throwable t) {

                Log.e("MPESA", t.getStackTrace().toString());
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_LONG).show();
                if (mPesaPaymentListener != null)
                    mPesaPaymentListener.onStkpushFailure();
            }
        });
    }

    public void getAccessToken() {
        mApiClient.setGetAccessToken(true);
        mApiClient.mpesaService().getAccessToken().enqueue(new Callback<AccessToken>() {
            @Override
            public void onResponse(@NonNull Call<AccessToken> call, @NonNull Response<AccessToken> response) {

                if (response.isSuccessful()) {
                    accessToken = response.body().accessToken;
                    mApiClient.setAuthToken(response.body().accessToken);
                    Log.e("Access token", "Access Token: " + accessToken);
                }
            }

            @Override
            public void onFailure(@NonNull Call<AccessToken> call, @NonNull Throwable t) {

            }
        });
    }
}
