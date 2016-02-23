import static org.junit.Assert.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.PriorityQueue;
import org.junit.Test;

public class HuffmanEncodingTest {
    //Method to check content equality of two files
    public boolean compareContents(FileCharIterator iter1, FileCharIterator iter2) {
        while (iter1.hasNext()){
            Object a = iter1.next();
            Object b = iter2.next();

            if (! (a.equals(b))){
                return false;}
        }
        //handling files of different lengths
        if (iter2.hasNext()){
            return false;
        } else {
            return true;
        }
    }

    //Method to check that a header matches that of an encoded file
    public boolean compareHeader(FileCharIterator iterHeader, FileCharIterator iterFile){
        while (iterHeader.hasNext()){
            Object a = iterHeader.next();
            Object b = iterFile.next();
            if (! (a.equals(b))){
                return false;
            }
        }
        return true;
    }

    @Test
    public void encodingAndDecoding() throws Exception {
        //encoding and decoding to files that do not exist
        HuffmanEncoding.main(new String[] {"encode", "testingtext1.txt", "toCompareHeader.txt"});
        HuffmanEncoding.main(new String[] {"decode", "toCompareHeader.txt", "copied1.txt"});
        FileCharIterator iterA = new FileCharIterator("testingtext1.txt");
        FileCharIterator iterB = new FileCharIterator("copied1.txt");
        assertTrue(compareContents(iterA, iterB));

        //encoding and decoding to files that exist (e.g. overwriting)
        HuffmanEncoding.main(new String[] {"encode", "testingtext1.txt", "testingtext2.txt"});
        HuffmanEncoding.main(new String[] {"decode", "testingtext2.txt", "testingtext3.txt"});
        FileCharIterator iterAA = new FileCharIterator("testingtext1.txt");
        FileCharIterator iterC = new FileCharIterator("testingtext3.txt");
        assertTrue(compareContents(iterAA, iterC));

        //encoding/decoding a previously encoded/decoded file to confirm newest file matches original file
        HuffmanEncoding.main(new String[] {"encode", "testingtext3.txt", "testingtext4.txt"});
        HuffmanEncoding.main(new String[] {"decode", "testingtext4.txt", "testingtext5.txt"});
        FileCharIterator iterD = new FileCharIterator("testingtext1.txt");
        FileCharIterator iterE = new FileCharIterator("testingtext5.txt");
        assertTrue(compareContents(iterD, iterE));

        //encoding/decoding an empty file
        try {
            HuffmanEncoding.main(new String[] {"encode", "EmptyFile.txt", "empty.txt"});
            HuffmanEncoding.main(new String[] {"decode", "empty.txt", "Emptyreturned.txt"});
            FileCharIterator iterEmptyFile = new FileCharIterator("Emptyfile.txt");
            FileCharIterator iterEmptyCheck = new FileCharIterator("Emptyreturned.txt");
            assertTrue(compareContents(iterEmptyFile, iterEmptyCheck));
        } catch (Exception e) {
            System.out.println("Exception was caught. Empty file cannot be encoded/decoded.");
        }
    }

    @Test
    public void countFrequencies() {

        HuffmanEncoding huffEncoderObj = new HuffmanEncoding("testingtext1.txt", "cft.txt");
        PriorityQueue<InfoNode> testing = huffEncoderObj.countFrequencies(0);
        System.out.println();
        System.out.println("PQ for testingtext1.txt:");

        for (InfoNode i: testing){
            System.out.println(i.getFreq()+ " " +i.getItem() + " " + i.getClass());
        }
        assertTrue(testing.element().getClass().toString().equals("class InfoNode"));
        assertTrue(testing.peek().getFreq() == 0); //IOP is at front of PQ

        HuffmanEncoding forJPG = new HuffmanEncoding("HangInThere.jpg", "jpg.jpg");
        PriorityQueue<InfoNode> pqJPG = forJPG.countFrequencies(0);
        assertTrue(pqJPG.element().getClass().toString().equals("class InfoNode"));
        assertTrue(pqJPG.size() == 257);
        assertTrue(pqJPG.peek().getFreq() == 0); //IOP is at front of PQ
    }

    @Test
    public void makeHuffmanTree() {
        HuffmanEncoding huffObj = new HuffmanEncoding("testingtext1.txt", "mhtt.txt");
        PriorityQueue<InfoNode> pq = huffObj.countFrequencies(0);
        InfoNode resultTree = huffObj.makeHuffmanTree(pq);
        System.out.println();
        System.out.println("printing resultTree (item, freq): ");
        resultTree.printPreorderTree();
        assertTrue(resultTree.getLeft().getFreq() > resultTree.getLeft().getRight().getFreq());
        assertTrue(resultTree.getRight().getFreq() > resultTree.getRight().getRight().getRight().getFreq());
        assertFalse(resultTree.getRight().getFreq() == resultTree.getLeft().getFreq());
    }

    @Test
    public void makeCodeWords() {
        HuffmanEncoding h = new HuffmanEncoding("testingtext1.txt", "mcwt.txt");
        PriorityQueue<InfoNode> pq = h.countFrequencies(0);
        InfoNode resultTree = h.makeHuffmanTree(pq);
        System.out.println();
        System.out.println("Printing HashMap: ");
        HashMap<String, String> checkCodeWords = h.makeCodeWords(resultTree);
        System.out.println(checkCodeWords + " checkCodeWords");
        System.out.println(checkCodeWords.size());
        //period is not in the file
        assertTrue (checkCodeWords.remove(".") == null);
        //making sure that the binary for \n  has a short codeword since it is the most frequent
        String a = checkCodeWords.remove("00100000");
        assertTrue(a.length() <= 3);
    }

    @Test
    public void outputCodeWords() {
        try{
            File f = new File("temp.txt");
            f.createNewFile();
            PrintWriter reset = new PrintWriter("temp.txt");
            reset.close();

            HuffmanEncoding h1 = new HuffmanEncoding("testingtext1.txt", "temp.txt");
            PriorityQueue<InfoNode> pq = h1.countFrequencies(0);
            InfoNode resultTree = h1.makeHuffmanTree(pq);
            HashMap<String, String> checkCodeWords = h1.makeCodeWords(resultTree);
            h1.outputCodeWords(checkCodeWords);

            FileCharIterator Header = new FileCharIterator("temp.txt");
            FileCharIterator EncodedFile = new FileCharIterator("toCompareHeader.txt");
            assertTrue(compareHeader(Header, EncodedFile));
            f.delete();
        } catch (Exception e) {
            System.out.println("this is never reached and is only included because of these file methods");
        }
    }

    @Test
    public void decode() throws Exception {
        HuffmanEncoding.main(new String[] {"decode", "HangInThere.jpg.huffman", "testingtext2.txt"});
        FileCharIterator iterDecodedHang = new FileCharIterator("testingtext2.txt");
        FileCharIterator iterActualHang = new FileCharIterator("HangInThere.jpg");
        assertTrue(compareContents(iterDecodedHang, iterActualHang));

        HuffmanEncoding.main(new String[] {"decode", "SmallFile.txt.huffman", "small.txt"});
        FileCharIterator iterSmalltoCheck = new FileCharIterator("SmallFile.txt");
        FileCharIterator iterSmallDecoded = new FileCharIterator("small.txt");
        assertTrue(compareContents(iterSmalltoCheck, iterSmallDecoded));

        try {
            HuffmanEncoding.main(new String[] {"decode", "EmptyFile.txt", "shouldBeEmpty.txt"});
            FileCharIterator empty = new FileCharIterator("SmallFile.txt");
            FileCharIterator empty1 = new FileCharIterator("small.txt");
            assertTrue(compareContents(empty, empty1));
        } catch (Exception e) {
            System.out.println();
            System.out.println("Cannot decode empty file.");
        }
    }
}
