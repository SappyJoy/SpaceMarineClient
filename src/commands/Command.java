package commands;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Abstract class for executable commands
 */
public abstract class Command implements Executable, Serializable {
    String name;
    String description;
    public abstract String execute();
    public abstract void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException;
    public abstract void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException;
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
}
