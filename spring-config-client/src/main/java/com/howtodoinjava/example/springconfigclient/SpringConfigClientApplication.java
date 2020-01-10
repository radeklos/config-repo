package com.howtodoinjava.example.springconfigclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SpringConfigClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringConfigClientApplication.class, args);
	}
}


@Configuration
class Config {

	@Bean
	@RefreshScope
	public Data data(@Value("${foo:Config Server is not working. Please check...}") String msg) {
		return new Data(msg);
	}

	@Bean
	public String msg(@Value("${foo:Config Server is not working. Please check...}") String msg) {
		return msg;
	}

}

@RefreshScope
@RestController
class MessageRestController {

	private String msgValue;
	private String msgBean;
	private Data data;

	public MessageRestController(Data data, String msgBean, @Value("${foo:Config Server is not working. Please check...}") String msgValue) {
		this.data = data;
		this.msgValue = msgValue;
		this.msgBean = msgBean;
	}

	@GetMapping("/msg")
	public Response getMsg() {
		return new Response(null, msgValue, msgBean);
	}

	@GetMapping("/data")
	public String getData() {
		return data.getMsg();
	}
}

class Data {

	private final String msg;

	public Data(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

}

class Response {
	public final Data data;
	public final String msgValue;
	public final String msgBean;

	public Response(Data data, String msgValue, String msgBean) {
		this.data = data;
		this.msgValue = msgValue;
		this.msgBean = msgBean;
	}
}