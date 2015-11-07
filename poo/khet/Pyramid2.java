package poo.khet;

import poo.khet.gameutils.Direction;

public class Pyramid2 extends Piece2 {

    private static final long serialVersionUID = 1L;

    public Pyramid2(Team team, Direction facing) {
        super(team);
        Mirror mirror = new Mirror(facing);
        addAccessory(mirror);
    }

    @Override
    boolean canBeSwapped() {
        return true;
    }

}
