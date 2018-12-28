package com.newspapperscrap.scrap.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.newspapperscrap.scrap.NewsPapperScrapApp;
import com.newspapperscrap.scrap.model.ArticlesData;
import com.newspapperscrap.scrap.service.DataBaseOPerations;

@RestController
@RequestMapping("/news")
public class RequestHandling {
	@Autowired
	private DataBaseOPerations dataBaseOs;

	private static final Logger LOGGER = LogManager.getLogger(RequestHandling.class);

	@GetMapping("/availableAuthor")
	public List<String> getAvailableAuthor() {
		LOGGER.info("Get request to find author name");
		return dataBaseOs.selectAuthorName();
	}

	@GetMapping("/articleByAuthor")
	public List<String> selectArticleBasedAuthor(@RequestParam("authorname") String authorName) {
		LOGGER.info("Get request to find article by author");

		return dataBaseOs.articleBasedAuthorName(authorName);
	}

	@GetMapping("/articleByAuthorAndDescription")
	public List<ArticlesData> selectArticleBasedAuthorAndDesc(@RequestParam("authorname") String authorName,
			@RequestParam("desc") String desc) {
		LOGGER.info("Get request to find article by author and description");

		return dataBaseOs.articleBasedOnDescription(authorName, desc);

	}

}
