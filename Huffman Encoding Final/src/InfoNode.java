import java.util.HashMap;

public class InfoNode implements Comparable<InfoNode> {

    private String item;
    private int freq;
    private long bytes;
    private InfoNode myLeft;
    private InfoNode myRight;

    protected InfoNode(String item, int freq) {
        this.item = item;
        this.freq = freq;
    }

    protected InfoNode(String item, long bytes) {
        this.item = item;
        this.bytes = bytes;
    }

    protected InfoNode(String item, int freq, InfoNode left, InfoNode right) {
        this.item = item;
        this.freq = freq;
        this.myLeft = left;
        this.myRight = right;
    }

    protected String getItem() {
        return this.item;
    }

    protected int getFreq() {
        return this.freq;
    }

    protected long getBytes() {return this.bytes;}

    protected InfoNode getLeft() {
        return this.myLeft;
    }

    protected InfoNode getRight() {
        return this.myRight;
    }

    public void printPreorderTree() {
        if (this == null){
            return;
        } else {
            System.out.println(this.item + " " + this.freq);
            if (this.myLeft != null){
                this.myLeft.printPreorderTree();
            }
            if (this.myRight != null){
                this.myRight.printPreorderTree();
            }
        }
    }

    protected void makeCodeWords(HashMap<String, String> codeWords, String path){
        //Base Case
        if (this.myLeft == null && this.myRight == null) {
            codeWords.put(this.item, path);
            return;
        }
        if (this.myLeft != null) {
            this.myLeft.makeCodeWords(codeWords, path + "0");
        }
        if (this.myRight !=  null) {
            this.myRight.makeCodeWords(codeWords, path + "1");
        }
    }

    protected void increment() {
        this.freq++;
    }

    public int compareTo(InfoNode arg) {
        if (this.freq < arg.freq) {
            return -1;
        }
        if (this.freq > arg.freq) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object arg) {
        return this.item.equals(((InfoNode) arg).item);
    }
}