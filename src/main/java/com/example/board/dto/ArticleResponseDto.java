package com.example.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArticleResponseDto {
    private Long id; // 게시물 ID 추가
    private String title;
    private String content;
    private int category; // 카테고리 추가
}