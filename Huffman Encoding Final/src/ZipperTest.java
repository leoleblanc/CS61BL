import static org.junit.Assert.*;
import org.junit.Test;
import java.io.File;

public class ZipperTest {

    @Test
    public void dotZipper() {
        Zipper.main(new String[] {"zipper", "small.txt", "small.txt"});
        File f = new File("small.txt.zipper");
        assertTrue(f.exists());
        assertTrue(f.getPath().endsWith(".zipper"));
    }

    @Test
    public void zipperWorksOnFile(){
        Zipper.main(new String[] {"zipper", "small.txt", "small.txt"});
        File f = new File("small.txt.zipper");
        assertTrue(f.exists());
    }

    @Test
    public void zipperZips() {
        Zipper.main(new String[] {"zipper", "ZipperTest", "ZipperTest"});
        File f = new File("ZipperTest1.zipper");
        assertTrue(f.exists());
        assertFalse(f.isDirectory());
    }

    @Test
    public void unzipperUnzips() {
        Zipper.main(new String[] {"unzipper" , "example_dir.zipper.txt" , "example_dir_Destination"});
        File f = new File("example_dir_Destination");
        assertTrue(f.exists());
        assertTrue(f.isDirectory());
    }

    @Test
    public void zipAndUnzip() {
        Zipper.main(new String[] {"zipper" , "ZipperTest" , "ZipperTest"});
        Zipper.main(new String[] {"unzipper" , "ZipperTest.zipper" , "ZipperDestination"});
    }

}