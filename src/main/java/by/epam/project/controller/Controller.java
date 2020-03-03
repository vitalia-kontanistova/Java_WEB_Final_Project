package by.epam.project.controller;

import by.epam.project.controller.command.Command;
import by.epam.project.controller.command.CommandProvider;
import by.epam.project.controller.util.ParameterName;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final CommandProvider provider = CommandProvider.getInstance();

    public Controller() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    /**
     * Метод обрабатывает http запрос.
     *
     * @param req
     * @param resp
     * @throws IOException, ServletException
     */

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String commandName = req.getParameter(ParameterName.COMMAND);
        Command command = provider.getCommand(commandName);
        command.execute(req, resp);
    }
}
