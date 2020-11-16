package Model;
import javax.swing.table.AbstractTableModel;
import java.util.List;


public class TableModelTransaksiMingguan extends AbstractTableModel{
    List<Transaksi> listOfTransaksi;
    public TableModelTransaksiMingguan(List<Transaksi> listOfTransaksi){
        this.listOfTransaksi = listOfTransaksi;
    }

    public int getRowCount() {
        return this.listOfTransaksi.size();
    }
    public int getColumnCount() {
        return 7;
    }

    public String getColumnName(int column) {
        switch (column){
            case 0:
                return "Hari";
            case 1:
                return "Tanggal";
            case 2:
                return "Nama Pengguna";
            case 3:
                return "Area";
            case 4:
                return "Garage";
            case 5:
                return "Durasi (jam)";
            case 6:
                return "Total Transaksi";
            default:
                return null;
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0:
                return listOfTransaksi.get(rowIndex).getNamaHariFromStartTime();
            case 1:
                return listOfTransaksi.get(rowIndex).getStartLocalTime().toLocalDate().toString();
            case 2:
                return listOfTransaksi.get(rowIndex).getUser();
            case 3:
                return listOfTransaksi.get(rowIndex).getArea();
            case 4:
                return listOfTransaksi.get(rowIndex).getGarage();
            case 5:
                return listOfTransaksi.get(rowIndex).getDuration();
            case 6:
                return listOfTransaksi.get(rowIndex).getTotalTransaction();
            default:
                return null;
        }
    }
}
