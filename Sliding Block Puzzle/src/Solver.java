import java.util.HashSet;
import java.util.ArrayList;
import java.util.LinkedList;

public class Solver  {

    protected Checker check;
    protected String initFileName;
    protected String goalFileName;
    protected LinkedList<TrayNode> fringe;
    protected HashSet<String> seen = new HashSet<String>();

    public Solver(String initFileName, String goalFileName){
        this.initFileName = initFileName;
        this.goalFileName = goalFileName;
        this.check = new Checker(initFileName, goalFileName);
        this.fringe = new LinkedList<TrayNode>();
        Tray initTray = new Tray(initFileName);
        this.markSeen(initTray);
        fringe.push(new TrayNode(initTray));
    }

    public boolean hasSeen(Tray t){
        return seen.contains(t.toString());
    }

    public void markSeen(Tray t){
        seen.add(t.toString());
    }


    public ArrayList<String> solve() {
        TrayNode current  = null;
        while (fringe.size() > 0) {
            current = fringe.remove(0);

            // 1. check if we are at goal state
            if (check.checkGoal(current.tray)) {
                return current.path;
            }

            // 2. find all possible moves (edges) from current node
            ArrayList<String> allMoves = current.tray.getAllMoves();

            // 3. add, to the fringe, each unique (AND NEW) configuration (vertex) from executing the possible moves
            for (String move: allMoves) {
                // a. copy current.tray
                Tray nextTray = current.tray.clone();
                // b. make new traynode
                TrayNode next = new TrayNode(nextTray, current.path, move);
                // c. execute move on new trayNode
                next.tray.executeMove(move);
                // d. if tray is new, add to fringe and mark as seen.
                if (!(this.hasSeen(next.tray)) ) {
                    this.markSeen(next.tray);
                    this.fringe.push(next);
                }
            }
        }

        System.exit(1);
        return current.path;
    }


    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("invalid number of arguments");
            System.exit(2);
        }
        Solver solver = new Solver(args[0], args[1]);
        ArrayList<String> result = solver.solve();
        for (String move: result) {
            System.out.println(move);
        }
        System.exit(0);
    }

    private class TrayNode {
        ArrayList<String> path = new ArrayList<String>();
        Tray tray;

        public TrayNode(Tray tray) {
            this.tray = tray;
        }
        public TrayNode(Tray tray, ArrayList<String> prevPath, String move) {
            this.tray = tray;
            this.path = new ArrayList<String>(prevPath);
            this.path.add(move);
        }

    }
}