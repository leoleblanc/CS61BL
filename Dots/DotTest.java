import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.*;

public class DotTest extends TestCase {

    @Test
    public void testisSameColor(){
        Dot derp = new Dot(Dot.COLOR_BLUE);
        Dot herp = new Dot(Dot.COLOR_BLUE);
        assertTrue(derp.isSameColor(herp));
        assertTrue(derp.isColor(1));
    }

    @Test
    public void testgetColor() {
        Dot test1 = new Dot(Dot.COLOR_BLUE);
        //System.out.println(test1.getColor());
        assertTrue(test1.getColor() == 1);
    }

    @Test
    public void testchangeSelected(){
        Dot a = new Dot(Dot.COLOR_RED);
        a.setMyCoords(1,1);
        assertFalse(a.getIsSelected());
        a.changeSelected();
        assertTrue(a.getIsSelected());
    }


    @Test
    public void testgetMyCoords() {
        Dot a = new Dot(Dot.COLOR_RED);
        a.setMyCoords(1,1);
    }

    @Test
    public void testtoString(){
        Dot powderpuff = new Dot(Dot.COLOR_PURPLE);
        powderpuff.setMyCoords(2,1);
        assertTrue(powderpuff.toString().equals("(2 , 1)"));
    }

    @Test
    public void testgetMyStack(){
        Dot dipping = new Dot(Dot.COLOR_GREEN);
        dipping.setMyCoords(4,5);
        assertTrue(Dot.getTotalStack()==0);

        Dot i = new Dot(Dot.COLOR_GREEN);
        i.setMyCoords(4,5);
        i.changeSelected();
        assertTrue(i.getMyStack() == 1);
        Dot f = new Dot(Dot.COLOR_GREEN);
        f.setMyCoords(4,6);
        f.changeSelected();
        assertTrue(f.getMyStack() == 2);
    }

    @Test
    public void testresetStack(){
        Dot.resetStack();
        Dot umm = new Dot(Dot.COLOR_GREEN);
        umm.setMyCoords(4,5);
        umm.changeSelected();
        assertTrue(Dot.getTotalStack() == 1);
        Dot.resetStack();
        assertTrue(Dot.getTotalStack() == 0);
    }

    @Test
    public void testresetMyStack(){
        Dot w = new Dot(Dot.COLOR_GREEN);
        w.setMyCoords(4,5);
        w.resetMyStack(w);
        Dot.resetStack();
        w.changeSelected();
        assertTrue(w.getMyStack() == 1);
        w.resetMyStack(w);
        assertTrue(w.getMyStack() == 0);
    }

}