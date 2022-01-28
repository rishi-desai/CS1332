import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * This set of tests is meant to test the functionality of the DoublyLinkedList class. These tests are NOT meant to
 * be used as a replacement for the provided tests; please make sure that your DoublyLinkedList implementations
 * pass the provided tests as well. The tests are split into 2 main sections. The first section tests the general
 * method functionality, basically just making sure everything works as intended when provided valid input. The second
 * section tests that each method throws the correct exception under the correct circumstances. To clarify, the first
 * section does NOT test for exceptions AT ALL. All testing for exceptions is done in Section 2.
 *
 *  The specific tests provided by this file can be found below.
 * *********************************************************************************************************************
 *
 * SECTION 1: GENERAL METHOD FUNCTIONALITY (NOT TESTING FOR EXCEPTIONS)
 *
 * 1. Tests that the DoublyLinkedList object is initialized correctly.
 * 2. Tests the addToFront() method, and ensures the size variable is updated accordingly.
 * 3. Tests the addToBack() method, and ensures the size variable is updated accordingly.
 * 4. Tests the addAtIndex() method, and ensures the size variable is updated accordingly. Also tests edge cases, such
 *    as addAtIndex(0) and addAtIndex(size).
 * 5. Tests the removeFromFront() method, and ensures the size variable is updated accordingly. Also checks that
 *    elements are now accessible by their new indices.
 * 6. Tests the removeFromBack() method, and ensures the size variable is updated accordingly.
 * 7. Tests the removeAtIndex() method, and ensures the size variable is updated accordingly. Also tests edge cases,
 *    such as removeAtIndex(0) and removeAtIndex(size - 1).
 * 8. Tests the get() method.
 * 9. Tests the isEmpty() method.
 * 10. Tests the clear() method.
 * 11. Tests the removeLastOccurrence method, and ensures the size variable is updated accordingly. Also tests edge
 *     cases, such as when the specified element is first or last in the list.
 * 12. Tests the toArray() method.
 * *********************************************************************************************************************
 *
 * SECTION 2: TESTING FOR EXCEPTIONS
 *
 * 1. Tests that addAtIndex() throws an IndexOutOfBoundsException when called with an index > size.
 * 2. Tests that addAtIndex() throws an IndexOutOfBoundsException when called with an index < 0.
 * 3. Tests that addAtIndex() throws an IllegalArgumentException when called to add null data to the list.
 * 4. Tests that addToFront() throws an IllegalArgumentException when called to add null data to the list.
 * 5. Tests that addToBack() throws an IllegalArgumentException when called to add null data to the list.
 * 5. Tests that removeAtIndex() throws an IndexOutOfBoundsException when called with index > size - 1.
 * 6. Tests that removeAtIndex() throws an IndexOutOfBoundsException when called with index < 0.
 * 7. Tests that removeFromFront() throws a NoSuchElementException when called on an empty list.
 * 8. Tests that removeFromBack() throws a NoSuchElementException when called on an empty list.
 * 9. Tests that get() throws an IndexOutOfBoundsException when called with an index > size - 1.
 * 10. Tests that get() throws an IndexOutOfBoundsException when called with an index < 0.
 * 11. Tests that removeLastOccurrence() throws an IllegalArgumentException when called to remove null data.
 * 12. Tests that removeLastOccurrence() throws a NoSuchElementException when the element specified to be removed
 *     is not found in the list.
 ***********************************************************************************************************************
 *
 * @author Stefano Ochoa
 * @version 1.0
 */
public class StefanoDoublyLinkedListTest {

    private DoublyLinkedList<String> list;

    @Rule
    public Timeout timeout = Timeout.millis(200);

    @Before
    public void setup() {
        list = new DoublyLinkedList<>();
    } // setup

    /* *******************************************************************************************************
     *
     * SECTION 1: GENERAL METHOD FUNCTIONALITY (NOT TESTING FOR EXCEPTIONS)
     *
     * *******************************************************************************************************/

    /**
     * SECTION 1: TEST 1
     */
    @Test
    public void testInitialization() {
        assertEquals(0, list.size());
        assertNull(list.getHead());
        assertNull(list.getTail());
    } // testInitialization

    /**
     * SECTION 1: TEST 2
     */
    @Test
    public void testAddToFront() {
        list.addToFront("5"); // 5
        assertEquals(1, list.size());
        list.addToFront("4"); // 4 5
        assertEquals(2, list.size());
        list.addToFront("3"); // 3 4 5
        assertEquals(3, list.size());
        list.addToFront("2"); // 2 3 4 5
        assertEquals(4, list.size());
        list.addToFront("1"); // 1 2 3 4 5
        assertEquals(5, list.size());
        list.addToFront("0"); // 0 1 2 3 4 5
        assertEquals(6, list.size());

        assertEquals("0", list.get(0));
        assertEquals("5", list.get(5));
    } // testAddToFrontOfEmptyList

    /**
     * SECTION 1: TEST 3
     */
    @Test
    public void testAddToBack() {
        list.addToBack("0"); // 0
        assertEquals(1, list.size());
        list.addToBack("1"); // 0 1
        assertEquals(2, list.size());
        list.addToBack("2"); // 0 1 2
        assertEquals(3, list.size());
        list.addToBack("3"); // 0 1 2 3
        assertEquals(4, list.size());
        list.addToBack("4"); // 0 1 2 3 4
        assertEquals(5, list.size());
        list.addToBack("5"); // 0 1 2 3 4 5
        assertEquals(6, list.size());

        assertEquals("0", list.get(0));
        assertEquals("5", list.get(5));
    } // testAddToBackOfEmptyList

    /**
     * SECTION 1: TEST 4
     */
    @Test
    public void testAddAtIndex() {
        list.addAtIndex(0, "0"); // 0
        assertEquals(1, list.size());
        list.addAtIndex(1, "1"); // 0 1
        assertEquals(2, list.size());
        list.addAtIndex(2, "3"); // 0 1 3
        assertEquals(3, list.size());
        list.addAtIndex(3, "4"); // 0 1 3 4
        assertEquals(4, list.size());

        list.addAtIndex(2, "2"); // 0 1 2 3 4
        assertEquals(5, list.size());

        assertEquals("0", list.get(0));
        assertEquals("2", list.get(2));
        assertEquals("4", list.get(4));
    } // testAddAtIndex

    /**
     * SECTION 1: TEST 5
     */
    @Test
    public void testRemoveFromFront() {
        for (int i = 0; i < 5; i++) {
            list.addToBack(Integer.toString(i));
        } // for
        // 0 1 2 3 4
        String temp = list.removeFromFront(); // 0
        // 1 2 3 4
        assertEquals("0", temp);
        assertEquals("1", list.get(0));
        assertEquals("4", list.get(3));
        assertEquals(4, list.size());
    } // testRemoveFromFront

    /**
     * SECTION 1: TEST 6
     */
    @Test
    public void testRemoveFromBack() {
        for (int i = 0; i < 5; i++) {
            list.addToBack(Integer.toString(i));
        } // for
        // 0 1 2 3 4
        String temp = list.removeFromBack();
        // 0 1 2 3
        assertEquals("4", temp);
        assertEquals("0", list.get(0));
        assertEquals("2", list.get(2));
        assertEquals(4, list.size());
    } // testRemoveFromBack

    /**
     * SECTION 1: TEST 7
     */
    @Test
    public void testRemoveAtIndex() {
        for (int i = 0; i < 5; i++) {
            list.addToBack(Integer.toString(i));
        } // for
        // 0 1 2 3 4
        list.addAtIndex(3, "5");
        // 0 1 2 5 3 4
        String temp = list.removeAtIndex(3);
        // 0 1 2 3 4
        String temp2 = list.removeAtIndex(0);
        // 1 2 3 4
        String temp3 = list.removeAtIndex(3);
        // 1 2 3
        assertEquals("5", temp);
        assertEquals("0", temp2);
        assertEquals("4", temp3);
        assertEquals("1", list.get(0));
        assertEquals("3", list.get(2));
        assertEquals(3, list.size());
    } // testRemoveAtIndex

    /**
     * SECTION 1: TEST 8
     */
    @Test
    public void testGet() {
        for (int i = 0; i < 5; i++) {
            list.addToBack(Integer.toString(i));
        } // for
        // 0 1 2 3 4
        assertEquals("0", list.get(0));
        assertEquals("2", list.get(2));
        assertEquals("4", list.get(4));
    } // testGet

    /**
     * SECTION 1: TEST 9
     */
    @Test
    public void testIsEmpty() {
        assertTrue(list.isEmpty());
        list.addToBack("0");
        assertFalse(list.isEmpty());
        list.clear();
        assertTrue(list.isEmpty());
    } // testIsEmpty

    /**
     * SECTION 1: TEST 10
     */
    @Test
    public void testClear() {
        for (int i = 0; i < 5; i++) {
            list.addToBack(Integer.toString(i));
        } // for

        list.clear();

        assertEquals(0, list.size());
        assertNull(list.getHead());
        assertNull(list.getTail());
    } // testClear

    /**
     * SECTION 1: TEST 11
     */
    @Test
    public void testRemoveLastOccurrence() {
        for (int i = 0; i < 100; i++) {
            list.addToBack(Integer.toString(i));
        } // for
        list.addAtIndex(50, "73");

        String temp = list.removeLastOccurrence("73");
        assertEquals("73", temp);
        assertEquals("74", list.get(74));
        assertEquals("73", list.get(50));
        assertEquals(100, list.size());

        String temp2 = list.removeLastOccurrence("99");
        assertEquals("99", temp2);
        assertEquals("98", list.get(list.size() - 1));
        assertEquals(99, list.size());

        String temp3 = list.removeLastOccurrence("0");
        assertEquals("0", temp3);
        assertEquals("1", list.get(0));
        assertEquals(98, list.size());
    } // testRemoveLastOccurrence

    /**
     * SECTION 1: TEST 12
     */
    @Test
    public void testToArray() {
        for (int i = 0; i < 51; i++) {
            list.addToBack(Integer.toString(i));
        } // for

        String[] arr = new String[51];
        for (int i = 0; i < 51; i++) {
            arr[i] = Integer.toString(i);
        } // for

        assertArrayEquals(arr, list.toArray());
    } // testToArray

    /* ***********************************************************************************
     *
     * SECTION 2: TESTING FOR EXCEPTIONS
     *
     * ***********************************************************************************/

    /**
     * SECTION 2: TEST 1
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexHighOutOfBounds() {
        for (int i = 0; i < 5; i++) {
            list.addToBack(Integer.toString(i));
        } // for

        list.addAtIndex(6, "L");
    } // testAddAtIndexHighOutOfBounds

    /**
     * SECTION 2: TEST 2
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testAddAtIndexLowOutOfBounds() {
        for (int i = 0; i < 5; i++) {
            list.addToBack(Integer.toString(i));
        } // for

        list.addAtIndex(-1, "L");
    } // testAddAtIndexLowOutOfBounds

    /**
     * SECTION 2: TEST 3
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddAtIndexIllegalArgument() {
        list.addAtIndex(0, null);
    } // testAddAtIndexIllegalArgument

    /**
     * SECTION 2: TEST 4
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddToFrontIllegalArgument() {
        list.addToFront(null);
    } // testAddToFrontIllegalArgument

    /**
     * SECTION 2: TEST 5
     */
    @Test(expected = IllegalArgumentException.class)
    public void testAddToBackIllegalArgument() {
        list.addToBack(null);
    } // testAddToBackIllegalArgument

    /**
     * SECTION 2: TEST 6
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexHighOutOfBounds() {
        for (int i = 0; i < 5; i++) {
            list.addToBack(Integer.toString(i));
        } // for

        String temp = list.removeAtIndex(5);
    } // testRemoveAtIndexHighOutOfBounds

    /**
     * SECTION 2: TEST 7
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testRemoveAtIndexLowOutOfBounds() {
        for (int i = 0; i < 5; i++) {
            list.addToBack(Integer.toString(i));
        } // for

        String temp = list.removeAtIndex(-1);
    } // testRemoveAtIndexLowOutOfBounds

    /**
     * SECTION 2: TEST 8
     */
    @Test(expected = NoSuchElementException.class)
    public void testRemoveFromFrontNoSuchElement() {
        list.removeFromFront();
    } // testRemoveFromFrontNoSuchElement

    /**
     * SECTION 2: TEST 9
     */
    @Test(expected = NoSuchElementException.class)
    public void testRemoveFromBackNoSuchElement() {
        list.removeFromBack();
    } // testRemoveFromBackNoSuchElement

    /**
     * SECTION 2: TEST 10
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetHighOutOfBounds() {
        for (int i = 0; i < 5; i++) {
            list.addToBack(Integer.toString(i));
        } // for

        String temp = list.get(5);
    } // testGetHighOutOfBounds

    /**
     * SECTION 2: TEST 11
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetLowOutOfBounds() {
        for (int i = 0; i < 5; i++) {
            list.addToBack(Integer.toString(i));
        } // for

        String temp = list.get(-1);
    } // testGetHighOutOfBounds

    /**
     * SECTION 2: TEST 12
     */
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveLastOccurrenceIllegalArgument() {
        String temp = list.removeLastOccurrence(null);
    } // testRemoveLastOccurrenceIllegalArgument

    /**
     * SECTION 2: TEST 13
     */
    @Test(expected = NoSuchElementException.class)
    public void testRemoveLastOccurrenceNoSuchElement() {
        for (int i = 0; i < 5; i++) {
            list.addToBack(Integer.toString(i));
        } // for

        String temp = list.removeLastOccurrence("Q");
    } // testRemoveLastOccurrenceNoSuchElement
} // StefanoDoublyLinkedListTest
