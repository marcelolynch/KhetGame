package poo.khet.gameutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

import poo.khet.GameState;
import poo.khet.Piece;

/**
 * {@code FileManager} se encarga de guardar datos en archivos y recuperar datos guardados previamente.
 * <p>
 * Cada archivo se identifica con un <b>name</b>.
 * <p>
 * Existen dos tipos de archivos, aquellos que contienen un <b>Map</b><{@link Position},{@link Piece}>
 * el cual representa la disposici&oacute;n de las piezas en un tablero, y otros que contienen un {@link GameState}.
 *
 * @see GameState
 */
public class FileManager {

    /**
     * Carga un archivo de juego previamente guardado por el usuario.
     * @param name Nombre del archivo
     * @return {@code GameState} El estado del juego
     * @throws IOException Si ocurri&oacute; un error en la lectura o cargado del archivo
     * @throws ClassNotFoundException Si el archivo a leer no corresponde al juego Khet
     * @see GameState
     */
    public static GameState loadGameSave(String name) throws IOException, ClassNotFoundException {
        File toRead = new File(name);
        FileInputStream fis = new FileInputStream(toRead);
        ObjectInputStream ois = new ObjectInputStream(fis);
        GameState setup = (GameState) ois.readObject();
        ois.close();
        fis.close();
        return setup;
    }

    /**
     * Carga un archivo que contiene un Map<{@link Position},{@link Piece}>
     * el cual representa la disposici&oacute;n de las piezas en un tablero.
     * @param name Nombre del archivo
     * @return Map <Position,Piece> con la ubicaci&oacute;n de las piezas
     * @throws IOException Si ocurri&oacute; un error en la lectura o cargado del archivo
     * @throws ClassNotFoundException Si el archivo a leer no corresponde al juego Khet
     */
	@SuppressWarnings("unchecked")
	public static Map<Position, Piece> loadBoardSetup(String name) throws IOException, ClassNotFoundException {
		File toRead = new File(name);
        FileInputStream fis = new FileInputStream(toRead);
        ObjectInputStream ois = new ObjectInputStream(fis);
        Map<Position, Piece> setup = (Map<Position, Piece>) ois.readObject();
        ois.close();
        fis.close();
        return setup;
	}

    /**
     * Guarda la partida a partir de un <b>{@code GameState}</b>
     * @param name Nombre del archivo a guardar
     * @param setup Configuraci&oacute;n que se desea guardar
     * @throws IOException Si ocurri&oacute; un error en la escritura o guardado del archivo
     * @see GameState
     */
    public static void writeGameFile(String name, GameState setup) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(name));
        oos.writeObject(setup);
        oos.flush();
        oos.close();
    }

    /**
     * Guarda la partida en un archivo a partir de un Map<{@link Position},{@link Piece}>
     * el cual representa la disposici&oacute;n de las piezas en un tablero.
     * @param name Nombre del archivo a guardar
     * @param boardSetup Map<{@link Position},{@link Piece}> con la ubicaci&oacute;n de las piezas
     * @throws IOException Si ocurri&oacute; un error en la escritura o guardado del archivo
     */
    public static void writeGameFile(String name, Map<Position, Piece> boardSetup) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(name));
        oos.writeObject(boardSetup);
        oos.flush();
        oos.close();
    }
    
}
