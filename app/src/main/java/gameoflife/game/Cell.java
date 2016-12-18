package gameoflife.game;

public class Cell {

	private CellState state;

	public Cell(CellState state) {
		this.state = state;
	}

	public void setState(CellState state) {
		this.state = state;
	}

	public enum CellState {
		ALIVE, DEAD
	}

	public void update(int neigbourCount) {
		if(this.state == CellState.ALIVE) {
			state = (neigbourCount == 2 || neigbourCount == 3) ? CellState.ALIVE : CellState.DEAD;
		} else {
			state = (neigbourCount == 3) ? CellState.ALIVE : CellState.DEAD;
		}
	}

	public CellState getState() {
		return state;
	};

}
