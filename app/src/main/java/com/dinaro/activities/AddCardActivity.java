
package com.dinaro.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.dinaro.R;
import com.dinaro.models.RequestModel.addCard.addCardBase;
import com.dinaro.models.RequestModel.addCard.addCardData;
import com.dinaro.models.RequestModel.addCard.addCardResult;
import com.dinaro.service.Api;
import com.dinaro.service.RequestController;
import com.dinaro.service.apiRequest.AddCardRequest;
import com.dinaro.utils.AppConstant;
import com.dinaro.utils.CardType;
import com.dinaro.utils.CommonUtils;
import com.dinaro.utils.CreditCardExpiryTextWatcher;
import com.dinaro.utils.ProgressDialogUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCardActivity extends AppCompatActivity implements View.OnClickListener {

    public Button btn_submit_addcard;
    public EditText etNameCard, etCardN, etDate, etCvc;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    private String name, card_number, cvc, date, userId;
    private String holder_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_add_card);
        sharedPreferences = getApplication().getSharedPreferences(AppConstant.SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        userId = sharedPreferences.getString(AppConstant.USER_ID_BY_SIGNUP, "");
        System.out.println("user id=" + userId);
        btn_submit_addcard = findViewById(R.id.btn_submit_addCard);
        etNameCard = findViewById(R.id.etNameCard);
        etNameCard.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
        etCardN = findViewById(R.id.etCardNumber);
        etDate = findViewById(R.id.etDate);
        etCvc = findViewById(R.id.etCvc);
        TextView tvCancel = findViewById(R.id.tvCancel);
        btn_submit_addcard.setOnClickListener(this);

        etDate.addTextChangedListener(new CreditCardExpiryTextWatcher(etDate));

        tvCancel.setOnClickListener(this);



    }

    private boolean checkValidation() {
        name = etNameCard.getText().toString().trim();
        card_number = etCardN.getText().toString().trim();
        date = etDate.getText().toString().trim();
        cvc = etCvc.getText().toString().trim();

        if (name.length() == 0) {
            etNameCard.requestFocus();
            etNameCard.setError("Please enter user name.");
            return false;
        } else if (card_number.length() == 0) {
            etCardN.requestFocus();
            etCardN.setError("Please enter card number.");
            return false;
        } else if (!CardType.isLuhnValid(card_number)) {
            etCardN.requestFocus();
            etCardN.setError("Please enter valid card number.");
            return false;

        } else if (date.length() == 0 || !CreditCardUtils.isValidDate(date)) {
            etDate.requestFocus();
            etDate.setError("Please enter valid expiry date.");
            return false;
        } else if (cvc.length() == 0) {
            etCvc.requestFocus();
            etCvc.setError("Please enter cvc number.");
            return false;
        }

        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_addCard:
                if (checkValidation()) {
                    saveCard();
//                    Toast.makeText(this, "Please Wait.", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.tvCancel:
                //startActivity(new Intent(AddCardActivity.this, SavedCardActivity.class));
                finish();
                break;

        }

    }

    private void saveCard() {


        if (CommonUtils.isOnline(getApplication())) {
            try {
                ProgressDialogUtils.show(AddCardActivity.this);
                final AddCardRequest addCardRequest = new AddCardRequest();
                addCardRequest.setCardName(name);
                addCardRequest.setCardNumber(card_number);
                addCardRequest.setDate(date);
                addCardRequest.setUserId(userId);
                addCardRequest.setCardType(CardType.forCardNumber(card_number).name());

                CardType.forCardNumber(card_number);

                Api api = RequestController.createService(Api.class);
                api.saveCard(addCardRequest).enqueue(new Callback<addCardBase>() {
                    @Override
                    public void onResponse(Call<addCardBase> call, Response<addCardBase> response) {
                        ProgressDialogUtils.dismiss();
                        if (response != null && response.body() != null) {
                            if (response.body().getStatus() == 1) {
                                addCardResult addCardResult = response.body().getResult();
                                addCardData data = addCardResult.getData();
                                holder_number = data.getCardNumber();
                                finish();
                                String msg = response.body().getMessage();
                                Toast.makeText(AddCardActivity.this, msg, Toast.LENGTH_SHORT).show();
                            } else {
                                String msg = response.body().getMessage();
                                Toast.makeText(AddCardActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(AddCardActivity.this, R.string.NoData, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<addCardBase> call, Throwable t) {
                        ProgressDialogUtils.dismiss();
                        Toast.makeText(AddCardActivity.this, R.string.failure, Toast.LENGTH_SHORT).show();
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, R.string.networ_error, Toast.LENGTH_SHORT).show();
        }
    }


}

