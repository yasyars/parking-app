import javax.swing.*;

public class UserLogin extends JFrame{
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton loginButton;
    private JButton registerButton;
    private JButton backButton;
    private JPanel userLoginPanel;

    public UserLogin(){
        add(userLoginPanel);
        setTitle("Login sebagai pengguna");
        setSize(500,500);
    }
}
