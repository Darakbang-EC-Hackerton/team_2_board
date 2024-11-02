package com.example.board.service;

import com.example.board.entity.Article;
import com.example.board.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service //서비스 객체를 스프링부트에 생성
public class ArticleService {
    private final ArticleRepository articleRepository;

    // 생성자 주입 방식으로 변경
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    // 리스트 형태의 Article 엔티티들을 반환해주는 메서드
    public List<Article> getAllArticles() {
        return (List<Article>) articleRepository.findAll();
    }

    // 검색어로 제목이나 내용을 포함하는 게시글을 반환해주는 메서드 추가
    public List<Article> searchArticles(String keyword) {
        return articleRepository.findByTitleContainingOrContentContaining(keyword, keyword);
    }

    // id값을 바탕으로 레파지토리에서 원하는 Article을 반환해주는 메서드
    public Optional<Article> getArticleById(Long id) {
        return articleRepository.findById(id);
    }

    // 새 게시글을 생성해주는 메서드 (카테고리 추가)
    public Article createArticle(Article article) {
        return articleRepository.save(article);
    }

    // 게시글을 업데이트해주는 메서드 (카테고리 수정 가능)
    public Article updateArticle(Long id, Article updatedArticle) {
        return articleRepository.findById(id)
                .map(article -> {
                    article.setTitle(updatedArticle.getTitle());
                    article.setContent(updatedArticle.getContent());
                    article.setCategory(updatedArticle.getCategory());
                    return articleRepository.save(article);
                })
                .orElseThrow(() -> new IllegalArgumentException("Article not found with id: " + id));
    }

    // 게시글을 삭제해주는 메서드
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

    // 여러 게시글 삭제해주는 메서드 추가
    public void deleteArticles(List<Article> articles) {
        articleRepository.deleteAll(articles);
    }

    public List<Article> getArticlesByTitle(String title) {
        return articleRepository.findByTitleContaining(title);
    }

    public List<Article> getArticlesByContent(String content) {
        return articleRepository.findByContentContaining(content);
    }

    // 카테고리로 게시글을 반환해주는 메서드 추가
    public List<Article> getArticlesByCategory(int category) {
        return articleRepository.findByCategory(category);
    }
}
