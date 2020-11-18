package Model;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TabelModelTransaksiUser extends AbstractTableModel {
    List<Transaksi> listOfTuser;

    public TabelModelTransaksiUser(List<Transaksi> listOfTusaer){
        this.listOfTuser = listOfTusaer;
    }

    public int getRowCount() {
        return this.listOfTuser.size();
    }

    public int getColumnCount() {
        return 6;
    }

    public String getColumnName(int column) {
        switch (column){
            case 0:
                return "Area";
            case 1:
                return "Nama Garage";
            case 2:
                return "Waktu Mulai";
            case 3:
                return "Waktu Selesai";
            case 4:
                return "Durasi (jam)";
            case 5:
                return "Total Transaksi";

            default:
                return null;
        }
    }



    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0:
                return listOfTuser.get(rowIndex).getArea();
            case 1:
                return listOfTuser.get(rowIndex).getGarage();
            case 2:
                return listOfTuser.get(rowIndex).getStartTime();
            case 3:
                return listOfTuser.get(rowIndex).getEndTime();
            case 4:
                return listOfTuser.get(rowIndex).getDuration();
            case 5:
                return listOfTuser.get(rowIndex).getTotalTransaction();
            default:
                return null;
        }
    }
}
