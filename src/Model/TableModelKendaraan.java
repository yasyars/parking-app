package Model;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelKendaraan extends AbstractTableModel{

    List<Kendaraan>listOfKendaraan;
    public TableModelKendaraan(List<Kendaraan> listOfKendaraan){
        this.listOfKendaraan = listOfKendaraan;
    }
    public int getRowCount() {
        return this.listOfKendaraan.size();
    }
    public int getColumnCount() {
        return 3;
    }

    public String getColumnName(int column) {
        switch (column){
            case 0:
                return "Plat NO";
            case 1:
                return "Jenis Kendaraan";
            case 2:
                return "Id User";
            default:
                return null;
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0:
                return listOfKendaraan.get(rowIndex).getNoPlat();
            case 1:
                return listOfKendaraan.get(rowIndex).getTipe();
            case 2:
                return listOfKendaraan.get(rowIndex).getOwner();
            default:
                return null;
        }
    }
}
