package com.subhan.onlinebank.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccReq {
	@NotBlank(message="Please enter your name")
	private String name;
	@Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
	private String email;
	@Pattern(regexp =  "^\\d{10}$" , message="Please enter valid phone number")
	private String phone;
	@NotBlank(message="Please enter your address")
	private String address;

	
}
