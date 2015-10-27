package poo.khet;

import java.io.Serializable;

import poo.khet.gameutils.Direction;

public class Pyramid extends Piece {
	
	//TODO: 
	private static final long serialVersionUID = 1L;
	
	private Mirror2 mirror;

	public Pyramid(Team team, Direction facing) {
		super(team);
		mirror = new Mirror2(facing);
	}

	/**
	 * Cambia la direccion del laser en caso de que sea reflejado. Devuelve si
	 * el laser fue o no reflejado.
	 * 
	 * @param beam
	 *            laser que recibe y al que le cambia la direccion
	 * @return <tt>true</tt> si el laser fue reflejado y su direccion cambio.
	 *         <ff>false</ff> si el laser no fue reflejado.
	 */
	@Override
	boolean receiveBeam(Beam beam) {
		boolean reflectable = mirror.canProcessBeam(beam);

		if (reflectable) {
			mirror.processBeam(beam);
		} else {
			beam.deactivate(); // no se pudo reflejar; el rayo choca con la
								// pieza y finaliza su recorrido
		}

		return reflectable;
	}

	@Override
	boolean canBeSwapped() {
		return true;
	}

	@Override
	void rotateClockwise() {
		mirror.rotateClockwise();
	}

	@Override
	void rotateCounterClockwise() {
		mirror.rotateCounterClockwise();
	}
	
	@Override
	public boolean equals(Object o){
		if(o == null || !(o instanceof Pyramid)){
			return false;
		}
		Pyramid other = (Pyramid)o;
		return other.getTeam().equals(this.getTeam()) && other.mirror.equals(this.mirror);
	}
	
	@Override
	public int hashCode(){
		return getTeam().hashCode() ^ mirror.hashCode();
	}


}