package com.newspapperscrap.scrap.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.newspapperscrap.scrap.model.ArticlesData;

@Repository
public interface ScrapRepo extends JpaRepository<ArticlesData, Integer> {
	@Query("SELECT t.authorName FROM ArticlesData t ")
	public List<String> findAuthorName();

	@Query("SELECT t.articleTitle FROM ArticlesData t where t.authorName = :authorName")
	List<String> findArticleByAuthorName(@Param("authorName") String authorName);

	@Query("SELECT t FROM ArticlesData t where t.authorName = ?1 AND t.description = ?2")
	List<ArticlesData> findArticleByAuthorAndDescription(String authorName, String desc);

}
