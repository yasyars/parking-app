package Model;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelKendaraan extends AbstractTableModel{

    List<Kendaraan> listOfKendaraan;

    public TableModelKendaraan(List<Kendaraan> listOfKendaraan){
        this.listOfKendaraan = listOfKendaraan;
    }

    @Override
    public int getRowCount() {
        return this.listOfKendaraan.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0:
                return "Plat No";
            case 1:
                return "Jenis Kendaraan";
            default:
                return null;
        }
    }



    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0:
                return listOfKendaraan.get(rowIndex).getNoPlat();
            case 1:
                return listOfKendaraan.get(rowIndex).getTipe();
            default:
                return null;
        }
    }
}
