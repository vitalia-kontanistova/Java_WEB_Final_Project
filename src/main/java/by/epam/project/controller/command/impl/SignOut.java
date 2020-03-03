package by.epam.project.controller.command.impl;

import by.epam.project.controller.util.JSPPageName;
import by.epam.project.controller.command.Command;
import by.epam.project.controller.util.ParameterName;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SignOut implements Command {
    /**
     * Метод реализует выход пользователя из приложения.
     *
     * @param req
     * @param resp
     * @throws IOException, ServletException
     */

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        session.setAttribute(ParameterName.MAIN_USER, null);
        session.invalidate();

        RequestDispatcher dispatcher = req.getRequestDispatcher(JSPPageName.INDEX_PAGE);
        dispatcher.forward(req, resp);
    }
}
