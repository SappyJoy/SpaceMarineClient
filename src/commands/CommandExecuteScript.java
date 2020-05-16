package commands;

import item.SpaceMarine;

import java.io.*;
import java.util.*;

/**
 * SpaceMarine.Command to execute commands from a file
 */
public class CommandExecuteScript extends Command {

    Map<Integer, SpaceMarine> lhm;
    String fileName;
    CommandManager commandManager;
    Set<String> executedFiles;

    public CommandExecuteScript(Map<Integer, SpaceMarine> lhm, CommandManager commandManager) {
        this();
        this.lhm = lhm;
        this.commandManager = commandManager;
    }

    public CommandExecuteScript() {
        this.name = "execute_script";
        this.description = "Reads and executes the script from the specified file" +
                " The script contains commands in " +
                "the same form in which they are entered by the user interactively.";
    }

    @Override
    public String execute() {
        String result;

        List<String> history = new LinkedList<>();
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(fileName);
        } catch (FileNotFoundException e) {
            return ("File not found");
        }
        Scanner in = new Scanner(new InputStreamReader(fileInputStream));
        // Создать новый файл, если имя файла указано некорректно запросить повторить ввод
        while (in.hasNextLine()) {
            // !!!!! Если присутствует комманда execute_script, проверить не ссылается ли она на тот же файл
            String name = "";
            while (in.hasNextLine()) {
                try {
                    name = in.next();
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Wrong input");
                } catch (NoSuchElementException e) {
                    continue;
                }
            }
            Command cmd = commandManager.getCommand(name);

            if (name.equals("execute_script")) {
                if (executedFiles.contains(fileName)) {
                    break;
                } else {
                    executedFiles.add(fileName);
                    CommandExecuteScript commandExecuteScript = (CommandExecuteScript)commandManager.getCommand("execute_script");
                    commandExecuteScript.setExecutedFiles(executedFiles);
                    cmd = commandExecuteScript;
                }
            }
            try {
                byte[] output = new byte[10000];
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
                cmd.readParameters(in, objectOutputStream);
                objectOutputStream.flush();
                output = byteArrayOutputStream.toByteArray();

                ByteArrayInputStream bais = new ByteArrayInputStream(output);
                ObjectInputStream ois = new ObjectInputStream(bais);
                cmd.setParameters(ois);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

            cmd.execute();
            history.add(name);
        }

        return "";
    }

    @Override
    public void readParameters(Scanner sc, ObjectOutputStream oos) throws IOException {
        oos.writeObject(sc.next());
    }

    @Override
    public void setParameters(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        fileName = (String) ois.readObject();
    }

    public void setExecutedFiles(Set<String> executedFiles) {
        this.executedFiles = executedFiles;
    }

    private static String makePath(String fileName) {
        return  fileName + "";
    }
}
