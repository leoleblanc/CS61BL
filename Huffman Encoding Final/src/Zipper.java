import java.io.*;
import java.util.*;
import java.nio.channels.*;

public class Zipper {

    private String input;
    private String output;
    private static String command;
    private File[] listOfFiles;
    private ArrayList<Boolean> isDirectory = new ArrayList<Boolean>();
    private ArrayList<String> filePaths = new ArrayList<String>();
    private ArrayList<Number> fileBytes = new ArrayList<Number>();
    private ArrayList<InfoNode> pathBytes = new ArrayList<InfoNode>();
    private File destination;
    private File temp;

    public Zipper(String input, String output) {
        this.input = input;
        if (command.equals("zipper")) {
            if (!output.endsWith(".zipper")) {
                output = output.concat(".zipper");
            }
        }
        this.output = output;
        temp = new File("temp");
    }

    public void zipper(){
        //step 1, grab files, put them into listOfFiles
        listOfFiles = listify();

        //step 2, get directory paths
        setPaths();

        //step 3, decode onto temp file
        writeToTemp();

        //step 4, write the table of contents
        writeTable();

        //step 5, transfer the bytes from temp to destination
        copyElements(temp, destination);

        //step 6, delete the temp file
        temp.delete();

    }



    public void unzipper() {
        try {
            HashMap<Integer, String> tableOfContents = new HashMap<Integer, String>();
            BufferedReader br = new BufferedReader(new FileReader(this.input));
            int start = 1;
            String line = br.readLine();
            while (!line.equals("")) {
                start += line.length() + 1;
                List<String> list = Arrays.asList(line.split(","));
                list.set(0, this.output + File.separator + list.get(0));
                File f = new File(list.get(0));
                if (!list.get(0).contains(".")) {
                    f.mkdir();
                    f.mkdirs();
                }
                tableOfContents.put(Integer.parseInt(list.get(1)), list.get(0));
                line = br.readLine();
            }
            br.close();

            FileCharIterator fc = new FileCharIterator(this.input);
            int currByte = 0;
            for (int i = 0; i < start; i++) {
                fc.next();
            }
            tableOfContents.remove(-1);

            while (!tableOfContents.isEmpty()) {
                String file = tableOfContents.remove(currByte);
                while (fc.hasNext() && !tableOfContents.containsKey(currByte)) {
                    FileOutputHelper.writeBinStrToFile(fc.next(), "temp");
                    currByte++;
                }
                HuffmanEncoding huffEncoder = new HuffmanEncoding("temp", file);
                huffEncoder.decode();

                temp.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**listify converts a file to a list of files
     */
    public File[] listify() {
        File f = new File(this.input);
        File[] toRtn;
        if (!f.isDirectory()) {
            toRtn = new File[1];
            toRtn[0] = f;
        } else {
            toRtn = f.listFiles();
        }
        return toRtn;
    }

    public void setPaths() {
        if (listOfFiles.length == 1) {
            if (listOfFiles[0].isDirectory()) {
                filePaths.add(this.input);
                isDirectory.add(true);
                setPathsHelper(listOfFiles[0]);
            } else {
                filePaths.add(this.input);
                isDirectory.add(false);
            }
        } else {
            filePaths.add(this.input);
            isDirectory.add(true);
            for (int count = 0; count < listOfFiles.length; count++) {
                String path = listOfFiles[count].getPath();
                filePaths.add(path);
                if (listOfFiles[count].isDirectory()) {
                    isDirectory.add(true);
                    setPathsHelper(listOfFiles[count]);
                } else {
                    isDirectory.add(false);
                }
            }
        }
    }

    public void setPathsHelper(File file) {
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            String path = files[i].getPath();
            filePaths.add(path);
            if (files[i].isDirectory()) {
                isDirectory.add(true);
                setPathsHelper(files[i]);
            } else {
                isDirectory.add(false);
            }
        }
    }

    public void makeFile(){
        try {
            destination = new File(this.output);
            temp = new File("temp");
            if (destination.exists()) {
                PrintWriter reset = new PrintWriter(destination);
                reset.close();
            } else {
                destination.createNewFile();
            }
            if (temp.exists()) {
                PrintWriter reset = new PrintWriter("temp");
                reset.close();
            } else {
                temp.createNewFile();

            }
        } catch (Exception e) {
            if (e.getClass().equals(IOException.class)) {
                System.err.println("IOException found");
            } else {
                System.err.println("FileNotFountException caught");
            }
        }
    }

    public void writeToTemp(){
        try {
            for (int count = 0; count < filePaths.size(); count++) {
                if (!isDirectory.get(count)) {
                    String[] arguments = new String[3];
                    arguments[0] = filePaths.get(count);
                    arguments[1] = "temp";
                    HuffmanEncoding huffCoder = new HuffmanEncoding(arguments[0], arguments[1]);
                    long currDestLength = temp.length();
                    pathBytes.add(new InfoNode(filePaths.get(count), temp.length()));
                    huffCoder.encode();
                    fileBytes.add(temp.length() - currDestLength);
                    PrintWriter out = new PrintWriter(new FileWriter("temp", true));
                    out.println();
                    out.close();
                } else {
                    long bytes = -1;
                    pathBytes.add(new InfoNode(filePaths.get(count), bytes));
                }
            }
            PrintWriter out = new PrintWriter(new FileWriter("temp", true));
//            out.print("nnn");
            out.close();
        } catch (IOException e) {
            System.err.println("IOException caught");
        }
    }

    public void writeTable(){
        try {
            for (int count = 0; count<pathBytes.size(); count++) {
            }
            PrintWriter out = new PrintWriter(new FileWriter(this.output, true));
            for (int count = 0; count < pathBytes.size(); count++) {
                String path = pathBytes.get(count).getItem();
                long bytes = pathBytes.get(count).getBytes();
                out.println(path + "," + bytes);
            }
            out.println(); //make a new line for temp file's contents
            out.close();
        } catch (IOException e) {
            System.err.println("IOException caught");
        }
    }

    private static void copyElements(File targ, File dest){
        try {
            FileOutputStream fos = new FileOutputStream(dest, true);
            BufferedReader toRead = new BufferedReader(new FileReader(targ));
            while (toRead.ready()) {
                fos.write(toRead.read());
            }
            fos.close();
            toRead.close();
        } catch (IOException e) {
            System.err.println("IOException caught");
        }
    }

    //main method, will test zipper & unzipper
    public static void main(String[] args) {
        command = args[0];
        String target = args[1];
        String destination = args[2];
        Zipper zipper = new Zipper(target, destination);
        if (command.equals("zipper")) {
            zipper.makeFile();
            zipper.zipper();
        } else if (command.equals("unzipper")) {
            zipper.unzipper();
        } else {
            System.err.println("unacceptable command");
        }
    }
}