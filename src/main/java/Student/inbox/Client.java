package Student.inbox;

import Database.*;
import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private boolean isInitialized = false;
    private static final int CONNECTION_TIMEOUT = 5000; // 5 seconds timeout
    private final String studentName;
    private final String teacherName;
    private final ChatHistoryDB chatHistoryDB;
    private final StudentDBConnect studentDBConnect;
    private final TeacherDBConnect teacherDBConnect;

    public Client(String teacherIP, int teacherPort, String studentName, String teacherName) {
        this.studentName = studentName;
        this.teacherName = teacherName;
        this.chatHistoryDB = new ChatHistoryDB();
        this.studentDBConnect = new StudentDBConnect();
        this.teacherDBConnect = new TeacherDBConnect();
        
        try {
            this.socket = new Socket();
            this.socket.connect(new InetSocketAddress(teacherIP, teacherPort), CONNECTION_TIMEOUT);
            
            this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            writer.write(studentName);
            writer.newLine();
            writer.flush();

            isInitialized = true;
            listenForMessages();
        } catch (SocketTimeoutException e) {
            closeConnection();
        } catch (UnknownHostException e) {
            System.out.println("Cannot find teacher's IP address: " + teacherIP);
            closeConnection();
        } catch (IOException e) {
            System.out.println("Cannot connect to teacher: " + e.getMessage());
            closeConnection();
        }
    }

    public void sendMessage(String message) {
        if (!isInitialized || writer == null) {
            // Store message in database for offline delivery
            int senderId = studentDBConnect.getStudentID(studentName);
            int receiverId = teacherDBConnect.getTeacherId(teacherName);
            chatHistoryDB.saveToDB(senderId, "student", receiverId, "teacher", message);
            return;
        }
        
        try {
            writer.write(message);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            System.out.println("Failed to send message: " + e.getMessage());
            closeConnection();
        }
    }

    private void listenForMessages() {
        new Thread(() -> {
            try {
                String message;
                while (isInitialized && (message = reader.readLine()) != null) {
                    System.out.println("Teacher: " + message);

                    if (messageListener != null) {
                        messageListener.onMessageReceived(message);
                    }
                }
            } catch (IOException e) {
                System.out.println("Disconnected from teacher: " + e.getMessage());
                closeConnection();
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
        isInitialized = false;
        try {
            if (reader != null) {
                reader.close();
                reader = null;
            }
            if (writer != null) {
                writer.close();
                writer = null;
            }
            if (socket != null) {
                socket.close();
                socket = null;
            }
        } catch (IOException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }
}
