package by.epam.project.dao;

import by.epam.project.dao.impl.SQLUserDAOImpl;
import by.epam.project.dao.impl.SqlLibDAOImpl;

public class DAOProvider {
    private static final DAOProvider instance = new DAOProvider();
    private final UserDAO userDAO = new SQLUserDAOImpl();
    private final LibDAO libDAO = new SqlLibDAOImpl();

    private DAOProvider() {
    }

    public static DAOProvider getInstance() {
        return instance;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public LibDAO getLibDAO() {
        return libDAO;
    }
}
