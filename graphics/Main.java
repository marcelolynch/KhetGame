package graphics;


import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import poo.khet.gameutils.Position;

public class Main extends Application{
	
	GameManager gameManager;
	GraphicsContext piecesGC;
	Canvas graphicBoard;
	Canvas piecesLayer;
	Canvas rotateButtons;
	BoardDrawer boardDrawer;
	
	public void start(Stage primaryStage) throws Exception{
		Group root = new Group();

		graphicBoard = new Canvas(750,600);
		graphicBoard.getGraphicsContext2D().drawImage(new Image("file:assets/Board.png"),0,0);

		piecesLayer = new Canvas(graphicBoard.getWidth(), graphicBoard.getHeight());
		piecesGC = piecesLayer.getGraphicsContext2D();
		
		boardDrawer = new BoardDrawer(piecesGC);
		
		rotateButtons = new Canvas(200,80);
		rotateButtons.getGraphicsContext2D().drawImage(new Image("file:assets/RotButtons.png"), 0, 0);
		rotateButtons.setTranslateY(graphicBoard.getHeight()+10);
		rotateButtons.setTranslateX(20);
	
		Button rotateCWButton = new Button("Rotate clockwise");
		rotateCWButton.setTranslateY(graphicBoard.getHeight() + 10);
		rotateCWButton.setTranslateX(10);

		Button rotateCCWButton= new Button("Rotate counterclockwise");
		rotateCCWButton.setTranslateY(graphicBoard.getHeight() + 10);
		rotateCCWButton.setTranslateX(120);

		
		gameManager = new GameManager(null);
	
	
		root.getChildren().add(graphicBoard);
		root.getChildren().add(piecesLayer);
		root.getChildren().add(rotateButtons);
		
		drawBoard();
		
        piecesLayer.addEventHandler(MouseEvent.MOUSE_CLICKED, 
        		new EventHandler<MouseEvent>(){
        			public void handle(MouseEvent e) {
    					Position selectedPos = getPositionFromMouse(e.getX(), e.getY());
        				piecesGC.clearRect((selectedPos.getRow()*75 - 1), (selectedPos.getCol()*75 - 1), 77, 77);
        				
        				if(e.getButton() == MouseButton.PRIMARY){
        					gameManager.handle(selectedPos, true);
        				}
        				else if(e.getButton() == MouseButton.SECONDARY){
        					gameManager.resetTurn();
        				}
        				drawBoard(); 
        			}
        });
		

        rotateButtons.addEventHandler(MouseEvent.MOUSE_CLICKED, 
        		new EventHandler<MouseEvent>(){
        			public void handle(MouseEvent e) {
        			gameManager.handleRotation(e.getX() < 98);
        			drawBoard();
        			}
        });

        primaryStage.setWidth(750); 
        primaryStage.setHeight(720);
        primaryStage.setResizable(false);
		primaryStage.setTitle("Khet � The Laser Game");
        primaryStage.setScene(new Scene(root, graphicBoard.getWidth() + 50, graphicBoard.getHeight()+ rotateButtons.getHeight() + 50));
        primaryStage.show();
	}

	private void drawBoard(){
		piecesGC.clearRect(0, 0, piecesLayer.getWidth(), piecesLayer.getHeight());
		boardDrawer.draw(gameManager.getBoard());
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
		System.out.println("Coord: (" + c.getRow() + ", " + c.getCol() + ")");
		return c;
	}

	public static void main(String[] args) {
		launch(args);
	}

}
