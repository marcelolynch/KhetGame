package poo.khet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import poo.khet.gameutils.*;

public class PCPlayer implements CannonPositions {	
	private Board board;
	static Team team=Team.RED;
	Position a=new Position(0, 0);
	public PCPlayer(Board board,Team team){
		this.board=board;
	}
	public Move getMove() {
		List<Move> possibleMoves = possibleMoves();
		List<Move> possibleRotation = possibleRotations();
		//Lo implemento con moves por ahora, si tengo una lista de actions no se como diferenciar entre si tengo que mover o rotar,para decirle al board
		for(Move move:possibleMoves){
			BeamCannon cannon = getBeamCannon(team);// desp veo como 
			Beam beam = cannon.generateBeam();
			BeamManager beamManager=new BeamManager(beam,board);
			Piece mover=board.withdrawFrom(start);
			board.placePiece(end, mover);
			Position startingPosition =  RED_CANNON_POSITION ;
			BeamAction beamFate = beamManager.throwBeam(startingPosition);
			board.withdrawFrom(end);
			board.placePiece(start, mover);
			if(beamFate == BeamAction.DESTROYED_PIECE){
					return move;
				}
			}
		return possibleMoves.get(0);
	}


	public List<Move> possibleMoves(){
		List<Move> possibleMoves = new ArrayList<Move>();
			
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
}

