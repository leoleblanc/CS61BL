import java.util.ArrayList;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Stack;
/**
 * this class is the Tray from which the puzzle will be seen and solved
 */

public class Tray implements Iterable<Block> {
    protected int height;
    protected int width;
    protected Block[][] board;

    public Tray(String file) {
        try {
            Scanner s = new Scanner(new FileReader(file));
            String[] dimensions = s.nextLine().split(" ");
            height = Integer.parseInt(dimensions[0]);
            width = Integer.parseInt(dimensions[1]);
            board = new Block[height][width];

            while (s.hasNext()) {
                String[] str = s.nextLine().split(" ");
                if (str.length != 4) {
                    break;
                }
                int topLY = Integer.parseInt(str[0]);
                int topLX = Integer.parseInt(str[1]);
                int bottomRY = Integer.parseInt(str[2]);
                int bottomRX = Integer.parseInt(str[3]);
                Block b = new Block(topLY, topLX, bottomRY, bottomRX);

                if (b.topLY >= height || b.topLY < 0 || b.bottomRY >= height || b.bottomRY < 0
                        ||
                    b.topLX >= width || b.topLX < 0 || b.bottomRX >= width || b.bottomRX < 0) {
                    System.out.println("invalid board");
                    System.exit(5);
                }

                for (int blockX = 0; blockX < b.width(); blockX++) {
                    for (int blockY = 0; blockY < b.height(); blockY++) {
                        board[blockY + topLY][blockX + topLX] = b;
                    }
                }
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(3);
        }
    }

    /**
     * this iterator is to iterate through every block in a given Tray
     */

    public Iterator<Block> iterator() {
        return new TrayIterator(this);
    }

    public class TrayIterator implements Iterator<Block> {

        public Stack<Block> fringe;

        public TrayIterator(Tray tray) {
            fringe = new Stack<Block>();
            for (int y = tray.height-1; y > -1; y--) {
                for (int x = tray.width-1; x > -1; x--) {
                    Block b = tray.board[y][x];
                    if (b != null && !fringe.contains(b)) {
                        fringe.push(b);
                    }
                }
            }
        }

        public boolean hasNext() {
            return !fringe.isEmpty();
        }

        public Block next() {
            return fringe.pop();
        }

        public void remove() {
        }
    }

    public Tray(Tray input) {
        height = input.height;
        width = input.width;
        board = new Block[height][width];
        Iterator<Block> iter = input.iterator();
        while (iter.hasNext()) {
            Block next = iter.next();
            Block clone = new Block(next.topLY, next.topLX, next.bottomRY, next.bottomRX);
            for (int y = clone.topLY; y < clone.height() + clone.topLY; y++) {
                for (int x = clone.topLX; x < clone.width() + clone.topLX; x++) {
                    board[y][x] = clone;
                }
            }
        }
    }


    public ArrayList<String> getAllMoves(){
        ArrayList<String> result = new ArrayList<String>();
        Iterator<Block> i = this.iterator();
        while (i.hasNext()) {
            Block b = i.next();
            Block pseudo = new Block(b.topLY, b.topLX, b.bottomRY, b.bottomRX);
            while (pseudo.canMoveUp(this)) {
                String move = "" + b.topLY + " "  + b.topLX + " " + (pseudo.topLY - 1) + " " + pseudo.topLX;
                result.add(move);
                pseudo.moveUp();
            }
            pseudo = new Block(b.topLY, b.topLX, b.bottomRY, b.bottomRX);
            while (pseudo.canMoveDown(this)) {
                String move = "" + b.topLY + " "  + b.topLX + " " + (pseudo.topLY + 1) + " " + pseudo.topLX;
                result.add(move);
                pseudo.moveDown();
            }
            pseudo = new Block(b.topLY, b.topLX, b.bottomRY, b.bottomRX);
            while (pseudo.canMoveLeft(this)) {
                String move = "" + b.topLY + " "  + b.topLX + " " + pseudo.topLY + " " + (pseudo.topLX - 1);
                result.add(move);
                pseudo.moveLeft();
            }
            pseudo = new Block(b.topLY, b.topLX, b.bottomRY, b.bottomRX);
            while (pseudo.canMoveRight(this)) {
                String move = "" + b.topLY + " "  + b.topLX + " " + pseudo.topLY + " " + (pseudo.topLX + 1);
                result.add(move);
                pseudo.moveRight();
            }
        }
        return result;
    }

    public Tray clone(){
        return new Tray(this);
    }

    protected void executeMove(String move) {
        this.executeMove(move.split(" "));
    }

    protected void executeMove(String[] move){
        if (move == null || move.length != 4) {
            System.err.println("improperly formatted move");
            System.exit(4);
        }
        int oldY = Integer.parseInt(move[0]);
        int oldX = Integer.parseInt(move[1]);
        int newY = Integer.parseInt(move[2]);
        int newX = Integer.parseInt(move[3]);

        if ((newY >= height || newX >= width || newY < 0 || newX < 0)
                ||
                (oldY >= height || oldX >= width || oldY < 0 || oldX < 0)) {
            System.out.println("impossible move");
            System.exit(6);
        }

        Block b = board[oldY][oldX];

        if (b == null) {
            System.out.println("impossible move");
            System.exit(6);
        }

        for (int currX = b.topLX; currX < b.topLX + b.width(); currX++) {
            for (int currY = b.topLY; currY < b.topLY + b.height(); currY++) {
                board[currY][currX] = null;
            }
        }

        if (b.topLY != oldY || b.topLX != oldX) {
            System.out.println("not the top left square");
            System.exit(6);
        }

        if (newX > oldX || newY > oldY) { //moving right or down
            for (int currX = b.bottomLX; currX <= newX; currX++) {
                for (int currY = b.bottomLY; currY <= newY; currY++) {
                    if (board[currY][currX] != null) {
                        System.out.println("block in movement path");
                        System.exit(6);
                    }
                }
            }
        } else {
            for (int currX = b.topLX; currX >= newX; currX--) {
                for (int currY = b.topLY; currY >= newY; currY--) {
                    if (board[currY][currX] != null) {
                        System.out.println("block in movement path");
                        System.exit(6);
                    }
                }
            }
        }

        b.move(newY, newX);
        if (b.bottomRY >= height || b.bottomRX >= width) {
            System.out.println("impossible move");
            System.exit(6);
        }

        for (int currX = newX; currX < newX + b.width(); currX++) {
            for (int currY = newY; currY < newY + b.height(); currY++) {
                if (board[currY][currX] != null) { //this means the spot is occupied
                    System.out.println("block already there");
                    System.exit(6);
                }
                board[currY][currX] = b;
            }
        }
    }

    public String toString() {
        StringBuilder String = new StringBuilder();
        boolean first = true;
        Iterator<Block> iter = this.iterator();
        while (iter.hasNext()) {
            Block b = iter.next();
            if (first) {
                first = false;
            } else {
                String.append("\n");
            }
            String.append(b.topLY).append(" ").append(b.topLX).append(" ").append(b.bottomRY).append(" ").append(b.bottomRX);
        }
        return String.toString();
    }
}