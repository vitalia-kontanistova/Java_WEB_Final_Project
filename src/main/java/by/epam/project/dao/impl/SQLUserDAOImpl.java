package by.epam.project.dao.impl;

import by.epam.project.bean.AccessInfo;
import by.epam.project.bean.Role;
import by.epam.project.bean.User;
import by.epam.project.connection_pool.SQLRequest;
import by.epam.project.dao.UserDAO;
import by.epam.project.exception.ConnectionPoolException;
import by.epam.project.exception.DAOException;
import by.epam.project.connection_pool.ConnectionPool;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class SQLUserDAOImpl implements UserDAO {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Метод сохраняет пользователя в базу данных.
     *
     * @param user
     * @param accessInfo
     * @return пользователь из базы данных
     * @throws DAOException
     */
    @Override
    public User saveUser(User user, AccessInfo accessInfo) throws DAOException {
        Connection connection = null;
        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            addUser(connection, user);
            if (user.getId() > 0) {
                accessInfo.setUserId(user.getId());
            }

            addAccessInfo(connection, accessInfo);
            if (user.getId() > 0 && accessInfo.getId() > 0) {
                connection.commit();
            } else {
                connection.rollback();
                user = null;
            }
            connection.setAutoCommit(true);
        } catch (SQLException | ConnectionPoolException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                throw new DAOException("SQLUserDAOImpl: saveUser(): " + e.getMessage());
            }
            throw new DAOException("SQLUserDAOImpl: saveUser(): " + e.getMessage());
        } finally {
            connectionPool.closeConnection(connection);
        }
        return user;
    }

    /**
     * Метод возвращает пользователя по его почте и паролю.
     *
     * @param email
     * @param password
     * @return пользователь из базы данных
     * @throws DAOException
     */
    @Override
    public User takeByAccessInfo(String email, String password) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try {
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(SQLRequest.AUTHORISATION_SQL);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                AccessInfo accessInfo = createAccessInfoObjectFromResultSet(rs);
                if (BCrypt.checkpw(password, accessInfo.getPassword())) {
                    user = takeById(accessInfo.getUserId());
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("SQLUserDAOImpl: takeByAccessInfo(): " + e.getMessage());
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return user;
    }

    /**
     * Метод возвращает пользователя по его идентификационному номеру.
     *
     * @param userId
     * @return пользователь из базы данных
     * @throws DAOException
     */
    @Override
    public User takeById(long userId) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;
        try {
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(SQLRequest.TAKE_USER_BY_ID_SQL);
            ps.setLong(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = createUserObjectFromResultSet(rs);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("SQLUserDAOImpl: takeById(): " + e.getMessage());
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
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
     * @throws DAOException
     */
    @Override
    public User takeByPhoneEmail(String phone, String email) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(SQLRequest.TAKE_USER_BY_PHONE_SQL);
            ps.setString(1, phone);
            rs = ps.executeQuery();

            if (rs.next()) {
                return createUserObjectFromResultSet(rs);
            }

            ps = connection.prepareStatement(SQLRequest.TAKE_USER_BY_EMAIL_SQL);
            ps.setString(1, email);
            rs = ps.executeQuery();

            if (rs.next()) {
                return createUserObjectFromResultSet(rs);
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("SQLUserDAOImpl: takeByPhoneEmail(): " + e.getMessage());
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return null;
    }

    /**
     * Метод возвращает всех пользователей.
     *
     * @return все пользователи из базы данных
     * @throws DAOException
     */
    @Override
    public List<User> takeAllUsers() throws DAOException {
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;

        List<User> users = new ArrayList<>();
        try {
            connection = connectionPool.takeConnection();
            st = connection.createStatement();
            rs = st.executeQuery(SQLRequest.TAKE_ALL_USERS_SQL);

            while (rs.next()) {
                users.add(createUserObjectFromResultSet(rs));
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("SQLUserDAOImpl: takeAll(): " + e.getMessage());
        } finally {
            connectionPool.closeConnection(connection, st, rs);
        }
        return users;
    }

    /**
     * Метод обновляет пароль пользователя.
     *
     * @param userId          идентификационный номер пользователя
     * @param newPasswordHash новый уже зашифрованный пароль
     * @return <code>true</code> когда операция прошла успешно, <code>false</code> в противном случае
     * @throws DAOException
     */
    @Override
    public boolean updatePassword(long userId, String newPasswordHash) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(SQLRequest.UPDATE_PASSWORD_SQL);
            ps.setString(1, newPasswordHash);
            ps.setLong(2, userId);

            return (ps.executeUpdate() > 0);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("SQLUserDAOImpl: updatePassword(): " + e.getMessage());
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    /**
     * Метод удаляет пользователя.
     *
     * @param userId
     * @return <code>true</code> когда операция прошла успешно, <code>false</code> в противном случае
     * @throws DAOException
     */
    @Override
    public boolean deleteUser(long userId) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(SQLRequest.DELETE_USER_SQL);
            ps.setLong(1, userId);
            return (ps.executeUpdate() != 0);
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("SQLUserDAOImpl: deleteUser(): " + e.getMessage());
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
    }

    ////////////////////////////////////////////////////////

    /**
     * Метод добавляет пользователя в базу данных.
     *
     * @param connection
     * @param user
     * @return пользователь из базы данных
     * @throws SQLException
     */
    private User addUser(Connection connection, User user) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQLRequest.ADD_USER_SQL)) {
            ps.setObject(1, null);
            ps.setString(2, user.getSurname());
            ps.setString(3, user.getName());
            ps.setString(4, user.getPatronymic());
            ps.setDate(5, user.getBirthday(), new GregorianCalendar());
            ps.setString(6, user.getPhone());
            ps.setString(7, user.getRole().toString());

            ps.executeUpdate();
            user.setId(getLastInsertId(ps));
        } catch (SQLException e) {
            throw new SQLException("addUser(): " + e.getMessage());
        }
        return user;
    }

    /**
     * Метод добавляет пользовательскую информацию для доступа в базу данных.
     *
     * @param connection
     * @param accessInfo
     * @return пользовательскую информацию для доступа из базы данных
     * @throws SQLException
     */
    private AccessInfo addAccessInfo(Connection connection, AccessInfo accessInfo) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQLRequest.ADD_ACCESS_INFO_SQL)) {
            ps.setObject(1, null);
            ps.setString(2, accessInfo.getEmail());
            ps.setString(3, accessInfo.getPassword());
            ps.setLong(4, accessInfo.getUserId());

            ps.executeUpdate();

            accessInfo.setId(getLastInsertId(ps));
        } catch (SQLException e) {
            throw new SQLException("addAccessInfo(): " + e.getMessage());
        }
        return accessInfo;
    }

    /**
     * Метод возвращает иденитификационный номер последней операции "INSERT" в базу данных.
     *
     * @param st <code>Statement</code>
     * @return иденитификационный номер
     * @throws SQLException
     */
    private long getLastInsertId(Statement st) throws SQLException {
        try (ResultSet rs = st.executeQuery(SQLRequest.GET_LAST_INSERT_ID_SQL)) {
            if (rs != null && rs.next()) {
                return rs.getLong(1);
            } else {
                return -1;
            }
        } catch (SQLException e) {
            throw new SQLException("getLastInsertId(): " + e.getMessage());
        }
    }

    /**
     * Метод возвращает объект пользователя, созданный из переданного объекта <code>ResultSet</code>.
     *
     * @param rs <code>ResultSet</code>
     * @return пользователя
     * @throws SQLException
     */
    private User createUserObjectFromResultSet(ResultSet rs) throws SQLException {
        User user = new User();
        try {
            user.setId(rs.getLong(1));
            user.setSurname(rs.getString(2));
            user.setName(rs.getString(3));
            user.setPatronymic(rs.getString(4));
            user.setBirthday(Date.valueOf(rs.getString(5)));
            user.setPhone(rs.getString(6));
            user.setRole(Role.valueOf(rs.getString(7)));
        } catch (SQLException e) {
            throw new SQLException("createUserObjectFromResultSet(): " + e.getMessage());
        }
        return user;
    }

    /**
     * Метод возвращает объект пользовательской информации для доступа в базу данных из переданного объекта.
     * <code>ResultSet</code>.
     *
     * @param rs <code>ResultSet</code>
     * @return пользовательскую информацию для доступа
     * @throws SQLException
     */
    private AccessInfo createAccessInfoObjectFromResultSet(ResultSet rs) throws SQLException {
        AccessInfo accessInfo = new AccessInfo();
        try {
            accessInfo.setId(rs.getLong(1));
            accessInfo.setEmail(rs.getString(2));
            accessInfo.setPassword(rs.getString(3));
            accessInfo.setUserId(rs.getLong(4));
        } catch (SQLException e) {
            throw new SQLException("createAccessInfoObjectFromResultSet(): " + e.getMessage());
        }
        return accessInfo;
    }
}