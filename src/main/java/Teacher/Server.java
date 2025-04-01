package Teacher;

import Teacher.inbox.pChat.pChatServerController;
import Database.*;
import a_Session.SessionManager;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

public class Server {
    private ServerSocket serverSocket;
    private Map<String, ClientHandler> students = new HashMap<>();
    private pChatServerController controller;
    private TeacherDBConnect teacherDb;
    private String teacherName;
    private String teacherIP;
    private int teacherPort;
    private boolean isRunning = false;

    public Server(ServerSocket serverSocket, pChatServerController controller) {
        this.serverSocket = serverSocket;
        this.controller = controller;
        this.teacherDb = new TeacherDBConnect();
        this.teacherName = SessionManager.getInstance().getUsername();
        this.teacherIP = getLocalIPAddress();
        this.teacherPort = serverSocket.getLocalPort();
        saveTeacherInfoToDB(teacherName);
    }

    public void startServer() {
        if (serverSocket == null || serverSocket.isClosed()) {
            return;
        }

        isRunning = true;
        System.out.println("Teacher server is running on " + teacherIP + ":" + teacherPort);

        try {
            while (isRunning && !serverSocket.isClosed()) {
                try {
                    Socket socket = serverSocket.accept();
                    new Thread(() -> handleStudent(socket)).start();
                } catch (IOException e) {
                    if (isRunning) {
                        System.out.println("Error accepting connection: " + e.getMessage());
                    }
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
            closeServer();
        }
    }

    public String getTeacherName() {
        return this.teacherName;
    }

    private void handleStudent(Socket socket) {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String studentName = reader.readLine();
            if (studentName == null) {
                System.out.println("Received null student name");
                return;
            }

            if (!students.containsKey(studentName)) {
                students.put(studentName, new ClientHandler(socket, reader, writer));
                controller.addStudent(studentName);
                teacherDb.addOrUpdateUser("studentlist", studentName, socket.getInetAddress().getHostAddress(), socket.getPort());
                System.out.println(studentName + " connected.");
            }

            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println(studentName + ": " + message);
                controller.receiveMessage(studentName, message);
            }

        } catch (IOException e) {
            System.out.println("Error handling student connection: " + e.getMessage());
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                System.out.println("Error closing student socket: " + e.getMessage());
            }
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
        isRunning = false;
        try {
            if (serverSocket != null) {
                serverSocket.close();
                serverSocket = null;
                System.out.println("Server closed.");
            }
        } catch (IOException e) {
            System.out.println("Error closing server: " + e.getMessage());
        }
    }

    public void saveTeacherInfoToDB(String teacherName) {
        String ip = getLocalIPAddress();
        int port = serverSocket.getLocalPort();
        teacherDb.addOrUpdateUser("teacherlist", teacherName, ip, port);
    }

    private String getLocalIPAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.out.println("Error getting local IP address: " + e.getMessage());
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
                if (writer != null && !socket.isClosed()) {
                    writer.write(message);
                    writer.newLine();
                    writer.flush();
                }
            } catch (IOException e) {
                System.out.println("Failed to send message: " + e.getMessage());
            }
        }
    }
}
