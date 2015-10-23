package poo.khet;

import java.util.Map;

import poo.khet.gameutils.Coordinate;
import poo.khet.gameutils.Position;

public class Game {
	
	private Board board;
	private BeamCannon redCannon;
	private BeamCannon silverCannon;
	
	//Ahora Game recibe lo mismo que se manda para construir board
	public Game (Map<Coordinate, Piece> piecesConfig) {
		board = new Board(piecesConfig); 
		redCannon = new BeamCannon(Team.RED);
		silverCannon = new BeamCannon(Team.SILVER);
	}
	
	//TODO: Graveyard
	public boolean move(Position init, Position dest) {
		if (!board.checkDistance(init,dest)) {
			return false;
		}
		Square initSquare = board.getPosition(init);
		Square destSquare = board.getPosition(dest);
		
		if (initSquare.isEmpty() || !((Piece)initSquare.getOccupant()).canMove(destSquare)) {
			return false;
		}
		
		if (destSquare.isEmpty()) {
			destSquare.setOccupant(initSquare.withdrawOccupant());
		} else { // Va a haber swap
			Piece swapped = ((Piece)destSquare.withdrawOccupant());
			destSquare.setOccupant(initSquare.withdrawOccupant());
			initSquare.setOccupant(swapped);
		}
		return true;
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
