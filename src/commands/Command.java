package commands;

import user.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Abstract class for executable commands
 */
public abstract class Command implements Executable, Serializable {
    String name;
    String description;
    User user;
    ReadWriteLock lock = new ReentrantReadWriteLock();
    public abstract String execute();
    public void setUser(User user) {
        this.user = user;
    }
    public abstract void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException;
    public abstract void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException;
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
}
