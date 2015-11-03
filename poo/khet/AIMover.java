package poo.khet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import poo.khet.gameutils.Position;

public class AIMover implements CannonPositions {	
	private Game game;
	static Team team=Team.RED;
	private Random brain=new Random();
	private BeamManager beamManager;
	private Board auxiliarBoard;
	
	public AIMover(Game game){
		this.game=game;
		auxiliarBoard=new Board(game.getBoard().getPiecesPosition());
		beamManager= new BeamManager(auxiliarBoard);
	}
	
	
	 /**
	  * Se encarga de elegir la mejor jugada posible, con un criterio establecido, y de realizarla, luego llama a game para que finalize el turno
	  */
	public void makeMove() {
		List<Action> possibleMoves = possibleMoves();
		possibleMoves.addAll(possibleRotations());
		Action secondChoice=null;
		boolean foundSecondChoice= false;
		for(Action action : possibleMoves){
			BeamAction beamFate = simulateMove(action);
			if(beamFate == BeamAction.DESTROYED_PIECE && auxiliarBoard.getOccupantIn(beamManager.getLastPos()).getTeam().equals(Team.SILVER)){
				action.executeActionIn(game.getBoard());
				game.nextTurn();	
				return;
				}
			else if(!foundSecondChoice && beamFate != BeamAction.DESTROYED_PIECE ){
				secondChoice=action;
				foundSecondChoice=true;
			}
			Action restore=action.getRevertedAction(action);
			restore.executeActionIn(auxiliarBoard);
			}
		if(foundSecondChoice){
			secondChoice.executeActionIn(game.getBoard());

		}else{
		Action finalChoice=possibleMoves.get(brain.nextInt(possibleMoves.size()-1)); // como no encontro ninguna "buena" , agarra cualquiera
		finalChoice.executeActionIn(game.getBoard());
		}
		game.nextTurn();
		return;	
	}
	/**
	 * Realiza la accion en el tablero auxiliar y lanza el rayo en él
	 * @param action
	 * @return
	 */
	public BeamAction simulateMove(Action action){
		action.executeActionIn(auxiliarBoard);	
		BeamCannon cannon = game.getBeamCannon(team);
		Beam beam = cannon.generateBeam();
		Position startingPosition =  RED_CANNON_POSITION ;
		return beamManager.manageBeam(beam,startingPosition);	
	}


	
	public List<Action> possibleMoves(){
		List<Action> possibleMoves = new ArrayList<Action>();
			
		for(int i = 0; i < Board.ROWS ; i++){
			for(int j = 0; j < Board.COLUMNS ; j++){
				Position start = new Position(i,j);
				Position end=getRandomEndInBounds(start);
					
					if (game.isValidSelection(start) &&  game.isValidMove(start, end)) {
						possibleMoves.add(new Move(start,end));
					}
				}											
			}	
		return possibleMoves;
	}
					
	Position getRandomEndInBounds(Position start){
		Position end= getRandomEnd(start);
			while(!auxiliarBoard.isInBounds(end)){
				end= getRandomEnd(start);
			}
		return end;				
	}
	/**
	 * 
	 * @param start
	 * @return	una posición aleatoria adyacente a ésta, pues nextInt(3) me da un número entre 0 y 2, y al restarle 1, me da uno entre -1 y 1.
	 */
	Position getRandomEnd(Position start){
		return new Position(start.getRow()+brain.nextInt(3)-1,start.getCol()+brain.nextInt(3)-1);
	}																							


	public List<Action> possibleRotations(){
		List<Action> possibleRotations = new ArrayList<Action>();
		
		for(int i = 0; i < Board.ROWS ; i++){
			for(int j = 0; j < Board.COLUMNS ; j++){
				Position start = new Position(i,j);
				if (game.isValidSelection(start)) {
					possibleRotations.add(randomRotation(start));
				}
			}											
		}	
		return possibleRotations;
	}
	
	/**
	 * Rota de manera aleatoria la pieza ubicada en la posición pos del tablero.
	 * @param start
	 * @return una rotacion
	 */
	Rotation randomRotation(Position pos){
		boolean clockwise = brain.nextInt(6)%2==0; // de ésta forma hay un 50% de probabilidad que rote de forma horaria y 50% que rote de forma antihoraria
		return new Rotation(pos,clockwise);
	}
	
	
}