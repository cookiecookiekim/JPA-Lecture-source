package com.ohgiraffers.mapping.section02.embedded;

// 25-01-08 3교시
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_book") // tbl_book 테이블 만들기
public class Book {

    @Id
    @Column(name = "book_no")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookNo;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "author")
    private String author;

    @Column(name = "pulisher")
    private String pulisher; // 출판사

    @Column(name = "created_date")
    private LocalDate createdDate;  // 출판일

    // Price 클래스 만들고 옴
    @Embedded
    private Price price;

    public Book () {}

    public Book(String bookTitle, String author, String pulisher, LocalDate createdDate, Price price) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.pulisher = pulisher;
        this.createdDate = createdDate;
        this.price = price;
    }
}
