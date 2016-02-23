import java.awt.Point;
import java.util.ArrayList;

public class Board {

    // Our representation of the board, where myBoard[0][0] represents
    // the bottom left dot.
    private Dot[][] myBoard;
    // Total number of moves allowed for a single game session.
    private static int movesAllowed = 5;

    // DO NOT MODIFY
    public static final int MINSIZE = 4;
    public static final int MAXSIZE = 10;

    private int height;
    private int width;
    private int numDots;
    private int myScore = 0;

    /**
     * Sets up the board's data and starts up the GUI. N is side length of the
     * board. (Number of dots per side) N will default to 0 if a non-integer is
     * entered as an argument. If there are no arguments, N will default to 10;
     */
    public static void main(String[] args) {
        int n = 0;
        try {
            n = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            n = 0;
        } catch (IndexOutOfBoundsException e) {
            // This line is run if no command line arguments are given.
            // If you wish to modify this line to test, remember to change it back to 
            // n = 10;
            n = 10;
        }
        GUI.initGUI(n);
    }

    /**
     * When the New Game button is clicked, a new randomized board is constructed.
     * Sets up the board with input SIZE, such that the board's side length is SIZE.
     * Note: The Board is always a square, so SIZE is both the length and the width.
     * Generate a random board such that each entry in board is a random color.
     * (represented by an int). Should print and error and System.exit if the size
     * is not within the MINSIZE and MAXSIZE. This constructor will only be called
     * once per game session. Initialize any variables if needed here.
     */
    public Board(int size) {
        // YOUR CODE HERE
        if (size < MINSIZE || size > MAXSIZE) {
            throw new IllegalArgumentException("Size is an invalid value; must be between 4 and 10, inclusive");
        }
        myBoard = new Dot[size][size];
        for (int width = 0; width < size; width++) {
            for (int height = 0; height < size; height++) {
                myBoard[width][height] = new Dot();
                myBoard[width][height].setMyCoords(width, height);
                numDots++;
            }
        }
        movesAllowed = 5;
        height = size;
        width = size;
    }

    /**
     * This constructor takes in a 2D int array of colors and generates a preset board
     * with each dot matching the color of the corresponding entry in the int[][]
     * argument. This constructor can be used for predetermined tests.
     * You may assume that the input is valid (between MINSIZE and MAXSIZE etc.)
     * since this is for your own testing.
     */

    public Board(int[][] preset) {
        // YOUR CODE HERE
        height = preset.length;
        width = height;
        myBoard = new Dot[height][width];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                myBoard[x][y] = new Dot(preset[x][y]);
                myBoard[x][y].setMyCoords(x, y);
                numDots++;
            }
        }
    }

    /**
     * Returns the array representation of the board. (Data is used by GUI).
     */
    public Dot[][] getBoard() {
        return myBoard;
    }

    /**
     * Returns the number of moves allowed per game. This value should not
     * change during a game session.
     */
    public static int getMovesAllowed() {
        // YOUR CODE HERE
        return movesAllowed;
    }

    /**
     * Change the number of moves allowed per game. This method can be used for
     * testing.
     */
    public static void setMovesAllowed(int n) {
        // YOUR CODE HERE
        movesAllowed = n;
    }

    /**
     * Returns the number of moves left.
     */
    public int getMovesLeft() {
        // YOUR CODE HERE
        return movesAllowed;
    }

    /**
     * Return whether or not it is possible to make a Move. (ie, there exists
     * two adjacent dots of the same color.) If false, the GUI will report a
     * game over.
     */
    public boolean canMakeMove() {
        // YOUR CODE HERE
        return checkAllDots();
    }

    /**
     * Returns if the board is in a state of game over. The game is over if there
     * are no possible moves left or if the player has used up the maximum
     * allowed moves.
     */
    public boolean isGameOver() {
        // YOUR CODE HERE
        if (this.getMovesLeft() == 0 || !this.canMakeMove()) {
            return true;
        }
        return false;
    }

    /**
     * Returns whether or not you are allowed to select a dot at X, Y at the
     * moment. Remember, if the game is over, you cannot select any dots.
     */
    public boolean canSelect(int x, int y) {
        // YOUR CODE HERE
        if (isGameOver()) {
            return false;
        }
        else if (myBoard[x][y].getIsSelected()) {
            return false;
        }
        else if (!anySelected()) {
            Dot.resetStack();
            return true;
        }
        else {
            return checkAdjacentDots(x, y);
        }
    }
    /**
     * This function confirms if any dot is currently selected
     * Returns false if no dots are selected on board
     */
    public boolean anySelected() {
        boolean any = false;
        for (int x_coord = 0; x_coord < width; x_coord++) {
            for (int y_coord = 0; y_coord < height; y_coord++) {
                if (myBoard[x_coord][y_coord].getIsSelected()) {
                    any = true;
                }
            }
        }
        return any;
    }

    /**
     * Is called when a dot located at myBoard[X][Y] is selected on the GUI.
     */
    public void selectDot(int x, int y) {
        // YOUR CODE HERE
        myBoard[x][y].changeSelected();
    }

    /**
     * Checks if you are allowed to deselect the chosen point.
     * Assumes at least one point has been selected already.
     * You can only deselect the most recent point you have selected.
     * (You can select 3 dots and deselect them in reverse order.)
     * Returns false if dot isn't selected
     */
    public boolean canDeselect(int x, int y) {
        if (!myBoard[x][y].getIsSelected()) {
            return false;
        }
        if (myBoard[x][y].getMyStack() == Dot.getTotalStack()) {
            return true;
        }
        return false;
    }

    /**
     * Is called when a dot located at myBoard[X][Y] is deselected on the GUI.
     */
    public void deselectDot(int x, int y) {
        // YOUR CODE HERE
        myBoard[x][y].changeSelected();
    }

    /**
     * Returns the number of currently selected dots.
     */
    public int numberSelected() {
        // YOUR CODE HERE
        return Dot.getTotalStack();
    }

    /**
     * Is called when the "Remove" button is clicked. Puts all selected dots in
     * a "removed" state. If no dots should be removed, throw a CantRemoveException.
     * You must also create your own Exception Class named CantRemoveException.
     * If selected dots form a closed shape, remove all dots on the board that have
     * the same color as the selected dots.
     */

    public void removeSelectedDots() throws CantRemoveException {
        // YOUR CODE HERE
        if (Dot.getTotalStack() < 2) {
            throw new CantRemoveException();

        }
        else if (isClosedShape()) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (myBoard[x][y].getIsSelected()) {
                        for (int x_coord = 0; x_coord < width; x_coord++) {
                            for (int y_coord = 0; y_coord < height; y_coord++) {
                                if (myBoard[x][y].isSameColor(myBoard[x_coord][y_coord])) {
                                    if (!myBoard[x_coord][y_coord].getIsSelected()) {
                                        myBoard[x_coord][y_coord].changeSelected();
                                    }
                                    myBoard[x_coord][y_coord].toRemove();
                                }
                            }
                        }
                        myBoard[x][y].toRemove();
                    }
                }
            }
        } else {
            for (int x_coord = 0; x_coord < width; x_coord++) {
                for (int y_coord = 0; y_coord < height; y_coord++) {
                    if (myBoard[x_coord][y_coord].getIsSelected()) {
                        myBoard[x_coord][y_coord].toRemove();
                    }
                }
            }
        }
    }


    public static class CantRemoveException extends Exception {

    }

    /**
     * Puts the dot at X, Y in a removed state. Later all dots above a
     * removed dot will drop.
     * Removed state is indicated by variable toBeReplaced
     */
    public void removeSingleDot(int x, int y) {
        // OPTIONAL
        myScore++;
        myBoard[x][y].changeSelected();
        try {
            for (int i = y; i < height; i++) {
                myBoard[x][i] = myBoard[x][i+1];
                myBoard[x][i+1].setMyCoords(x, i);
            }
        } catch (Exception e) {
            myBoard[x][height-1] = new Dot();
            myBoard[x][height-1].setMyCoords(x, height-1);
            myBoard[x][height-1].changeToFill();
        }
    }


    /**
     * Return whether or not the selected dots form a closed shape. Refer to
     * diagram for what a closed shape looks like.
     */
    public boolean isClosedShape() {
        // YOUR CODE HERE

        if (Dot.getTotalStack() < 3) {
            return false;
        }
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (myBoard[x][y].getMyStack() == Dot.getTotalStack()) {
                    return checkClosedShape(x, y);
                }
            }
        }
        return false;
    }

    /**
     * Removes all dots of the same color of the dots on the currently selected
     * dots. Assume it is confirmed that a closed shape has been formed from the
     * selected dots.
     */
    public void removeSameColor() {
        // OPTIONAL
    }

    /**
     * Once the dots are removed. Rearrange the board to simulate the dropping of
     * all of the dots above the removed dots. Refer to diagram in the spec for clarity.
     * After dropping the dots, there should exist some "bad" dots at the top.
     * (These are the blank dots on the 4-stage diagram.)
     * fillRemovedDots will be called immediately after this by the GUI so that random
     * dots replace these bad dots with new ones that have a randomly generated color.
     */
    public void dropRemainingDots() {
        // YOUR CODE HERE
        for (int x_coord = 0; x_coord < width; x_coord++) {
            for (int y_coord = 0; y_coord < height; y_coord++) {
                while (myBoard[x_coord][y_coord].getRemoveStatus()) {
                    removeSingleDot(x_coord, y_coord);
                }
            }
        }
    }

    /**
     * After removing all dots that were meant to be removed, replace any
     * removed dot with a new dot of a random color.
     */
    public void fillRemovedDots() {
        // YOUR CODE HERE
        for (int x_coord = 0; x_coord < width; x_coord++) {
            for (int y_coord = 0; y_coord < height; y_coord++) {
                if (myBoard[x_coord][y_coord].toReplace()) {
                    myBoard[x_coord][y_coord] = new Dot();
                    myBoard[x_coord][y_coord].setMyCoords(x_coord, y_coord);
                }
            }
        }
        movesAllowed--;
        //Dot.resetStack();
        //System.out.println(Dot.getTotalStack());
    }

    /**
     * Return the current score, which is called by the GUI when it needs to
     * update the display of the score. Remember to update the score in your
     * other methods.
     */
    public int getScore() {
        // YOUR CODE HERE
        return myScore;
    }

    /**
     * Search the board for a sequence of 4 consecutive points which form a
     * square such that out of all possible 2x2 squares, selecting this one
     * yields the most points.
     */
    public ArrayList<Point> findBestSquare() {
        // YOUR CODE HERE
        boolean [] colorHasSquare = new boolean [5];
        int [] colorCount = new int [5];
        int [] colors = new int [5];
        colors[0] = 1; colors[1] = 2; colors[2] = 3; colors[3] = 4; colors[4] = 5;
        ArrayList[] toRtn = new ArrayList[5];
        for (int index = 0; index < colorHasSquare.length; index++) {
            for (int x = 0; x < width-1; x++) {
                for (int y = 0; y < height-1; y++) {
                    Dot testDot = new Dot(colors[index]);
                    if (myBoard[x][y].isSameColor(testDot)
                            &&
                            (myBoard[x][y].isSameColor(myBoard[x+1][y])) &&
                            (myBoard[x][y].isSameColor(myBoard[x+1][y+1])) &&
                            (myBoard[x][y].isSameColor(myBoard[x][y+1])) &&
                            (toRtn[index] == null)) {
                        ArrayList<Point> coordinates = new ArrayList<Point>();
                        coordinates.add(myBoard[x][y].getMyCoords());
                        coordinates.add(myBoard[x+1][y].getMyCoords());
                        coordinates.add(myBoard[x+1][y+1].getMyCoords());
                        coordinates.add(myBoard[x][y+1].getMyCoords());
                        colorHasSquare[index] = true;
                        toRtn[index] = coordinates;
                    }
                }
            }
        }
        for (int index = 0; index < colorCount.length; index++) {
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    Dot testDot = new Dot(colors[index]);
                    if (myBoard[x][y].isSameColor(testDot)) {
                        colorCount[index]++;
                    }
                }
            }
        }
        int maxIndex = 0;
        for (int index = 0; index < colorCount.length; index++) {
            if (colorHasSquare[index] && (colorCount[index] > colorCount[maxIndex] || !colorHasSquare[maxIndex])) {
                maxIndex = index;
            }
        }

        return toRtn[maxIndex];
    }



    /**
     * Prints the the board any way you like for testing purposes.
     */
    public void printBoard() {
        // OPTIONAL
    }

    public void printBoard(String msg) {
        System.out.println(msg);
        printBoard();
    }

    public int getNumDots() {
        return numDots;
    }

    /**
     * Function checks all dots and allows only those next
     * to currently selected dots of the same color--to be selected.
     * Utilized in canSelect function
     */
    public boolean checkAdjacentDots(int x, int y) {
        int color = myBoard[x][y].getColor();
        try {
            if (myBoard[x-1][y].getIsSelected() && myBoard[x-1][y].isColor(color)
                    &&
                    myBoard[x-1][y].getMyStack() == Dot.getTotalStack()) {
                return true;
            }
        } catch (Exception e) {//if we can't, check the right
            if (myBoard[x+1][y].getIsSelected() && myBoard[x+1][y].isColor(color)
                    &&
                    myBoard[x+1][y].getMyStack() == Dot.getTotalStack()) {
                return true;
            }
        }
        try {
            if (myBoard[x+1][y].getIsSelected() && myBoard[x+1][y].isColor(color)
                    &&
                    myBoard[x+1][y].getMyStack() == Dot.getTotalStack()) {
                return true;
            }
        } catch (Exception e) {
            if (myBoard[x-1][y].getIsSelected() && myBoard[x-1][y].isColor(color)
                    &&
                    myBoard[x-1][y].getMyStack() == Dot.getTotalStack()) {
                return true;
            }
        }
        try {
            if (myBoard[x][y+1].getIsSelected() && myBoard[x][y+1].isColor(color)
                    &&
                    myBoard[x][y+1].getMyStack() == Dot.getTotalStack()) {
                return true;
            }
        } catch (Exception e) {
            if (myBoard[x][y-1].getIsSelected() && myBoard[x][y-1].isColor(color)
                    &&
                    myBoard[x][y-1].getMyStack() == Dot.getTotalStack()) {
                return true;
            }
        }
        try {
            if (myBoard[x][y-1].getIsSelected() && myBoard[x][y-1].isColor(color)
                    &&
                    myBoard[x][y-1].getMyStack() == Dot.getTotalStack()) {
                return true;
            }
        } catch (Exception e) {
            if (myBoard[x][y+1].getIsSelected() && myBoard[x][y+1].isColor(color)
                    &&
                    myBoard[x][y+1].getMyStack() == Dot.getTotalStack()) {
                return true;
            }
        }
        return false;
    }


    /**
     * Helper method utilized in canMakeMove.
     */
    public boolean checkAllDots() {
        for (int x_coord = 0; x_coord < width; x_coord++) {
            for (int y_coord = 0; y_coord < height; y_coord++) {
                int color = myBoard[x_coord][y_coord].getColor();
                try{
                    if (myBoard[x_coord+1][y_coord].isColor(color)) {
                        return true;
                    }
                } catch (Exception e) {
                }
                try{
                    if (myBoard[x_coord-1][y_coord].isColor(color)) {
                        return true;
                    }
                } catch (Exception e) {
                }
                try{
                    if (myBoard[x_coord][y_coord+1].isColor(color)) {
                        return true;
                    }
                } catch (Exception e) {
                }
                try {
                    if (myBoard[x_coord][y_coord-1].isColor(color)) {
                        return true;
                    }
                } catch (Exception e) {
                }
            }
        }
        return false;
    }

    /**
     * Method for checking adjacent dots of the most-recently-selected dot.
     * Returns true if two or more adjacent dots exist to this top-of-stack dot.
     */
    public boolean checkClosedShape(int x, int y) {
        int count = 0;
        try {
            if (myBoard[x-1][y].getIsSelected())  {
                count++;
            }
        } catch (Exception e) {
        }

        try {
            if (myBoard[x+1][y].getIsSelected()) {
                count++;
            }
        } catch (Exception e) {
        }

        try {
            if (myBoard[x][y-1].getIsSelected()) {
                count++;
            }
        } catch (Exception e) {
        }

        try {
            if (myBoard[x][y+1].getIsSelected()) {
                count++;
            }
        } catch (Exception e) {
        }
        return count >= 2;
    }
}
