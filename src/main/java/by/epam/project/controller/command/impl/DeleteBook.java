package by.epam.project.controller.command.impl;

import by.epam.project.bean.User;
import by.epam.project.controller.util.JSPPageName;
import by.epam.project.controller.util.ParameterName;
import by.epam.project.controller.command.Command;
import by.epam.project.exception.ServiceException;
import by.epam.project.service.LibService;
import by.epam.project.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteBook implements Command {
    private static Logger logger = LogManager.getLogger(DeleteBook.class);
    private static final String PART_OF_URL = "/Controller?command=take_books";

    /**
     * Метод удаляет книгу.
     *
     * @param req
     * @param resp
     * @throws IOException, ServletException
     */

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LibService libService = ServiceProvider.getInstance().getLibService();

        try {
            User mainUser = (User) req.getSession(true).getAttribute(ParameterName.MAIN_USER);
            long bookId = Long.parseLong(req.getParameter(ParameterName.BOOK_ID));

            if (mainUser != null) {
                libService.deleteBook(bookId);
                resp.sendRedirect(req.getContextPath() + PART_OF_URL);
            } else {
                RequestDispatcher dispatcher = req.getRequestDispatcher(JSPPageName.INDEX_PAGE);
                dispatcher.forward(req, resp);
            }
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }
}
