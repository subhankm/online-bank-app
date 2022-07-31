package com.subhan.onlinebank.entiry;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
@Table
public class Customer {

	@Id
	private String custId;
	private String name;
	private String email;
	private String phone;
	private String address;
	private String accId;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Kolkata")
	@CreationTimestamp
	private LocalDateTime created;	
	
}
