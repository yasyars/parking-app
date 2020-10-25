import javax.swing.*;

public class ParkingTest extends JFrame {
    private JPanel testPanel;
    public JLabel labelNama;

    public ParkingTest(){
        add(testPanel);
    }

    public JLabel getLabelNama() {
        return labelNama;
    }

//    public void setLabelNama(JLabel labelNama) {
//        this.labelNama.setText("Welcome, <"+labelNama+">");
//    }
}
