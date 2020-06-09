package user;

import client.Client;
import utils.EmailValidator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserCreator {
    private Scanner in;
    private DatagramChannel channel;
    private ByteBuffer buffer;
    private InetSocketAddress inetSocketAddress;

    public UserCreator(Scanner in, DatagramChannel channel, ByteBuffer buffer, InetSocketAddress inetSocketAddress) {
        this.in = in;
        this.channel = channel;
        this.buffer = buffer;
        this.inetSocketAddress = inetSocketAddress;
    }

    public User create() {
        // Отправляем класс User во время каждого запроса, при регистрации
        // и входе в аккаунт отправляем только пользователя
        // При успешной регистрации возвращаем уведомление о регистрации
        // При успешном входе ничего не отправляем, на окне появляется надпись
        // for help, type "help" - вход окончен
        User user = null;
        try {
            System.out.println("Do you already have an account? [y/n]");
            String answer = in.next();
            if (answer.equals("y") || answer.equals("yes")) {
                user = login();
            } else if (answer.equals("n") || answer.equals("no")) {
                user = registration();
            } else {
                throw new InputMismatchException();
            }
        } catch (Exception e) {
            System.err.println("Connection is interrupted");
            System.exit(0);
        }
        return user;
    }

    private User registration() {
        User user = new User();
        user.setRegistered(false);
        String answer = "";
        while (!answer.equals("Successful registration")) {
            System.out.print("Email: ");
            String email = in.next();
            if (!EmailValidator.validateEmail(email)) {
                System.out.println("Wrong email");
                continue;
            }
            user.setEmail(email);
            System.out.print("Login: ");
            String login = in.next();
            user.setLogin(login);
            user.setEntry(true);

            DatagramPacket sendPacket = prepareSendPacket(user);
            answer = Client.receive(channel, buffer, sendPacket);
            System.out.println(answer);
        }
        user = login();
        return user;
    }

    private User login() {
        User user = new User();
        user.setRegistered(true);
        String answer = "Wrong login or password";
        while (answer.equals("Wrong login or password")) {
            System.out.print("Login: ");
            user.setLogin(in.next());
            System.out.print("Password: ");
            user.setPassword(in.next());
            user.setEntry(true);

            DatagramPacket sendPacket = prepareSendPacket(user);
            answer = Client.receive(channel, buffer, sendPacket);
            System.out.println(answer);
        }
        user.setEntry(false);
        return user;
    }

    private DatagramPacket prepareSendPacket(User user) {
        DatagramPacket sendPacket = null;
        try {
            buffer.clear();

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

            objectOutputStream.writeObject(user);
            buffer.put(byteArrayOutputStream.toByteArray());
            buffer.flip();
            sendPacket = new DatagramPacket(buffer.array(), buffer.remaining(), inetSocketAddress);
        } catch (IOException e) {
            close();
        }
        return sendPacket;
    }

    public void close() {
        try {
            channel.socket().disconnect();
            channel.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
