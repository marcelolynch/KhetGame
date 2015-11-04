package poo.khet;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import poo.khet.gameutils.Direction;

public class SquaresTest {

    // Piezas genericas para colocar
    static Piece redPiece;
    static Piece silverPiece;

    Square regular;
    ReservedSquare reservedRed;
    ReservedSquare reservedSilver;

    @BeforeClass
    public static void setPieces() {
        redPiece = new Pyramid(Team.RED, Direction.NORTH);
        silverPiece = new Pyramid(Team.SILVER, Direction.NORTH);
    }

    @Before
    public void setUp() throws Exception {
        // Nuevas para cada etapa, por las dudas
        regular = new Square();
        reservedRed = new ReservedSquare(Team.RED);
        reservedSilver = new ReservedSquare(Team.SILVER);
    }


    @Test
    public void testSetAndWithdraw() {
        regular.setOccupant(redPiece);
        regular.withdrawOccupant();

        reservedRed.setOccupant(redPiece);
        reservedRed.withdrawOccupant();

        reservedSilver.setOccupant(silverPiece);
        reservedSilver.withdrawOccupant();
    }

    // Tratar de colocar pieza en casilla ocupada
    @Test(expected = IllegalStateException.class)
    public void testSetInOccupied() {
        regular.setOccupant(redPiece);
        regular.setOccupant(redPiece);
    }

    // Tratar de retirar pieza de casilla vacia
    @Test(expected = IllegalStateException.class)
    public void testWithdrawEmpty() {
        regular.withdrawOccupant();
    }

    // Deben comenzar vacias
    @Test
    public void testIsEmpty() {
        assertTrue(regular.isEmpty());
        assertTrue(reservedRed.isEmpty());
    }

    @Test
    public void emptyAfterWithdrawal() {
        regular.setOccupant(redPiece);
        assertFalse(regular.isEmpty());
        regular.withdrawOccupant();
        assertTrue(regular.isEmpty());
    }

    @Test
    public void testCanAccomodate() {
        assertTrue(regular.canAccomodate(redPiece));
        assertTrue(regular.canAccomodate(silverPiece));

        assertTrue(reservedRed.canAccomodate(redPiece));
        assertFalse(reservedRed.canAccomodate(silverPiece));

        assertTrue(reservedSilver.canAccomodate(silverPiece));
        assertFalse(reservedSilver.canAccomodate(redPiece));
    }


    @Test
    public void testCanAccomodateAfterAccomodation() {
        regular.setOccupant(redPiece);
        assertFalse(regular.canAccomodate(silverPiece));

        reservedRed.setOccupant(redPiece);
        assertFalse(reservedRed.canAccomodate(redPiece));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testAccomodateInReserved() {
        reservedRed.setOccupant(silverPiece);
    }


    // Test de interaccion con el escarabajo (enroca con piramides)
    @Test
    public void testAcceptScarab() {
        regular.setOccupant(silverPiece);
        assertTrue(regular.canAccomodate(new Scarab(Team.RED, Direction.NORTH)));
    }

    @Test
    public void testRejectInvalidScarab() {
        reservedRed.setOccupant(redPiece);
        assertFalse(reservedRed.canAccomodate(new Scarab(Team.SILVER, Direction.NORTH)));

    }

}
