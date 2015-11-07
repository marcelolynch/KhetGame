package poo.khet.AI;

import poo.khet.Board;
import poo.khet.Game;
import poo.khet.gameutils.Position;

/**
 * Representa una acci&oacute;n a ser realizada en el juego sobre una <tt>Piece</tt>.
 * <p>
 * Existen dos tipos de acciones v&aacute;lidas {@link Rotation} y {@link Move}
 * @see AIMover
 * @see Rotation
 * @see Move
 * @see poo.khet.Piece
 */
abstract class Action {
    /**
     * Posici&oacute;n inicial, previo a realizar la acci&oacute;n
     */
    private Position start;

    Action(Position start) {
        this.start = start;
    }

    /**
     * Devuelve la posici&oacute;n incial de la acci&oacute;n
     * @return <tt>Position</tt> incial
     */
    Position getStart() {
        return start;
    }

    /**
     * Realiza una acci&oacute;n sobre un {@link Board}
     * @param board El tablero sobre el que se ejecutar&aacute; la acci&oacute;n
     */
    abstract void executeActionIn(Board board);

    /**
     * Revierte una acci&oacute;n realizada previamente
     * @param action la acci&oacute;n a revertir
     * @return la acci&oacute;n ya revertida
     */
    abstract Action getRevertedAction(Action action);

    /**
     * Se realiza un movimiento sobre un {@link Game}
     * @param game Instancia de juego sobre la que se quiere realizar el movimiento
     */
    abstract void updateGame(Game game);

}
