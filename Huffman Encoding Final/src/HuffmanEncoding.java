import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.io.File;
import java.io.PrintWriter;

public class HuffmanEncoding {

    protected String target;
    protected String destination;
    protected int n;
    protected File output;

    protected HuffmanEncoding(String target, String destination) {
        this.target = target;
        this.destination = destination;
    }

    protected HuffmanEncoding(String target, String destination, String n) {
        this.target = target;
        this.destination = destination;
        this.n = Integer.parseInt(n);
    }

    protected PriorityQueue<InfoNode> countFrequencies(int iterType) {
        ArrayList<InfoNode> list = new ArrayList<InfoNode>();
        PriorityQueue<InfoNode> queueToRtn = new PriorityQueue<InfoNode>();

        list.add(new InfoNode("EOF", 0));

        if (iterType == 0) { //Using FileCharIterator 
            FileCharIterator iter = new FileCharIterator(this.target);
            while (iter.hasNext()) {
                InfoNode next = new InfoNode(iter.next(), 1);
                if (list.isEmpty() || !list.contains(next)) {
                    list.add(next);
                } else {
                    list.get(list.indexOf(next)).increment();
                }
            }
        } else { //Using FileFreqWordsIterator 
            FileFreqWordsIterator iter = new FileFreqWordsIterator(this.target, this.n);
            while (iter.hasNext()) {
                InfoNode next = new InfoNode(iter.next(), 1);
                if (list.isEmpty() || !list.contains(next)) {
                    list.add(next);
                } else {
                    list.get(list.indexOf(next)).increment();
                }
            }
        }

        for (int index = 0; index < list.size(); index++) {
            queueToRtn.add(list.get(index));
        }

        return queueToRtn;
    }

    protected InfoNode makeHuffmanTree(PriorityQueue<InfoNode> queue) {
        while (queue.size() != 1){
            InfoNode left = queue.remove();
            InfoNode right = queue.remove();
            InfoNode combo = new InfoNode (null, left.getFreq()+right.getFreq(), left, right);;
            queue.add(combo);
        }
        return queue.remove();
    }

    protected HashMap<String, String> makeCodeWords(InfoNode huffTree) {
        HashMap <String, String> codeWords = new HashMap<String, String>();
        huffTree.makeCodeWords(codeWords, "");
        return codeWords;
    }

    protected void outputCodeWords(HashMap<String, String> codeWords) {
        StringBuilder output = new StringBuilder();

        for (String key: codeWords.keySet()){
            String codeWord = codeWords.get(key);
            output.append(key);
            output.append(",");
            output.append(codeWord);
            output.append("\n");
        }
        output.append("\n");
        this.writeString(output.toString());
    }

    protected void writeString(String output) {
        for (int i = 0; i < output.length(); i++) {
            String str = HuffmanEncoding.AsciiToBinary(output.substring(i, i+1));
            FileOutputHelper.writeBinStrToFile(str, this.destination);
        }
    }

    // From Stackoverflow
    public static String AsciiToBinary(String asciiString) {
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
        }
        return binary.toString();
    }

    protected void outputEncoding(int iterType, HashMap<String, String> codeWords) {
        StringBuilder output = new StringBuilder();

        if (iterType == 0) {
            FileCharIterator iter = new FileCharIterator(this.target);
            while (iter.hasNext()) {
                String character = iter.next();
                String huffCode = codeWords.get(character);
                output.append(huffCode);
            }
        } else {
            FileFreqWordsIterator iter = new FileFreqWordsIterator(this.target, this.n);
            while (iter.hasNext()) {
                String character = iter.next();
                String huffCode = codeWords.get(character);
                output.append(huffCode);
            }
        }

        output.append(codeWords.get("EOF"));

        for (int i = 0; i < output.length(); i += 8) {
            String str = "";
            if (i + 8 > output.length()) {
                str = output.substring(i, output.length());
                while (str.length() < 8) {
                    str += "0";
                }
            } else {
                str = output.substring(i, i+8);
            }

            FileOutputHelper.writeBinStrToFile(str, this.destination);
        }
    }

    public void encode() {

        PriorityQueue<InfoNode> freqs = this.countFrequencies(0);

        if (freqs.isEmpty()){
            return;
        }
        InfoNode huffTree = this.makeHuffmanTree(freqs);
        HashMap<String, String> codeWords = this.makeCodeWords(huffTree);

        this.outputCodeWords(codeWords);
        this.outputEncoding(0, codeWords);
    }

    public void encode2() {

        PriorityQueue<InfoNode> freqs = this.countFrequencies(1);

        if (freqs.isEmpty()){
            return;
        }
        InfoNode huffTree = this.makeHuffmanTree(freqs);
        HashMap<String, String> codeWords = this.makeCodeWords(huffTree);

        this.outputCodeWords(codeWords);
        this.outputEncoding(1, codeWords);
    }

    public void decode() {
        try {
            HashMap <String, String> codeMap = new HashMap<String, String>();
            BufferedReader br = new BufferedReader(new FileReader(this.target));
            FileCharIterator iter = new FileCharIterator(this.target);
            String line = br.readLine();

            while (!line.equals("")) {
                for (int i = 0; i <= line.length(); i++) {
                    iter.next();
                }
                List<String> list = Arrays.asList(line.split(","));
                codeMap.put(list.get(1), list.get(0));
                line = br.readLine();
            }
            iter.next();
            br.close();

            String str = "";
            String ch = "";

            while (iter.hasNext()) {
                String curr = iter.next();
                for (int i = 0; i < curr.length(); i++) {
                    ch += curr.substring(i, i+1);
                    if (codeMap.containsKey(ch)) {
                        if (codeMap.get(ch).equals("EOF")) {
                            FileOutputHelper.writeBinStrToFile(str, this.destination);
                            return;
                        }
                        str += codeMap.get(ch);
                        ch = "";
                    }
                }
                FileOutputHelper.writeBinStrToFile(str, this.destination);
                str = "";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void makeFile() {
        try {
            output = new File(this.destination);
            if (output.exists()) {
                PrintWriter writer = new PrintWriter(this.destination);
                writer.close();
            } else {
                output.createNewFile();
            }
        } catch (Exception e) {
            if (e.getClass().equals(IOException.class)) {
                System.err.println("IOException found");
            } else {
                System.err.println("FileNotFoundException found");
            }
        }
    }


    public static void main(String[] args) throws Exception{
        if (args.length < 3) {
            System.err.println("Wrong # of inputs");
            return;
        }

        HuffmanEncoding huffEncoder;
        String command = args[0];
        String target = args[1];
        File targetFile = new File(target);
        String destination = args[2];

        if (targetFile.length() == 0){
            throw new Exception("Cannot " + command + " an empty file.");
        }

        if (args.length == 4) {
            String n = args[3];
            System.out.println(args[3]);
            huffEncoder = new HuffmanEncoding(target, destination, n);
        } else {
            huffEncoder = new HuffmanEncoding(target, destination);
        }
        huffEncoder.makeFile();

        if (command.equals("encode")) {
            huffEncoder.encode();

        } else if (command.equals("encode2")) {
            huffEncoder.encode2();

        } else if (command.equals("decode")) {
            huffEncoder.decode();
        }
    }
}
