package graphics;

import javafx.scene.canvas.GraphicsContext;
import poo.khet.Coordinate;


@Deprecated
public class BoardDrawer {

	static final int COLUMNS = 10;
	static final int ROWS = 8;

	static final private Coordinate SILVER_CANNON_POS = new Coordinate(0, 0);
	static final private Coordinate RED_CANNON_POS = new Coordinate(ROWS-1, COLUMNS-1);

	void draw(GraphicsContext gc){
			for (int i=0; i < ROWS; i++) {
			    for(int j=0; j < COLUMNS ; j++){
			        if(j == 0 && i != SILVER_CANNON_POS.getX()) {
			        	UISquare.SILVER.draw(gc, j*80 + 5, i*80 + 5);
			        }
			        else if(j == COLUMNS-1 && i != RED_CANNON_POS.getX()) {
			        	UISquare.RED.draw(gc, j*80+5, i*80 + 5);
			        }
			        else {
			        	UISquare.FREE.draw(gc, j*80 + 5, i*80 +5);
			        }
			    }
	        	UISquare.RED.draw(gc, 85, 5);
	        	UISquare.RED.draw(gc, 85, (ROWS-1)*80 + 5);
	        	UISquare.SILVER.draw(gc, (COLUMNS-2)*80 + 5, 5);
	        	UISquare.SILVER.draw(gc, (COLUMNS-2)*80 + 5, (ROWS-1)*80 + 5);

			}
			
			//Sobreescribo, si no los if/else se hacen muy pesados
			//Se crearon arriba 4 instancias que se dejan de referenciar aca
	}
}
