package swing;

import inbox.DataBase.topicDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class postTopic implements ActionListener {

    private JFrame jFrame;
    private JPanel contentPanel;
    private JButton cancel, post;
    private JTextArea jTextArea;
    private JLabel yourTopic;

    public void openSwingWindow() {
        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            jFrame = new JFrame("Swing Window");
            jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jFrame.setUndecorated(true); // ปิดขอบของระบบเพื่อให้เราสร้างขอบเอง
            jFrame.setSize(400, 400);
            jFrame.setLocation(800, 300);

            contentPanel = new JPanel(new BorderLayout());
            contentPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#A8A8A8"), 2));
            contentPanel.setBackground(Color.WHITE);

            JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
            yourTopic = new JLabel("Your Topics");
            top.add(yourTopic);

            JPanel middle = new JPanel(new BorderLayout());
            jTextArea = new JTextArea(20, 50);
            jTextArea.setLineWrap(true);
            jTextArea.setWrapStyleWord(true);
            jTextArea.setMargin(new Insets(5, 5, 5, 5));
            JScrollPane scrollPane = new JScrollPane(jTextArea);
            middle.add(scrollPane, BorderLayout.CENTER);

            JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            cancel = new JButton("Cancel");
            post = new JButton("Post");

            post.setBackground(Color.decode("#8100CC"));
            post.setForeground(Color.WHITE);
            post.setBorderPainted(false);

            post.addActionListener(this);
            cancel.setBorderPainted(false);
            cancel.setBackground(Color.decode("#D9D9D9"));
            cancel.addActionListener(this);

            bottom.add(cancel);
            bottom.add(post);

            contentPanel.add(top, BorderLayout.NORTH);
            contentPanel.add(middle, BorderLayout.CENTER);
            contentPanel.add(bottom, BorderLayout.SOUTH);

            jFrame.setContentPane(contentPanel);
            jFrame.setVisible(true);
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancel) {
            jFrame.dispose();
        } else if (e.getSource() == post){
            String messages = jTextArea.getText();
            topicDB topic = new topicDB();
            topic.saveTopic(messages, "test");
            jFrame.dispose();
        }
    }

}
