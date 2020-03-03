package by.epam.project.dao.impl;

import by.epam.project.bean.AccessInfo;
import by.epam.project.bean.Role;
import by.epam.project.bean.User;
import by.epam.project.dao.DAOProvider;
import by.epam.project.dao.UserDAO;
import by.epam.project.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class UserDAOTest {

    private final static Logger logger = LogManager.getLogger();
    UserDAO userDAO = DAOProvider.getInstance().getUserDAO();
    private static User user;
    private static AccessInfo accessInfo;
    private static String passwordHash;
    private static String password;

    @BeforeClass
    public static void setUp() {
        String surname = "Surname";
        String name = "Name";
        String patronymic = "Patronymic";
        Date bd = Date.valueOf("1991-01-09");
        String phone = "(44)1100101";
        String role = "READER";
        String email = "test@email.em";
        passwordHash = BCrypt.hashpw("test@email.em", BCrypt.gensalt());
        password = "test@email.em";

        user = new User(surname, name, patronymic, bd, phone, Role.valueOf(role));
        accessInfo = new AccessInfo(email, passwordHash);
    }

    @Test
    public void saveUserTest() {
        User actualUser;
        User expectedUser = user;
        AccessInfo expectedAccess = accessInfo;
        try {
            actualUser = userDAO.saveUser(expectedUser, expectedAccess);
            userDAO.deleteUser(actualUser.getId());

            Assert.assertEquals(expectedUser.getSurname(), actualUser.getSurname());
            Assert.assertEquals(expectedUser.getName(), actualUser.getName());
            Assert.assertEquals(expectedUser.getPatronymic(), actualUser.getPatronymic());
            Assert.assertEquals(expectedUser.getBirthday(), actualUser.getBirthday());
            Assert.assertEquals(expectedUser.getPhone(), actualUser.getPhone());
            Assert.assertEquals(expectedUser.getRole(), actualUser.getRole());
        } catch (DAOException e) {
            logger.error(e);
        }
    }

    @Test
    public void takeByAccessInfoTest() {
        User actualUser;
        User expectedUser = user;
        AccessInfo expectedAccess = accessInfo;
        try {
            userDAO.saveUser(expectedUser, expectedAccess);
            actualUser = userDAO.takeByAccessInfo(expectedAccess.getEmail(), password);
            userDAO.deleteUser(actualUser.getId());

            Assert.assertEquals(expectedUser.getSurname(), actualUser.getSurname());
            Assert.assertEquals(expectedUser.getName(), actualUser.getName());
            Assert.assertEquals(expectedUser.getPatronymic(), actualUser.getPatronymic());
            Assert.assertEquals(expectedUser.getBirthday(), actualUser.getBirthday());
            Assert.assertEquals(expectedUser.getPhone(), actualUser.getPhone());
            Assert.assertEquals(expectedUser.getRole(), actualUser.getRole());
        } catch (DAOException e) {
            logger.error(e);
        }
    }

    @Test
    public void takeByIdTest() {
        User actualUser;
        User expectedUser = user;
        AccessInfo expectedAccess = accessInfo;
        try {
            actualUser = userDAO.saveUser(expectedUser, expectedAccess);
            actualUser = userDAO.takeById(actualUser.getId());
            userDAO.deleteUser(actualUser.getId());

            Assert.assertEquals(expectedUser.getSurname(), actualUser.getSurname());
            Assert.assertEquals(expectedUser.getName(), actualUser.getName());
            Assert.assertEquals(expectedUser.getPatronymic(), actualUser.getPatronymic());
            Assert.assertEquals(expectedUser.getBirthday(), actualUser.getBirthday());
            Assert.assertEquals(expectedUser.getPhone(), actualUser.getPhone());
            Assert.assertEquals(expectedUser.getRole(), actualUser.getRole());
        } catch (DAOException e) {
            logger.error(e);
        }
    }

    @Test
    public void takeByPhoneEmailTest() {
        User actualUser;
        User expectedUser = user;
        AccessInfo expectedAccess = accessInfo;
        try {
            actualUser = userDAO.saveUser(expectedUser, expectedAccess);
            actualUser = userDAO.takeByPhoneEmail(actualUser.getPhone(), expectedAccess.getEmail());
            userDAO.deleteUser(actualUser.getId());

            Assert.assertEquals(expectedUser.getSurname(), actualUser.getSurname());
            Assert.assertEquals(expectedUser.getName(), actualUser.getName());
            Assert.assertEquals(expectedUser.getPatronymic(), actualUser.getPatronymic());
            Assert.assertEquals(expectedUser.getBirthday(), actualUser.getBirthday());
            Assert.assertEquals(expectedUser.getPhone(), actualUser.getPhone());
            Assert.assertEquals(expectedUser.getRole(), actualUser.getRole());
        } catch (DAOException e) {
            logger.error(e);
        }
    }

    @Test
    public void takeAllTest() {
        List<User> list = new ArrayList<>();
        try {
            list = userDAO.takeAllUsers();
        } catch (DAOException e) {
            logger.error(e);
        }
        Assert.assertEquals(13, list.size(),1);
    }

    @Test
    public void updatePasswordTest() {
        boolean success;
        User actualUser;
        User expectedUser = user;
        String newPassword = passwordHash;
        AccessInfo expectedAccess = accessInfo;

        try {
            actualUser = userDAO.saveUser(expectedUser, expectedAccess);
            success = userDAO.updatePassword(actualUser.getId(), newPassword);
            userDAO.deleteUser(actualUser.getId());
            Assert.assertTrue(success);
        } catch (DAOException e) {
            logger.error(e);
        }
    }

    @Test
    public void deleteUserTest() {
        boolean success;
        User actualUser;
        User expectedUser = user;
        AccessInfo expectedAccess = accessInfo;
        try {
            actualUser = userDAO.saveUser(expectedUser, expectedAccess);
            success = userDAO.deleteUser(actualUser.getId());
            Assert.assertTrue(success);
        } catch (DAOException e) {
            logger.error(e);
        }
    }
}
