package by.epam.project.controller.command.impl;

import by.epam.project.bean.dto.AccessDTO;
import by.epam.project.controller.util.JSPPageName;
import by.epam.project.controller.util.ParameterName;
import by.epam.project.exception.ServiceException;
import by.epam.project.service.UserService;
import by.epam.project.service.ServiceProvider;
import by.epam.project.bean.User;
import by.epam.project.bean.dto.UserDTO;
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

public class Registration implements Command {
    private static Logger logger = LogManager.getLogger(Registration.class);
    private static final String PART_OF_URL = "/Controller?command=go_to_profile";
    private static final String BASE_NAME = "localization.local";
    private static final String BUNDLE_KEY = "local.message.registration_message";

    /**
     * Метод регистрирует ного пользователя.
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
        UserDTO userDTO = DTOConverter.getInstance().reqConvertIntoUserDTO(req);
        AccessDTO accessDTO = DTOConverter.getInstance().reqConvertIntoAccessDTO(req);

        try {
            User mainUser = null;
            HttpSession session = req.getSession(true);

            if (userService.takeByPhoneEmail(userDTO.getPhone(), accessDTO.getEmail()) == null) {
                mainUser = userService.saveUser(userDTO, accessDTO);
            } else {
                String registrationMessage = (String) bundle.getObject(BUNDLE_KEY);
                session.setAttribute(ParameterName.REGISTRATION_MESSAGE, registrationMessage);
            }

            if (mainUser != null) {
                session.setAttribute(ParameterName.MAIN_USER, mainUser);
                session.setAttribute(ParameterName.EMAIL, accessDTO.getEmail());
                resp.sendRedirect(req.getContextPath() + PART_OF_URL);
            } else {
                resp.sendRedirect(req.getContextPath() + JSPPageName.REGISTRATION_PAGE);
            }
        } catch (ServiceException e) {
            logger.error(e.getMessage());
        }
    }
}