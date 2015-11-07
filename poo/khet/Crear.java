package poo.khet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import poo.khet.gameutils.Direction;
import poo.khet.gameutils.Position;

public class Crear {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Map<Position, Piece> pMap= new HashMap<Position, Piece>();
		pMap.put(new Position(7,2), new Pyramid(Team.RED, Direction.WEST));
		pMap.put(new Position(7,3), new Anubis(Team.RED, Direction.NORTH));
		pMap.put(new Position(7,4), new Pharaoh(Team.RED));
		pMap.put(new Position(7,5), new Anubis(Team.RED, Direction.NORTH));
		pMap.put(new Position(6,7), new Pyramid(Team.RED, Direction.EAST));
		pMap.put(new Position(5,6), new Pyramid(Team.SILVER, Direction.EAST));
		pMap.put(new Position(4,0), new Pyramid(Team.SILVER, Direction.EAST));
		pMap.put(new Position(4,2), new Pyramid(Team.RED, Direction.WEST));
		pMap.put(new Position(4,7), new Pyramid(Team.SILVER, Direction.NORTH));
		pMap.put(new Position(4,9), new Pyramid(Team.RED, Direction.SOUTH));
		pMap.put(new Position(4,4), new Scarab(Team.RED, Direction.WEST));
		pMap.put(new Position(4,5), new Scarab(Team.RED, Direction.SOUTH));
		pMap.put(new Position(3,0), new Pyramid(Team.SILVER, Direction.NORTH));
		pMap.put(new Position(3,2), new Pyramid(Team.RED, Direction.SOUTH));
		pMap.put(new Position(3,7), new Pyramid(Team.SILVER, Direction.EAST));
		pMap.put(new Position(3,9), new Pyramid(Team.RED, Direction.WEST));
		pMap.put(new Position(3,4), new Scarab(Team.SILVER, Direction.NORTH));
		pMap.put(new Position(3,5), new Scarab(Team.SILVER, Direction.WEST));
		pMap.put(new Position(2,3), new Pyramid(Team.RED, Direction.WEST));
		pMap.put(new Position(1,2), new Pyramid(Team.SILVER, Direction.WEST));
		pMap.put(new Position(0,4), new Anubis(Team.SILVER, Direction.SOUTH));
		pMap.put(new Position(0,5), new Pharaoh(Team.SILVER));
		pMap.put(new Position(0,6), new Anubis(Team.SILVER, Direction.SOUTH));
		pMap.put(new Position(0,7), new Pyramid(Team.SILVER, Direction.EAST));

		FileManager.writeGameFile("defaultConfigs/d1", pMap);
	}

}
