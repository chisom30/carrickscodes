package com.ebanking;

import com.ebanking.util.dataLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EbankingApplication {

		//implements CommandLineRunner {
	//@Autowired
	//private com.ebanking.util.dataLoader dataLoader;
	public static void main(String[] args) {
		SpringApplication.run(EbankingApplication.class, args);
	}
//	@Override
//	public void run(String... args) throws Exception {
//		dataLoader.save();
//	}
}