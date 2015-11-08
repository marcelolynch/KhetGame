package poo.khet;

import java.util.ArrayList;
import java.util.List;

import poo.khet.gameutils.Position;
/**
 * {@code BeamManager} lleva a cabo el lanzamiento de un <code>Beam</code> en un tablero dado, indiferentemente del equipo que lo genere.
 *
 * Lleva un registro del recorrido que siguió éste <code>Beam</code>
 * 
 * 
 * @see {@link Beam}
 */
public class BeamManager {
    private Board board;
    private List<Position> beamTrace = new ArrayList<>();

    public BeamManager(Board board) {
        super();
        this.board = board;
    }
   
    /**
     * 
     * @return la última la <code>Position</code> por la que paso el último <code>Beam</code> lanzado.
     */
    public Position getLastPos() {
        return beamTrace.get(beamTrace.size() - 1);
    }
    
    /**
     * 
     * @return el recorrido del último <code>Beam</code> beam lanzado.
     */
    
    public List<Position> getBeamTrace() {
        return beamTrace;
    }
    /**
     * Lanza el <code>Beam</code> beam y sigue su recorrido.
     * @param beam
     * @param initialPosition
     * @return El resultado obtenido en ese recorrido.
     */
    public BeamFate manageBeam(Beam beam, Position initialPosition) {
        BeamFate beamAction = null;
        Position beamPos = initialPosition;
        beamTrace.clear();

        while (beam.isActive()) {
            beamPos = nextBeamPosition(beamPos, beam);
            beamTrace.add(beamPos);
            if (!board.isInBounds(beamPos)) {
                beam.deactivate();
                beamAction = BeamFate.OUT_OF_BOUNDS;
            } else if (!board.isEmptyPosition(beamPos)) {
            	boolean survivedBeam = board.getOccupantIn(beamPos).receiveBeam(beam);
                if (survivedBeam) {
                    beamAction = BeamFate.WAS_CONTAINED;
                } else {
                    beamAction = BeamFate.IMPACTED_PIECE;
                }
            }
        }

        return beamAction;
    }
    /**
     *  
     * @param pos
     * @param beam
     * @return la <code>Position</code> siguiente a la que debería ir el <code>Beam</code> .
     */
    private Position nextBeamPosition(Position pos, Beam beam) {
        return pos.getPosInDirection(beam.getDirection());
    }
}
