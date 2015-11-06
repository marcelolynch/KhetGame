package poo.khet;

import java.util.ArrayList;
import java.util.List;

import poo.khet.gameutils.Position;

public class BeamManager {
    private Board board;
    private List<Position> beamTrace = new ArrayList<>();

    public BeamManager(Board board) {
        super();
        this.board = board;
    }

    public Position getLastPos() {
        return beamTrace.get(beamTrace.size() - 1);
    }

    public List<Position> getBeamTrace() {
        return beamTrace;
    }

    public BeamAction manageBeam(Beam beam, Position initialPosition) {
        BeamAction beamAction = null;
        Position beamPos = initialPosition;
        beamTrace.clear();

        while (beam.isActive()) {
            beamPos = nextBeamPosition(beamPos, beam);
            beamTrace.add(beamPos);
            if (!board.isInBounds(beamPos)) {
                beam.deactivate();
                beamAction = BeamAction.OUT_OF_BOUNDS;
            } else if (!board.isEmptyPosition(beamPos)) {
            	boolean survivedBeam = board.getOccupantIn(beamPos).receiveBeam(beam);
                if (survivedBeam) {
                    beamAction = BeamAction.WAS_CONTAINED;
                } else {
                    beamAction = BeamAction.DESTROYED_PIECE;
                }
            }
        }

        return beamAction;
    }

    private Position nextBeamPosition(Position pos, Beam beam) {
        return pos.getPosInDirection(beam.getDirection());
    }
}
