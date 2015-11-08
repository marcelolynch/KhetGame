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

    // Algo as� -Chelo
    // Tendria que ser llamado en casi todos lados. Creo que esta bien
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

    public Team getMovingTeam() {
        assertGameInProgress();

        return movingTeam;
    }

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

    private BeamFate throwBeam(Team team) {
        BeamCannon cannon = getBeamCannon(team);
        Beam beam = cannon.generateBeam();

        Position startingPosition = team == Team.RED ? RED_CANNON_POSITION : SILVER_CANNON_POSITION;

        return beamManager.manageBeam(beam, startingPosition);
    }


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

    public boolean isCannonPosition(Position position) {
        return position.equals(RED_CANNON_POSITION) || position.equals(SILVER_CANNON_POSITION);
    }

    public List<Position> getBeamTrace() {
        return beamManager.getBeamTrace();
    }

    public boolean hasWinner() {
        return winnerTeam != null;
    }

    public Team getWinnerTeam() {
        if (!hasWinner()) {
            throw new IllegalStateException();
        }
        return this.winnerTeam;
    }

}
