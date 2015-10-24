package poo.khet.gameutils;

import java.util.HashMap;
import java.util.Map;

//TODO: cambiar nombre métodos
public class Grid<T> {
	
	private Map<Position, T> grid;
	
	public Grid () {
		grid = new HashMap<Position, T>();
	}
	
	public T getSquare(Position pos) {
		if (!isInBounds(pos)) {
			throw new IllegalArgumentException();
		}
		return grid.get(pos);
	}
	
	public void setSquare(Position pos, T square) {
		if (pos == null || square == null) {
			throw new IllegalArgumentException();
		}
		
		//Comente esto porque no tiene sentido: isInBounds pregunta si ya existe la posicion en el mapa
		//O sea tira excepcion si queres inicializar algo que nunca inicializaste, o sea no se puede llenar
		//por primera vez. -Chelo
		
	/*	if (!isInBounds(pos)) {
			throw new IllegalArgumentException("Out of bounds"); //exception de outofbounds
		}*/
		grid.put(pos, square);
	}
	
	public boolean isInBounds(Position pos) {
		if (pos == null)
			throw new IllegalArgumentException("null pos");
		return grid.containsKey(pos);
	}
}
