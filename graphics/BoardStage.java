package graphics;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import poo.khet.gameutils.GameMode;
import poo.khet.gameutils.Position;

import java.io.IOException;

public class BoardStage implements GraphicDimensions {

    Stage loadScreen;

    GameManager gameManager;
    GraphicsContext piecesGC;
    GraphicsContext beamGC;
    Canvas graphicBoard;
    Canvas piecesLayer;
    Canvas beamLayer;
    Canvas rotateButtons;
    Canvas saveButton;
    Canvas closeButton;
    GameDrawer drawer;

    public BoardStage(String fileName, final Stage loadScreen) throws Exception, IOException {
        gameManager = new GameManager(fileName);
        setStage(loadScreen);
    }
    
    public BoardStage(String fileName, GameMode mode, final Stage loadScreen) throws Exception, IOException {
    	gameManager = new GameManager(fileName, mode);
    	setStage(loadScreen);
    }
    
    private void setStage(final Stage loadScreen) {
        this.loadScreen = loadScreen;

        final Stage primaryStage = new Stage();
        Group root = new Group();

        graphicBoard = new Canvas(750, 600);
        graphicBoard.getGraphicsContext2D().drawImage(new Image("file:assets/Board.png"), 0, 0);

        piecesLayer = new Canvas(graphicBoard.getWidth(), graphicBoard.getHeight());
        piecesGC = piecesLayer.getGraphicsContext2D();

        rotateButtons = new Canvas(200, 80);
        rotateButtons.getGraphicsContext2D().drawImage(new Image("file:assets/RotButtons.png"), 0, 0);
        rotateButtons.setTranslateY(graphicBoard.getHeight() + 10);
        rotateButtons.setTranslateX(20);

        closeButton = new Canvas(SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        closeButton.getGraphicsContext2D().drawImage(new Image("file:assets/CloseButton.png"), 0, 0);
        closeButton.setTranslateY(graphicBoard.getHeight() + 10);
        closeButton.setTranslateX(750 - SQUARE_BUTTON_SIZE - 20);
        
        saveButton = new Canvas(SQUARE_BUTTON_SIZE, SQUARE_BUTTON_SIZE);
        saveButton.getGraphicsContext2D().drawImage(new Image("file:assets/SaveButton.png"), 0, 0);
        saveButton.setTranslateY(graphicBoard.getHeight() + 10);
        saveButton.setTranslateX(closeButton.getTranslateX() - SQUARE_BUTTON_SIZE - 20);

        Canvas bar = new Canvas(750, 120);
        bar.getGraphicsContext2D().drawImage(new Image("file:assets/bar.png"), 0, 0);
        bar.setTranslateY(graphicBoard.getHeight());

        root.getChildren().add(bar);
        root.getChildren().add(graphicBoard);
        root.getChildren().add(piecesLayer);
        root.getChildren().add(rotateButtons);
        root.getChildren().add(saveButton);
        root.getChildren().add(closeButton);

        drawer = gameManager.getDrawer();
        drawGame();

        piecesLayer.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                if (!gameManager.hasWinner()) {
                    Position selectedPos = getPositionFromMouse(e.getX(), e.getY());
                    piecesGC.clearRect((selectedPos.getRow() * SQUARE_SIZE),
                            (selectedPos.getCol() * SQUARE_SIZE), SQUARE_SIZE, SQUARE_SIZE);

                    if (e.getButton() == MouseButton.PRIMARY) {
                        gameManager.handle(selectedPos);
                    } else if (e.getButton() == MouseButton.SECONDARY && !gameManager.inStandby()) {
                        gameManager.resetTurn();
                    }
           
                    drawGame();

                    // A esta altura el juego pudo haber cambiado el estado
                    if (gameManager.hasWinner()) {
                    	showWinner();
                    }
                }
            }
        });

        rotateButtons.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
            	if(!gameManager.hasWinner()) {
            		gameManager.handleRotation(e.getX() < 100);
                	drawGame();
                	if (gameManager.hasWinner()) {
                		showWinner();
                	}
            	}
            }
        });

        saveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
            	if (!gameManager.hasWinner()) {
            		new SaveStage(gameManager);
            	}
            }
        });


        closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                primaryStage.close();
                loadScreen.show();
            }
        });

        primaryStage.setWidth(750);
        primaryStage.setHeight(720);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Khet - The Laser Game");
        primaryStage.setScene(new Scene(root, graphicBoard.getWidth() + 50,
                graphicBoard.getHeight() + rotateButtons.getHeight() + 50));
        primaryStage.show();

    }

    private void showWinner() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Fin del Juego");
        alert.setHeaderText(null);
        alert.setContentText(
                "FIN DEL JUEGO: Ganador: " + gameManager.getWinnerTeam());
        alert.showAndWait();
        closeButton.toFront();
        rotateButtons.toBack();		
	}

	private void drawGame() {
        piecesGC.clearRect(0, 0, piecesLayer.getWidth(), piecesLayer.getHeight());
        drawer.draw(piecesGC);

        // Resalta pieza seleccionada
        if (gameManager.isChosen()) {
        	rotateButtons.toFront();
            Position selected = gameManager.getActiveSquare();
            piecesGC.drawImage(new Image("file:assets/select.png"), selected.getCol() * SQUARE_SIZE,
                    selected.getRow() * SQUARE_SIZE);
        }
        
        else if(gameManager.inStandby()){
        	saveButton.toBack();
        	rotateButtons.toBack();
        }
        else{
        	rotateButtons.toBack();
        	saveButton.toFront();
        }
        
        if(gameManager.hasWinner()){
        	saveButton.toBack();
        }
   }

    /**
     * Devuelve una coordenada a partir de la posicion del mouse
     * 
     * @param x:
     * @param y
     * @return - Coordinate: la coordenada del tablero correspondiente al click
     */
    private Position getPositionFromMouse(double x, double y) {
        Position p = new Position((int) y / SQUARE_SIZE, (int) x / SQUARE_SIZE);
        return p;
    }

}
