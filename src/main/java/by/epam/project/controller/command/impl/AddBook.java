package by.epam.project.controller.command.impl;

import by.epam.project.bean.Book;
import by.epam.project.bean.User;
import by.epam.project.bean.dto.BookDTO;
import by.epam.project.controller.util.JSPPageName;
import by.epam.project.controller.util.ParameterName;
import by.epam.project.controller.command.Command;
import by.epam.project.exception.ServiceException;
import by.epam.project.service.LibService;
import by.epam.project.service.ServiceProvider;
import by.epam.project.service.util.DTOConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddBook implements Command {
    private static Logger logger = LogManager.getLogger(AddBook.class);
    private static final String PART_OF_URL = "/Controller?command=take_book&book_id=";

    /**
     * Метод добваляет книгу.
     *
     * @param req
     * @param resp
     * @throws IOException, ServletException
     */

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LibService libService = ServiceProvider.getInstance().getLibService();
        BookDTO bookDTO = DTOConverter.getInstance().reqConvertIntoBookDTO(req);

        try {
            User mainUser = (User) req.getSession().getAttribute(ParameterName.MAIN_USER);
            Book book = libService.saveBook(bookDTO);

            if (mainUser != null && book != null) {
                resp.sendRedirect(req.getContextPath() + PART_OF_URL + book.getId());
            } else {
                RequestDispatcher dispatcher = req.getRequestDispatcher(JSPPageName.INDEX_PAGE);
                dispatcher.forward(req, resp);
            }
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }
}
