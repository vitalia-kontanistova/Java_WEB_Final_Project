package by.epam.project.bean.dto;

import java.util.Objects;

public class UserDTO {
    private String surname;
    private String name;
    private String patronymic;
    private String bdYear;
    private String bdMonth;
    private String bdDay;
    private String phone;
    private String role;

    public UserDTO() {
    }

    public UserDTO(String surname, String name, String patronymic,
                   String bdYear, String bdMonth, String bdDay, String phone, String role) {
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.bdYear = bdYear;
        this.bdMonth = bdMonth;
        this.bdDay = bdDay;
        this.phone = phone;
        this.role = role;
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

    public String getBdYear() {
        return bdYear;
    }

    public void setBdYear(String bdYear) {
        this.bdYear = bdYear;
    }

    public String getBdMonth() {
        return bdMonth;
    }

    public void setBdMonth(String bdMonth) {
        this.bdMonth = bdMonth;
    }

    public String getBdDay() {
        return bdDay;
    }

    public void setBdDay(String bdDay) {
        this.bdDay = bdDay;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "UserDataTransferObject{" +
                "surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", bdYear='" + bdYear + '\'' +
                ", bdMonth='" + bdMonth + '\'' +
                ", bdDay='" + bdDay + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDTO that = (UserDTO) o;

        return
                Objects.equals(surname, that.surname) &&
                        Objects.equals(name, that.name) &&
                        Objects.equals(patronymic, that.patronymic) &&
                        Objects.equals(bdYear, that.bdYear) &&
                        Objects.equals(bdMonth, that.bdMonth) &&
                        Objects.equals(bdDay, that.bdDay) &&
                        Objects.equals(phone, that.phone) &&
                        Objects.equals(role, that.role);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + surname.hashCode();
        result = result * 31 + name.hashCode();
        result = result * 31 + patronymic.hashCode();
        result = result * 31 + bdYear.hashCode();
        result = result * 31 + bdMonth.hashCode();
        result = result * 31 + bdDay.hashCode();
        result = result * 31 + phone.hashCode();
        result = result * 31 + role.hashCode();

        return result;
    }
}