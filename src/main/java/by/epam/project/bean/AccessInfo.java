package by.epam.project.bean;

import java.io.Serializable;

public class AccessInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private long userId;
    private String email;
    private String password;

    public AccessInfo() {
    }

    public AccessInfo(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public AccessInfo(long id, String email, String password, long userId) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.userId = userId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
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
        return "AccessInfo{" +
                "id=" + id +
                ", userId=" + userId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccessInfo that = (AccessInfo) o;
        return id == that.id &&
                userId == that.userId &&
                email.equals(that.email) &&
                password.equals(that.password);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + (int) id;
        result = result * 31 + (int) userId;
        result = result * 31 + email.hashCode();
        result = result * 31 + password.hashCode();

        return result;
    }
}