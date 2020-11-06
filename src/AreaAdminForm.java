import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;



public class AreaAdminForm extends JFrame implements ActionListener {
    private JPanel panelareaparkir;
    private JTextField txtnamaarea;
    private JTextField txtidarea;
    private JButton updateButton;
    private JButton insertButton;
    private JButton deleteButton;
    private JTable tabelareaparkir;

    private void kosongkan_form(){
        txtidarea.setEditable(true);
        txtidarea.setText(null);
        txtnamaarea.setEditable(true);
        txtnamaarea.setText(null);
    }
    private void tampilkan_data(){
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("No.");
        model.addColumn("ID Area");
        model.addColumn("Nama Area");

        try{
            int no = 1;
            String sql = "SELECT * FROM area_parkir";
            java.sql.Connection CONN =(Connection) DbConnection.getConnection();
            java.sql.Statement stm = CONN.createStatement();
            java.sql.ResultSet res = stm.executeQuery(sql);

            while (res.next()){
                model.addRow(new Object[]{no++,res.getString(1),res.getString(2)});
            }
            tabelareaparkir.setModel(model);


        }catch (SQLException e){
            System.out.println("Error : " +e.getMessage());

        }
    }
    public void actionPerformed(ActionEvent evt){
    //private void setinsertButtonActionPerformed(java.awt.event.ActionEvent evt){
        try{
            String sql = "INSERT INTO area_parkir VALUES('" +txtidarea.getText()+"','" +txtnamaarea.getText()+"')";
            java.sql.Connection CONN =(Connection) DbConnection.getConnection();
            java.sql.PreparedStatement pstm = CONN.prepareStatement(sql);
            pstm.execute();
            JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan");
            tampilkan_data();
        }catch (HeadlessException | SQLException e){
            JOptionPane.showMessageDialog(this, e.getMessage());

        }
    }

    public AreaAdminForm(){
        tampilkan_data();
        kosongkan_form();
        insertButton.addActionListener(this);

        add(panelareaparkir);
        setTitle("Aplikasi Parking Subcription");
        setSize(400,400);


        tabelareaparkir.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int baris = tabelareaparkir.rowAtPoint(e.getPoint());
                String id_area =tabelareaparkir.getValueAt(baris, 1).toString();
                txtidarea.setText(id_area);
                String nama_area =tabelareaparkir.getValueAt(baris, 2).toString();
                txtnamaarea.setText(nama_area);

            }
        });
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String sql = "UPDATE area_parkir SET id_area = '"+txtidarea.getText()+"', nama_area ='"+txtnamaarea.getText()+"' WHERE id_area= '"+txtidarea.getText()+"'";
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
                    String sql ="DELETE FROM area_parkir WHERE id_area ='"+txtidarea.getText()+"'";
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
