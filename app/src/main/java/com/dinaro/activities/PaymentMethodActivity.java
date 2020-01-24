package com.dinaro.activities;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.dinaro.R;
import com.dinaro.models.RequestModel.PaypalPayment.CheckoutData;
import com.dinaro.models.RequestModel.PaypalPayment.TokenResponseDto;
import com.dinaro.models.RequestModel.paymentDetails.DetailsBase;
import com.dinaro.mpesa.MPESAPayment;
import com.dinaro.mpesa.utils.MPesaPaymentListener;
import com.dinaro.mpesa.utils.NotificationUtils;
import com.dinaro.service.Api;
import com.dinaro.service.RequestController;
import com.dinaro.service.RequestInterface;
import com.dinaro.service.ServiceGenerator;
import com.dinaro.service.apiRequest.paymentRequest;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.PrefManagerNew;
import com.dinaro.utils.ProgressDialogUtils;
//import com.finserve.pgw_sdk_android.forpublic.ErrorResponse;
//import com.finserve.pgw_sdk_android.forpublic.PgwSdk;
//import com.finserve.pgw_sdk_android.forpublic.SetUpResp;
//import com.finserve.pgw_sdk_android.forpublic.auth.AuthData;
import com.google.gson.JsonObject;

import java.util.Calendar;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dinaro.mpesa.utils.AppConstants.PUSH_NOTIFICATION;

public class PaymentMethodActivity extends AppCompatActivity
//        implements
//        View.OnClickListener
//        , MPesaPaymentListener {
{
    private static final String TAG = PaymentMethodActivity.class.getSimpleName();
    // private static final String PATH_TO_SERVER = "http://192.168.1.162:81/braintree/";
    // private String clientToken="eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiJleUowZVhBaU9pSktWMVFpTENKaGJHY2lPaUpGVXpJMU5pSXNJbXRwWkNJNklqSXdNVGd3TkRJMk1UWXRjMkZ1WkdKdmVDSjkuZXlKbGVIQWlPakUxTmpFd01qTTJNemdzSW1wMGFTSTZJalU0WVdVME5qazBMVEUwTkRRdE5HRTNNeTFoTURobUxUa3daRFJtWm1VNU5UWXhOeUlzSW5OMVlpSTZJbkU1WkRjMVluWm1OMlJqYTJ0cmJqWWlMQ0pwYzNNaU9pSkJkWFJvZVNJc0ltMWxjbU5vWVc1MElqcDdJbkIxWW14cFkxOXBaQ0k2SW5FNVpEYzFZblptTjJSamEydHJiallpTENKMlpYSnBabmxmWTJGeVpGOWllVjlrWldaaGRXeDBJanBtWVd4elpYMHNJbkpwWjJoMGN5STZXeUp0WVc1aFoyVmZkbUYxYkhRaVhTd2liM0IwYVc5dWN5STZlMzE5Lnd5MVgxdm1qWENVV3FfQWItaTBBSlphSXUxRzRNbmVOY0l6UzZuZXB1Si1DbFpHY0pjVE5kR1dpM3I0Zk1kOXRlemR5V2JLcy1HLUdBaWlXNEc4QWN3IiwiY29uZmlnVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzL3E5ZDc1YnZmN2Rja2trbjYvY2xpZW50X2FwaS92MS9jb25maWd1cmF0aW9uIiwiZ3JhcGhRTCI6eyJ1cmwiOiJodHRwczovL3BheW1lbnRzLnNhbmRib3guYnJhaW50cmVlLWFwaS5jb20vZ3JhcGhxbCIsImRhdGUiOiIyMDE4LTA1LTA4In0sImNoYWxsZW5nZXMiOltdLCJlbnZpcm9ubWVudCI6InNhbmRib3giLCJjbGllbnRBcGlVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvcTlkNzVidmY3ZGNra2tuNi9jbGllbnRfYXBpIiwiYXNzZXRzVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhdXRoVXJsIjoiaHR0cHM6Ly9hdXRoLnZlbm1vLnNhbmRib3guYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhbmFseXRpY3MiOnsidXJsIjoiaHR0cHM6Ly9vcmlnaW4tYW5hbHl0aWNzLXNhbmQuc2FuZGJveC5icmFpbnRyZWUtYXBpLmNvbS9xOWQ3NWJ2ZjdkY2tra242In0sInRocmVlRFNlY3VyZUVuYWJsZWQiOnRydWUsInBheXBhbEVuYWJsZWQiOnRydWUsInBheXBhbCI6eyJkaXNwbGF5TmFtZSI6Ik1vYmlsb2l0dGUiLCJjbGllbnRJZCI6bnVsbCwicHJpdmFjeVVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS9wcCIsInVzZXJBZ3JlZW1lbnRVcmwiOiJodHRwOi8vZXhhbXBsZS5jb20vdG9zIiwiYmFzZVVybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXNzZXRzVXJsIjoiaHR0cHM6Ly9jaGVja291dC5wYXlwYWwuY29tIiwiZGlyZWN0QmFzZVVybCI6bnVsbCwiYWxsb3dIdHRwIjp0cnVlLCJlbnZpcm9ubWVudE5vTmV0d29yayI6dHJ1ZSwiZW52aXJvbm1lbnQiOiJvZmZsaW5lIiwidW52ZXR0ZWRNZXJjaGFudCI6ZmFsc2UsImJyYWludHJlZUNsaWVudElkIjoibWFzdGVyY2xpZW50MyIsImJpbGxpbmdBZ3JlZW1lbnRzRW5hYmxlZCI6dHJ1ZSwibWVyY2hhbnRBY2NvdW50SWQiOiJtb2JpbG9pdHRlIiwiY3VycmVuY3lJc29Db2RlIjoiVVNEIn0sIm1lcmNoYW50SWQiOiJxOWQ3NWJ2ZjdkY2tra242IiwidmVubW8iOiJvZmYifQ==";
    private static final int REQUEST_CODE = 123;
    private Button btn1;
    private Bundle bundle;
    private ImageView imageView, ivPayPal, iv_jenga;
    private LinearLayout linearLayout;
    private LinearLayout payMpesa;
    private ImageView ivPaypal, ivMpesa, ivCard;

    private String transactionId, userId;
    private MPESAPayment mpesaPayment;
    private String amount, from;
    private int AMOUNT = 3;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    private paymentRequest paymentRequest;
    private Context context;
    private String payment_method_nonce;
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // startPayment();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_payment_method);
        context = PaymentMethodActivity.this;
//        findViews();
        getBundle();
        preferences = getApplication().getSharedPreferences(AppConstant.SHARED_PREF, MODE_PRIVATE);
        editor = preferences.edit();
        userId = preferences.getString(AppConstant.USER_ID_BY_SIGNUP, "");

        Intent intent = getIntent();
        amount = intent.getStringExtra("amount");


        mpesaPayment = MPESAPayment.getInstance();
//        mpesaPayment.setListener(this);
        mpesaPayment.getAccessToken();

        transactionId = getIntent().getStringExtra("transaction_id");
        transactionId = getIntent().getStringExtra("trans_id");
       // paymentSdkInt();
        paymentRequest = (paymentRequest) getIntent().getSerializableExtra("paymentRequest");

        mRegistrationBroadcastReceiver =
                new BroadcastReceiver() {

                    @Override
                    public void onReceive(Context context, Intent intent) {

                        if (intent.getAction().equals(PUSH_NOTIFICATION)) {
                            int status = intent.getIntExtra("status", 0);
                            if (status == 1) {
                                NotificationUtils.createNotification(getApplicationContext(), "Your payment from mPesa is successfull.");
                                Intent intentSucess = new Intent(PaymentMethodActivity.this, PaymentSuccessActivity.class);
                                startActivity(intentSucess);
                            } else {
                                NotificationUtils.createNotification(getApplicationContext(), "Your payment from mPesa is failed.");
                                Intent intentFailed = new Intent(PaymentMethodActivity.this, PaymentFailedActivity.class);
                                startActivity(intentFailed);
                            }

                        }
                    }
                };

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(PUSH_NOTIFICATION));


//
//
//        AuthData authData = new AuthData(username, password, username, outletCode, apikey);  //they have to be passed in this order.
//
//        PgwSdk.getSDK(authData, new SetUpResp() {
//            @Override
//            public void onConnected(PgwSdk pgwSdk) {
//                sdk = pgwSdk; //am assuming you already declared this variable somewhere.
//                //TODO: SDK is ready for you to consume.
//            }
//
//            @Override
//            public void onError(ErrorResponse errorResponse) {
//                //TODO: an error occurred. You have access to the message code and a message through ErrorResponse
//            }
//
//        }, context);
//
//
//


    }

//    private void findViews() {
//        btn1 = findViewById(R.id.btn_back_paymentmethod);
//        imageView = findViewById(R.id.iv_paymentback);
//        ivPayPal = findViewById(R.id.ivPayPal);
//       /* linearLayout = findViewById(R.id.layout_Pay);
//        payMpesa = findViewById(R.id.payMpesa);*/
//        ivCard = findViewById(R.id.ivCard);
//        ivMpesa = findViewById(R.id.ivMpesa);
//        ivPayPal = findViewById(R.id.ivPayPal);
//
//        iv_jenga = findViewById(R.id.iv_jenga);
//        ivPayPal.setOnClickListener(this);
//        ivMpesa.setOnClickListener(this);
//        ivCard.setOnClickListener(this);
//        // payMpesa.setOnClickListener(this);
//        // linearLayout.setOnClickListener(this);
//        btn1.setOnClickListener(this);
//        imageView.setOnClickListener(this);
//        ivPayPal.setOnClickListener(this);
//        iv_jenga.setOnClickListener(this);
////        mpesaPayment.setListener(this);
////        mpesaPayment.getAccessToken();
//    }

    private void getBundle() {

        Bundle intent = getIntent().getExtras();
        if (intent != null) {
            if (intent.getString(AppConstant.FROM) != null) {

                from = intent.getString(AppConstant.FROM);
                if (from.equalsIgnoreCase("FragmentRecent")) {
                    ivMpesa.setVisibility(View.GONE);
                }
            }

        }

    }

    private void startPayment() {

        ProgressDialogUtils.show(PaymentMethodActivity.this);
//        AsyncHttpClient androidClient = new AsyncHttpClient();
//        androidClient.get(PATH_TO_SERVER, new TextHttpResponseHandler()
//        {
//            @Override
//            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
//                Log.d(TAG, "Token Failed" + throwable.toString());
//                Toast.makeText( PaymentMethodActivity.this, throwable.toString(), Toast.LENGTH_SHORT ).show();
//            }
//
//            @Override
//            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
//                Log.d( TAG,"Cleint Token:" +responseString );
//                clientToken=responseString;
//            }
//
//
//        });
//        if (CommonUtils.isOnline(context)) {
//            try {
//                ProgressDialogUtils.show(context);
//
//                Api api = RequestController.createService(Api.class);
//                api.gettoken().enqueue( new Callback<PaymentResult>() {
//                    @Override
//                    public void onResponse(Call<PaymentResult> call, Response<PaymentResult> response) {
//                        String data =response.body().getResult();
//                        PrefManager.getInstance(context).savePreference( AppConstants.GENERATED_TOCKEN,data);
//                    }
//
//                    @Override
//                    public void onFailure(Call<PaymentResult> call, Throwable t) {
//                        String msg = t.getMessage();
//                        Toast.makeText(PaymentMethodActivity.this, msg, Toast.LENGTH_SHORT).show();
//                    }
//                } );
//
//            } catch (Exception e) {
//                ProgressDialogUtils.dismiss();
//                e.printStackTrace();
//            }
//        } else {
//            Toast.makeText(context, R.string.networ_error, Toast.LENGTH_SHORT).show();
//        }

        RequestInterface loginService = ServiceGenerator.createService(RequestInterface.class, "admin", "admin");


        Call<TokenResponseDto> call1 = loginService.getClientToken();
        call1.enqueue(new Callback<TokenResponseDto>() {
            @Override
            public void onResponse(Call<TokenResponseDto> call, Response<TokenResponseDto> response) {
                ProgressDialogUtils.dismiss();
                if (response.isSuccessful() && response != null) {
                    if (response.body().getStatus().equals("1")) {
                        TokenResponseDto tokenResponseDto = response.body();
                        String token = tokenResponseDto.getToken();
                        // Toast.makeText(PaymentMethodActivity.this, "" + tokenResponseDto.getMessage(), Toast.LENGTH_SHORT).show();
                        onBraintreeSubmit(token);
                    } else {
                        Toast.makeText(PaymentMethodActivity.this, "" + response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(context, "" + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TokenResponseDto> call, Throwable t) {
                ProgressDialogUtils.dismiss();
                Toast.makeText(PaymentMethodActivity.this, "Oops! Something went wrong!", Toast.LENGTH_SHORT).show();
            }

        });
    }




    public void paymentSdkInt()
    {


    }

    private void showResultDialog(String message) {
        Toast.makeText(PaymentMethodActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    public void onBraintreeSubmit(String token) {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(token);
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == BRAINTREE_REQUEST_CODE){
//            if (RESULT_OK == resultCode){
//                DropInResult result = data.getParcelableExtra( DropInResult.EXTRA_DROP_IN_RESULT);
//                String paymentNonce = result.getPaymentMethodNonce().getNonce();
//                //send to your server
//                Log.d(TAG, "Testing the app here");
//                sendPaymentNonceToServer(paymentNonce);
//            }else if(resultCode == Activity.RESULT_CANCELED){
//                Log.d(TAG, "User cancelled payment");
//            }else {
//                Exception error = (Exception)data.getSerializableExtra( DropInActivity.EXTRA_ERROR);
//                Log.d(TAG, " error exception");
//            }
//
//        }
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                payment_method_nonce = result.getPaymentMethodNonce().getNonce();
                //Toast.makeText(PaymentMethodActivity.this, "Nonce is ---- " + payment_method_nonce, Toast.LENGTH_LONG).show();
//                submitNonce(payment_method_nonce);
                //-->> create server side api to accept payment amount and PaymentNonce
                // use the result to update your UI and send the payment method nonce to your server
            } else if (resultCode == Activity.RESULT_CANCELED) {
                // the user canceled
                Toast.makeText(PaymentMethodActivity.this, "cancelled", Toast.LENGTH_LONG).show();
            } else {
                // handle errors here, an exception may be available in
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                Toast.makeText(PaymentMethodActivity.this, "error is---" + error, Toast.LENGTH_LONG).show();
            }
        }

//        if (requestCode == PgwSdk.CREATE_ORDER) {
//            if (resultCode == RESULT_OK) {
//                //TODO: receive the response from the data
//                Toast.makeText(context, "createbill transactionRef :" + data.getStringExtra("transactionRef"), Toast.LENGTH_SHORT).show();
//                // Log.d("data","createbill transactionRef :" + data.getStringExtra("transactionRef"));
//                // Log.d("data"," Customer OrderRef :" + data.getStringExtra("billReference"));
//            } else if (resultCode == RESULT_CANCELED) {
//                //TODO: request canceled by user. Handle it
//            }
//        }
    }
//    private void sendPaymentNonceToServer(String paymentNonce){
//        RequestParams params = new RequestParams("NONCE", paymentNonce);
//        AsyncHttpClient androidClient = new AsyncHttpClient();
//        androidClient.post("http://ec2-3-8-105-184.eu-west-2.compute.amazonaws.com/api", params, new TextHttpResponseHandler() {
//            @Override
//            public void onFailure(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString, Throwable throwable) {
//                Log.d(TAG, "Error: Failed to create a transaction");
//            }
//
//            @Override
//            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, String responseString) {
//                Log.d(TAG, "Output " + responseString);
//            }
//
//
//        });
//    }

//    private void submitNonce(String payment_method_nonce) {
//        ProgressDialogUtils.show(PaymentMethodActivity.this);
//        RequestInterface requestInterface =
//                ServiceGenerator.createService(RequestInterface.class, "admin", "admin");
//
//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("amount", amount);
//        jsonObject.addProperty("nonce", payment_method_nonce);
//        jsonObject.addProperty("user_id", userId/*"5d08adb80d46af5da56575a3"*/);
////        String credentials = "admin" + ":" + "admin";
////        final String basic = "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
//
//        Call<CheckoutData> call1 = requestInterface.getPayment(jsonObject);
//        call1.enqueue(new Callback<CheckoutData>() {
//            @Override
//            public void onResponse(Call<CheckoutData> call, Response<CheckoutData> response) {
//                ProgressDialogUtils.dismiss();
//                CheckoutData checkoutData = response.body();
//                if (response.isSuccessful()) {
//                    if (checkoutData.getStatus().equals("1")) {
//
//                        NotificationUtils.createNotification(getApplicationContext(), "Your payment from Paypal is successfull.");
//                        submitData(payment_method_nonce, AppConstant.PAYPAL, amount);
//                        //Toast.makeText(PaymentMethodActivity.this, "" + checkoutData.getMessage(), Toast.LENGTH_LONG).show();
//
//                    } else {
//                        Toast.makeText(PaymentMethodActivity.this, "" + checkoutData.getMessage(), Toast.LENGTH_LONG).show();
//                        NotificationUtils.createNotification(getApplicationContext(), "Your payment from Paypal is failed.");
//                        Intent intentFailed = new Intent(PaymentMethodActivity.this, PaymentFailedActivity.class);
//                        startActivity(intentFailed);
//
//
//                    }
//                } else {
//                    Toast.makeText(PaymentMethodActivity.this, "" + response.message(), Toast.LENGTH_LONG).show();
//                }
//
//
//            }
//
//            @Override
//            public void onFailure(Call<CheckoutData> call, Throwable t) {
//                ProgressDialogUtils.dismiss();
//                Toast.makeText(context, "Failure !", Toast.LENGTH_SHORT).show();
//            }
//
//        });
//    }
//
//
//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.ivPayPal:
//                startPayment();
//                break;
//            case R.id.iv_jenga:
//
//
//                String username = "2135484601";//marchant code
//                String password = "tIEAEg0PvOfhfhqRLf5y1owlaTHwnOny";
//                String apikey = "9k8GkBYs8x81ToRlKNq0Vo2MShLS";
//                String outletCode = "0000000000";
//                AuthData authData = new AuthData(username, password, username, outletCode, apikey);  //they have to be passed in this order.
//
//                PgwSdk.getSDK(authData, new SetUpResp() {
//                    @Override
//                    public void onConnected(PgwSdk pgwSdk) {
//
//                        Toast.makeText(context, "sdk connectd", Toast.LENGTH_SHORT).show();
//                        //sdk = pgwSdk; //am assuming you already declared this variable somewhere.
//                        //TODO: SDK is ready for you to consume.
//                        if (pgwSdk.isConnected()) {
//                            String merchantWebSite = "testcompany.com"; //merchant website
//                            String orderRef = "REF001BN"; //Random minimum 9 characters
//                            float orderAmount = 250f; // order reference
//                            String currency = "KES";
//                            String orderDescription = "This is an order. A test order";
//                            Calendar calendar = Calendar.getInstance();
//                            calendar.add(Calendar.DATE, 2);
//                            String customerName = "testCustomer"; //Merchant Name
//                            String outletCode = "0000000000";//Outlet Code
//                            Date orderExpiryDate = calendar.getTime();
//                            String customerFirstName = "John"; //your customer first name
//                            String customerlastName = "Doe"; //your customer last name
//                            String customerEmail = "johndoe@abc.com"; //your customer email
//                            String customerPostcodeZip = "00100"; //your customer postal code
//                            String customerCity = "Nairobi"; //your customer current city
//                            String customerAddress = "Test Address"; //your customer current address
//                            String customerSourceIP = ""; ////your customer IP. leave it blank if you are not sure
//                            String customerWebSite = "https://www.dinaro.app/";
////                            Intent intent = pgwSdk.createOrder(orderAmount, orderRef,
////                                    customerWebSite, customerName, outletCode,
////                                    orderExpiryDate, orderDescription, currency, customerlastName,
////                                    customerEmail, customerPostcodeZip, customerCity, customerAddress,
////                                    customerSourceIP);
//                            //startActivityForResult(intent, PgwSdk.CREATE_ORDER);
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onError(ErrorResponse errorResponse) {
//                        Toast.makeText(context, "sdk is not connedct", Toast.LENGTH_SHORT).show();
//                        //TODO: an error occurred. You have access to the message code and a message through ErrorResponse
//                    }
//
//                }, context);
//
//
//                break;
//            case R.id.btn_back_paymentmethod:
//                finish();
//                break;
//
//            case R.id.iv_paymentback:
//                finish();
//                break;
//
//            case R.id.ivCard:
//                Intent intent = new Intent(PaymentMethodActivity.this, SavedCardActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("payment", "payment value");
//                intent.putExtra("transId", transactionId);
//                intent.putExtras(bundle);
//                intent.putExtra("paymentRequest", paymentRequest);
//                startActivity(intent);
//                break;
//
//            case R.id.ivMpesa:
//                // showCheckoutDialog();
//                //String phone_number = "+25471116660";
//
//                String phone_number = "0" + PrefManagerNew.getStringPreferences(PaymentMethodActivity.this, AppConstant.MOBILE);
//
//                //Toast.makeText(this,""+phone_number,Toast.LENGTH_LONG);
//
//                // System.out.println(""+PrefManagerNew.getStringPreferences(PaymentMethodActivity.this, AppConstant.MOBILE));
//
//                ProgressDialogUtils.show(PaymentMethodActivity.this);
//                mpesaPayment.performSTKPush(phone_number, paymentRequest, PaymentMethodActivity.this, 1.00);
//
//                break;
//
//            default:
//                break;
//
//        }
//    }

    public void showCheckoutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter Customer\'s Safaricom phone number to checkout Kshs 1");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_PHONE);
        input.setHint("07xxxxxxxx");
        builder.setView(input);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String phone_number = input.getText().toString();
                ProgressDialogUtils.show(PaymentMethodActivity.this);
                mpesaPayment.performSTKPush(phone_number, paymentRequest, PaymentMethodActivity.this, 1.00);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }
//    private void getBundle(){
//        bundle = getIntent().getExtras() ;
//         amount=bundle.getString( "Amount" );
//
//    }


    public void submitData(String payment_method_nonce, String PaymentType, String amount) {
        if (CommonUtils.isOnline(getApplication())) {

            try {
                ProgressDialogUtils.show(getApplication());
                paymentRequest.setPaymentId(payment_method_nonce);
                paymentRequest.setPaymentType(PaymentType);
                paymentRequest.setAmount(amount);

                Api api = RequestController.createService(Api.class);
                api.setPaymentDetails(paymentRequest).enqueue(new Callback<DetailsBase>() {
                    @Override
                    public void onResponse(Call<DetailsBase> call, Response<DetailsBase> response) {
                        ProgressDialogUtils.dismiss();
                        if (response != null && response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                String id = response.body().getResult().getData().getId();
                                String msg = response.body().getMessage();
                                //Toast.makeText(PaymentMethodActivity.this, msg, Toast.LENGTH_SHORT).show();

                                Intent intentSucess = new Intent(PaymentMethodActivity.this, PaymentSuccessActivity.class);
                                intentSucess.putExtra("trans_id", id);
                                startActivity(intentSucess);

                            } else {
                                String msg = response.body().getMessage();
                                Toast.makeText(PaymentMethodActivity.this, "the data 1 is -" + msg, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(PaymentMethodActivity.this, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailsBase> call, Throwable t) {
                        ProgressDialogUtils.dismiss();
                        String msg = t.getMessage();
                        Toast.makeText(PaymentMethodActivity.this, msg, Toast.LENGTH_SHORT).show();

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }
    }


//    @Override
//    public void onStkpushSuccess(String transactionId) {
//        //Toast.makeText(PaymentMethodActivity.this, "Success", Toast.LENGTH_SHORT).show();
//        ProgressDialogUtils.dismiss();
//        this.transactionId = transactionId;
//        int finalAmount = Integer.parseInt(amount) + AMOUNT;
//        submitData(transactionId, AppConstant.MPESA, String.valueOf(finalAmount));
//    }
//
//    @Override
//    public void onStkpushFailure() {
//        ProgressDialogUtils.dismiss();
//        if (CommonUtils.isOnline(PaymentMethodActivity.this)) {
//
//            Toast.makeText(PaymentMethodActivity.this, "Failure: Invalid PhoneNumer", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(PaymentMethodActivity.this, "Please check your Internet connection", Toast.LENGTH_SHORT).show();
//        }
//    }
}
