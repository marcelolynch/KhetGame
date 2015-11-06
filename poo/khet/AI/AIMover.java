package poo.khet.AI;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

public class AIMover implements CannonPositions, BoardDimensions {
    private Game game;
    static Team team = Team.RED;
    private Random brain = new Random();
    private BeamManager beamManager;
    private Board auxiliarBoard;

    public AIMover(Game game) {
        this.game = game;
    }

    /**
     * Se encarga de elegir la mejor jugada posible, con un criterio establecido, y de realizarla,
     * luego llama a game para que finalize el turno
     */
    public void makeMove() {
    	auxiliarBoard = new Board(game.getBoard().getPiecesPosition());
        beamManager = new BeamManager(auxiliarBoard);
        List<Action> possibleMoves = possibleMoves();
        possibleMoves.addAll(possibleRotations());
        Action secondChoice = null;
        boolean foundSecondChoice = false;
        for (Action action : possibleMoves) {
            BeamAction beamFate = simulateMove(action);
            if (beamFate == BeamAction.DESTROYED_PIECE && 
            		auxiliarBoard.getOccupantIn(beamManager.getLastPos()).getTeam().equals(Team.SILVER)) {
                action.updateGame(game);
                game.nextTurn();
                return;
            } else if (!foundSecondChoice && beamFate != BeamAction.DESTROYED_PIECE) {
                secondChoice = action;
                foundSecondChoice = true;
            }
            Action restore = action.getRevertedAction(action);
            restore.executeActionIn(auxiliarBoard);
        }
        if (foundSecondChoice) {
            secondChoice.updateGame(game);
        } else {
            Action finalChoice = possibleMoves.get(brain.nextInt(possibleMoves.size() - 1));
            finalChoice.updateGame(game);
        }
        game.nextTurn();
    }

    /**
     * Realiza la accion en el tablero auxiliar y lanza el rayo en �l
     * 
     * @param action
     * @return
     */
    private BeamAction simulateMove(Action action) {
        action.executeActionIn(auxiliarBoard);
        BeamCannon cannon = game.getBeamCannon(team);
        Beam beam = cannon.generateBeam();
        Position startingPosition = RED_CANNON_POSITION;
        return beamManager.manageBeam(beam, startingPosition);
    }



    private List<Action> possibleMoves() {
        List<Action> possibleMoves = new ArrayList<Action>();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Position start = new Position(i, j);
                Position end = getRandomEndInBounds(start);

                if (game.isValidSelection(start) && game.isValidMove(start, end)) {
                    possibleMoves.add(new Move(start, end));
                }
            }
        }
        return possibleMoves;
    }

    private Position getRandomEndInBounds(Position start) {
        Position end = getRandomEnd(start);
        while (!auxiliarBoard.isInBounds(end)) {
            end = getRandomEnd(start);
        }
        return end;
    }

    /**
     * 
     * @param start
     * @return una posici�n aleatoria adyacente a �sta, pues nextInt(3) me da un n�mero entre 0 y 2,
     *         y al restarle 1, me da uno entre -1 y 1.
     */
    private Position getRandomEnd(Position start) {
        return new Position(start.getRow() + brain.nextInt(3) - 1,
                start.getCol() + brain.nextInt(3) - 1);
    }


    private List<Action> possibleRotations() {
        List<Action> possibleRotations = new ArrayList<Action>();

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Position start = new Position(i, j);
                if (game.isValidSelection(start)) {
                    possibleRotations.add(randomRotation(start));
                }
            }
        }
        return possibleRotations;
    }

    /**
     * Rota de manera aleatoria la pieza ubicada en la posici�n pos del tablero.
     * 
     * @param start
     * @return una rotacion
     */
    private Rotation randomRotation(Position pos) {
        boolean clockwise = brain.nextInt() % 2 == 0; // de �sta forma hay un 50% de probabilidad
                                                       // que rote de forma horaria y 50% que rote
                                                       // de forma antihoraria
        return new Rotation(pos, clockwise);
    }


}
