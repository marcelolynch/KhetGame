package poo.khet;

import java.util.ArrayList;
import java.util.List;

import poo.khet.gameutils.Position;

public class BeamManager {
	private Board board;
	private List<Position> beamTrace = new ArrayList<>();

	BeamManager(Board board) {
		super();
		this.board = board;
	}
	
	Position getLastPos() {
		return beamTrace.get(beamTrace.size()-1);
	}
	
	public List<Position> getBeamTrace() {
		return beamTrace;
	}
	
	
	public BeamAction manageBeam (Beam beam, Position initialPosition) {
		BeamAction beamAction = null;
		Position beamPos = initialPosition;
		beamTrace.clear(); 
		
		while (beam.isActive()) {
			beamPos = nextBeamPosition(beamPos, beam);
			System.out.println("BEAMPOS: " + beamPos); //TODO: Delete
			beamTrace.add(beamPos);
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
