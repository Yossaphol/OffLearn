package Student.swing;

import Database.*;
import a_Session.SessionHandler;
import a_Session.SessionManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PostTopicMDI extends JInternalFrame implements ActionListener, SessionHandler {

    private JTextArea jTextArea;
    private JButton cancel, post;
    private String name;

    public PostTopicMDI() {
        super("Post Topic", true, true, true, true);
        handleSession();

        setSize(400, 400);
        setLocation(50, 50);
        setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.decode("#A8A8A8"), 2));
        contentPanel.setBackground(Color.WHITE);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel yourTopic = new JLabel("Your Topics");
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

        setContentPane(contentPanel);
        setVisible(true);
    }

    @Override
    public void handleSession() {
        this.name = SessionManager.getInstance().getUsername();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancel) {
            dispose();
        } else if (e.getSource() == post) {
            String messages = jTextArea.getText();
            topicDB topic = new topicDB();
            topic.saveToDB(messages, name);
            dispose();
        }
    }
}
