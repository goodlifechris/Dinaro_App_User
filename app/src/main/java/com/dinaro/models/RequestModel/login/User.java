package com.dinaro.models.RequestModel.login;

import com.dinaro.service.BaseResponse;

public class User extends BaseResponse {
    private LogInResult result;

    public LogInResult getResult() {
        return result;
    }

    public void setResult(LogInResult result) {
        this.result = result;
    }

}
