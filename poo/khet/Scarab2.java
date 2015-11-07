package poo.khet;

import poo.khet.gameutils.Direction;

public class Scarab2 extends Piece2 {

    private static final long serialVersionUID = 1L;

    public Scarab2(Team team, Direction facing) {
        super(team);
        Mirror mirror1 = new Mirror(facing);
        Mirror mirror2 = new Mirror(facing.getOppositeDir());
 
        addAccessory(mirror1);
        addAccessory(mirror2);
    }

    @Override
    boolean canMove(Square square) {
        if (super.canMove(square)) {
            return true;
        } else {
            return square.getOccupant().canBeSwapped();
        }
    }

    @Override
    boolean canBeSwapped() {
        return false;
    }

}
