package graphics;

import java.io.FileNotFoundException;
import java.io.IOException;
import poo.khet.Board;
import poo.khet.Editor;
import poo.khet.FileManager;
import poo.khet.gameutils.Position;

public class EditorManager {
	enum Stage{ CHOICE, ACTION }  
	
	private Editor editor;
	private Stage stage;
	private Position activeSquare;
	private GameDrawer gameDrawer;
	
	public EditorManager() {	
		stage = Stage.CHOICE;
		
		editor = new Editor();
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

	public GameMessages handle(Position position){
		if(position == null){
			throw new IllegalArgumentException("null parameter");
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
				return GameMessages.INVALID_MOVE_SELECTED;
			}
		} 
		else if (editor.isValidSelection(position)){
			activeSquare = position;
			setStage(Stage.ACTION);
		}
		
		return GameMessages.OK;
	}

	public GameMessages handleRotation(boolean clockwise){
		if (currentStage() == Stage.ACTION) {
			editor.rotate(activeSquare, clockwise);
			resetTurn();
			return GameMessages.OK;
		}
		return GameMessages.CANT_ROTATE_RIGHT_NOW;
	}
	
	/**
	 * Indica si la etapa es de eleccion de piezas (por claridad)
	 * @return
	 */
	public boolean isChosen(){
		return currentStage() == Stage.ACTION;
	}
	
	public void resetTurn() {
		activeSquare = null;
		setStage(Stage.CHOICE);
	}
	
	//TODO: excepciones
	public void saveGame(String name) throws FileNotFoundException, IOException {	
		FileManager.writeGameFile(name, editor.getBoardSetup());
	}
}