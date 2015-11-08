package poo.khet;

import java.util.List;
import java.util.Map;

import poo.khet.gameutils.GameMode;
import poo.khet.gameutils.Position;

public class Game implements CannonPositions {

    private Board board;
    private RedCannon redCannon;
    private SilverCannon silverCannon;
    private BeamManager beamManager;
    private Team movingTeam;
    private GameMode mode;
    private Team winnerTeam;

    public Game(Map<Position, Piece> initialBoardSetup, GameMode gameMode) {
    	board = new Board(initialBoardSetup);
    	beamManager = new BeamManager(board);
    	redCannon = new RedCannon();
    	silverCannon = new SilverCannon();
    	movingTeam = Team.SILVER; //siempre empieza Silver
    	mode = gameMode;
    }
    
    public Game(GameState state) {
        board = new Board(state.getBoardConfig());
        beamManager = new BeamManager(board);
        redCannon = state.getRedCannon();
        silverCannon = state.getSilverCannon();
        movingTeam = state.getMovingTeam();
        mode = state.getGameMode();
    }
    
    public Board getBoard() {
        return board;
    }

    public GameMode getGameMode() {
        return mode;
    }

    public GameState getGameState() {
        return new GameState(mode, board.getPiecesPosition(), getMovingTeam(), redCannon,
                silverCannon);
    }

    /**
     * Valida si el <code>Game</code> terminó, verificando si existe algun ganador.
     * @throws IllegalStateException si el juego terminó
     */
    private void assertGameInProgress() {
        if (hasWinner()) {
            throw new IllegalStateException("Illegal operation: game has ended");
        }
    }

    /**
     * Valida que la posición esté ocupada por una pieza del equipo moviendo.
     * 
     * @param pos - posición a validar
     * @return <tt>true</tt> si se encuentra una pieza del equipo moviendo, <tt>false</tt> sino.
     */
    public boolean isValidSelection(Position pos) {
        assertGameInProgress();

        if (hasWinner()) {
            return false;
        }

        if (!board.isInBounds(pos)) {
            return false;
        }

        if (board.isEmptyPosition(pos)) {
            return false;
        }

        Team pieceTeam = board.getOccupantIn(pos).getTeam();

        if (!pieceTeam.equals(movingTeam)) {
            return false;
        }

        return true;
    }

    /**
     * Verifica si un {@link poo.khet.AI.Move} es válido a partir de una <code>Position</code> inicial
     * y una final.
     * @param init <code>Position</code> inicial del movimiento
     * @param dest <code>Position</code> final del movimiento
     * @return <tt>true</tt> si es lícito realizar el movimiento; <tt>false</tt> en caso contrario.
     */
    public boolean isValidMove(Position init, Position dest) {
        assertGameInProgress();

        if (init == null || dest == null) {
            return false;
        }

        if (!isValidSelection(init)) {
            return false;
        }

        if (!init.isAdjacent(dest)) {
            return false;
        }

        Piece p = board.getOccupantIn(init);

        return board.canPlace(p, dest); 
        
    }

    /**
     * Mueve una {@link Piece} desde una {@link Position} a otra.
     * <p>
     * Si la <code>Position</code> de destino no tiene ningún ocupante, se coloca la pieza.
     * <p>
     * En caso de que la posicion de destino este ocupada por otra <code>Piece</code>
     * se intercambian sus posiciones.
     * @param init <code>Position</code> inicial del movimiento
     * @param dest <code>Position</code> final del movimiento
     * @throws IllegalArgumentException si las posiciones entregadas no representan un movimiento valido
     * @see Board
     * @see Position
     */
    public void move(Position init, Position dest) {
        assertGameInProgress();

        if (!isValidMove(init, dest)) {
            throw new IllegalArgumentException("Movimiento inválido.");
        }

        Piece p = board.withdrawFrom(init);

        if (!board.isEmptyPosition(dest)) { // hay swap
            board.placePiece(init, board.withdrawFrom(dest));
        }

        board.placePiece(dest, p);
    }

    public void rotate(Position pos, boolean clockwise) {
        assertGameInProgress();

        if (!isValidSelection(pos)) {
            throw new IllegalArgumentException();
        }

        Piece p = board.getOccupantIn(pos);

        if (clockwise) {
            p.rotateClockwise();
        } else {
            p.rotateCounterClockwise();
        }
    }

    /**
     * Devuelve el {@link Team} que esta jugando actualmente.
     * @return equipo al que corresponde el turno actual.
     */
    public Team getMovingTeam() {
        assertGameInProgress();

        return movingTeam;
    }

    /**
     * Retorna el {@link BeamCannon} del {@link Team} deseado.
     * @param team equipo del cual se desea obtener el cañón
     * @return el <code>BeamCannon</code>
     */
    public BeamCannon getBeamCannon(Team team) {
        if (team == Team.RED) {
            return redCannon;
        }
        if (team == Team.SILVER) {
            return silverCannon;
        }
        throw new IllegalArgumentException();
    }

    public void nextTurn() {
        assertGameInProgress();

        BeamFate beamFate = throwBeam(getMovingTeam());
        if (beamFate == BeamFate.IMPACTED_PIECE) {
            Piece withdrawn = board.withdrawFrom(beamManager.getLastPos());

            if (withdrawn instanceof Pharaoh) { // TODO: instanceof justificado
                winnerTeam = (withdrawn.getTeam() == Team.SILVER ? Team.RED : Team.SILVER);
            }
        }
        if (!hasWinner()) {
            changePlayer();
        }
    }

    /**
     * Emite un {@link Beam} desde el {@link BeamCannon} correspondiente a un {@link Team}.
     * @param team equipo que desea lanzar el rayo
     * @return un {@link BeamFate} con el destino del rayo
     */
    private BeamFate throwBeam(Team team) {
        BeamCannon cannon = getBeamCannon(team);
        Beam beam = cannon.generateBeam();

        Position startingPosition = team == Team.RED ? RED_CANNON_POSITION : SILVER_CANNON_POSITION;

        return beamManager.manageBeam(beam, startingPosition);
    }

    /**
     * Cambia el equipo que juega actualmente.
     * @see Team
     */
    private void changePlayer() {
        movingTeam = (movingTeam == Team.SILVER ? Team.RED : Team.SILVER);
    }

    public void switchCannon() {
        assertGameInProgress();

        BeamCannon current = getMovingTeam() == Team.SILVER ? silverCannon : redCannon;
        current.switchFacing();
    }

    public boolean isSwitchable(Position position) {
        assertGameInProgress();

        if (!isCannonPosition(position)) {
            throw new IllegalArgumentException("Posición inválida");
        }
        if (getMovingTeam() == Team.SILVER) {
            return position.equals(SILVER_CANNON_POSITION);
        } else {
            return position.equals(RED_CANNON_POSITION);
        }
    }

    /**
     * Consulta si la {@link Position} es aquella que corresponde a un {@link BeamCannon}.
     * @param position <code>Position</code> a consultar
     * @return <tt>true</tt> si la posicion corresponde al lugar de un cañón, <tt>false</tt>
     * en caso contrario
     */
    public boolean isCannonPosition(Position position) {
        return position.equals(RED_CANNON_POSITION) || position.equals(SILVER_CANNON_POSITION);
    }

    /**
     * Devuelve el recorrido de un {@link Beam} en forma de una Lista de <code>Position</code>s.
     * @return Lista que representa el recorrido de un rayo
     */
    public List<Position> getBeamTrace() {
        return beamManager.getBeamTrace();
    }

    /**
     * Consulta si hay un {@link Team} ganador.
     * @return <tt>true</tt> si hay un equipo ganador; <tt>false</tt> en caso contrario
     */
    public boolean hasWinner() {
        return winnerTeam != null;
    }

    /**
     * Devuelve el {@link Team} ganador en caso de que lo haya.
     * @return el <code>Team</code> ganador
     * @throws IllegalStateException si no hay ningun ganador.
     */
    public Team getWinnerTeam() {
        if (!hasWinner()) {
            throw new IllegalStateException();
        }
        return this.winnerTeam;
    }

}
