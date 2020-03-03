package by.epam.project.controller.command.impl;

import by.epam.project.bean.User;
import by.epam.project.controller.util.JSPPageName;
import by.epam.project.controller.util.ParameterName;
import by.epam.project.controller.command.Command;
import by.epam.project.exception.ServiceException;
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

public class TakeUsers implements Command {
    private static Logger logger = LogManager.getLogger(TakeUsers.class);

    /**
     * Метод направляет на страницу каталога пользователей.
     *
     * @param req
     * @param resp
     * @throws IOException, ServletException
     */

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UserService userService = ServiceProvider.getInstance().getUserService();

        try {
            User mainUser = (User) req.getSession().getAttribute(ParameterName.MAIN_USER);
            String page = JSPPageName.INDEX_PAGE;

            List<User> users = userService.takeAllUsers();
            if (users != null && mainUser != null) {
                req.setAttribute(ParameterName.USERS, users);
                page = JSPPageName.USER_LIST_PAGE;
            }

            RequestDispatcher dispatcher = req.getRequestDispatcher(page);
            dispatcher.forward(req, resp);
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }
}
