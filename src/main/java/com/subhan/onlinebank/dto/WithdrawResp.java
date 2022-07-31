package com.subhan.onlinebank.dto;

import lombok.Data;

@Data
public class WithdrawResp {
	private String accNum;		
	private double availableAmount;
	private String message;
	private String status;
}
