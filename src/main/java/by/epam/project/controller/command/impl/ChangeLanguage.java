package by.epam.project.controller.command.impl;

import by.epam.project.controller.command.Command;
import by.epam.project.controller.util.ParameterName;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class ChangeLanguage implements Command {

    /**
     * Метод меняет язык пользовательсткого интерфейса с последующем обновлением текущей страницы.
     *
     * @param req
     * @param resp
     * @throws IOException, ServletException
     */

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String local = req.getParameter(ParameterName.LOCAL);
        req.getSession().setAttribute(ParameterName.LOCAL, local);
        resp.sendRedirect(req.getParameter(ParameterName.PATH));

    }
}
