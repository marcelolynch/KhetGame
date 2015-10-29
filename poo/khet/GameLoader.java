package poo.khet;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.*;

// parece como que puede ser estatica. ver
public class GameLoader { // no me convence el nombre porque tambien se encarga de guardar
	
    /**
     * Lee el GameFile guardado previamente identificandolo con <tt>name</tt> 
     * @param name
     * @return HashMap<Coordinate, Piece>
     * @throws IOException
     * @throws ClassNotFoundException
     */
    
    public static GameState loadGameFile(String name) throws IOException, ClassNotFoundException {
		File toRead = new File(name);
		FileInputStream fis = new FileInputStream(toRead);
		ObjectInputStream ois = new ObjectInputStream(fis);
		GameState setup = (GameState)ois.readObject();
		ois.close();
		fis.close();
		return setup;
	}
	
    /**
     * Guarda en un archivo el estado del juego.
     * @param name - nombre del archivo
     * @param setup - estado del juego a guardar
     * @throws FileNotFoundException
     * @throws IOException
     */
	//TODO: Exceptions
	public static void writeGameFile(String name, GameState setup) throws FileNotFoundException, IOException {		
		ObjectOutputStream oos = new ObjectOutputStream( 
				new FileOutputStream(name)); 
		oos.writeObject(setup);
		oos.flush();
		oos.close();
	}
}
