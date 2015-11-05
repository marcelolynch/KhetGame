package poo.khet;

import poo.khet.gameutils.GameMode;
import poo.khet.gameutils.Position;

import java.io.Serializable;
import java.util.Map;

public class GameState implements Serializable {

    private static final long serialVersionUID = 1L;

    private GameMode mode;
    private Map<Position, Piece> boardConfig;
    private RedCannon redCannon;
    private SilverCannon silverCannon;
    private Team movingTeam;

    public GameState(GameMode mode, Map<Position, Piece> boardConfig, Team movingTeam,
            RedCannon redCannon, SilverCannon silverCannon) {
        this.mode = mode;
        this.boardConfig = boardConfig;
        this.movingTeam = movingTeam;
        this.redCannon = redCannon;
        this.silverCannon = silverCannon;
    }

    public GameMode getGameMode() {
        return mode;
    }

    public Team getMovingTeam() {
        return movingTeam;
    }

    public Map<Position, Piece> getBoardConfig() {
        return boardConfig;
    }

    public RedCannon getRedCannon() {
        return redCannon;
    }

    public SilverCannon getSilverCannon() {
        return silverCannon;
    }
}
