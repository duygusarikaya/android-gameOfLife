package gameoflife.game;


import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

public class Universe {

	private Cell[][] cells;

	public Universe(Cell[][] cells) {
		this.cells = cells;
	}

	public Cell[][] getCells() {
		return cells;
	}
	
	public Cell[][] getNextState() {
		update();
		return getCells();
	}

	public void update() {
        printCellsToScreen(cells);
		Cell[][] cellStates = makeDeepClone(cells);
		for (int i = 0; i < cells.length; i++) { //rows
			for (int j = 0; j < cells[0].length; j++) { //columns
				int aliveNeighbours = getAliveNeighbourCountOf(i, j, cellStates);
				cells[i][j].update(aliveNeighbours);
			}
		}
        printCellsToScreen(cells);
	}



    private int getAliveNeighbourCountOf(int i, int j, Cell[][] cellStates) {

		int aliveNeighbourCount = 0;

		int rowStart  = Math.max( i - 1, 0   );
		int rowFinish = Math.min( i + 1, cellStates.length - 1 );
		int colStart  = Math.max( j - 1, 0   );
		int colFinish = Math.min( j + 1, cellStates[0].length - 1 );

		for ( int curRow = rowStart; curRow <= rowFinish; curRow++ ) {
		    for ( int curCol = colStart; curCol <= colFinish; curCol++ ) {
		        if (cellStates[curRow][curCol].getState() == Cell.CellState.ALIVE )
		        	aliveNeighbourCount++;
		    }
		}
		if (cellStates[i][j].getState()== Cell.CellState.ALIVE)
			aliveNeighbourCount--;

		if(aliveNeighbourCount>0) {
			Log.d("Hello neighbour!", "cell["+i+"]["+j+"] neighbour count: "+aliveNeighbourCount);
            printCellsToScreen(cellStates);
		}
		return aliveNeighbourCount;
	}

    private Cell[][] makeDeepClone(Cell[][] cells) {
        Cell[][] clone = new Cell[cells.length][cells[0].length];
        for(int i=0;i<clone.length;i++) {
            for(int j=0; j<clone[0].length;j++) {
                clone[i][j] = new Cell(cells[i][j].getState());
            }
        }
        return clone;
    }


    private void printCellsToScreen(Cell[][] c) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<c.length;i++) {
            for(int j=0; j<c[0].length;j++) {
                sb.append((c[i][j].getState()== Cell.CellState.DEAD ? "O" : "X") + "\t");
            }
            sb.append("\n"); // Append newline after every row
        }
        Log.d("printUniverse:\n", sb.toString());
    }

}
