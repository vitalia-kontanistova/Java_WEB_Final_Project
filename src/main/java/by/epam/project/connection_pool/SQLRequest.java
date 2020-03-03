package by.epam.project.connection_pool;

public final class SQLRequest {
    private SQLRequest() {
    }

    public static final String GET_LAST_INSERT_ID_SQL = "SELECT LAST_INSERT_ID()";

    public static final String ADD_USER_SQL = "INSERT INTO users(`u_id`,`u_surname`,`u_name`,`u_patronymic`,`u_birthday`,`u_phone`,`u_role`) VALUES(?,?,?,?,?,?,?)";
    public static final String TAKE_USER_BY_ID_SQL = "SELECT * FROM users WHERE u_id=?";
    public static final String TAKE_USER_BY_PHONE_SQL = "SELECT * FROM users WHERE u_phone=?";
    public static final String TAKE_USER_BY_EMAIL_SQL = "SELECT * FROM users WHERE u_id=(SELECT u_id FROM access_info WHERE ai_email=?)";
    public static final String TAKE_ALL_USERS_SQL = "SELECT * FROM users";
    public static final String DELETE_USER_SQL = "DELETE users FROM users WHERE users.u_id=?";
    public static final String DELETE_ACCESS_INFO_BY_USER_ID_SQL = "DELETE access_info FROM access_info WHERE u_id=?";

    public static final String ADD_ACCESS_INFO_SQL = "INSERT INTO access_info (`ai_id`,`ai_email`,`ai_password`,`u_id`) VALUES(?,?,?,?)";
    public static final String AUTHORISATION_SQL = "SELECT * FROM access_info WHERE ai_email=?";
    public static final String UPDATE_PASSWORD_SQL = "UPDATE access_info SET ai_password=? WHERE u_id=?";

    public static final String ADD_NEW_BOOK_SQL = "INSERT into books (b_title, b_year, b_lang, b_pages, b_quantity, b_available_books, b_annotation) VALUES (?,?,?,?,?,?,?)";
    public static final String TAKE_BOOK_SQL = "SELECT * FROM books WHERE b_title=? and b_year=? and b_lang=? and b_pages=?";
    public static final String TAKE_BOOK_BY_ID_SQL = "SELECT * FROM books where b_id=?";
    public static final String TAKE_BOOK_BY_NOTE_ID_SQL = "SELECT * FROM books WHERE b_id=(SELECT b_id FROM card_notes WHERE cn_id=?)";
    public static final String TAKE_BOOKS_BY_PART_OF_TITLE_SQL = "SELECT * FROM books where b_title LIKE ?";
    public static final String TAKE_ALL_BOOKS_SQL = "SELECT * FROM books";
    public static final String INCREASE_AVAILABLE_BOOK_QUANTITY_BY_ONE_SQL = "UPDATE books SET b_available_books=b_available_books+1 WHERE b_id=?";
    public static final String DECREASE_AVAILABLE_BOOK_QUANTITY_BY_ONE_SQL = "UPDATE books SET b_available_books=b_available_books-1 WHERE b_id=?";
    public static final String INCREASE_BOOK_QUANTITY_SQL = "UPDATE books SET b_quantity=(b_quantity+?), b_available_books=(b_available_books+?) WHERE b_id=?";
    public static final String DECREASE_BOOK_QUANTITY_BY_ONE_SQL = "UPDATE books SET b_quantity = b_quantity-1, b_available_books=b_available_books-1 WHERE b_id=?";
    public static final String DELETE_BOOK_SQL = "DELETE books FROM books WHERE b_id=?";

    public static final String ADD_NEW_AUTHOR_SQL = "INSERT into authors (a_surname, a_name, a_patronymic) VALUES (?,?,?)";
    public static final String TAKE_AUTHOR_SQL = "SELECT * FROM authors WHERE a_surname=? and a_name=? and a_patronymic=?";
    public static final String TAKE_AUTHORS_BY_BOOK_ID_SQL = "SELECT * FROM authors WHERE a_id IN (SELECT a_id FROM books_has_authors WHERE b_id=?)";
    public static final String ADD_BOOK_AUTHOR_PAIR_SQL = "INSERT into books_has_authors (b_id, a_id) VALUES (?,?)";

    public static final String ADD_CARD_NOTE_SQL = "INSERT INTO card_notes(`cn_initial_date`,`cn_final_date`,`cn_destination`,`cn_is_active`,`b_id`,`u_id`) VALUES(?,?,?,?,?,?)";
    public static final String TAKE_NOTE_BY_ID_SQL = "SELECT * FROM card_notes WHERE cn_id=?";
    public static final String TAKE_NOTES_BY_USER_SQL = "SELECT * FROM card_notes WHERE u_id=?";
    public static final String CANCEL_ORDER_SQL = "UPDATE card_notes SET cn_is_active='N' WHERE cn_id=?";
    public static final String DELETE_CARD_NOTE_SQL = "DELETE card_notes FROM card_notes WHERE cn_id=?";
}