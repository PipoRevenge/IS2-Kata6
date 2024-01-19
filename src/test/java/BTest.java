import org.example.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BTest {
    @Test
    public void testEmptyBoard() {
        String[] emptyState = {"....","....","...."};
        Board board = new Board(emptyState);

        boolean isEmpty = board.isEmpty();

        assertTrue(isEmpty, "The board should be empty");
    }

    @Test
    public void testDeathWithFewerThanTwoNeighbors() {
        String[] initialState = {"X..","X.X","XXX"};
        Board board = new Board(initialState);

        Board nextState = board.next();

        assertFalse(nextState.isAlive(0,0), "Cell at (0,0) should die");
    }
    @Test
    public void testCreateCell(){
        String[] initialState = {"....","....","...."};
        Board board = new Board(initialState);

        board.createCell(1,2);

        assertTrue(board.isAlive(1,2), "Cell at (1,2) should be alive");
    }
    @Test
    public void testNotEmptyBoard() {
        String[] nonEmptyState = {"....","....",".X.."};
        Board board = new Board(nonEmptyState);

        boolean isEmpty= board.isEmpty();
        assertFalse(isEmpty, "The board should not be empty");
    }
}


