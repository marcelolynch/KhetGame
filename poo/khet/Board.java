package poo.khet;

import java.util.Map;

import poo.khet.gameutils.Position;
import poo.khet.gameutils.Grid;

public class Board {

    static final int COLUMNS = 10;
    static final int ROWS = 8;
    
    // TODO interfaz de constantes
    //esto ya queda fuera de lugar pero hay que ver como ponerlo para poder hacer lo de outOfBounds
    static final private Position SILVER_CANNON_POS = new Position(0, 0);
    static final private Position RED_CANNON_POS = new Position(COLUMNS-1, ROWS-1);

    private Grid<Square> grid;
    
    public Board(Map<Position, Piece> pieces) { 
    	generateBoard();
    	placePieces(pieces);
    }

    //TODO: hacerlo bien
    private void generateBoard() {
		grid = new Grid<Square>();
		Position position;
		
		for (int i=0; i < ROWS; i++) {
		    for(int j=0; j < COLUMNS ; j++){
		    	position = new Position(i, j);
		        if(j == 0 && i != SILVER_CANNON_POS.getRow()) {
	                grid.setSquare(position, new ReservedSquare(Team.SILVER));
		        }
		        else if(j == COLUMNS-1 && i != RED_CANNON_POS.getCol()) {
		        	grid.setSquare(position, new ReservedSquare(Team.RED));
		        }
		        else {
		            grid.setSquare(position, new Square());
		        }
		    }
		}
		
		//Sobreescribo, si no los if/else se hacen muy pesados
		//Se crearon arriba 4 instancias que se dejan de referenciar aca
		grid.setSquare(new Position(0, 1), new ReservedSquare(Team.RED));
		grid.setSquare(new Position(0,8), new ReservedSquare(Team.RED));
		grid.setSquare(new Position(7, 8), new ReservedSquare(Team.SILVER));
		grid.setSquare(new Position(7, 1), new ReservedSquare(Team.SILVER));
	
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
    	Square selected = grid.getSquare(position);
    	return selected.canAccomodate(piece);
    }
    
    public boolean isInBounds(Position pos) {
        return grid.isInBounds(pos);
    }
    

    public Piece withdrawFrom (Position position) {
    	return grid.getSquare(position).withdrawOccupant();
    }
    
    
    public boolean isEmptyPosition(Position position){
    	return grid.getSquare(position).isEmpty();
    }
    
    
    public Piece getOccupantIn (Position position) {
    	if(isEmptyPosition(position)){
    		throw new IllegalStateException(); //TODO: Excepcion
    	}
    	return grid.getSquare(position).getOccupant();
    }
    
    public void placePiece (Position pos, Piece piece) {
    	if (grid.isInBounds(pos) && grid.getSquare(pos).isEmpty()) {
    		grid.getSquare(pos).setOccupant(piece);
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
