package graphics;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import poo.khet.gameutils.Position;

@Deprecated
public class UIPiece {
	
		public void draw(GraphicsContext gc, Position c, boolean horizontal){
		if(horizontal){
			Image image = new Image("file:assets/BeamGreen.png");
			gc.drawImage(image, c.getRow()*75, c.getCol()*75 + 33);
			}
		else{
			Image image = new Image("file:assets/BeamGreen2.png");
			gc.drawImage(image, c.getRow()*75 + 33, c.getCol()*75 + 1);

		}
	}


}
