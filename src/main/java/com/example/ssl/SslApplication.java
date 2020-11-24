package com.example.ssl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;

@SpringBootApplication
public class SslApplication {

	public static void main(String[] args) {
	
		//프로세스 실행 시 pid파일 생성을 위해 추가 
		SpringApplication app = new SpringApplication(SslApplication.class);
		app.addListeners(new ApplicationPidFileWriter());
		app.run(args); 

	}

}
