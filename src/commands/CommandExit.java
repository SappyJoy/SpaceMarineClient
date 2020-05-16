package commands;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * Complete the entire program
 */
public class CommandExit extends Command {

    public CommandExit() {
        this.name = "exit";
        this.description = "Terminate the program (without saving to a file)";
    }

    public String execute() {
        System.exit(0);
        return "";
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {

    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {

    }
}
