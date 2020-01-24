package com.dinaro.models.RequestModel.notification;

import com.google.gson.annotations.SerializedName;

public class GetDataModel{

	@SerializedName("result")
	private Result result;

	@SerializedName("response_code")
	private int responseCode;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private int status;

	public void setResult(Result result){
		this.result = result;
	}

	public Result getResult(){
		return result;
	}

	public void setResponseCode(int responseCode){
		this.responseCode = responseCode;
	}

	public int getResponseCode(){
		return responseCode;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(int status){
		this.status = status;
	}

	public int getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"GetDataModel{" + 
			"result = '" + result + '\'' + 
			",response_code = '" + responseCode + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}