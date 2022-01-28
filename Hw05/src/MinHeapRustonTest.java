import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * This set of tests is designed to SUPPLEMENT the TA provided JUnits.
 * I would highly encourage you to run and pass those tests.
 *
 *  Designed to Test:
 *  1. ArrayList constructor edge cases
 *  2. Add edge cases
 *  3. Remove edge cases
 *  4. getMin
 *  5. isEmpty + Clear
 *  6. Size incrementation for all operations
 *  7. All Expected Exception
 *
 *  Not Designed to Test:
 *  1. Efficiency
 *
 * NOTE: I tried to include a good mix of edge cases to cover both recursive and iterative implementations.
 * So far I have only tested these against a recursive implementation, so if anything comes up with the iterative
 * before I get around to it just let me know!
 *
 * I hope everyone is doing fantastic and staying safe. As a plug, adults with underlying conditions are now eligible
 * for COVID-19 booster vaccines through Georgia Tech. You can book an appt through mytest.gatech.edu
 *
 *
 *  @author Ruston Shome
 *  @version 1.0
 */

public class MinHeapRustonTest {
    private MinHeap<Integer> heap;
    private static Integer zero = 0;
    private static Integer one = 1;
    private static Integer two = 2;
    private static Integer three = 3;
    private static Integer four = 4;
    private static Integer five = 5;
    private static Integer six = 6;
    private static Integer seven = 7;
    private static Integer eight = 8;
    private static Integer nine = 9;
    private static Integer nOne = -1;
    private static Integer nTwo = -2;
    private static Integer nThree = -3;
    private static Integer nFour = -4;
    private static Integer nFive = -5;
    private static Integer nSix = -6;
    private static Integer nSeven = -7;
    private static Integer nEight = -8;
    private static Integer nNine = -9;



    /**
     * If you are getting a time out error, there is a very high chance your code produces an infinite loop.
     * Check the base/break case and increment on any recursive code or While loops
     * Check the indices on any For loops
     */
    @Rule
    public Timeout timeout = Timeout.seconds(5);

    @Before
    public void setup() {
        heap = new MinHeap<Integer>();
    }

    /**************************************************************************************
     ArrayList Constructor
     ***********************************************************************************/
    @Test
    public void testArrayListConstructorReverseSorted() {
        ArrayList<Integer> buildArray = new ArrayList<>();
        Integer[] expected = new Integer[43];
        for (int i = 10; i >= -10; i--) {
            Integer add = new Integer(i);
            buildArray.add(add);
        }
        expected[1] = -10;
        expected[2] = -9;
        expected[3] = -4;
        expected[4] = -8;
        expected[5] = 0;
        expected[6] = -2;
        expected[7] = -3;
        expected[8] = -6;
        expected[9] = -7;
        expected[10] = 1;
        expected[11] = 10;
        expected[12] = -1;
        expected[13] = 5;
        expected[14] = 8;
        expected[15] = 4;
        expected[16] = -5;
        expected[17] = 3;
        expected[18] = 7;
        expected[19] = 2;
        expected[20] = 6;
        expected[21] = 9;

        MinHeap<Integer> myHeap = new MinHeap<>(buildArray);
        Assert.assertArrayEquals(expected, myHeap.getBackingArray());
    }

    @Test
    public void testArrayListConstructorSorted() {
        ArrayList<Integer> buildArray = new ArrayList<>();
        Integer[] expected = new Integer[43];
        for (int i = -10; i <= 10; i++) {
            Integer add = new Integer(i);
            buildArray.add(add);
            expected[i + 11] = add;
        }
        MinHeap<Integer> myHeap = new MinHeap<>(buildArray);
        Assert.assertArrayEquals(expected, myHeap.getBackingArray());
    }

    @Test
    public void testArrayListConstructorUnsorted() {
        ArrayList<Integer> buildArray = new ArrayList<>();
        Integer[] expected = new Integer[15];
        buildArray.add(six);
        buildArray.add(four);
        buildArray.add(nSeven);
        buildArray.add(nEight);
        buildArray.add(one);
        buildArray.add(five);
        buildArray.add(seven);
        expected[1] = nEight;
        expected[2] = one;
        expected[3] = nSeven;
        expected[4] = four;
        expected[5] = six;
        expected[6] = five;
        expected[7] = seven;

        MinHeap<Integer> myHeap = new MinHeap<>(buildArray);
        Assert.assertArrayEquals(expected, myHeap.getBackingArray());
    }

    @Test
    public void testArrayListConstructorNoRightChild() {
        ArrayList<Integer> buildArray = new ArrayList<>();
        Integer[] expected = new Integer[29];
        for (int i = 6; i >= -7; i--) {
            Integer add = new Integer(i);
            buildArray.add(add);
        }
        expected[1] = -7;
        expected[2] = -4;
        expected[3] = -6;
        expected[4] = -2;
        expected[5] = -3;
        expected[6] = -5;
        expected[7] = 0;
        expected[8] = -1;
        expected[9] = 3;
        expected[10] = 5;
        expected[11] = 2;
        expected[12] = 6;
        expected[13] = 1;
        expected[14] = 4;

        MinHeap<Integer> myHeap = new MinHeap<>(buildArray);
        Assert.assertArrayEquals(expected, myHeap.getBackingArray());
    }

    /**************************************************************************************
     Add
     ***********************************************************************************/
    @Test
    public void testAddToEmpty() {
        heap.add(zero);
        Integer[] expected = new Integer[MinHeap.INITIAL_CAPACITY];
        expected[1] = zero;
        Assert.assertArrayEquals(expected, heap.getBackingArray());
    }

    @Test
    public void testAddNoRightChild() {
        heap.add(zero);
        heap.add(nOne);
        heap.add(one);
        heap.add(nTwo);
        Integer[] expected = new Integer[MinHeap.INITIAL_CAPACITY];
        expected[1] = nTwo;
        expected[2] = nOne;
        expected[3] = one;
        expected[4] = zero;
        Assert.assertArrayEquals(expected, heap.getBackingArray());
    }

    @Test
    public void testAddWithUpHeap() {
        Integer[] expected = new Integer[MinHeap.INITIAL_CAPACITY];
        for (int i = 5; i >= -5; i--) {
            Integer add = new Integer(i);
            heap.add(add);
        }

        expected[1] = -5;
        expected[2] = -4;
        expected[3] = 0;
        expected[4] = -1;
        expected[5] = -3;
        expected[6] = 4;
        expected[7] = 1;
        expected[8] = 5;
        expected[9] = 2;
        expected[10] = 3;
        expected[11] = -2;

        Assert.assertArrayEquals(expected, heap.getBackingArray());
        Assert.assertEquals(11, heap.size());
    }

    @Test
    public void testAddWithResize() {
        Integer[] expected = new Integer[2 * MinHeap.INITIAL_CAPACITY];
        for (int i = 6; i >= -7; i--) {
            Integer add = new Integer(i);
            heap.add(add);
        }

        expected[1] = -7;
        expected[2] = -3;
        expected[3] = -6;
        expected[4] = 0;
        expected[5] = -2;
        expected[6] = -4;
        expected[7] = -5;
        expected[8] = 6;
        expected[9] = 3;
        expected[10] = 4;
        expected[11] = -1;
        expected[12] = 5;
        expected[13] = 1;
        expected[14] = 2;

        Assert.assertArrayEquals(expected, heap.getBackingArray());
        Assert.assertEquals(14, heap.size());
    }

    @Test
    public void testAddMultipleResize() {
        Integer[] expected = new Integer[4 * MinHeap.INITIAL_CAPACITY];
        for (int i = 0; i < 28; i++) {
            Integer add = new Integer(i);
            heap.add(add);
            expected[i + 1] = i;
        }
        Assert.assertArrayEquals(expected, heap.getBackingArray());
        Assert.assertEquals(28, heap.size());
    }

    @Test
    public void testAddNewMin() {
        heap.add(one);
        heap.add(two);
        heap.add(three);
        heap.add(four);
        heap.add(five);
        heap.add(six);

        heap.add(zero);

        Integer[] expected = new Integer[MinHeap.INITIAL_CAPACITY];
        expected[0] = null;
        expected[1] = zero;
        expected[2] = two;
        expected[3] = one;
        expected[4] = four;
        expected[5] = five;
        expected[6] = six;
        expected[7] = three;

        Assert.assertArrayEquals(expected, heap.getBackingArray());
    }

    @Test
    public void testAddInRange() {
        heap.add(zero);
        heap.add(two);
        heap.add(four);
        heap.add(six);
        heap.add(eight);

        heap.add(one);
        heap.add(three);
        heap.add(five);
        heap.add(seven);

        Integer[] expected = {null, zero, two, one, five, eight, four, three, six, seven, null, null, null};
        Assert.assertArrayEquals(expected, heap.getBackingArray());
    }

    /**************************************************************************************
     Remove
     ***********************************************************************************/
    @Test
    public void removeSizeOne() {
        heap.add(zero);
        Assert.assertSame(zero, heap.remove());
    }

    @Test
    public void removeSizeTwo() {
        heap.add(zero);
        heap.add(one);
        Assert.assertSame(zero, heap.remove());
        Assert.assertSame(one, heap.remove());
    }

    @Test
    public void removeSizeThree() {
        heap.add(zero);
        heap.add(one);
        heap.add(two);
        Assert.assertSame(zero, heap.remove());
        Assert.assertSame(one, heap.remove());
        Assert.assertSame(two, heap.remove());
    }

    @Test
    public void removeSizeFour() {
        heap.add(zero);
        heap.add(one);
        heap.add(two);
        heap.add(three);
        Assert.assertSame(zero, heap.remove());
        Assert.assertSame(one, heap.remove());
        Assert.assertSame(two, heap.remove());
        Assert.assertSame(three, heap.remove());
    }

    @Test
    public void removeAll() {
        for (int i = 6; i >= -7; i--) {
            Integer add = new Integer(i);
            heap.add(add);
        }

        for (int i = -7; i >= 6; i--) {
            Assert.assertEquals(new Integer(i), heap.remove());
        }
    }


    /**************************************************************************************
     Other Methods
     ***********************************************************************************/
    @Test
    public void testGetMin() {
        ArrayList<Integer> buildArray = new ArrayList<>();
        buildArray.add(four);
        buildArray.add(six);
        buildArray.add(nTwo);
        buildArray.add(zero);
        buildArray.add(nine);
        buildArray.add(three);
        MinHeap<Integer> myHeap = new MinHeap<>(buildArray);

        Assert.assertSame(nTwo, myHeap.getMin());
        myHeap.remove();
        Assert.assertSame(zero, myHeap.getMin());
        myHeap.remove();
        Assert.assertSame(three, myHeap.getMin());
        myHeap.remove();
        Assert.assertSame(four, myHeap.getMin());
        myHeap.remove();
        Assert.assertSame(six, myHeap.getMin());
        myHeap.remove();
        Assert.assertSame(nine, myHeap.getMin());
        myHeap.remove();
    }

    @Test
    public void testIsEmpty() {
        Assert.assertTrue(heap.isEmpty());
        heap.add(zero);
        Assert.assertFalse(heap.isEmpty());
        heap.remove();
        Assert.assertTrue(heap.isEmpty());
    }

    @Test
    public void testClear() {
        heap.add(one);
        heap.add(two);
        heap.add(three);
        heap.add(four);
        Assert.assertEquals(4, heap.size());
        Assert.assertNotNull(heap.getBackingArray());

        heap.clear();
        Assert.assertEquals(0, heap.size());
        Assert.assertArrayEquals(new Comparable[MinHeap.INITIAL_CAPACITY], heap.getBackingArray());
    }

    /**************************************************************************************
     Size
     ***********************************************************************************/
    @Test
    public void testDefaultSize() {
        Assert.assertArrayEquals(new Comparable[MinHeap.INITIAL_CAPACITY], heap.getBackingArray());
        Assert.assertEquals(0, heap.size());
    }

    @Test
    public void testBuildHeapSize() {
        ArrayList<Integer> buildArray = new ArrayList<>();
        buildArray.add(four);
        buildArray.add(six);
        buildArray.add(nTwo);
        buildArray.add(zero);
        buildArray.add(nine);
        buildArray.add(three);
        MinHeap<Integer> myHeap = new MinHeap<>(buildArray);

        //Comparable[] expected = {null, nTwo, zero, three, six, nine, four, null, null, null, null, null, null};

        Assert.assertEquals(6, myHeap.size());
        //Assert.assertArrayEquals(expected, myHeap.getBackingArray());
    }

    @Test()
    public void testAddNullSizeCheck() {
        try {
            heap.add(null);
        } catch (IllegalArgumentException e) {
            //do nothing
        }
        Assert.assertEquals(0, heap.size());
    }

    @Test
    public void testAddSizeCheck() {
        heap.add(six);
        Assert.assertEquals(1, heap.size());
        heap.add(nThree);
        Assert.assertEquals(2, heap.size());
        heap.add(three);
        Assert.assertEquals(3, heap.size());
        heap.add(five);
        Assert.assertEquals(4, heap.size());
        heap.add(nSeven);
        Assert.assertEquals(5, heap.size());
        heap.add(one);
        Assert.assertEquals(6, heap.size());
        heap.add(nFour);
        Assert.assertEquals(7, heap.size());
    }

    @Test
    public void testAddResizeSizeCheck() {
        ArrayList<Integer> buildArray = new ArrayList<>();
        buildArray.add(three);
        buildArray.add(eight);
        buildArray.add(nFour);
        MinHeap<Integer> myHeap = new MinHeap<>(buildArray);

        Assert.assertEquals(3, myHeap.size());

        myHeap.add(nOne);
        myHeap.add(five);
        myHeap.add(two);
        myHeap.add(zero);

        Assert.assertEquals(7, myHeap.size());
    }

    @Test
    public void testRemoveEmptySizeCheck() {
        try {
            heap.remove();
        } catch (NoSuchElementException e) {
            //do nothing
        }
        Assert.assertEquals(0, heap.size());
    }

    @Test
    public void testRemoveSizeCheck() {
        heap.add(one);
        heap.add(two);
        heap.add(three);
        heap.add(four);
        Assert.assertEquals(4, heap.size());

        heap.remove();
        Assert.assertEquals(3, heap.size());
        heap.remove();
        Assert.assertEquals(2, heap.size());
        heap.remove();
        Assert.assertEquals(1, heap.size());
        heap.remove();
        Assert.assertEquals(0, heap.size());
    }

    @Test
    public void testClearSizeCheck() {
        heap.add(one);
        heap.add(two);
        heap.add(three);
        heap.add(four);
        Assert.assertEquals(4, heap.size());

        heap.clear();
        Assert.assertEquals(0, heap.size());
    }

    /**************************************************************************************
     EXPECTED EXCEPTIONS
     ***********************************************************************************/
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorExceptionNullList() {
        ArrayList<Integer> myList = null;
        MinHeap<Integer> myHeap = new MinHeap<>(myList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorExceptionNullElements() {
        ArrayList<Integer> myList = new ArrayList<>();
        myList.add(two);
        myList.add(zero);
        myList.add(null);
        myList.add(nine);
        MinHeap<Integer> myHeap = new MinHeap<>(myList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddException() {
        heap.add(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveException() {
        heap.remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetMinException() {
        heap.getMin();
    }
}