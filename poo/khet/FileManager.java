package poo.khet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Clase con metodos para guardar y cargar archivos del juego, a partir de la clase GameSetup.
 */
public class FileManager {

    /**
     * Lee el GameFile guardado previamente identificandolo con <tt>name</tt>
     * 
     * @param name - nombre del archivo
     * @return GameState - estado del juego cargado
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public static GameState loadGameFile(String name) throws IOException, ClassNotFoundException {
        File toRead = new File(name);
        FileInputStream fis = new FileInputStream(toRead);
        ObjectInputStream ois = new ObjectInputStream(fis);
        GameState setup = (GameState) ois.readObject();
        ois.close();
        fis.close();
        return setup;
    }

    /**
     * Guarda en un archivo el estado del juego.
     * 
     * @param name - nombre del archivo
     * @param setup - estado del juego a guardar
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static void writeGameFile(String name, GameState setup) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(name));
        oos.writeObject(setup);
        oos.flush();
        oos.close();
    }
}
