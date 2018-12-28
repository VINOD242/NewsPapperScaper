package com.newspapperscrap.scrap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.newspapperscrap.scrap.service.ExtractingData;

@SpringBootApplication
public class NewsPapperScrapApp implements CommandLineRunner {
	private static final Logger LOGGER = LogManager.getLogger(NewsPapperScrapApp.class);

	@Autowired
	private ExtractingData extractData;

	public static void main(String[] args) {
		SpringApplication.run(NewsPapperScrapApp.class, args);

	}

	public void run(String... args) throws Exception {
		LOGGER.debug("BOOT STARTED UP NEW VERSION...");
		LOGGER.info("Started scrapping url \"https://www.thehindu.com/archive/\"");
		LOGGER.info("pls wait to complete scrap....");
		extractData.extractData("https://www.thehindu.com/archive/");
		LOGGER.info("Scrapping complted ready to send Api request");
	}
}
