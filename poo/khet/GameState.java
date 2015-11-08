package poo.khet;

import poo.khet.gameutils.GameMode;
import poo.khet.gameutils.Position;

import java.io.Serializable;
import java.util.Map;

/**
 * Un {@link GameState} encapsula información relevante acerca de una partida, representando
 * entonces el estado actual de un {@link Game}.
 * <p>
 * Contiene:
 * <p>
 * - Un {@link GameMode} indicando el modo de juego elegido <i>PVE</i> o <i>PVP</i>.
 * <p>
 * - Un mapa con la ubicación de las {@link Piece}s que se encuentran en el tablero
 * <p>
 * - Los {@link BeamCannon} de la partida
 * <p>
 * - El {@link Team} al que le corresponde realizar una jugada
 *
 * @see Game
 * @see GameMode
 * @see Team
 * @see BeamCannon
 * @see Piece
 * @see Position
 */
public class GameState implements Serializable {

    private static final long serialVersionUID = 1L;

    private GameMode mode;
    private Map<Position, Piece> boardConfig;
    private RedCannon redCannon;
    private SilverCannon silverCannon;
    private Team movingTeam;

    /**
     * Crea un nuevo estado de juego
     * @param mode modo de juego
     * @param boardConfig ubicacion de las piezas
     * @param movingTeam <code>Team</code> al que corresponde el turno actual
     * @param redCannon <code>BeamCannon</code> del equipo <i>RED</i>
     * @param silverCannon <code>BeamCannon</code> del equipo <i>SILVER</i>
     */
    public GameState(GameMode mode, Map<Position, Piece> boardConfig, Team movingTeam,
            RedCannon redCannon, SilverCannon silverCannon) {
        this.mode = mode;
        this.boardConfig = boardConfig;
        this.movingTeam = movingTeam;
        this.redCannon = redCannon;
        this.silverCannon = silverCannon;
    }

    /**
     * Devuelve el modo de juego <i>PVE</i> o <i>PVP</i>
     * @return modo de Juego
     */
    public GameMode getGameMode() {
        return mode;
    }

    /**
     * El {@link Team} al que le toca realizar un jugada
     * @return <code>Team</code> a quien corresponde el turno actual
     */
    public Team getMovingTeam() {
        return movingTeam;
    }

    /**
     * Posiciones de las piezas en juego
     * @return Mapa con las posiciones de las piezas en juego
     */
    public Map<Position, Piece> getBoardConfig() {
        return boardConfig;
    }

    /**
     * Devuelve el {@link BeamCannon} del {@link Team} <i>RED</i>
     * @return cañon del equipo <i>RED</i>
     */
    public RedCannon getRedCannon() {
        return redCannon;
    }

    /**
     * Devuelve el {@link BeamCannon} del {@link Team} <i>SILVER</i>
     * @return cañon del equipo <i>SILVER</i>
     */
    public SilverCannon getSilverCannon() {
        return silverCannon;
    }
}
