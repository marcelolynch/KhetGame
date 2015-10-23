package graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import poo.khet.gameutils.Position;

public class UIPiece {
	
		public void draw(GraphicsContext gc, Position c){
		Image image = new Image("file:assets/King Red.png");
		gc.drawImage(image, c.getRow()*75 + 1, c.getCol()*75 + 1);
	}


}
