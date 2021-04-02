package io.devai.tutorials.app.controller;

import io.devai.tutorials.app.dao.ArticleRepository;
import io.devai.tutorials.app.model.Article;
import io.devai.tutorials.app.model.User;
import io.devai.tutorials.app.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class ArticleController {

    @Autowired
    ArticleService service;

    @Autowired
    ArticleRepository repository;

    @GetMapping("/articles")
    public ResponseEntity articles() {
        Iterable<Article> articles = repository.findAll();
        return ResponseEntity.ok(articles);
    }

    @GetMapping("/articles/{url}")
    public ResponseEntity article(@PathVariable("url") String url) {
        Article article = repository.findByUrl(url);
        return ResponseEntity.ok(article);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PostMapping(value = "/articles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> create(@RequestBody Article request) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("[create] user={}, article={}", currentUser.getEmail(), request.getUrl());
        service.create(request);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @PutMapping(value = "/articles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> update(@RequestBody Article request) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("[update] user={}, article={}", currentUser.getEmail(), request.getUrl());
        service.update(request);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    @DeleteMapping(value = "/articles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete(@PathVariable("id") String id) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("[delete] user={}, article={}", currentUser.getEmail(), id);
        service.delete(id);
        return ResponseEntity.ok().build();
    }
}
