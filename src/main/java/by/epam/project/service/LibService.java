package by.epam.project.service;

import by.epam.project.bean.Book;
import by.epam.project.bean.CardNote;
import by.epam.project.bean.Destination;
import by.epam.project.bean.User;
import by.epam.project.bean.dto.BookDTO;
import by.epam.project.exception.ServiceException;

import java.util.List;

public interface LibService {
    CardNote saveCardNote(long userId, long bookId, Destination destination) throws ServiceException;

    boolean deleteCardNote(long cardsNoteId) throws ServiceException;

    List<CardNote> takeNotesByUser(User user) throws ServiceException;

    boolean updateNoteActivity(long cardNoteId) throws ServiceException;

    Book saveBook(BookDTO libDTO) throws ServiceException;

    Book takeBookById(long id) throws ServiceException;

    List<Book> takeBookByPartOfTitle(String title) throws ServiceException;

    List<Book> takeAllBooks() throws ServiceException;

    boolean deleteBook(long bookId) throws ServiceException;

}