package poo.khet;

import java.util.List;
import poo.khet.gameutils.GameMode;
import poo.khet.gameutils.Position;

public class Editor implements CannonPositions {
	
	private Board board;
	private BeamCannon redCannon;
	private BeamCannon silverCannon;
	private BeamManager beamManager;
	
	//Puede empezar siempre con lo mismo
	public Editor (GameState setup) {
		board = new Board(setup.getBoardConfig()); 
		beamManager = new BeamManager(board);
		redCannon = setup.getRedCannon();
		silverCannon = setup.getSilverCannon();
	}
	
	public Board getBoard() {
		return board;
	}
	
	public GameState getGameState() {		
		return new GameState(GameMode.PVP, board.getPiecesPosition(), Team.SILVER, redCannon, silverCannon);
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
	
	public BeamCannon getBeamCannon(Team team) {
		if (team == Team.RED) {
			return redCannon;
		}
		if (team == Team.SILVER) {
			return silverCannon;
		}
		throw new IllegalArgumentException();
	}

	public void nextTurn() {
	}
	
	public void switchCannon() {
		//BeamCannon current = getMovingTeam()==Team.SILVER ? silverCannon : redCannon;
		//current.switchFacing();
	}
	
	public boolean isCannonPosition(Position position) {
		return position.equals(RED_CANNON_POSITION) || position.equals(SILVER_CANNON_POSITION);
	}
	
	public List<Position> getLastBeamTrace() {
		return beamManager.getBeamTrace();
	}
}