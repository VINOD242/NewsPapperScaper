package com.newspapperscrap.scrap.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newspapperscrap.scrap.model.ArticlesData;
import com.newspapperscrap.scrap.repo.ScrapRepo;

@Service
public class DataBaseOPerations {

	@Autowired
	private ScrapRepo repo;

	public List<String> selectAuthorName() {

		List<String> authorName = repo.findAuthorName();
		authorName.removeAll(Arrays.asList("", null));
		return authorName;
	}

	public List<String> articleBasedAuthorName(String authorName) {

		List<String> articleName = repo.findArticleByAuthorName(authorName);
		articleName.removeAll(Arrays.asList("", null));
		return articleName;
	}

	public List<ArticlesData> articleBasedOnDescription(String authorName, String description) {
		return repo.findArticleByAuthorAndDescription(authorName, description);
	}
}
