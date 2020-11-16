package Model;
import javax.swing.table.AbstractTableModel;
import java.time.DayOfWeek;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.List;


public class TableModelTransaksiBulanan extends AbstractTableModel{
    List<Transaksi> listOfTransaksi;
    public TableModelTransaksiBulanan(List<Transaksi> listOfTransaksi){
        this.listOfTransaksi = listOfTransaksi;
    }

    public int getRowCount() {
        return this.listOfTransaksi.size();
    }
    public int getColumnCount() {
        return 6;
    }

    public String getColumnName(int column) {
        switch (column){
            case 0:
                return "Minggu";
            case 1:
                return "Nama Pengguna";
            case 2:
                return "Area";
            case 3:
                return "Garage";
            case 4:
                return "Durasi (jam)";
            case 5:
                return "Total Transaksi";
            default:
                return null;
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        WeekFields weekFields = WeekFields.of(DayOfWeek.SUNDAY, 1);
        // apply weekOfMonth(
        TemporalField weekOfMonth= weekFields.weekOfMonth();

        switch (columnIndex){
            case 0:
                return "Minggu ke-" + listOfTransaksi.get(rowIndex).getStartLocalTime().get(weekOfMonth);
            case 1:
                return listOfTransaksi.get(rowIndex).getUser();
            case 2:
                return listOfTransaksi.get(rowIndex).getArea();
            case 3:
                return listOfTransaksi.get(rowIndex).getGarage();
            case 4:
                return listOfTransaksi.get(rowIndex).getDuration();
            case 5:
                return listOfTransaksi.get(rowIndex).getTotalTransaction();
            default:
                return null;
        }
    }
}
