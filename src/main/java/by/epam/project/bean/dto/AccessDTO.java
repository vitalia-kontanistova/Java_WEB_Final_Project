package by.epam.project.bean.dto;

import java.util.Objects;

public class AccessDTO {
    private String email;
    private String password;

    public AccessDTO() {
    }

    public AccessDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDataTransferObject{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessDTO that = (AccessDTO) o;

        return Objects.equals(email, that.email) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + email.hashCode();
        result = result * 31 + password.hashCode();

        return result;
    }
}