package commands;

import item.SpaceMarine;
import utils.ValidateInput;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Scanner;

/**
 * SpaceMarine.Command that displays elements whose health field value is less than the specified
 */
public class CommandFilterLessThanHealth extends Command {

    Map<Integer, SpaceMarine> lhm;
    private float health;

    public CommandFilterLessThanHealth(Map<Integer, SpaceMarine> lhm) {
        this();
        this.lhm = lhm;
    }

    public CommandFilterLessThanHealth() {
        this.name = "filter_less_than_health";
        this.description = "Displays elements whose health field value is less than the specified";
    }

    @Override
    public String execute() {
        StringBuffer sb = new StringBuffer();
        for (SpaceMarine spaceMarine : lhm.values()) {
            if (spaceMarine.getHealth() < health) {
                sb.append(spaceMarine);
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {
        oos.writeObject(new ValidateInput(sc).validateFloat());
    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        health = (Float) ois.readObject();
    }
}
