package commands;

import item.SpaceMarine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Comparator;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        if (lhm.size() == 0) {
            return "Collection is empty";
        }
        String s = lhm.values().stream()
                .sorted(Comparator.comparing(SpaceMarine::getName))
                .map(a -> a.toString() + "\n")
                .collect(Collectors.joining());
        return s;
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {

    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {

    }
}
