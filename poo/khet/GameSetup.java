package poo.khet;
import poo.khet.gameutils.Position;

import java.io.Serializable;
import java.util.Map;

public class GameSetup implements Serializable {
	//TODO: Preguntar 
	private static final long serialVersionUID = 1L;
	
	private boolean twoPlayers;
	private Map<Position, Piece> boardConfig;
	private BeamCannon redCannon;
	private BeamCannon silverCannon;
	
	public GameSetup() {
	}
	
	//Todo en periodo de prueba
	public GameSetup(boolean twoPlayers, Map<Position, Piece> boardConfig, BeamCannon redCannon,
			BeamCannon silverCannon) {
		super();
		this.twoPlayers = twoPlayers;
		this.boardConfig = boardConfig;
		this.redCannon = redCannon;
		this.silverCannon = silverCannon;
	}
	
	public GameSetup(boolean twoPlayers, Map<Position, Piece> boardConfig) {
		super();
		this.twoPlayers = twoPlayers;
		this.boardConfig = boardConfig;
		redCannon = new BeamCannon(Team.RED);
		silverCannon = new BeamCannon(Team.SILVER);
	}

	public boolean isTwoPlayers() {
		return twoPlayers;
	}

	public void setTwoPlayers(boolean twoPlayers) {
		this.twoPlayers = twoPlayers;
	}

	public Map<Position, Piece> getBoardConfig() {
		return boardConfig;
	}

	public void setBoardConfig(Map<Position, Piece> boardConfig) {
		this.boardConfig = boardConfig;
	}

	public BeamCannon getRedCannon() {
		return redCannon;
	}

	public void setRedCannon(BeamCannon redCannon) {
		this.redCannon = redCannon;
	}

	public BeamCannon getSilverCannon() {
		return silverCannon;
	}

	public void setSilverCannon(BeamCannon silverCannon) {
		this.silverCannon = silverCannon;
	}
}
