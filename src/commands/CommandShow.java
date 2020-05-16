package commands;

import item.SpaceMarine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Scanner;

/**
 * When calling this command, displays the entire collection in the console
 */
public class CommandShow extends Command {
    private Map<Integer, SpaceMarine> lhm;
    public CommandShow(Map<Integer, SpaceMarine> lhm) {
        this();
        this.lhm = lhm;
    }

    public CommandShow() {
        this.name = "show";
        this.description = "Outputs to the standard output stream all the elements of the collection in a string representation";
    }

    @Override
    public String execute() {
        StringBuilder result = new StringBuilder();
        for (int i : lhm.keySet()) {
            result.append("{\"key\":" + i + ", \"value\":{" + lhm.get(i) + "}\n");
        }
        return result.toString();
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {

    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {

    }
}
