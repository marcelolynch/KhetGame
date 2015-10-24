package graphics;

import poo.khet.Game;
import poo.khet.Piece;
import poo.khet.Team;
import poo.khet.gameutils.Position;

public class GameManager implements ErrorConstants{
	enum Stage{ RED_CHOICE, SILVER_CHOICE, RED_ACTION, SILVER_ACTION }  
	
//	Board board = new Board();
	Game game;
	Stage stage;
	Position activeSquare;
	
	GameManager(Game game){
		stage = Stage.SILVER_CHOICE;
		this.game = game;
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
	
	private void setStage(Stage newStage){
		if(newStage == null){
			throw new IllegalArgumentException("null parameter");
		}
		stage = newStage;
	}
	
	public boolean handle(Position position){
		/*activeSquare = board.getPosition(coord);
		if(stage == Stage.RED_MOVE){
				activeSquare.setOccupant(new Anubis(Team.RED, Direction.NORTH));
				board.print();
				return true;
			}*/
		System.out.println("Manejando la coordenada (" + position.getRow() + ", " + position.getCol() + ")");
		return false;
		
	}
	
	/**
	 * Recibe una posicion activada por el usuario; de ser posible
	 * intenta aplicar la operacion (movimiento, seleccion) correspondiente
	 * a la etapa del juego en curso.
	 * @param position - posicion del tablero activada
	 * @param dummy
	 * @return
	 */
	public int handle(Position position, boolean dummy){ //Trabajando aca temporariamente
		if(position == null){
			throw new IllegalArgumentException("null parameter"); //TODO: Dejar que tire el NullPointer?
		}
		
		if(choosing() && game.getBoard().isEmptyPosition(position))
			return OK; //Selecciono casilla vacia - no pasa nada

		
		if(currentStage() == Stage.RED_CHOICE){
			Piece activePiece = game.getBoard().getOccupantIn(position);
			if(activePiece.getTeam() != Team.RED){
				return INVALID_TEAM_SELECTED;
			}
			else{
				activeSquare = position;
				setStage(Stage.RED_ACTION);
			}
		}
		
		else if(currentStage() == Stage.SILVER_CHOICE){
			Piece activePiece = game.getBoard().getOccupantIn(position);
			if(activePiece.getTeam() != Team.SILVER){
				return INVALID_TEAM_SELECTED;
			}
			else{
				activeSquare = position;
				setStage(Stage.SILVER_ACTION);
			}
		}

		else{
			Piece selected = game.getBoard().getOccupantIn(activeSquare);
			if(currentStage() == Stage.SILVER_ACTION && selected.getTeam() != Team.SILVER
					|| currentStage() == Stage.RED_ACTION && selected.getTeam() != Team.RED)
				return INVALID_ACTION_SELECTED; 
			
			if(game.canItMove(selected, position)){
				game.move(activeSquare, position);
				activeSquare = null; //Back to square 1
				setStage(currentStage() == Stage.SILVER_ACTION ? Stage.RED_CHOICE : Stage.SILVER_CHOICE);
			}
			else{
				return INVALID_MOVE_SELECTED;
			}
		}

	return OK;
	}
	
	
	public int handleRotation(boolean clockwise){
		if(choosing()){
			return CANT_ROTATE_RIGHT_NOW;
		}
		else{
			game.rotate(activeSquare, clockwise);
			return OK;
		}
	}
	
	/**
	 * Indica si la etapa es de eleccion de piezas (por claridad)
	 * @return
	 */
	private boolean choosing(){
		return currentStage() == Stage.RED_CHOICE || currentStage() == Stage.SILVER_CHOICE;
	}

	public void resetTurn() {
		if(currentStage() == Stage.RED_ACTION){
			setStage(Stage.RED_CHOICE);
		}
		else if(currentStage() == Stage.SILVER_ACTION){
			setStage(Stage.SILVER_CHOICE);
		}
	}
}
