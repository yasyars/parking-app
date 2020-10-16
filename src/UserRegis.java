import javax.swing.*;

public class UserRegis extends JFrame {

    private JPanel userRegisPanel;
    private JTextField namaTextField;
    private JTextField alamatTextField;
    private JTextField emailTextField;
    private JButton registrasiButton;
    private JButton cancelButton;
    private JPasswordField passwordPasswordField;

    public UserRegis(){
        add(userRegisPanel);
        setTitle("Registrasi Pengguna Baru");
    }
}
