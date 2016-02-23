import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.FileReader;

public class Checker {

    protected String goalFileName;
    protected String initFileName;
    protected Tray tray;
    protected LinkedList<Block> goalBlocks;

    public Checker(String initFileName, String goalFileName) {
        this.initFileName =  initFileName;
        this.goalFileName = goalFileName;
        this.checkInitAndGoal();
        this.tray = new Tray(initFileName);
        this.parseGoal();
    }

    protected void checkMoves() {
        Scanner input = new Scanner(System.in);
        // read moves until EOF
        while (input.hasNext()){
            String arg = input.nextLine();
            this.tray.executeMove(arg.split(" "));
        }
        input.close();
        boolean result = checkGoal();
        if (result) {
            System.out.println("goal config reached");
            System.exit(0);
        } else {
            System.out.println("goal config not reached");
            System.exit(1);
        }
    }

    // read goalFile file and parse into this.goalBlocks
    protected void parseGoal() {
        this.goalBlocks = new LinkedList<Block>();
        try {
            Scanner theGoal;
            theGoal = new Scanner(new FileReader(this.goalFileName));
            while (theGoal.hasNext()) {
                int tLY = theGoal.nextInt();
                int tLX = theGoal.nextInt();
                int bRY = theGoal.nextInt();
                int bRX = theGoal.nextInt();
                if ((tLY >= tray.height || tLX >= tray.width) || (tLY < 0 || tLX < 0)
                        ||
                        (bRY >= tray.height || bRX >= tray.width) || (bRY < 0 || bRX < 0)) {
                    System.out.println("goal contains an impossible block position");
                    System.exit(5);
                }
                this.goalBlocks.add(new Block(tLY, tLX, bRY, bRX));
            }
            theGoal.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(3);
        }
    }

    //default checking with an init tray
    protected boolean checkGoal() {
        return this.checkGoal(this.tray);
    }

    //check the given tray against goal blocks
    protected boolean checkGoal(Tray tray) {
        for (Block goalBlock: this.goalBlocks) {
            Block a = tray.board[goalBlock.topLY][goalBlock.topLX];
            Block b = tray.board[goalBlock.bottomRY][goalBlock.bottomRX];
            if (a != null) {
                if (!a.equals(b)) {
                    return false;
                }
            } else {
                return false;
            }
        }
        return true;
    }

    protected void checkInitAndGoal() {
        try {
            Scanner init = new Scanner(new FileReader(initFileName));
            Scanner goal = new Scanner(new FileReader(goalFileName));
            String[] initNext = init.nextLine().split(" ");
            String[] goalNext;
            if (initNext.length != 2) {
                System.out.println("improperly formatted file");
                System.exit(5);
            }
            while (init.hasNext()) {
                initNext = init.nextLine().split(" ");
                if (initNext.length != 4) {
                    init.close();
                    System.out.println("improperly formatted file");
                    System.exit(5);
                }
            }
            init.close();
            while (goal.hasNext()) {
                goalNext = goal.nextLine().split(" ");
                if (goalNext.length != 4) {
                    goal.close();
                    System.out.println("improperly formatted file");
                    System.exit(5);
                }
            }
            goal.close();
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(3);
        }
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("unacceptable number of arguments");
            System.exit(2);
        }
        Checker check = new Checker(args[0], args[1]);
        check.checkMoves();
    }
}