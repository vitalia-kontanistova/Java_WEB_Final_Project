package by.epam.project.dao.impl;

import by.epam.project.bean.*;
import by.epam.project.dao.LibDAO;
import by.epam.project.connection_pool.SQLRequest;
import by.epam.project.exception.ConnectionPoolException;
import by.epam.project.exception.DAOException;
import by.epam.project.connection_pool.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;


public class SqlLibDAOImpl implements LibDAO {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    /**
     * Метод сохраняет запись в карточке пользователя в базу данных.
     * <p>
     * Если сохранение записи в базу данных прошло успешно, то колличество доступных книг из этой записи уменьшается
     * на единицу.
     *
     * @param cardNote
     * @return запись из карточки пользователя.
     * @throws DAOException
     */
    @Override
    public CardNote saveCardNote(CardNote cardNote) throws DAOException {
        PreparedStatement ps = null;
        Connection connection = null;

        try {
            connection = connectionPool.takeConnection();

            connection.setAutoCommit(false);
            ps = connection.prepareStatement(SQLRequest.ADD_CARD_NOTE_SQL);
            ps.setDate(1, cardNote.getInitialDate(), new GregorianCalendar());
            ps.setDate(2, cardNote.getFinalDate(), new GregorianCalendar());
            ps.setString(3, cardNote.getDestination().toString());
            ps.setBoolean(4, cardNote.isActive());
            ps.setLong(5, cardNote.getBook().getId());
            ps.setLong(6, cardNote.getUserId());

            boolean success = ps.executeUpdate() >= 1;
            cardNote.setId(getLastInsertId(ps));
            success = success && decreaseAvailableBookQuantity(connection, cardNote.getBook().getId());

            if (success) {
                connection.commit();
            } else {
                connection.rollback();
            }
            connection.setAutoCommit(true);
        } catch (SQLException | ConnectionPoolException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                throw new DAOException("SqlLibDAOImpl: saveCardNote(): " + e.getMessage());
            }
            throw new DAOException("SqlLibDAOImpl: saveCardNote(): " + e.getMessage());
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return cardNote;
    }

    /**
     * Метод удаляет из базы данных запись в карточке пользователя.
     *
     * @param cardNoteId
     * @return <code>true</code> когда операция прошла успешно, <code>false</code> в противном случае
     * @throws DAOException
     */
    @Override
    public boolean deleteCardNote(long cardNoteId) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        boolean success;

        try {
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(SQLRequest.DELETE_CARD_NOTE_SQL);
            ps.setLong(1, cardNoteId);
            success = ps.executeUpdate() > 0;
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("SqlLibDAOImpl: deleteCardNote(): " + e.getMessage());
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return success;
    }

    /**
     * Метод возвращает запись по идентификационному номеру из базы данных.
     *
     * @param cardNoteId
     * @return запись из карточки пользователя.
     * @throws DAOException
     */
    @Override
    public CardNote takeNoteById(long cardNoteId) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        CardNote cardNote = null;

        try {
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(SQLRequest.TAKE_NOTE_BY_ID_SQL);
            ps.setLong(1, cardNoteId);
            rs = ps.executeQuery();

            if (rs.next()) {
                cardNote = createNoteObjectFromResultSet(rs);
            }
            if (cardNote != null && cardNote.getBook() == null) {
                cardNote = null;
            }

            rs.close();
        } catch (SQLException | ConnectionPoolException e) {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                throw new DAOException("SqlLibDAOImpl: takeNoteById(): " + e.getMessage());
            }
            throw new DAOException("SqlLibDAOImpl: takeNoteById(): " + e.getMessage());
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return cardNote;
    }

    /**
     * Метод возвращает из базы данных все записи в карточке указанного пользователя.
     *
     * @param user
     * @return все записи из карточки пользователя.
     * @throws DAOException
     */
    @Override
    public List<CardNote> takeNotesByUser(User user) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<CardNote> cardNotes = new ArrayList<>();
        try {
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(SQLRequest.TAKE_NOTES_BY_USER_SQL);
            ps.setLong(1, user.getId());
            rs = ps.executeQuery();

            while (rs.next()) {
                cardNotes.add(createNoteObjectFromResultSet(rs));
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("SqlLibDAOImpl: takeNotesByUser(): " + e.getMessage());
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return cardNotes;
    }

    /**
     * Метод делает запись в карточке неактивной.
     *
     * @param cardNoteId
     * @return <code>true</code> когда операция прошла успешно, <code>false</code> в противном случае
     * @throws DAOException
     */
    @Override
    public boolean updateNoteActivity(long cardNoteId) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        boolean success = false;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);
            CardNote cardNote = takeNoteById(cardNoteId);
            if (cardNote != null) {
                ps = connection.prepareStatement(SQLRequest.CANCEL_ORDER_SQL);
                ps.setLong(1, cardNoteId);
                success = ps.executeUpdate() >= 1;
                success = success && increaseAvailableBookQuantity(connection, cardNote.getBook().getId());

                if (success) {
                    connection.commit();
                } else {
                    connection.rollback();
                }
            }
            connection.setAutoCommit(true);
        } catch (SQLException | ConnectionPoolException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                throw new DAOException("SqlLibDAOImpl: updateNoteActivity(): " + e.getMessage());
            }
            throw new DAOException("SqlLibDAOImpl: updateNoteActivity(): " + e.getMessage());
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return success;
    }

    /**
     * Метод сохраняет книгу в базу данных.
     * <p>
     * Если добавляемая книга уже есть в базе, то колличество ее экземпляров и колличество доступных единиц увеличивается
     * на указанное число. Если добавляемой книги в базе нет, но есть ее авторы, то в базу добавляется сначала книга,
     * а потом связи книга-автор. Если авторов в базе также нет, то сначала добавляется книга, после авторы,
     * а потом связи книга-авор.
     *
     * @param book
     * @return книга из базы данных со всеми её авторами.
     * @throws DAOException
     */
    @Override
    public Book saveBook(Book book) throws DAOException {
        Connection connection = null;
        boolean bookSuccess = false;
        boolean authorSuccess = false;
        boolean bookAuthorSuccess = false;
        Book currentBook = null;

        try {
            connection = connectionPool.takeConnection();
            connection.setAutoCommit(false);

            currentBook = takeBook(connection, book);
            if (currentBook != null) {
                bookSuccess = increaseBookQuantity(connection, currentBook.getId(), book.getQuantity());
                currentBook = takeBook(connection, book);
                authorSuccess = bookAuthorSuccess = bookSuccess;
            } else {
                bookSuccess = addNewBook(connection, book);
                book.setId(getLastInsertId(connection.createStatement()));

                for (Author author : book.getAuthors()) {
                    Author currentAuthor = takeAuthor(connection, author);
                    if (currentAuthor == null) {
                        authorSuccess = addNewAuthor(connection, author);
                        currentAuthor = takeAuthor(connection, author);
                    } else {
                        authorSuccess = true;
                    }
                    bookAuthorSuccess = addBookAuthorPair(connection, currentAuthor.getId(), book.getId());
                }
                currentBook = takeBook(connection, book);
            }
            if (bookSuccess && authorSuccess && bookAuthorSuccess) {
                connection.commit();
            } else {
                connection.rollback();
                currentBook = null;
            }
            connection.setAutoCommit(true);
        } catch (SQLException | ConnectionPoolException e) {
            try {
                if (connection != null) {
                    connection.rollback();
                    connection.setAutoCommit(true);
                }
            } catch (SQLException ex) {
                throw new DAOException("SqlLibDAOImpl: saveBook(): " + e.getMessage());
            }
            throw new DAOException("SqlLibDAOImpl: saveBook(): " + e.getMessage());
        } finally {
            connectionPool.closeConnection(connection);
        }
        return currentBook;
    }

    /**
     * Метод возвращает книгу из базы данных по идентификационному номеру.
     *
     * @param bookId
     * @return книга из базы данных со всеми её авторами.
     * @throws DAOException
     */
    @Override
    public Book takeBookById(long bookId) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        Book book = null;
        try {
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(SQLRequest.TAKE_BOOK_BY_ID_SQL);
            ps.setLong(1, bookId);
            rs = ps.executeQuery();

            if (rs.next()) {
                book = createBookObjectFromResultSet(connection, rs);
            }
            if (book != null && !book.getAuthors().isEmpty() && book.getAuthors().get(0) == null) {
                book = null;
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("SqlLibDAOImpl: takeBookById(): " + e.getMessage());
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return book;
    }

    /**
     * Метод возвращает книги из базы данных по части насзвания.
     *
     * @param title
     * @return список книг из базы данных в названии которых встречается передаваемая строка.
     * @throws DAOException
     */
    @Override
    public List<Book> takeBookByPartOfTitle(String title) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Book> books = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();
            ps = connection.prepareStatement(SQLRequest.TAKE_BOOKS_BY_PART_OF_TITLE_SQL);
            ps.setString(1, title);
            rs = ps.executeQuery();

            while (rs.next()) {
                Book book = createBookObjectFromResultSet(connection, rs);
                if (book.getAuthors() != null && book.getAuthors().get(0) != null) {
                    books.add(book);
                } else {
                    books.clear();
                    break;
                }
            }
            rs.close();
        } catch (SQLException | ConnectionPoolException e) {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException ex) {
                throw new DAOException("SqlLibDAOImpl: takeBookByPartOfTitle(): " + e.getMessage());
            }
            throw new DAOException("SqlLibDAOImpl: takeBookByPartOfTitle(): " + e.getMessage());
        } finally {
            connectionPool.closeConnection(connection, ps, rs);
        }
        return books;
    }

    /**
     * Метод возвращает все книги из базы данных.
     *
     * @return список книг из базы данных.
     * @throws DAOException
     */
    @Override
    public List<Book> takeAllBooks() throws DAOException {
        Connection connection = null;
        Statement st = null;
        ResultSet rs = null;

        List<Book> books = new ArrayList<>();

        try {
            connection = connectionPool.takeConnection();
            st = connection.createStatement();
            rs = st.executeQuery(SQLRequest.TAKE_ALL_BOOKS_SQL);

            while (rs.next()) {
                Book book = createBookObjectFromResultSet(connection, rs);
                if (book.getAuthors() != null && !book.getAuthors().isEmpty() && book.getAuthors().get(0) != null) {
                    books.add(book);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("SqlLibDAOImpl: takeAllBooks(): " + e.getMessage());
        } finally {
            connectionPool.closeConnection(connection, st, rs);
        }
        return books;
    }

    /**
     * Метод удаляет из базы данных однин экземпляр книги.
     * <p>
     * Если число доступных экземпляров книги и общее колличество больше, чем один, то метод уменьшает эти значения
     * на единицу. Если общее колличество экземпляров в базе равно единице, то книга из базы удаляется полностью.
     *
     * @param bookId
     * @return <code>true</code> когда операция прошла успешно, <code>false</code> в противном случае
     * @throws DAOException
     */
    @Override
    public boolean deleteBook(long bookId) throws DAOException {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = connectionPool.takeConnection();
            Book book = takeBookById(bookId);
            if (book != null) {
                int quantity = book.getQuantity();
                int availableBooks = book.getAvailableBooks();

                if (quantity > 1 && availableBooks >= 1) {
                    return decreaseBookQuantityByOne(connection, bookId);
                } else if (quantity == 1 && availableBooks == 1) {
                    return deleteAllBookCopies(connection, bookId);
                }
            }
        } catch (SQLException | ConnectionPoolException e) {
            throw new DAOException("SqlLibDAOImpl: deleteBook(): " + e.getMessage());
        } finally {
            connectionPool.closeConnection(connection, ps);
        }
        return false;
    }

    //////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Метод возвращает список авторов из базы данных по идентификационному номеру книги.
     *
     * @param connection
     * @param bookId
     * @return список авторов из базы данных.
     * @throws SQLException
     */
    private List<Author> takeAuthorsByBookId(Connection connection, long bookId) throws SQLException {
        List<Author> authors = new ArrayList<>();
        ResultSet rs = null;
        try (PreparedStatement ps = connection.prepareStatement(SQLRequest.TAKE_AUTHORS_BY_BOOK_ID_SQL)) {
            ps.setLong(1, bookId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Author foundAuthor = createAuthorObjectFromResultSet(rs);
                if (foundAuthor.getSurname() != null) {
                    authors.add(foundAuthor);
                }
            }
            rs.close();
        } catch (SQLException e) {
            if (rs != null) {
                rs.close();
            }
            throw new SQLException("takeAuthorsByBookId(): " + e.getMessage());
        }
        return authors;
    }

    /**
     * Метод возвращает книгу из базы данных по объекту книги.
     * <p>
     * Передаваемая книга не обязана иметь идентификационный номер или список авторов, поиск проходит по названию,
     * году издания, языку, и колличеству страниц.
     *
     * @param connection
     * @param connection
     * @param book
     * @return книга из базы данных со всеми её авторами.
     * @throws SQLException
     */
    private Book takeBook(Connection connection, Book book) throws SQLException {
        ResultSet rs = null;
        Book foundBook = null;

        try (PreparedStatement ps = connection.prepareStatement(SQLRequest.TAKE_BOOK_SQL)) {
            ps.setString(1, book.getTitle());
            ps.setInt(2, book.getYear());
            ps.setString(3, book.getLang().toString());
            ps.setInt(4, book.getNumberOfPages());
            rs = ps.executeQuery();


            if (rs.next()) {
                foundBook = createBookObjectFromResultSet(connection, rs);
            }

            List<Author> authors = book.getAuthors();
            List<Author> foundAuthors;

            if (foundBook != null && !foundBook.getAuthors().isEmpty() && foundBook.getAuthors().get(0) != null) {
                foundAuthors = foundBook.getAuthors();

                if (Arrays.equals(authors.toArray(), foundAuthors.toArray())) {
                    return foundBook;
                }
            } else {
                return null;
            }
            rs.close();
        } catch (SQLException e) {
            if (rs != null) {
                rs.close();
            }
            throw new SQLException("takeBook(): " + e.getMessage());
        }
        return foundBook;
    }

    /**
     * Метод увеличивает колличество экземпляров книги и колличество доступных единиц на передаваемое число.
     *
     * @param connection
     * @param bookId
     * @param quantity
     * @return <code>true</code> когда операция прошла успешно, <code>false</code> в противном случае
     * @throws SQLException
     */
    private boolean increaseBookQuantity(Connection connection, long bookId, int quantity) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQLRequest.INCREASE_BOOK_QUANTITY_SQL)) {
            ps.setInt(1, quantity);
            ps.setInt(2, quantity);
            ps.setLong(3, bookId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("increaseBookQuantity(): " + e.getMessage());
        }
    }

    /**
     * Метод полностью удаляет книгу из базы данных.
     *
     * @param connection
     * @param bookId
     * @return <code>true</code> когда операция прошла успешно, <code>false</code> в противном случае
     * @throws SQLException
     */
    private boolean deleteAllBookCopies(Connection connection, long bookId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQLRequest.DELETE_BOOK_SQL)) {
            ps.setLong(1, bookId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("deleteAllBookCopies(): " + e.getMessage());
        }
    }

    /**
     * Метод уменьшает общее колличество книги и колличество доступных экземпляров на единицу.
     *
     * @param connection
     * @param bookId
     * @return <code>true</code> когда операция прошла успешно, <code>false</code> в противном случае
     * @throws SQLException
     */
    private boolean decreaseBookQuantityByOne(Connection connection, long bookId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQLRequest.DECREASE_BOOK_QUANTITY_BY_ONE_SQL)) {
            ps.setLong(1, bookId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("decreaseBookQuantityByOne(): " + e.getMessage());
        }
    }

    /**
     * Метод возвращает автора из базы данных по объекту автора.
     * <p>
     * Передаваемый автор не обязан иметь идентификационный номер, поиск проходит по имени, фамилии и отчеству.
     *
     * @param connection
     * @param author
     * @return автор из базы данных.
     * @throws SQLException
     */
    private Author takeAuthor(Connection connection, Author author) throws SQLException {
        ResultSet rs = null;
        Author foundAuthor = null;

        try (PreparedStatement ps = connection.prepareStatement(SQLRequest.TAKE_AUTHOR_SQL)) {
            ps.setString(1, author.getSurname());
            ps.setString(2, author.getName());
            if (author.getPatronymic() != null) {
                ps.setString(3, author.getPatronymic());
            } else {
                ps.setString(3, "");
            }
            rs = ps.executeQuery();

            if (rs.next()) {
                foundAuthor = createAuthorObjectFromResultSet(rs);
            }
            rs.close();
        } catch (SQLException e) {
            if (rs != null) {
                rs.close();
            }
            throw new SQLException("takeAuthor(): " + e.getMessage());
        }
        return foundAuthor;
    }

    /**
     * Метод добавляет нового автора в базу данных.
     *
     * @param connection
     * @param author
     * @return <code>true</code> когда операция прошла успешно, <code>false</code> в противном случае
     * @throws SQLException
     */
    private boolean addNewAuthor(Connection connection, Author author) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQLRequest.ADD_NEW_AUTHOR_SQL)) {
            ps.setString(1, author.getSurname());
            ps.setString(2, author.getName());
            if (author.getPatronymic() != null) {
                ps.setString(3, author.getPatronymic());
            } else {
                ps.setString(3, "");
            }

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("addNewAuthor(): " + e.getMessage());
        }
    }

    /**
     * Метод добавляет новую книгу в базу данных.
     *
     * @param connection
     * @param book
     * @return <code>true</code> когда операция прошла успешно, <code>false</code> в противном случае
     * @throws SQLException
     */
    private boolean addNewBook(Connection connection, Book book) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQLRequest.ADD_NEW_BOOK_SQL)) {
            ps.setString(1, book.getTitle());
            ps.setInt(2, book.getYear());
            ps.setString(3, book.getLang().toString());
            ps.setInt(4, book.getNumberOfPages());
            ps.setInt(5, book.getQuantity());
            ps.setInt(6, book.getQuantity());
            ps.setString(7, book.getAnnotation());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("addNewBook(): " + e.getMessage());
        }
    }

    /**
     * Метод добавляет новую пару книг-автор в базу данных.
     *
     * @param connection
     * @param authorId
     * @param bookId
     * @return <code>true</code> когда операция прошла успешно, <code>false</code> в противном случае
     * @throws SQLException
     */
    private boolean addBookAuthorPair(Connection connection, long authorId, long bookId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQLRequest.ADD_BOOK_AUTHOR_PAIR_SQL)) {
            ps.setLong(1, bookId);
            ps.setLong(2, authorId);

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new SQLException("addBookAuthorPair(): " + e.getMessage());
        }
    }

    /**
     * Метод возвращает объект записи в картоке, созданный из переданного объекта <code>ResultSet</code>.
     *
     * @param rs <code>ResultSet</code>
     * @return запись в карточке
     * @throws DAOException
     */
    private CardNote createNoteObjectFromResultSet(ResultSet rs) throws DAOException {
        CardNote cardNote = new CardNote();
        try {
            cardNote.setId(rs.getLong(1));
            cardNote.setInitialDate(Date.valueOf(rs.getString(2)));
            cardNote.setFinalDate(Date.valueOf(rs.getString(3)));
            cardNote.setDestination(Destination.valueOf((rs.getString(4)).replace(" ", "_")));
            cardNote.setActive(rs.getBoolean(5));
            cardNote.setBook(takeBookById(rs.getLong(6)));
            cardNote.setUserId(rs.getLong(7));
        } catch (SQLException | DAOException e) {
            throw new DAOException("createNoteObjectFromResultSet(): " + e.getMessage());
        }
        return cardNote;
    }

    /**
     * Метод возвращает объект книги, созданный из переданного объекта <code>ResultSet</code>.
     *
     * @param connection
     * @param rs         <code>ResultSet</code>
     * @return книга
     * @throws SQLException
     */
    private Book createBookObjectFromResultSet(Connection connection, ResultSet rs) throws SQLException {
        Book book = new Book();
        try {
            book.setId(rs.getLong(1));
            book.setTitle(rs.getString(2));
            book.setYear(rs.getInt(3));
            book.setLang(Language.valueOf(rs.getString(4)));
            book.setNumberOfPages(rs.getInt(5));
            book.setQuantity(rs.getInt(6));
            book.setAvailableBooks(rs.getInt(7));
            book.setAnnotation(rs.getString(8));

            book.setAuthors(takeAuthorsByBookId(connection, book.getId()));
        } catch (SQLException e) {
            throw new SQLException("createBookObjectFromResultSet(): " + e.getMessage());
        }
        return book;
    }

    /**
     * Метод возвращает объект автора, созданный из переданного объекта <code>ResultSet</code>.
     *
     * @param rs <code>ResultSet</code>
     * @return автор
     * @throws SQLException
     */
    private Author createAuthorObjectFromResultSet(ResultSet rs) throws SQLException {
        Author author = new Author();
        try {
            author.setId(rs.getLong(1));
            author.setSurname(rs.getString(2));
            author.setName(rs.getString(3));
            author.setPatronymic(rs.getString(4));
        } catch (SQLException e) {
            throw new SQLException("createAuthorObjectFromResultSet(): " + e.getMessage());
        }
        return author;
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
     * Метод увеличиват колличество доступных книг на единицу.
     *
     * @param connection
     * @param bookId
     * @return <code>true</code> когда операция прошла успешно, <code>false</code> в противном случае
     * @throws SQLException
     */
    private boolean increaseAvailableBookQuantity(Connection connection, long bookId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQLRequest.INCREASE_AVAILABLE_BOOK_QUANTITY_BY_ONE_SQL)) {
            ps.setLong(1, bookId);
            return ps.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new SQLException("increaseAvailableBookQuantity(): " + e.getMessage());
        }
    }

    /**
     * Метод уменьшает колличество доступных книг на единицу.
     *
     * @param connection
     * @param bookId
     * @return <code>true</code> когда операция прошла успешно, <code>false</code> в противном случае
     * @throws SQLException
     */
    private boolean decreaseAvailableBookQuantity(Connection connection, long bookId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQLRequest.DECREASE_AVAILABLE_BOOK_QUANTITY_BY_ONE_SQL)) {
            ps.setLong(1, bookId);
            return ps.executeUpdate() >= 1;
        } catch (SQLException e) {
            throw new SQLException("decreaseAvailableBookQuantity(): " + e.getMessage());
        }
    }
}