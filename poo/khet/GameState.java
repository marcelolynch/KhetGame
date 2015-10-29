package poo.khet;
import poo.khet.gameutils.Position;

import java.io.Serializable;
import java.util.Map;

// TODO: hacen falta los setters? Si Game no sabe si isTwoPlayers parece que por lo menos hace falta ese setter
// para que lo pueda usar GameManager.
public class GameState implements Serializable {
	//TODO: Preguntar 
	private static final long serialVersionUID = 1L;
	
	private boolean twoPlayers;
	private Map<Position, Piece> boardConfig;
	private BeamCannon redCannon;
	private BeamCannon silverCannon;
	private Team movingTeam;
	
	//Todo en periodo de prueba
	public GameState(boolean twoPlayers, Map<Position, Piece> boardConfig, Team movingTeam,
			BeamCannon redCannon, BeamCannon silverCannon) {
		super();
		this.twoPlayers = twoPlayers;
		this.boardConfig = boardConfig;
		this.movingTeam = movingTeam;
		this.redCannon = redCannon;
		this.silverCannon = silverCannon;
	}
	
	// Provisorio, para que ande el GameManager mientras no tengamos GameStates para cargar
	public GameState(boolean twoPlayers, Map<Position, Piece> boardConfig) {
		super();
		this.twoPlayers = twoPlayers;
		this.boardConfig = boardConfig;
		movingTeam = Team.SILVER;
		redCannon = new BeamCannon(Team.RED);
		silverCannon = new BeamCannon(Team.SILVER);
	}

	public boolean isTwoPlayers() {
		return twoPlayers;
	}

	public Team getMovingTeam() {
		return movingTeam;
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
