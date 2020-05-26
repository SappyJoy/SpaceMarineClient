package commands;

import item.SpaceMarine;
import utils.ValidateInput;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Scanner;

/**
 * SpaceMarine.Command which removes an item from the collection by its key
 */
public class CommandRemoveKey extends Command {
    /**
     * Removes an item from the collection by its key
     */
    private Map<Integer, SpaceMarine> lhm;
    private int removeKey;

    public CommandRemoveKey(Map<Integer, SpaceMarine> lhm) {
        this();
        this.lhm = lhm;
    }

    public CommandRemoveKey() {
        this.name = "remove_key";
        this.description = "Removes an item from the collection by its key";
    }

    @Override
    public String execute() {
        for (Integer key : lhm.keySet()) {
            if (key.equals(removeKey)) {
                lhm.remove(removeKey);
                return ("Element has been removed\n");
            }
        }
        return String.format("Key %d not found\n", removeKey);
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {
        oos.writeObject(new ValidateInput(sc).validateInt());
    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        removeKey = (Integer) ois.readObject();
    }
}
