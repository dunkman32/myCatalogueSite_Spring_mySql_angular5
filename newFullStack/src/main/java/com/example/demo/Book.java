package com.example.demo;

import javax.persistence.*;

@Entity
@Table(name = "books", schema = "testDB")
public class Book {
    @Id
    @GeneratedValue
    @Column(name = "book_id")
    private int id;
    @Column(name = "author")
    private String author;
    @Column(name = "year")
    private String year;
    @Column(name = "name")
    private String name;
    @Column(name = "isbn")
    private String isbn;
    @Transient
    private boolean favorite;
    public Book() {
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null)
            return false;
        if (obj instanceof Book) {
            Book tmp = (Book) obj;
            if(tmp.id == (this.id))
              return true;
         }
        return false;
    }
    @Override
    public int hashCode() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", author='" + author + '\'' +
                ", year='" + year + '\'' +
                ", name='" + name + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
