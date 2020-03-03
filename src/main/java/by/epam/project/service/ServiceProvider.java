package by.epam.project.service;

import by.epam.project.service.impl.LibServiceImpl;
import by.epam.project.service.impl.UserServiceImpl;

public class ServiceProvider {
    private static final ServiceProvider instance = new ServiceProvider();
    private final UserService userService = new UserServiceImpl();
    private final LibService libService = new LibServiceImpl();

    private ServiceProvider() {
    }

    public static ServiceProvider getInstance() {
        return instance;
    }

    public UserService getUserService() {
        return userService;
    }

    public LibService getLibService() {
        return libService;
    }
}
