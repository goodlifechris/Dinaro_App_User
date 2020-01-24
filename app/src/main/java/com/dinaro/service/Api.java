package com.dinaro.service;

import com.dinaro.models.RequestModel.DeleteCard;
import com.dinaro.models.RequestModel.ForgetPasswordEmail.Base;
import com.dinaro.models.RequestModel.PaypalPayment.CheckoutData;
import com.dinaro.models.RequestModel.PaypalPayment.TokenResponseDto;
import com.dinaro.models.RequestModel.addCard.addCardBase;
import com.dinaro.models.RequestModel.addFavourite.favouriteBase;
import com.dinaro.models.RequestModel.cardList.cardListBase;
import com.dinaro.models.RequestModel.fAQ.FaqBase;
import com.dinaro.models.RequestModel.forgetPin.ForgetUser;
import com.dinaro.models.RequestModel.getFavourite.getBase;
import com.dinaro.models.RequestModel.getProfile.profileBase;
import com.dinaro.models.RequestModel.helpSupport.HelpResult;
import com.dinaro.models.RequestModel.legal.legalBase;
import com.dinaro.models.RequestModel.login.User;
import com.dinaro.models.RequestModel.loginPin.Userpin;
import com.dinaro.models.RequestModel.notification.GetDataModel;
import com.dinaro.models.RequestModel.otp.OtpResult;
import com.dinaro.models.RequestModel.payBill.payBillBase;
import com.dinaro.models.RequestModel.paymentDetails.DetailsBase;
import com.dinaro.models.RequestModel.pin.ResultPin;
import com.dinaro.models.RequestModel.rating.RatingBase;
import com.dinaro.models.RequestModel.recipt.ReciptResponse;
import com.dinaro.models.RequestModel.resentActivity.RecentBase;
import com.dinaro.models.RequestModel.updateProfile.updateBase;
import com.dinaro.models.SignUpEmailData;
import com.dinaro.models.SingupVerifyModel;
import com.dinaro.models.billpaymodel.BillPayResponseDto;
import com.dinaro.service.apiRequest.AddCardRequest;
import com.dinaro.service.apiRequest.ResetPasswordRequest;
import com.dinaro.service.apiRequest.ForgetPasswordRequest;
import com.dinaro.service.apiRequest.ForgotEmailModel;
import com.dinaro.service.apiRequest.ForgotRequest;
import com.dinaro.service.apiRequest.HelpSupportRequest;
import com.dinaro.service.apiRequest.LoginRequest;
import com.dinaro.service.apiRequest.LogoutRequest;
import com.dinaro.service.apiRequest.PinConformRequest;
import com.dinaro.service.apiRequest.PinRequest;
import com.dinaro.service.apiRequest.RegisterRequest;
import com.dinaro.service.apiRequest.UpdateProfileRequest;
import com.dinaro.service.apiRequest.changePasswordRequest;
import com.dinaro.service.apiRequest.paymentRequest;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {
    //Register

    @POST("signup")
    Call<User> getData(@Body RegisterRequest registerRequest);

    //Login through email

    @POST("login")
    Call<User> getLoginDataByEmail(@Body LoginRequest loginRequest);

    //Login through pin

    @POST("login")
    Call<Userpin> getLoginDataByPin(@Body LoginRequest loginRequest);

    //Logout

    @POST("logout")
    Call<BaseResponse> getLogoutData(@Body LogoutRequest logoutRequest);

    //Forgot pin through mobile

    @POST("phone/forgot/password")
    Call<ForgetUser> getMobile(@Body ForgotRequest forgotRequest);


    //Forgot password through email

    @POST("email/forgot/password")
    Call<Base> getEmail(@Body ForgetPasswordRequest forgetPasswordRequest);

    //verify otp by email

    @POST("verify/email/otp")
    Call<com.dinaro.models.RequestModel.VerifyEmailOtp.Result> verifyOtp(@Body ForgotEmailModel forgotEmailModel);


    @POST("send/otp")
    Call<BaseResponse> sendOtpByEmail(
            @Body SignUpEmailData signUpEmailData
            );

    @POST("verify/otp")
    Call<BaseResponse> verifyOtpByEmail(
            @Body SingupVerifyModel signUpEmailData
    );


    //verify otp from mobile

    @POST("verify/mobile/otp")
    Call<OtpResult> getOtp(@Body PinRequest pinRequest);

    //getUserPin api

    @POST("setpin")
    Call<ResultPin> getPin(@Body PinConformRequest pinConformRequest);

    //reset password
    @POST("reset/password")
    Call<BaseResponse> getNewPassword(@Body ResetPasswordRequest resetPasswordRequest);

    //FAQ
    @GET("faq")
    Call<FaqBase> getQuestions();

    //help and support
    @POST("help/and/support")
    Call<HelpResult> setData(@Body HelpSupportRequest helpSupportRequest);

    //change password
    @POST("change/password")
    Call<BaseResponse> setNewPassword(@Body changePasswordRequest changePasswordRequest);

    //update profile
    @POST("update/profile")
    Call<updateBase> updateProfile(@Body UpdateProfileRequest updateProfileRequest);  //update profile

    @POST("favourite/all")
    Call<favouriteBase> addAndRemoveFavouriteApi(@Body JsonObject jsonObject);

    //legal
    @GET("legal")
    Call<legalBase> getLegalContent();

    //get profile
    @FormUrlEncoded
    @POST("get/profile")
    Call<profileBase> getUserProfile(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("notification/get")
    Call<GetDataModel> getNotificationStatus(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("notification/add")
    Call<profileBase> updateStatus(@Field("user_id") String userId,
                                   @Field("sendSuccess") int sendSuccess,
                                   @Field("sendFail") int sendFail);
    // Call<profileBase> updateStatus(@Field("user_id") String userId, @Field("sendSuccess") int sendSuccess, @Field("sendFail") int sendFail);

 /*   //utilities/Restaurant
    @GET("fetch/pay/bill")
    Call<payBillBase>getList();*/

    //add card
    @POST("add/card")
    Call<addCardBase> saveCard(@Body AddCardRequest addCardRequest);

    //show card
    @FormUrlEncoded
    @POST("card/list")
    Call<cardListBase> getCards(@Field("user_id") String userId);

    //delete card
    @FormUrlEncoded
    @POST("delete/card")
    Call<DeleteCard> deleteCards(@Field("user_id") String userId,
                                 @Field("card_id") String cardId);



    //utilities/Restaurant

    @FormUrlEncoded
    @POST("fetch/pay/bill")
    Call<BillPayResponseDto> getList(@Field("user_id") String userId);

    @FormUrlEncoded
    @POST("search/fetch/pay/bill")
    Call<BillPayResponseDto> getSearch(@Field("user_id") String userId,
                                       @Field("search") String search);


    //add favourite
    @FormUrlEncoded
    @POST("add/favourite")
    Call<favouriteBase> addFavourite(@Field("user_id") String userId,
                                     @Field("card_id") String cardId,
                                     @Field("type") String type);


    //get favourite card
    @FormUrlEncoded
    @POST("get/favourite")
    Call<getBase> getFavourite(@Field("user_id") String userId,
                               @Field("card_id") String cardId);

    //post payment details
    @POST("bill/payment")
    Call<DetailsBase> setPaymentDetails(@Body paymentRequest paymentRequest);

    //get payment details

    @FormUrlEncoded
    @POST("get/bill/payment")
    Call<RecentBase> getPaymentList(@Field("user_id") String userId);

    //post rating
    @FormUrlEncoded
    @POST("add/rating")
    Call<RatingBase> setRating(@Field("transaction_id") String transactionId,
                               @Field("rate") String rating);
    //get rating
    @FormUrlEncoded
    @POST("get/rating")
    Call<RatingBase> getRating(@Field("transaction_id") String transactionId);
    @FormUrlEncoded
    @POST("recent/transactions/get")
    Call<ReciptResponse> getRecipt(@Field("user_id") String userId);

    //@GET("http://ec2-3-8-105-184.eu-west-2.compute.amazonaws.com/website/public/api/get/token")
    //Call<PaymentResult> gettoken();
    //get transaction by date
   /* @FormUrlEncoded
    @POST("")
    Call<TransactionDate> getTransaction(@Field("date") String date);*/



}
