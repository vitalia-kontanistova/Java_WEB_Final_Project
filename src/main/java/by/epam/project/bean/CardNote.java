package by.epam.project.bean;

import java.io.Serializable;
import java.sql.Date;

public class CardNote implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private Date initialDate;
    private Date finalDate;
    private Destination destination;
    private boolean active;
    private long userId;
    private Book book;

    public CardNote() {
    }

    public CardNote(Date initialDate, Date finalDate, Destination destination, boolean active, Book book, long userId) {
        this.initialDate = initialDate;
        this.finalDate = finalDate;
        this.destination = destination;
        this.active = active;
        this.book = book;
        this.userId = userId;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "CardNote{" +
                "id=" + id +
                ", initialDate=" + initialDate +
                ", finalDate=" + finalDate +
                ", destination=" + destination +
                ", active=" + active +
                ", book=" + book +
                ", userId=" + userId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardNote that = (CardNote) o;
        return
                initialDate.equals(that.initialDate) &&
                finalDate.equals(that.finalDate) &&
                destination.equals(that.destination) &&
                active == that.active &&
                book == that.book &&
                userId == that.userId;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + initialDate.hashCode();
        result = result * 31 + finalDate.hashCode();
        result = result * 31 + destination.hashCode();
        result = result * 31 + ((active) ? 1 : 0);
        result = result * 31 + book.hashCode();
        result = result * 31 + (int) userId;

        return result;
    }
}
