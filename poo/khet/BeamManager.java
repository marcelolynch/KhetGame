package poo.khet;

import java.util.ArrayList;
import java.util.List;

import poo.khet.gameutils.Position;

public class BeamManager {
	private Beam beam;
	private Board board;
	private List<Position> beamPath;

	BeamManager(Beam beam, Board board) {
		super();
		this.beam = beam;
		this.board = board;
		this.beamPath = new ArrayList<>();
	}
	
	Beam getBeam() {
		return beam;
	}
	
	Position getLastPos() {
		return beamPath.get(beamPath.size()-1);
	}
	
	public List<Position> getBeamPath() {
		return beamPath;
	}
	
	BeamAction throwBeam(Team team) {
		// podriamos pasarle la posicion del cannon para no tener que pasarle Game o algo asi
		Position initialBeamPos = board.getCannonPosition(team);
		initialBeamPos.getPosInDirection(beam.getDirection());
		// Lo mueve para que no este en el mismo casillero que Cannon
		return manageBeamTravel(beam, initialBeamPos);
	}
	
	BeamAction manageBeamTravel (Beam beam, Position initialPosition) {
		BeamAction beamAction = null;
		Position beamPos = initialPosition;
		
		while (beam.isActive()) {
			beamPath.add(beamPos);
			if (!board.isInBounds(beamPos)) {
				beam.deactivate();
				beamAction = BeamAction.OUT_OF_BOUNDS;
			} else {
				if (!board.isEmptyPosition(beamPos)) {
					if (board.getOccupantIn(beamPos).receiveBeam(beam)) {
						beamAction = BeamAction.WAS_CONTAINED;
					} else {
						beamAction = BeamAction.DESTROYED_PIECE;
					}
				}
				if (beam.isActive()) {
					beamPos = nextBeamPosition(beamPos, beam);
				}
			}
		}

		return beamAction;
	}
	
	private Position nextBeamPosition(Position pos, Beam beam) {
		pos.getPosInDirection(beam.getDirection());
		return pos;
	}
		
	
}
