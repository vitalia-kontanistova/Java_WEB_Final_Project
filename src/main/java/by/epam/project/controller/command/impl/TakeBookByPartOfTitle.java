package by.epam.project.controller.command.impl;

import by.epam.project.bean.Book;
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
import java.util.List;

public class TakeBookByPartOfTitle implements Command {
    private static Logger logger = LogManager.getLogger(TakeBookByPartOfTitle.class);

    /**
     * Метод направляет на страницу с результатами поиска книг по части названия.
     *
     * @param req
     * @param resp
     * @throws IOException, ServletException
     */

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LibService libService = ServiceProvider.getInstance().getLibService();

        try {
            User mainUser = (User) req.getSession().getAttribute(ParameterName.MAIN_USER);
            String title = req.getParameter(ParameterName.BOOK_PART_OF_TITLE);
            String page = JSPPageName.INDEX_PAGE;

            List<Book> books;
            if (!title.isEmpty()) {
                books = libService.takeBookByPartOfTitle(title);
            } else {
               books = libService.takeAllBooks();
            }

            if (books != null && mainUser != null) {
                req.setAttribute(ParameterName.BOOKS, books);
                page = JSPPageName.BOOK_CATALOG_PAGE;
            }

            RequestDispatcher dispatcher = req.getRequestDispatcher(page);
            dispatcher.forward(req, resp);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }
}
