package by.epam.project.service.impl;

import by.epam.project.bean.*;
import by.epam.project.service.LibService;
import by.epam.project.bean.dto.BookDTO;
import by.epam.project.dao.DAOProvider;
import by.epam.project.dao.LibDAO;
import by.epam.project.exception.DAOException;
import by.epam.project.exception.ServiceException;
import by.epam.project.service.util.DTOConverter;
import by.epam.project.service.util.Validator;

import java.sql.Date;
import java.util.List;

public class LibServiceImpl implements LibService {
    private static final Validator validator = Validator.getInstance();

    /**
     * Метод сохраняет запись в карточке пользователя в базу данных.
     *
     * @param userId
     * @param bookId
     * @param destination определяет куда выдавалась книга: на карточку, в читальный зал или это предварительный заказ.
     * @return запись из карточки пользователя.
     * @throws ServiceException
     */
    @Override
    public CardNote saveCardNote(long userId, long bookId, Destination destination) throws ServiceException {
        LibDAO libDAO = DAOProvider.getInstance().getLibDAO();

        CardNote cardNote = new CardNote();
        try {
            Date initialDate = new Date((new java.util.Date()).getTime());
            Date finalDate = initialDate;
            switch (destination) {
                case CARD:
                    finalDate = new Date(initialDate.getTime() + 2592000000L);
                    break;
                case ORDER:
                    finalDate = new Date(initialDate.getTime() + 259200000L);
                    break;
            }
            Book book = takeBookById(bookId);

            cardNote.setInitialDate(initialDate);
            cardNote.setFinalDate(finalDate);
            cardNote.setDestination(destination);
            cardNote.setActive(true);
            cardNote.setUserId(userId);
            cardNote.setBook(book);

            cardNote = libDAO.saveCardNote(cardNote);
        } catch (DAOException e) {
            throw new ServiceException("LibServiceImpl: saveCardNote(): " + e.getMessage());
        }
        return cardNote;
    }

    /**
     * Метод удаляет из базы данных запись в карточке пользователя.
     *
     * @param cardNoteId
     * @return <code>true</code> когда операция прошла успешно, <code>false</code> в противном случае
     * @throws ServiceException
     */
    @Override
    public boolean deleteCardNote(long cardNoteId) throws ServiceException {
        LibDAO libDAO = DAOProvider.getInstance().getLibDAO();
        try {
            return libDAO.deleteCardNote(cardNoteId);
        } catch (DAOException e) {
            throw new ServiceException("LibServiceImpl: deleteCardNote(): " + e.getMessage());
        }
    }

    /**
     * Метод возвращает из базы данных все записи в карточке указанного пользователя.
     *
     * @param user
     * @return все записи из карточки пользователя.
     * @throws ServiceException
     */
    @Override
    public List<CardNote> takeNotesByUser(User user) throws ServiceException {
        LibDAO libDAO = DAOProvider.getInstance().getLibDAO();
        List<CardNote> cardNotes;
        try {
            cardNotes = libDAO.takeNotesByUser(user);
        } catch (DAOException e) {
            throw new ServiceException("LibServiceImpl: takeNotesByUser(): " + e.getMessage());
        }
        return cardNotes;
    }

    /**
     * Метод сохраняет книгу в базу данных из объекта <code>BookDTO</code>.
     *
     * @param bookDTO
     * @return книга из базы данных со всеми её авторами.
     * @throws ServiceException
     */
    @Override
    public Book saveBook(BookDTO bookDTO) throws ServiceException {
        LibDAO libDAO = DAOProvider.getInstance().getLibDAO();
        Book book = null;

        try {
            if (validator.bookCheck(bookDTO)) {
                book = DTOConverter.getInstance().dtoConvertIntoBook(bookDTO);
                book = libDAO.saveBook(book);
            }
        } catch (DAOException e) {
            throw new ServiceException("LibServiceImpl: saveBook(): " + e.getMessage());
        }
        return book;
    }

    /**
     * Метод возвращает книгу из базы данных по идентификационному номеру.
     *
     * @param bookId
     * @return книга из базы данных со всеми её авторами.
     * @throws ServiceException
     */
    @Override
    public Book takeBookById(long bookId) throws ServiceException {
        LibDAO libDAO = DAOProvider.getInstance().getLibDAO();
        Book book;
        try {
            book = libDAO.takeBookById(bookId);
        } catch (DAOException e) {
            throw new ServiceException("LibServiceImpl: takeBookById(): " + e.getMessage());
        }
        return book;
    }

    /**
     * Метод возвращает книги из базы данных по части насзвания.
     *
     * @param title
     * @return список книг из базы данных в названии которых встречается передаваемая строка.
     * @throws ServiceException
     */
    @Override
    public List<Book> takeBookByPartOfTitle(String title) throws ServiceException {
        LibDAO libDAO = DAOProvider.getInstance().getLibDAO();
        List<Book> books;
        try {
            books = libDAO.takeBookByPartOfTitle("%" + title + "%");
        } catch (DAOException e) {
            throw new ServiceException("LibServiceImpl: takeBookByPartOfTitle(): " + e.getMessage());
        }
        return books;
    }

    /**
     * Метод делает запись в карточке неактивной.
     *
     * @param cardNoteId
     * @return <code>true</code> когда операция прошла успешно, <code>false</code> в противном случае
     * @throws ServiceException
     */
    @Override
    public boolean updateNoteActivity(long cardNoteId) throws ServiceException {
        LibDAO libDAO = DAOProvider.getInstance().getLibDAO();
        boolean success;
        try {
            success = libDAO.updateNoteActivity(cardNoteId);
        } catch (DAOException e) {
            throw new ServiceException("LibServiceImpl: updateNoteActivity(): " + e.getMessage());
        }
        return success;
    }

    /**
     * Метод возвращает все книги из базы данных.
     *
     * @return список книг из базы данных.
     * @throws ServiceException
     */
    @Override
    public List<Book> takeAllBooks() throws ServiceException {
        LibDAO libDAO = DAOProvider.getInstance().getLibDAO();
        List<Book> books;
        try {
            books = libDAO.takeAllBooks();
        } catch (DAOException e) {
            throw new ServiceException("LibServiceImpl: takeAllBooks(): " + e.getMessage());
        }
        return books;
    }

    /**
     * Метод удаляет из базы данных однин экземпляр книги.
     *
     * @param bookId
     * @return <code>true</code> когда операция прошла успешно, <code>false</code> в противном случае
     * @throws ServiceException
     */
    @Override
    public boolean deleteBook(long bookId) throws ServiceException {
        LibDAO libDAO = DAOProvider.getInstance().getLibDAO();
        boolean success;
        try {
            success = libDAO.deleteBook(bookId);
        } catch (DAOException e) {
            throw new ServiceException("LibServiceImpl: deleteBook(): " + e.getMessage());
        }
        return success;
    }
}