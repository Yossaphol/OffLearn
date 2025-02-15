package com.example.offlearn.pChat;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public Client(String teacherIP, int teacherPort, String studentName) {
        try {
            this.socket = new Socket(teacherIP, teacherPort);
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // ส่งชื่อนักเรียนให้ครูรู้ว่าใครคุยอยู่
            writer.write(studentName);
            writer.newLine();
            writer.flush();

            listenForMessages();
        } catch (IOException e) {
            System.out.println("Cannot connect to teacher.");
        }
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

    private void listenForMessages() {
        new Thread(() -> {
            try {
                String message;
                while ((message = reader.readLine()) != null) {
                    System.out.println("Teacher: " + message);
                }
            } catch (IOException e) {
                System.out.println("Disconnected from teacher.");
            }
        }).start();
    }
}
