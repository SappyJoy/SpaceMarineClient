package utils;

import client.Client;
import gui.SpaceMarineAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AnswerHandler {

    public static List<SpaceMarineAdapter> show() {
        List<SpaceMarineAdapter> list = new ArrayList<>();

        String answer = Client.getInstance().get("show");
        StringBuilder smStringBuilder = new StringBuilder();
        Scanner scanner = new Scanner(answer);
        int i = 0;
        while (scanner.hasNextLine()) {
            smStringBuilder.append(scanner.nextLine() + "\n");
            i++;
            if (i == 19) {
                String smString = "{" + smStringBuilder.toString() + "}";
                list.add(new SpaceMarineAdapter(WorkWithJson.getSpaceMarine(smString)));
                smStringBuilder.setLength(0);
                i = 0;
            }
        }

        return list;
    }
}
