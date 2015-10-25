package graphics;

import java.util.HashMap;
import java.util.Map;

import poo.khet.Anubis;
import poo.khet.Board;
import poo.khet.Game;
import poo.khet.Pharaoh;
import poo.khet.Piece;
import poo.khet.Pyramid;
import poo.khet.Scarab;
import poo.khet.Team;
import poo.khet.gameutils.Direction;
import poo.khet.gameutils.Position;

public class GameManager2 implements ErrorConstants {
	enum Stage{ CHOICE, ACTION }  
	
	private Game game;
	private Stage stage;
	private Position activeSquare;
	
	GameManager2(Game game){
		Map<Position, Piece> pMap= new HashMap<Position, Piece>();
		pMap.put(new Position(7,2), new Pyramid(Team.RED, Direction.WEST));
		pMap.put(new Position(7,3), new Anubis(Team.RED, Direction.NORTH));
		pMap.put(new Position(7,4), new Pharaoh(Team.RED));
		pMap.put(new Position(7,5), new Anubis(Team.RED, Direction.NORTH));

		pMap.put(new Position(6,7), new Pyramid(Team.RED, Direction.EAST));

		pMap.put(new Position(5,6), new Pyramid(Team.SILVER, Direction.SOUTH));

		pMap.put(new Position(4,0), new Pyramid(Team.SILVER, Direction.EAST));
		pMap.put(new Position(4,2), new Pyramid(Team.RED, Direction.WEST));
		pMap.put(new Position(4,7), new Pyramid(Team.SILVER, Direction.NORTH));
		pMap.put(new Position(4,9), new Pyramid(Team.RED, Direction.SOUTH));
		pMap.put(new Position(4,4), new Scarab(Team.RED, Direction.WEST));
		pMap.put(new Position(4,5), new Scarab(Team.RED, Direction.SOUTH));
		
		pMap.put(new Position(3,0), new Pyramid(Team.SILVER, Direction.NORTH));
		pMap.put(new Position(3,2), new Pyramid(Team.RED, Direction.SOUTH));
		pMap.put(new Position(3,7), new Pyramid(Team.SILVER, Direction.EAST));
		pMap.put(new Position(3,9), new Pyramid(Team.RED, Direction.WEST));
		pMap.put(new Position(3,4), new Scarab(Team.SILVER, Direction.NORTH));
		pMap.put(new Position(3,5), new Scarab(Team.SILVER, Direction.WEST));

		pMap.put(new Position(2,3), new Pyramid(Team.RED, Direction.WEST));
	
		pMap.put(new Position(1,2), new Pyramid(Team.SILVER, Direction.WEST));

		pMap.put(new Position(0,4), new Anubis(Team.SILVER, Direction.SOUTH));
		pMap.put(new Position(0,5), new Pharaoh(Team.SILVER));
		pMap.put(new Position(0,6), new Anubis(Team.SILVER, Direction.SOUTH));
		pMap.put(new Position(0,7), new Pyramid(Team.SILVER, Direction.SOUTH));
		
		stage = Stage.CHOICE;
		this.game = new Game(pMap);
	}

	Team currentTeam(){
		return game.getMovingTeam();
	}
	
	
	Stage currentStage(){
		return this.stage;
	}
	
	Board getBoard(){
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
				game.throwBeam(currentTeam());
				resetTurn();
				System.out.println(currentStage());
			} 
			else {
				System.out.println("INVALID MOVE SELECT");
				return INVALID_MOVE_SELECTED;
			}
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
			game.throwBeam(currentTeam());
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
	
}