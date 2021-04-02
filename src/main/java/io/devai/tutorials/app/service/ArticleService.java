package io.devai.tutorials.app.service;

import io.devai.tutorials.app.dao.ArticleRepository;
import io.devai.tutorials.app.model.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class ArticleService {

    @Autowired
    ArticleRepository repository;

    public void create(Article request) {
        Article existing = repository.findByUrl(request.getUrl());
        if (existing != null) {
            throw new IllegalArgumentException("Article with given url already exists");
        }
        repository.save(request);
    }

    public void update(Article request) {
        Optional<Article> existing = repository.findById(request.getId());
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Article with given id does not exist");
        }
        Article article = existing.get();
        article.setTitle(request.getTitle());
        article.setUrl(request.getUrl());
        article.setTags(request.getTags());
        article.setContent(request.getContent());
        article.setUpdatedAt(new Date());
        repository.save(article);
    }

    public void delete(String id) {
        Optional<Article> existing = repository.findById(id);
        if (existing.isEmpty()) {
            throw new IllegalArgumentException("Article with given id does not exist");
        }
        repository.deleteById(id);
    }
}
