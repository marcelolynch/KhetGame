package poo.khet;

import java.io.Serializable;

import poo.khet.gameutils.Direction;

public class BeamCannon implements Serializable {

    //TODO
	private static final long serialVersionUID = 1L;

	private Team team;

    private Direction facing;
    private boolean clockwiseSwitch;

    public BeamCannon(Team team) {
        this.team = team;
        clockwiseSwitch = false;
        if (team == Team.RED) {
        	facing = Direction.NORTH;
        } else {
        	facing = Direction.SOUTH;
        }
    }

    public Direction getFacing() {
       	return facing;
     }
    
    public Team getTeam() {
    	return team;
    }
    
    Beam generateBeam() {
     	return new Beam(getFacing());
    }

    /**
     * Alterna la orientación del laser (entre las dos posibles)
     */
    public void switchFacing() {
    	if (clockwiseSwitch) {
            facing = facing.getClockwiseDir();
    	} else {
    		facing = facing.getCounterClockwiseDir();
        }
    	clockwiseSwitch = !clockwiseSwitch;
    }

    @Override
    public boolean equals(Object o){
    	if(o == null || !(o instanceof BeamCannon)){
    		return false;
    	}
    	BeamCannon b = (BeamCannon)o;
    	return b.getTeam().equals(this.getTeam()) && b.getFacing().equals(this.getFacing());
    }
    
    @Override
    public int hashCode(){
    	return 31*getTeam().hashCode() + getFacing().hashCode();
    }
}
