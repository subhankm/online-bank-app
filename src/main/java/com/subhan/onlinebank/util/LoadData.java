package com.subhan.onlinebank.util;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.subhan.onlinebank.dto.CreateAccReq;
import com.subhan.onlinebank.dto.CreateAccResp;

@Component
public class LoadData {
	String data = "Smith#smith.johnson@gmail.com#6311821779#Indianapolis, Indiana |Williams#williams.brown@gmail.com#6949917793#Chicago, Illinois |Jones#jones.miller@gmail.com#6654415391#San Francisco, California |Davis#davis.garcia@gmail.com#9124368922#Philadelphia, Pennsylvania |Rodriguez#rodriguez.wilson@gmail.com#7882431450#Baltimore, Maryland |Martinez#martinez.anderson@gmail.com#7095980445#Tucson, Arizona |Taylor#taylor.thomas@gmail.com#6391595714#Kansas City, Missouri |Hernandez#hernandez.moore@gmail.com#9952847463#Tucson, Arizona |Martin#martin.jackson@gmail.com#8295673517#Milwaukee, Wisconsin |Thompson#thompson.white@gmail.com#6702362344#Albuquerque, N.M. |Lopez#lopez.lee@gmail.com#8503750303#Dallas, Texas |Gonzalez#gonzalez.harris@gmail.com#8323700785#New York, New York |Clark#clark.lewis@gmail.com#8926691565#Denver, Colorado |Robinson#robinson.walker@gmail.com#7655591841#San Diego, California |Perez#perez.hall@gmail.com#7813733323#Detroit, Michigan |Young#young.allen@gmail.com#6833878575#Philadelphia, Pennsylvania |Sanchez#sanchez.wright@gmail.com#8935364922#Memphis, Tennessee |King#king.scott@gmail.com#8928886422#Miami, Florida |Green#green.baker@gmail.com#8552680835#Houston, Texas |Adams#adams.nelson@gmail.com#8559943917#San Diego, California |Hill#hill.ramirez@gmail.com#9855705776#Baltimore, Maryland |Campbell#campbell.mitchell@gmail.com#9235761607#Louisville, Kentucky2 |Roberts#roberts.carter@gmail.com#9475451420#Jacksonville, Florida |Phillips#phillips.evans@gmail.com#6422836592#Nashville-Davidson, Tennessee1 |Turner#turner.torres@gmail.com#7401512389#Las Vegas, Nevada";
	private RestTemplate rt = new RestTemplate();
	final static String CREATE_API = "http://localhost:8081/bank/create";
	final static String TRANSFER_API = "";
	final static String WITHDRAW_API = "";
	final static String DEPOSIT_API = "";

	public void createAccount() {
		for (String st : data.split("\\|")) {
			String[] d = st.split("#");
			CreateAccResp result = rt.postForObject(CREATE_API, new CreateAccReq(d[0], d[1], d[2], d[3]),
					CreateAccResp.class);
			System.out.println(result.toString());
		}
	}

	public void transfer() {

	}

	public void withdraw() {

	}

	public void deposit() {

	}

}
