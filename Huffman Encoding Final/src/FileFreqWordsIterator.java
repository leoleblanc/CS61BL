import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

public class FileFreqWordsIterator implements Iterator<String> {

    protected FileInputStream input;
    private String wordCheck = "";
    private String inputFileName;
    private int h = 0;
    private boolean started = false;
    private boolean lastChars = false;

    PriorityQueue<InfoNode> wordQueue = new PriorityQueue<InfoNode>();
    ArrayList<String> mostFreqWords = new ArrayList<String>();
    ArrayList<String> toAdd = new ArrayList<String>();
    FileCharIterator iter, iter2;

    public FileFreqWordsIterator(String inputFileName, int amountToKeep) {
        iter = new FileCharIterator(inputFileName);
        iter2 = new FileCharIterator(inputFileName);

        try {
            input = new FileInputStream(inputFileName);
            countFreqWords(amountToKeep);
            this.inputFileName = inputFileName;
        } catch (FileNotFoundException e) {
            System.err.printf("No such file: %s\n", inputFileName);
            System.exit(1);
        } catch (IOException e) {
            System.err.printf("IOException while reading from file %s\n",
                    inputFileName);
            System.exit(1);
        }
    }

    private void countFreqWords(int amountToKeep) {
        ArrayList<InfoNode> list = new ArrayList<InfoNode>();
        String wordBuilt = "";
        String letter;
        InfoNode next;
        int timesToRemove;

        while (iter.hasNext()) {
            letter = iter.next();
            next = new InfoNode(wordBuilt, 1);

            if (!letter.equals(AsciiToBinary(" ")) && !letter.equals(AsciiToBinary("\n"))) {
                wordBuilt += letter;
            } else if (list.indexOf(next) == -1 && wordBuilt.length() >= 16) {
                list.add(next);
                wordBuilt = "";
            } else if (wordBuilt.length() >= 16) {
                list.get(list.indexOf(next)).increment(); // Find word and increment its frequency
                wordBuilt = "";
            } else { // In the case that we just pass by a single character
                wordBuilt = "";
            }
        }

        // Add words to priority queue
        for (int index = 0; index < list.size(); index++) {
            wordQueue.add(list.get(index));
        }
        // Remove nonfrequent words from priority queue
        timesToRemove = wordQueue.size() - amountToKeep;
        while (timesToRemove > 0) {
            wordQueue.poll();
            timesToRemove--;
        }
        // Add to ArrayList
        while(wordQueue.size() > 0) {
            mostFreqWords.add(wordQueue.poll().getItem());
        }
    }

    public static String AsciiToBinary(String asciiString){
        byte[] bytes = asciiString.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes)
        {
            int val = b;
            for (int i = 0; i < 8; i++)
            {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
            // binary.append(' ');
        }
        return binary.toString();
    }

    @Override
    public boolean hasNext() {
        return iter2.hasNext() || lastChars == true;
    }

    @Override
    public String next() {
        String toRtn = "";

        if (!started) {
            while (iter2.hasNext()) {
                String ltr = iter2.next();
                if (!ltr.equals(AsciiToBinary(" ")) && !ltr.equals(AsciiToBinary("\n"))) {
                    wordCheck += ltr;
                    toRtn = wordCheck;
                } else if (!mostFreqWords.contains(wordCheck)) {
                    for (int k = 0; k < wordCheck.length(); k+=8) {
                        toAdd.add(wordCheck.substring(k, k+8));
                    }
                    toAdd.add(ltr);
                    wordCheck = "";
                    started = true;
                    break;
                } else {
                    toRtn = wordCheck;
                    toAdd.add(ltr);
                    wordCheck = "";
                    started = true;
                    return toRtn;
                }
            }
            // In the case that there are no more items to iterate though
            // You still want to return the final items that made up wordCheck 
            if (!iter2.hasNext()) {
                if (!mostFreqWords.contains(wordCheck)) {
                    for (int k = 0; k < wordCheck.length(); k += 8) {
                        toAdd.add(wordCheck.substring(k, k+8));
                    }
                    started = true;
                    lastChars = true;
                }
                else {
                    toRtn = wordCheck;
                    return toRtn;
                }
            }
        }

        if (started == true) {
            toRtn = toAdd.get(h);
            h++;
            if (h == toAdd.size()) {
                h = 0;
                toAdd.clear();
                started = false;
                lastChars = false;
            }
        }
        return toRtn;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException(
                "FileFreqWordsIterator does not delete from files.");
    }

}