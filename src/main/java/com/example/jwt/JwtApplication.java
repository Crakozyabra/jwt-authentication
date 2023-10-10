package com.example.jwt;

import lombok.extern.slf4j.Slf4j;
import org.h2.tools.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

import java.sql.SQLException;

@SpringBootApplication
@Slf4j
public class JwtApplication {

	@Order(1)
	@Bean(initMethod = "start", destroyMethod = "stop")
	Server h2Server() throws SQLException {
		log.info("Start H2 TCP server");
		return Server.createTcpServer("-tcp", "-tcpAllowOthers", "-tcpPort", "9092");
	}

	public static void main(String[] args) {
		SpringApplication.run(JwtApplication.class, args);
	}
}