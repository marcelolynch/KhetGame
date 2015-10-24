package poo.khet;

import poo.khet.gameutils.Direction;

public abstract class Accessory {
	private Direction facing;
	
	Accessory(Direction dir) {
		facing = dir;
	}
	
	Direction getFacing() {
		return facing;
	}
	
	void rotateClockwise() {
		facing = getFacing().getClockwiseDir();
	}
	
	void rotateCounterClockwise() {
		facing = getFacing().getCounterClockwiseDir();
	}

	abstract boolean canProcessBeam(Beam beam);
	abstract Beam processBeam(Beam beam); // ya que modifica el estado interno de Beam, estaría bien que devuelva void?
	
	@Override
	public boolean equals(Object o){
		if(o == null || !(o.getClass().equals(this.getClass()))){
			return false;
		}
		Accessory other = (Accessory)o;
		return other.getFacing().equals(this.getFacing());
	}
	
	public int hashCode(){
		return facing.hashCode();
	}

}
