import java.awt.*;
import java.util.Random;
import java.util.*;

public class Dot {

    // DO NOT MODIFY
    public static final int COLOR_BLUE = 1;
    public static final int COLOR_RED = 2;
    public static final int COLOR_GREEN = 3;
    public static final int COLOR_YELLOW = 4;
    public static final int COLOR_PURPLE = 5;
    public static final int NUM_COLORS = 5;
    
    private int myColor;
    private Point myCoords; //coordinates of a dot
    private boolean isSelected; //is a dot selected?
    private static int totalStack; //the total number of selected dots
    private int myStack; //the order of the selected dot in reference to totalStack
    private boolean remove = false;
    private boolean toBeReplaced = false; //only for those dots on the top row that need to be replaced; will get replaced


    /**
     * Generates a dot with a random color attribute.
     * Note: There is a variable defined as NUM_COLORS which should be used 
     * for generating random colors (ints). You random number generator 
     * should return an integer from 1 to NUM_COLORS inclusive, (not 1 - 5).
     */
    public Dot() {

        // YOUR CODE HERE
        Random randNum = new Random();
        myColor = randNum.nextInt(NUM_COLORS) + 1;
        isSelected = false;
        myStack = 0;
    }
    
    /**Generates a dot with an input color. */
    public Dot(int color) {
        // YOUR CODE HERE
        myColor = color;
        isSelected = false;
    }
    
    /**Returns the integer representation of a dot's color (myColor). */
    public int getColor() {
    	// YOUR CODE HERE
    	return myColor;
    }
    
    /**Returns whether or not this dot is the same color as otherDot. */
    public boolean isSameColor(Dot otherDot){
    	// YOUR CODE HERE
        if (otherDot.getColor() == this.getColor()) {
            return true;
        }
    	return false;
    }
    
    /**
     * Returns whether or not this dot is the same color 
     * as the argument, which is also an integer representation.
     */
    public boolean isColor(int color) {
    	// YOUR CODE HERE
        if (this.getColor() == color) {
            return true;
        }
    	return false;
    }

    public Point getMyCoords() {
        return myCoords;
    }

    public void setMyCoords(int x, int y) {
        myCoords = new Point(x, y);
        //return myCoords;
    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void changeSelected() { //changing the selected status of the dot
        if (isSelected) { //if the dot is selected
            totalStack--; //decrement from the total stack
            myStack = 0;  //reset the dot's stack
            //myStack = selectedStack;
            isSelected = false;
        } else {
            totalStack++;
            myStack = totalStack;
            isSelected = true;
        }
    }


    public static int getTotalStack() { //the total stack of dots
        return totalStack;
    }

    public int getMyStack() { //the dot's order on the stack of selected dots
        return myStack;
    }

    public String toString() {
        return "(" + myCoords.x + " , " + myCoords.y + ")";
    }

    public static void resetStack() {
        totalStack = 0;
    }

    public void resetMyStack(Dot dot) {
        dot.myStack = 0;
    }



    public void toRemove() {
        remove = true;
    }

    public boolean getRemoveStatus() {
        return remove;
    }

    public void changeToFill() {
        if (toBeReplaced) {
            toBeReplaced = false;
        } else {
            toBeReplaced = true;
        }
    }

    public boolean toReplace() {
        return toBeReplaced;
    }

    //public void addDotsToRemove(Point dotToRemove) {
    //    dotsToRemove.add(dotToRemove);
    //}



}
