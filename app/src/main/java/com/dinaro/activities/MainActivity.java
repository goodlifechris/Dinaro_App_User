package com.dinaro.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.dinaro.R;
import com.dinaro.models.CheckoutData;
import com.dinaro.models.TokenResponseDto;
import com.dinaro.service.RequestInterface;
import com.dinaro.service.ServiceGenerator;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends Activity {

    private static final int REQUEST_CODE = 123;
    private Button paypal_button;
    private EditText et_amount;
    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        paypal_button= findViewById(R.id.start_payment);
        et_amount= findViewById(R.id.et_amount);
        paypal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount=et_amount.getText().toString();

               //-->> add api to generate client token to call startPayment()

                if(amount.isEmpty())
                {
                   et_amount.setError("Enter the Amount !");
                    et_amount.requestFocus();
                   return;
               }
               else {

                   //startPayment();
                //  onBraintreeSubmit(getString(R.string.test_client_token)); // -->> added for testing with sample client token, call this method from onResponse() of startPayment() only
               }
            }


        });
    }

   /* public void startPayment() {




        RequestInterface loginService =
                ServiceGenerator.createService(RequestInterface.class, "admin", "admin");


        Call<TokenResponseDto> call1=loginService.getClientToken();
        call1.enqueue(new Callback<TokenResponseDto>() {
            @Override
            public void onResponse(Call<TokenResponseDto> call, Response<TokenResponseDto> response) {

                TokenResponseDto tokenResponseDto=response.body();
                if (response.isSuccessful() && response!=null) {
                    if(tokenResponseDto.getStatus().equals("1"))
                    {
                        Toast.makeText(MainActivity.this,""+tokenResponseDto.getMessage(),Toast.LENGTH_SHORT).show();
                        onBraintreeSubmit(tokenResponseDto.getToken());
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,""+tokenResponseDto.getMessage(),Toast.LENGTH_LONG).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<TokenResponseDto> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Oops! Something went wrong!",Toast.LENGTH_SHORT).show();
            }

        });
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                String payment_method_nonce=result.getPaymentMethodNonce().getNonce();
                Toast.makeText(MainActivity.this,"Nonce is ---- "+payment_method_nonce,Toast.LENGTH_LONG).show();
             //  submitNonce(payment_method_nonce);
               //-->> create server side api to accept payment amount and PaymentNonce
                // use the result to update your UI and send the payment method nonce to your server
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
                Toast.makeText(MainActivity.this,"cancelled",Toast.LENGTH_LONG).show();
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Toast.makeText(MainActivity.this,"error is---"+error,Toast.LENGTH_LONG).show();
            }
        }
    }

   /* private void submitNonce(String payment_method_nonce) {
        RequestInterface requestInterface =
                ServiceGenerator.createService(RequestInterface.class, "admin", "admin");

        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("amount", amount);
        jsonObject.addProperty("nonce",payment_method_nonce);
        jsonObject.addProperty("user_id","5d08adb80d46af5da56575a3");


        Call<CheckoutData> call1=requestInterface.getPayment(jsonObject);
        call1.enqueue(new Callback<CheckoutData>() {
            @Override
            public void onResponse(Call<CheckoutData> call, Response<CheckoutData> response) {
                CheckoutData checkoutData=response.body();
                if(response.isSuccessful())
                {
                    if(checkoutData.getStatus().equals("1"))
                    {
                        Toast.makeText(MainActivity.this,""+checkoutData.getMessage(),Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,""+checkoutData.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this,""+response.message(),Toast.LENGTH_LONG).show();
                }



            }

            @Override
            public void onFailure(Call<CheckoutData> call, Throwable t) {

            }

        });
    }*/

    public void onBraintreeSubmit(String token) {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(token);
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }
}
