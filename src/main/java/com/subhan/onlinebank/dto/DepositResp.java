package com.subhan.onlinebank.dto;

import lombok.Data;

@Data
public class DepositResp {
	private String accNum;	
	private String message;
	private double availableAmount;
	private String status;
}
