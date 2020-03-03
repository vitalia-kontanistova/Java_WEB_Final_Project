package by.epam.project.service.util;

import by.epam.project.bean.dto.AccessDTO;
import by.epam.project.bean.dto.BookDTO;
import by.epam.project.bean.dto.UserDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Класс предназначен для валидации передаваемых клиентом данных.
 */
public class Validator {

    private final static String PATTERN_TITLE = ".{3,}";
    private final static String PATTERN_YEAR = "^(190[1-9])$|^(19[1-9]\\d)$|^(20[0-1]\\d)$";
    private final static String PATTERN_LANG = "RU|BE|PL|LV|EN|DE|FR|ES|EO";
    private final static String PATTERN_NUMBER_OF_PAGES = "(0*\\d{1,4})|(2000)";
    private final static String PATTERN_QUANTITY = "(0*\\d{1,2})|(99)";
    private final static String PATTERN_ANNOTATION = ".{3,}|.{1200}";

    private final static String PATTERN_AUTHOR_1_SURNAME = "^[a-zA-Zа-яА-Я']{2,}$|^.{150}$";
    private final static String PATTERN_AUTHOR_1_NAME = "^[a-zA-Zа-яА-Я']{2,}$|^.{150}$";
    private final static String PATTERN_AUTHOR_1_PATRONYMIC = ".*|.{150}";

    private final static String PATTERN_AUTHOR_2_3_SURNAME = ".*|.{150}";
    private final static String PATTERN_AUTHOR_2_3_NAME = ".*|.{150}";
    private final static String PATTERN_AUTHOR_2_3_PATRONYMIC = ".*|.{150}";

    private final static String PATTERN_EMAIL = "^\\S+@[a-z]+\\.[a-z]+$";
    private final static String PATTERN_PASSWORD = ".{9,}";

    private final static String PATTERN_USER_SURNAME = "^[a-zA-Zа-яА-Я']{2,}$|^.{150}$";
    private final static String PATTERN_USER_NAME = "^[a-zA-Zа-яА-Я']{2,}$|^.{150}$";
    private final static String PATTERN_USER_PATRONYMIC = "^[a-zA-Zа-яА-Я']{2,}$|^.{150}$";

    private final static String PATTERN_BD_YEAR = "^(19[4-9]\\d)$|^(20[0-1]\\d)$";
    private final static String PATTERN_BD_MONTH = "^[0-9]$|^(1[0-1])$";
    private final static String PATTERN_BD_DAY = "^[1-9]$|^([1-2]\\d)$|^(3[0-1])$";
    private final static String PATTERN_PHONE = "^(\\(17\\)|\\(25\\)|\\(29\\)|\\(33\\)|\\(44\\))\\d{7}$";


    private static final Validator instance = new Validator();

    private Validator() {
    }

    public static Validator getInstance() {
        return instance;
    }

    public boolean passwordCheck(String password) {
        return compareParameterWithPattern(PATTERN_PASSWORD, password);
    }

    public boolean accessInfoCheck(AccessDTO accessDTO) {
        return compareParameterWithPattern(PATTERN_EMAIL, accessDTO.getEmail()) &&
                passwordCheck(accessDTO.getPassword());
    }

    public boolean userInfoCheck(UserDTO userDTO) {
        return compareParameterWithPattern(PATTERN_USER_SURNAME, userDTO.getSurname()) &&
                compareParameterWithPattern(PATTERN_USER_NAME, userDTO.getName()) &&
                compareParameterWithPattern(PATTERN_USER_PATRONYMIC, userDTO.getPatronymic()) &&

                compareParameterWithPattern(PATTERN_BD_YEAR, userDTO.getBdYear()) &&
                compareParameterWithPattern(PATTERN_BD_MONTH, userDTO.getBdMonth()) &&
                compareParameterWithPattern(PATTERN_BD_DAY, userDTO.getBdDay()) &&

                compareParameterWithPattern(PATTERN_PHONE, userDTO.getPhone());
    }

    public boolean bookCheck(BookDTO bookDTO) {
        return compareParameterWithPattern(PATTERN_TITLE, bookDTO.getTitle()) &&
                compareParameterWithPattern(PATTERN_YEAR, bookDTO.getYear()) &&
                compareParameterWithPattern(PATTERN_LANG, bookDTO.getLang()) &&
                compareParameterWithPattern(PATTERN_NUMBER_OF_PAGES, bookDTO.getNumberOfPages()) &&
                compareParameterWithPattern(PATTERN_QUANTITY, bookDTO.getQuantity()) &&
                compareParameterWithPattern(PATTERN_ANNOTATION, bookDTO.getAnnotation()) &&

                compareParameterWithPattern(PATTERN_AUTHOR_1_SURNAME, bookDTO.getAuthor1Surname()) &&
                compareParameterWithPattern(PATTERN_AUTHOR_1_NAME, bookDTO.getAuthor1Name()) &&
                compareParameterWithPattern(PATTERN_AUTHOR_1_PATRONYMIC, bookDTO.getAuthor1Patronymic()) &&
                compareParameterWithPattern(PATTERN_AUTHOR_2_3_SURNAME, bookDTO.getAuthor2Surname()) &&
                compareParameterWithPattern(PATTERN_AUTHOR_2_3_NAME, bookDTO.getAuthor2Name()) &&
                compareParameterWithPattern(PATTERN_AUTHOR_2_3_PATRONYMIC, bookDTO.getAuthor2Patronymic()) &&
                compareParameterWithPattern(PATTERN_AUTHOR_2_3_SURNAME, bookDTO.getAuthor3Surname()) &&
                compareParameterWithPattern(PATTERN_AUTHOR_2_3_NAME, bookDTO.getAuthor3Name()) &&
                compareParameterWithPattern(PATTERN_AUTHOR_2_3_PATRONYMIC, bookDTO.getAuthor3Patronymic());
    }

    private boolean compareParameterWithPattern(String pattern, String parameter) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(parameter);
        return m.matches();
    }
}
