package by.epam.project.controller.command.impl;

import by.epam.project.controller.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WrongRequest implements Command {

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse response) {
        System.out.println("WRONG_REQUEST");
    }
}