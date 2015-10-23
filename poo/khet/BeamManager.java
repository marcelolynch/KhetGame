package poo.khet;

import poo.khet.gameutils.Position;

public class BeamManager {
	private Beam beam;
	private Board board;
	public Beam getBeam() {
		return beam;
	}
	public void setBeam(Beam beam) {
		this.beam = beam;
	}
	public Board getBoard() {
		return board;
	}
	public void setBoard(Board board) {
		this.board = board;
	}
	public BeamManager(Beam beam, Board board) {
		super();
		this.beam = beam;
		this.board = board;
	}
	
	/**
	 * Es horrible devolver Coordinate pero por ahora no se me ocurrio otra cosa, lo vemos
	 * @param team
	 */
	Position throwBeam(Team team){
		Position initialBeamPos = board.getCannonPosition(team);
		initialBeamPos.getPosInDirection(beam.getDirection());
		// Lo mueve para que no este en el mismo casillero que Cannon
		return manageBeamTravel(beam, initialBeamPos);
	}
	
	Position manageBeamTravel (Beam beam, Position initialPosition) {
		Position beamPos = initialPosition;
		boolean survivedBeam=true;
		while (beam.isActive()) {
			if (board.isInBounds(beamPos)) {
				beam.deactivate();
			} else {
				Square currentPos = board.getPosition(beamPos);
				if (!currentPos.isEmpty()) { 
					survivedBeam = currentPos.getOccupant().receiveBeam(beam);		
				}
				if(beam.isActive()){
					beamPos = nextBeamPosition(beamPos, beam);
				}
			}
		}
		if (!survivedBeam) {
			return beamPos;
		}
		return null;
	}
	
	private Position nextBeamPosition(Position pos, Beam beam) {
		pos.getPosInDirection(beam.getDirection());
		return pos;
	}
		
	
}
