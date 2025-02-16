package com.example.offlearn.pChat;

import com.example.offlearn.pChat.DataBase.TeacherDBConnect;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private ServerSocket serverSocket;
    private Map<String, ClientHandler> students = new HashMap<>();
    private pChatTeacherController controller;
    private TeacherDBConnect teacherDb;
    private String teacherName;
    private String teacherIP;
    private int teacherPort;

    public Server(ServerSocket serverSocket, pChatTeacherController controller) {
        this.serverSocket = serverSocket;
        this.controller = controller;
        this.teacherDb = new TeacherDBConnect();
        this.teacherName = "teacher 1";
        this.teacherIP = getLocalIPAddress();
        this.teacherPort = serverSocket.getLocalPort();
        saveTeacherInfoToDB(teacherName);
    }

    public void startServer() {
        System.out.println("Teacher server is running on " + teacherIP + ":" + teacherPort);

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

            if (!students.containsKey(studentName)) {
                students.put(studentName, new ClientHandler(socket, reader, writer));

                controller.addStudent(studentName);
                teacherDb.addStudent(studentName, socket.getInetAddress().getHostAddress(), socket.getPort());

                System.out.println(studentName + " connected.");
            }

            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println(studentName + ": " + message);

                controller.receiveMessage(studentName, message);
            }

        } catch (IOException e) {
            System.out.println("A student disconnected.");
        }
    }



    public void sendMessageToStudent(String studentName, String message) {
        ClientHandler student = students.get(studentName);
        if (student != null) {
            System.out.println("Sending message to " + studentName + ": " + message);
            student.sendMessage(message);
        } else {
            System.out.println("Student not found: " + studentName);
        }
    }

    public void closeServer() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
                System.out.println("Server closed.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveTeacherInfoToDB(String teacherName) {
        String ip = getLocalIPAddress();
        int port = serverSocket.getLocalPort();
        teacherDb.addTeacher(teacherName, ip, port);
    }

    private String getLocalIPAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "localhost";
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
