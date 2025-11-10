import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit test cases for EvenOddCheckerEnhanced
 */
public class EvenOddCheckerTest {
    
    @Test
    public void testZero() {
        assertTrue("0 should be even", EvenOddCheckerEnhanced.isEven(0));
        assertFalse("0 should not be odd", EvenOddCheckerEnhanced.isOdd(0));
    }
    
    @Test
    public void testPositiveEven() {
        assertTrue("2 should be even", EvenOddCheckerEnhanced.isEven(2));
        assertTrue("100 should be even", EvenOddCheckerEnhanced.isEven(100));
        assertFalse("2 should not be odd", EvenOddCheckerEnhanced.isOdd(2));
    }
    
    @Test
    public void testPositiveOdd() {
        assertTrue("1 should be odd", EvenOddCheckerEnhanced.isOdd(1));
        assertTrue("99 should be odd", EvenOddCheckerEnhanced.isOdd(99));
        assertFalse("1 should not be even", EvenOddCheckerEnhanced.isEven(1));
    }
    
    @Test
    public void testNegativeOne() {
        assertFalse("-1 should not be even", EvenOddCheckerEnhanced.isEven(-1));
        assertTrue("-1 should be odd", EvenOddCheckerEnhanced.isOdd(-1));
    }
    
    @Test
    public void testNegativeEven() {
        assertTrue("-2 should be even", EvenOddCheckerEnhanced.isEven(-2));
        assertTrue("-100 should be even", EvenOddCheckerEnhanced.isEven(-100));
        assertFalse("-2 should not be odd", EvenOddCheckerEnhanced.isOdd(-2));
    }
    
    @Test
    public void testNegativeOdd() {
        assertTrue("-3 should be odd", EvenOddCheckerEnhanced.isOdd(-3));
        assertTrue("-99 should be odd", EvenOddCheckerEnhanced.isOdd(-99));
        assertFalse("-3 should not be even", EvenOddCheckerEnhanced.isEven(-3));
    }
    
    @Test
    public void testMaxValue() {
        assertFalse("Integer.MAX_VALUE should be odd", EvenOddCheckerEnhanced.isEven(Integer.MAX_VALUE));
        assertTrue("Integer.MAX_VALUE should be odd", EvenOddCheckerEnhanced.isOdd(Integer.MAX_VALUE));
    }
    
    @Test
    public void testLargeNumbers() {
        assertTrue("1000000 should be even", EvenOddCheckerEnhanced.isEven(1000000));
        assertTrue("999999 should be odd", EvenOddCheckerEnhanced.isOdd(999999));
    }
}
