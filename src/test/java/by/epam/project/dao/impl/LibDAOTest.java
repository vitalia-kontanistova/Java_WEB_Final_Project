package by.epam.project.dao.impl;

import by.epam.project.bean.*;
import by.epam.project.dao.DAOProvider;
import by.epam.project.dao.LibDAO;
import by.epam.project.dao.UserDAO;
import by.epam.project.exception.DAOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class LibDAOTest {

    private final static Logger logger = LogManager.getLogger();
    LibDAO libDAO = DAOProvider.getInstance().getLibDAO();
    UserDAO userDAO = DAOProvider.getInstance().getUserDAO();
    private static Author author;
    private static Book book;
    private static CardNote cardNote;
    private static String partOfTitle;

    @BeforeClass
    public static void setUp() {
        String surname = "Surname";
        String name = "Name";
        String patronymic = "Patronymic";

        String title = "Title";
        partOfTitle = "%love%";
        List<Author> authors = new ArrayList<>();
        int year = 2000;
        Language lang = Language.EO;
        int numberOfPages = 100;
        int quantity = 1;
        int availableBooks = 1;
        String annotation = "Annotation";

        Date initialDate = Date.valueOf("2020-03-03");
        Date finalDate = Date.valueOf("2020-03-05");
        Destination destination = Destination.ORDER;
        boolean active = true;
        long userId = 5;

        author = new Author(name, surname, patronymic);
        authors.add(author);
        book = new Book(title, authors, year, lang, numberOfPages, quantity, availableBooks, annotation);
        cardNote = new CardNote(initialDate, finalDate, destination, active, book, userId);
    }

    @Test
    public void saveBookTest() {
        Book actualBook;
        Book expectedBook = book;
        try {
            actualBook = libDAO.saveBook(expectedBook);
            libDAO.deleteBook(actualBook.getId());

            Assert.assertEquals(expectedBook.getTitle(), actualBook.getTitle());
            Assert.assertEquals(expectedBook.getAuthors().get(0).getSurname(), actualBook.getAuthors().get(0).getSurname());
            Assert.assertEquals(expectedBook.getAuthors().get(0).getName(), actualBook.getAuthors().get(0).getName());
            Assert.assertEquals(expectedBook.getAuthors().get(0).getPatronymic(), actualBook.getAuthors().get(0).getPatronymic());
            Assert.assertEquals(expectedBook.getYear(), actualBook.getYear());
            Assert.assertEquals(expectedBook.getLang(), actualBook.getLang());
            Assert.assertEquals(expectedBook.getNumberOfPages(), actualBook.getNumberOfPages());
            Assert.assertEquals(expectedBook.getQuantity(), actualBook.getQuantity());
            Assert.assertEquals(expectedBook.getAvailableBooks(), actualBook.getAvailableBooks());
            Assert.assertEquals(expectedBook.getAnnotation(), actualBook.getAnnotation());
        } catch (DAOException e) {
            logger.error(e);
        }
    }

    @Test
    public void takeBookByIdTest() {
        Book actualBook;
        Book expectedBook = book;
        try {
            actualBook = libDAO.saveBook(expectedBook);
            actualBook = libDAO.takeBookById(actualBook.getId());
            libDAO.deleteBook(actualBook.getId());

            Assert.assertEquals(expectedBook.getTitle(), actualBook.getTitle());
            Assert.assertEquals(expectedBook.getAuthors().get(0).getSurname(), actualBook.getAuthors().get(0).getSurname());
            Assert.assertEquals(expectedBook.getAuthors().get(0).getName(), actualBook.getAuthors().get(0).getName());
            Assert.assertEquals(expectedBook.getAuthors().get(0).getPatronymic(), actualBook.getAuthors().get(0).getPatronymic());
            Assert.assertEquals(expectedBook.getYear(), actualBook.getYear());
            Assert.assertEquals(expectedBook.getLang(), actualBook.getLang());
            Assert.assertEquals(expectedBook.getNumberOfPages(), actualBook.getNumberOfPages());
            Assert.assertEquals(expectedBook.getQuantity(), actualBook.getQuantity());
            Assert.assertEquals(expectedBook.getAvailableBooks(), actualBook.getAvailableBooks());
            Assert.assertEquals(expectedBook.getAnnotation(), actualBook.getAnnotation());
        } catch (DAOException e) {
            logger.error(e);
        }
    }

    @Test
    public void takeAllTest() {
        List<Book> list = new ArrayList<>();
        try {
            list = libDAO.takeAllBooks();
        } catch (DAOException e) {
            logger.error(e);
        }
        Assert.assertEquals(614, list.size(), 1);
    }

    @Test
    public void takeBookByPartOfTitleTest() {
        List<Book> list = new ArrayList<>();
        try {
            list = libDAO.takeBookByPartOfTitle(partOfTitle);
        } catch (DAOException e) {
            logger.error(e);
        }
        Assert.assertEquals(4, list.size(), 1);
    }

    @Test
    public void deleteBookTest() {
        Book actualBook;
        Book expectedBook = book;
        boolean success;
        try {
            actualBook = libDAO.saveBook(expectedBook);
            success = libDAO.deleteBook(actualBook.getId());
            Assert.assertTrue(success);
        } catch (DAOException e) {
            logger.error(e);
        }
    }

    ////////////////////////////////////////////////////

    @Test
    public void saveCardNoteTest() {
        CardNote actualNote;
        Book actualBook;
        CardNote expectedNote = cardNote;
        Book expectedBook = book;
        try {
            actualBook = libDAO.saveBook(expectedBook);
            actualNote = libDAO.saveCardNote(expectedNote);
            libDAO.updateNoteActivity(actualNote.getId());
            libDAO.deleteBook(actualBook.getId());

            Assert.assertEquals(expectedNote.getInitialDate(), actualNote.getInitialDate());
            Assert.assertEquals(expectedNote.getFinalDate(), actualNote.getFinalDate());
            Assert.assertEquals(expectedNote.getDestination(), actualNote.getDestination());
            Assert.assertEquals(expectedNote.isActive(), actualNote.isActive());

            Assert.assertEquals(expectedNote.getBook().getTitle(),
                    actualNote.getBook().getTitle());
            Assert.assertEquals(expectedNote.getBook().getYear(),
                    actualNote.getBook().getYear());
            Assert.assertEquals(expectedNote.getBook().getLang(),
                    actualNote.getBook().getLang());
            Assert.assertEquals(expectedNote.getBook().getNumberOfPages(),
                    actualNote.getBook().getNumberOfPages());
            Assert.assertEquals(expectedNote.getBook().getQuantity(),
                    actualNote.getBook().getQuantity());
            Assert.assertEquals(expectedNote.getBook().getAvailableBooks(),
                    actualNote.getBook().getAvailableBooks());
            Assert.assertEquals(expectedNote.getBook().getAnnotation(),
                    actualNote.getBook().getAnnotation());

            Assert.assertEquals(expectedNote.getBook().getAuthors().get(0).getSurname(),
                    actualNote.getBook().getAuthors().get(0).getSurname());
            Assert.assertEquals(expectedNote.getBook().getAuthors().get(0).getName(),
                    actualNote.getBook().getAuthors().get(0).getName());
            Assert.assertEquals(expectedNote.getBook().getAuthors().get(0).getPatronymic(),
                    actualNote.getBook().getAuthors().get(0).getPatronymic());
        } catch (DAOException e) {
            logger.error(e);
        }
    }

    @Test
    public void takeNoteByIdTest() {
        CardNote actualNote;
        Book actualBook;
        CardNote expectedNote = cardNote;
        Book expectedBook = book;
        try {
            actualBook = libDAO.saveBook(expectedBook);
            actualNote = libDAO.saveCardNote(expectedNote);
            actualNote = libDAO.takeNoteById(actualNote.getId());
            libDAO.updateNoteActivity(actualNote.getId());
            libDAO.deleteBook(actualBook.getId());

            Assert.assertEquals(expectedNote.getInitialDate(), actualNote.getInitialDate());
            Assert.assertEquals(expectedNote.getFinalDate(), actualNote.getFinalDate());
            Assert.assertEquals(expectedNote.getDestination(), actualNote.getDestination());
            Assert.assertEquals(expectedNote.isActive(), actualNote.isActive());

            Assert.assertEquals(expectedNote.getBook().getTitle(),
                    actualNote.getBook().getTitle());
            Assert.assertEquals(expectedNote.getBook().getYear(),
                    actualNote.getBook().getYear());
            Assert.assertEquals(expectedNote.getBook().getLang(),
                    actualNote.getBook().getLang());
            Assert.assertEquals(expectedNote.getBook().getNumberOfPages(),
                    actualNote.getBook().getNumberOfPages());
            Assert.assertEquals(expectedNote.getBook().getQuantity(),
                    actualNote.getBook().getQuantity());
            Assert.assertEquals(expectedNote.getBook().getAvailableBooks(),
                    actualNote.getBook().getAvailableBooks() + 1);
            Assert.assertEquals(expectedNote.getBook().getAnnotation(),
                    actualNote.getBook().getAnnotation());

            Assert.assertEquals(expectedNote.getBook().getAuthors().get(0).getSurname(),
                    actualNote.getBook().getAuthors().get(0).getSurname());
            Assert.assertEquals(expectedNote.getBook().getAuthors().get(0).getName(),
                    actualNote.getBook().getAuthors().get(0).getName());
            Assert.assertEquals(expectedNote.getBook().getAuthors().get(0).getPatronymic(),
                    actualNote.getBook().getAuthors().get(0).getPatronymic());
        } catch (DAOException e) {
            logger.error(e);
        }
    }

    @Test
    public void takeNotesByUserTest() {
        List<CardNote> list = new ArrayList<>();
        try {
            User actualUser = userDAO.takeById(5);
            list = libDAO.takeNotesByUser(actualUser);
        } catch (DAOException e) {
            logger.error(e);
        }
        Assert.assertEquals(13, list.size(), 1);
    }

    @Test
    public void updateNoteActivityTest() {
        CardNote actualNote;
        Book actualBook;
        CardNote expectedNote = cardNote;
        Book expectedBook = book;
        boolean success;
        try {
            actualBook = libDAO.saveBook(expectedBook);
            actualNote = libDAO.saveCardNote(expectedNote);
            actualNote = libDAO.takeNoteById(actualNote.getId());
            success = libDAO.updateNoteActivity(actualNote.getId());
            libDAO.deleteBook(actualBook.getId());

            Assert.assertTrue(success);
        } catch (DAOException e) {
            logger.error(e);
        }
    }

    @Test
    public void deleteCardNoteTest() {
        CardNote actualNote;
        Book actualBook;
        CardNote expectedNote = cardNote;
        Book expectedBook = book;
        boolean success;
        try {
            actualBook = libDAO.saveBook(expectedBook);
            actualNote = libDAO.saveCardNote(expectedNote);
            actualNote = libDAO.takeNoteById(actualNote.getId());
            libDAO.updateNoteActivity(actualNote.getId());
            success = libDAO.deleteCardNote(actualNote.getId());
            libDAO.deleteBook(actualBook.getId());

            Assert.assertTrue(success);
        } catch (DAOException e) {
            logger.error(e);
        }
    }
}