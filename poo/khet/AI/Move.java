package poo.khet.AI;

import poo.khet.Board;
import poo.khet.Game;
import poo.khet.Piece;
import poo.khet.gameutils.Position;

/**
 * Representa un movimiento desde una <code><tt>Position</tt></code> a otra distinta.
 * @see Position
 */
class Move extends Action {

    /**
     * <tt>Position</tt> final del movimiento
     */
    private Position end;

    /**
     * Genera un movimiento a partir de una <tt>Position</tt> inicial y una final
     * @param start <tt>Position</tt> inicial del movimiento
     * @param end <tt>Position</tt> final del movimiento
     */
    public Move(Position start, Position end) {
        super(start);
        this.end = end;
    }

    public Position getDest() {
        return end;
    }

    /**
     * Realiza un movimiento en un {@link Board}.
     * Retira una <tt>Piece</tt> de la posici&oacute;n inicial, para luego depositarla
     * en la posici&oacute;n final.
     * <p>
     * En caso de de que la posici&oacute;n final tenga un ocupante realiza un intercambio.
     * @param board El tablero sobre el que se ejecutar&aacute; el movimiento
     */
    @Override
    void executeActionIn(Board board) {
        Piece moved = board.withdrawFrom(this.getStart());

        if (!board.isEmptyPosition(getDest())) {
            Piece swapped = board.withdrawFrom(getDest());
            board.placePiece(getStart(), swapped);
        }
        board.placePiece(this.getDest(), moved);
    }

    @Override
    Action getRevertedAction(Action action) {
        return new Move(this.getDest(), this.getStart());
    }

    @Override
    void updateGame(Game game) {
        game.move(this.getStart(), this.getDest());
    }

}
