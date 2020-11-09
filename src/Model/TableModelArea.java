package Model;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class TableModelArea extends AbstractTableModel{

    List<Area> listOfArea;

    public TableModelArea(List<Area> listOfArea){
        this.listOfArea = listOfArea;
    }

    @Override
    public int getRowCount() {
        return this.listOfArea.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public String getColumnName(int column) {
        switch (column){
            case 0:
                return "ID Area";
            case 1:
                return "Nama Area";
            default:
                return null;
        }
    }



    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex){
            case 0:
                return listOfArea.get(rowIndex).getId();
            case 1:
                return listOfArea.get(rowIndex).getName();
            default:
                return null;
        }
    }
}
