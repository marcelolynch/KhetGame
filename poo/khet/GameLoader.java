package poo.khet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import java.io.*;


import poo.khet.gameutils.Position;

public class GameLoader { // no me convence el nombre porque tambien se encarga de guardar
	static final int COLUMNS = 10;
    static final int ROWS = 8;
    
    /**
     * Lee el GameFile guardado previamente identificandolo con <tt>name</tt> 
     * Solo devuelve el mapa de coordenadas-Piezas 
     * @param name
     * @return HashMap<Coordinate, Piece>
     * @throws IOException
     * @throws ClassNotFoundException
     */
    
    @SuppressWarnings("unchecked")
	public HashMap<Position, Piece> loadGameFile(String name) throws IOException, ClassNotFoundException {
		File toRead = new File(name);
		FileInputStream fis = new FileInputStream(toRead);
		ObjectInputStream ois = new ObjectInputStream(fis);
		HashMap<Position, Piece> piecesConfig = (HashMap<Position,Piece>)ois.readObject();
		ois.close();
		fis.close();
		return piecesConfig;
		// Lee el archivo antes creado con WriteGameFile , lee SOLO el mapa y lo devuelve(Por ahora)
		
	}
	
	//TODO: Exceptions?
	// tendria que haber un GameMode para poder guardarlo y cargarlo
	public void writeGameFile(String name, Board board) throws FileNotFoundException, IOException {
		HashMap<Position, Piece> piecesConfig = new HashMap<Position, Piece>();
		
		for (int i=0; i<ROWS; i++) {
			for (int j=0; j<COLUMNS; j++) {
				Position coord = new Position(i, j);
				Square square = board.getPosition(coord);
				if (!square.isEmpty()) {
					piecesConfig.put(coord, square.getOccupant());
				}
			}
		}

		ObjectOutputStream oos = new ObjectOutputStream( 
				new FileOutputStream(name)); // de esto no estoy seguro
		oos.writeObject(piecesConfig);
		oos.writeObject(board.getBeamCannon(Team.RED));
		oos.writeObject(board.getBeamCannon(Team.SILVER));
		// Algun modo de juego o algo de eso tambien se puede escribir
		oos.flush();
		oos.close();
	}
	

	
	// TESTEO
//	private HashMap<Coordinate, Piece> conf1Gen() {
//		HashMap<Coordinate, Piece> pieces = new HashMap<Coordinate, Piece>();
//		
//		pieces.put(new Coordinate(3,0), new Pyramid(Team.RED, Direction.EAST));
//		pieces.put(new Coordinate(4,0), new Pyramid(Team.RED, Direction.NORTH));
//		pieces.put(new Coordinate(3,2), new Pyramid(Team.SILVER, Direction.WEST));
//		pieces.put(new Coordinate(4,2), new Pyramid(Team.SILVER, Direction.SOUTH));
//		pieces.put(new Coordinate(0,2), new Pyramid(Team.SILVER, Direction.WEST));
//		pieces.put(new Coordinate(6,2), new Pyramid(Team.RED, Direction.SOUTH));
//		pieces.put(new Coordinate(4,4), new Scarab(Team.RED, Direction.NORTH));
//		pieces.put(new Coordinate(3,5), new Scarab(Team.SILVER, Direction.NORTH));
//		pieces.put(new Coordinate(4,5), new Scarab(Team.RED, Direction.WEST));
//		pieces.put(new Coordinate(3,4), new Scarab(Team.SILVER, Direction.WEST));
//		
//		pieces.put(new Coordinate(4,9), new Pyramid(Team.SILVER, Direction.WEST));
//		pieces.put(new Coordinate(3,9), new Pyramid(Team.SILVER, Direction.SOUTH));
//		pieces.put(new Coordinate(4,7), new Pyramid(Team.RED, Direction.EAST));
//		pieces.put(new Coordinate(3,7), new Pyramid(Team.RED, Direction.NORTH));
//
//		pieces.put(new Coordinate(2,6), new Pyramid(Team.RED, Direction.EAST));
//		
//		
//		
//		
//		return pieces; 
//	}
//	
//	public static void main(String[] args) {
//		//System.out.println(config1.get(new Coordinate(0, 3)).getClass());
//		for (int i=7; i>=0; i--) {
//			String str = "";
//
//			for (int j=0; j<10; j++) {
//				Piece piece = config1.get(new Coordinate(i, j));
//				if (piece == null) {
//					str = str.concat("🔲");
//
//				}else if (piece instanceof Pyramid){
//					str = str.concat("P" +  printFace(piece.getFacing()));
//				} else if (piece instanceof Scarab) {
//					str = str.concat("S" +  printFace(piece.getFacing()));
//				} else if (piece instanceof Anubis) {
//					str = str.concat("A" +  printFace(piece.getFacing()));
//				} else if (piece instanceof Pharaoh) {
//					str = str.concat("F" +  printFace(piece.getFacing()));
//				} else {
//					str = str.concat("Paso algo malo");
//				}
//			}
//			System.out.println(str);
//
//		}
//	}
//		
//	private static String printFace(Direction dir) {
//		if (dir.equals(Direction.NORTH))
//			return "N";
//		if (dir.equals(Direction.EAST))
//			return "E";
//		if (dir.equals(Direction.SOUTH))
//			return "S";
//		if (dir.equals(Direction.WEST))
//			return "W";
//		return "O";
//		
//	}
//	
}
