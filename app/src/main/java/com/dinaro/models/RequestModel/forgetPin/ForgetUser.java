package com.dinaro.models.RequestModel.forgetPin;

import com.dinaro.service.BaseResponse;

public class ForgetUser extends BaseResponse {
    private ForgetResult result;

    public ForgetResult getResult() {
        return result;
    }

    public void setResult(ForgetResult result) {
        this.result = result;
    }

}
