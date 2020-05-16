package commands;

import java.io.Serializable;
import java.util.HashMap;

/**
 * SpaceMarine.CommandManager executes a command corresponding to the input
 */
public class CommandManager implements Serializable {

    HashMap<String, Command> hm = new HashMap<>();

    public void addCommand(String name, Command cmd) {
        hm.put(name, cmd);
    }

    public Command getCommand(String name) {
        return hm.get(name);
    }

    public HashMap<String, Command> getHm() {
        return hm;
    }
}
