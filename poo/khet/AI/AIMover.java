package poo.khet.AI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import poo.khet.Beam;
import poo.khet.BeamAction;
import poo.khet.BeamCannon;
import poo.khet.BeamManager;
import poo.khet.Board;
import poo.khet.CannonPositions;
import poo.khet.Game;
import poo.khet.Team;
import poo.khet.gameutils.BoardDimensions;
import poo.khet.gameutils.Position;

/**
 * AIMover es la clase encargada de generar y efectuar movimientos hechos por la computadora. Tiene
 * una referencia al {@link Game} actual. Para decidir la jugada simula movimientos posibles sobre
 * un tablero auxiliar (sin modificar el tablero del juego) y aplica diversos criterios para
 * determinar la mejor opcion. Realiza la jugada sobre el juego que tiene referenciado, y luego
 * cambia de turno.
 *
 */
public class AIMover implements CannonPositions, BoardDimensions {
    private Game game;
    static Team team = Team.RED; // Siempre juega con el equipo Rojo
    private BeamManager beamManager;
    private Board auxiliarBoard;

    public AIMover(Game game) {
        this.game = game;
    }

    /**
     * Se encarga de simular todas las jugadas posibles que tiene el jugador rojo, y elegir
     * aleatoriamente una en la que pueda destruir una pieza rival. En caso de no poder hacerlo,
     * elige al azar otra jugada en la que no se destruya una pieza propia. Realiza la jugada en el
     * Game dado, y termina el turno.
     */
    public void makeMove() {
        auxiliarBoard = new Board(game.getBoard().getPiecesPosition());
        beamManager = new BeamManager(auxiliarBoard);
        List<Action> possibleActions = new ArrayList<>();
        possibleActions.addAll(possibleMoves());
        possibleActions.addAll(possibleRotations());
        Action destroyChoice = null;
        Action secondChoice = null;
        Collections.shuffle(possibleActions);

        for (Action action : possibleActions) {
            BeamAction beamFate = simulateAction(action);
            if (beamFate == BeamAction.DESTROYED_PIECE
                    && isOpponentPiece(beamManager.getLastPos())) {
                destroyChoice = action;
            } else if (beamFate != BeamAction.DESTROYED_PIECE) {
                secondChoice = action;
                // Guarda una jugada aleatoria en caso de que no se pueda destruir una ficha rival
            }

            // Revierte la jugada simulada para seguir simulando otras
            Action restore = action.getRevertedAction(action);
            restore.executeActionIn(auxiliarBoard);
        }
        if (destroyChoice != null) {
            destroyChoice.updateGame(game);
        } else if (secondChoice != null) {
            secondChoice.updateGame(game);
        } else {
            possibleActions.get(0).updateGame(game);
        }
        game.nextTurn();// No lo tendria que llamar el gameManager a esto?
    }

    /**
     * Cheque si en la posicion dada hay una pieza del SILVER
     * 
     * @param pos posicion de la cual se quiere obtener la pieza
     * @return <tt>true</tt> si la pieza es del SILVER <ff>false</ff> si la pieza es del RED
     * 
     */
    private boolean isOpponentPiece(Position pos) {
        // pos isEmpty?
        return auxiliarBoard.getOccupantIn(pos).getTeam().equals(Team.SILVER);
    }

    /**
     * Ejecuta la <code>Action</code> dada como parametro en <code>auxiliarBoard</code>, y luego
     * Genera un nuevo <code>Beam</code> desde el cañon rojo y devuelve el efecto que tuvo el
     * <code>Beam</code> en dicho tablero.
     * 
     * @param action
     * @return BeamAction efecto del Beam en el tablero
     * @see BeamManager
     */
    private BeamAction simulateAction(Action action) {
        action.executeActionIn(auxiliarBoard);
        BeamCannon cannon = game.getBeamCannon(team);
        Beam beam = cannon.generateBeam();
        Position startingPosition = RED_CANNON_POSITION;
        return beamManager.manageBeam(beam, startingPosition);
    }

    /**
     * Genera una lista con todos los movimientos posibles y validos (no las rotaciones), de piezas
     * del jugador actual.
     * 
     * @return List<Action> - Movimientos (<code>Move</code>) validos para cada pieza del jugador
     *         actual.
     */
    private List<Action> possibleMoves() {
        List<Action> possibleMoves = new ArrayList<Action>();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Position from = new Position(i, j);
                if (game.isValidSelection(from)) {
                    for (Position to : getAdjacentPositions(from)) {
                        if (game.isValidMove(from, to)) {
                            possibleMoves.add(new Move(from, to));
                        }
                    }
                }
            }
        }
        return possibleMoves;
    }

    /**
     * Genera una lista con todas las posiciones, dentro de los limites del tablero, adyacentes a la
     * posicion dada como parametro
     * 
     * @param start - Posicion de referencia para sacar las posiciones adyacentes
     * @return List<Position> - Posiciones adyacentes dentro del tablero
     * @see Board
     */
    private List<Position> getAdjacentPositions(Position start) {
        List<Position> ends = new ArrayList<>();
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Position end = new Position(start.getRow() + i, start.getCol() + j);
                if (auxiliarBoard.isInBounds(end)) {
                    ends.add(end);
                }
            }
        }
        return ends;
    }

    /**
     * Genera una lista con todas las rotaciones posibles en cada pieza del jugador actual.
     * 
     * @return List<Action> - Rotaciones (<code>Rotation</code>) CW y CCW.
     */
    private List<Action> possibleRotations() {
        List<Action> possibleRotations = new ArrayList<Action>();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Position start = new Position(i, j);
                if (game.isValidSelection(start)) {
                    // Rotacion Clockwise y Counterclockwise
                    possibleRotations.add(new Rotation(start, true));
                    possibleRotations.add(new Rotation(start, false));
                }
            }
        }
        return possibleRotations;
    }
}
