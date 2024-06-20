package me.choihyunmyung.springbootdeveloper.dto;

import lombok.Getter;
import me.choihyunmyung.springbootdeveloper.domain.Article;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
public class ArticleListViewResponse {

    private final Long id;
    private final String title;
    private final String content;
    private final String author;

    // Setter 기능을 엔터티를 인수로 받는 생성자
    public ArticleListViewResponse(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.author = article.getAuthor();
    }
}
