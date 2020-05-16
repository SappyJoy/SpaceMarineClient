package commands;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * SpaceMarine.Command that shows information about every command that user can use
 */
public class CommandHelp extends Command {

    CommandManager commandManager;

    public CommandHelp(CommandManager commandManager) {
        this();
        this.commandManager = commandManager;
    }

    public CommandHelp() {
        this.name = "help";
        this.description = "Shows info about every command";
    }

    @Override
    public String execute() {
        StringBuffer result = new StringBuffer();
        result.append("The options are:\n");
        for (Command cmd : commandManager.getHm().values()) {
            String name = cmd.getName();
            String description = cmd.getDescription();
            result.append(String.format("%-40s%-150s\n", name, description));
        }
        return result.toString();
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {
        oos.writeObject(commandManager);
    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        commandManager = (CommandManager) ois.readObject();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
