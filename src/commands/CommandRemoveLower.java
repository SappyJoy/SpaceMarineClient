package commands;

import item.SpaceMarine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * SpaceMarine.Command which removes from the collection all elements smaller than the specified
 */
public class CommandRemoveLower extends Command {

    Map<Integer, SpaceMarine> lhm;
    private SpaceMarine spaceMarine;

    public CommandRemoveLower(Map<Integer, SpaceMarine> lhm) {
        this();
        this.lhm = lhm;
    }

    public CommandRemoveLower() {
        this.name = "remove_lower";
        this.description = "Removes from the collection all elements smaller than the specified";
    }

    @Override
    public String execute() {
        boolean exists = false;
        for (SpaceMarine sm : lhm.values()) {
            if (sm.equals(spaceMarine))
                exists = true;
        }
        if (exists) {
            Map<Integer, SpaceMarine> copyLhm = new LinkedHashMap<>();
            for (Map.Entry<Integer, SpaceMarine> entry : lhm.entrySet()) {
                int key = entry.getKey();
                SpaceMarine value = entry.getValue();
                if (value.compareTo(spaceMarine) >= 0) {
                    copyLhm.put(key, value);
                }
            }
            lhm.clear();
            lhm.putAll(copyLhm);
        }
        return "";
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {
        SpaceMarine spaceMarine = new SpaceMarine();
        spaceMarine.scan(sc);
        oos.writeObject(spaceMarine);
    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        spaceMarine = (SpaceMarine) ois.readObject();
    }
}
