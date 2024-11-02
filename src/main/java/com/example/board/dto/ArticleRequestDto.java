package com.example.board.dto;

import com.example.board.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleRequestDto {
    private Long id;
    private String title;
    private String content;
    private int category;

    public Article toEntity() {
        return new Article(id, title, content, category);
    }
}
