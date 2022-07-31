package com.subhan.onlinebank.dto;

import lombok.Data;
@Data
public class CreateAccResp {
	
	private String accNum;	
	private String custId;	
	private double bal;
	private String status;
	private String message;	
		
}
