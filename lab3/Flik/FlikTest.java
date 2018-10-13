
import static org.junit.Assert.*;
import org.junit.Test;

/** Run test on Flik.java class
 * @author Hai Yan
 * @version 0.1
 */
public class FlikTest {

    @Test
    public void testFlik() {
        int a = 200;
        int b = 200;
        int c = 4;
        assertTrue(Flik.isSameNumber(a, b));
        assertFalse(Flik.isSameNumber(a, c));
        assertFalse(Flik.isSameNumber(b, c));

//        int i = 0;
//        for (int j = 0; i < 500; ++i, ++j) {
//            assertTrue(i == j);
//        }

    }

}
