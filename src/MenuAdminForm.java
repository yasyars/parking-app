import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuAdminForm extends JFrame{
    private JPanel panelmenuadmin;
    private JButton areaButton;
    private JButton garageButton;
    private JButton riwayatTransaksiButton;
    private JButton tarifButton;

    public MenuAdminForm(){
        add(panelmenuadmin);
        setTitle("Aplikasi Parking Subcription");
        setSize(400,300);


        areaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AreaAdminForm aa = new AreaAdminForm();
                aa.setVisible(true);
            }
        });
        tarifButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                TarifForm tf = new TarifForm();
                tf.setVisible(true);
            }
        });
        garageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                GarageForm g = new GarageForm();
                g.setVisible(true);
            }
        });
    }
}
