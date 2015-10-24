package poo.khet;

import java.util.Map;

import poo.khet.gameutils.Position;

public class Game {
	
	private Board board;
	private BeamCannon redCannon;
	private BeamCannon silverCannon;
	private Team movingTeam;
	
	//Ahora Game recibe lo mismo que se manda para construir board
	public Game (Map<Position, Piece> piecesConfig) {
		board = new Board(piecesConfig); 
		redCannon = new BeamCannon(Team.RED);
		silverCannon = new BeamCannon(Team.SILVER);
		movingTeam = Team.SILVER; // Siempre comienza SILVER
	}
	
	/**
	 * Valida que la posición esté ocupada por una pieza del equipo moviendo.
	 * @param pos - posición a validar
	 * @return <tt>true</tt> si se encuentra una pieza del equipo moviendo, <tt>false</tt> sino.
	 */
	public boolean isValidSelection(Position pos) { // la terminé haciendo para poder validar rotaciones
		if (pos == null) {
			return false;
		}
		
		if (!board.isInBounds(pos)) {
			return false;
		}
		
		if (board.isEmptyPosition(pos)) {
			return false;
		}
		
		Piece p = board.getOccupantIn(pos);
		
		if (!p.getTeam().equals(movingTeam)) {
			return false;
		}
		
		return true;
	}
	
	// TODO: constantes de error
	public boolean isValidMove(Position init, Position dest) {
		if (init == null || dest == null) {
			return false;
		}
		
		if (!isValidSelection(init)) {
			return false;
		}
		
		if (!init.isAdjacent(dest)) {
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
	
	BeamCannon getBeamCannon(Team team) {
		if (team == Team.RED)
			return redCannon;
		if (team == Team.SILVER)
			return silverCannon;
		throw new IllegalArgumentException();
	}
	
	Board throwBeam(Team team) {
		BeamCannon cannon = getBeamCannon(team);
		Beam beam = cannon.generateBeam();
		BeamManager beamManager=new BeamManager(beam,board);
		Position death=beamManager.throwBeam(team);
		if(death != null) {
			board.withdrawFrom(death);
		}
	}	
	
	/*
		Coordinate initialBeamCoord = board.getCannonPosition(team);
		
		initialBeamCoord.changeInDirection(beam.getDirection());
		// Lo mueve para que no este en el mismo casillero que Cannon
		manageBeamTravel(beam, initialBeamCoord);
	}
	
	void manageBeamTravel (Beam beam, Coordinate initialPosition) {
		Coordinate beamCoord = initialPosition;
		while (beam.isActive()) {
			if (board.isInBounds(beamCoord)) {
				beam.deactivate();
			} else {
				Square currentPos = board.getPosition(beamCoord);
				if (!currentPos.isEmpty()) { 
					boolean survivedBeam = currentPos.getOccupant().receiveBeam(beam);
					if (!survivedBeam) {
						currentPos.withdrawOccupant();
					}
				}
				beamCoord = nextBeamPosition(beamCoord, beam);
			}
		}
	}
	
	private Coordinate nextBeamPosition(Coordinate coord, Beam beam) {
		coord.changeInDirection(beam.getDirection());
		return coord;
	}
	*/
	


	public Board getBoard() {
		return board;
	}


	public boolean canItMove(Piece selected, Position position) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
