import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;


public class FileFreqWordsIteratorTest {

    //Method to compare contents of a file; returns true iff contents are equal.
    private boolean compareContents(FileCharIterator iter1, FileCharIterator iter2) {
        while (iter1.hasNext()){
            Object a = iter1.next();
            Object b = iter2.next();
            if (! (a.equals(b))){
                return false;}

        } if (iter2.hasNext()){
            return false;}
        else{ return true;}
    }

    @Test
    public void emptyEncode2() {
        try{
            HuffmanEncoding.main(new String[] {"encode2", "EmptyFile.txt", "empty.txt", "4"});
        }
        catch (Exception e){
            System.out.println("Cannot encode2 an empty file.");
        }
    }

    @Test
    public void DecodingAnEncoded2File() throws Exception {
        HuffmanEncoding.main(new String[] {"encode2", "testingtext1.txt", "version2text1.txt", "3"});
        HuffmanEncoding.main(new String[] {"decode", "version2text1.txt", "checkv2t1.txt"});
        FileCharIterator x = new FileCharIterator("testingtext1.txt");
        FileCharIterator y = new FileCharIterator("checkv2t1.txt");
        assertTrue(compareContents(x, y));
    }

    @Test
    public void CheckingMostFreqWords() throws Exception {

        FileFreqWordsIterator test1 = new FileFreqWordsIterator("testingtext1.txt", 3);
        ArrayList<String> binaryFreqWords = test1.mostFreqWords;
        System.out.println();
        System.out.println("codemap of testingtext1.txt: ");
        assertTrue(binaryFreqWords.size() == 3);
        for (int i = 0; i < binaryFreqWords.size(); i++){
            System.out.println(binaryFreqWords.get(i));
        } System.out.println();


        FileFreqWordsIterator test11 = new FileFreqWordsIterator("testingtext1.txt", 1);
        ArrayList<String> binaryFreqWords1 = test11.mostFreqWords;
        assertTrue(binaryFreqWords1.size() == 1);
        //header should be identical most frequent from that from test1
        assertTrue(binaryFreqWords1.get(0).toString().equals(binaryFreqWords.get(2).toString()));
        System.out.println("codemap of testingtext1.txt (n=1): ");
        for (int i = 0; i < binaryFreqWords1.size(); i++){
            System.out.println(binaryFreqWords1.get(i));
        } System.out.println();


        FileFreqWordsIterator kittentest1 = new FileFreqWordsIterator("kittenexample.txt", 1);
        ArrayList<String> kittenFreqWords1 = kittentest1.mostFreqWords;
        assertTrue(kittenFreqWords1.size() == 1);
        //checking the generated codemap to that of kitten example in proj2.pdf
        assertTrue(kittenFreqWords1.get(0).toString().equals("011010110110100101110100011101000110010101101110"));
        System.out.println("kittentest1: ");
        for (int i = 0; i < kittenFreqWords1.size(); i++){
            System.out.println(kittenFreqWords1.get(i));
        } System.out.println();


        FileFreqWordsIterator kittentest2 = new FileFreqWordsIterator("kittenexample.txt", 2);
        ArrayList<String> kittenFreqWords2 = kittentest2.mostFreqWords;
        assertTrue(kittenFreqWords2.size() == 2);
        System.out.println("kittentest2: ");
        for (int i = 0; i < kittenFreqWords2.size(); i++){
            System.out.println(kittenFreqWords2.get(i));
        }

        //edge case testing punctuation, non-frequent word at the end
        FileFreqWordsIterator kittentest3 = new FileFreqWordsIterator("kittenexample1.txt", 2);
        ArrayList<String> kittenFreqWords3 = kittentest3.mostFreqWords;
        System.out.println();
        System.out.println("kittentest3: ");
        for (int i = 0; i < kittenFreqWords3.size(); i++){
            System.out.println(kittenFreqWords3.get(i));
        }
        assertTrue(kittenFreqWords2.size() == 2);


        //edge case testing altered punctuation from kittentest3
        //newline and spaces will break the words, therefore kittentest4 != kittentest3
        FileFreqWordsIterator kittentest4 = new FileFreqWordsIterator("kittenexample2.txt", 2);
        ArrayList<String> kittenFreqWords4 = kittentest4.mostFreqWords;
        assertTrue(kittenFreqWords2.size() == 2);
        System.out.println();
        System.out.println("kittentest4: ");
        for (int i = 0; i < kittenFreqWords4.size(); i++){
            System.out.println(kittenFreqWords4.get(i));
        }
    }
}
