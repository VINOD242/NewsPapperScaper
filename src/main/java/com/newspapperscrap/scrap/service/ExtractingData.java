package com.newspapperscrap.scrap.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.newspapperscrap.scrap.NewsPapperScrapApp;
import com.newspapperscrap.scrap.model.ArticlesData;
import com.newspapperscrap.scrap.repo.ScrapRepo;

@Service
public class ExtractingData {
	private static final Logger LOGGER = LogManager.getLogger(NewsPapperScrapApp.class);

	@Autowired
	private ScrapRepo repo;

	@Value("${spring.driver.chromdriver.path}")
	private String chromeDriverPath;

	public void extractData(String url) {
		WebDriver driver = getChromeHeadlessBrowser();
		try {
			String document = loadDocumentUseSelenium(driver, url);
			Document doc = convertSeleniumDocumentIntoJsoup(document);
			Elements eles = doc.getElementsByClass("story-card-news");
			List<ArticlesData> articles = selectArticleTitle(eles);
			insertIntoDataBase(articles);
			LOGGER.info("Scraping complted....");
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		} finally {
			driver.quit();
		}
	}

	private void insertIntoDataBase(List<ArticlesData> article) {

		for (ArticlesData data : article) {
			repo.save(data);
		}
	}

	private List<ArticlesData> selectArticleTitle(Elements eles) {

		List<ArticlesData> articleDataCollection = new ArrayList<>();

		for (Element ele : eles) {
			try {
				ArticlesData data = new ArticlesData();
				String authorName = selectAuthorName(ele);
				String tilte = selectTitleName(ele);
				String description = selectDescription(ele);
				data.setArticleTitle(tilte);
				data.setAuthorName(authorName);
				data.setDescription(description);
				articleDataCollection.add(data);
			} catch (Exception e) {
				LOGGER.error(e.getMessage());
				continue;
			}
		}
		return articleDataCollection;
	}

	private String selectAuthorName(Element ele) {
		String authorName = "";
		try {

			authorName = ele.select(".story-card-33-author-name.lnk").get(0).ownText();

		} catch (IndexOutOfBoundsException e) {
			authorName = ele.select(".story-card-33-author-name.no-lnk").get(0).ownText();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());

		}
		return authorName;
	}

	private String selectTitleName(Element ele) {
		String title = "";
		try {

			title = ele.select(".story-card75x1-text").get(0).ownText();

		} catch (Exception e) {
			LOGGER.error(e.getMessage());

		}
		return title;
	}

	private String selectDescription(Element eles) {

		String description = "";
		try {
			description = eles.select(".light-gray-color.story-card-33-text.hidden-xs").get(0).ownText();
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}
		return description;
	}

	private Document convertSeleniumDocumentIntoJsoup(String document) {

		return Jsoup.parse(document);
	}

	private String loadDocumentUseSelenium(WebDriver webDriver, String url) {

		String document = "";

		try {
			webDriver.get(url);
			webDriver.findElement(By.xpath("/html/body/div[2]/header/div/div/div/section[1]/div[2]/form/input[1]"))
					.sendKeys("newspaper articles");

			webDriver.findElement(By.xpath("/html/body/div[2]/header/div/div/div/section[1]/div[2]/form/button"))
					.click();
			WebDriverWait wait = new WebDriverWait(webDriver, 60);
			wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
			document = webDriver.getPageSource();
		} catch (Exception e) {

		}

		return document;
	}

	public WebDriver getChromeHeadlessBrowser() {

		WebDriver driver = null;
		try {
			System.setProperty("webdriver.chrome.driver", chromeDriverPath);
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--no-sandbox");
			driver = new ChromeDriver(options);

		} catch (Exception e) {
			LOGGER.error(e.getMessage());
		}

		return driver;

	}
}
