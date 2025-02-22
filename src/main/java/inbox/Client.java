package inbox;

import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public Client(String teacherIP, int teacherPort, String studentName) {
        try {
            this.socket = new Socket(teacherIP, teacherPort);
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

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

                    if (messageListener != null) {
                        messageListener.onMessageReceived(message);
                    }
                }
            } catch (IOException e) {
                System.out.println("Disconnected from teacher.");
            }
        }).start();
    }


    public interface MessageListener {
        void onMessageReceived(String message);
    }

    private MessageListener messageListener;

    public void setMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }


    public void closeConnection() {
        try {
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (socket != null) {
                socket.close();
            }
            System.out.println("Connection closed.");
        } catch (IOException e) {
            System.out.println("Error closing connection.");
        }
    }
}
