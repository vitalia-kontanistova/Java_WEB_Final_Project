package by.epam.project.controller.command;

import by.epam.project.controller.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public final class CommandProvider {
    private final static CommandProvider instance = new CommandProvider();

    public static CommandProvider getInstance() {
        return instance;
    }

    private final Map<CommandName, Command> commands = new HashMap<>();

    private CommandProvider() {
        commands.put(CommandName.AUTHORISATION, new Authorisation());
        commands.put(CommandName.SIGN_OUT, new SignOut());
        commands.put(CommandName.REGISTRATION, new Registration());
        commands.put(CommandName.TAKE_USERS, new TakeUsers());
        commands.put(CommandName.UPDATE_PASSWORD, new UpdatePassword());

        commands.put(CommandName.ADD_BOOK, new AddBook());
        commands.put(CommandName.TAKE_BOOK, new TakeBook());
        commands.put(CommandName.TAKE_BOOKS, new TakeBooks());
        commands.put(CommandName.DELETE_BOOK, new DeleteBook());

        commands.put(CommandName.TAKE_USER_CARD, new TakeUserCard());
        commands.put(CommandName.ADD_CARD_NOTE, new AddCardNote());
        commands.put(CommandName.DELETE_CARD_NOTE, new DeleteCardNote());

        commands.put(CommandName.GO_TO_PROFILE, new GoToProfile());
        commands.put(CommandName.TAKE_BOOK_BY_PART_OF_TITLE, new TakeBookByPartOfTitle());
        commands.put(CommandName.CANCEL_ORDER, new CancelOrder());
        commands.put(CommandName.WRONG_REQUEST, new WrongRequest());

        commands.put(CommandName.CHANGE_LANGUAGE, new ChangeLanguage());
    }

    public Command getCommand(String name) {
        Command command;
        try {
            CommandName commandName = CommandName.valueOf(name.toUpperCase().replace("-", "_"));
            command = commands.get(commandName);
        } catch (IllegalArgumentException e) {
            command = commands.get(CommandName.WRONG_REQUEST);
        }
        return command;
    }
}