package poo.khet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.*;
import java.io.*;


import poo.khet.gameutils.Position;

// parece como que puede ser estatica. ver
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
    
    public static GameSetup loadGameFile(String name) throws IOException, ClassNotFoundException {
		File toRead = new File(name);
		FileInputStream fis = new FileInputStream(toRead);
		ObjectInputStream ois = new ObjectInputStream(fis);
		GameSetup setup = (GameSetup)ois.readObject();
		ois.close();
		fis.close();
		return setup;
	}
	
	//TODO: Exceptions?
	// tendria que haber un GameMode para poder guardarlo y cargarlo
	public static void writeGameFile(String name, Board board /*, GameSetup setup*/) throws FileNotFoundException, IOException {
		// O lo hacemos afuera? en la funcion que llama a writeGameFile. Digo porque capaz es raro de que se encargue
		// aca de cambiar el GameSetup. Es como que son cosas que tendria que hacer Game
		
		HashMap<Position, Piece> boardConfig = new HashMap<>();
		for (int i=0; i<ROWS; i++) {
			for (int j=0; j<COLUMNS; j++) {
				Position pos = new Position(i, j);
				if (board.isInBounds(pos) && !board.isEmptyPosition(pos)) {
					boardConfig.put(pos, board.getOccupantIn(pos));
				}
			}
		}
		//setup.setBoardConfig(boardConfig);
		
		ObjectOutputStream oos = new ObjectOutputStream( 
				new FileOutputStream(name)); 
		oos.writeObject(boardConfig);
		//TODO: algo de serializable sino tira IOException
		System.out.println("Saved: " + name);
		oos.flush();
		oos.close();
	}
}
