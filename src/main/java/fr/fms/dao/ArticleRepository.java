package fr.fms.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import fr.fms.entities.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

	Page<Article> findByDescriptionContainsOrBrandContains(String description, String brand, Pageable pageable);

	Page<Article> findByCategoryId(Long categoryId, Pageable pageable);

	List<Article> findByCategoryId(Long categoryId);

}
