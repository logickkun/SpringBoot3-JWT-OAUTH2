package me.choihyunmyung.springbootdeveloper.service;

import lombok.RequiredArgsConstructor;
import me.choihyunmyung.springbootdeveloper.domain.Article;
import me.choihyunmyung.springbootdeveloper.dto.AddArticleRequest;
import me.choihyunmyung.springbootdeveloper.dto.UpdateArticleRequest;
import me.choihyunmyung.springbootdeveloper.repository.BlogRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    //게시글 저장
    public Article save(AddArticleRequest request, String userName) {
        return blogRepository.save(request.toEntity(userName));
    }

    //전체 게시글 조회
    public List<Article> findAll() {
        return blogRepository.findAll();
    }

    //게시글 조회
    public Article findById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글 정보가 없습니다. : " + id));
    }

    //게시글 삭제
    public void delete(Long id) {

        Article article = blogRepository.findById(id)
                        .orElseThrow(() -> new IllegalArgumentException("not found : " + id));


        authorizeArticleAuthor(article);
        blogRepository.deleteById(id);

    }

    //게시글 업데이트
    @Transactional
    public Article update(long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("업데이트 할 게시글 정보가 존재하지 않습니다. : " + id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());
        return article;
    }
    private static void authorizeArticleAuthor(Article article) {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();
        if (!article.getAuthor().equals(userName)) {
            throw new IllegalArgumentException("not authorized");
        }
    }
}
