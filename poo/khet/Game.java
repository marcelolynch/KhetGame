package poo.khet;

import java.util.List;
import java.util.Map;

import poo.khet.gameutils.GameMode;
import poo.khet.gameutils.Position;

//TODO: JavaDoc de la clase

/**
 * La clase <code>Game</code> modela el estado puntual y el desarrollo de un juego de Khet en curso.
 * Maneja los movimientos efectuados en cada turno sobre el tablero, la ejecuci&oacute;n de los ca&ntilde;ones 
 * <p>Antes de realizar cualquier acci&oacute;n se debe consultar si la misma es v&aacute;lida. La progresi&oacute;n de un turno
 * esta dada por
 * <ul>
 * <li>Movimiento o rotacion de alguna pieza, o cambio en la direccion del ca&ntilde;&oacute;n, del equipo correspondiente</li>
 * <li>Llamada a cambio de turno, que dispara el rayo correspondiente y altera el estado del tablero</li>
 * </ul>
 * <p>Se realizan validaciones de estado a la hora de ejecutar los m&eacute;todos, de manera tal que no se puede
 * violar este orden del turno sin provocar una excepci&oacute;n
 * 
 * <p><code>Game</code> provee una serie de m&eacute;todos de consulta del estado actual del juego y de la legitimidad de
 * acciones a realizar, de manera tal que una llamada incorrecta a un m&eacute;todo de acci&oacute;n 
 * (como {@link #move()}, o {@link #rotate(Position, boolean)}) resulta en una {@link IllegalStateException}.
 * 
 */
public class Game implements CannonPositions {

    /**
     * Tablero de Juego
     */
    private Board board;

    /**
     * Ca&ntilde;on del equipo <i>RED</i>
     */
    private RedCannon redCannon;

    /**
     * Ca&ntilde;on del equipo <i>SILVER</i>
     */
    private SilverCannon silverCannon;
    private BeamManager beamManager;

    /**
     * Equipo a quien le corresponde el turno actual
     */
    private Team movingTeam;

    /**
     * Modo de juego actual <i>PVE</i> o <i>PVP</i>
     */
    private GameMode mode;

    /**
     * Equipo ganador
     */
    private Team winnerTeam;
    
    
    /**
     * Indica si es el momento de llamar a nextTurn(), es decir, si
     * ya se realizo una accion
     */
    private boolean turnEnded;

    /**
     * Crea un juego a partir de un mapa que contiene las posiciones de las piezas
     * y un modo de juego.
     * @param initialBoardSetup mapa con las <code>Position</code> de las piezas
     * @param gameMode modo de juego <i>PVE</i> o <i>PVP</i>
     */
    public Game(Map<Position, Piece> initialBoardSetup, GameMode gameMode) {
    	board = new Board(initialBoardSetup);
    	beamManager = new BeamManager(board);
    	redCannon = new RedCannon();
    	silverCannon = new SilverCannon();
    	movingTeam = Team.SILVER; //siempre empieza Silver
    	mode = gameMode;
    }

    /**
     * Crea un juego a partir de un {@link GameState} creado previamente.
     * @param state
     */
    public Game(GameState state) {
        board = new Board(state.getBoardConfig());
        beamManager = new BeamManager(board);
        redCannon = state.getRedCannon();
        silverCannon = state.getSilverCannon();
        movingTeam = state.getMovingTeam();
        mode = state.getGameMode();
    }

    /**
     * Devuelve el {@link Board} del juego
     * @return el tablero
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Devuelve el modo de juego <i>PVE</i> o <i>PVP</i>
     * @return modo de juego
     */
    public GameMode getGameMode() {
        return mode;
    }

    /**
     * Crea un nuevo {@link GameState} con informaci&oacute;n actual del <code>Game</code>.
     * @return el estado de juego
     */
    public GameState getGameState() {
        return new GameState(mode, board.getPiecesPosition(), getMovingTeam(), redCannon,
                silverCannon);
    }

 
    /**
     * Valida que la posici&oacute;n est&eacute; ocupada por una pieza del equipo moviendo.
     * 
     * @param pos - posici&oacute;n a validar
     * @return <tt>true</tt> si se encuentra una pieza del equipo moviendo, <tt>false</tt> sino.
     */
    public boolean isValidSelection(Position pos) {
        assertGameInProgress();
        
        if (pos == null) {
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
     * Verifica si un movimiento es v&aacute;lido a partir de una <code>Position</code> inicial
     * y una final.
     * @param init <code>Position</code> inicial del movimiento
     * @param dest <code>Position</code> final del movimiento
     * @return <tt>true</tt> si es l&iacute;cito realizar el movimiento; <tt>false</tt> en caso contrario.
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
        assertInTurn();

        if (!isValidMove(init, dest)) {
            throw new IllegalArgumentException("Movimiento invalido.");
        }

        Piece p = board.withdrawFrom(init);

        if (!board.isEmptyPosition(dest)) { // hay swap
            board.placePiece(init, board.withdrawFrom(dest));
        }

        board.placePiece(dest, p);
        turnEnded = true; // Listo para nextTurn()
    }
    
	/**
     * Rota la {@link Piece} que se encuentra en la {@link Position] dada en sentido horario
     * si clockwise es true, antihorario sino.
     * @param pos - <code>Position</code> de la <code>Piece</code> a rotar
     * @param clockwise - indica si se rotara en sentido horario
     * @throws IllegalArgumentException si la posicion entregada es invalida
     */
    public void rotate(Position pos, boolean clockwise) {
        assertGameInProgress();
        assertInTurn();

        if (!isValidSelection(pos)) {
            throw new IllegalArgumentException("Posici&oacute;n invalida");
        }

        Piece p = board.getOccupantIn(pos);

        if (clockwise) {
            p.rotateClockwise();
        } else {
            p.rotateCounterClockwise();
        }
        turnEnded = true; // Listo para nextTurn()
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
     * @param team equipo del cual se desea obtener el ca&ntilde;&oacute;n
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

    /**
     * Representa el cambio de turno.
     * <p>
     * Lanza el rayo y en caso de que &eacute;ste haya impactado de forma letal contra una 
     * {@link Piece} la retira del juego. Si la <code>Piece</code> es un {@link Pharaoh},
     * se termina el juego y se declara ganador al <code>Team</code> con el <code>Pharaoh</code>
     * en pie. Consultar con {@link #hasWinner()} y {@link #getWinnerTeam()}.
     * <p>
     * Si ambos <code>Pharaoh</code> est&aacute;n en pie se cambia de equipo, es decir que
     * le corresponder&aacute; realizar un movimiento al equipo contrario que al que haya
     * realizado el &uacute;ltimo movimiento.
     */
    public void nextTurn() {
        assertGameInProgress();
        assertTurnEnded();
        
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
    
        turnEnded = false;
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
     * Rota el ca&ntilde;&oacute; del equipo que juega actualmente.
     */
    public void switchCannon(Team team) {
        assertGameInProgress();
        assertInTurn();
        
        if (!isSwitchable(team)) {
        	throw new IllegalArgumentException("Not current team cannon");
        }
        BeamCannon current = getMovingTeam() == Team.SILVER ? silverCannon : redCannon;
        current.switchFacing();
        
        turnEnded = true; //Listo para nextTurn()
    }

    /**
     * Consulta si el {@link BeamCannon} del equipo indicado puede ser accionado, 
     * es decir si corresponde al equipo jugando actualmente.
     * @param team - el equipo del ca&ntilde;&oacute;n a rotar
     * @return <code>true</code> si el ca&ntilde;$oacute;n es rotable, <code>false</code> si no.
     */
    public boolean isSwitchable(Team team) {
        assertGameInProgress();
        return getMovingTeam().equals(team); 
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
     * @see #hasWinner()
     */
    public Team getWinnerTeam() {
        if (!hasWinner()) {
            throw new IllegalStateException();
        }
        return this.winnerTeam;
    }
    
    
    //Metodos privados
    
    /**
     * Cambia el equipo que juega actualmente.
     * @see Team
     */
    private void changePlayer() {
        movingTeam = (movingTeam == Team.SILVER ? Team.RED : Team.SILVER);
    }


    /**
     * Valida si el <code>Game</code> termin&oacute;, verificando si existe algun ganador.
     * @throws IllegalStateException si el juego termin&oacute;
     */
    private void assertGameInProgress() throws IllegalStateException {
        if (hasWinner()) {
            throw new IllegalStateException("Illegal operation: game has ended");
        }
    }

    
    /**
     * Verifica si todav&iacute;a se espera una acci&oacute;n en el turno,
     * y de no ser as&iacute; (el turno ha terminado), lanza una excepci&oacute;n
     */
    private void assertInTurn() throws IllegalStateException {
        if(turnEnded){
        	throw new IllegalStateException("Illegal operation: turn already ended. Must nextTurn() first");
        }
		
	}
    
    /**
     * Verifica si el turno ha terminado (ya se ejecut&oacute; una acci&oacute;n);
     * de no ser as&iacute;, se lanza una excepci&oacute;n
     * 
     */
    private void assertTurnEnded() throws IllegalStateException {
        if(!turnEnded){
        	throw new IllegalStateException("Must make a move, rotation or switch first");
        }
		
	}

    
}
