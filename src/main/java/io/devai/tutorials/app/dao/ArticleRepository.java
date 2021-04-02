package io.devai.tutorials.app.dao;

import io.devai.tutorials.app.model.Article;
import org.springframework.data.repository.CrudRepository;

public interface ArticleRepository extends CrudRepository<Article, String> {
    Article findByUrl(String url);
}
