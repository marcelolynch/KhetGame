package graphics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import poo.khet.Piece;
import poo.khet.Anubis;
import poo.khet.BeamCannon;
import poo.khet.Board;
import poo.khet.CannonPositions;
import poo.khet.Editor;
import poo.khet.Game;
import poo.khet.Pyramid;
import poo.khet.RedCannon;
import poo.khet.Pharaoh;
import poo.khet.Scarab;
import poo.khet.SilverCannon;
import poo.khet.Team;
import poo.khet.gameutils.BoardDimensions;
import poo.khet.gameutils.Direction;
import poo.khet.gameutils.Position;

public class GameDrawer implements CannonPositions, BoardDimensions, GraphicDimensions{

	/**
	 * Este mapa guarda una imagen por cada pieza posible:
	 * la pieza se identifica por su tipo, equipo y orientacion,
	 * y funciona como <i>key</i> del mapa. El <i>value</i> asociado es
	 * de tipo {@link Image}, y sera lo que se dibujara en pantalla representando
	 * a la pieza
	 * 
	 * @see Image
	 */
    private Map<Piece, Image> imageMap = new HashMap<Piece, Image>();
    
    /**
     * Mapa analogo a {@link #imageMap}, para los ca&ntilde;ones
     * 
     * @see #imageMap
     */
    private Map<BeamCannon, Image> cannonImg = new HashMap<BeamCannon, Image>();

    
    /** Imagen del rayo impactando */
    private Image beamImg = new Image("file:assets/beam/BeamPoint.png");

    /** El tablero que se dibujar&aacute; */
    private Board board;
    
    /** Referencia al ca&ntilde;on del equipo rojo en el juego en curso*/
    private BeamCannon redCannon;
    
    /** Referencia al ca&ntilde;on plateado */
    private BeamCannon silverCannon;
    
    /**
     * Una lista de las posiciones por donde pas&oacute; el ultimo rayo emitido
     * 
     * @see Game#getBeamTrace()
     */
    private List<Position> beamTrace;

    
    
    public GameDrawer(Game game) {
        mapFiller();
        this.board = game.getBoard();
        this.redCannon = game.getBeamCannon(Team.RED);
        this.silverCannon = game.getBeamCannon(Team.SILVER);
        this.beamTrace = game.getBeamTrace();
    }
    
    

    // Para el EDITORRR
    public GameDrawer(Editor editor) {
        mapFiller();
        this.board = editor.getBoard();
    }
    // ------------------------------

    public void draw(GraphicsContext gc) {
        drawPieces(gc);
        drawBeam(gc);
    }


    public void drawPieces(GraphicsContext gc) {

        gc.clearRect(SQUARE_SIZE * RED_CANNON_POSITION.getCol(),
                SQUARE_SIZE * RED_CANNON_POSITION.getRow(), SQUARE_SIZE, SQUARE_SIZE);
        gc.drawImage(cannonImg.get(redCannon), SQUARE_SIZE * RED_CANNON_POSITION.getCol(),
                SQUARE_SIZE * RED_CANNON_POSITION.getRow());

        gc.clearRect(SQUARE_SIZE * SILVER_CANNON_POSITION.getCol(),
                SQUARE_SIZE * SILVER_CANNON_POSITION.getRow(), SQUARE_SIZE, SQUARE_SIZE);
        gc.drawImage(cannonImg.get(silverCannon), SQUARE_SIZE * (SILVER_CANNON_POSITION.getCol()),
                SQUARE_SIZE * (SILVER_CANNON_POSITION.getRow()));

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                Position pos = new Position(i, j);
                if (board.isInBounds(pos) && !board.isEmptyPosition(pos)) { // Me protejo contra las
                                                                            // esquinas que no son
                                                                            // del tablero
                    Piece piece = board.getOccupantIn(pos);
                    gc.clearRect(SQUARE_SIZE * j, SQUARE_SIZE * i, SQUARE_SIZE, SQUARE_SIZE);
                    gc.drawImage(imageMap.get(piece), SQUARE_SIZE * j + 1, SQUARE_SIZE * i + 1);
                }
            }
        }
    }

    public void drawBeam(GraphicsContext gc) {
        Position prev = null;
        Image toDraw;

        for (Position each : beamTrace) {
            if (prev != null) {
                if (board.isEmptyPosition(prev)) {
                    toDraw = beamImageFromPath(prev, each);
                    gc.drawImage(toDraw, prev.getCol() * SQUARE_SIZE, prev.getRow() * SQUARE_SIZE);
                } else {
                    gc.drawImage(beamImg, prev.getCol() * SQUARE_SIZE, prev.getRow() * SQUARE_SIZE);
                }
            }
            prev = each;
        }

        // Me protejo contra un beamTrace vacio (prev queda en null)
        if (prev != null) {
            gc.drawImage(beamImg, prev.getCol() * SQUARE_SIZE, prev.getRow() * SQUARE_SIZE);
        }

    }


    Image beamH = new Image("file:assets/beam/BeamHorizontal.png");
    Image beamV = new Image("file:assets/beam/BeamVertical.png");

    private Image beamImageFromPath(Position prev, Position now) {
        int deltaCol = now.getCol() - prev.getCol();
        int deltaRow = now.getRow() - prev.getRow();

        if (deltaCol != 0) {
            return beamH;
        } else if (deltaRow != 0) {
            return beamV;
        } else {
            System.out.println("What");
            return null;
        }
    }

    
    /**
     * Carga los recursos (imagenes) en un el mapa de imagenes
     * @see #imageMap
     */
    void mapFiller() {
        // Piezas
        imageMap.put(new Anubis(Team.RED, Direction.NORTH),
                new Image("file:assets/pieces/anubis/red_north.png"));
        imageMap.put(new Anubis(Team.RED, Direction.EAST),
                new Image("file:assets/pieces/anubis/red_east.png"));
        imageMap.put(new Anubis(Team.RED, Direction.WEST),
                new Image("file:assets/pieces/anubis/red_west.png"));
        imageMap.put(new Anubis(Team.RED, Direction.SOUTH),
                new Image("file:assets/pieces/anubis/red_south.png"));

        imageMap.put(new Anubis(Team.SILVER, Direction.NORTH),
                new Image("file:assets/pieces/anubis/silver_north.png"));
        imageMap.put(new Anubis(Team.SILVER, Direction.EAST),
                new Image("file:assets/pieces/anubis/silver_east.png"));
        imageMap.put(new Anubis(Team.SILVER, Direction.WEST),
                new Image("file:assets/pieces/anubis/silver_west.png"));
        imageMap.put(new Anubis(Team.SILVER, Direction.SOUTH),
                new Image("file:assets/pieces/anubis/silver_south.png"));

        imageMap.put(new Scarab(Team.RED, Direction.NORTH),
                new Image("file:assets/pieces/scarab/red_north_south.png"));
        imageMap.put(new Scarab(Team.RED, Direction.SOUTH),
                new Image("file:assets/pieces/scarab/red_north_south.png"));
        imageMap.put(new Scarab(Team.RED, Direction.EAST),
                new Image("file:assets/pieces/scarab/red_east_west.png"));
        imageMap.put(new Scarab(Team.RED, Direction.WEST),
                new Image("file:assets/pieces/scarab/red_east_west.png"));

        imageMap.put(new Scarab(Team.SILVER, Direction.NORTH),
                new Image("file:assets/pieces/scarab/silver_north_south.png"));
        imageMap.put(new Scarab(Team.SILVER, Direction.SOUTH),
                new Image("file:assets/pieces/scarab/silver_north_south.png"));
        imageMap.put(new Scarab(Team.SILVER, Direction.EAST),
                new Image("file:assets/pieces/scarab/silver_east_west.png"));
        imageMap.put(new Scarab(Team.SILVER, Direction.WEST),
                new Image("file:assets/pieces/scarab/silver_east_west.png"));

        imageMap.put(new Pyramid(Team.RED, Direction.NORTH),
                new Image("file:assets/pieces/pyramid/red_north.png"));
        imageMap.put(new Pyramid(Team.RED, Direction.EAST),
                new Image("file:assets/pieces/pyramid/red_east.png"));
        imageMap.put(new Pyramid(Team.RED, Direction.WEST),
                new Image("file:assets/pieces/pyramid/red_west.png"));
        imageMap.put(new Pyramid(Team.RED, Direction.SOUTH),
                new Image("file:assets/pieces/pyramid/red_south.png"));

        imageMap.put(new Pyramid(Team.SILVER, Direction.NORTH),
                new Image("file:assets/pieces/pyramid/silver_north.png"));
        imageMap.put(new Pyramid(Team.SILVER, Direction.EAST),
                new Image("file:assets/pieces/pyramid/silver_east.png"));
        imageMap.put(new Pyramid(Team.SILVER, Direction.WEST),
                new Image("file:assets/pieces/pyramid/silver_west.png"));
        imageMap.put(new Pyramid(Team.SILVER, Direction.SOUTH),
                new Image("file:assets/pieces/pyramid/silver_south.png"));

        imageMap.put(new Pharaoh(Team.RED), new Image("file:assets/pieces/pharaoh/red.png"));
        imageMap.put(new Pharaoh(Team.SILVER), new Image("file:assets/pieces/pharaoh/silver.png"));

        // Cañones
        cannonImg.put(new RedCannon(), new Image("file:assets/cannons/red_regular.png"));
        cannonImg.put(new SilverCannon(), new Image("file:assets/cannons/silver_regular.png"));

        // Cañones rotados
        BeamCannon redSwitched = new RedCannon();
        redSwitched.switchFacing();
        cannonImg.put(redSwitched, new Image("file:assets/cannons/red_switched.png"));

        BeamCannon silverSwitched = new SilverCannon();
        silverSwitched.switchFacing();
        cannonImg.put(silverSwitched, new Image("file:assets/cannons/silver_switched.png"));
    }


}
