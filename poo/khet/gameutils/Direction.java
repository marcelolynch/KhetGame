package poo.khet.gameutils;

/**
 * Representa una dirección cuyos posibles valores son NORTH, EAST, SOUTH y WEST.
 * Presenta métodos para compararse con otras direcciones.
 */
public class Direction {
	public static final Direction NORTH = new Direction(0);
	public static final Direction EAST = new Direction(1);
	public static final Direction SOUTH = new Direction(2);
	public static final Direction WEST = new Direction(3);

	// Facilitan los cálculos
	private static final Direction directions[] = {NORTH, EAST, SOUTH, WEST};
	private static final int NUM_DIRECTIONS = directions.length;
	private final int directionIndex;
	
	private Direction(int index) {
		directionIndex = index;
	}
	
	/**
	 * Devuelve la <tt>Direction</tt> que se encuentra a 90° de la actual dirección.
	 * @return <tt>Direction</tt> que se encuentra a 90° de la actual dirección
	 */
	public Direction getClockwiseDir() {
		int index = (directionIndex + 1) % NUM_DIRECTIONS;
		return directions[index];
	}
	
	/**
	 * Devuelve la <tt>Direction</tt> que se encuentra a -90° de la actual dirección.
	 * @return <tt>Direction</tt> que se encuentra a -90° de la actual dirección
	 */
	public Direction getCounterClockwiseDir() {
		int index;
		if (directionIndex == 0) {
			index = 3;
		}
		else {
			index = directionIndex - 1;
		}
		return directions[index];
	}
	
	/**
	 * Devuelve la <tt>Direction</tt> opuesta a la actual dirección.
	 * @return <tt>Direction</tt> opuesta a la actual dirección
	 */
	public Direction getOppositeDir() {
		int index = (directionIndex + 2) % NUM_DIRECTIONS;
		return directions[index];
	}
	
	/**
	 * Evalúa si otherDir se encuentra a 90° con respecto de la dirección actual.
	 * @param otherDir - dirección a evaluar con la dirección actual
	 * @return <tt>true</tt> si otherDir se encuentra a 90° con respecto de la dirección actual; <tt>false</tt> sino
	 */
	public boolean isClockwiseDir(Direction otherDir) {
		return getClockwiseDir().equals(otherDir);
	}
	
	/**
	 * Evalúa si otherDir se encuentra a -90° con respecto de la dirección actual.
	 * @param otherDir - dirección a evaluar con la dirección actual
	 * @return <tt>true</tt> si otherDir se encuentra a -90° con respecto de la dirección actual; <tt>false</tt> sino
	 */
	public boolean isCounterClockwiseDir(Direction otherDir) {
		return getCounterClockwiseDir().equals(otherDir);
	}	

	/**
	 * Evalúa si otherDir es opuesta a la dirección actual.
	 * @param otherDir - dirección a evaluar con la dirección actual
	 * @return <tt>true</tt> si otherDir es opuesta a la dirección actual; <tt>false</tt> sino
	 */
	public boolean isOppositeDir(Direction otherDir) {
		return getOppositeDir().equals(otherDir);
	}
	
	@Override
	public String toString(){
		String str;
		
		switch(directionIndex) {
		case 0:
			str = "NORTH";
			break;
		case 1:
			str = "EAST";
			break;
		case 2:
			str = "SOUTH";
			break;
		case 3:
			str = "WEST";
			break;
		default:
			throw new IllegalStateException();
		}
		
		return str;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this == obj;
	}
	
	@Override
	public int hashCode() {
		return directionIndex;
	}
}
