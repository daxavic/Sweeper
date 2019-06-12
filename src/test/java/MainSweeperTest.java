import org.junit.jupiter.api.Test;
import sweeper.*;

import java.util.ArrayList;
import static java.util.Arrays.*;
import static org.junit.jupiter.api.Assertions.*;

class MainSweeperTest {


    @Test
    public void equals() {
        Coord coord1 = new Coord(1, 1);
        Coord coord2 = new Coord(1, 1);
        Coord coord3 = new Coord(2,2);
        assertEquals(coord1, coord2);
        assertEquals(coord2, coord1);
        assertNotEquals(coord1, coord3);
        assertNotEquals(coord2, coord3);
    }


    @Test
    public void getNextNumberBox() {
        assertEquals(Box.NUM3, Box.NUM2.getNextNumberBox());
        assertEquals(Box.NUM5, Box.NUM4.getNextNumberBox());
        assertEquals(Box.BOMB, Box.NUM6.getNextNumberBox());
    }

    @Test
    public void inRange() {
        new Game(9, 9, 85);
        assertTrue(Ranges.inRange(new Coord(4, 5)));
        assertTrue(Ranges.inRange(new Coord(6, 6)));
        assertFalse(Ranges.inRange(new Coord(11, 2)));
        assertFalse(Ranges.inRange(new Coord(2, 11)));
        assertFalse(Ranges.inRange(new Coord(9, 9)));
    }

    @Test
    public void getCoordAround() {
        new Game(9, 9, 20);
        assertEquals(new ArrayList<>(asList(new Coord(6, 7), new Coord(7, 6), new Coord(7, 8),
                new Coord(8, 6), new Coord(8, 7), new Coord(8, 8))), Ranges.getCoordAround(new Coord(7, 7)));
        assertEquals(new ArrayList<>(asList(new Coord(5, 5), new Coord(5, 6), new Coord(5, 7),
                new Coord(6, 5), new Coord(6, 7), new Coord(7, 6))), Ranges.getCoordAround(new Coord(6, 6)));
        assertEquals(new ArrayList<>(asList(new Coord(0, 2), new Coord(0, 4), new Coord(1, 2),
                new Coord(1, 3), new Coord(1, 4))), Ranges.getCoordAround(new Coord(0, 3)));
        assertEquals(new ArrayList<>(asList(new Coord(0, 1), new Coord(1, 0))),
                Ranges.getCoordAround(new Coord(0, 0)));
    }


}