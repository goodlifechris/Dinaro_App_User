package com.dinaro.service;

import com.dinaro.models.RequestModel.PaypalPayment.CheckoutData;
import com.dinaro.models.RequestModel.PaypalPayment.TokenResponseDto;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;


public interface RequestInterface {

    @GET("get/token")
    Call<TokenResponseDto> getClientToken();

    @POST("checkout/code")
    @Headers("Content-Type: application/json")
    Call<CheckoutData> getPayment(
                                  @Body JsonObject jsonObject);

}
