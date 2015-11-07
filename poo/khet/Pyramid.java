package poo.khet;

import poo.khet.gameutils.Direction;

public class Pyramid extends Piece {

    private static final long serialVersionUID = 1L;

    public Pyramid(Team team, Direction facing) {
        super(team);
        Mirror mirror = new Mirror(facing);
        addAccessory(mirror);
    }

    @Override
    boolean canBeSwapped() {
        return true;
    }

}
