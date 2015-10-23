package poo.khet.gameutils;

import java.util.HashMap;
import java.util.Map;

import poo.khet.Square;

public class Grid<T> {
	
	private Map<Position, T> grid;
	private int rows;
	private int columns;
	
	public Grid (int rows, int cols) {
		if (rows < 0 || cols < 0) {
			throw new IllegalArgumentException();
		}
		this.rows = rows;
		this.columns = cols;
		grid = new HashMap<Position, T>();
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return columns;
	}
	
	public Square getSquare(Position pos) {
		if (pos == null || !isInBounds(pos)) {
			throw new IllegalArgumentException();
		}
		return grid[pos.getRow()][pos.getCol()];
	}
	
	public void setSquare(Position pos, Square square) {
		if (pos == null || square == null) {
			throw new IllegalArgumentException();
		}
		if (!isInBounds(pos)) {
			throw new IllegalArgumentException(); //exception de outofbounds
		}
		grid[pos.getRow()][pos.getCol()] = square;
	}
	
	public boolean isInBounds(Position pos) {
		return inBoundsCol(pos.getCol()) && inBoundsRow(pos.getRow());
	}
	
	private boolean inBoundsRow(int pos) {
		return (pos >= 0 && pos < rows);
	}

	private boolean inBoundsCol(int pos) {
		return (pos >= 0 && pos < columns);
	}
}
