package Model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelParkir extends AbstractTableModel {
    List<Parkir> listOfParkir;

    public TableModelParkir(List<Parkir> listOfParkir){
        this.listOfParkir = listOfParkir;
    }

    @Override
    public int getRowCount() {
        return this.listOfParkir.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0:
                return "Area";
            case 1:
                return "Garage";
            case 2:
                return "Kendaraan";
            case 3:
                return "Waktu Masuk";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0:
                return listOfParkir.get(rowIndex).getArea();
            case 1:
                return listOfParkir.get(rowIndex).getGarage();
            case 2:
                return listOfParkir.get(rowIndex).getKendaraan();
            case 3:
                return listOfParkir.get(rowIndex).getStartTime();
            default:
                return null;
        }
    }
}
