package com.example.board.api;

import com.example.board.dto.ArticleResponseDto;
import com.example.board.service.ArticleService;
import com.example.board.dto.ArticleRequestDto;
import com.example.board.entity.Article;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:63342")
@RequestMapping("/api/articles")
@RestController
public class ArticleApiController {
    private final ArticleService articleService;

    // 생성자 주입 방식
    public ArticleApiController(ArticleService articleService) {
        this.articleService = articleService;
    }

    // GET - 모든 게시글 조회 및 검색 기능 추가
    @GetMapping
    public List<ArticleResponseDto> getAllArticles(@RequestParam(required = false) String search) {
        if (search != null && !search.isEmpty()) {
            return articleService.searchArticles(search).stream()
                    .map(article -> new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent(), article.getCategory()))
                    .collect(Collectors.toList());
        } else {
            return articleService.getAllArticles().stream()
                    .map(article -> new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent(), article.getCategory()))
                    .collect(Collectors.toList());
        }
    }

    // GET - 특정 게시글 조회
    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> getArticleById(@PathVariable Long id) {
        Article article = articleService.getArticleById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found with id: " + id));
        return ResponseEntity.ok(new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent(), article.getCategory()));
    }

    // POST - 새 게시글 생성 (카테고리 추가)
    @PostMapping
    public ResponseEntity<ArticleResponseDto> createArticle(@RequestBody ArticleRequestDto articleRequestDto) {
        if (articleRequestDto.getCategory() < 1 || articleRequestDto.getCategory() > 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid category. Must be 1, 2, or 3.");
        }
        try {
            Article article = articleService.createArticle(articleRequestDto.toEntity());
            return ResponseEntity.status(HttpStatus.CREATED).body(new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent(), article.getCategory()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data", e);
        }
    }

    // PUT - 게시글 수정 (카테고리 수정 가능)
    @PutMapping("/{id}")
    public ResponseEntity<ArticleResponseDto> updateArticle(@PathVariable Long id,
                                                            @RequestBody ArticleRequestDto articleRequestDto) {
        if (articleRequestDto.getCategory() < 1 || articleRequestDto.getCategory() > 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid category. Must be 1, 2, or 3.");
        }
        try {
            Article updatedArticle = articleService.updateArticle(id, articleRequestDto.toEntity());
            return ResponseEntity.ok(new ArticleResponseDto(updatedArticle.getId(), updatedArticle.getTitle(), updatedArticle.getContent(), updatedArticle.getCategory()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found with id: " + id, e);
        }
    }

    // DELETE - 특정 게시글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArticleById(@PathVariable Long id) {
        try {
            articleService.deleteArticle(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Article not found with id: " + id, e);
        }
    }

    // GET - 카테고리별 게시글 조회
    @GetMapping("/category/{category}")
    public List<ArticleResponseDto> getArticlesByCategory(@PathVariable int category) {
        if (category < 1 || category > 3) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid category. Must be 1, 2, or 3.");
        }
        return articleService.getArticlesByCategory(category).stream()
                .map(article -> new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent(), article.getCategory()))
                .collect(Collectors.toList());
    }

    // DELETE - 제목으로 게시글 삭제
    @DeleteMapping("/delete-by-title/{title}")
    public ResponseEntity<Void> deleteArticlesByTitle(@PathVariable String title) {
        List<Article> articles = articleService.getArticlesByTitle(title);
        if (articles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No articles found with title containing: " + title);
        }
        articleService.deleteArticles(articles);
        return ResponseEntity.noContent().build();
    }

    // DELETE - 내용으로 게시글 삭제
    @DeleteMapping("/delete-by-content/{content}")
    public ResponseEntity<Void> deleteArticlesByContent(@PathVariable String content) {
        List<Article> articles = articleService.getArticlesByContent(content);
        if (articles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No articles found with content containing: " + content);
        }
        articleService.deleteArticles(articles);
        return ResponseEntity.noContent().build();
    }

    // GET - 제목으로 게시글 찾기
    @GetMapping("/title/{title}")
    public List<ArticleResponseDto> getArticlesByTitle(@PathVariable String title) {
        List<Article> articles = articleService.getArticlesByTitle(title);
        if (articles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No articles found with title containing: " + title);
        }
        return articles.stream()
                .map(article -> new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent(), article.getCategory()))
                .collect(Collectors.toList());
    }

    // GET - 내용으로 게시글 찾기
    @GetMapping("/content/{content}")
    public List<ArticleResponseDto> getArticlesByContent(@PathVariable String content) {
        List<Article> articles = articleService.getArticlesByContent(content);
        if (articles.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No articles found with content containing: " + content);
        }
        return articles.stream()
                .map(article -> new ArticleResponseDto(article.getId(), article.getTitle(), article.getContent(), article.getCategory()))
                .collect(Collectors.toList());
    }
}
