package poo.khet;

import java.util.HashMap;
import java.util.Map;

import poo.khet.gameutils.Position;

public class Board implements CannonPositions {

    static final int COLUMNS = 10;
    static final int ROWS = 8;
    
    private Map<Position, Square> grid;
    
    public Board(Map<Position, Piece> pieces) { 
    	generateBoard();
    	placePieces(pieces);
    }

    //TODO: hacerlo bien
    private void generateBoard() {
		grid = new HashMap<Position, Square>();
		Position position;
		
		for (int i=0; i < ROWS; i++) {
		    for(int j=0; j < COLUMNS ; j++){
		    	position = new Position(i, j);
		    	if( !(position.equals(RED_CANNON_POSITION) || position.equals(SILVER_CANNON_POSITION)) ){
		    		if(j == 0) {
		    			grid.put(position, new ReservedSquare(Team.SILVER));
		    		}
		    		else if(j == COLUMNS-1) {
		    			grid.put(position, new ReservedSquare(Team.RED));
		    		}
		    		else {
		    			grid.put(position, new Square());
		    		}
		    	}
		    }
		}
		
		//Sobreescribo, si no los if/else se hacen muy pesados
		//Se crearon arriba 4 instancias que se dejan de referenciar aca
		grid.put(new Position(0, 1), new ReservedSquare(Team.RED));
		grid.put(new Position(0, 8), new ReservedSquare(Team.RED));
		grid.put(new Position(7, 8), new ReservedSquare(Team.SILVER));
		grid.put(new Position(7, 1), new ReservedSquare(Team.SILVER));
		

    }
   
    /**
     * Consulta si es valido mover una pieza como la que se pasa
     * como parametro (teniendo en cuenta tipo y equipo)
     * @param piece - la pieza a mover
     * @param position - la posicion que se quiere consultar
     * @return <tt>true</tt> si es licito moverla ahi; <tt>false</tt> de otro modo
     */
    public boolean canPlace(Piece piece, Position position){
    	if (!isInBounds(position)) {
    		return false;
    	}
    	Square selected = grid.get(position);
    	return selected.canAccomodate(piece);
    }
    
    public boolean isInBounds(Position pos) {
        return grid.containsKey(pos);
    }
    

    public Piece withdrawFrom (Position position) {
    	return grid.get(position).withdrawOccupant();
    }
    
    
    public boolean isEmptyPosition(Position position){
    	return grid.get(position).isEmpty();
    }
    
    
    public Piece getOccupantIn (Position position) {
    	if(isEmptyPosition(position)){
    		throw new IllegalStateException(); //TODO: Excepcion
    	}
    	return grid.get(position).getOccupant();
    }
    
    public void placePiece (Position pos, Piece piece) {
    	if (isInBounds(pos) && isEmptyPosition(pos)) {
    		grid.get(pos).setOccupant(piece);
    	}
    	else {
    		throw new IllegalArgumentException();
    	}
    }
    
    private void placePieces(Map<Position, Piece> pieces) {
    	for (Position pos: pieces.keySet()) {
    		placePiece(pos, pieces.get(pos));
    	}
    }
 
}
