package com.example.board.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity //DB가 해당 객체를 인식가능!
public class Article {
    //Setters
    //Getters
    @Id //대표값을 지정! Like a 주민번호
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 id를 자동 생성
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private int category;

    public void update(String title, String content, int category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    //AllArgsconstructor
    public Article(Long id, String title, String content, int category) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.category = category;
    }
    //NoArgsConstructor
    public Article() {
    }

    //ToString
    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", category=" + category +
                '}';
    }
}
