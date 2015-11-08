package graphics;

import java.io.IOException;
import poo.khet.Board;
import poo.khet.Game;
import poo.khet.GameState;
import poo.khet.FileManager;
import poo.khet.Team;
import poo.khet.AI.AIMover;
import poo.khet.gameutils.GameMode;
import poo.khet.gameutils.Position;

//TODO: cambiar la doc para hablar de los enums en vez de las constantes de error
/**
 * {@code GameManager} se encarga de mediar entre las acciones del usuario y el {@link Game} en
 * s&iacute;.<br>
 * Los estados del juego se diferencian en <b>elecci&oacute;n</b> y <b>acci&oacute;n</b>. La etapa
 * de <i>elecci&oacute;n</i> es en la que el equipo activo debe seleccionar la pieza que
 * accionar&aacute; en la etapa de <i>acci&oacute;n</i>, o un ca&ntilde;&oacute;n a switchear. En la
 * etapa de <i>acci&oacute;n</i> se comunica la casilla destino de la pieza seleccionada, o un
 * sentido de rotacion de la misma.
 * 
 * Las elecciones del usuario se comunican mediante {@link Position}s, que corresponderan a
 * posiciones del tablero o de los ca&ntilde;ones, mediante el metodo {@link #handle(Position)}, o
 * bien una rotaci&oacute;n mediante {@link #handlePosition(boolean clockwise)}
 * 
 * @see {@link Position}
 */
public class GameManager {
    enum Stage {
        CHOICE, ACTION, STANDBY
    }

    /**
     * El juego a manejar
     */
    private Game game;
 
    
    /**
     * La etapa en que se encuentra el turno: <i>CHOICE</i> corresponder&aacute; a esperar
     * una elecci&oacute;n del usuario (selecci&oacute;n de pieza o switcheo del ca&ntilde;on,
     * en la etapa de <i>ACTION</i> se espera una decision de movimiento o rotacion de la pieza
     * previamente seleccionada, y la etapa de <i>STANDBY</i> existe justo antes de que se accione
     * el movimiento de la inteligencia artificial (esperando un input antes de ejecutarla).
     */
    private Stage stage;
    
    /**
     * Indica, en la etapa de <i>ACTION</i>, la posici&oacute;n de la pieza seleccionada por el usuario,
     * que ser&aacute; la que mover&aacute; o rotar&aacute; con la siguiente decisi&oacute;n
     */
    private Position activeSquare;
    
    
    /**
     * El modo de juego: dos jugadores, o jugador contra la inteligencia artificial
     * @see GameMode
     */
    private GameMode mode;

    /**
     * Utilidad para dibujar el tablero
     */
    private GameDrawer gameDrawer;
    
    /**
     * La inteligencia artificial, encargada de ejecutar movimientos
     * entre los turnos del jugador humano.
     */
    private AIMover AI;

    // TODO: excepciones
    /**
     * Construye un nuevo GameManager a partir de un archivo GameState cuyo nombre se pasa
     * como par&aacute;metro.
     * 
     * @param name - El nombre del archivo de configuracion a cargar
     * @throws ClassNotFoundException //TODO: ??????
     * @throws IOException - de no encontrarse el archivo
     * @see GameState
     */
    public GameManager(String name) throws ClassNotFoundException, IOException {
        game = new Game(FileManager.loadGameSave(name));
        setManager();
    }
    
    
    /**
     * Construye un nuevo GameManager a partir de un archivo que guarde una disposici&oacute;n inicial
     * de las piezas, y un modo de juego. El juego comienza asi desde cero con esa disposici&oacute;n inicial.
     * @param name - el nombre del archivo donde se guardo esa configuracion
     * @param mode - modo de juego
     * @throws ClassNotFoundException
     * @throws IOException
     * @see GameMode
     */
    public GameManager(String name, GameMode mode) throws ClassNotFoundException, IOException {
    	game = new Game(FileManager.loadBoardSetup(name), mode);
    	setManager();
    }
    
    /**
     * Setea las configuraciones iniciales generales
     */
    private void setManager() {
        stage = Stage.CHOICE;
        gameDrawer = new GameDrawer(game);
        mode = game.getGameMode();
        if (mode == GameMode.PVE) {
        	AI = new AIMover(game);
        }
    }

    
    /**
     * Devuelve el GameDrawer correspondiente al juego que se esta manejando
     * @return El <i>drawer</i> del juego
     * 
     * @see GameDrawer
     */
    GameDrawer getDrawer() {
        return this.gameDrawer;
    }


    /**
     * Indica el equipo activo en el turno actual (<code>SILVER</code> o <code>RED</code>)
     * 
     * @return El equipo activo (<code>Team.SILVER, Team.RED</code>)
     */
    public Team currentTeam() {
        return game.getMovingTeam();
    }


    /**
     * Devuelve la instancia del turno en el que se encuentra el juego
     * 
     * @return <code>Stage.CHOICE</code>, de estar en etapa de seleccion de pieza o bien
     *         <code>Stage.ACTION</code>, si se debe elegir la accion (movimiento o rotacion)
     */
    public Stage currentStage() {
        return this.stage;
    }

    /**
     * Devuelve una referencia al tablero del juego
     * 
     * @return El tablero correspondiente
     */
    public Board getBoard() {
        return game.getBoard();
    }


    /**
     * Cambia la etapa de turno que se esta manejando
     * 
     * @param newStage - la nueva etapa
     */
    private void setStage(Stage newStage) {
        if (newStage == null) {
            throw new IllegalArgumentException("null parameter");
        }
        stage = newStage;
    }


    /**
     * Devuelve la <code>Position</code> que corresponde a la casilla seleccionada en la etapa de
     * <code>CHOICE</code>
     * 
     * @throws IllegalStateException - Si no existe ninguna posicion seleccionada en este momento
     *         (nuevo turno)
     * @return - la posicion de la casilla activa
     */
    public Position getActiveSquare() {
        if (activeSquare == null) {
            throw new IllegalStateException("No active square at this Stage");
        }
        return activeSquare;
    }


    /**
     * El m&aacute;todo <code>handle</code> ejecuta la siguiente acci&oacute;n del juego segun la
     * posici&oacute;n seleccionada por el usuario para manejar.
     * <p>
     * Si el turno esta en etapa de selecci&oacute;n (<code>Stage.CHOICE</code>) y se activa una
     * posici&oacute;n correspondiente a una pieza, se selecciona la misma para manejar en la
     * siguiente llamada, y la etapa cambia a la de acci&oacute;n.<br>
     * Si se activa una posici&oacute;n de ca&ntilde;&oacute;n (del equipo que esta moviendo), se
     * alterna su orientaci&oacute;n y cambia el turno.
     * <p>
     * 
     * Si el turno esta en etapa de acci&oacute;n (inmediatamente despues de la elecci&oacute;), la
     * posici&oacute;n que se pasa se asume como la posici&oacute;n de la casilla de destino de la
     * pieza seleccionada. En caso de ser una posici&oacute;n de destino v&aacute;lida, se ejecuta
     * el movimiento y la siguiente llamada al metodo esperara una elecci&oacute;n de pieza (del
     * siguiente equipo)
     * <p>
     * 
     * Si la posicion recibida no corresponde a una posicion valida para procesar, no se hace nada y
     * se indica el error mediante el valor de retorno
     * 
     * @param position - la posicion a ser interpretada y manejada
     * @return Se devuelven distintas {@link ManagerResponseCodes} seg&uacute;n si la posici&oacute;n
     *         cambi&oacute; el estado del juego o no:
     *         <p>
     * 
     * 
     *         Se retorna <code><i>INVALID_TEAM_SELECTED</i></code> si correspondia elegir la pieza,
     *         y no se envi&oacute; una posici&oacute;n que correspondiera a una pieza de ese
     *         equipo.<br>
     *         Se retorna <code><i>INVALID_MOVE_SELECTED</i></code> si correspond&iacute;a elegir la
     *         posici&oacute;n destino de la pieza seleccionada, y no se env&iacute;o una correcta.
     *         <br>
     *         Se retorna <code><i>OK</i></code> si la acci&oacute;n fue procesada correctamente.
     * 
     * @see  ManagerResponseCodes
     */
    public ManagerResponseCodes handle(Position position) {
        if (position == null) {
            throw new IllegalArgumentException("null parameter"); 
        }
        
        if (currentStage() == Stage.STANDBY) {
        	AI.makeMove();
        	game.nextTurn();
        	setStage(Stage.CHOICE);
        }
        else if (currentStage() == Stage.ACTION) {
            if (game.isValidMove(activeSquare, position)) {
                System.out.println("MOVING PIECE");
                game.move(activeSquare, position);
                nextTurn();
                System.out.println(currentStage());
            } else {
                System.out.println("INVALID MOVE SELECT");
                return ManagerResponseCodes.INVALID_MOVE_SELECTED;
            }
        } else if (game.isCannonPosition(position) && game.isSwitchable(position)) {
            game.switchCannon();
            nextTurn();
        } else {
            if (game.isValidSelection(position)) {
                activeSquare = position;
                setStage(Stage.ACTION);
            } else {
                return ManagerResponseCodes.INVALID_TEAM_SELECTED;
            }
        }

        return ManagerResponseCodes.OK;
    }

    /**
     * Si corresponde a la etapa del juego (acci&oacute;n), rota la pieza (previamente
     * seleccionada). Si no corresponde, no hace nada. Se indica si cambio el estado del juego
     * mediante el valor de retorno.
     * 
     * @param clockwise - debe ser <code><b>true</b></code> si se quiere efectuar una rotacion
     *        horaria, <code><b>false</b></code> en caso contrario.
     * @return <code><i>OK</i></code> si se efectu&oacute; la rotaci&oacute;n, <code></i>
     *         CANT_ROTATE_RIGHT_NOW<code></i> en caso contrario.
     * @see ManagerResponseCodes
     */
    public ManagerResponseCodes handleRotation(boolean clockwise) {
        if (currentStage() == Stage.ACTION) {
            game.rotate(activeSquare, clockwise);
            nextTurn();
            return ManagerResponseCodes.OK;
        }
        return ManagerResponseCodes.CANT_ROTATE_RIGHT_NOW;
    }

    /**
     * Indica si la etapa es de elecci&oacute;n de pieza (o ca&ntilde;&oacute;n a rotar)
     * 
     * @return - <code>true</code> si la etapa es de elecci&oacute;n, <code>false</code> en caso
     *         contrario
     * @see GameManager#handle(Position)
     */
    
    public boolean isChoosing() {
        return currentStage() == Stage.CHOICE;
    }


    /**
     * Indica si ya hay una pieza seleccionada - Se esta esperando una acci&oacute;n (una llamada a {@link #handle(Position)})
     * @return <code>true</code> si la etapa es de acci&oacute;n - <code>false</code> en caso contrario
     * @see GameManager#handle(Position)
     */
	public boolean isChosen() {
		return currentStage() == Stage.ACTION;
	}

	
	/**
	 * Indica si el GameManager esta en standby, es decir, esperando una llamada a handle()
	 * para ejecutar la proxima jugada del jugador automatico (esto solo tiene sentido para
	 * partidas contra la inteligencia artificial, y existe por claridad). Se est&aacute;
	 * esperando una llamada a {@link GameManager#handle(Position)} para hacerlo.
	 * 
	 * @return <code>true</code> si se esta en standby, <code>false</code> de lo contrario
	 * @see GameManager#handle(Position)
	 */
	public boolean inStandby() {
		return currentStage() == Stage.STANDBY;
	}

    
    /**
     * Devuelve el turno a la etapa inicial y deselecciona la pieza. El equipo que estaba jugando
     * debe volver a elegir una pieza antes de efectuar una acci&oacute;n.
     */
    public void resetTurn() {
        activeSquare = null;
        setStage(Stage.CHOICE);
    }
    
    
    /**
     * Cambia el turno: se espera ahora una <i>elecci&oacute;n</i> del equipo opuesto al que
     * estaba jugando hasta ahora. 
     */
    private void nextTurn() {
        game.nextTurn();
        resetTurn();
        if (mode == GameMode.PVE) {
        	setStage(Stage.STANDBY); //Voy a esperar un input cualquiera antes de ejecutar el proximo turno
        }
    }

    
    /**
     * Indica si el juego ya tiene un ganador o si todavia esta en curso (no hay ganador)
     * @return <code>true</code> si el juego ya termino, y hay un ganador, </code>false</code> si el juego sigue activo.
     */
    public boolean hasWinner() {
        return game.hasWinner();
    }

    
    /**
     * Devuelve el equipo ganador del juego, una vez terminado el mismo
     * @return - el equipo ganador
     * @throws IllegalStateException - si el juego todav&iacute;a no termino (no existe ganador)
     */
    public Team getWinnerTeam() throws IllegalStateException {
        return game.getWinnerTeam();
    }

        
    // TODO: excepciones
    /**
     * Guarda el juego en un archivo con el nombre especificado en el par&aacute;metro <b>name</b>
     * @param name - el nombre del archivo a guardar
     * @throws IOException - de haber un error en la escritura del archivo
     * 
     * @see GameState
     */
    public void saveGame(String name) throws IOException {
        FileManager.writeGameFile(name, game.getGameState());
    }


}
