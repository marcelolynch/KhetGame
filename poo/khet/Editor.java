package poo.khet;

import java.util.HashMap;
import java.util.Map;

import poo.khet.gameutils.Direction;
import poo.khet.gameutils.Position;

public class Editor implements CannonPositions {
	
	private Board board;
	
	public Editor () {
		board = new Board(getInitialSetup()); 
	}
	
	public Board getBoard() {
		return board;
	}
	
	public Map<Position, Piece> getBoardSetup() {
		return board.getPiecesPosition();
	}
	
	/**
	 * Valida que la posición esté ocupada por una pieza del equipo moviendo.
	 * @param pos - posición a validar
	 * @return <tt>true</tt> si se encuentra una pieza del equipo moviendo, <tt>false</tt> sino.
	 */
	public boolean isValidSelection(Position pos) {	
		if (!board.isInBounds(pos)) {
			return false;
		}
		
		if (board.isEmptyPosition(pos)) {
			return false;
		}
		
		return true;
	}
	
	public boolean isValidMove(Position init, Position dest) {	
		if (init == null || dest == null) {
			return false;
		}
		
		if (!isValidSelection(init)) {
			return false;
		}
		Piece p = board.getOccupantIn(init);
		
		return board.canPlace(p, dest);
	}
	
	public void move(Position init, Position dest) {
		
		if (!isValidMove(init, dest)) {
			throw new IllegalArgumentException("Movimiento inválido.");
		}
		
		Piece p = board.withdrawFrom(init);
		
		//TODO: corregir que no se pueda swappear en lugares reservados si la pieza no es del color
		if (!board.isEmptyPosition(dest)) { // hay swap
			board.placePiece(init, board.withdrawFrom(dest));
		}
		
		board.placePiece(dest, p);
	}
	
	public void rotate(Position pos, boolean clockwise) {

		if (!isValidSelection(pos)) {
			throw new IllegalArgumentException();
		}
		
		Piece p = board.getOccupantIn(pos);
		
		if (clockwise) {
			p.rotateClockwise();
		}
		else {
			p.rotateCounterClockwise();
		}
	}
	
	private Map<Position, Piece> getInitialSetup() {
		Map<Position, Piece> pMap= new HashMap<Position, Piece>();
		pMap.put(new Position(7,2), new Pyramid(Team.RED, Direction.WEST));
		pMap.put(new Position(7,3), new Anubis(Team.RED, Direction.NORTH));
		pMap.put(new Position(7,4), new Pharaoh(Team.RED));
		pMap.put(new Position(7,5), new Anubis(Team.RED, Direction.NORTH));
		pMap.put(new Position(6,7), new Pyramid(Team.RED, Direction.EAST));
		pMap.put(new Position(5,6), new Pyramid(Team.SILVER, Direction.EAST));
		pMap.put(new Position(4,0), new Pyramid(Team.SILVER, Direction.EAST));
		pMap.put(new Position(4,2), new Pyramid(Team.RED, Direction.WEST));
		pMap.put(new Position(4,7), new Pyramid(Team.SILVER, Direction.NORTH));
		pMap.put(new Position(4,9), new Pyramid(Team.RED, Direction.SOUTH));
		pMap.put(new Position(4,4), new Scarab(Team.RED, Direction.WEST));
		pMap.put(new Position(4,5), new Scarab(Team.RED, Direction.SOUTH));
		pMap.put(new Position(3,0), new Pyramid(Team.SILVER, Direction.NORTH));
		pMap.put(new Position(3,2), new Pyramid(Team.RED, Direction.SOUTH));
		pMap.put(new Position(3,7), new Pyramid(Team.SILVER, Direction.EAST));
		pMap.put(new Position(3,9), new Pyramid(Team.RED, Direction.WEST));
		pMap.put(new Position(3,4), new Scarab(Team.SILVER, Direction.NORTH));
		pMap.put(new Position(3,5), new Scarab(Team.SILVER, Direction.WEST));
		pMap.put(new Position(2,3), new Pyramid(Team.RED, Direction.WEST));
		pMap.put(new Position(1,2), new Pyramid(Team.SILVER, Direction.WEST));
		pMap.put(new Position(0,4), new Anubis(Team.SILVER, Direction.SOUTH));
		pMap.put(new Position(0,5), new Pharaoh(Team.SILVER));
		pMap.put(new Position(0,6), new Anubis(Team.SILVER, Direction.SOUTH));
		pMap.put(new Position(0,7), new Pyramid(Team.SILVER, Direction.EAST));
		
		return pMap;
	}
}