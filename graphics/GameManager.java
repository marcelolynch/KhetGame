package graphics;

import java.io.FileNotFoundException;
import java.io.IOException;

import com.sun.javafx.geom.transform.CanTransformVec3d;

import jdk.internal.org.objectweb.asm.Handle;
import poo.khet.Board;
import poo.khet.Game;
import poo.khet.FileManager;
import poo.khet.Team;
import poo.khet.gameutils.Position;


/**
 * {@code GameManager} se encarga de mediar entre las acciones del usuario y el {@link Game} en s&iacute;.<br>
 * Los estados del juego se diferencian en <b>elecci&oacute;n</b> y <b>acci&oacute;n</b>. La etapa de <i>elecci&oacute;n</i>
 * es en la que el equipo activo debe seleccionar la pieza que accionar&aacute; en la etapa de <i>acci&oacute;n</i>,
 * o un ca&ntilde;&oacute;n a switchear. En la etapa de <i>acci&oacute;n</i> se comunica la casilla destino de la pieza seleccionada,
 * o un sentido de rotacion de la misma.
 * 
 * Las elecciones del usuario se comunican mediante {@link Position}s, que corresponderan a posiciones del tablero o de los ca&ntilde;ones,
 * mediante el metodo {@code handle(Position)}, o bien una rotaci&oacute;n mediante {@code handlePosition(boolean clockwise)}
 * 
 * @author Marcelo
 * @see {@link Position} 
 */
public class GameManager implements ErrorConstants {
	enum Stage{ CHOICE, ACTION }  
	private Game game;
	private Stage stage;
	private Position activeSquare;
	private GameDrawer gameDrawer;
	
	//TODO: excepciones
	/**
	 * Construye un nuevo GameManager a partir de un archivo de configuracion 
	 * cuyo nombre se pasa como par&aacute;metro.
	 * 
	 * @param name - El nombre del archivo de configuracion a cargar
	 * @throws ClassNotFoundException //TODO: ??????
	 * @throws IOException - de no encontrarse el archivo 
	 */
	public GameManager(String name) throws ClassNotFoundException, IOException{	
		stage = Stage.CHOICE;
		
		game = new Game(FileManager.loadGameFile(name));
		gameDrawer = new GameDrawer(game);

	}

	
	
	GameDrawer getDrawer(){
		return this.gameDrawer;
	}	
	
	
	/**
	 * Indica el equipo activo en el turno actual (<code>SILVER</code> o <code>RED</code>)
	 * @return El equipo activo (<code>Team.SILVER, Team.RED</code>)
	 */
	public Team currentTeam(){
		return game.getMovingTeam();
	}
	
	
	/**
	 * Devuelve la instancia del turno en el que se encuentra el juego
	 * @return <code>Stage.CHOICE</code>, de estar en etapa de seleccion de pieza
	 * 			o bien <code>Stage.ACTION</code>, si se debe elegir la accion (movimiento o rotacion)
	 */
	public Stage currentStage(){
		return this.stage;
	}
	
	/**
	 * Devuelve una referencia al tablero del juego
	 * @return El tablero correspondiente
	 */
	public Board getBoard(){
		return game.getBoard();
	}
	
	
	/**
	 * Cambia la etapa de turno que se esta manejando
	 * @param newStage - la nueva etapa
	 */
	private void setStage(Stage newStage){
		if(newStage == null){
			throw new IllegalArgumentException("null parameter");
		}
		stage = newStage;
	}
	
	
	/**
	 * Devuelve la <code>Position</code> que corresponde a la casilla seleccionada en la etapa de <code>CHOICE</code>
	 * @throws IllegalStateException - Si no existe ninguna posicion seleccionada en este momento (nuevo turno)
	 * @return - la posicion de la casilla activa
	 */
	public Position getActiveSquare() {
		if(activeSquare == null){
			throw new IllegalStateException("No active square at this Stage");
		}
		return activeSquare;
	}

	
	/**
	 * El m&aacute;todo <code>handle</code> ejecuta la siguiente acci&oacute;n del juego segun la posici&oacute;n
	 * seleccionada por el usuario para manejar.<p>
	 * Si el turno esta en etapa de selecci&oacute;n (<code>Stage.CHOICE</code>) y se activa una posici&oacute;n
	 * correspondiente a una pieza, se selecciona la misma para manejar en la siguiente llamada, y la etapa
	 * cambia a la de acci&oacute;n.<br>
	 * Si se activa una posici&oacute;n de ca&ntilde;&oacute;n (del equipo que esta moviendo), se alterna su orientaci&oacute;n y 
	 * cambia el turno.<p>
	 * 
	 * Si el turno esta en etapa de acci&oacute;n (inmediatamente despues de la elecci&oacute;), la posici&oacute;n que se pasa
	 * se asume como la posici&oacute;n de la casilla de destino de la pieza seleccionada. 
	 * En caso de ser una posici&oacute;n de destino v&aacute;lida, se ejecuta el movimiento 
	 * y la siguiente llamada al metodo esperara una elecci&oacute;n de pieza (del siguiente equipo)<p>
	 * 
	 * Si la posicion recibida no corresponde a una posicion valida para procesar, no se hace nada y se indica
	 * el error mediante el valor de retorno
	 * 
	 * @param position - la posicion a ser interpretada y manejada
	 * @return Se devuelven distintas {@link ErrorConstants} seg&uacute;n si la posici&oacute;n cambi&oacute; el estado del juego o no:<p>
	 * 			
	 * 
	 * 			Se retorna <code><i>INVALID_TEAM_SELECTED</i></code> si correspondia elegir la pieza,
	 * 				y no se envi&oacute; una posici&oacute;n que correspondiera a una pieza de ese equipo.<br>
	 * 			Se retorna <code><i>INVALID_MOVE_SELECTED</i></code> si correspond&iacute;a elegir la posici&oacute;n destino de la pieza seleccionada,
	 * 				y no se env&iacute;o una correcta.<br>
	 * 			Se retorna <code><i>OK</i></code> si la acci&oacute;n fue procesada correctamente.
	 * 
	 * @see {@link ErrorConstants}
	 */
	public int handle(Position position){
		if(position == null){
			throw new IllegalArgumentException("null parameter"); //TODO: Dejar que tire el NullPointer?
		}		
		
		if (currentStage() == Stage.ACTION) {
			if (game.isValidMove(activeSquare, position)) {
				System.out.println("MOVING PIECE");
				game.move(activeSquare, position);
				nextTurn();
				System.out.println(currentStage());
			} 
			else {
				System.out.println("INVALID MOVE SELECT");
				return INVALID_MOVE_SELECTED;
			}
		} 
		else if(game.isCannonPosition(position) && game.isSwitchable(position)){
			game.switchCannon();	
			nextTurn();
		}
		else{
			if (game.isValidSelection(position)){
			activeSquare = position;
			setStage(Stage.ACTION);
			}
			else{
				return INVALID_TEAM_SELECTED;
			}
		}
		
		return OK;
	}

	/**
	 * Si corresponde a la etapa del juego (acci&oacute;n), rota la pieza (previamente seleccionada).
	 * Si no corresponde, no hace nada. Se indica si cambio el estado del juego mediante el valor de retorno.
	 * 
	 * @param clockwise - debe ser <code><b>true</b></code> si se quiere efectuar una rotacion horaria, <code><b>false</b></code> en caso contrario.
	 * @return <code><i>OK</i></code> si se efectu&oacute; la rotaci&oacute;n, <code></i>CANT_ROTATE_RIGHT_NOW<code></i> en caso contrario.
	 * @see {@link ErrorConstants}
	 */
	public int handleRotation(boolean clockwise){
		if (currentStage() == Stage.ACTION) {
			game.rotate(activeSquare, clockwise);
			nextTurn();
			return OK;
		}
		return CANT_ROTATE_RIGHT_NOW;
	}
	
	/**
	 * Indica si la etapa es de eleccion de pieza (o ca&ntilde;&oacute;n a rotar)
	 * @return - <code>true</code> si la etapa es de elecci&oacute;n, <code>false</code> en caso contrario
	 */
	public boolean isChoosing(){
		return currentStage() == Stage.CHOICE;
	}
	
	
	/**
	 * Devuelve el turno a la etapa inicial y deselecciona la pieza.
	 * El equipo que estaba jugando debe volver a elegir una pieza antes de 
	 * efectuar una acci&oacute;n.
	 */
	public void resetTurn() {
		activeSquare = null;
		setStage(Stage.CHOICE);
	}

	
	//TODO: excepciones
	public void saveGame(String name) throws FileNotFoundException, IOException {	
		FileManager.writeGameFile(name, game.getGameState());
	}
	
	public boolean hasWinner() {
		return game.hasWinner();
	}
	
	public Team getWinnerTeam() {
		return game.getWinnerTeam();
	}
	
	//TODO: si es PVE que mueva la compu
	private void nextTurn() {
		game.nextTurn();
		resetTurn();
//		if (game.getGameMode() == GameMode.PVE) {
//			AIMover.makeMove();
//		}
		
	}
	
}
