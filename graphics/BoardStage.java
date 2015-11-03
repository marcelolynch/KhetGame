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
import poo.khet.gameutils.Position;

public class BoardStage {
	
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
	
	public BoardStage(String fileName, Stage loadScreen) throws Exception{
		this.loadScreen = loadScreen;
		
		Stage primaryStage = new Stage();
		Group root = new Group();

		graphicBoard = new Canvas(750,600);
		graphicBoard.getGraphicsContext2D().drawImage(new Image("file:assets/Board.png"),0,0);
		
		piecesLayer = new Canvas(graphicBoard.getWidth(), graphicBoard.getHeight());
		piecesGC = piecesLayer.getGraphicsContext2D();
		
		
		rotateButtons = new Canvas(200,80);
		rotateButtons.getGraphicsContext2D().drawImage(new Image("file:assets/RotButtons.png"), 0, 0);
		rotateButtons.setTranslateY(graphicBoard.getHeight()+10);
		rotateButtons.setTranslateX(20);
		
		saveButton = new Canvas(75, 75);
		saveButton.getGraphicsContext2D().drawImage(new Image("file:assets/SaveButton.png"), 0, 0);
		saveButton.setTranslateY(graphicBoard.getHeight()+10);
		saveButton.setTranslateX(750-75-20);
		
		closeButton = new Canvas(75,75);
		closeButton.getGraphicsContext2D().drawImage(new Image("file:assets/CloseButton.png"), 0, 0);
		closeButton.setTranslateY(graphicBoard.getHeight()+10);
		closeButton.setTranslateX(500);

		Canvas bar  = new Canvas(750,90);
		bar.getGraphicsContext2D().drawImage(new Image("file:assets/fondo.png"), -1, 0);
		bar.setTranslateY(graphicBoard.getHeight());
	
		gameManager = new GameManager(fileName);
		
		//Cambiar aca para que ande el editor, y cambiar el tipo de gameManager arriba
		//gameManager = new EditorManager(fileName);
		
		//root.getChildren().add(bar);
		root.getChildren().add(graphicBoard);
		root.getChildren().add(piecesLayer);
		root.getChildren().add(rotateButtons);
		root.getChildren().add(saveButton);
		root.getChildren().add(closeButton);
		closeButton.toBack();
		
		drawer = gameManager.getDrawer();	
		drawGame();
		
        piecesLayer.addEventHandler(MouseEvent.MOUSE_CLICKED, 
        		new EventHandler<MouseEvent>(){
        			public void handle(MouseEvent e) {
        				if(!gameManager.hasWinner()){
        					Position selectedPos = getPositionFromMouse(e.getX(), e.getY());
        					piecesGC.clearRect((selectedPos.getRow()*75 - 1), (selectedPos.getCol()*75 - 1), 77, 77);
        				
        					if(e.getButton() == MouseButton.PRIMARY){
        						gameManager.handle(selectedPos);
        					}
        					else if(e.getButton() == MouseButton.SECONDARY){
        						gameManager.resetTurn();
        					}
        					drawGame();
        					
        					//A esta altura el juego pudo haber cambiado el estado
            				if (gameManager.hasWinner()) {
           					Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("Fin del Juego");
          					alert.setHeaderText(null);
            					alert.setContentText("FIN DEL JUEGO: Ganador: " + gameManager.getWinnerTeam());

            					alert.showAndWait();
            					closeButton.toFront();
            					rotateButtons.toBack();
            				}
        				}
        			}
        });
        
		

        rotateButtons.addEventHandler(MouseEvent.MOUSE_CLICKED, 
        		new EventHandler<MouseEvent>(){
        			public void handle(MouseEvent e) {
        			gameManager.handleRotation(e.getX() < 98);
        			drawGame();
        		}
        });
        
        saveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
        		new EventHandler<MouseEvent>() {
					public void handle(MouseEvent e) {
					new SaveStage(gameManager);
				}
        });

        
        closeButton.addEventHandler(MouseEvent.MOUSE_CLICKED, 
        		new EventHandler<MouseEvent>() {
					public void handle(MouseEvent e) {
						primaryStage.close();
						loadScreen.show();
				}
        });
        
        primaryStage.setWidth(750); 
        primaryStage.setHeight(720);
        primaryStage.setResizable(false);
		primaryStage.setTitle("Khet - The Laser Game");
		primaryStage.setScene(new Scene(root, graphicBoard.getWidth() + 50, graphicBoard.getHeight()+ rotateButtons.getHeight() + 50));
        primaryStage.show();
	}

	private void drawGame(){
		piecesGC.clearRect(0, 0, piecesLayer.getWidth(), piecesLayer.getHeight());
		drawer.draw(piecesGC);
		
		// Resalta pieza seleccionada
		if (!gameManager.isChoosing()) {
			Position selected = gameManager.getActiveSquare();
			piecesGC.drawImage(new Image("file:assets/select.png"), selected.getCol()*75, selected.getRow()*75);
		}
	}
	
	/**
	 * Devuelve una coordenada a partir de la posicion del mouse
	 * @param x: 
	 * @param y
	 * @return - Coordinate: la coordenada del tablero correspondiente al click
	 */
	private Position getPositionFromMouse(double x, double y) {
		int squareSize = 75;
		Position c = new Position((int)y / squareSize, (int)x / squareSize);
		return c;
	}

}