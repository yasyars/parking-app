package Model;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelGarage extends AbstractTableModel {
    List<Garage> listOfGarage;

    public TableModelGarage(List<Garage> listOfGarage){
        this.listOfGarage = listOfGarage;
    }

    @Override
    public int getRowCount() {
        return this.listOfGarage.size();
    }

    @Override
    public int getColumnCount() {
        return 8;
    }

    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0:
                return "ID Garage";
            case 1:
                return "Nama Garage";
            case 2:
                return "Hari Operasional";
            case 3:
                return "Waktu Buka";
            case 4:
                return "Waktu Tutup";
            case 5:
                return "ID Area Garage";
            case 6:
                return "Tarif Motor";
            case 7:
                return "Tarif Mobil";
            default:
                return null;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0:
                return listOfGarage.get(rowIndex).getId();
            case 1:
                return listOfGarage.get(rowIndex).getName();
            case 2:
                return listOfGarage.get(rowIndex).getOperationalTime().getDay();
            case 3:
                return listOfGarage.get(rowIndex).getOperationalTime().getOpenHour();
            case 4:
                return listOfGarage.get(rowIndex).getOperationalTime().getCloseHour();
            case 5:
                return listOfGarage.get(rowIndex).getArea();
            case 6:
                return listOfGarage.get(rowIndex).getTarifMotor();
            case 7:
                return listOfGarage.get(rowIndex).getTarifMobil();
            default:
                return null;
        }
    }
}
