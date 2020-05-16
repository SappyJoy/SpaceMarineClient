package commands;

import item.Chapter;
import item.SpaceMarine;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

/**
 * SpaceMarine.Command that prints the values of the chapter field of all elements in ascending order
 */
public class CommandPrintFieldAscendingChapter extends Command {

    Map<Integer, SpaceMarine> lhm;

    public CommandPrintFieldAscendingChapter(Map<Integer, SpaceMarine> lhm) {
        this();
        this.lhm = lhm;
    }

    public CommandPrintFieldAscendingChapter() {
        this.name = "print_field_ascending_chapter";
        this.description = "Print the values of the chapter field of all elements in ascending order";
    }

    @Override
    public String execute() {
        StringBuilder result = new StringBuilder();
        ArrayList<SpaceMarine> sortByChapter = new ArrayList<>(lhm.values());
        Comparator<SpaceMarine> comparator = new Comparator<SpaceMarine>() {
            @Override
            public int compare(SpaceMarine spaceMarine, SpaceMarine t1) {
                Chapter chapter1 = spaceMarine.getChapter();
                Chapter chapter2 = t1.getChapter();
                return chapter1.compareTo(chapter2);
            }
        };
        sortByChapter.sort(comparator);
        for (SpaceMarine sm : sortByChapter) {
            result.append(sm.getChapter() + "\n");
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
