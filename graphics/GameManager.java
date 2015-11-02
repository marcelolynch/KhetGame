package graphics;

import java.io.FileNotFoundException;
import java.io.IOException;
import poo.khet.Board;
import poo.khet.Game;
import poo.khet.FileManager;
import poo.khet.Team;
import poo.khet.gameutils.Position;

public class GameManager implements ErrorConstants {
	enum Stage{ CHOICE, ACTION }  
	
	private Game game;
	private Stage stage;
	private Position activeSquare;
	private GameDrawer gameDrawer;
	
	//TODO: excepciones
	public GameManager(String name) throws ClassNotFoundException, IOException{	
		stage = Stage.CHOICE;
		
		game = new Game(FileManager.loadGameFile(name));
		gameDrawer = new GameDrawer(game);
	}

	GameDrawer getDrawer(){
		return this.gameDrawer;
	}	
	public Team currentTeam(){
		return game.getMovingTeam();
	}
	
	public Stage currentStage(){
		return this.stage;
	}
	
	public Board getBoard(){
		return game.getBoard();
	}
	
	private void setStage(Stage newStage){
		if(newStage == null){
			throw new IllegalArgumentException("null parameter");
		}
		stage = newStage;
	}
	
	public Position getActiveSquare() {
		return activeSquare;
	}

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
		else if (game.isValidSelection(position)){
			activeSquare = position;
			setStage(Stage.ACTION);
		}
		
		return OK;
	}

	public int handleRotation(boolean clockwise){
		if (currentStage() == Stage.ACTION) {
			game.rotate(activeSquare, clockwise);
			nextTurn();
			return OK;
		}
		return CANT_ROTATE_RIGHT_NOW;
	}
	
	/**
	 * Indica si la etapa es de eleccion de piezas (por claridad)
	 * @return
	 */
	public boolean isChoosing(){
		return currentStage() == Stage.CHOICE;
	}
	
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
