import org.junit.Assert;
import org.junit.Rule;
import org.junit.rules.Timeout;
import org.junit.Before;
import org.junit.Test;


/**
 * This set of tests is designed to SUPPLEMENT the TA provided JUnits. It does not check
 * the content of the tests from the provided JUnits. I would highly encourage you to run
 * and pass those before running these. Many of these tests depend on the assumption that
 * the basics are functional (ie assumes that the size field is always correct). These
 * tests are also not designed to check for efficiency in any way.
 *
 * This set of JUnits is not necessarily exhaustive. It IS designed to cover the following:
 * 1.Proper Resizing on Add Methods
 * 2.Null Checking on Add Methods
 * 3.No Resize on Remove Methods
 * 4.Null values in all unused cells + contiguous storage
 * 5.Bound checking on Add and Remove methods
 *
 * Hope you find these useful and feedback is always appreciated! With Love, Ruston.
 *
 * @author Ruston Shome <3
 * @version 4.0
 */
public class ArrayListTestRuston {
    private ArrayList<String> list;

    /**
     * If you are getting a time out error, there is a very high chance your code produces an infinite loop.
     * Check the base/break case and increment on any recursive code or While loops
     * Check the indices on any For loops
     */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    @Before
    public void setup() {
        list = new ArrayList<>();
    }

    @Test
    public void testAddToFrontResizing() {
        for (int i = 9; i >= 0; i--) {
            String input = String.format("b0%d", i);
            list.addToFront(input);
        }
        int actualCapacity = ((Object[]) (list.getBackingArray())).length;
        Assert.assertEquals("list did not double capacity", ArrayList.INITIAL_CAPACITY * 2, actualCapacity);
    }

    @Test
    public void testAddToBackResizing() {
        for (int i = 0; i <= 9; i++) {
            String input = String.format("b0%d", i);
            list.addToBack(input);
        }
        int actualCapacity = ((Object[]) (list.getBackingArray())).length;
        Assert.assertEquals("list did not double capacity", ArrayList.INITIAL_CAPACITY * 2, actualCapacity);
    }

    @Test
    public void testAddAtIndexResizing() {
        for (int i = 2; i <= 7; i++) {
            String input = String.format("b0%d", i);
            list.addToBack(input);
        }
        list.addAtIndex(0, "b00");
        list.addAtIndex(7, "bo8");
        list.addAtIndex(8, "bo9");
        list.addAtIndex(1, "b01");
        int actualCapacity = ((Object[]) (list.getBackingArray())).length;
        Assert.assertEquals("list did not double capacity", ArrayList.INITIAL_CAPACITY * 2, actualCapacity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddAtIndexNullCheck() {
        list.addAtIndex(0, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddToFrontNullCheck() {
        list.addToFront(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddToBackNullCheck() {
        list.addToBack(null);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexLowerBoundsCheck() {
        list.addAtIndex(-1, "Filler");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexUpperBoundsCheck() {
        list.addAtIndex(1234567890, "Filler");
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexLowerBoundsCheck() {
        list.removeAtIndex(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexUpperBoundsCheck() {
        list.removeAtIndex(1234567890);
    }

    /**
     * This test is designed to ensure removing an element from a list at capacity does not resize the list.
     * Removing elements should never result in a resize of any kind.
     */
    @Test
    public void testRemoveAtIndexNoResize() {
        //No Resize
        for (int i = 0; i < 9; i++) {
            String input = String.format("b0%d", i);
            list.addToBack(input);
        }
        list.removeAtIndex(4);
        int actualCapacity = ((Object[]) (list.getBackingArray())).length;
        Assert.assertEquals("The list resized when removing an element from a list while at capacity",
                ArrayList.INITIAL_CAPACITY, actualCapacity);

        //With Resize
        list.addToBack("Filler1");
        list.addToBack("Filler2");
        list.addToBack("Filler3");
        list.removeAtIndex(0);
        list.removeAtIndex(3);
        list.removeAtIndex(8);
        int newActualCapacity = ((Object[]) (list.getBackingArray())).length;
        Assert.assertEquals("The list resized when moving from above to below initial capacity.",
                ArrayList.INITIAL_CAPACITY * 2, newActualCapacity);
    }
    
    /**
     * This test is designed to ensure removing an element from a list at capacity does not resize the list.
     * Removing elements should never result in a resize of any kind.
     */
    @Test
    public void testRemoveFromBackNoResize() {
        //No Resize
        for (int i = 0; i < 9; i++) {
            String input = String.format("b0%d", i);
            list.addToBack(input);
        }
        list.removeFromBack();
        int actualCapacity = ((Object[]) (list.getBackingArray())).length;
        Assert.assertEquals("The list resized when removing an element from a list while at capacity",
                ArrayList.INITIAL_CAPACITY, actualCapacity);

        //With Resize
        list.addToBack("Filler1");
        list.addToBack("Filler2");
        list.addToBack("Filler3");
        list.removeFromBack();
        list.removeFromBack();
        list.removeFromBack();
        int newActualCapacity = ((Object[]) (list.getBackingArray())).length;
        Assert.assertEquals("The list resized when moving from above to below initial capacity.",
                ArrayList.INITIAL_CAPACITY * 2, newActualCapacity);
    }

    /**
     * This test is designed to ensure removing an element from a list at capacity does not resize the list.
     * Removing elements should never result in a resize of any kind.
     */
    @Test
    public void testRemoveFromFrontNoResize() {
        //No Resize
        for (int i = 0; i < 9; i++) {
            String input = String.format("b0%d", i);
            list.addToBack(input);
        }
        list.removeFromFront();
        int actualCapacity = ((Object[]) (list.getBackingArray())).length;
        Assert.assertEquals("The list resized when removing an element from a list while at capacity",
                ArrayList.INITIAL_CAPACITY, actualCapacity);

        //With Resize
        list.addToBack("Filler1");
        list.addToBack("Filler2");
        list.addToBack("Filler3");
        list.removeFromFront();
        list.removeFromFront();
        list.removeFromFront();
        int newActualCapacity = ((Object[]) (list.getBackingArray())).length;
        Assert.assertEquals("The list resized when moving from above to below initial capacity.",
                ArrayList.INITIAL_CAPACITY * 2, newActualCapacity);
    }

    /**
     * This test is designed to ensure that there are no empty spaces between data and 
     * ensure that there are only null values after the data.
     * This is the concept of "Zero-Alignment" in lecture.
     */
    @Test
    public void testContinguosStorageOriginalSize() {
        list.addToFront("Filler1");
        list.addToBack("Filler2");
        list.addToBack("Filler3");
        list.addToFront("Filler0");
        list.addAtIndex(0, "Filler#");
        list.addAtIndex(2, "Filler!");
        list.addAtIndex(6, "Filler$");
        list.removeFromFront();
        list.removeFromBack();
        list.removeAtIndex(4);
        list.removeAtIndex(0);
        list.removeAtIndex(2);

        int actualCapacity = ((Object[]) (list.getBackingArray())).length;
        for (int i = 0; i < list.size(); i++) {
            Assert.assertNotNull(((Object[]) (list.getBackingArray()))[i]);
        }
        for (int i = list.size(); i < actualCapacity; i++) {
            Assert.assertNull(((Object[]) (list.getBackingArray()))[i]);
        }
    }

    /**
     * This test is designed to ensure that there are no empty spaces between data and 
     * ensure that there are only null values after the data.
     * This is the concept of "Zero-Alignment" in lecture.
     */
    @Test
    public void testContinguosStorageReSize() {
        for (int i = 9; i >= 0; i--) {
            String input = String.format("b0%d", i);
            list.addToFront(input);
        }

        list.addToFront("Filler1");
        list.addToBack("Filler2");
        list.addToBack("Filler3");
        list.addToFront("Filler0");
        list.addAtIndex(0, "Filler#");
        list.addAtIndex(2, "Filler!");
        list.addAtIndex(6, "Filler$");
        list.removeFromFront();
        list.removeFromBack();
        list.removeAtIndex(4);
        list.removeAtIndex(0);
        list.removeAtIndex(2);

        int actualCapacity = ((Object[]) (list.getBackingArray())).length;
        for (int i = 0; i < list.size(); i++) {
            Assert.assertNotNull(((Object[]) (list.getBackingArray()))[i]);
        }
        for (int i = list.size(); i < actualCapacity; i++) {
            Assert.assertNull(((Object[]) (list.getBackingArray()))[i]);
        }
    }
}