package TableModel;

public class EditibleCells {
    public boolean[][] editable_cells;

    public EditibleCells(int rows, int cols, boolean initValue) {
        editable_cells = new boolean[rows + 10][cols];

        for (int d1 = 0; d1 < editable_cells.length; d1++) {
            for (int l1 = 0; l1 < editable_cells[0].length; l1++) {
                editable_cells[d1][l1] = initValue;
            }
        }

    }

    public void setRow(int row, boolean value) {
        for (int i = 0; i < editable_cells[0].length; i++) {
            editable_cells[row][i] = value;
        }
    }

    public void setCol(int col, boolean value) {
        for (int i = 0; i < editable_cells.length; i++) {
            editable_cells[i][col] = value;
        }
    }

    public void setCase(int row, int col, boolean value) {
        editable_cells[row][col] = value;
    }

    public boolean[][] getEditable_cells() {
        return this.editable_cells;
    }

    public void setEditable_cells(boolean[][] editable_cells) {
        this.editable_cells = editable_cells;
    }

}
