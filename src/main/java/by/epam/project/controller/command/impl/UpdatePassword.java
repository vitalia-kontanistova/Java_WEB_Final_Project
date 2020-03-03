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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class UpdatePassword implements Command {
    private static Logger logger = LogManager.getLogger(UpdatePassword.class);
    private static final String PART_OF_URL = "/Controller?command=go_to_profile";
    private static final String BASE_NAME = "localization.local";
    private static final String BUNDLE_KEY_1 = "local.message.update_pass_message_success";
    private static final String BUNDLE_KEY_2 = "local.message.update_pass_message_fail";

    /**
     * Метод обновляет пароль пользователя.
     *
     * @param req
     * @param resp
     * @throws IOException, ServletException
     */

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        Locale locale;
        if (req.getSession().getAttribute(ParameterName.LOCAL) != null) {
            locale = new Locale(req.getSession().getAttribute(ParameterName.LOCAL).toString());
        } else {
            locale = req.getLocale();
        }

        ResourceBundle bundle = ResourceBundle.getBundle(BASE_NAME, locale);
        UserService userService = ServiceProvider.getInstance().getUserService();

        try {
            HttpSession session = req.getSession(true);
            User mainUser = (User) session.getAttribute(ParameterName.MAIN_USER);
            String newPassword = req.getParameter(ParameterName.PASSWORD);

            String updatePassMessage;

            if (mainUser != null && newPassword != null) {
                boolean success = userService.updatePassword(mainUser, newPassword);
                if (success) {
                    updatePassMessage = (String) bundle.getObject(BUNDLE_KEY_1);
                } else {
                    updatePassMessage = (String) bundle.getObject(BUNDLE_KEY_2);
                }

                session.setAttribute(ParameterName.UPDATE_PASS_MESSAGE, updatePassMessage);
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