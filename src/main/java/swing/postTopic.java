package swing;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class postTopic implements ActionListener {

    private JFrame jFrame;
    private JDesktopPane desktopPane;
    private JInternalFrame jInternalFrame;
    private JPanel top, middle, buttom;
    private JButton cancle, post, browseButton;
    private JTextField jTextField;
    private JLabel yourTopic;

    public void openSwingWindow(Stage primaryStage){
        SwingUtilities.invokeLater(() -> {

            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            jFrame = new JFrame("Swing Window");
            jFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            jFrame.setUndecorated(true);
            jFrame.setSize(400, 200);

            double stageX = primaryStage.getX();
            double stageY = primaryStage.getY();
            double stageWidth = primaryStage.getWidth();
            double stageHeight = primaryStage.getHeight();

            int frameWidth = jFrame.getWidth();
            int frameHeight = jFrame.getHeight();

            int x = (int) (stageX + (stageWidth - frameWidth) / 2);
            int y = (int) (stageY + (stageHeight - frameHeight) / 2);

            jFrame.setLocation(x, y);

            desktopPane = new JDesktopPane();
            jFrame.add(desktopPane);

            jInternalFrame = new JInternalFrame();
            jInternalFrame.setSize(400, 200);
            jInternalFrame.setBorder(null);
            ((javax.swing.plaf.basic.BasicInternalFrameUI) jInternalFrame.getUI()).setNorthPane(null);
            jInternalFrame.setVisible(true);
            jInternalFrame.setLayout(new BorderLayout());

            jTextField = new JTextField();

            top = new JPanel();
            middle = new JPanel();
            buttom = new JPanel();

            top.setLayout(new FlowLayout(FlowLayout.CENTER));
            middle.setLayout(new BorderLayout());
            buttom.setLayout(new FlowLayout(FlowLayout.RIGHT));

            yourTopic = new JLabel("Your Topics");

            cancle = new JButton("Cancle");
            post = new JButton("Post");

            cancle.addActionListener(this);

            middle.add(yourTopic, BorderLayout.NORTH);
            middle.add(jTextField, BorderLayout.CENTER);

            buttom.add(cancle);
            buttom.add(post);

            jInternalFrame.add(top, BorderLayout.NORTH);
            jInternalFrame.add(middle, BorderLayout.CENTER);
            jInternalFrame.add(buttom, BorderLayout.SOUTH);

            desktopPane.add(jInternalFrame);

            jFrame.setVisible(true);
        });
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Cancle")){
            System.exit(0);
        }
    }
}
