import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import poo.khet.CannonPositions;
import poo.khet.Game;
import poo.khet.gameutils.FileManager;
import poo.khet.gameutils.GameMode;
import poo.khet.gameutils.Position;

public class GameTest implements CannonPositions {
    
    private Game testGame;
    
    @Before
    public void setUp() {
        try {
            testGame = new Game(FileManager.loadBoardSetup("defaultConfigs/d1"), GameMode.PVP);
        } catch (ClassNotFoundException e)  {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Test
    public void testValidMove() {
        assertFalse(testGame.isValidMove(RED_CANNON_POSITION, new Position(7,9)));
        assertFalse(testGame.isValidMove(new Position(7,9), RED_CANNON_POSITION));
        assertFalse(testGame.isValidMove(SILVER_CANNON_POSITION, new Position(1,0)));
        assertFalse(testGame.isValidMove(new Position(1,0), SILVER_CANNON_POSITION));
        //Turno del silver
        assertFalse(testGame.isValidMove(new Position(3, 2), new Position(2, 2)));
        assertTrue(testGame.isValidMove(new Position(1, 2), new Position(1, 1)));
        //Swap
    }
    
   @Test(expected=IllegalStateException.class)
   public void invalidMoveTwice() {
       testGame.move(new Position(3, 0), new Position(2, 0));
       testGame.move(new Position(2, 0), new Position(1, 1)); 
   }
   
   @Test(expected=IllegalStateException.class)
   public void invalidNextTurn() {       
       testGame.move(new Position(3, 0), new Position(2, 0));
       testGame.nextTurn();
       testGame.nextTurn(); // Excepcion: hay que mover antes
   }

   @Test(expected=IllegalArgumentException.class)
   public void invalidMoveWrongPlayer() {
       testGame.move(new Position(7, 2), new Position(6, 2));
   }

}
