package Model;
import javax.swing.table.AbstractTableModel;
import java.util.List;


public class TableModelTransaksiAdmin extends AbstractTableModel{
    List<Transaksi> listOfTransaksi;
    public TableModelTransaksiAdmin(List<Transaksi> listOfTransaksi){
        this.listOfTransaksi = listOfTransaksi;
    }

    public int getRowCount() {
        return this.listOfTransaksi.size();
    }
    public int getColumnCount() {
        return 5;
    }

    public String getColumnName(int column) {
        switch (column){
            case 0:
                return "Nama Pengguna";
            case 1:
                return "area";
            case 2:
                return "Garage";
            case 3:
                return "Durasi";
            case 4:
                return "Total Transaksi";
            default:
                return null;
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0:
                return listOfTransaksi.get(rowIndex).getUser();
            case 1:
                return listOfTransaksi.get(rowIndex).getArea();
            case 2:
                return listOfTransaksi.get(rowIndex).getGarage();
            case 3:
                return listOfTransaksi.get(rowIndex).getDuration();
            case 4:
                return listOfTransaksi.get(rowIndex).getTotalTransaction();
            default:
                return null;
        }
    }
}
