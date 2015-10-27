package poo.khet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import poo.khet.gameutils.Position;

public class PCPlayer implements CannonPositions {	
	private Board board;
	static Team team=Team.RED;
	Position a=new Position(0, 0);
	public PCPlayer(Board board){
		this.board=board;
	}
	
	
	 //nose que devolveria
	public void makeMove() {
		List<Action> possibleMoves = possibleMoves();
		possibleMoves.addAll(possibleRotations());
		Position startingPosition =  RED_CANNON_POSITION ;
		for(Action action : possibleMoves){
			BeamCannon cannon = getBeamCannon(team);// desp veo como 
			Beam beam = cannon.generateBeam();
			BeamManager beamManager=new BeamManager(beam,board);
			changeBoard(action);			
			BeamAction beamFate = beamManager.throwBeam(startingPosition);
			
			if(beamFate == BeamAction.DESTROYED_PIECE){
					return;
				}
			restoreBoard(action);
			}
		changeBoard(possibleMoves.get(0));
		BeamCannon cannon = getBeamCannon(team);// desp veo como 
		Beam beam = cannon.generateBeam();
		BeamManager beamManager=new BeamManager(beam,board);
		BeamAction beamFate = beamManager.throwBeam(startingPosition);	
	}

	/**
	 * solucion provisioria
	 * @param move
	 */
	 
	private void changeBoard(Move move){
		Piece moved=board.withdrawFrom(move.getStart());
		board.placePiece(move.getDest(), moved);
	}
	
	private void restoreBoard(Move move){
		Piece restored=board.withdrawFrom(move.getDest());
		board.placePiece(move.getStart(), restored);
	}
	private void changeBoard(Rotation rotation){
		Piece rotated=board.getOccupantIn(rotation.getStart());
		if(rotation.isClockwise()){
			rotated.rotateClockwise();
		}else{
			rotated.rotateCounterClockwise();
		}
	}
	
	private void restoreBoard(Rotation rotation){
		Piece restored=board.getOccupantIn(rotation.getStart());
		if(rotation.isClockwise()){
			restored.rotateCounterClockwise();
		}else{
			restored.rotateClockwise();
		}
	}
	
	private void changeBoard(Action action){

	}
	
	private void restoreBoard(Action action){
		
	}
	
	
	public List<Action> possibleMoves(){
		List<Action> possibleMoves = new ArrayList<Action>();
			
		for(int i = 0; i < Board.ROWS ; i++){
			for(int j = 0; j < Board.COLUMNS ; j++){
				Position start = new Position(i,j);
				Position end=getAnEnd(start);
				if(!board.isEmptyPosition(start)){										
				   	boolean isValid = true;
					if (!start.isAdjacent(end)) {
						isValid = false;
					}
					Piece initialPiece=board.getOccupantIn(start);
					boolean correctPiece=initialPiece.getTeam().equals(team);
					boolean correctMovement=board.canPlace(initialPiece, end);
					if (!isValid || !correctPiece || !correctMovement) {
						isValid = false;
					}
					if (isValid) {
						possibleMoves.add(new Move(start,end));
					}
				}											
			}
		}
		
		return possibleMoves;
	}
					
	Position getAnEnd(Position start){
		Random num=new Random();
		Position end= new Position(start.getRow()+num.nextInt(3)-1,start.getCol()+num.nextInt(3)-1);
			while(!board.isInBounds(end)){
				end= new Position(start.getRow()+num.nextInt(3)-1,start.getCol()+num.nextInt(3)-1);
			}
		return end;				
	}


	public List<Action> possibleRotations(){
		List<Action> possibleRotations = new ArrayList<Action>();
		
		for(int i = 0; i < Board.ROWS ; i++){
			for(int j = 0; j < Board.COLUMNS ; j++){
				Position start = new Position(i,j);
				boolean clockwise=rotate(start);
				if(!board.isEmptyPosition(start)){										
				   	boolean isValid = true;
					Piece piece=board.getOccupantIn(start);
					boolean correctPiece=piece.getTeam().equals(team);
					if (!isValid || !correctPiece) {
						isValid = false;
					}
					if (isValid) {
						possibleRotations.add(new Rotation(start,clockwise));
					}
				}											
			}
		}
		
		return possibleRotations;
	}

	boolean rotate(Position start){
		Random num=new Random();
		return num.nextInt(6)>3;
	}
	
	
}