package poo.khet;

import java.util.Map;

import poo.khet.gameutils.Coordinate;
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
	
	// TODO: constantes de error
	public boolean isValidMove(Position init, Position dest) {
		if (init == null || dest == null) {
			return false;
		}
		
		// hay un checkDistance en board que hace las dos sig validaciones. ¿Mejor hacerlas así
		// o que las haga el board?
		if (!board.isInBounds(init) || !board.isInBounds(dest)) {
			return false;
		}
		
		if (!init.isAdjacent(dest)) {
			return false;
		}
		
		if (board.isEmptyPosition(init)) {
			return false;
		}
		
		Piece p = board.getOccupantIn(init);
		
		if (!p.getTeam().equals(movingTeam)) {
			return false;
		}
		
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
	
	void rotateClockwise (Position position) {
		board.getOccupantIn(position).rotateClockwise();
	}
	
	void rotateCounterClockwise (Position position) {
		board.getOccupantIn(position).rotateCounterClockwise();
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

	//TODO: TODO
	public void rotate(Position activeSquare, boolean clockwise) {
		return;
	}

}
