package by.epam.project.bean;

import java.io.Serializable;
import java.util.List;

public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String title;
    private List<Author> authors;
    private int year;
    private Language lang;
    private int numberOfPages;
    private int quantity;
    private int availableBooks;
    private String annotation;

    public Book() {
    }

    public Book(String title, List<Author> authors, int year, Language lang,
                int numberOfPages, int quantity, int availableBooks, String annotation) {
        this.title = title;
        this.authors = authors;
        this.year = year;
        this.lang = lang;
        this.numberOfPages = numberOfPages;
        this.quantity = quantity;
        this.availableBooks = availableBooks;
        this.annotation = annotation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public int getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(int numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public Language getLang() {
        return lang;
    }

    public void setLang(Language lang) {
        this.lang = lang;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getAvailableBooks() {
        return availableBooks;
    }

    public void setAvailableBooks(int availableBooks) {
        this.availableBooks = availableBooks;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", year='" + year + '\'' +
                ", lang=" + lang +
                ", numberOfPages=" + numberOfPages +
                ", quantity=" + quantity +
                ", availableBooks=" + availableBooks +
                ", annotation=" + annotation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book that = (Book) o;
        return id == that.id &&
                title.equals(that.title) &&
                authors.equals(that.authors) &&
                year == that.year &&
                lang.equals(that.lang) &&
                numberOfPages == that.numberOfPages &&
                quantity == that.quantity &&
                availableBooks == that.availableBooks &&
                annotation.equals(that.annotation);
    }

    @Override
    public int hashCode() {
        int result = 17;

        result = result * 31 + (int) id;
        result = result * 31 + title.hashCode();
        result = result * 31 + authors.hashCode();
        result = result * 31 + year;
        result = result * 31 + lang.hashCode();
        result = result * 31 + numberOfPages;
        result = result * 31 + quantity;
        result = result * 31 + availableBooks;
        result = result * 31 + annotation.hashCode();

        return result;
    }
}
