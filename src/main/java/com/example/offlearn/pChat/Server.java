package com.example.offlearn.pChat;

import javafx.geometry.Pos;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private ServerSocket serverSocket;
    private Map<String, ClientHandler> students = new HashMap<>();
    private pChatTeacherController controller;

    public Server(ServerSocket serverSocket, pChatTeacherController controller) {
        this.serverSocket = serverSocket;
        this.controller = controller;
    }

    public void startServer() {
        System.out.println("Teacher server is running...");
        try {
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                new Thread(() -> handleStudent(socket)).start();
            }
        } catch (IOException e) {
            closeServer();
        }
    }

    private void handleStudent(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String studentName = reader.readLine();
            students.put(studentName, new ClientHandler(socket, reader, writer));
            controller.addStudent(studentName);
            System.out.println(studentName + " connected.");

            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println(studentName + ": " + message);
                controller.addMessage(studentName + ": " + message, Pos.CENTER_LEFT, "#EAEAEA");
            }

        } catch (IOException e) {
            System.out.println("A student disconnected.");
        }
    }

    public void sendMessageToStudent(String studentName, String message) {
        ClientHandler student = students.get(studentName);
        if (student != null) {
            student.sendMessage("Teacher: " + message);
        }
    }

    public void closeServer() {
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ClientHandler {
        private Socket socket;
        private BufferedWriter writer;

        public ClientHandler(Socket socket, BufferedReader reader, BufferedWriter writer) {
            this.socket = socket;
            this.writer = writer;
        }

        public void sendMessage(String message) {
            try {
                writer.write(message);
                writer.newLine();
                writer.flush();
            } catch (IOException e) {
                System.out.println("Failed to send message.");
            }
        }
    }
}
