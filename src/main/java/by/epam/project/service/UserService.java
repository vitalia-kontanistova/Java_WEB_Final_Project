package by.epam.project.service;

import by.epam.project.bean.User;
import by.epam.project.bean.dto.AccessDTO;
import by.epam.project.bean.dto.UserDTO;
import by.epam.project.exception.ServiceException;

import java.util.List;

public interface UserService {
    User saveUser(UserDTO userDTO, AccessDTO accessDTO) throws ServiceException;

    User takeByAccessInfo(AccessDTO accessDTO) throws ServiceException;

    User takeById(long id) throws ServiceException;

    User takeByPhoneEmail(String phone, String email) throws ServiceException;

    List<User> takeAllUsers() throws ServiceException;

    boolean updatePassword(User user, String password) throws ServiceException;

    boolean deleteUser(long userId) throws ServiceException;
}
