package by.epam.project.bean;

import java.io.Serializable;
import java.sql.Date;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String surname;
    private String name;
    private String patronymic;
    private Date birthday;
    private String phone;
    private Role role;

    public User() {
    }

    public User(long id, String surname, String name, String patronymic, Date birthday,
                String phone, Role role) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.phone = phone;
        this.role = role;
    }

    public User(String surname, String name, String patronymic, Date birthday, String phone, Role role) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthday = birthday;
        this.phone = phone;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", birthday=" + birthday +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User that = (User) o;
        return
                surname.equals(that.surname) &&
                name.equals(that.name) &&
                patronymic.equals(that.patronymic) &&
                birthday.equals(that.birthday) &&
                phone.equals(that.phone) &&
                role == that.role;
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + surname.hashCode();
        result = result * 31 + name.hashCode();
        result = result * 31 + patronymic.hashCode();
        result = result * 31 + birthday.hashCode();
        result = result * 31 + phone.hashCode();
        result = result * 31 + role.hashCode();

        return result;
    }
}
