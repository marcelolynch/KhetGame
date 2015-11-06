package poo.khet.AI;

import poo.khet.Board;
import poo.khet.Game;
import poo.khet.gameutils.Position;

abstract class Action {
    private Position start;

    Action(Position start) {
        this.start = start;
    }

    Position getStart() {
        return start;
    }

    abstract void executeActionIn(Board board);
    abstract void updateGame(Game game);
    abstract Action getRevertedAction(Action action);
}
