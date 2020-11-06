import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TarifForm extends JFrame{
    private JPanel paneltarifparkir;
    private JTextField txtharga;
    private JTextField txtidtarif;
    private JTable tabeltarif;
    private JButton insertButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JComboBox cmb_jeniskendaraan;

    private void tampilkan_data(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No.");
        model.addColumn("ID Tarif");
        model.addColumn("Harga");
        model.addColumn("Jenis Kendaraan");

        try{
            int no = 1;
            String sql = "SELECT * FROM tarif";
            java.sql.Connection CONN =(Connection) DbConnection.getConnection();
            java.sql.Statement stm = CONN.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            while (res.next()){
                model.addRow(new Object[]{no++,res.getString(1),res.getString(2),res.getString(3)});
            }
            tabeltarif.setModel(model);


        }catch (SQLException e){
            System.out.println("Error : " +e.getMessage());

        }
    }

    public TarifForm(){
        add(paneltarifparkir);
        setTitle("Aplikasi Parking Subcription");
        setSize(400,400);

        tampilkan_data();


        insertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String sql = "INSERT INTO tarif VALUES('" +txtidtarif.getText()+"','" +txtharga.getText()+"','" +cmb_jeniskendaraan.getSelectedItem()+"')";
                    java.sql.Connection CONN =(Connection) DbConnection.getConnection();
                    java.sql.PreparedStatement pstm = CONN.prepareStatement(sql);
                    pstm.execute();
                    JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                    tampilkan_data();
                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
                tampilkan_data();
            }
        });

        tabeltarif.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int baris = tabeltarif.rowAtPoint(e.getPoint());
                String id_tarif =tabeltarif.getValueAt(baris, 1).toString();
                txtidtarif.setText(id_tarif);
                String harga =tabeltarif.getValueAt(baris, 2).toString();
                txtharga.setText(harga);
                String jenis_kendaraan =tabeltarif.getValueAt(baris, 2).toString();
                cmb_jeniskendaraan.setSelectedItem(jenis_kendaraan);
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String sql = "UPDATE tarif SET id_tarif = '"+txtidtarif.getText()+"', harga ='"+txtharga.getText()+"',jenis_kendaraan ='" +cmb_jeniskendaraan.getSelectedItem()+"' WHERE id_tarif= '"+txtidtarif.getText()+"'";
                    java.sql.Connection CONN = (Connection) DbConnection.getConnection();
                    java.sql.PreparedStatement pstm = CONN.prepareStatement(sql);
                    pstm.execute();
                    JOptionPane.showMessageDialog(null, "Edit Data Berhasil");


                }catch (Exception ex) {
                    //Logger.getLogger(Registrasi.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex);
                }
                tampilkan_data();

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String sql ="DELETE FROM tarif WHERE id_tarif='"+txtidtarif.getText()+"'";
                    java.sql.Connection CONN = (Connection) DbConnection.getConnection();
                    java.sql.PreparedStatement pstm = CONN.prepareStatement(sql);
                    pstm.execute();
                    JOptionPane.showMessageDialog(null, "Delete Data Berhasil");
                }catch (Exception ex) {
                    //Logger.getLogger(Registrasi.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex);
                }
                tampilkan_data();

            }
        });
    }
}
