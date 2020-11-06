import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;

public class GarageForm extends JFrame implements ActionListener{
    private JPanel panelgarage;
    private JTextField txtNamaGarage;
    private JTextField txtIdGarage;
    private JTable tabelGarage;
    private JButton insertButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JTextField txtHariOperasional;
    private JTextField txtWaktuBuka;
    private JTextField txtWaktuTutup;
    private JTextField txtIdArea;
    private JButton backButton;

    private void kosongkan_form(){
        txtNamaGarage.setEditable(true);
        txtNamaGarage.setText(null);
        txtIdGarage.setEditable(true);
        txtIdGarage.setText(null);
        txtHariOperasional.setEditable(true);
        txtHariOperasional.setText(null);
        txtWaktuBuka.setEditable(true);
        txtWaktuBuka.setText(null);
        txtIdArea.setEditable(true);
        txtIdArea.setText(null);
    }
    private void tampilkan_data(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No.");
        model.addColumn("ID Garage");
        model.addColumn("Nama Garage");
        model.addColumn("Hari Operasional");
        model.addColumn("Waktu Buka");
        model.addColumn("Waktu Tutup");
        model.addColumn("Id Area");




        try{
            int no = 1;
            String sql = "SELECT * FROM garage";
            java.sql.Connection CONN =(Connection) DbConnection.getConnection();
            java.sql.Statement stm = CONN.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            while (res.next()){
                model.addRow(new Object[]{no++,res.getString(1),res.getString(2),res.getString(3),res.getString(4),res.getString(5),res.getString(6)});
            }
            tabelGarage.setModel(model);


        }catch (SQLException e){
            System.out.println("Error : " +e.getMessage());

        }
    }
    public void actionPerformed(ActionEvent evt){
        //private void setinsertButtonActionPerformed(java.awt.event.ActionEvent evt){
        try{
            if (txtIdGarage.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Id Garage tidak boleh kosong!");
            } else if (txtNamaGarage.equals("")) {
                JOptionPane.showMessageDialog(null, "Nama Garage tidak boleh kosong");
            } else if (txtHariOperasional.equals("")){
                JOptionPane.showMessageDialog(null, "Hari Operasional tidak boleh kosong");
            } else if (txtWaktuBuka.equals("")){
                JOptionPane.showMessageDialog(null,"Waktu Buka tidak boleh kosong");
            } else if (txtWaktuTutup.equals("")){
                JOptionPane.showMessageDialog(null,"Waktu Tutup tidak boleh kosong");
            } else if (txtIdArea.equals("")){
                JOptionPane.showMessageDialog(null,"Id Area tidak boleh kosong");
            }else{
                String sql = "INSERT INTO garage VALUES('" +txtIdGarage.getText()+"','" +txtNamaGarage.getText()+"','"+txtHariOperasional.getText()+"','"+txtWaktuBuka.getText()+"','"+txtWaktuTutup.getText()+"','"+txtIdArea.getText()+"')";
                java.sql.Connection CONN =(Connection) DbConnection.getConnection();
                java.sql.PreparedStatement pstm = CONN.prepareStatement(sql);
                pstm.execute();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
                tampilkan_data();
            }

        }catch (HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());

        }
    }


    public GarageForm(){
        tampilkan_data();
        kosongkan_form();
        insertButton.addActionListener( this);

        add(panelgarage);
        setTitle("Aplikasi Parking Subcription");
        setSize(400,400);

        tabelGarage.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int baris = tabelGarage.rowAtPoint(e.getPoint());
                String id_garage = tabelGarage.getValueAt(baris,1).toString();
                txtIdGarage.setText(id_garage);
                String nama_garage = tabelGarage.getValueAt(baris,2).toString();
                txtNamaGarage.setText(nama_garage);
                String hari = tabelGarage.getValueAt(baris,3).toString();
                txtHariOperasional.setText(hari);
                String waktu_buka = tabelGarage.getValueAt(baris,4).toString();
                txtWaktuBuka.setText(waktu_buka);
                String waktu_tutup = tabelGarage.getValueAt(baris,5).toString();
                txtWaktuTutup.setText(waktu_tutup);
                String id_area = tabelGarage.getValueAt(baris,6).toString();
                txtIdArea.setText(id_area);
            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String sql = "UPDATE garage SET id_garage = '"+txtIdGarage.getText()+"', nama_garage ='"+txtNamaGarage.getText()+"', hari_operasional ='"+txtHariOperasional.getText()+"', waktu_buka ='"+txtWaktuBuka.getText()+"', waktu_tutup ='"+txtWaktuTutup.getText()+"', id_area ='"+txtIdArea.getText()+"' WHERE id_garage= '"+txtIdGarage.getText()+"'";
                    java.sql.Connection CONN = (Connection) DbConnection.getConnection();
                    java.sql.PreparedStatement pstm = CONN.prepareStatement(sql);
                    pstm.execute();
                    JOptionPane.showMessageDialog(null, "Edit Data Berhasil");


                }catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex);
                }
                tampilkan_data();
               }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String sql ="DELETE FROM garage WHERE id_garage ='"+txtIdGarage.getText()+"'";
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
