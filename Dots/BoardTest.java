import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

public class BoardTest extends TestCase {


    int[][] preset = new int[5][5];

    public void testBoardConstructor() {
        Board fourByFour = new Board(4);
        Board fiveByFive = new Board(5);
        Board sevenBySeven = new Board(7);
        Board tenByTen = new Board(10);

        //tenByTen.printBoard();

        assertTrue(fourByFour.getNumDots() == 16);
        assertTrue(fiveByFive.getNumDots() == 25);
        assertTrue(sevenBySeven.getNumDots() == 49);
        assertTrue(tenByTen.getNumDots() == 100);
    }

    public void testPresetBoard() {
        preset[0][0] = Dot.COLOR_GREEN;  preset[1][0] = Dot.COLOR_GREEN; preset[2][0] = Dot.COLOR_RED;    preset[3][0] = Dot.COLOR_RED;    preset[4][0] = Dot.COLOR_YELLOW;
        preset[0][1] = Dot.COLOR_GREEN;  preset[1][1] = Dot.COLOR_BLUE;  preset[2][1] = Dot.COLOR_BLUE;   preset[3][1] = Dot.COLOR_BLUE;   preset[4][1] = Dot.COLOR_GREEN;
        preset[0][2] = Dot.COLOR_PURPLE; preset[1][2] = Dot.COLOR_BLUE;  preset[2][2] = Dot.COLOR_BLUE;   preset[3][2] = Dot.COLOR_PURPLE; preset[4][2] = Dot.COLOR_YELLOW;
        preset[0][3] = Dot.COLOR_PURPLE; preset[1][3] = Dot.COLOR_GREEN; preset[2][3] = Dot.COLOR_YELLOW; preset[3][3] = Dot.COLOR_PURPLE; preset[4][3] = Dot.COLOR_PURPLE;
        preset[0][4] = Dot.COLOR_RED;    preset[1][4] = Dot.COLOR_RED;   preset[2][4] = Dot.COLOR_YELLOW; preset[3][4] = Dot.COLOR_PURPLE; preset[4][4] = Dot.COLOR_PURPLE;

        Board testBoard = new Board(preset);
        Dot.resetStack();

        assertTrue(testBoard.getBoard()[0][0].getColor() == Dot.COLOR_GREEN);
        assertTrue(testBoard.getBoard()[4][4].getColor() == Dot.COLOR_PURPLE);
        assertTrue(testBoard.getBoard()[1][3].getColor() == Dot.COLOR_GREEN);
        assertTrue(testBoard.getBoard()[3][4].getColor() == Dot.COLOR_PURPLE);
        assertTrue(testBoard.getBoard()[2][3].getColor() == Dot.COLOR_YELLOW);
        assertTrue(testBoard.getBoard()[1][2].getColor() == Dot.COLOR_BLUE);
        assertTrue(testBoard.getBoard()[0][4].getColor() == Dot.COLOR_RED);

        assertFalse(testBoard.anySelected());

    }

    public void testCanMakeMove() {
        preset[0][0] = Dot.COLOR_GREEN;  preset[1][0] = Dot.COLOR_GREEN; preset[2][0] = Dot.COLOR_RED;    preset[3][0] = Dot.COLOR_RED;    preset[4][0] = Dot.COLOR_YELLOW;
        preset[0][1] = Dot.COLOR_GREEN;  preset[1][1] = Dot.COLOR_BLUE;  preset[2][1] = Dot.COLOR_BLUE;   preset[3][1] = Dot.COLOR_BLUE;   preset[4][1] = Dot.COLOR_GREEN;
        preset[0][2] = Dot.COLOR_PURPLE; preset[1][2] = Dot.COLOR_BLUE;  preset[2][2] = Dot.COLOR_BLUE;   preset[3][2] = Dot.COLOR_PURPLE; preset[4][2] = Dot.COLOR_YELLOW;
        preset[0][3] = Dot.COLOR_PURPLE; preset[1][3] = Dot.COLOR_GREEN; preset[2][3] = Dot.COLOR_YELLOW; preset[3][3] = Dot.COLOR_PURPLE; preset[4][3] = Dot.COLOR_PURPLE;
        preset[0][4] = Dot.COLOR_RED;    preset[1][4] = Dot.COLOR_RED;   preset[2][4] = Dot.COLOR_YELLOW; preset[3][4] = Dot.COLOR_PURPLE; preset[4][4] = Dot.COLOR_PURPLE;

        Board testBoard = new Board(preset);
        Dot.resetStack();

        assertTrue(testBoard.canMakeMove());
    }

    public void testIsGameOver() {
        preset[0][0] = Dot.COLOR_GREEN;  preset[1][0] = Dot.COLOR_BLUE; preset[2][0] = Dot.COLOR_GREEN;    preset[3][0] = Dot.COLOR_BLUE;  preset[4][0] = Dot.COLOR_GREEN;
        preset[0][1] = Dot.COLOR_BLUE;  preset[1][1] = Dot.COLOR_GREEN;  preset[2][1] = Dot.COLOR_BLUE;    preset[3][1] = Dot.COLOR_GREEN; preset[4][1] = Dot.COLOR_BLUE;
        preset[0][2] = Dot.COLOR_GREEN; preset[1][2] = Dot.COLOR_BLUE;  preset[2][2] = Dot.COLOR_GREEN;    preset[3][2] = Dot.COLOR_BLUE;  preset[4][2] = Dot.COLOR_GREEN;
        preset[0][3] = Dot.COLOR_BLUE;  preset[1][3] = Dot.COLOR_GREEN; preset[2][3] = Dot.COLOR_BLUE;     preset[3][3] = Dot.COLOR_GREEN; preset[4][3] = Dot.COLOR_BLUE;
        preset[0][4] = Dot.COLOR_GREEN;    preset[1][4] = Dot.COLOR_BLUE;   preset[2][4] = Dot.COLOR_GREEN; preset[3][4] = Dot.COLOR_BLUE;  preset[4][4] = Dot.COLOR_GREEN;

        Board testBoard = new Board(preset);
        Dot.resetStack();

        assertFalse(testBoard.canMakeMove());
        assertTrue(testBoard.isGameOver());
    }

    public void testCanSelect() {
        preset[0][0] = Dot.COLOR_GREEN;  preset[1][0] = Dot.COLOR_GREEN; preset[2][0] = Dot.COLOR_RED;    preset[3][0] = Dot.COLOR_RED;    preset[4][0] = Dot.COLOR_YELLOW;
        preset[0][1] = Dot.COLOR_GREEN;  preset[1][1] = Dot.COLOR_BLUE;  preset[2][1] = Dot.COLOR_BLUE;   preset[3][1] = Dot.COLOR_BLUE;   preset[4][1] = Dot.COLOR_GREEN;
        preset[0][2] = Dot.COLOR_PURPLE; preset[1][2] = Dot.COLOR_BLUE;  preset[2][2] = Dot.COLOR_BLUE;   preset[3][2] = Dot.COLOR_PURPLE; preset[4][2] = Dot.COLOR_YELLOW;
        preset[0][3] = Dot.COLOR_PURPLE; preset[1][3] = Dot.COLOR_GREEN; preset[2][3] = Dot.COLOR_YELLOW; preset[3][3] = Dot.COLOR_PURPLE; preset[4][3] = Dot.COLOR_PURPLE;
        preset[0][4] = Dot.COLOR_RED;    preset[1][4] = Dot.COLOR_RED;   preset[2][4] = Dot.COLOR_YELLOW; preset[3][4] = Dot.COLOR_PURPLE; preset[4][4] = Dot.COLOR_PURPLE;

        Board testBoard = new Board(preset);
        Dot.resetStack();

        assertFalse(testBoard.anySelected());

        testBoard.getBoard()[0][0].changeSelected();
        assertFalse(testBoard.canSelect(1,1));
        assertTrue(testBoard.canSelect(1,0));

        testBoard.getBoard()[1][0].changeSelected();
        assertFalse(testBoard.canSelect(2, 0));
        assertFalse(testBoard.canSelect(1, 0));
        //testBoard.dropRemainingDots();
        //testBoard.fillRemovedDots();
    }

    public void testSelectDot() {
        preset[0][0] = Dot.COLOR_GREEN;  preset[1][0] = Dot.COLOR_GREEN; preset[2][0] = Dot.COLOR_RED;    preset[3][0] = Dot.COLOR_RED;    preset[4][0] = Dot.COLOR_YELLOW;
        preset[0][1] = Dot.COLOR_GREEN;  preset[1][1] = Dot.COLOR_BLUE;  preset[2][1] = Dot.COLOR_BLUE;   preset[3][1] = Dot.COLOR_BLUE;   preset[4][1] = Dot.COLOR_GREEN;
        preset[0][2] = Dot.COLOR_PURPLE; preset[1][2] = Dot.COLOR_BLUE;  preset[2][2] = Dot.COLOR_BLUE;   preset[3][2] = Dot.COLOR_PURPLE; preset[4][2] = Dot.COLOR_YELLOW;
        preset[0][3] = Dot.COLOR_PURPLE; preset[1][3] = Dot.COLOR_GREEN; preset[2][3] = Dot.COLOR_YELLOW; preset[3][3] = Dot.COLOR_PURPLE; preset[4][3] = Dot.COLOR_PURPLE;
        preset[0][4] = Dot.COLOR_RED;    preset[1][4] = Dot.COLOR_RED;   preset[2][4] = Dot.COLOR_YELLOW; preset[3][4] = Dot.COLOR_PURPLE; preset[4][4] = Dot.COLOR_PURPLE;

        Board testBoard = new Board(preset);
        Dot.resetStack();

        testBoard.getBoard()[2][3].changeSelected();
        assertTrue(testBoard.getBoard()[2][3].getIsSelected());
        testBoard.getBoard()[2][4].changeSelected();
        assertTrue(testBoard.getBoard()[2][4].getIsSelected());
        assertTrue(Dot.getTotalStack() == 2);

    }

    public void testCanDeselect() {
        preset[0][0] = Dot.COLOR_GREEN;  preset[1][0] = Dot.COLOR_GREEN; preset[2][0] = Dot.COLOR_RED;    preset[3][0] = Dot.COLOR_RED;    preset[4][0] = Dot.COLOR_YELLOW;
        preset[0][1] = Dot.COLOR_GREEN;  preset[1][1] = Dot.COLOR_BLUE;  preset[2][1] = Dot.COLOR_BLUE;   preset[3][1] = Dot.COLOR_BLUE;   preset[4][1] = Dot.COLOR_GREEN;
        preset[0][2] = Dot.COLOR_PURPLE; preset[1][2] = Dot.COLOR_BLUE;  preset[2][2] = Dot.COLOR_BLUE;   preset[3][2] = Dot.COLOR_PURPLE; preset[4][2] = Dot.COLOR_YELLOW;
        preset[0][3] = Dot.COLOR_PURPLE; preset[1][3] = Dot.COLOR_GREEN; preset[2][3] = Dot.COLOR_YELLOW; preset[3][3] = Dot.COLOR_PURPLE; preset[4][3] = Dot.COLOR_PURPLE;
        preset[0][4] = Dot.COLOR_RED;    preset[1][4] = Dot.COLOR_RED;   preset[2][4] = Dot.COLOR_YELLOW; preset[3][4] = Dot.COLOR_PURPLE; preset[4][4] = Dot.COLOR_PURPLE;

        Board testBoard = new Board(preset);
        Dot.resetStack();

        assertFalse(testBoard.canDeselect(0, 4));
        testBoard.getBoard()[0][4].changeSelected();
        assertTrue(testBoard.canDeselect(0, 4));
        testBoard.getBoard()[1][4].changeSelected();
        assertFalse(testBoard.canDeselect(0, 4));
        assertTrue(testBoard.canDeselect(1, 4));
    }

    public void testNumberSelected() {
        preset[0][0] = Dot.COLOR_GREEN;  preset[1][0] = Dot.COLOR_GREEN; preset[2][0] = Dot.COLOR_RED;    preset[3][0] = Dot.COLOR_RED;    preset[4][0] = Dot.COLOR_YELLOW;
        preset[0][1] = Dot.COLOR_GREEN;  preset[1][1] = Dot.COLOR_BLUE;  preset[2][1] = Dot.COLOR_BLUE;   preset[3][1] = Dot.COLOR_BLUE;   preset[4][1] = Dot.COLOR_GREEN;
        preset[0][2] = Dot.COLOR_PURPLE; preset[1][2] = Dot.COLOR_BLUE;  preset[2][2] = Dot.COLOR_BLUE;   preset[3][2] = Dot.COLOR_PURPLE; preset[4][2] = Dot.COLOR_YELLOW;
        preset[0][3] = Dot.COLOR_PURPLE; preset[1][3] = Dot.COLOR_GREEN; preset[2][3] = Dot.COLOR_YELLOW; preset[3][3] = Dot.COLOR_PURPLE; preset[4][3] = Dot.COLOR_PURPLE;
        preset[0][4] = Dot.COLOR_RED;    preset[1][4] = Dot.COLOR_RED;   preset[2][4] = Dot.COLOR_YELLOW; preset[3][4] = Dot.COLOR_PURPLE; preset[4][4] = Dot.COLOR_PURPLE;

        Board testBoard = new Board(preset);
        Dot.resetStack();

        testBoard.getBoard()[0][4].changeSelected();
        testBoard.getBoard()[1][4].changeSelected();

        assertTrue(testBoard.numberSelected() == 2);
        testBoard.getBoard()[1][4].changeSelected();
        assertTrue(testBoard.numberSelected() == 1);
    }

    public void testRemoveSelectedDots() throws Board.CantRemoveException{
        preset[0][0] = Dot.COLOR_GREEN;  preset[1][0] = Dot.COLOR_GREEN; preset[2][0] = Dot.COLOR_RED;    preset[3][0] = Dot.COLOR_RED;    preset[4][0] = Dot.COLOR_YELLOW;
        preset[0][1] = Dot.COLOR_GREEN;  preset[1][1] = Dot.COLOR_BLUE;  preset[2][1] = Dot.COLOR_BLUE;   preset[3][1] = Dot.COLOR_BLUE;   preset[4][1] = Dot.COLOR_GREEN;
        preset[0][2] = Dot.COLOR_PURPLE; preset[1][2] = Dot.COLOR_BLUE;  preset[2][2] = Dot.COLOR_BLUE;   preset[3][2] = Dot.COLOR_PURPLE; preset[4][2] = Dot.COLOR_YELLOW;
        preset[0][3] = Dot.COLOR_PURPLE; preset[1][3] = Dot.COLOR_GREEN; preset[2][3] = Dot.COLOR_YELLOW; preset[3][3] = Dot.COLOR_PURPLE; preset[4][3] = Dot.COLOR_PURPLE;
        preset[0][4] = Dot.COLOR_RED;    preset[1][4] = Dot.COLOR_RED;   preset[2][4] = Dot.COLOR_YELLOW; preset[3][4] = Dot.COLOR_PURPLE; preset[4][4] = Dot.COLOR_PURPLE;

        Board testBoard = new Board(preset);
        Dot.resetStack();

        testBoard.getBoard()[0][4].changeSelected();
        testBoard.getBoard()[1][4].changeSelected();
        assertTrue(testBoard.numberSelected() == 2);
        testBoard.removeSelectedDots();
        testBoard.dropRemainingDots();
        testBoard.fillRemovedDots();
        assertTrue(Dot.getTotalStack() == 0);
        assertTrue(testBoard.numberSelected() == 0);
    }

    public void testIsClosedShape() {
        preset[0][0] = Dot.COLOR_GREEN;  preset[1][0] = Dot.COLOR_GREEN; preset[2][0] = Dot.COLOR_RED;    preset[3][0] = Dot.COLOR_RED;    preset[4][0] = Dot.COLOR_YELLOW;
        preset[0][1] = Dot.COLOR_GREEN;  preset[1][1] = Dot.COLOR_BLUE;  preset[2][1] = Dot.COLOR_BLUE;   preset[3][1] = Dot.COLOR_BLUE;   preset[4][1] = Dot.COLOR_GREEN;
        preset[0][2] = Dot.COLOR_PURPLE; preset[1][2] = Dot.COLOR_BLUE;  preset[2][2] = Dot.COLOR_BLUE;   preset[3][2] = Dot.COLOR_PURPLE; preset[4][2] = Dot.COLOR_YELLOW;
        preset[0][3] = Dot.COLOR_PURPLE; preset[1][3] = Dot.COLOR_GREEN; preset[2][3] = Dot.COLOR_YELLOW; preset[3][3] = Dot.COLOR_PURPLE; preset[4][3] = Dot.COLOR_PURPLE;
        preset[0][4] = Dot.COLOR_RED;    preset[1][4] = Dot.COLOR_RED;   preset[2][4] = Dot.COLOR_YELLOW; preset[3][4] = Dot.COLOR_PURPLE; preset[4][4] = Dot.COLOR_PURPLE;

        Board testBoard = new Board(preset);
        Dot.resetStack();

        testBoard.getBoard()[3][1].changeSelected();
        testBoard.getBoard()[2][1].changeSelected();
        testBoard.getBoard()[1][1].changeSelected();
        testBoard.getBoard()[1][2].changeSelected();
        testBoard.getBoard()[2][2].changeSelected();

        assertTrue(testBoard.isClosedShape());

        testBoard.getBoard()[3][1].changeSelected();
        testBoard.getBoard()[2][1].changeSelected();
        testBoard.getBoard()[1][1].changeSelected();
        testBoard.getBoard()[1][2].changeSelected();
        testBoard.getBoard()[2][2].changeSelected();

        preset[0][0] = Dot.COLOR_GREEN;  preset[1][0] = Dot.COLOR_GREEN; preset[2][0] = Dot.COLOR_RED;    preset[3][0] = Dot.COLOR_RED;    preset[4][0] = Dot.COLOR_YELLOW;
        preset[0][1] = Dot.COLOR_GREEN;  preset[1][1] = Dot.COLOR_BLUE;  preset[2][1] = Dot.COLOR_BLUE;   preset[3][1] = Dot.COLOR_BLUE;   preset[4][1] = Dot.COLOR_GREEN;
        preset[0][2] = Dot.COLOR_PURPLE; preset[1][2] = Dot.COLOR_BLUE;  preset[2][2] = Dot.COLOR_PURPLE;   preset[3][2] = Dot.COLOR_PURPLE; preset[4][2] = Dot.COLOR_PURPLE;
        preset[0][3] = Dot.COLOR_PURPLE; preset[1][3] = Dot.COLOR_GREEN; preset[2][3] = Dot.COLOR_PURPLE; preset[3][3] = Dot.COLOR_PURPLE; preset[4][3] = Dot.COLOR_PURPLE;
        preset[0][4] = Dot.COLOR_RED;    preset[1][4] = Dot.COLOR_RED;   preset[2][4] = Dot.COLOR_PURPLE; preset[3][4] = Dot.COLOR_PURPLE; preset[4][4] = Dot.COLOR_PURPLE;

        Board testBoard2 = new Board(preset); //checking for a donut shape

        testBoard2.getBoard()[2][2].changeSelected();
        testBoard2.getBoard()[2][3].changeSelected();
        testBoard2.getBoard()[2][4].changeSelected();
        testBoard2.getBoard()[3][4].changeSelected();
        testBoard2.getBoard()[4][4].changeSelected();
        testBoard2.getBoard()[4][3].changeSelected();
        testBoard2.getBoard()[4][2].changeSelected();
        testBoard2.getBoard()[3][2].changeSelected();

        assertTrue(testBoard2.isClosedShape());


    }

    public void testDropRemainingDots() throws Board.CantRemoveException{
        preset[0][0] = Dot.COLOR_GREEN;  preset[1][0] = Dot.COLOR_GREEN; preset[2][0] = Dot.COLOR_RED;    preset[3][0] = Dot.COLOR_RED;    preset[4][0] = Dot.COLOR_YELLOW;
        preset[0][1] = Dot.COLOR_GREEN;  preset[1][1] = Dot.COLOR_BLUE;  preset[2][1] = Dot.COLOR_BLUE;   preset[3][1] = Dot.COLOR_BLUE;   preset[4][1] = Dot.COLOR_GREEN;
        preset[0][2] = Dot.COLOR_PURPLE; preset[1][2] = Dot.COLOR_BLUE;  preset[2][2] = Dot.COLOR_BLUE;   preset[3][2] = Dot.COLOR_PURPLE; preset[4][2] = Dot.COLOR_YELLOW;
        preset[0][3] = Dot.COLOR_PURPLE; preset[1][3] = Dot.COLOR_GREEN; preset[2][3] = Dot.COLOR_YELLOW; preset[3][3] = Dot.COLOR_PURPLE; preset[4][3] = Dot.COLOR_PURPLE;
        preset[0][4] = Dot.COLOR_RED;    preset[1][4] = Dot.COLOR_RED;   preset[2][4] = Dot.COLOR_YELLOW; preset[3][4] = Dot.COLOR_PURPLE; preset[4][4] = Dot.COLOR_PURPLE;

        Board testBoard = new Board(preset);
        Dot.resetStack();

        testBoard.getBoard()[0][0].changeSelected();
        testBoard.getBoard()[0][1].changeSelected();

        testBoard.removeSelectedDots();
        testBoard.dropRemainingDots();
        testBoard.fillRemovedDots();

        assertTrue(testBoard.getBoard()[0][0].getColor() == Dot.COLOR_PURPLE);
        assertTrue(testBoard.getBoard()[0][1].getColor() == Dot.COLOR_PURPLE);
        assertTrue(testBoard.getBoard()[0][2].getColor() == Dot.COLOR_RED);
    }

    public void testFillRemovedDots() throws Board.CantRemoveException {
        preset[0][0] = Dot.COLOR_GREEN;  preset[1][0] = Dot.COLOR_GREEN; preset[2][0] = Dot.COLOR_RED;    preset[3][0] = Dot.COLOR_RED;    preset[4][0] = Dot.COLOR_YELLOW;
        preset[0][1] = Dot.COLOR_GREEN;  preset[1][1] = Dot.COLOR_BLUE;  preset[2][1] = Dot.COLOR_BLUE;   preset[3][1] = Dot.COLOR_BLUE;   preset[4][1] = Dot.COLOR_GREEN;
        preset[0][2] = Dot.COLOR_PURPLE; preset[1][2] = Dot.COLOR_BLUE;  preset[2][2] = Dot.COLOR_BLUE;   preset[3][2] = Dot.COLOR_PURPLE; preset[4][2] = Dot.COLOR_YELLOW;
        preset[0][3] = Dot.COLOR_PURPLE; preset[1][3] = Dot.COLOR_GREEN; preset[2][3] = Dot.COLOR_YELLOW; preset[3][3] = Dot.COLOR_PURPLE; preset[4][3] = Dot.COLOR_PURPLE;
        preset[0][4] = Dot.COLOR_RED;    preset[1][4] = Dot.COLOR_RED;   preset[2][4] = Dot.COLOR_YELLOW; preset[3][4] = Dot.COLOR_PURPLE; preset[4][4] = Dot.COLOR_PURPLE;

        Board testBoard = new Board(preset);
        Dot.resetStack();

        testBoard.getBoard()[0][1].changeSelected();
        testBoard.getBoard()[0][0].changeSelected();
        testBoard.getBoard()[1][0].changeSelected();

        testBoard.removeSelectedDots();
        testBoard.dropRemainingDots();
        testBoard.fillRemovedDots();

        assertFalse(testBoard.getBoard()[0][3] == null);
        assertFalse(testBoard.getBoard()[0][4] == null);
        assertFalse(testBoard.getBoard()[1][4] == null);


    }

    public void testGetScore() throws Board.CantRemoveException {
        preset[0][0] = Dot.COLOR_GREEN;  preset[1][0] = Dot.COLOR_GREEN; preset[2][0] = Dot.COLOR_RED;    preset[3][0] = Dot.COLOR_RED;    preset[4][0] = Dot.COLOR_YELLOW;
        preset[0][1] = Dot.COLOR_GREEN;  preset[1][1] = Dot.COLOR_BLUE;  preset[2][1] = Dot.COLOR_BLUE;   preset[3][1] = Dot.COLOR_BLUE;   preset[4][1] = Dot.COLOR_GREEN;
        preset[0][2] = Dot.COLOR_PURPLE; preset[1][2] = Dot.COLOR_BLUE;  preset[2][2] = Dot.COLOR_BLUE;   preset[3][2] = Dot.COLOR_PURPLE; preset[4][2] = Dot.COLOR_YELLOW;
        preset[0][3] = Dot.COLOR_PURPLE; preset[1][3] = Dot.COLOR_GREEN; preset[2][3] = Dot.COLOR_YELLOW; preset[3][3] = Dot.COLOR_PURPLE; preset[4][3] = Dot.COLOR_PURPLE;
        preset[0][4] = Dot.COLOR_RED;    preset[1][4] = Dot.COLOR_RED;   preset[2][4] = Dot.COLOR_YELLOW; preset[3][4] = Dot.COLOR_PURPLE; preset[4][4] = Dot.COLOR_BLUE;

        Board testBoard = new Board(preset);
        Dot.resetStack();

        testBoard.getBoard()[3][1].changeSelected();
        testBoard.getBoard()[2][1].changeSelected();
        testBoard.getBoard()[1][1].changeSelected();
        testBoard.getBoard()[1][2].changeSelected();
        testBoard.getBoard()[2][2].changeSelected();

        testBoard.removeSelectedDots();
        testBoard.dropRemainingDots();
        testBoard.fillRemovedDots();

        assertTrue(testBoard.getScore() == 6);

        testBoard.getBoard()[0][1].changeSelected();
        testBoard.getBoard()[0][0].changeSelected();
        testBoard.getBoard()[1][0].changeSelected();

        testBoard.removeSelectedDots();
        testBoard.dropRemainingDots();
        testBoard.fillRemovedDots();

        assertTrue(testBoard.getScore() == 9);
    }

    public void testFindBestSquare() {
        preset[0][0] = Dot.COLOR_BLUE;  preset[1][0] = Dot.COLOR_GREEN; preset[2][0] = Dot.COLOR_RED;    preset[3][0] = Dot.COLOR_RED;    preset[4][0] = Dot.COLOR_YELLOW;
        preset[0][1] = Dot.COLOR_GREEN;  preset[1][1] = Dot.COLOR_BLUE;  preset[2][1] = Dot.COLOR_BLUE;   preset[3][1] = Dot.COLOR_BLUE;   preset[4][1] = Dot.COLOR_GREEN;
        preset[0][2] = Dot.COLOR_PURPLE; preset[1][2] = Dot.COLOR_BLUE;  preset[2][2] = Dot.COLOR_BLUE;   preset[3][2] = Dot.COLOR_PURPLE; preset[4][2] = Dot.COLOR_YELLOW;
        preset[0][3] = Dot.COLOR_PURPLE; preset[1][3] = Dot.COLOR_GREEN; preset[2][3] = Dot.COLOR_YELLOW; preset[3][3] = Dot.COLOR_PURPLE; preset[4][3] = Dot.COLOR_PURPLE;
        preset[0][4] = Dot.COLOR_RED;    preset[1][4] = Dot.COLOR_RED;   preset[2][4] = Dot.COLOR_YELLOW; preset[3][4] = Dot.COLOR_PURPLE; preset[4][4] = Dot.COLOR_PURPLE;

        Board testBoard = new Board(preset);
        Dot.resetStack();

        assertTrue(testBoard.findBestSquare().toString().equals("[java.awt.Point[x=3,y=3], java.awt.Point[x=4,y=3], java.awt.Point[x=4,y=4], java.awt.Point[x=3,y=4]]"));


    }
}