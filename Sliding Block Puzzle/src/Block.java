/**This class will be used to create blocks
 * and store their information, namely
 * their coordinates on the Tray
 */

public class Block {
    protected int topLY;
    protected int topLX;
    protected int topRY;
    protected int topRX;
    protected int bottomLY;
    protected int bottomLX;
    protected int bottomRY;
    protected int bottomRX;

    public Block(int topLY, int topLX, int bottomRY, int bottomRX) {
        this.topLY = topLY;
        this.topLX = topLX;
        this.topRY = topLY;
        this.topRX = bottomRX;
        this.bottomLY = bottomRY;
        this.bottomLX = topLX;
        this.bottomRY = bottomRY;
        this.bottomRX = bottomRX;
    }

    public int width() {
        return topRX - topLX + 1;
    }

    public int height() {
        return bottomLY - topLY + 1;
    }

    public void move(int newY, int newX) {
        //only one of newY or newX can differ from original
        if (newY != this.topLY && newX != this.topLX) {
            System.out.println("input from stdin contains an impossible move");
            System.exit(6);
        }
        int yShiftFactor = newY - this.topLY;
        int xShiftFactor = newX - this.topLX;

        this.topLY += yShiftFactor;
        this.topLX += xShiftFactor;
        this.topRX += xShiftFactor;
        this.topRY += yShiftFactor;
        this.bottomLX += xShiftFactor;
        this.bottomLY += yShiftFactor;
        this.bottomRX += xShiftFactor;
        this.bottomRY += yShiftFactor;
    }

    public String toString() {
        return topLY + " " + topLX + " " + bottomRY + " " + bottomRX;
    }

    public void moveUp() {
        this.topLY--;
        this.topRY--;
        this.bottomLY--;
        this.bottomRY--;
    }

    public void moveDown() {
        this.topLY++;
        this.topRY++;
        this.bottomLY++;
        this.bottomRY++;
    }

    public void moveLeft() {
        this.topLX--;
        this.topRX--;
        this.bottomLX--;
        this.bottomRX--;
    }

    public void moveRight() {
        this.topLX++;
        this.topRX++;
        this.bottomLX++;
        this.bottomRX++;
    }

    protected boolean canMoveLeft(Tray tray) {
        int x = this.topLX;
        if (x - 1 < 0) {
            return false;
        }
        for (int y = this.topLY; y < this.height()+this.topLY; y++) {
            Block other = tray.board[y][x-1];
            if (other != null) {
                return false;
            }
        }
        return true;
    }

    protected boolean canMoveRight(Tray tray) {
        int x = this.topRX;
        if (x + 1 == tray.width) {
            return false;
        }
        for (int y = this.topRY; y < this.height() + this.topRY; y++) {
            Block other = tray.board[y][x+1];
            if (other != null) {
                return false;
            }
        }
        return true;
    }

    protected boolean canMoveUp(Tray tray) {
        int y = this.topLY;
        if (y - 1 < 0) {
            return false;
        }
        for (int x = this.topLX; x < this.width()+this.topLX; x++) {
            Block other = tray.board[y-1][x];
            if (other != null) {
                return false;
            }
        }
        return true;
    }

    protected boolean canMoveDown(Tray tray) {
        int y = this.bottomLY;
        if (y + 1 == tray.height) {
            return false;
        }
        for (int x = this.bottomLX; x < this.width()+this.bottomLX; x++) {
            Block other = tray.board[y+1][x];
            if (other != null) {
                return false;
            }
        }
        return true;
    }
}