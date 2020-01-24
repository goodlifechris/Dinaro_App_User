package com.dinaro.models.RequestModel.notification;


import com.google.gson.annotations.SerializedName;

public class Result{

	@SerializedName("data")
	private Data data;

	public void setData(Data data){
		this.data = data;
	}

	public Data getData(){
		return data;
	}

	@Override
 	public String toString(){
		return 
			"Result{" + 
			"data = '" + data + '\'' + 
			"}";
		}
}