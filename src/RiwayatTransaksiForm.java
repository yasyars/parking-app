import javax.swing.*;

public class RiwayatTransaksiForm extends JFrame{
    private JTextField waktumasuk;
    private JTextField waktukeluar;
    private JTextField durasi;
    private JTextField idparkir;
    private JTextField idtransaksi;
    private JTextField iduser;
    private JTextField idsub;
    private JButton deleteButton;
    private JButton selectButton;
    private JTable table1;
    private JTextField platno;
    private JTextField idgarage;
    private JTextField total;
    private JLabel IDGarageLabel;
    private JLabel IDTransaksiLabel;
    private JLabel IDUserLabel;
    private JLabel IDParkirLabel;
    private JLabel IDSubLabel;
    private JLabel platNoLabel;
    private JLabel waktuMasukLabel;
    private JLabel waktuKeluarLabel;
    private JLabel durasiLabel;
    private JLabel totalLabel;
    private JPanel panelriwayattransaksi;

    public RiwayatTransaksiForm(){
        add(panelriwayattransaksi);
        setTitle("Aplikasi Parking Subcription");
        setSize(600,450);
    }

}
