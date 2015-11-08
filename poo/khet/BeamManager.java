package poo.khet;

import java.util.ArrayList;
import java.util.List;

import poo.khet.gameutils.Position;
/**
 * {@code BeamManager} lleva a cabo el lanzamiento y seguimiento de <code>Beam</code>s sobre un tablero dado,
 * y su interacci&oacute;n con las piezas que se encuentren en el mismo.
 *
 * <p>Lleva un registro del recorrido que sigui&oacute; el &uacute;ltimo <code>Beam</code> manejado.
 * 
 * @see {@link Beam}
 */
public class BeamManager {
    private Board board;
    private List<Position> beamTrace = new ArrayList<>();

    /**
     * Construye un nuevo <code>BeamManager</code> que trabajar&aacute; sobre el tablero 
     * que se pasa por par&aacute;metro.
     * 
     * @param board - el tablero sobre el que se manejar&aacute;n los rayos
     */
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
     * Devuelve una lista con las posiciones que ocup&oacute; el ultimo rayo emitido
     * en su trayectoria por el tablero
     * 
     * @return el recorrido del último <code>Beam</code> lanzado.
     */
    public List<Position> getBeamTrace() {
        return beamTrace;
    }
    
    /**
     * Lanza el <code>Beam</code> al tablero y sigue su recorrido.
     * @param beam - el rayo a manejar
     * @param initialPosition - la primera posici&oacute;n del rayo en el tablero 
     * @return - el resultado obtenido en ese recorrido.
     * 
   	 * @see BeamFate
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
     * Calcula la siguiente {@link Position} que debe ocupar el rayo indicado, a partir de 
     * la posicion que se indica por par&aacute;metro
     * @param pos - la posicion desde la que se quiere calcular 
     * @param beam - el rayo sobre el que se realiza el calculo
     * 
     * @return la <code>Position</code> siguiente a la que debería ir el <code>Beam</code>
     */
    private Position nextBeamPosition(Position pos, Beam beam) {
        return pos.getPosInDirection(beam.getDirection());
    }
}
