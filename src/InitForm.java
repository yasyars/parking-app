import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitForm extends JFrame{
    private JPanel initPanel;
    private JButton adminButton;
    private JButton penggunaButton;


    public InitForm(){
        add(initPanel);
        setTitle("Aplikasi Parking Subcription");
        setSize(500,500);

        penggunaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                UserLogin ul = new UserLogin();
                ul.setVisible(true);
            }
        });
    }


}
