
package by.epam.project.controller.command.impl;

import by.epam.project.bean.dto.AccessDTO;
import by.epam.project.controller.util.JSPPageName;
import by.epam.project.controller.util.ParameterName;
import by.epam.project.exception.ServiceException;
import by.epam.project.service.UserService;
import by.epam.project.service.ServiceProvider;
import by.epam.project.bean.User;
import by.epam.project.controller.command.Command;
import by.epam.project.service.util.DTOConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

public class Authorisation implements Command {
    private static Logger logger = LogManager.getLogger(AddBook.class);
    private static final String PART_OF_URL = "/Controller?command=go_to_profile";
    private static final String BASE_NAME = "localization.local";
    private static final String BUNDLE_KEY = "local.message.authorisation_message";

    /**
     * Метод авторизирует пользователя.
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
        AccessDTO accessDTO = DTOConverter.getInstance().reqConvertIntoAccessDTO(req);
        UserService userService = ServiceProvider.getInstance().getUserService();
        HttpSession session = req.getSession(true);

        try {
            User mainUser = userService.takeByAccessInfo(accessDTO);
            if (mainUser != null) {
                session.setAttribute(ParameterName.MAIN_USER, mainUser);
                session.setAttribute(ParameterName.EMAIL, accessDTO.getEmail());

                resp.sendRedirect(req.getContextPath() + PART_OF_URL);
            } else {
                String authorisationMessage = (String) bundle.getObject(BUNDLE_KEY);
                session.setAttribute(ParameterName.AUTHORISATION_MESSAGE, authorisationMessage);
                resp.sendRedirect(req.getContextPath() + JSPPageName.INDEX_PAGE);
            }
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }
}