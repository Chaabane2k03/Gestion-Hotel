package TableModel;

import javax.swing.table.DefaultTableModel;

public class MyDefaultTableModel extends DefaultTableModel {
	private static final long serialVersionUID = 1L;
	
	
	public boolean[][] editable_cells; 

    public MyDefaultTableModel(Object[][] data, String[] column) { 

        super(data, column);
        this.editable_cells = new boolean[data.length][column.length];
    }

    public void setEditable_cells(boolean[][] editable_cells) {
        this.editable_cells = editable_cells;
    }

    @Override
    public boolean isCellEditable(int row, int column) { 
        return this.editable_cells[row][column];
       
    }

    public void setCellEditable(int row, int col, boolean value) {
        this.editable_cells[row][col] = value; // set cell true/false
        this.fireTableCellUpdated(row, col);
    }
}
