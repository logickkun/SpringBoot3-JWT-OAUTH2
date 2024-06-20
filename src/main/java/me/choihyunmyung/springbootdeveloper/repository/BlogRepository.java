package me.choihyunmyung.springbootdeveloper.repository;

import me.choihyunmyung.springbootdeveloper.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Article, Long> {
}
