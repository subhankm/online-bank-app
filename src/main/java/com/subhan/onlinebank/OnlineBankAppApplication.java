package com.subhan.onlinebank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.subhan.onlinebank.util.LoadData;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2  //http://localhost:8081/v2/api-docs http://localhost:8081/swagger-ui.html
public class OnlineBankAppApplication implements CommandLineRunner {
	private static Logger log = LoggerFactory.getLogger(OnlineBankAppApplication.class);
	@Autowired
	public LoadData data;
	public static void main(String[] args) {
		SpringApplication.run(OnlineBankAppApplication.class, args);	
		
	}
	 @Override
	    public void run(String... args) {
	      //  log.info("EXECUTING : command line runner");
	     //  data.createAccount();
	    }
}
