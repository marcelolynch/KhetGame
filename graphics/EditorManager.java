package graphics;

import java.io.FileNotFoundException;
import java.io.IOException;
import poo.khet.Board;
import poo.khet.Editor;
import poo.khet.FileManager;
import poo.khet.gameutils.Position;

public class EditorManager implements ErrorConstants {
	enum Stage{ CHOICE, ACTION }  
	
	private Editor editor;
	private Stage stage;
	private Position activeSquare;
	private GameDrawer gameDrawer;
	
	//TODO: excepciones
	public EditorManager(String name) throws ClassNotFoundException, IOException{	
		stage = Stage.CHOICE;
		
		editor = new Editor(FileManager.loadGameFile(name));
		gameDrawer = new GameDrawer(editor);
	}

	GameDrawer getDrawer(){
		return this.gameDrawer;
	}	
	
	public Stage currentStage(){
		return this.stage;
	}
	
	public Board getBoard(){
		return editor.getBoard();
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
			if (editor.isValidMove(activeSquare, position)) {
				System.out.println("MOVING PIECE");
				editor.move(activeSquare, position);
				resetTurn();
				System.out.println(currentStage());
			} 
			else {
				System.out.println("INVALID MOVE SELECT");
				return INVALID_MOVE_SELECTED;
			}
		} 
		else if(editor.isCannonPosition(position)){
			//TODO: no anda
			editor.switchCannon();	
			resetTurn();
		}
		else if (editor.isValidSelection(position)){
			activeSquare = position;
			setStage(Stage.ACTION);
		}
		
		return OK;
	}

	public int handleRotation(boolean clockwise){
		if (currentStage() == Stage.ACTION) {
			editor.rotate(activeSquare, clockwise);
			resetTurn();
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
		FileManager.writeGameFile(name, editor.getGameState());
	}

	
}