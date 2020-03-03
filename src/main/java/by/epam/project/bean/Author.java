package by.epam.project.bean;

import java.io.Serializable;

public class Author implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String name;
    private String surname;
    private String patronymic;

    public Author() {
    }

    public Author(String name, String surname, String patronymic) {
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    public Author(long id, String name, String surname, String patronymic) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.patronymic = patronymic;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", patronymic='" + patronymic + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author that = (Author) o;
        return id == that.id &&
                name.equals(that.name) &&
                surname.equals(that.surname) &&
                patronymic.equals(that.patronymic);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + (int) id;
        result = result * 31 + name.hashCode();
        result = result * 31 + surname.hashCode();
        result = result * 31 + patronymic.hashCode();

        return result;
    }
}
