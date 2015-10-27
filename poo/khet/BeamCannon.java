package poo.khet;

import java.io.Serializable;

import poo.khet.gameutils.Direction;

public class BeamCannon implements Serializable {

    //TODO
	private static final long serialVersionUID = 1L;

	private Team team;

    private Direction facing;
    private boolean isClockRotation;

    public BeamCannon(Team team) {
        this.team = team;
        isClockRotation = false;
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
    void rotate() {
    	if (isClockRotation) {
            facing = facing.getClockwiseDir();
    	} else {
    		facing = facing.getCounterClockwiseDir();
        }
    	isClockRotation = !isClockRotation;
    }

}
