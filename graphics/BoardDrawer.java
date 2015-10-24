package graphics;

import java.util.HashMap;
import java.util.Map;


import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import poo.khet.Piece;
import poo.khet.Anubis;
import poo.khet.Board;
import poo.khet.Pyramid;
import poo.khet.Pharaoh;
import poo.khet.Scarab;
import poo.khet.Team;
import poo.khet.gameutils.Direction;
import poo.khet.gameutils.Position;


public class BoardDrawer{
	
    static final int COLUMNS = 10;
    static final int ROWS = 8;
    static final int SQUARE_SIZE = 75;
    
	Map<Piece, Image> imageMap = new HashMap<Piece, Image>();
	GraphicsContext graphicsContext;
	
	public BoardDrawer(GraphicsContext gc) {
		mapFiller(imageMap);
		graphicsContext = gc;
	} 
	
	
	public void draw(Board board){
		Position pos;
		Piece piece;
		for(int i=1; i<ROWS ;i++){
			for(int j=0; j<COLUMNS; j++){
				pos = new Position(i, j);
				if(board.isInBounds(pos) && !board.isEmptyPosition(pos)){
					piece = board.getOccupantIn(pos);
					System.out.println(piece + "... en: (" + i + ", " + ","+ j+ ")");
					graphicsContext.clearRect(SQUARE_SIZE*j, SQUARE_SIZE*i, SQUARE_SIZE, SQUARE_SIZE);
					graphicsContext.drawImage(imageMap.get(piece), SQUARE_SIZE*j + 1, SQUARE_SIZE*i + 1);
				}
			}
		}
	}
	
	/*
	boolean cannonPosition(Position pos){
		return (pos.getRow() == 0 && pos.getCol()==0) || (pos.getRow()==ROWS-1 && pos.getCol() == COLUMNS-1);
	}
	*/
	
	/**
	 * Cargar imagenes
	 * @param imageMap
	 */
	void mapFiller(Map<Piece, Image> imageMap){
		imageMap.put(new Anubis(Team.RED, Direction.NORTH), new Image("file:assets/Shield Red.png"));
		imageMap.put(new Anubis(Team.RED, Direction.EAST), new Image("file:assets/Shield Red.png"));
		imageMap.put(new Anubis(Team.RED, Direction.WEST), new Image("file:assets/Shield Red.png"));
		imageMap.put(new Anubis(Team.RED, Direction.SOUTH), new Image("file:assets/Shield Red.png"));

		imageMap.put(new Anubis(Team.SILVER, Direction.NORTH), new Image("file:assets/Shield Silver.png"));
		imageMap.put(new Anubis(Team.SILVER, Direction.EAST), new Image("file:assets/Shield Silver.png"));
		imageMap.put(new Anubis(Team.SILVER, Direction.WEST), new Image("file:assets/Shield Silver.png"));
		imageMap.put(new Anubis(Team.SILVER, Direction.SOUTH), new Image("file:assets/Shield Silver.png"));

		imageMap.put(new Scarab(Team.RED, Direction.NORTH), new Image("file:assets/Double Mirror Red.png"));
		imageMap.put(new Scarab(Team.RED, Direction.EAST), new Image("file:assets/Double Mirror Red.png"));
		imageMap.put(new Scarab(Team.RED, Direction.WEST), new Image("file:assets/Double Mirror Red.png"));
		imageMap.put(new Scarab(Team.RED, Direction.SOUTH), new Image("file:assets/Double Mirror Red.png"));

		imageMap.put(new Scarab(Team.SILVER, Direction.NORTH), new Image("file:assets/Double Mirror Silver.png"));
		imageMap.put(new Scarab(Team.SILVER, Direction.EAST), new Image("file:assets/Double Mirror Silver.png"));
		imageMap.put(new Scarab(Team.SILVER, Direction.WEST), new Image("file:assets/Double Mirror Silver.png"));
		imageMap.put(new Scarab(Team.SILVER, Direction.SOUTH), new Image("file:assets/Double Mirror Silver.png"));

		imageMap.put(new Pyramid(Team.RED, Direction.NORTH), new Image("file:assets/Mirror Red.png"));
		imageMap.put(new Pyramid(Team.RED, Direction.EAST), new Image("file:assets/Mirror Red.png"));
		imageMap.put(new Pyramid(Team.RED, Direction.WEST), new Image("file:assets/Mirror Red.png"));
		imageMap.put(new Pyramid(Team.RED, Direction.SOUTH), new Image("file:assets/Mirror Red.png"));

		imageMap.put(new Pyramid(Team.SILVER, Direction.NORTH), new Image("file:assets/Mirror Silver.png"));
		imageMap.put(new Pyramid(Team.SILVER, Direction.EAST), new Image("file:assets/Mirror Silver.png"));
		imageMap.put(new Pyramid(Team.SILVER, Direction.WEST), new Image("file:assets/Mirror Silver.png"));
		imageMap.put(new Pyramid(Team.SILVER, Direction.SOUTH), new Image("file:assets/Mirror Silver.png"));

		imageMap.put(new Pharaoh(Team.RED), new Image("file:assets/King Red.png"));
		imageMap.put(new Pharaoh(Team.SILVER), new Image("file:assets/King Silver.png"));

	}

	
}