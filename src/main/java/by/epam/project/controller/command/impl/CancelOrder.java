package by.epam.project.controller.command.impl;

import by.epam.project.service.LibService;
import by.epam.project.bean.User;
import by.epam.project.controller.util.JSPPageName;
import by.epam.project.controller.util.ParameterName;
import by.epam.project.controller.command.Command;
import by.epam.project.service.UserService;
import by.epam.project.exception.ServiceException;
import by.epam.project.service.ServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CancelOrder implements Command {
    private static Logger logger = LogManager.getLogger(CancelOrder.class);
    private static final String PART_OF_URL = "/Controller?command=take_user_card&user_id=";

    /**
     * Метод отменяет заказ книги.
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
            long cardNoteId = Long.parseLong(req.getParameter(ParameterName.CARD_NOTE_ID));
            long userId = Long.parseLong(req.getParameter(ParameterName.USER_ID));

            User mainUser = (User) req.getSession().getAttribute(ParameterName.MAIN_USER);
            User user = userService.takeById(userId);

            if (user != null) {
                req.setAttribute(ParameterName.USER, user);
                libService.updateNoteActivity(cardNoteId);

                if (mainUser != null) {
                    resp.sendRedirect(req.getContextPath() + PART_OF_URL + user.getId());
                } else {
                    RequestDispatcher dispatcher = req.getRequestDispatcher(JSPPageName.INDEX_PAGE);
                    dispatcher.forward(req, resp);
                }
            }
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }
}
