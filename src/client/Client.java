package client;

import commands.Command;
import commands.CommandManager;
import user.GlobalUser;
import user.User;
import user.UserCreator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Date;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client {
    private static volatile Client instance;
    private InetSocketAddress inetSocketAddress;
    private ByteBuffer buffer;
    private DatagramChannel channel;
    private Scanner sc;
    private CommandManager commandManager;
    private Date date;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private Client(String host, int port, Scanner sc, CommandManager commandManager) throws IOException {
        this.sc = sc;
        this.commandManager = commandManager;
        inetSocketAddress = new InetSocketAddress(InetAddress.getByName(host), port);
        buffer = ByteBuffer.allocate(500000);
        channel = DatagramChannel.open();
        channel.connect(inetSocketAddress);
        date = new Date();
        user = GlobalUser.getUser();
    }

    public static void init(String host, int port, Scanner sc, CommandManager commandManager) throws IOException {
        instance = new Client(host, port, sc, commandManager);
    }

    public synchronized static Client getInstance() {
        if (instance == null) {
            throw new ExceptionInInitializerError();
        }
        return instance;
    }

    public void run() {
        String commandName;
        // try to register or log in
        UserCreator.setUserCreator(sc, channel, buffer, inetSocketAddress);

//        System.out.println("For help, type \"help\"");
        while (true) {
            while (isConnected()) {
                commandName = null;
                try {
                    commandName = sc.next();
                } catch (NoSuchElementException e) {
                    System.exit(0);
                }
                get(commandName);
            }
        }
    }

    public synchronized String get(String request) {
        sc = new Scanner(request).useLocale(Locale.US);
        user = GlobalUser.getUser();
        String commandName = sc.next();
        DatagramPacket sendPacket = null;
        try {
            buffer.clear();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);


            Command cmd = commandManager.getCommand(commandName);
            if (cmd == null) {
                System.out.println("Command not found");
                return "";
            }

            if (cmd.getName().equals("exit")) {
                System.out.println("Exiting...");
                close();
                System.exit(0);
            }

            objectOutputStream.writeObject(user);
            objectOutputStream.writeObject(cmd);
            cmd.readParameters(sc, objectOutputStream);
            objectOutputStream.flush();

            buffer.put(byteArrayOutputStream.toByteArray());
            buffer.flip();

            sendPacket = new DatagramPacket(buffer.array(), buffer.remaining(), inetSocketAddress);
        } catch (IOException e) {
            close();
        }

//        System.out.println(receive(channel, buffer, sendPacket));
        return receive(channel, buffer, sendPacket);
    }

    public static String receive(DatagramChannel channel, ByteBuffer buffer, DatagramPacket sendPacket) {
        try {
            channel.socket().setSoTimeout(1000);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        long timeStart = System.currentTimeMillis();
        while (true) {
            try {
                channel.socket().send(sendPacket);
                buffer.clear();
                DatagramPacket receivePacket = new DatagramPacket(buffer.array(), buffer.remaining());
                channel.socket().receive(receivePacket);
                String data = new String(receivePacket.getData(), 0, receivePacket.getLength());
                return data.trim();
            } catch (IOException e) {
                long timeNow = System.currentTimeMillis();
                if (timeNow - timeStart > 10000) {
                    System.out.println("Timeout");
                    System.exit(0);
                }
            }
        }
    }

    public void close() {
        try {
            channel.socket().disconnect();
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isConnected() {
        return !channel.socket().isClosed();
    }
}
