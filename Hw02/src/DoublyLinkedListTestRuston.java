import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertEquals;

/**
 *  This set of tests is designed to SUPPLEMENT the TA provided JUnits. It does not check
 *  the content of the tests from the provided JUnits. I would highly encourage you to run
 *  and pass those before running these. Many of these tests depend on the assumption that
 *  the basics are functional (ie assumes that the size field is always correct). These
 *  tests are also not designed to check for efficiency in any way.
 *
 * Designed to test:
 * 1. addAtIndex when index is non-zero but closer to head
 * 2. removeAtIndex when index non-tail but closer to tail
 * 3. standard remove methods on list size 1
 * 4. all expected exceptions (i'll probably miss 1 or 2 ngl)
 * 5. removeLastOccurrence at head and tail and on size: 0, 1
 * 6. empty list in toArray()
 *
 * Hope you the first few weeks have treated you all well!
 *
 * @author Ruston Shome
 * @version 1.0
 */
public class DoublyLinkedListTestRuston {
    private DoublyLinkedList<String> list;

    /**
     * If you are getting a time out error, there is a very high chance your code produces an infinite loop.
     * Check the base/break case and increment on any recursive code or While loops
     * Check the indices on any For loops
     */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    @Before
    public void setUp() {
        list = new DoublyLinkedList<>();
    }

    //This section of tests corresponds to traversing from different endpoints
    /**
     * This test checks addAtIndex for non-zero index that is closer to the head.
     * This is useful if you're implementation is different when traversing from the head v. tail
     * The TA provided JUnits only check adding at head, tail, and close to tail.
     */
    @Test
    public void testAddAtIndexFront() {
        for (int i = 0; i < 8; i++) {
            list.addToBack(String.format("b%d", i));
        } //[b0, b1, b2, b3, b4, b5, b6, b7]

        list.addAtIndex(2, "filler1"); //[b0, b1, filler1, b2, b3, b4, b5, b6, b7]

        list.addAtIndex(3, "filler2"); //[b0, b1, filler1, filler2, b2, b3, b4, b5, b6, b7]

        //Checks Head
        DoublyLinkedListNode<String> current = list.getHead();
        assertNotNull(current);
        assertNull(current.getPrevious());
        assertEquals("b0", current.getData());

        //Checks index 1
        DoublyLinkedListNode<String> previous = current;
        current = current.getNext();
        assertNotNull(current);
        assertSame(previous, current.getPrevious());
        assertEquals("b1", current.getData());

        //Checks first add
        previous = current;
        current = current.getNext();
        assertNotNull(current);
        assertSame(previous, current.getPrevious());
        assertEquals("filler1", current.getData());

        //Checks second add
        previous = current;
        current = current.getNext();
        assertNotNull(current);
        assertSame(previous, current.getPrevious());
        assertEquals("filler2", current.getData());

        //checks all other indices
        for (int i = 2; i < 7; i++) {
            previous = current;
            current = current.getNext();
            assertNotNull(current);
            assertSame(previous, current.getPrevious());
            assertEquals(String.format("b%d", i), current.getData());
        }

        //checks tail
        previous = current;
        current = current.getNext();
        assertNotNull(current);
        assertSame(previous, current.getPrevious());
        assertEquals("b7", current.getData());
        assertSame(list.getTail(), current);

        current = current.getNext();
        assertNull(current);
    }

    /**
     * This test checks removeAtIndex for non-tail index that is closer to the tail.
     * This is useful if you're implementation is different when traversing from the head v. tail
     * The TA provided JUnits only check removing at head, tail, and close to head.
     */
    @Test
    public void testRemoveAtIndexBack() {
        for (int i = 0; i < 8; i++) {
            list.addToBack(String.format("b%d", i));
        } //[b0, b1, b2, b3, b4, b5, b6, b7]

        list.removeAtIndex(5); //[b0, b1, b2, b3, b4, b6, b7]

        //checks head
        DoublyLinkedListNode<String> current = list.getHead();
        assertNotNull(current);
        assertNull(current.getPrevious());
        assertEquals("b0", current.getData());
        DoublyLinkedListNode<String> previous;

        //checks all before remove
        for (int i = 1; i < 5; i++) {
            previous = current;
            current = current.getNext();
            assertNotNull(current);
            assertSame(previous, current.getPrevious());
            assertEquals(String.format("b%d", i), current.getData());
        }

        //checks after remove
        previous = current;
        current = current.getNext();
        assertNotNull(current);
        assertSame(previous, current.getPrevious());
        assertEquals("b6", current.getData());

        //checks tail
        previous = current;
        current = current.getNext();
        assertNotNull(current);
        assertSame(previous, current.getPrevious());
        assertEquals("b7", current.getData());
        assertSame(list.getTail(), current);

        current = current.getNext();
        assertNull(current);
    }

    //This section of tests corresponds to removing from lists of size 1
    /**
     * This test is designed to check removeAtIndex method on a list of size one
     */
    @Test
    public void testRemoveAtIndexSizeOne() {
        list.addToBack("filler1");
        Assert.assertEquals("filler1", list.removeAtIndex(0));
        Assert.assertEquals(0, list.size());
        Assert.assertNull(list.getHead());
        Assert.assertNull(list.getTail());
    }

    /**
     * This test is designed to check removeFromFront method on a list of size one
     */
    @Test
    public void testRemoveFromFrontSizeOne() {
        list.addToBack("filler1");
        Assert.assertEquals("filler1", list.removeFromFront());
        Assert.assertEquals(0, list.size());
        Assert.assertNull(list.getHead());
        Assert.assertNull(list.getTail());
    }

    /**
     * This test is designed to check removeFromBack method on a list of size one
     */
    @Test
    public void testRemoveFromBAckSizeOne() {
        list.addToBack("filler1");
        Assert.assertEquals("filler1", list.removeFromBack());
        Assert.assertEquals(0, list.size());
        Assert.assertNull(list.getHead());
        Assert.assertNull(list.getTail());
    }

    //This section of tests corresponds to expected exceptions
    @Test (expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexLowerBounds() {
        list.addAtIndex(-1, "Anything");
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexUpperBounds() {
        list.addAtIndex(1, "Anything");
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddAtIndexNullData() {
        list.addAtIndex(0, null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddToFrontNullData() {
        list.addToFront(null);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testAddToBackNullData() {
        list.addToBack(null);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexLowerBounds() {
        list.removeAtIndex(-1);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexUpperBounds() {
        list.removeAtIndex(1);
    }

    @Test (expected = NoSuchElementException.class)
    public void testRemoveFromFrontEmpty() {
        list.removeFromFront();
    }

    @Test (expected = NoSuchElementException.class)
    public void testRemoveFromBackEmpty() {
        list.removeFromBack();
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testGetLowerBounds() {
        list.get(-1);
    }

    @Test (expected = IndexOutOfBoundsException.class)
    public void testGetUpperBounds() {
        list.get(1);
    }

    @Test (expected = IllegalArgumentException.class)
    public void testRemoveLastOccurrenceNullData() {
        list.removeLastOccurrence(null);
    }

    @Test (expected = NoSuchElementException.class)
    public void testRemoveLastOccurrenceNoData() {
        for (int i = 0; i < 8; i++) {
            list.addToBack(String.format("b%d", i));
        } //[b0, b1, b2, b3, b4, b5, b6, b7]

        list.removeLastOccurrence("Purple Unicorn");
    }

    //This section of tests corresponds to edge cases for removeLastInstance
    @Test
    public void testRemoveLastOccurrenceHead() {
        for (int i = 0; i < 8; i++) {
            list.addToBack(String.format("b%d", i));
        } //[b0, b1, b2, b3, b4, b5, b6, b7]

        list.removeLastOccurrence("b0");
        Assert.assertEquals("b1", (list.getHead()).getData());
        Assert.assertNull((list.getHead()).getPrevious());
        Assert.assertEquals("b2", ((list.getHead()).getNext()).getData());
    }

    @Test
    public void testRemoveLastOccurrenceTail() {
        for (int i = 0; i < 8; i++) {
            list.addToBack(String.format("b%d", i));
        } //[b0, b1, b2, b3, b4, b5, b6, b7]

        list.removeLastOccurrence("b7");
        Assert.assertEquals("b6", (list.getTail()).getData());
        Assert.assertNull((list.getTail()).getNext());
        Assert.assertEquals("b5", ((list.getTail()).getPrevious()).getData());
        Assert.assertEquals(7, list.size());
    }

    @Test
    public void testRemoveLastOccurrenceSizeOne() {
        list.addToBack("Purple Unicorn");
        Assert.assertEquals("Purple Unicorn", list.removeLastOccurrence("Purple Unicorn"));
        Assert.assertEquals(0, list.size());
        Assert.assertNull(list.getHead());
        Assert.assertNull(list.getTail());
    }

    @Test (expected = NoSuchElementException.class)
    public void testRemoveLastOccurrenceSizeZero() {
        list.removeLastOccurrence("Purple Unicorn");
    }

    //This test checks for an empty array on toArray
    @Test
    public void testToArrayEmpty() {
        Assert.assertEquals(new Object[0], list.toArray());
    }
}