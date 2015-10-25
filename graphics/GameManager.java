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

public class GameManager implements ErrorConstants{
	enum Stage{ RED_CHOICE, SILVER_CHOICE, RED_ACTION, SILVER_ACTION }  
	
	private Game game;
	private Stage stage;
	private Position activeSquare;
	
	GameManager(Game game){
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
		
		stage = Stage.SILVER_CHOICE;
		this.game = new Game(pMap);
	}

	Team currentTeam(){
		if(stage == Stage.RED_CHOICE || stage == Stage.RED_ACTION)
			return Team.RED;
		else
			return Team.SILVER;
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
	
//	public boolean handle(Position position){
//		System.out.println("Manejando la coordenada (" + position.getRow() + ", " + position.getCol() + ")");
//		return false;
//	}
	/**
	 * Recibe una posicion activada por el usuario; de ser posible
	 * intenta aplicar la operacion (movimiento, seleccion) correspondiente
	 * a la etapa del juego en curso.
	 * @param position - posicion del tablero activada
	 * @param dummy
	 * @return
	 */
	public int handle(Position position){
		if(position == null){
			throw new IllegalArgumentException("null parameter"); //TODO: Dejar que tire el NullPointer?
		}
		
		if(isChoosing() && game.getBoard().isEmptyPosition(position)){
			return OK; //Selecciono casilla vacia - no pasa nada
		}
		
		if(currentStage() == Stage.RED_CHOICE){
			Piece activePiece = game.getBoard().getOccupantIn(position);
			if(activePiece.getTeam() != Team.RED){
				System.out.println("INVALID TEAM: SILVER");
				return INVALID_TEAM_SELECTED;
			}
			else{
				activeSquare = position;
				setStage(Stage.RED_ACTION);
				System.out.println(currentStage());
			}
		}
		
		else if(currentStage() == Stage.SILVER_CHOICE){
			Piece activePiece = game.getBoard().getOccupantIn(position);
			if(activePiece.getTeam() != Team.SILVER){
				System.out.println("INVALID TEAM: RED");
				return INVALID_TEAM_SELECTED;
			}
			else{
				activeSquare = position;
				System.out.println("SILVER_ACTION");
				setStage(Stage.SILVER_ACTION);
			}
		}

		else{
			Piece selected = game.getBoard().getOccupantIn(activeSquare);
			if(currentStage() == Stage.SILVER_ACTION && selected.getTeam() != Team.SILVER
					|| currentStage() == Stage.RED_ACTION && selected.getTeam() != Team.RED)
				return INVALID_ACTION_SELECTED; 
			
			if(game.isValidMove(activeSquare, position)){
				System.out.println("MOVING PIECE");
				game.move(activeSquare, position);
				activeSquare = null; //Back to square 1
				game.throwBeam(currentTeam());
				setStage(currentStage() == Stage.SILVER_ACTION ? Stage.RED_CHOICE : Stage.SILVER_CHOICE);
				System.out.println(currentStage());
			}
			else{
				System.out.println("INVALID MOVE SELECT");
				return INVALID_MOVE_SELECTED;
			}
		}

	return OK;
	}
	
	
	public int handleRotation(boolean clockwise){
		if(isChoosing()){
			return CANT_ROTATE_RIGHT_NOW;
		}
		else{
			game.rotate(activeSquare, clockwise);
			game.throwBeam(currentTeam());
			Stage newStage = currentStage() == Stage.RED_ACTION ? Stage.SILVER_CHOICE : Stage.RED_CHOICE;
			setStage(newStage);
			return OK;
		}
	}
	
	/**
	 * Indica si la etapa es de eleccion de piezas (por claridad)
	 * @return
	 */
	public boolean isChoosing(){
		return currentStage() == Stage.RED_CHOICE || currentStage() == Stage.SILVER_CHOICE;
	}

	public void resetTurn() {
		activeSquare = null;
		if(currentStage() == Stage.RED_ACTION){
			setStage(Stage.RED_CHOICE);
		}
		else if(currentStage() == Stage.SILVER_ACTION){
			setStage(Stage.SILVER_CHOICE);
		}
	}
}
