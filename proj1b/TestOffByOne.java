import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    @Test
    public void testequalChars() {
        assertTrue(offByOne.equalChars('x','y'));
        assertTrue(offByOne.equalChars('%', '&'));
        assertFalse(offByOne.equalChars('c', 'f'));
        assertFalse(offByOne.equalChars('a', 'A'));
        assertFalse(offByOne.equalChars('a', 'a'));
    }

}