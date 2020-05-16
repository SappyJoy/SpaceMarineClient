package commands;

import item.SpaceMarine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Scanner;

import static java.lang.String.format;

/**
 * Shows info about collection
 */
public class CommandInfo extends Command {

    Map<Integer, SpaceMarine> lhm;

    public CommandInfo(Map<Integer, SpaceMarine> lhm) {
        this();
        this.lhm = lhm;
    }

    public CommandInfo() {
        this.name = "info";
        this.description = "Display information about your collection";
    }

    @Override
    public String execute() {

        String typeOfCollection = lhm.getClass().getSimpleName();
        java.time.LocalDateTime creationDate = LocalDateTime.MAX;
        for (SpaceMarine spaceMarine : lhm.values()) {
            if (creationDate.compareTo(spaceMarine.getCreationDate()) > 0)
                creationDate = spaceMarine.getCreationDate();
        }
        int sizeOfCollection = lhm.size();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
        String formattedCreationDate = creationDate.format(formatter);

        String result = String.format("Type of collection: %s\nInitialization date: %s\nSize of collection: %d\n",
                typeOfCollection, formattedCreationDate, sizeOfCollection);
        return result;
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {

    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {

    }
}
