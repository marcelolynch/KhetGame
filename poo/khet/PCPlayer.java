package poo.khet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Vector;

import poo.khet.gameutils.Position;

public class PCPlayer {	
	private Board board;
	private Team team;
	
	public PCPlayer(Board board,Team team){
		this.team=team;
		this.board=board;
	}
	public Move getMove() {
		List<Move> possibleMoves = possibleMoves();
		List<Move> possibleRotation = possibleRotations();//Nose si lo prefieren asi o hacer un metodo mas complejo, para no tener que iterar en dos listas
		List<Move> goodMoves = new ArrayList<Move>();
		for(Move move:possibleMoves){
			BeamCannon cannon = board.getBeamCannon(team);
			Beam beam = cannon.generateBeam();
			BeamManager beamManager=new BeamManager(beam,board);
			Position death=beamManager.throwBeam(team);
			if(death != null){
				Square deathSquare = board.getPosition(death);
				if(deathSquare.getOccupant().getTeam()!=team){
					goodMoves.add(move);
				}
			}
		}
		Random number=new Random();
		Integer selectedMove = number.nextInt(goodMoves.size());
		return goodMoves.get(selectedMove);
	}


	
	public List<Move> possibleMoves(){
		List<Move> possibleMoves = new ArrayList<Move>();
	
		for(int i = 0; i < Board.ROWS ; i++){
			for(int j = 0; j < Board.COLUMNS ; j++){
				Position start = new Position(i,j);
				
				List<Position> possibleEnds = getAdyacentPositions(start);
				
				for (Position end: possibleEnds){
				   	boolean isValid = true;
					// pasarle board a la pc para que pueda simular sus jugadas?
					Square initSquare = board.getPosition(start);
					Square destSquare = board.getPosition(end);
					if (!board.checkDistance(start,end)) {
						isValid = false;
					}
					boolean correctPiece=((Piece)initSquare.getOccupant()).getTeam().equals(team);
					boolean correctMovement=((Piece)initSquare.getOccupant()).canMove(destSquare);
					if (!isValid || initSquare.isEmpty() || !correctPiece || !correctMovement) {
						isValid = false;
					}
					if (isValid) {
						moves.add(new Move(start,end));
					}
				}											
			}
		}
		return possibleMoves;
	}
	
	
	
	private List<Position> getAdyacentPositions(Position start) {
	//asi nomas por ahora, despues lo hago lindo en Coordinate
		List<Position> possibleDirections= new ArrayList<Position>();
		Position c1 = new Position(start.getRow()+1,start.getCol());
		Position c2 = new Position(start.getRow(),start.getCol()+1);
		Position c3 = new Position(start.getRow()-1,start.getCol());
		Position c4 = new Position(start.getRow(),start.getCol()-1);
		Position c5 = new Position(start.getRow()+1,start.getCol()+1);
		Position c6 = new Position(start.getRow()+1,start.getCol()-1);
		Position c7 = new Position(start.getRow()-1,start.getCol()+1);
		Position c8 = new Position(start.getRow()-1,start.getCol()-1);
		possibleDirections.add(c1);
		possibleDirections.add(c2);
		possibleDirections.add(c3);
		possibleDirections.add(c4);
		possibleDirections.add(c5);
		possibleDirections.add(c6);
		possibleDirections.add(c7);
		possibleDirections.add(c8);
		return possibleDirections;
	}
}

