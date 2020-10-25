import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InitForm extends JFrame{
    private JPanel initPanel;
    private JLabel labelUser;
    private JLabel labelAdmin;


    public InitForm(){
        add(initPanel);
        setTitle("Aplikasi Parking Subcription");
        setSize(500,500);

        labelUser.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                dispose();
                Login ul = new Login();
                ul.setVisible(true);
            }
        });
//        labelAdmin.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseEntered(MouseEvent e) {
//                super.mouseEntered(e);
//            }
//        });
    }


    private void createUIComponents() {
        // TODO: place custom component creation code here
        ImageIcon iconAdmin = createImageIcon("images/admin.png", "admin image");
        ImageIcon iconUser = createImageIcon("images/user.png", "user image");

        labelAdmin = new JLabel(iconAdmin);
        labelAdmin.setVerticalTextPosition(JLabel.BOTTOM);
        labelAdmin.setHorizontalTextPosition(JLabel.CENTER);

        labelUser = new JLabel(iconUser);
        labelUser.setVerticalTextPosition(JLabel.BOTTOM);
        labelUser.setHorizontalTextPosition(JLabel.CENTER);


    }

    protected ImageIcon createImageIcon(String path, String description) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

}
