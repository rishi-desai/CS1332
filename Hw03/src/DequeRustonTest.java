import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 *  This set of tests is designed to SUPPLEMENT the TA provided JUnits. It does not check
 *  the content of the tests from the provided JUnits. I would highly encourage you to run
 *  and pass those before running these. Many of these tests depend on the assumption that
 *  the basics are functional (ie assumes that the size field is always correct). These
 *  tests are also not designed to check for efficiency in any way.
 *
 * Designed to test for Arrays:
 *  1. Add to empty (first + last)
 *  2. Remove from size 1 (first + last)
 *  3. wrap around remove (first + last)
 *  4. resize on simple add (first + last)
 *  5. resize with wrap around (first + last)
 *  6. alignment checks (first always refers to first and last always refers to last) are built into most tests
 *  7. size checks are built into each test
 *
 * Designed to test for Linked List:
 * 1. Adding from an empty list (first + last)
 * 2. Removing from a list of size 1 (first + last)
 * 3. Basic Checks on Add + Remove including size and alignment
 *
 *  Also checks all expected exceptions.
 *
 * Hope you all are doing well and staying safe!
 *
 * @author Ruston Shome
 * @version 1.0
 */
public class DequeRustonTest {
    private ArrayDeque<String> array;
    private LinkedDeque<String> linked;

    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    @Before
    public void setup() {
        array = new ArrayDeque<>();
        linked = new LinkedDeque<>();
    }

    //ARRAY TESTS
    /**
     * This test is designed to check edge cases on adding to to an empty list for array addFirst Method
     */
    @Test
    public void testArrayAddFirstEmpty() {
        array.addFirst("flowers");
        Assert.assertEquals("flowers", array.getFirst());
        Assert.assertEquals("flowers", array.getLast());
        Assert.assertEquals(array.getFirst(), array.getLast());
        Assert.assertEquals(1, array.size());
    }

    /**
     * This test is designed to check edge cases on adding to to an empty list for array addLast Method
     */
    @Test
    public void testArrayAddLastEmpty() {
        array.addLast("tulips");
        Assert.assertEquals("tulips", array.getFirst());
        Assert.assertEquals("tulips", array.getLast());
        Assert.assertEquals(array.getFirst(), array.getLast());
        Assert.assertEquals(1, array.size());
    }

    /**
     * This test is designed to check removing from a list of size 1 for array removeFirst Method
     */
    @Test
    public void testArrayRemoveFirstSizeOne() {
        Object[] expected = new Object[ArrayDeque.INITIAL_CAPACITY];
        array.addFirst("hydrangea");
        expected[10] = "hydrangea";

        Assert.assertArrayEquals(expected, array.getBackingArray());

        Assert.assertEquals("hydrangea", array.removeFirst());
        expected[10] = null;

        Assert.assertEquals(0, array.size());
        Assert.assertArrayEquals(expected, array.getBackingArray());
        //at this points front should be equal to 0, but theres no accessor method to test this
        //instead I'll just repeat the test to make sure front was reset properly

        array.addFirst("hydrangea");
        expected[10] = "hydrangea";

        Assert.assertArrayEquals(expected, array.getBackingArray());

        Assert.assertEquals("hydrangea", array.removeFirst());
        expected[10] = null;

        Assert.assertEquals(0, array.size());
        Assert.assertArrayEquals(expected, array.getBackingArray());
    }

    /**
     * This test is designed to check removing from a list of size 1 for array removeLast Method
     */
    @Test
    public void testArrayRemoveLastSizeOne() {
        Object[] expected = new Object[ArrayDeque.INITIAL_CAPACITY];
        array.addLast("jasmine");
        expected[0] = "jasmine";

        Assert.assertArrayEquals(expected, array.getBackingArray());

        Assert.assertEquals("jasmine", array.removeLast());
        expected[0] = null;

        Assert.assertEquals(0, array.size());
        Assert.assertArrayEquals(expected, array.getBackingArray());
        //at this points front should be equal to 0, but theres no accessor method to test this
        //instead I'll just repeat the test to make sure front was reset properly

        array.addLast("jasmine");
        expected[0] = "jasmine";

        Assert.assertArrayEquals(expected, array.getBackingArray());

        Assert.assertEquals("jasmine", array.removeLast());
        expected[0] = null;

        Assert.assertEquals(0, array.size());
        Assert.assertArrayEquals(expected, array.getBackingArray());
    }

    /**
     * This test is designed to check wrap around removing from first
     * Wrap around adding is covered in the TA provided JUnits
     * This test also checks if your front is unaligned (ie get first and remove first go to different data)
     */
    @Test
    public void testArrayWrapAroundRemoveFirst() {
        Object[] expected = new Object[ArrayDeque.INITIAL_CAPACITY];
        for (int i = 4; i >= 0; i--) {
            String add = String.format("b%d", i);
            array.addFirst(add);
            expected[6 + i] = add;
            Assert.assertArrayEquals(expected, array.getBackingArray());
            Assert.assertEquals(5 - i, array.size());
        } //[null, null, null, null, null, null, b0, b1, b2 , b3, b4]

        array.addLast("b5"); //[b5, null, null, null, null, null, b0, b1, b2 , b3, b4]
        expected[0] = "b5";
        Assert.assertEquals(6, array.size());
        Assert.assertArrayEquals(expected, array.getBackingArray());

        array.addLast("b6"); //[b5, b6, null, null, null, null, b0, b1, b2 , b3, b4]
        expected[1] = "b6";
        Assert.assertEquals(7, array.size());
        Assert.assertArrayEquals(expected, array.getBackingArray());

        for (int i = 0; i < 7; i++) {
            Assert.assertEquals(String.format("b%d", i), array.getFirst());
            Assert.assertEquals(String.format("b%d", i), array.removeFirst());
            Assert.assertEquals(6 - i, array.size());
        }
    }

    /**
     * This test is designed to check wrap around removing from last
     * Wrap around adding is covered in the TA provided JUnits
     *  * This test also checks if your front is unaligned (ie get last and remove last go to different data)
     */
    @Test
    public void testArrayWrapAroundRemoveLast() {
        Object[] expected = new Object[ArrayDeque.INITIAL_CAPACITY];
        for (int i = 4; i >= 0; i--) {
            String add = String.format("b%d", i);
            array.addFirst(add);
            expected[6 + i] = add;
            Assert.assertArrayEquals(expected, array.getBackingArray());
            Assert.assertEquals(5 - i, array.size());
        } //[null, null, null, null, null, null, b0, b1, b2 , b3, b4]

        array.addLast("b5"); //[b5, null, null, null, null, null, b0, b1, b2 , b3, b4]
        expected[0] = "b5";
        Assert.assertEquals(6, array.size());
        Assert.assertArrayEquals(expected, array.getBackingArray());

        array.addLast("b6"); //[b5, b6, null, null, null, null, b0, b1, b2 , b3, b4]
        expected[1] = "b6";
        Assert.assertEquals(7, array.size());
        Assert.assertArrayEquals(expected, array.getBackingArray());


        for (int i = 6; i >= 0; i--) {
            Assert.assertEquals(String.format("b%d", i), array.getLast());
            Assert.assertEquals(String.format("b%d", i), array.removeLast());
            Assert.assertEquals(i, array.size());
        }
    }

    /**
     * This test is designed to check a basic resize with the addFirst method
     */
    @Test
    public void testArraySimpleResizeAddFirst() {
        Object[] expected = new Object[ArrayDeque.INITIAL_CAPACITY];
        for (int i = 10; i >= 0; i--) {
            String add = String.format("b%d", i);
            array.addFirst(add);
            expected[i] = add;
            Assert.assertArrayEquals(expected, array.getBackingArray());
            Assert.assertEquals(11 - i, array.size());
        } //[b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10]

        array.addFirst("roses"); //[roses, b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10]
        expected = new Object[]{"roses", "b0", "b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8", "b9", "b10",
                null, null, null, null, null, null, null, null, null, null};
        Assert.assertArrayEquals(expected, array.getBackingArray());
        Assert.assertEquals(12, array.size());
        Assert.assertEquals("roses", array.getFirst());
        Assert.assertEquals("b10", array.getLast());
        Assert.assertEquals(ArrayDeque.INITIAL_CAPACITY * 2, ((Object[]) (array.getBackingArray())).length);
    }

    /**
     * This test is designed to check a basic resize with the addLast method
     */
    @Test
    public void testArraySimpleResizeAddLast() {
        Object[] expected = new Object[ArrayDeque.INITIAL_CAPACITY];
        for (int i = 0; i < 11; i++) {
            String add = String.format("b%d", i);
            array.addLast(add);
            expected[i] = add;
            Assert.assertArrayEquals(expected, array.getBackingArray());
            Assert.assertEquals(i + 1, array.size());
        } //[b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10]

        array.addLast("roses"); //[b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, roses]
        expected = new Object[]{"b0", "b1", "b2", "b3", "b4", "b5", "b6", "b7", "b8", "b9", "b10", "roses",
                null, null, null, null, null, null, null, null, null, null};
        Assert.assertArrayEquals(expected, array.getBackingArray());
        Assert.assertEquals(12, array.size());
        Assert.assertEquals("b0", array.getFirst());
        Assert.assertEquals("roses", array.getLast());
        Assert.assertEquals(ArrayDeque.INITIAL_CAPACITY * 2, ((Object[]) (array.getBackingArray())).length);
    }

    /**
     * This test is designed to check a resize with a wrap around using the addFirst method
     * remember that the front should be move to index zero
     */
    @Test
    public void testArrayWrapAroundResizeAddFirst() {
        Object[] expected = new Object[ArrayDeque.INITIAL_CAPACITY];
        for (int i = 10; i >= 4; i--) {
            String add = String.format("b%d", i);
            array.addFirst(add);
            expected[i] = add;
            Assert.assertArrayEquals(expected, array.getBackingArray());
            Assert.assertEquals(11 - i, array.size());
        } //[null, null, null, null, b4, b5, b6, b7, b8, b9, b10]
        //notice now that the front is at b4

        for (int i = 0; i < 4; i++) {
            String add = String.format("b%d", i);
            array.addLast(add);
            expected[i] = add;
            Assert.assertArrayEquals(expected, array.getBackingArray());
            Assert.assertEquals(i + 8, array.size());
        } //[b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10]

        array.addFirst("index 0"); //["index 0", b4, b5, b6, b7, b8, b9, b10, b0, b1, b2, b3, null, null...]
        expected = new Object[]{"index 0", "b4", "b5", "b6", "b7", "b8", "b9", "b10", "b0", "b1", "b2", "b3",
                null, null, null, null, null, null, null, null, null, null};
        Assert.assertArrayEquals(expected, array.getBackingArray());
        Assert.assertEquals(12, array.size());
        Assert.assertEquals("index 0", array.getFirst());
        Assert.assertEquals("b3", array.getLast());
        Assert.assertEquals(ArrayDeque.INITIAL_CAPACITY * 2, ((Object[]) (array.getBackingArray())).length);
    }

    /**
     * This test is designed to check a resize with a wrap around using the addLast method
     * remember that the front should be move to index zero
     */
    @Test
    public void testArrayWrapAroundResizeAddLast() {
        Object[] expected = new Object[ArrayDeque.INITIAL_CAPACITY];
        for (int i = 10; i >= 4; i--) {
            String add = String.format("b%d", i);
            array.addFirst(add);
            expected[i] = add;
            Assert.assertArrayEquals(expected, array.getBackingArray());
            Assert.assertEquals(11 - i, array.size());
        } //[null, null, null, null, b4, b5, b6, b7, b8, b9, b10]
        //notice now that the front is at b4

        for (int i = 0; i < 4; i++) {
            String add = String.format("b%d", i);
            array.addLast(add);
            expected[i] = add;
            Assert.assertArrayEquals(expected, array.getBackingArray());
            Assert.assertEquals(i + 8, array.size());
        } //[b0, b1, b2, b3, b4, b5, b6, b7, b8, b9, b10]

        array.addLast("last index"); //[b4, b5, b6, b7, b8, b9, b10, b0, b1, b2, b3, "last index", null, null...]
        expected = new Object[]{"b4", "b5", "b6", "b7", "b8", "b9", "b10", "b0", "b1", "b2", "b3", "last index",
                null, null, null, null, null, null, null, null, null, null};
        Assert.assertArrayEquals(expected, array.getBackingArray());
        Assert.assertEquals(12, array.size());
        Assert.assertEquals("b4", array.getFirst());
        Assert.assertEquals("last index", array.getLast());
        Assert.assertEquals(ArrayDeque.INITIAL_CAPACITY * 2, ((Object[]) (array.getBackingArray())).length);
    }

    //LINKED LIST TESTS
    /**
     * This test is designed to check for adding to and empty list from first
     */
    @Test
    public void testLinkedAddFirstEmpty() {
        linked.addFirst("lilies");
        Assert.assertEquals("lilies", linked.getFirst());
        Assert.assertEquals("lilies", linked.getLast());
        Assert.assertEquals("lilies", (linked.getHead()).getData());
        Assert.assertEquals("lilies", (linked.getTail()).getData());
        Assert.assertEquals(linked.getHead(), linked.getTail());
        Assert.assertEquals(linked.getFirst(), linked.getLast());
        Assert.assertEquals(1, linked.size());
    }

    /**
     * This test is designed to check for adding to and empty list from last
     */
    @Test
    public void testLinkedAddLastEmpty() {
        linked.addLast("lilies");
        Assert.assertEquals("lilies", linked.getFirst());
        Assert.assertEquals("lilies", linked.getLast());
        Assert.assertEquals("lilies", (linked.getHead()).getData());
        Assert.assertEquals("lilies", (linked.getTail()).getData());
        Assert.assertEquals(linked.getHead(), linked.getTail());
        Assert.assertEquals(linked.getFirst(), linked.getLast());
        Assert.assertEquals(1, linked.size());
    }

    /**
     * This test is designed to check removing from a list of size 1 for linked removeFirst Method
     */
    @Test
    public void testLinkedRemoveFirstSizeOne() {
        linked.addFirst("phlox");
        Assert.assertEquals("phlox", linked.removeFirst());
        Assert.assertEquals(0, linked.size());
        Assert.assertNull(linked.getHead());
        Assert.assertNull(linked.getTail());
    }

    /**
     * This test is designed to check removing from a list of size 1 for linked removeLast Method
     */
    @Test
    public void testLinkedRemoveLastSizeOne() {
        linked.addLast("goldenrod");
        Assert.assertEquals("goldenrod", linked.removeLast());
        Assert.assertEquals(0, linked.size());
        Assert.assertNull(linked.getHead());
        Assert.assertNull(linked.getTail());
    }

    /**
     * This is a basic add check for addFirst
     */
    @Test
    public void testLinkedAddFirst() {
        for (int i = 0; i < 15; i++) {
            linked.addFirst(String.format("b%d", i));
            Assert.assertEquals(String.format("b%d", i), linked.getFirst());
            Assert.assertEquals(String.format("b%d", i), (linked.getHead()).getData());
            Assert.assertEquals(i + 1, linked.size());
        }

        LinkedNode<String> cur = linked.getHead();
        assertNotNull(cur);
        assertNull(cur.getPrevious());
        assertEquals("b14", cur.getData());
        LinkedNode<String> prev = cur;

        for (int i = 13; i > 0; i--) {
            prev = cur;
            cur = cur.getNext();
            assertNotEquals(null, cur);
            assertEquals(prev, cur.getPrevious());
            assertEquals(String.format("b%d", i), cur.getData());
        }

        prev = cur;
        cur = cur.getNext();
        assertNotNull(cur);
        assertEquals(prev, cur.getPrevious());
        assertEquals("b0", cur.getData());
        assertEquals(linked.getTail(), cur);
        assertNull(cur.getNext());

    }

    /**
     * This is a basic add check for addLast
     */
    @Test
    public void testLinkedAddLast() {
        for (int i = 0; i < 15; i++) {
            linked.addLast(String.format("b%d", i));
            Assert.assertEquals(String.format("b%d", i), linked.getLast());
            Assert.assertEquals(String.format("b%d", i), (linked.getTail()).getData());
            Assert.assertEquals(i + 1, linked.size());
        }

        LinkedNode<String> cur = linked.getTail();
        assertNotNull(cur);
        assertNull(cur.getNext());
        assertEquals("b14", cur.getData());
        LinkedNode<String> next = cur;

        for (int i = 13; i > 0; i--) {
            next = cur;
            cur = cur.getPrevious();
            assertNotEquals(null, cur);
            assertEquals(next, cur.getNext());
            assertEquals(String.format("b%d", i), cur.getData());
        }

        next = cur;
        cur = cur.getPrevious();
        assertNotNull(cur);
        assertEquals(next, cur.getNext());
        assertEquals("b0", cur.getData());
        assertEquals(linked.getHead(), cur);
        assertNull(cur.getPrevious());
    }

    /**
     * This is a basic add check for addFirst
     */
    @Test
    public void testLinkedRemoveFirst() {
        for (int i = 0; i < 15; i++) {
            linked.addFirst(String.format("b%d", i));
        }

        for (int i = 14; i >= 0; i--) {
            Assert.assertEquals(String.format("b%d", i), linked.removeFirst());
            Assert.assertEquals(i, linked.size());
        }
    }

    /**
     * This is a basic add check for addLast
     */
    @Test
    public void testLinkedRemoveLast() {
        for (int i = 0; i < 15; i++) {
            linked.addFirst(String.format("b%d", i));
        }

        for (int i = 0; i < 15; i++) {
            Assert.assertEquals(String.format("b%d", i), linked.removeLast());
            Assert.assertEquals(14 - i, linked.size());
        }
    }

    //All Expected Exceptions
    @Test(expected = IllegalArgumentException.class)
    public void testArrayAddFirstException() {
        array.addFirst(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testArrayAddLastException() {
        array.addLast(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testArrayRemoveFirstException() {
        array.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void testArrayRemoveLastException() {
        array.removeLast();
    }

    @Test(expected = NoSuchElementException.class)
    public void testArrayGetFirstException() {
        array.getFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void testArrayGetLastException() {
        array.getLast();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLinkedAddFirstException() {
        linked.addFirst(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLinkedAddLastException() {
        linked.addLast(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testLinkedRemoveFirstException() {
        linked.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void testLinkedRemoveLastException() {
        linked.removeLast();
    }

    @Test(expected = NoSuchElementException.class)
    public void testLinkedGetFirstException() {
        linked.getFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void testLinkedGetLastException() {
        linked.getLast();
    }

}