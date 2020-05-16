import client.Client;
import commands.*;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.*;

/**
 *  Class that runs the program. It's responsible for reading commands
 * @author Stepan Ponomaryov
 * @version 1.0 2020-03-12
 */
public class Main {
    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Syntax: java Main <hostname> <port-number>");
            return;
        }

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        Scanner sc = new Scanner(System.in);
        CommandManager commandManager = new CommandManager();
        addCommands(commandManager);
        System.out.println("For help, type \"help\"");

        Client client = new Client(hostname, port, sc, commandManager);
        client.run();
    }

    /**
     * Главный цикл, запускающий клиент, который подключается к серверу.
     * Далее клиент создаёт объект комманды нужного типа, сериализует его,
     * далее устанавливает нужные параметры в комманду и отправляет на сервер.
     * Далее следует приём строки и вывод её в консоль.
     * @param sc
     * @param commandManager
     * @throws IOException
     */
    private static void run(Scanner sc, CommandManager commandManager) throws IOException {
        DatagramSocket ds = new DatagramSocket();
        ds.setSoTimeout(10000);
        while(sc.hasNextLine()) {
            byte[] ib = new byte[10000];
            byte[] ob = new byte[10000];
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);

            String name = sc.next();
            Command cmd = commandManager.getCommand(name);
            if (cmd == null) {
                System.out.println("Command not found");
                continue;
            }

            if (cmd.getName().equals("exit")) {
                cmd.execute();
            }

            oos.writeObject(cmd);
            cmd.readParameters(sc, oos);
            oos.flush();

            ob = baos.toByteArray();

            DatagramPacket op = new DatagramPacket(ob, ob.length, InetAddress.getByName("localhost"), 7777);

            ds.send(op);

            DatagramPacket ip = new DatagramPacket(ib, ib.length);
            try {
                ds.receive(ip);
            } catch (SocketTimeoutException e) {
                System.out.println("Timeout");
                break;
            }

            System.out.println(new String(ib).trim());
        }
    }


    private static void addCommands(CommandManager commandManager) {
        commandManager.addCommand("insert", new CommandInsert());
        commandManager.addCommand("show", new CommandShow());
        commandManager.addCommand("update", new CommandUpdate());
        commandManager.addCommand("remove_key", new CommandRemoveKey());
        commandManager.addCommand("clear", new CommandClear());
        commandManager.addCommand("exit", new CommandExit());
        commandManager.addCommand("remove_greater", new CommandRemoveGreater());
        commandManager.addCommand("remove_lower", new CommandRemoveLower());
        commandManager.addCommand("history", new CommandHistory());
        commandManager.addCommand("count_by_weapon_type", new CommandCountByWeaponType());
        commandManager.addCommand("filter_less_than_health", new CommandFilterLessThanHealth());
        commandManager.addCommand("print_field_ascending_chapter", new CommandPrintFieldAscendingChapter());
        commandManager.addCommand("info", new CommandInfo());
        commandManager.addCommand("help", new CommandHelp(commandManager));
        commandManager.addCommand("execute_script", new CommandExecuteScript());
    }


    private static File checkFile(String[] args) {
        if (args.length <= 0) {
            System.out.println("You should enter name of file in main arguments");
            System.exit(0);
        }
        File file = new File(args[0]);
        if (!file.exists()) {
            System.out.println("There is no such file");
            System.exit(0);
        }
        if (!file.canWrite() || !file.canRead()) {
            System.out.println("You have not got enough permissions to this file");
            System.exit(0);
        }
        return file;
    }
}