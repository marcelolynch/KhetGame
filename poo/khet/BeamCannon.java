package poo.khet;

import poo.khet.gameutils.Direction;

public class BeamCannon /*extends Component*/ implements BoardAccepter{

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
            facing = facing.getClockwiseDirection();
    	} else {
    		facing = facing.getCounterClockwiseDirection();
        }
    	isClockRotation = !isClockRotation;
    }

    @Override
    public boolean accept(BoardVisitor visitor) {
        return visitor.visit(this);
    }
}
