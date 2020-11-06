import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.regex.Pattern;

public class Kendaraan extends JFrame implements ActionListener{

    private JPanel PanelBawah;
    private JPanel PanelAtas;
    private JTextField platNoField;
    private JButton insertButton;
    private JButton selectButton;
    private JButton deleteButton;
    private JButton updateButton;
    private JPanel panelKendaraan;
    private JPanel JudulPanel;
    private JComboBox jenisKendaraanCombo;
    private JButton backButton;
    private JTable tableKendaraan;
    private int idUser;

    public Kendaraan() {
        add(panelKendaraan);
        setTitle("Login sebagai pengguna");
        setSize(500, 500);
        insertButton.addActionListener(this);

//
//        backButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                dispose();
//                MenuUser mu = new MenuUser();
//       //         mu.setVisible(true);
//            }
        //    });
    }
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            dispose();
            MenuUser mu = new MenuUser();
            mu.setVisible(true);
            mu.setLocationRelativeTo(null);
        } else if (e.getSource() == insertButton) {
            String plat_no = platNoField.getText().toUpperCase().replaceAll("\\s", "");

            if (plat_no.equals("")) {
                JOptionPane.showMessageDialog(null, "Plat nomor tidak boleh kosong!");
            } else if (!isPlatNoValid(plat_no)) {
                JOptionPane.showMessageDialog(null, "Format plat nomor salah!");
            } else {
                Connection conn = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                String query = "INSERT INTO `parking`.`kendaraan` (`plat_no`, `jenis_kendaraan`, `id_user`) VALUES (?, ?, ?)";
                try {
                    conn = DbConnection.getConnection();
                    ps = conn.prepareStatement(query);
                    ps.setString(1, plat_no);
                    ps.setString(2, String.valueOf(jenisKendaraanCombo.getSelectedItem()));
                    ps.setInt(3, idUser);


                    if (ps.executeUpdate() != 0) {
                        JOptionPane.showMessageDialog(null, "Insert Berhasil!");
//                        dispose();
//                        Init init = new Init();
//                        init.setVisible(true);
//                        init.setLocationRelativeTo(null);
                    } else {
                        JOptionPane.showMessageDialog(null, "Insert Gagal");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                } finally {
                    try { rs.close(); } catch (Exception ex) { /* ignored */ }
                    try { ps.close(); } catch (Exception ex) { /* ignored */ }
                    try { conn.close(); } catch (Exception ex) { /* ignored */ }
                }
            }
        }
    }


    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int id_user) {
        this.idUser = id_user;
    }

    public boolean isPlatNoValid(String plat_no){
        String mobilRegex = "^[A-Z]{2}[0-9]{4}[A-Z]{1}$";
        String motorRegex = "^[A-Z]{2}[0-9]{3}[A-Z]{2}$";
        String jenis = String.valueOf(jenisKendaraanCombo.getSelectedItem());

        if (jenis == "Mobil"){
            Pattern p = Pattern.compile(mobilRegex);
            return p.matcher(plat_no).matches();
        } else {
            Pattern p = Pattern.compile(motorRegex);
            return p.matcher(plat_no).matches();
        }
    }
}


