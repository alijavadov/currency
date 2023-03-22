package com.digirella.task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.bind.JAXBException;
import java.io.IOException;

@SpringBootApplication
public class TaskApplication {

	public static void main(String[] args) throws IOException, JAXBException {
		SpringApplication.run(TaskApplication.class, args);
			}

}
