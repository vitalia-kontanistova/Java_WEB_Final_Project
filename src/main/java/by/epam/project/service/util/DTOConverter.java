package by.epam.project.service.util;

import by.epam.project.bean.*;
import by.epam.project.bean.dto.AccessDTO;
import by.epam.project.bean.dto.BookDTO;
import by.epam.project.bean.dto.UserDTO;
import by.epam.project.controller.util.ParameterName;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс предназначен для преобразования data transfer objects в объекты entity.
 */

public class DTOConverter {
    private static final Validator validator = Validator.getInstance();

    private static DTOConverter instance = new DTOConverter();

    private DTOConverter() {
    }

    public static DTOConverter getInstance() {
        return instance;
    }

    public Book dtoConvertIntoBook(BookDTO bookDTO) {
        Book book = null;

        if (validator.bookCheck(bookDTO)) {
            int year = Integer.parseInt(bookDTO.getYear());
            Language lang = Language.valueOf(bookDTO.getLang().toUpperCase());
            int numberOfPages = Integer.parseInt(bookDTO.getNumberOfPages());
            int quantity = Integer.parseInt(bookDTO.getQuantity());
            List<Author> authors = new ArrayList<>();

            String surname1 = bookDTO.getAuthor1Surname();
            String surname2 = bookDTO.getAuthor2Surname();
            String surname3 = bookDTO.getAuthor3Surname();

            String name1 = bookDTO.getAuthor1Name();
            String name2 = bookDTO.getAuthor2Name();
            String name3 = bookDTO.getAuthor3Name();

            String patronymic1 = bookDTO.getAuthor1Patronymic();
            String patronymic2 = bookDTO.getAuthor2Patronymic();
            String patronymic3 = bookDTO.getAuthor3Patronymic();

            if (surname1 != null && !surname1.equals("") && name1 != null && !name1.equals("")) {
                authors.add(new Author(name1, surname1, patronymic1));
            }
            if (surname2 != null && !surname2.equals("") && name2 != null && !name2.equals("")) {
                authors.add(new Author(name2, surname2, patronymic2));
            }
            if (surname3 != null && !surname3.equals("") && name3 != null && !name3.equals("")) {
                authors.add(new Author(name3, surname3, patronymic3));
            }

            book = new Book();
            book.setTitle(bookDTO.getTitle());
            book.setYear(year);
            book.setLang(lang);
            book.setNumberOfPages(numberOfPages);
            book.setQuantity(quantity);
            book.setAnnotation(bookDTO.getAnnotation());
            book.setAuthors(authors);
        }
        return book;
    }

    public User dtoConvertIntoUser(UserDTO userDTO) {
        User user = null;
        if (validator.userInfoCheck(userDTO)) {
            String bdYear = userDTO.getBdYear();
            String bdMonth = (userDTO.getBdMonth());
            String bdDay = (userDTO.getBdDay());

            Date birthday = Date.valueOf(bdYear + "-" + bdMonth + "-" + bdDay);
            Role roleEnum = Role.valueOf(userDTO.getRole().toUpperCase());

            user = new User(userDTO.getSurname(), userDTO.getName(), userDTO.getPatronymic(), birthday, userDTO.getPhone(), roleEnum);
        }
        return user;
    }

    public AccessInfo dtoConvertIntoAccess(AccessDTO accessDTO) {
        AccessInfo accessInfo = null;
        if (validator.accessInfoCheck(accessDTO)) {
            accessInfo = new AccessInfo(accessDTO.getEmail(), accessDTO.getPassword());
        }
        return accessInfo;
    }

    public BookDTO reqConvertIntoBookDTO(HttpServletRequest req) {
        String title = req.getParameter(ParameterName.BOOK_TITLE);
        String year = req.getParameter(ParameterName.BOOK_YEAR);
        String lang = req.getParameter(ParameterName.BOOK_LANG);
        String numberOfPages = req.getParameter(ParameterName.BOOK_NUMBER_OF_PAGES);
        String quantity = req.getParameter(ParameterName.BOOK_QUANTITY);
        String annotation = req.getParameter(ParameterName.BOOK_ANNOTATION);

        String author1Surname = req.getParameter(ParameterName.AUTHOR_1_SURNAME);
        String author1Name = req.getParameter(ParameterName.AUTHOR_1_NAME);
        String author1Patronymic = req.getParameter(ParameterName.AUTHOR_1_PATRONYMIC);

        String author2Surname = req.getParameter(ParameterName.AUTHOR_2_SURNAME);
        String author2Name = req.getParameter(ParameterName.AUTHOR_2_NAME);
        String author2Patronymic = req.getParameter(ParameterName.AUTHOR_2_PATRONYMIC);

        String author3Surname = req.getParameter(ParameterName.AUTHOR_3_SURNAME);
        String author3Name = req.getParameter(ParameterName.AUTHOR_3_NAME);
        String author3Patronymic = req.getParameter(ParameterName.AUTHOR_3_PATRONYMIC);

        return new BookDTO(title, year, lang, numberOfPages, quantity, annotation,
                author1Surname, author1Name, author1Patronymic, author2Surname, author2Name, author2Patronymic,
                author3Surname, author3Name, author3Patronymic);
    }

    public UserDTO reqConvertIntoUserDTO(HttpServletRequest req) {
        String userSurname = req.getParameter(ParameterName.USER_SURNAME);
        String userName = req.getParameter(ParameterName.USER_NAME);
        String userPatronymic = req.getParameter(ParameterName.USER_PATRONYMIC);
        String bdYear = req.getParameter(ParameterName.BD_YEAR);
        String bdMonth = req.getParameter(ParameterName.BD_MONTH);
        String bdDay = req.getParameter(ParameterName.BD_DAY);
        String phone = "(" + req.getParameter(ParameterName.PHONE_CODE) + ")" +
                req.getParameter(ParameterName.PHONE_NUMBER);
        String role = req.getParameter(ParameterName.ROLE);

        return new UserDTO(userSurname, userName, userPatronymic, bdYear, bdMonth, bdDay, phone, role);
    }

    public AccessDTO reqConvertIntoAccessDTO(HttpServletRequest req) {
        String email = req.getParameter(ParameterName.EMAIL);
        String password = req.getParameter(ParameterName.PASSWORD);

        return new AccessDTO(email, password);
    }
}