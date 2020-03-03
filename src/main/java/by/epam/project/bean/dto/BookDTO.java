package by.epam.project.bean.dto;

import java.util.Objects;

public class BookDTO {
    private String title;
    private String year;
    private String lang;
    private String numberOfPages;
    private String quantity;
    private String annotation;

    private String author1Surname;
    private String author1Name;
    private String author1Patronymic;

    private String author2Surname;
    private String author2Name;
    private String author2Patronymic;

    private String author3Surname;
    private String author3Name;
    private String author3Patronymic;


    public BookDTO() {
    }

    public BookDTO(
            String title, String year, String lang, String numberOfPages, String quantity, String annotation,
            String author1Surname, String author1Name, String author1Patronymic,
            String author2Surname, String author2Name, String author2Patronymic,
            String author3Surname, String author3Name, String author3Patronymic) {
        this.title = title;
        this.year = year;
        this.lang = lang;
        this.numberOfPages = numberOfPages;
        this.quantity = quantity;
        this.annotation = annotation;
        this.author1Surname = author1Surname;
        this.author1Name = author1Name;
        this.author1Patronymic = author1Patronymic;
        this.author2Surname = author2Surname;
        this.author2Name = author2Name;
        this.author2Patronymic = author2Patronymic;
        this.author3Surname = author3Surname;
        this.author3Name = author3Name;
        this.author3Patronymic = author3Patronymic;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getNumberOfPages() {
        return numberOfPages;
    }

    public void setNumberOfPages(String numberOfPages) {
        this.numberOfPages = numberOfPages;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getAuthor1Surname() {
        return author1Surname;
    }

    public void setAuthor1Surname(String author1Surname) {
        this.author1Surname = author1Surname;
    }

    public String getAuthor1Name() {
        return author1Name;
    }

    public void setAuthor1Name(String author1Name) {
        this.author1Name = author1Name;
    }

    public String getAuthor1Patronymic() {
        return author1Patronymic;
    }

    public void setAuthor1Patronymic(String author1Patronymic) {
        this.author1Patronymic = author1Patronymic;
    }

    public String getAuthor2Surname() {
        return author2Surname;
    }

    public void setAuthor2Surname(String author2Surname) {
        this.author2Surname = author2Surname;
    }

    public String getAuthor2Name() {
        return author2Name;
    }

    public void setAuthor2Name(String author2Name) {
        this.author2Name = author2Name;
    }

    public String getAuthor2Patronymic() {
        return author2Patronymic;
    }

    public void setAuthor2Patronymic(String author2Patronymic) {
        this.author2Patronymic = author2Patronymic;
    }

    public String getAuthor3Surname() {
        return author3Surname;
    }

    public void setAuthor3Surname(String author3Surname) {
        this.author3Surname = author3Surname;
    }

    public String getAuthor3Name() {
        return author3Name;
    }

    public void setAuthor3Name(String author3Name) {
        this.author3Name = author3Name;
    }

    public String getAuthor3Patronymic() {
        return author3Patronymic;
    }

    public void setAuthor3Patronymic(String author3Patronymic) {
        this.author3Patronymic = author3Patronymic;
    }


    @Override
    public String toString() {
        return "LibDataTransferObject{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", lang='" + lang + '\'' +
                ", numberOfPages='" + numberOfPages + '\'' +
                ", quantity='" + quantity + '\'' +
                ", annotation='" + annotation + '\'' +
                ", author1Surname='" + author1Surname + '\'' +
                ", author1Name='" + author1Name + '\'' +
                ", author1Patronymic='" + author1Patronymic + '\'' +
                ", author2Surname='" + author2Surname + '\'' +
                ", author2Name='" + author2Name + '\'' +
                ", author2Patronymic='" + author2Patronymic + '\'' +
                ", author3Surname='" + author3Surname + '\'' +
                ", author3Name='" + author3Name + '\'' +
                ", author3Patronymic='" + author3Patronymic + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDTO that = (BookDTO) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(year, that.year) &&
                Objects.equals(lang, that.lang) &&
                Objects.equals(numberOfPages, that.numberOfPages) &&
                Objects.equals(quantity, that.quantity) &&
                Objects.equals(annotation, that.annotation) &&
                Objects.equals(author1Surname, that.author1Surname) &&
                Objects.equals(author1Name, that.author1Name) &&
                Objects.equals(author1Patronymic, that.author1Patronymic) &&
                Objects.equals(author2Surname, that.author2Surname) &&
                Objects.equals(author2Name, that.author2Name) &&
                Objects.equals(author2Patronymic, that.author2Patronymic) &&
                Objects.equals(author3Surname, that.author3Surname) &&
                Objects.equals(author3Name, that.author3Name) &&
                Objects.equals(author3Patronymic, that.author3Patronymic);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + title.hashCode();
        result = result * 31 + year.hashCode();
        result = result * 31 + lang.hashCode();
        result = result * 31 + numberOfPages.hashCode();
        result = result * 31 + quantity.hashCode();
        result = result * 31 + annotation.hashCode();
        result = result * 31 + author1Surname.hashCode();
        result = result * 31 + author1Name.hashCode();
        result = result * 31 + author1Patronymic.hashCode();
        result = result * 31 + author2Surname.hashCode();
        result = result * 31 + author2Name.hashCode();
        result = result * 31 + author2Patronymic.hashCode();
        result = result * 31 + author3Surname.hashCode();
        result = result * 31 + author3Name.hashCode();
        result = result * 31 + author3Patronymic.hashCode();

        return result;
    }
}
