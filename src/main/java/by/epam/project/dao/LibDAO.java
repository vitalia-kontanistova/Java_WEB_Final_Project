package by.epam.project.dao;

import by.epam.project.bean.Book;
import by.epam.project.bean.CardNote;
import by.epam.project.bean.User;
import by.epam.project.exception.DAOException;

import java.util.List;

public interface LibDAO {
    CardNote saveCardNote(CardNote cardNote) throws DAOException;

    boolean deleteCardNote(long cardNoteId) throws DAOException;

    List<CardNote> takeNotesByUser(User user) throws DAOException;

    CardNote takeNoteById(long id) throws DAOException;

    boolean updateNoteActivity(long cardNoteId) throws DAOException;

    Book saveBook(Book book) throws DAOException;

    Book takeBookById(long id) throws DAOException;

    List<Book> takeBookByPartOfTitle(String title) throws DAOException;

    List<Book> takeAllBooks() throws DAOException;

    boolean deleteBook(long bookId) throws DAOException;
}