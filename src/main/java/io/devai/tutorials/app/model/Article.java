package io.devai.tutorials.app.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "articles")
public class Article {

    @Id
    private String id;
    private String title;
    private String url;
    private String content;
    private List<Tag> tags;
    private Date createdAt;
    private Date updatedAt;
    private List<Comment> comments;
}
