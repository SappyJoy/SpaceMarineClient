package commands;

import item.SpaceMarine;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;
import java.util.Scanner;

/**
 * SpaceMarine.Command which clear all collection
 */
public class CommandClear extends Command {
    private Map<Integer, SpaceMarine> lhm;
    public CommandClear(Map<Integer, SpaceMarine> lhm) {
        this();
        this.lhm = lhm;
    }

    public CommandClear() {
        this.name = "clear";
        this.description = "Clear collection";
    }

    @Override
    public String execute() {
        lhm.clear();
        return "";
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) {

    }

    @Override
    public void setParameters(ObjectInputStream ois) {

    }
}
