package hw3.hash;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }

    @Test
    public void testWithDeadlyParams() {
        List<Oomage> deadlyList = new ArrayList<>();
        List<Integer> input1 = new ArrayList<>();
        input1.add(6);
        input1.add(0);
        input1.add(0);
        input1.add(0);
        deadlyList.add(new ComplexOomage(input1));

        List<Integer> input2 = new ArrayList<>();
        input2.add(0);
        input2.add(36);
        input2.add(0);
        input2.add(0);
        deadlyList.add(new ComplexOomage(input2));

        List<Integer> input3 = new ArrayList<>();
        input3.add(0);
        input3.add(0);
        input3.add(216);
        input3.add(0);
        deadlyList.add(new ComplexOomage(input3));

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
