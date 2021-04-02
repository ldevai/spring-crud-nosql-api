package io.devai.tutorials.app.dto;

import lombok.Data;

import java.util.List;

@Data
public class ArticleRequest {
    private String id;
    private String title;
    private String url;
    private String content;
    private List<String> tags;
}
