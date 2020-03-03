package by.epam.project.dao;

import by.epam.project.bean.AccessInfo;
import by.epam.project.exception.DAOException;
import by.epam.project.bean.User;

import java.util.List;

public interface UserDAO {
    User saveUser(User user, AccessInfo accessInfo) throws DAOException;

    User takeByAccessInfo(String email, String password) throws DAOException;

    User takeById(long id) throws DAOException;

    User takeByPhoneEmail(String phone, String email) throws DAOException;

    List<User> takeAllUsers() throws DAOException;

    boolean updatePassword(long userId, String newPassword) throws DAOException;

    boolean deleteUser(long userId) throws DAOException;
}