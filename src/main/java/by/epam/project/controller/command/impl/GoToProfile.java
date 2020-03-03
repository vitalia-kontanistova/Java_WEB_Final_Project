package by.epam.project.controller.command.impl;

import by.epam.project.bean.User;
import by.epam.project.controller.util.JSPPageName;
import by.epam.project.controller.util.ParameterName;
import by.epam.project.controller.command.Command;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToProfile implements Command {
    /**
     * Метод направляет на профиль пользователя.
     *
     * @param req
     * @param resp
     * @throws IOException, ServletException
     */

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        User mainUser = (User) req.getSession().getAttribute(ParameterName.MAIN_USER);
        String page = JSPPageName.INDEX_PAGE;

        if (mainUser != null) {
//            req.setAttribute(ParameterName.USER, mainUser);
            page = JSPPageName.USER_PROFILE_PAGE;
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher(page);
        dispatcher.forward(req, resp);
    }
}
