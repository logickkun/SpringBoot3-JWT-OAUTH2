package me.choihyunmyung.springbootdeveloper.dto;

import lombok.Getter;
import me.choihyunmyung.springbootdeveloper.domain.Article;

@Getter
public class ArticleResponse {

    private final String title;
    private final String content;

    // Setter 기능을 엔터티를 인수로 받는 생성자
    public ArticleResponse(Article article) {

        this.title = article.getTitle();
        this.content = article.getContent();

    }

}
