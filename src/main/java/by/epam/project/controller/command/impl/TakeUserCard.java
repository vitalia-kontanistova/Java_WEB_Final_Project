package by.epam.project.controller.command.impl;

import by.epam.project.bean.CardNote;
import by.epam.project.bean.Role;
import by.epam.project.bean.User;
import by.epam.project.controller.util.JSPPageName;
import by.epam.project.controller.util.ParameterName;
import by.epam.project.controller.command.Command;
import by.epam.project.exception.ServiceException;
import by.epam.project.service.LibService;
import by.epam.project.service.UserService;
import by.epam.project.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class TakeUserCard implements Command {
    private static Logger logger = LogManager.getLogger(TakeUserCard.class);

    /**
     * Метод направляет на страницу картоки пользоваьеля.
     *
     * @param req
     * @param resp
     * @throws IOException, ServletException
     */

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        LibService libService = ServiceProvider.getInstance().getLibService();
        UserService userService = ServiceProvider.getInstance().getUserService();

        try {
            User mainUser = (User) req.getSession().getAttribute(ParameterName.MAIN_USER);
            User user = mainUser;
            String page = JSPPageName.INDEX_PAGE;

            if (mainUser != null) {
                if (mainUser.getRole() != Role.READER) {
                    long userId = Long.parseLong(req.getParameter(ParameterName.USER_ID));
                    user = userService.takeById(userId);
                }

                if (user != null) {
                    req.setAttribute(ParameterName.USER, user);
                    List<CardNote> cardNotes = libService.takeNotesByUser(user);
                    if (cardNotes != null) {
                        req.setAttribute(ParameterName.CARD_NOTES, cardNotes);
                    }
                    page = JSPPageName.READER_CARD_PAGE;
                }
            }

            RequestDispatcher dispatcher = req.getRequestDispatcher(page);
            dispatcher.forward(req, resp);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }
}