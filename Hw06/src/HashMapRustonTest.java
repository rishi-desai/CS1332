import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.HashSet;
import java.util.NoSuchElementException;
/**
 *  Designed to Test: Just about everything EXCEPT efficiency
 *
 * Hope you all are doing fantastic and staying dry! As always please let me know if you see anything wrong.
 * Due to the sheer number of tests this week, it's almost certain that there is at least one error
 *
 *  @author Ruston Shome
 *  @version 1.0
 */
public class HashMapRustonTest {
    private LinearProbingHashMap<Integer, String> map;
    /**
     * If you are getting a time out error, there is a very high chance your code produces an infinite loop.
     * Check the base/break case and increment on any recursive code or While loops
     * Check the indices on any For loops
     */
    @Rule
    public Timeout timeout = Timeout.seconds(5);

    @Before
    public void setup() {
        map = new LinearProbingHashMap<>();
    }

    /**************************************************************************************
     Constructors
     ***********************************************************************************/
    @Test
    public void testNoArgConstructor() {
        Assert.assertEquals(0, map.size());
        Assert.assertArrayEquals(new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY], map.getTable());
    }

    @Test
    public void testOneArgConstructor() {
        LinearProbingHashMap<Integer, String> myMapOne = new LinearProbingHashMap<>(0);
        Assert.assertEquals(0, myMapOne.size());
        Assert.assertArrayEquals(new LinearProbingMapEntry[0], myMapOne.getTable());

        LinearProbingHashMap<Integer, String> myMapTwo = new LinearProbingHashMap<>(1);
        Assert.assertEquals(0, myMapTwo.size());
        Assert.assertArrayEquals(new LinearProbingMapEntry[1], myMapTwo.getTable());

        LinearProbingHashMap<Integer, String> myMapThree = new LinearProbingHashMap<>(4);
        Assert.assertEquals(0, myMapThree.size());
        Assert.assertArrayEquals(new LinearProbingMapEntry[4], myMapThree.getTable());

        LinearProbingHashMap<Integer, String> myMapFour = new LinearProbingHashMap<>(14);
        Assert.assertEquals(0, myMapFour.size());
        Assert.assertArrayEquals(new LinearProbingMapEntry[14], myMapFour.getTable());

        LinearProbingHashMap<Integer, String> myMapFive = new LinearProbingHashMap<>(40);
        Assert.assertEquals(0, myMapFive.size());
        Assert.assertArrayEquals(new LinearProbingMapEntry[40], myMapFive.getTable());
    }

    /**************************************************************************************
     Put
     ***********************************************************************************/
    @Test(expected = IllegalArgumentException.class)
    public void testPutNullKey() {
        try {
            map.put(null, "Moon");
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(0, map.size());
        }
        map.put(null, "Moon");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testPutNullValue() {
        try {
            map.put(0, null);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(0, map.size());
        }
        map.put(0, null);
    }

    @Test
    public void testPutAtZero() {
        Assert.assertNull(map.put(0, "Stars"));
        Assert.assertEquals(1, map.size());

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[0] = new LinearProbingMapEntry<Integer, String>(0, "Stars");
        Assert.assertArrayEquals(expected, map.getTable());
    }

    @Test
    public void testPutSame() {
        Integer myZero = new Integer(0);
        String myString = new String("Stars");
        Assert.assertNull(map.put(myZero, myString));
        Assert.assertEquals(1, map.size());

        Assert.assertSame(myZero, map.getTable()[0].getKey());
        Assert.assertSame(myString, map.getTable()[0].getValue());
    }

    @Test
    public void testPutAtIndex() {
        Assert.assertNull(map.put(7, "Sun"));
        Assert.assertEquals(1, map.size());

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[7] = new LinearProbingMapEntry<Integer, String>(7, "Sun");
        Assert.assertArrayEquals(expected, map.getTable());
    }

    @Test
    public void testPutAtMod() {
        Assert.assertNull(map.put(270, "Sun"));
        Assert.assertEquals(1, map.size());

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[10] = new LinearProbingMapEntry<Integer, String>(270, "Sun");
        Assert.assertArrayEquals(expected, map.getTable());
    }

    @Test
    public void testPutMultiple() {
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];

        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i, String.valueOf((char) (i + 65))));
            Assert.assertEquals(i + 1, map.size());
            expected[i] = new LinearProbingMapEntry<Integer, String>(i, String.valueOf((char) (i + 65)));
            Assert.assertArrayEquals(expected, map.getTable());
        }
        //Should build: [(0, A), (1, B), (2, C), (3, D), (4, E), (5, F), (6, G), null, null, ...]

        Assert.assertNull(map.put(12, String.valueOf((char) (12 + 65))));
        Assert.assertEquals(8, map.size());
        expected[12] = new LinearProbingMapEntry<Integer, String>(12, String.valueOf((char) (12 + 65)));
        Assert.assertArrayEquals(expected, map.getTable());
    }

    @Test
    public void testPutDuplicateValue() {
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];

        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i, "ABC"));
            Assert.assertEquals(i + 1, map.size());
            expected[i] = new LinearProbingMapEntry<Integer, String>(i, "ABC");
            Assert.assertArrayEquals(expected, map.getTable());
        }
        //Should build: [(0, ABC), (1, ABC), (2, ABC), (3, ABC), (4, ABC), (5, ABC), (6, ABC), null, null, ...]

        Assert.assertNull(map.put(12, "ABC"));
        Assert.assertEquals(8, map.size());
        expected[12] = new LinearProbingMapEntry<Integer, String>(12, "ABC");
        Assert.assertArrayEquals(expected, map.getTable());
    }

    @Test
    public void testPutDuplicateKey() {
        String myString = new String("Saturn");
        Assert.assertNull(map.put(3, myString));
        Assert.assertEquals(1, map.size());

        Assert.assertSame(myString, map.put(3, "Jupiter"));
        Assert.assertEquals(1, map.size());

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[3] = new LinearProbingMapEntry<Integer, String>(3, "Jupiter");
        Assert.assertArrayEquals(expected, map.getTable());
    }

    //Note, this test may not work if your remove method not functional
    @Test
    public void testPutDuplicateKeyRemoved() {
        //add key sameness?
        String myString = new String("Venus");
        Assert.assertNull(map.put(5, myString));
        Assert.assertEquals(1, map.size());

        map.remove(5);
        Assert.assertEquals(0, map.size());

        Assert.assertNull(map.put(5, "Mercury"));
        Assert.assertEquals(1, map.size());

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[5] = new LinearProbingMapEntry<Integer, String>(5, "Mercury");
        Assert.assertArrayEquals(expected, map.getTable());
    }

    @Test
    public void testPutSingleCollision() {
        Assert.assertNull(map.put(8, "Earth"));
        Assert.assertNull(map.put(21, "Mars"));
        Assert.assertEquals(2, map.size());

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[8] = new LinearProbingMapEntry<Integer, String>(8, "Earth");
        expected[9] = new LinearProbingMapEntry<Integer, String>(21, "Mars");
        Assert.assertArrayEquals(expected, map.getTable());
    }

    @Test
    public void testPutMultipleCollisions() {
        Assert.assertNull(map.put(10, "Comet"));
        Assert.assertNull(map.put(23, "Meteor"));
        Assert.assertNull(map.put(12, "Meteorite"));
        Assert.assertNull(map.put(25, "Meteoroid"));
        Assert.assertNull(map.put(14, "Asteroid"));
        Assert.assertNull(map.put(36, "COLLISION"));
        Assert.assertEquals(6, map.size());

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[10] = new LinearProbingMapEntry<Integer, String>(10, "Comet");
        expected[11] = new LinearProbingMapEntry<Integer, String>(23, "Meteor");
        expected[12] = new LinearProbingMapEntry<Integer, String>(12, "Meteorite");
        expected[0] = new LinearProbingMapEntry<Integer, String>(25, "Meteoroid");
        expected[1] = new LinearProbingMapEntry<Integer, String>(14, "Asteroid");
        expected[2] = new LinearProbingMapEntry<Integer, String>(36, "COLLISION");
        Assert.assertArrayEquals(expected, map.getTable());
    }

    //Note, this test may not work if your remove method not functional
    @Test
    public void testPutAtRemoved() {
        Assert.assertNull(map.put(2, "Galaxy"));
        Assert.assertNull(map.put(3, "Nebulae"));
        Assert.assertEquals(2, map.size());

        Assert.assertEquals("Nebulae", map.remove(3));
        Assert.assertEquals(1, map.size());

        Assert.assertNull(map.put(15, "Black Hole"));
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[2] = new LinearProbingMapEntry<Integer, String>(2, "Galaxy");
        expected[3] = new LinearProbingMapEntry<Integer, String>(15, "Black Hole");
        Assert.assertArrayEquals(expected, map.getTable());
        Assert.assertEquals(2, map.size());
    }

    //Note, this test may not work if your remove method not functional
    @Test
    public void testPutAtFirstRemoved() {
        Assert.assertNull(map.put(11, "Aquarius"));
        Assert.assertNull(map.put(12, "Pisces"));
        Assert.assertNull(map.put(13, "Aries"));
        Assert.assertNull(map.put(14, "Taurus"));
        Assert.assertEquals(4, map.size());

        Assert.assertEquals("Pisces", map.remove(12));
        Assert.assertEquals("Aries", map.remove(13));
        Assert.assertEquals("Taurus", map.remove(14));
        Assert.assertEquals(1, map.size());

        Assert.assertNull(map.put(24, "Gemini"));
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        expected[11] = new LinearProbingMapEntry<Integer, String>(11, "Aquarius");
        expected[12] = new LinearProbingMapEntry<Integer, String>(24, "Gemini");
        LinearProbingMapEntry<Integer, String> expEntry = new LinearProbingMapEntry<Integer, String>(13, "Aries");
        expEntry.setRemoved(true);
        expected[0] = expEntry;
        expEntry = new LinearProbingMapEntry<Integer, String>(14, "Taurus");
        expEntry.setRemoved(true);
        expected[1] = expEntry;
        Assert.assertArrayEquals(expected, map.getTable());
        Assert.assertEquals(2, map.size());
    }

    //Note, this test may not work if your remove method not functional
    @Test
    public void testPutDuplicateKeyAfterProbe() {
        Assert.assertNull(map.put(1, "Cancer"));
        Assert.assertNull(map.put(2, "Leo"));
        Assert.assertNull(map.put(3, "Virgo"));
        Assert.assertNull(map.put(14, "Libra"));
        Assert.assertEquals(4, map.size());

        Assert.assertEquals("Cancer", map.remove(1));
        Assert.assertEquals("Leo", map.remove(2));
        Assert.assertEquals("Virgo", map.remove(3));
        Assert.assertEquals(1, map.size());

        Assert.assertEquals("Libra", map.put(14, "Scorpio"));
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        LinearProbingMapEntry<Integer, String> expEntry = new LinearProbingMapEntry<Integer, String>(1, "Cancer");
        expEntry.setRemoved(true);
        expected[1] = expEntry;

        expEntry = new LinearProbingMapEntry<Integer, String>(2, "Leo");
        expEntry.setRemoved(true);
        expected[2] = expEntry;

        expEntry = new LinearProbingMapEntry<Integer, String>(3, "Virgo");
        expEntry.setRemoved(true);
        expected[3] = expEntry;

        expEntry = new LinearProbingMapEntry<Integer, String>(14, "Scorpio");
        expEntry.setRemoved(false);
        expected[4] = expEntry;

        Assert.assertArrayEquals(expected, map.getTable());
        Assert.assertEquals(1, map.size());
    }

    //Note, this test may not work if your remove method not functional
    @Test
    public void testPutDuplicateRemovedKeyAfterProbe() {
        Assert.assertNull(map.put(1, "Leo"));
        Assert.assertNull(map.put(2, "Virgo"));
        Assert.assertNull(map.put(3, "Libra"));
        Assert.assertNull(map.put(14, "Sagittarius"));
        Assert.assertEquals(4, map.size());

        Assert.assertEquals("Leo", map.remove(1));
        Assert.assertEquals("Virgo", map.remove(2));
        Assert.assertEquals("Libra", map.remove(3));
        Assert.assertEquals("Sagittarius", map.remove(14));
        Assert.assertEquals(0, map.size());

        Assert.assertNull(map.put(14, "Capricorn"));
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        LinearProbingMapEntry<Integer, String> expEntry = new LinearProbingMapEntry<Integer, String>(1, "Leo");
        expEntry.setRemoved(true);
        expected[1] = expEntry;

        expEntry = new LinearProbingMapEntry<Integer, String>(2, "Virgo");
        expEntry.setRemoved(true);
        expected[2] = expEntry;

        expEntry = new LinearProbingMapEntry<Integer, String>(3, "Libra");
        expEntry.setRemoved(true);
        expected[3] = expEntry;

        expEntry = new LinearProbingMapEntry<Integer, String>(14, "Sagittarius");
        expEntry.setRemoved(true);
        expected[4] = expEntry;

        expEntry = new LinearProbingMapEntry<Integer, String>(14, "Capricorn");
        expEntry.setRemoved(false);
        expected[1] = expEntry;
        Assert.assertArrayEquals(expected, map.getTable());
        Assert.assertEquals(1, map.size());
    }

    //note, this test may not work if your resize method is not functional
    @Test
    public void testPutSingleResize() {
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        Assert.assertNull(map.put(52, "A"));
        expected[0] = new LinearProbingMapEntry<Integer, String>(52, "A");
        Assert.assertNull(map.put(54, "B"));
        expected[2] = new LinearProbingMapEntry<Integer, String>(54, "B");
        Assert.assertNull(map.put(56, "C"));
        expected[4] = new LinearProbingMapEntry<Integer, String>(56, "C");
        Assert.assertNull(map.put(57, "D"));
        expected[5] = new LinearProbingMapEntry<Integer, String>(57, "D");
        Assert.assertNull(map.put(58, "E"));
        expected[6] = new LinearProbingMapEntry<Integer, String>(58, "E");
        Assert.assertNull(map.put(59, "F"));
        expected[7] = new LinearProbingMapEntry<Integer, String>(59, "F");
        Assert.assertNull(map.put(60, "G"));
        expected[8] = new LinearProbingMapEntry<Integer, String>(60, "G");
        Assert.assertNull(map.put(61, "H"));
        expected[9] = new LinearProbingMapEntry<Integer, String>(61, "H");

        Assert.assertEquals(8, map.size());
        Assert.assertArrayEquals(expected, map.getTable());


        Assert.assertNull(map.put(63, "I"));

        expected = new LinearProbingMapEntry[2 * LinearProbingHashMap.INITIAL_CAPACITY + 1];
        expected[25] = new LinearProbingMapEntry<Integer, String>(52, "A");
        expected[0] = new LinearProbingMapEntry<Integer, String>(54, "B");
        expected[2] = new LinearProbingMapEntry<Integer, String>(56, "C");
        expected[3] = new LinearProbingMapEntry<Integer, String>(57, "D");
        expected[4] = new LinearProbingMapEntry<Integer, String>(58, "E");
        expected[5] = new LinearProbingMapEntry<Integer, String>(59, "F");
        expected[6] = new LinearProbingMapEntry<Integer, String>(60, "G");
        expected[7] = new LinearProbingMapEntry<Integer, String>(61, "H");
        expected[9] = new LinearProbingMapEntry<Integer, String>(63, "I");
        Assert.assertArrayEquals(expected, map.getTable());
        Assert.assertEquals(9, map.size());
    }

    //Note, this test may not work if your remove + resize methods are not functional
    @Test
    public void testPutResizeWithRemoved() {
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY];
        Assert.assertNull(map.put(52, "A"));
        expected[0] = new LinearProbingMapEntry<Integer, String>(52, "A");
        Assert.assertNull(map.put(54, "B"));
        expected[2] = new LinearProbingMapEntry<Integer, String>(54, "B");
        Assert.assertNull(map.put(56, "C"));
        expected[4] = new LinearProbingMapEntry<Integer, String>(56, "C");
        Assert.assertNull(map.put(57, "D"));
        expected[5] = new LinearProbingMapEntry<Integer, String>(57, "D");
        Assert.assertNull(map.put(58, "E"));
        expected[6] = new LinearProbingMapEntry<Integer, String>(58, "E");
        Assert.assertNull(map.put(59, "F"));
        expected[7] = new LinearProbingMapEntry<Integer, String>(59, "F");
        Assert.assertNull(map.put(60, "G"));
        expected[8] = new LinearProbingMapEntry<Integer, String>(60, "G");
        Assert.assertNull(map.put(61, "H"));
        expected[9] = new LinearProbingMapEntry<Integer, String>(61, "H");

        Assert.assertEquals("E", map.remove(58));
        expected[6].setRemoved(true);
        Assert.assertEquals("F", map.remove(59));
        expected[7].setRemoved(true);

        Assert.assertNull(map.put(63, "I"));
        expected[11] = new LinearProbingMapEntry<Integer, String>(63, "I");
        Assert.assertNull(map.put(64, "J"));
        expected[12] = new LinearProbingMapEntry<Integer, String>(64, "J");


        Assert.assertEquals(8, map.size());
        Assert.assertArrayEquals(expected, map.getTable());


        Assert.assertNull(map.put(62, "K"));

        expected = new LinearProbingMapEntry[2 * LinearProbingHashMap.INITIAL_CAPACITY + 1];
        expected[25] = new LinearProbingMapEntry<Integer, String>(52, "A");
        expected[0] = new LinearProbingMapEntry<Integer, String>(54, "B");
        expected[2] = new LinearProbingMapEntry<Integer, String>(56, "C");
        expected[3] = new LinearProbingMapEntry<Integer, String>(57, "D");
        expected[6] = new LinearProbingMapEntry<Integer, String>(60, "G");
        expected[7] = new LinearProbingMapEntry<Integer, String>(61, "H");
        expected[8] = new LinearProbingMapEntry<Integer, String>(62, "K");
        expected[9] = new LinearProbingMapEntry<Integer, String>(63, "I");
        expected[10] = new LinearProbingMapEntry<Integer, String>(64, "J");
        Assert.assertArrayEquals(expected, map.getTable());
        Assert.assertEquals(9, map.size());
    }

    //note, this test may not work if your resize method is not functional
    @Test
    public void testPutDoubleResize() {
        Assert.assertNull(map.put(52, "A"));
        Assert.assertNull(map.put(54, "B"));
        Assert.assertNull(map.put(56, "C"));
        Assert.assertNull(map.put(57, "D"));
        Assert.assertNull(map.put(58, "E"));
        Assert.assertNull(map.put(59, "F"));
        Assert.assertNull(map.put(60, "G"));
        Assert.assertNull(map.put(61, "H"));
        Assert.assertNull(map.put(63, "I"));
        Assert.assertNull(map.put(64, "J"));
        Assert.assertNull(map.put(65, "K"));
        Assert.assertNull(map.put(66, "L"));
        Assert.assertNull(map.put(68, "M"));
        Assert.assertNull(map.put(69, "N"));
        Assert.assertNull(map.put(70, "O"));
        Assert.assertNull(map.put(71, "P"));
        Assert.assertNull(map.put(72, "Q"));
        Assert.assertNull(map.put(74, "R"));

        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[2 * LinearProbingHashMap.INITIAL_CAPACITY + 1];
        expected[25] = new LinearProbingMapEntry<Integer, String>(52, "A");
        expected[0] = new LinearProbingMapEntry<Integer, String>(54, "B");
        expected[2] = new LinearProbingMapEntry<Integer, String>(56, "C");
        expected[3] = new LinearProbingMapEntry<Integer, String>(57, "D");
        expected[4] = new LinearProbingMapEntry<Integer, String>(58, "E");
        expected[5] = new LinearProbingMapEntry<Integer, String>(59, "F");
        expected[6] = new LinearProbingMapEntry<Integer, String>(60, "G");
        expected[7] = new LinearProbingMapEntry<Integer, String>(61, "H");
        expected[9] = new LinearProbingMapEntry<Integer, String>(63, "I");
        expected[10] = new LinearProbingMapEntry<Integer, String>(64, "J");
        expected[11] = new LinearProbingMapEntry<Integer, String>(65, "K");
        expected[12] = new LinearProbingMapEntry<Integer, String>(66, "L");
        expected[14] = new LinearProbingMapEntry<Integer, String>(68, "M");
        expected[15] = new LinearProbingMapEntry<Integer, String>(69, "N");
        expected[16] = new LinearProbingMapEntry<Integer, String>(70, "O");
        expected[17] = new LinearProbingMapEntry<Integer, String>(71, "P");
        expected[18] = new LinearProbingMapEntry<Integer, String>(72, "Q");
        expected[20] = new LinearProbingMapEntry<Integer, String>(74, "R");
        Assert.assertEquals(18, map.size());
        Assert.assertArrayEquals(expected, map.getTable());

        Assert.assertNull(map.put(75, "S"));

        expected = new LinearProbingMapEntry[2 * (2 * LinearProbingHashMap.INITIAL_CAPACITY + 1) + 1];
        expected[52] = new LinearProbingMapEntry<Integer, String>(52, "A");
        expected[54] = new LinearProbingMapEntry<Integer, String>(54, "B");
        expected[1] = new LinearProbingMapEntry<Integer, String>(56, "C");
        expected[2] = new LinearProbingMapEntry<Integer, String>(57, "D");
        expected[3] = new LinearProbingMapEntry<Integer, String>(58, "E");
        expected[4] = new LinearProbingMapEntry<Integer, String>(59, "F");
        expected[5] = new LinearProbingMapEntry<Integer, String>(60, "G");
        expected[6] = new LinearProbingMapEntry<Integer, String>(61, "H");
        expected[8] = new LinearProbingMapEntry<Integer, String>(63, "I");
        expected[9] = new LinearProbingMapEntry<Integer, String>(64, "J");
        expected[10] = new LinearProbingMapEntry<Integer, String>(65, "K");
        expected[11] = new LinearProbingMapEntry<Integer, String>(66, "L");
        expected[13] = new LinearProbingMapEntry<Integer, String>(68, "M");
        expected[14] = new LinearProbingMapEntry<Integer, String>(69, "N");
        expected[15] = new LinearProbingMapEntry<Integer, String>(70, "O");
        expected[16] = new LinearProbingMapEntry<Integer, String>(71, "P");
        expected[17] = new LinearProbingMapEntry<Integer, String>(72, "Q");
        expected[19] = new LinearProbingMapEntry<Integer, String>(74, "R");
        expected[20] = new LinearProbingMapEntry<Integer, String>(75, "S");
        Assert.assertEquals(19, map.size());
        Assert.assertArrayEquals(expected, map.getTable());
    }

    //note, this test may not work if your remove + resize methods are not functional
    @Test
    public void testPutUnexpectedSize() {
        map.resizeBackingTable(3);
        LinearProbingMapEntry<Integer, String>[] expected = new LinearProbingMapEntry[3];
        Assert.assertArrayEquals(expected, map.getTable());

        Assert.assertNull(map.put(12, "Andromeda"));
        expected[0] = new LinearProbingMapEntry<>(12, "Andromeda");
        Assert.assertEquals(1, map.size());
        Assert.assertArrayEquals(expected, map.getTable());

        Assert.assertNull(map.put(13, "Antila"));
        expected[1] = new LinearProbingMapEntry<>(13, "Antila");
        Assert.assertEquals(2, map.size());
        Assert.assertArrayEquals(expected, map.getTable());

        Assert.assertEquals("Antila", map.remove(13));
        expected[1].setRemoved(true);
        Assert.assertEquals(1, map.size());
        Assert.assertArrayEquals(expected, map.getTable());

        Assert.assertNull(map.put(24, "Apus"));
        expected[1] = new LinearProbingMapEntry<>(24, "Apus");
        Assert.assertEquals(2, map.size());
        Assert.assertArrayEquals(expected, map.getTable());

        Assert.assertNull(map.put(14, "Ara"));
        expected = new LinearProbingMapEntry[7];
        expected[0] = new LinearProbingMapEntry<>(14, "Ara");
        expected[3] = new LinearProbingMapEntry<>(24, "Apus");
        expected[5] = new LinearProbingMapEntry<>(12, "Andromeda");
        Assert.assertEquals(3, map.size());
        Assert.assertArrayEquals(expected, map.getTable());

        Assert.assertNull(map.put(15, "Cygnus"));
        expected[1] = new LinearProbingMapEntry<>(15, "Cygnus");
        Assert.assertEquals(4, map.size());
        Assert.assertArrayEquals(expected, map.getTable());

        Assert.assertEquals("Cygnus", map.remove(15));
        expected[1].setRemoved(true);
        Assert.assertEquals(3, map.size());
        Assert.assertArrayEquals(expected, map.getTable());

        Assert.assertNull(map.put(16, "Draco"));
        expected[2] = new LinearProbingMapEntry<>(16, "Draco");
        Assert.assertEquals(4, map.size());
        Assert.assertArrayEquals(expected, map.getTable());

        Assert.assertEquals("Draco", map.remove(16));
        expected[2].setRemoved(true);
        Assert.assertEquals(3, map.size());
        Assert.assertArrayEquals(expected, map.getTable());

        Assert.assertNull(map.put(22, "Equuleus"));
        expected[1] = new  LinearProbingMapEntry<>(22, "Equuleus");
        Assert.assertEquals(4, map.size());
        Assert.assertArrayEquals(expected, map.getTable());

        Assert.assertEquals("Andromeda", map.remove(12));
        expected[5].setRemoved(true);
        Assert.assertEquals(3, map.size());
        Assert.assertArrayEquals(expected, map.getTable());

        Assert.assertEquals("Apus", map.put(24, "Fornax"));
        expected[3].setValue("Fornax");
        Assert.assertEquals(3, map.size());
        Assert.assertArrayEquals(expected, map.getTable());

        Assert.assertEquals("Fornax", map.remove(24));
        expected[3].setRemoved(true);
        Assert.assertEquals(2, map.size());
        Assert.assertArrayEquals(expected, map.getTable());

        Assert.assertNull(map.put(24, "Grus"));
        expected[3].setValue("Grus");
        expected[3].setRemoved(false);
        Assert.assertEquals(3, map.size());
        Assert.assertArrayEquals(expected, map.getTable());

        Assert.assertNull(map.put(29, "Hercules"));
        Assert.assertNull(map.put(12, "Indus"));

        expected = new LinearProbingMapEntry[15];
        expected[12] = new LinearProbingMapEntry<>(12, "Indus");
        expected[14] = new LinearProbingMapEntry<>(14, "Ara");
        expected[7] = new  LinearProbingMapEntry<>(22, "Equuleus");
        expected[9] = new LinearProbingMapEntry<>(24, "Grus");
        expected[0] = new LinearProbingMapEntry<>(29, "Hercules");
        Assert.assertEquals(5, map.size());
        Assert.assertArrayEquals(expected, map.getTable());
    }

    /**************************************************************************************
     Remove
     ***********************************************************************************/
    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNull() {
        try {
            map.remove(null);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(0, map.size());
        }
        map.remove(null);
    }

    //this test may not work if your put method is not functional
    @Test
    public void testRemoveAtZero() {
        map.put(0, "A");
        Assert.assertEquals("A", map.remove(0));
        Assert.assertEquals(0, map.size());
    }

    //this test may not work if your put method is not functional
    @Test
    public void testRemoveSame() {
        String myString = new String("I AM OUT OF IDEAS");
        map.put(0, myString);
        Assert.assertSame(myString, map.remove(0));
        Assert.assertEquals(0, map.size());
    }

    //this test may not work if your put method is not functional
    @Test
    public void testRemoveAtIndex() {
        String myString = new String("Ummmmm........");
        map.put(9, myString);
        Assert.assertSame(myString, map.remove(9));
        Assert.assertEquals(0, map.size());
    }

    //this test may not work if your put method is not functional
    @Test
    public void testRemoveMod() {
        String myString = new String("i know words");
        map.put(200, myString);
        Assert.assertSame(myString, map.remove(200));
        Assert.assertEquals(0, map.size());
    }

    //this test may not work if your put method is not functional
    @Test
    public void testRemoveMultiple() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i, String.valueOf((char) (i + 65))));
        }

        for (int i = 0; i < 7; i++) {
            Assert.assertEquals(String.valueOf((char) (i + 65)), map.remove(i));
            Assert.assertEquals(6 - i, map.size());
        }
    }

    //this test may not work if your put method is not functional
    @Test
    public void testRemoveCollided() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i * 13, String.valueOf((char) (i + 65))));
        }

        for (int i = 0; i < 7; i++) {
            Assert.assertEquals(String.valueOf((char) (i + 65)), map.remove(i * 13));
            Assert.assertEquals(6 - i, map.size());
        }
    }

    //this test may not work if your put method is not functional
    @Test
    public void testRemoveCollidedReversed() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i * 13, String.valueOf((char) (i + 65))));
        }

        for (int i = 6; i >= 0; i--) {
            Assert.assertEquals(String.valueOf((char) (i + 65)), map.remove(i * 13));
            Assert.assertEquals(i, map.size());
        }
    }

    //this test may not work if your put method is not functional
    @Test
    public void testRemoveCollidedUnordered() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i * 13, String.valueOf((char) (i + 65))));
        }

        int[] myOrder = {3, 5, 2, 4, 6, 1, 0};
        int size = 7;
        for (int i : myOrder) {
            Assert.assertEquals(String.valueOf((char) (i + 65)), map.remove(i * 13));
            Assert.assertEquals(--size, map.size());
        }
    }

    //this test may not work if your put method is not functional
    @Test(expected = NoSuchElementException.class)
    public void testRemoveNotInTable() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i, String.valueOf((char) (i + 65))));
        }

        try {
            map.remove(1500);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(7, map.size());
        }
        map.remove(1500);
    }

    //this test may not work if your put method is not functional
    @Test(expected = NoSuchElementException.class)
    public void testRemoveNotInTableDeleted() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i, String.valueOf((char) (i + 65))));
        }

        Assert.assertEquals(String.valueOf((char) 65), map.remove(0));
        try {
            map.remove(0);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(6, map.size());
        }
        map.remove(0);
    }

    //this test may not work if your put method is not functional
    @Test(expected = NoSuchElementException.class)
    public void testRemoveNotInTableCollided() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i * 13, String.valueOf((char) (i + 65))));
        }

        Assert.assertEquals(String.valueOf((char) 71), map.remove(78));

        try {
            map.remove(78);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(6, map.size());
        }
        map.remove(78);
    }

    //this test may not work if your put method is not functional
    @Test(expected = NoSuchElementException.class)
    public void testRemoveNotInTableCollidedDeleted() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i * 13, String.valueOf((char) (i + 56))));
        }

        for (int i = 0; i < 7; i++) {
            Assert.assertEquals(String.valueOf((char) (i + 56)), map.remove(i * 13));
            Assert.assertEquals(6 - i, map.size());
        }

        try {
            map.remove(78);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(0, map.size());
        }
        map.remove(78);
    }

    //this test may not work if your put and resize methods are not functional
    @Test
    public void testRemoveResize() {
        for (int i = 0; i < 10; i++) {
            Assert.assertNull(map.put(i * 13, String.valueOf((char) (i + 56))));
        }

        for (int i = 9; i >= 0; i--) {
            Assert.assertEquals(String.valueOf((char) (i + 56)), map.remove(i * 13));
            Assert.assertEquals(i, map.size());
        }
    }

    //this test may not work if your put and resize methods are not functional
    @Test
    public void testRemoveUnexpected() {
        map.resizeBackingTable(3);
        map.put(27, "Test1");
        map.put(29, "Test2");
        Assert.assertEquals("Test1", map.remove(27));
        Assert.assertEquals(1, map.size());
        Assert.assertEquals("Test2", map.remove(29));
        Assert.assertEquals(0, map.size());

        map.put(21, "Test3");
        map.put(28, "Test4");
        map.put(35, "Test5");
        map.put(25, "Test6");
        map.put(26, "Test7");
        Assert.assertEquals("Test3", map.remove(21));
        Assert.assertEquals(4, map.size());
        Assert.assertEquals("Test4", map.remove(28));
        Assert.assertEquals(3, map.size());
        Assert.assertEquals("Test5", map.remove(35));
        Assert.assertEquals(2, map.size());
        Assert.assertEquals("Test6", map.remove(25));
        Assert.assertEquals(1, map.size());
        Assert.assertEquals("Test7", map.remove(26));
        Assert.assertEquals(0, map.size());

        map.put(30, "Test8");
        map.put(31, "Test9");
        map.put(47, "Test10");
        map.put(48, "Test11");
        map.put(49, "Test12");
        map.put(35, "Test13");
        map.put(36, "Test14");
        map.put(38, "Test15");
        map.put(55, "Test16");
        map.put(42, "Test17");

        Assert.assertEquals("Test11", map.remove(48));
        Assert.assertEquals(9, map.size());
        Assert.assertEquals("Test8", map.remove(30));
        Assert.assertEquals(8, map.size());
        Assert.assertEquals("Test14", map.remove(36));
        Assert.assertEquals(7, map.size());
        Assert.assertEquals("Test9", map.remove(31));
        Assert.assertEquals(6, map.size());
        Assert.assertEquals("Test15", map.remove(38));
        Assert.assertEquals(5, map.size());
        Assert.assertEquals("Test12", map.remove(49));
        Assert.assertEquals(4, map.size());
        Assert.assertEquals("Test13", map.remove(35));
        Assert.assertEquals(3, map.size());
        Assert.assertEquals("Test16", map.remove(55));
        Assert.assertEquals(2, map.size());
        Assert.assertEquals("Test17", map.remove(42));
        Assert.assertEquals(1, map.size());
        Assert.assertEquals("Test10", map.remove(47));
        Assert.assertEquals(0, map.size());
    }

    /**************************************************************************************
     Get (tests may depend on add, remove, and resize)
     ***********************************************************************************/
    @Test(expected = IllegalArgumentException.class)
    public void testGetNull() {
        map.get(null);
    }

    @Test
    public void testGetAtZero() {
        map.put(0, "A");
        Assert.assertEquals("A", map.get(0));
        Assert.assertEquals(1, map.size());
    }

    @Test
    public void testGetSame() {
        String myString = new String("I AM OUT OF IDEAS");
        map.put(0, myString);
        Assert.assertSame(myString, map.get(0));
        Assert.assertEquals(1, map.size());
    }

    @Test
    public void testGetAtIndex() {
        String myString = new String("Ummmmm........");
        map.put(9, myString);
        Assert.assertSame(myString, map.get(9));
        Assert.assertEquals(1, map.size());
    }

    @Test
    public void testGetMod() {
        String myString = new String("i know words");
        map.put(200, myString);
        Assert.assertSame(myString, map.get(200));
        Assert.assertEquals(1, map.size());
    }

    @Test
    public void testGetMultiple() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i, String.valueOf((char) (i + 65))));
        }

        for (int i = 0; i < 7; i++) {
            Assert.assertEquals(String.valueOf((char) (i + 65)), map.get(i));
            Assert.assertEquals(String.valueOf((char) (i + 65)), map.remove(i));
            Assert.assertEquals(6 - i, map.size());
        }
    }

    @Test
    public void testGetCollided() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i * 13, String.valueOf((char) (i + 65))));
        }

        for (int i = 0; i < 7; i++) {
            Assert.assertEquals(String.valueOf((char) (i + 65)), map.get(i * 13));
            Assert.assertEquals(String.valueOf((char) (i + 65)), map.remove(i * 13));
            Assert.assertEquals(6 - i, map.size());
        }
    }

    @Test
    public void testGetCollidedReversed() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i * 13, String.valueOf((char) (i + 65))));
        }

        for (int i = 6; i >= 0; i--) {
            Assert.assertEquals(String.valueOf((char) (i + 65)), map.get(i * 13));
            Assert.assertEquals(String.valueOf((char) (i + 65)), map.remove(i * 13));
            Assert.assertEquals(i, map.size());
        }
    }

    @Test
    public void testGetCollidedUnordered() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i * 13, String.valueOf((char) (i + 65))));
        }

        int[] myOrder = {3, 5, 2, 4, 6, 1, 0};
        int size = 7;
        for (int i : myOrder) {
            Assert.assertEquals(String.valueOf((char) (i + 65)), map.get(i * 13));
            Assert.assertEquals(String.valueOf((char) (i + 65)), map.remove(i * 13));
            Assert.assertEquals(--size, map.size());
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetNotInTable() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i, String.valueOf((char) (i + 65))));
        }

        try {
            map.get(1500);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(7, map.size());
        }
        map.get(1500);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetNotInTableDeleted() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i, String.valueOf((char) (i + 65))));
        }

        Assert.assertEquals(String.valueOf((char) 65), map.remove(0));
        try {
            map.get(0);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(6, map.size());
        }
        map.get(0);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetNotInTableCollided() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i * 13, String.valueOf((char) (i + 65))));
        }

        Assert.assertEquals(String.valueOf((char) 71), map.remove(78));

        try {
            map.get(78);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(6, map.size());
        }
        map.get(78);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetNotInTableCollidedDeleted() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i * 13, String.valueOf((char) (i + 56))));
        }

        for (int i = 0; i < 7; i++) {
            Assert.assertEquals(String.valueOf((char) (i + 56)), map.remove(i * 13));
            Assert.assertEquals(6 - i, map.size());
        }

        try {
            map.get(78);
        } catch (IllegalArgumentException e) {
            Assert.assertEquals(0, map.size());
        }
        map.get(78);
    }

    @Test
    public void testGetResize() {
        for (int i = 0; i < 10; i++) {
            Assert.assertNull(map.put(i * 13, String.valueOf((char) (i + 56))));
        }

        for (int i = 9; i >= 0; i--) {
            Assert.assertEquals(String.valueOf((char) (i + 56)), map.get(i * 13));
            Assert.assertEquals(String.valueOf((char) (i + 56)), map.remove(i * 13));
            Assert.assertEquals(i, map.size());
        }
    }

    @Test
    public void testGetUnexpected() {
        map.resizeBackingTable(3);
        map.put(27, "Test1");
        map.put(29, "Test2");
        Assert.assertEquals("Test1", map.get(27));
        Assert.assertEquals("Test1", map.remove(27));
        Assert.assertEquals(1, map.size());
        Assert.assertEquals("Test2", map.get(29));
        Assert.assertEquals("Test2", map.remove(29));
        Assert.assertEquals(0, map.size());

        map.put(21, "Test3");
        map.put(28, "Test4");
        map.put(35, "Test5");
        map.put(25, "Test6");
        map.put(26, "Test7");
        Assert.assertEquals("Test3", map.get(21));
        Assert.assertEquals("Test3", map.remove(21));
        Assert.assertEquals(4, map.size());
        Assert.assertEquals("Test4", map.get(28));
        Assert.assertEquals("Test4", map.remove(28));
        Assert.assertEquals(3, map.size());
        Assert.assertEquals("Test5", map.get(35));
        Assert.assertEquals("Test5", map.remove(35));
        Assert.assertEquals(2, map.size());
        Assert.assertEquals("Test6", map.get(25));
        Assert.assertEquals("Test6", map.remove(25));
        Assert.assertEquals(1, map.size());
        Assert.assertEquals("Test7", map.get(26));
        Assert.assertEquals("Test7", map.remove(26));
        Assert.assertEquals(0, map.size());

        map.put(30, "Test8");
        map.put(31, "Test9");
        map.put(47, "Test10");
        map.put(48, "Test11");
        map.put(49, "Test12");
        map.put(35, "Test13");
        map.put(36, "Test14");
        map.put(38, "Test15");
        map.put(55, "Test16");
        map.put(42, "Test17");

        Assert.assertEquals("Test11", map.get(48));
        Assert.assertEquals("Test11", map.remove(48));
        Assert.assertEquals(9, map.size());
        Assert.assertEquals("Test8", map.get(30));
        Assert.assertEquals("Test8", map.remove(30));
        Assert.assertEquals(8, map.size());
        Assert.assertEquals("Test14", map.get(36));
        Assert.assertEquals("Test14", map.remove(36));
        Assert.assertEquals(7, map.size());
        Assert.assertEquals("Test9", map.get(31));
        Assert.assertEquals("Test9", map.remove(31));
        Assert.assertEquals(6, map.size());
        Assert.assertEquals("Test15", map.get(38));
        Assert.assertEquals("Test15", map.remove(38));
        Assert.assertEquals(5, map.size());
        Assert.assertEquals("Test12", map.get(49));
        Assert.assertEquals("Test12", map.remove(49));
        Assert.assertEquals(4, map.size());
        Assert.assertEquals("Test13", map.get(35));
        Assert.assertEquals("Test13", map.remove(35));
        Assert.assertEquals(3, map.size());
        Assert.assertEquals("Test16", map.get(55));
        Assert.assertEquals("Test16", map.remove(55));
        Assert.assertEquals(2, map.size());
        Assert.assertEquals("Test17", map.get(42));
        Assert.assertEquals("Test17", map.remove(42));
        Assert.assertEquals(1, map.size());
        Assert.assertEquals("Test10", map.get(47));
        Assert.assertEquals("Test10", map.remove(47));
        Assert.assertEquals(0, map.size());
    }

    /**************************************************************************************
     Contains (tests may depend on add, remove, and resize)
     ***********************************************************************************/
    @Test(expected = IllegalArgumentException.class)
    public void testContainsNull() {
        map.containsKey(null);
    }

    @Test
    public void testContainsAtZero() {
        map.put(0, "A");
        Assert.assertTrue(map.containsKey(0));

        map.remove(0);
        Assert.assertFalse(map.containsKey(0));
    }

    @Test
    public void testContainstAtIndex() {
        String myString = new String("Ummmmm........");
        map.put(9, myString);
        Assert.assertTrue(map.containsKey(9));

        map.remove(9);
        Assert.assertFalse(map.containsKey(9));
    }

    @Test
    public void testContainsMod() {
        String myString = new String("i know words");
        map.put(200, myString);
        Assert.assertTrue(map.containsKey(200));

        map.remove(200);
        Assert.assertFalse(map.containsKey(200));
    }

    @Test
    public void testContainsMultiple() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i, String.valueOf((char) (i + 65))));
        }

        for (int i = 0; i < 7; i++) {
            Assert.assertTrue(map.containsKey(i));
            Assert.assertEquals(String.valueOf((char) (i + 65)), map.remove(i));
            Assert.assertFalse(map.containsKey(i));
        }
    }

    @Test
    public void testContainsCollided() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i * 13, String.valueOf((char) (i + 65))));
        }

        for (int i = 0; i < 7; i++) {
            Assert.assertTrue(map.containsKey(i * 13));
            Assert.assertEquals(String.valueOf((char) (i + 65)), map.remove(i * 13));
            Assert.assertFalse(map.containsKey(i * 13));
        }
    }

    @Test
    public void testContainsCollidedReversed() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i * 13, String.valueOf((char) (i + 65))));
        }

        for (int i = 6; i >= 0; i--) {
            Assert.assertTrue(map.containsKey(i * 13));
            Assert.assertEquals(String.valueOf((char) (i + 65)), map.remove(i * 13));
            Assert.assertFalse(map.containsKey(i * 13));
        }
    }

    @Test
    public void testContainsCollidedUnordered() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i * 13, String.valueOf((char) (i + 65))));
        }

        int[] myOrder = {3, 5, 2, 4, 6, 1, 0};
        for (int i : myOrder) {
            Assert.assertTrue(map.containsKey(i * 13));
            Assert.assertEquals(String.valueOf((char) (i + 65)), map.remove(i * 13));
            Assert.assertFalse(map.containsKey(i * 13));
        }
    }

    @Test
    public void testContainsNotInTable() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i, String.valueOf((char) (i + 65))));
        }

        Assert.assertFalse(map.containsKey(1500));
    }

    @Test
    public void testContainsNotInTableDeleted() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i, String.valueOf((char) (i + 65))));
        }

        Assert.assertTrue(map.containsKey(0));
        Assert.assertEquals(String.valueOf((char) 65), map.remove(0));
        Assert.assertFalse(map.containsKey(0));
    }

    @Test
    public void testContainsNotInTableCollided() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i * 13, String.valueOf((char) (i + 65))));
        }

        Assert.assertTrue(map.containsKey(78));
        Assert.assertEquals(String.valueOf((char) 71), map.remove(78));
        Assert.assertFalse(map.containsKey(78));
    }

    @Test
    public void testContainsNotInTableCollidedDeleted() {
        for (int i = 0; i < 7; i++) {
            Assert.assertNull(map.put(i * 13, String.valueOf((char) (i + 56))));
        }

        for (int i = 0; i < 7; i++) {
            Assert.assertTrue(map.containsKey(i * 13));
            Assert.assertEquals(String.valueOf((char) (i + 56)), map.remove(i * 13));
            Assert.assertFalse(map.containsKey(i * 13));
        }
    }

    @Test
    public void testContainsResize() {
        for (int i = 0; i < 10; i++) {
            Assert.assertNull(map.put(i * 13, String.valueOf((char) (i + 56))));
        }

        for (int i = 9; i >= 0; i--) {
            Assert.assertTrue(map.containsKey(i * 13));
            Assert.assertEquals(String.valueOf((char) (i + 56)), map.remove(i * 13));
            Assert.assertFalse(map.containsKey(i * 13));
        }
    }

    @Test
    public void testContainsUnexpected() {
        map.resizeBackingTable(3);
        map.put(27, "Test1");
        map.put(29, "Test2");
        Assert.assertTrue(map.containsKey(27));
        Assert.assertEquals("Test1", map.remove(27));
        Assert.assertFalse(map.containsKey(27));
        Assert.assertTrue(map.containsKey(29));
        Assert.assertEquals("Test2", map.remove(29));
        Assert.assertFalse(map.containsKey(29));

        map.put(21, "Test3");
        map.put(28, "Test4");
        map.put(35, "Test5");
        map.put(25, "Test6");
        map.put(26, "Test7");
        Assert.assertTrue(map.containsKey(21));
        Assert.assertEquals("Test3", map.remove(21));
        Assert.assertFalse(map.containsKey(21));
        Assert.assertTrue(map.containsKey(28));
        Assert.assertEquals("Test4", map.remove(28));
        Assert.assertFalse(map.containsKey(28));
        Assert.assertTrue(map.containsKey(35));
        Assert.assertEquals("Test5", map.remove(35));
        Assert.assertFalse(map.containsKey(35));
        Assert.assertTrue(map.containsKey(25));
        Assert.assertEquals("Test6", map.remove(25));
        Assert.assertFalse(map.containsKey(25));
        Assert.assertTrue(map.containsKey(26));
        Assert.assertEquals("Test7", map.remove(26));
        Assert.assertFalse(map.containsKey(26));

        map.put(30, "Test8");
        map.put(31, "Test9");
        map.put(47, "Test10");
        map.put(48, "Test11");
        map.put(49, "Test12");
        map.put(35, "Test13");
        map.put(36, "Test14");
        map.put(38, "Test15");
        map.put(55, "Test16");
        map.put(42, "Test17");

        Assert.assertTrue(map.containsKey(48));
        Assert.assertEquals("Test11", map.remove(48));
        Assert.assertFalse(map.containsKey(48));
        Assert.assertTrue(map.containsKey(30));
        Assert.assertEquals("Test8", map.remove(30));
        Assert.assertFalse(map.containsKey(30));
        Assert.assertTrue(map.containsKey(36));
        Assert.assertEquals("Test14", map.remove(36));
        Assert.assertFalse(map.containsKey(36));
        Assert.assertTrue(map.containsKey(31));
        Assert.assertEquals("Test9", map.remove(31));
        Assert.assertFalse(map.containsKey(31));
        Assert.assertTrue(map.containsKey(38));
        Assert.assertEquals("Test15", map.remove(38));
        Assert.assertFalse(map.containsKey(38));
        Assert.assertTrue(map.containsKey(49));
        Assert.assertEquals("Test12", map.remove(49));
        Assert.assertFalse(map.containsKey(49));
        Assert.assertTrue(map.containsKey(35));
        Assert.assertEquals("Test13", map.remove(35));
        Assert.assertFalse(map.containsKey(35));
        Assert.assertTrue(map.containsKey(55));
        Assert.assertEquals("Test16", map.remove(55));
        Assert.assertFalse(map.containsKey(55));
        Assert.assertTrue(map.containsKey(42));
        Assert.assertEquals("Test17", map.remove(42));
        Assert.assertFalse(map.containsKey(42));
        Assert.assertTrue(map.containsKey(47));
        Assert.assertEquals("Test10", map.remove(47));
        Assert.assertFalse(map.containsKey(47));
    }

    /**************************************************************************************
     keySet (tests may depend on add, remove, and resize)
     ***********************************************************************************/
    @Test
    public void testKeySetEmpty() {
        Assert.assertEquals(new HashSet<Integer>(0), map.keySet());

    }

    @Test
    public void testKeySetSimple() {
        HashSet<Integer> expected = new HashSet<Integer>(7);

        for (int i = 0; i < 7; i++) {
            map.put(i, String.valueOf((char) (i + 65)));
            expected.add(i);
            Assert.assertEquals(expected, map.keySet());
        }
    }

    @Test
    public void testKeySetDeleted() {
        HashSet<Integer> expected;

        for (int i = 0; i < 7; i++) {
            map.put(i, String.valueOf((char) (i + 65)));
        }

        for (int i = 0; i < 7; i++) {
            expected = new HashSet<>(7 - i);
            for (int j = i; j < 7; j++) {
                expected.add(j);
            }
            Assert.assertEquals(expected, map.keySet());
            map.remove(i);
        }
    }

    @Test
    public void testKeySetResized() {
        HashSet<Integer> expected = new HashSet<Integer>(10);

        for (int i = 0; i < 10; i++) {
            map.put(i, String.valueOf((char) (i + 65)));
            expected.add(i);
            Assert.assertEquals(expected, map.keySet());
        }
    }

    @Test
    public void testKeySetResizedDeleted() {
        HashSet<Integer> expected;

        for (int i = 0; i < 10; i++) {
            map.put(i, String.valueOf((char) (i + 65)));
        }

        for (int i = 0; i < 10; i++) {
            expected = new HashSet<>(10 - i);
            for (int j = i; j < 10; j++) {
                expected.add(j);
            }
            Assert.assertEquals(expected, map.keySet());
            map.remove(i);
        }
    }

    @Test
    public void testKeySetOffSet() {
        HashSet<Integer> expected;

        for (int i = 200; i < 210; i++) {
            map.put(i, String.valueOf((char) (i - 200 + 65)));
        }

        for (int i = 200; i < 210; i++) {
            expected = new HashSet<>(10 - i + 200);
            for (int j = i; j < 210; j++) {
                expected.add(j);
            }
            Assert.assertEquals(expected, map.keySet());
            map.remove(i);
        }
    }

    @Test
    public void testKeySetUnexpected() {
        map.resizeBackingTable(3);
        HashSet<Integer> expected = new HashSet<Integer>(2);

        map.put(3000, "A");
        expected.add(3000);
        Assert.assertEquals(expected, map.keySet());

        map.put(3001, "B");
        expected.add(3001);
        Assert.assertEquals(expected, map.keySet());

        expected = new HashSet<Integer>(1);
        map.remove(3000);
        expected.add(3001);
        Assert.assertEquals(expected, map.keySet());
    }

    /**************************************************************************************
     values (tests may depend on add, remove, and resize)
     ***********************************************************************************/
    @Test
    public void testValuesEmpty() {
        String[] expected = new String[0];
        Assert.assertArrayEquals(expected, map.values().toArray());
    }

    @Test
    public void testValuesSimple() {
        String[] expected = new String[7];

        for (int i = 0; i < 7; i++) {
            map.put(i, String.valueOf((char) (i + 65)));
            expected[i] = String.valueOf((char) (i + 65));
        }
        Assert.assertArrayEquals(expected, map.values().toArray());
    }

    @Test
    public void testValuesDeleted() {
        String[] expected;

        for (int i = 0; i < 7; i++) {
            map.put(i, String.valueOf((char) (i + 65)));
        }

        for (int i = 0; i < 7; i++) {
            expected = new String[7 - i];
            for (int j = i; j < 7; j++) {
                expected[j - i] = String.valueOf((char) (j + 65));
            }
            Assert.assertArrayEquals(expected, map.values().toArray());
            map.remove(i);
        }
    }

    @Test
    public void testValuesResized() {
        String[] expected = new String[10];

        for (int i = 0; i < 10; i++) {
            map.put(i, String.valueOf((char) (i + 65)));
            expected[i] = String.valueOf((char) (i + 65));
        }
        Assert.assertArrayEquals(expected, map.values().toArray());
    }

    @Test
    public void testValuesResizedDeleted() {
        String[] expected;

        for (int i = 0; i < 10; i++) {
            map.put(i, String.valueOf((char) (i + 65)));
        }

        for (int i = 0; i < 10; i++) {
            expected = new String[10 - i];
            for (int j = i; j < 10; j++) {
                expected[j - i] = String.valueOf((char) (j + 65));
            }
            Assert.assertArrayEquals(expected, map.values().toArray());
            map.remove(i);
        }
    }

    @Test
    public void testValuesOffSet() {
        String[] expected;

        for (int i = 200; i < 210; i++) {
            map.put(i, String.valueOf((char) (i - 200 + 65)));
        }

        for (int i = 200; i < 210; i++) {
            expected = new String[10 - i + 200];
            for (int j = i; j < 210; j++) {
                expected[j - i] = String.valueOf((char) (j - 200 + 65));
            }
            Assert.assertArrayEquals(expected, map.values().toArray());
            map.remove(i);
        }
    }

    @Test
    public void testValuesUnexpected() {
        map.resizeBackingTable(3);
        String[] expected = new String[2];

        map.put(3000, "A");
        expected[0] = "A";
        map.put(3001, "B");
        expected[1] = "B";
        Assert.assertEquals(expected, map.values().toArray());

        expected = new String[1];
        map.remove(3000);
        expected[0] = "B";
        Assert.assertEquals(expected, map.values().toArray());
    }

    /**************************************************************************************
     resize + clear (tests may depend on add, remove, and resize)
     note that most resize cases have already been covered by the
     put method, which calls resize.
     ***********************************************************************************/
    @Test(expected = IllegalArgumentException.class)
    public void testResizeUnderCurrentSize() {
        for (int i = 0; i < 7; i++) {
            map.put(i, String.valueOf((char) (i + 65)));
        }

        map.resizeBackingTable(6);
    }

    @Test
    public void testResizeOverLoadFactor() {
        LinearProbingMapEntry[] expected = new LinearProbingMapEntry[8];
        for (int i = 0; i < 7; i++) {
            map.put(i, String.valueOf((char) (i + 65)));
            expected[i] = new LinearProbingMapEntry<Integer, String>(i, String.valueOf((char) (i + 65)));
        }

        map.resizeBackingTable(8);
        Assert.assertArrayEquals(expected, map.getTable());
    }

    @Test
    public void testClear() {
        for (int i = 0; i < 30; i++) {
            map.put(i, String.valueOf((char) (i + 65)));
        }
        map.clear();

        Assert.assertEquals(0, map.size());
        Assert.assertArrayEquals(new LinearProbingMapEntry[LinearProbingHashMap.INITIAL_CAPACITY], map.getTable());
    }
}