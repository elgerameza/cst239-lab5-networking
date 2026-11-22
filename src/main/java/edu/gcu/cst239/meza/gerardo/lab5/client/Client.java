package edu.gcu.cst239.meza.gerardo.lab5.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 5050;

        try (Socket socket = new Socket(host, port);
             PrintWriter output = new PrintWriter(
                     socket.getOutputStream(), true, StandardCharsets.UTF_8);
             BufferedReader input = new BufferedReader(
                     new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to " + host + ":" + port);
            System.out.println("Commands: ALL, GET id, ADD id name age, DELETE id, FIND name");
            System.out.println("Enter . to quit.");

            while (true) {
                System.out.print("> ");
                if (!scanner.hasNextLine()) {
                    break;
                }

                String command = scanner.nextLine();
                output.println(command);

                String response = input.readLine();
                if (response == null) {
                    System.out.println("Server closed the connection.");
                    break;
                }

                System.out.println(response);
                if (command.equals(".")) {
                    break;
                }
            }
        } catch (IOException ex) {
            System.err.println("Client error: " + ex.getMessage());
        }
    }
}