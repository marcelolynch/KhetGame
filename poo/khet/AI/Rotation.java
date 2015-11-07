package poo.khet.AI;

import poo.khet.Board;
import poo.khet.Game;
import poo.khet.Piece;
import poo.khet.gameutils.Position;

/**
 * Representa la rotaci&oacute;n de una <tt>Piece</tt>, es decir el cambio de su orientaci&oacute;n.
 * <p>
 * Una rotaci&oacute;n puede ser en sentido horario (Clockwise) o en sentido antihorario
 * (CounterClockwise)
 * @see AIMover
 * @see poo.khet.gameutils.Direction
 */
class Rotation extends Action {
    /**
     *<code><b>true</b></code> si se quiere efectuar una rotaci&oacute;n
     * horaria, <code><b>false</b></code> en caso contrario.
     */
    private boolean isClockwise;

    public Rotation(Position start, boolean isClockwise) {
        super(start);
        this.isClockwise = isClockwise;
    }

    public boolean isClockwise() {
        return isClockwise;
    }

    /**
     * Realiza el cambio de orientaci&oacute;n de una <tt>Piece</tt> en un {@link Board}
     * @param board Tablero sobre el cual se quiere rotar una pieza
     */
    @Override
    void executeActionIn(Board board) {
        Piece rotated = board.getOccupantIn(this.getStart());
        if (this.isClockwise()) {
            rotated.rotateClockwise();
        } else {
            rotated.rotateCounterClockwise();
        }
    }

    @Override
    Action getRevertedAction(Action action) {
        return new Rotation(this.getStart(), !this.isClockwise());
    }

    @Override
    void updateGame(Game game) {
        game.rotate(getStart(), isClockwise());
    }
}
