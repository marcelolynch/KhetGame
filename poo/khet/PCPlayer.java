package poo.khet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import poo.khet.gameutils.Position;

public class PCPlayer implements CannonPositions {	
	private Game game;
	static Team team=Team.RED;
	private Random brain=new Random();
	private BeamManager beamManager;
	private Board board;//TODO: poner nombre lindo
	
	public PCPlayer(Game game){
		this.game=game;
		board=new Board(game.getBoard().getPiecesPosition());
		beamManager= new BeamManager(board);
	}
	
	
	 //nose que devolveria
	public void makeMove() {
		List<Action> possibleMoves = possibleMoves();
		possibleMoves.addAll(possibleRotations());
		Position startingPosition =  RED_CANNON_POSITION ;
		Action secondChoice=null;;//en caso de que no encuentre una que destruya una pieza del enemigo,por lo menos que elija una que no hace nada
		boolean foundSecondChoice= false;
		for(Action action : possibleMoves){
			BeamCannon cannon = game.getBeamCannon(team);
			Beam beam = cannon.generateBeam();
			action.executeActionIn(board);			
			BeamAction beamFate = beamManager.manageBeam(beam,startingPosition);
			if(beamFate == BeamAction.DESTROYED_PIECE && board.getOccupantIn(beamManager.getLastPos()).getTeam().equals(Team.SILVER)){
				action.executeActionIn(game.getBoard());//este es el tablero real
				game.nextTurn();	
				return;
				}
			else if(!foundSecondChoice && beamFate != BeamAction.DESTROYED_PIECE ){
				secondChoice=action;
				foundSecondChoice=true;
			}
			Action restore=action.getRevertedAction(action);
			restore.executeActionIn(board);
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


	
	public List<Action> possibleMoves(){
		List<Action> possibleMoves = new ArrayList<Action>();
			
		for(int i = 0; i < Board.ROWS ; i++){
			for(int j = 0; j < Board.COLUMNS ; j++){
				Position start = new Position(i,j);
				Position end=getAnEnd(start);
					
					if (game.isValidSelection(start) &&  game.isValidMove(start, end)) {
						possibleMoves.add(new Move(start,end));
					}
				}											
			}	
		return possibleMoves;
	}
					
	Position getAnEnd(Position start){
		Position end= getRandomEnd(start);
			while(!board.isInBounds(end)){
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
				boolean clockwise=rotate(start);
				if (game.isValidSelection(start)) {
					possibleRotations.add(new Rotation(start,clockwise));
				}
			}											
		}	
		return possibleRotations;
	}

	boolean rotate(Position start){
		return brain.nextInt(6)%2==0; // de ésta forma hay un 50% de probabilidad que rote de forma horaria y 50% que rote de forma antihoraria
	}
	
	
}