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

    private Grid grid;
    
    public Board(Map<Position, Piece> pieces) { 
    	generateBoard();
    	placePieces(pieces);
    }

    //TODO: hacerlo bien
    private void generateBoard() {
		grid = new Grid(ROWS, COLUMNS);
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
		            grid.setSquare(position, new FreeSquare());
		        }
		    }
		}
		
		//Sobreescribo, si no los if/else se hacen muy pesados
		//Se crearon arriba 4 instancias que se dejan de referenciar aca
		grid.setSquare(new Position(1, 0), new ReservedSquare(Team.RED));
		grid.setSquare(new Position(1, ROWS-1), new ReservedSquare(Team.RED));
		grid.setSquare(new Position(COLUMNS-2, 0), new ReservedSquare(Team.SILVER));
		grid.setSquare(new Position(COLUMNS-2, ROWS-1), new ReservedSquare(Team.SILVER));
//		board.setSquare(SILVER_CANNON_POS, new ReservedSquare(Team.SILVER));
//		board.setSquare(RED_CANNON_POS, new ReservedSquare(Team.RED));
	
    }
    
    public boolean checkDistance(Position init, Position dest) {
    	if (!isInBounds(init) || !isInBounds(dest))
    		return false;
        return init.isAdjacent(dest);
    }

   
    /**
     * Consulta si es valido mover una pieza como la que se pasa
     * como parametro (teniendo en cuenta tipo y equipo)
     * @param piece - la pieza a mover
     * @param position - la posicion que se quiere consultar
     * @return <tt>true</tt> si es licito moverla ahi; <tt>false</tt> de otro modo
     */
    public boolean canPlace(Piece piece, Position position){
    	Square selected = grid.getSquare(position);
    	return selected.canAccomodate(piece);
    }
    
    
    // TODO: validar también lo de las dos posiciones de cañón
    public boolean isInBounds(Position pos) {
        if (!grid.isInBounds(pos)) {
            return false;
        }

        return true;
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
    
    
    //TODO: Validaciones? 
    public void placePiece (Position position, Piece piece) {
    	grid.getSquare(position).setOccupant(piece);
    }
    
    private void placePieces(Map<Position, Piece> pieces) {
    	for (Position pos: pieces.keySet()) {
    		placePiece(pos, pieces.get(pos));
    	}
    }
 
}
