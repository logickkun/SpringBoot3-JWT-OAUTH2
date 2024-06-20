package me.choihyunmyung.springbootdeveloper.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import me.choihyunmyung.springbootdeveloper.domain.Article;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class ArticleViewResponse {

    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private String author;

    // Setter 기능을 엔터티를 인수로 받는 생성자
    public ArticleViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.createdAt = article.getCreatedAt();
        this.author = article.getAuthor();
    }
}
