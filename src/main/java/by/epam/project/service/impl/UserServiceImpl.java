package by.epam.project.service.impl;

import by.epam.project.bean.AccessInfo;
import by.epam.project.bean.User;
import by.epam.project.bean.dto.AccessDTO;
import by.epam.project.bean.dto.UserDTO;
import by.epam.project.dao.DAOProvider;
import by.epam.project.dao.UserDAO;
import by.epam.project.exception.DAOException;
import by.epam.project.exception.ServiceException;
import by.epam.project.service.UserService;
import by.epam.project.service.util.DTOConverter;
import by.epam.project.service.util.Validator;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;

public class UserServiceImpl implements UserService {
    private static final Validator validator = Validator.getInstance();

    /**
     * Метод сохраняет пользователя и информацию о доступе в базу данных, получая их из объектов <code>UserDTO</code>
     * и <code>AccessDTO</code>.
     *
     * @param userDTO
     * @param accessDTO
     * @return пользователь из базы данных
     * @throws ServiceException
     */
    @Override
    public User saveUser(UserDTO userDTO, AccessDTO accessDTO) throws ServiceException {
        UserDAO userDAO = DAOProvider.getInstance().getUserDAO();

        User user;
        try {
            accessDTO.setPassword(BCrypt.hashpw(accessDTO.getPassword(), BCrypt.gensalt()));
            user = DTOConverter.getInstance().dtoConvertIntoUser(userDTO);
            AccessInfo accessInfo = DTOConverter.getInstance().dtoConvertIntoAccess(accessDTO);

            user = userDAO.saveUser(user, accessInfo);
        } catch (DAOException e) {
            throw new ServiceException("UserServiceImpl: saveUser(): " + e.getMessage());
        }
        return user;
    }

    /**
     * Метод возвращает пользователя по объекту <code>AccessDTO</code>.
     *
     * @param accessDTO
     * @return пользователь из базы данных
     * @throws ServiceException
     */
    @Override
    public User takeByAccessInfo(AccessDTO accessDTO) throws ServiceException {
        UserDAO userDAO = DAOProvider.getInstance().getUserDAO();
        User user = null;

        if (validator.accessInfoCheck(accessDTO)) {
            try {
                user = userDAO.takeByAccessInfo(accessDTO.getEmail(), accessDTO.getPassword());
            } catch (DAOException e) {
                throw new ServiceException("UserServiceImpl: takeByAccessInfo(): " + e.getMessage());
            }
        }
        return user;
    }

    /**
     * Метод возвращает пользователя по его идентификационному номеру.
     *
     * @param userId
     * @return пользователь из базы данных
     * @throws ServiceException
     */
    @Override
    public User takeById(long userId) throws ServiceException {
        UserDAO userDAO = DAOProvider.getInstance().getUserDAO();
        User user;
        try {
            user = userDAO.takeById(userId);
        } catch (DAOException e) {
            throw new ServiceException("UserServiceImpl: takeById(): " + e.getMessage());
        }
        return user;
    }

    /**
     * Метод возвращает пользователя по его номеру тефона и почте.
     * <p>
     * Метод необходим для проверки уникальности передаваемых данных.
     *
     * @param phone
     * @param email
     * @return пользователь из базы данных
     * @throws ServiceException
     */
    @Override
    public User takeByPhoneEmail(String phone, String email) throws ServiceException {
        UserDAO userDAO = DAOProvider.getInstance().getUserDAO();
        User user;
        try {
            user = userDAO.takeByPhoneEmail(phone, email);
        } catch (DAOException e) {
            throw new ServiceException("UserServiceImpl: takeByPhoneEmail(): " + e.getMessage());
        }
        return user;
    }

    /**
     * Метод возвращает всех пользователей.
     *
     * @return все пользователи из базы данных
     * @throws ServiceException
     */
    @Override
    public List<User> takeAllUsers() throws ServiceException {
        UserDAO userDAO = DAOProvider.getInstance().getUserDAO();
        List<User> users;
        try {
            users = userDAO.takeAllUsers();
        } catch (DAOException e) {
            throw new ServiceException("UserServiceImpl: takeAllUsers(): " + e.getMessage());
        }
        return users;
    }

    /**
     * Метод обновляет пароль пользователя.
     *
     * @param user
     * @param newPassword новый пароль
     * @return <code>true</code> когда операция прошла успешно, <code>false</code> в противном случае
     * @throws ServiceException
     */
    @Override
    public boolean updatePassword(User user, String newPassword) throws ServiceException {
        UserDAO userDAO = DAOProvider.getInstance().getUserDAO();
        String newPasswordHash = "";
        boolean success = false;
        long id;

        try {
            if (user != null) {
                id = user.getId();

                if (validator.passwordCheck(newPassword)) {
                    newPasswordHash = BCrypt.hashpw(newPassword, BCrypt.gensalt());
                }
                success = userDAO.updatePassword(id, newPasswordHash);
            }
        } catch (DAOException e) {
            throw new ServiceException("UserServiceImpl: updatePassword(): " + e.getMessage());
        }
        return success;
    }

    /**
     * Метод удаляет пользователя.
     *
     * @param userId
     * @return <code>true</code> когда операция прошла успешно, <code>false</code> в противном случае
     * @throws ServiceException
     */
    @Override
    public boolean deleteUser(long userId) throws ServiceException {
        UserDAO userDAO = DAOProvider.getInstance().getUserDAO();
        try {
            return userDAO.deleteUser(userId);
        } catch (DAOException e) {
            throw new ServiceException("UserServiceImpl: deleteUser(): " + e.getMessage());
        }
    }
}
