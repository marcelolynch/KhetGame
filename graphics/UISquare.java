package graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

@Deprecated
public class UISquare {

	public final static UISquare FREE = new UISquare(1);
	public final static UISquare RED = new UISquare(2);
	public final static UISquare SILVER = new UISquare(3);
	
	Image image;
	
	private UISquare(int type){
		switch(type){
			case 1:
				image = new Image("file:assets/Square.png");
				break;
			case 2:
				image = new Image("file:assets/ReservedSquareRedB.png");
				break;
			case 3:
				image = new Image("file:assets/ReservedSquareSilverB.png");
				break;
			default:
				throw new IllegalArgumentException();
		}
	
	}
	
	public void draw(GraphicsContext gc, int x, int y){
		gc.drawImage(image, x, y);
	}
	
}
