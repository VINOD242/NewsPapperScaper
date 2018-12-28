package com.newspapperscrap.scrap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Article_strore")
public class ArticlesData {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer idno;
	@Column(name = "AuthorName")
	private String authorName;
	@Column(name = "ArticleTitle")
	private String articleTitle;
	@Column(name = "Description")
	private String description;

	public String getAuthorName() {
		return authorName;
	}

	public void setAuthorName(String authorName) {
		this.authorName = authorName;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
