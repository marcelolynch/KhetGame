package poo.khet;

import java.util.ArrayList;
import java.util.List;

import poo.khet.gameutils.Position;

public class BeamManager {
	private Board board;
	private List<Position> beamPath;

	BeamManager(Board board) {
		super();
		this.board = board;
		this.beamPath = new ArrayList<Position>();
	}
	

	
	Position getLastPos() {
		return beamPath.get(beamPath.size()-1);
	}
	
	public List<Position> getBeamPath() {
		return beamPath;
	}
	
	BeamAction throwBeam(Beam beam ,Position initialPosition) {
		System.out.println("IMMA FIRIN' MY LAZOR"); //TODO: Delete
		return manageBeamTravel(beam, initialPosition);
	}
	
	private BeamAction manageBeamTravel (Beam beam, Position initialPosition) {
		BeamAction beamAction = null;
		Position beamPos = initialPosition;
		
		while (beam.isActive()) {
			beamPos = nextBeamPosition(beamPos, beam);
			System.out.println("BEAMPOS: " + beamPos); //TODO: Delete
			beamPath.add(beamPos);
			if (!board.isInBounds(beamPos)) {
				beam.deactivate();
				beamAction = BeamAction.OUT_OF_BOUNDS;
				System.out.println("BEAM OUT OF BOUNDS"); //TODO: Delete
			} else if (!board.isEmptyPosition(beamPos)){
				if (board.getOccupantIn(beamPos).receiveBeam(beam)) {
					beamAction = BeamAction.WAS_CONTAINED;
					System.out.println("BEAM CONTAINED / REFLECTED"); //TODO: Delete
				} else {
					beamAction = BeamAction.DESTROYED_PIECE;
					System.out.println("BEAM DESTROYED PIECE"); //TODO: Delete

				}
			}
		}

		return beamAction;
	}
	
	private Position nextBeamPosition(Position pos, Beam beam) {
		return pos.getPosInDirection(beam.getDirection());
	}
}
