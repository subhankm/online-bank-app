package com.subhan.onlinebank.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class DepositReq {
	@NotBlank(message="Please enter your account number")
	private String accNum;
	@DecimalMin(value = "1.0", message = "Please Enter a valid Deposit Amount")
	private double amt;
}
