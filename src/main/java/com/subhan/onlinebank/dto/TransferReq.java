package com.subhan.onlinebank.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class TransferReq {	
	@NotBlank(message="Please enter your account number")
	private String fromAcc;
	@NotBlank(message="Please enter  account beneficiary number")
	private String toAcc;
	@DecimalMin(value = "1.0", message = "Please Enter a valid Transfer Amount")
	private double amt;
}
