package com.example.board.repository;

import com.example.board.entity.Article;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    // 제목에 특정 문자열이 포함된 게시글 찾기
    List<Article> findByTitleContaining(String title);

    // 내용에 특정 문자열이 포함된 게시글 찾기
    List<Article> findByContentContaining(String content);

    // 제목 또는 내용에 특정 문자열이 포함된 게시글 찾기
    List<Article> findByTitleContainingOrContentContaining(String title, String content);

    // 특정 카테고리에 속하는 게시글 찾기
    List<Article> findByCategory(int category);
}
