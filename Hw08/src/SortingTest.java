import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

/**
 * This week Jack Smalligan and Ruston Shome have teamed up to create a set of test cases together! We decided to try
 * this out after foreseeing a lot of overlap in the tests for the algorithms section of the course. Overall it's been
 * fun collaborating, and we hope you enjoy the results!
 *
 * As always, we encourage you to run and pass TA provided JUnits.
 *
 *  Designed to Test:
 *  1. Best Case
 *  2. Worst Case
 *  3. Empty Array
 *  4. Array Size One
 *  5. Array Size Two
 *  6. Even Length Array
 *  7. Odd Length Array
 *  8. Random Case
 *  9. Negative Elements
 *  10. Positive Elements
 *  11. Mixed Sign Elements
 *  12. Expected Exceptions
 *  13. Stability/instability
 *
 *  Not Designed to Test:
 *  1. The stability/instability tests are basic. Make sure to carefully check your logic to ensure
 *     correctness in these.
 *  2. The exact number of comparison required. While we do set upper bounds, these are NOT strict upper bounds.
 *     It is possible that a correct implementation would require less.
 *  3. Efficiency
 *  4. Checkstyle, ensuring private visibility of helper methods, checking for print statements
 *  (all of the things in bullet 4 require reflection, which I simply do not want to touch)
 *
 *
 *
 *  @author Ruston Shome
 *  @author Jack Smalligan
 *  @version 1.1
 *  @link https://github.gatech.edu/gist/rshome3/28cf1914bb584842510dda7ed4931d7a
 */
public class SortingTest {
    private ComparatorPlus<Integer> comp;
    private Integer[] globalArr;
    private Random globalRand;

    /**
     * If you are getting a time-out error, there is a very high chance your code produces an infinite loop.
     * Check the base/break case and increment on any recursive code or While loops
     * Check the indices and increment on any For loops
     */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10);

    @Before
    public void setup() {
        comp = new ComparatorPlus<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                incrementCount();
                return Integer.compare(o1, o2);
            }
        };
        globalArr = new Integer[10];
        globalRand = new Random(123);
    }

    /**************************************************************************************
     Insertion Sort
     ***********************************************************************************/
    @Test
    public void insertionSortBestCase() {
        Integer[] arr = new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Sorting.insertionSort(arr, comp);

        assertArrayEquals(new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, arr);
        System.out.println("Expected: " + 10);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 10);
    }

    @Test
    public void insertionSortWorstCase() {
        Integer[] arr = new Integer[] {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        Sorting.insertionSort(arr, comp);

        assertArrayEquals(new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, arr);
        System.out.println("Expected: " + 45);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 45);
    }

    @Test
    public void insertionEmpty() {
        Integer[] arr = new Integer[] {};
        Sorting.insertionSort(arr, comp);

        assertArrayEquals(new Integer[] {}, arr);
        System.out.println("Expected: " + 0);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() == 0);
    }

    @Test
    public void insertionOneElement() {
        Integer[] arr = new Integer[] {0};
        Sorting.insertionSort(arr, comp);

        assertArrayEquals(new Integer[] {0}, arr);
        System.out.println("Expected: <=1");
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 1);
    }

    @Test
    public void insertionTwoElement() {
        Integer[] arr = new Integer[] {1, 0};
        Sorting.insertionSort(arr, comp);

        assertArrayEquals(new Integer[] {0, 1}, arr);
        System.out.println("Expected: 1");
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 1);

    }

    @Test
    public void insertionEvenLength() {
        Integer[] arr = new Integer[] {1, 0, 4, 16, 6, 7, 11, 8};
        Sorting.insertionSort(arr, comp);

        assertArrayEquals(new Integer[] {0, 1, 4, 6, 7, 8, 11, 16}, arr);
        System.out.println("Expected: 12");
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 12);
    }

    @Test
    public void insertionOddLength() {
        Integer[] arr = new Integer[] {1, 0, 4, 16, 6, 7, 11, 8, 89};
        Sorting.insertionSort(arr, comp);

        assertArrayEquals(new Integer[] {0, 1, 4, 6, 7, 8, 11, 16, 89}, arr);
        System.out.println("Expected: 13");
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 13);
    }

    @Test
    public void insertionNegativeElements() {
        Integer[] arr = new Integer[] {-21, -16, -8, -31, -4, -12, Integer.MIN_VALUE};
        Sorting.insertionSort(arr, comp);

        assertArrayEquals(new Integer[] {Integer.MIN_VALUE, -31, -21, -16, -12, -8, -4}, arr);
        System.out.println("Expected: 15");
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 15);
    }

    @Test
    public void insertionPositiveElements() {
        Integer[] arr = new Integer[] {3, 7, 9, 2, 15, 12, Integer.MAX_VALUE, 100};
        Sorting.insertionSort(arr, comp);

        assertArrayEquals(new Integer[] {2, 3, 7, 9, 12, 15, 100, Integer.MAX_VALUE}, arr);
        System.out.println("Expected: 11");
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 11);
    }

    @Test
    public void insertionMixedSign() {
        Integer[] arr = new Integer[] {-1, 0, 4, -16, 6, 7, 11, -8, 89};
        Sorting.insertionSort(arr, comp);

        assertArrayEquals(new Integer[] {-16, -8, -1, 0, 4, 6, 7, 11, 89}, arr);
        System.out.println("Expected: 16");
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 16);
    }

    @Test
    public void insertionStability() {
        Integer one0 = new Integer(1);
        Integer one1 = new Integer(1);
        Integer one2 = new Integer(1);
        Integer max0 = new Integer(Integer.MAX_VALUE);
        Integer max1 = new Integer(Integer.MAX_VALUE);
        Integer[] arr = {max0, 8, 17, 200, 3, one0, max1, 13, 215, one1, 8990, one2};

        Sorting.insertionSort(arr, comp);

        assertArrayEquals(new Integer[] {1, 1, 1, 3, 8, 13, 17, 200, 215, 8990, max0, max0}, arr);
        assertSame(one0, arr[0]);
        assertSame(one1, arr[1]);
        assertSame(one2, arr[2]);
        assertSame(max0, arr[10]);
        assertSame(max1, arr[11]);
    }

    /**************************************************************************************
     Cocktail-Shaker Sort
     ***********************************************************************************/
    @Test
    public void cocktailSortBestCase() {
        Integer[] arr = new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Sorting.cocktailSort(arr, comp);

        assertArrayEquals(new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, arr);
        System.out.println("Expected: " + 9);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 9);
    }

    @Test
    public void cocktailSortWorstCase() {
        Integer[] arr = new Integer[] {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        Sorting.cocktailSort(arr, comp);

        assertArrayEquals(new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, arr);
        System.out.println("Expected: " + 45);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 45);
    }

    @Test
    public void cocktailEmpty() {
        Integer[] arr = new Integer[] {};
        Sorting.cocktailSort(arr, comp);

        assertArrayEquals(new Integer[] {}, arr);
        System.out.println("Expected: " + 0);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 0);
    }

    @Test
    public void cocktailOneElement() {
        Integer[] arr = new Integer[] {0};
        Sorting.cocktailSort(arr, comp);

        assertArrayEquals(new Integer[] {0}, arr);
        System.out.println("Expected: 0");
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 0);
    }

    @Test
    public void cocktailTwoElement() {
        Integer[] arr = new Integer[] {1, 0};
        Sorting.cocktailSort(arr, comp);

        assertArrayEquals(new Integer[] {0, 1}, arr);
        System.out.println("Expected: 1");
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 1);

    }

    @Test
    public void cocktailEvenLength() {
        Integer[] arr = new Integer[] {1, 0, 4, 16, 6, 7, 11, 8};
        Sorting.cocktailSort(arr, comp);

        assertArrayEquals(new Integer[] {0, 1, 4, 6, 7, 8, 11, 16}, arr);
        System.out.println("Expected: 13");
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 13);
    }

    @Test
    public void cocktailOddLength() {
        Integer[] arr = new Integer[] {1, 0, 4, 16, 6, 7, 11, 8, 89};
        Sorting.cocktailSort(arr, comp);

        assertArrayEquals(new Integer[] {0, 1, 4, 6, 7, 8, 11, 16, 89}, arr);
        System.out.println("Expected: 14");
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 14);
    }

    @Test
    public void cocktailNegativeElements() {
        Integer[] arr = new Integer[] {-21, -16, -8, -31, -4, -12, Integer.MIN_VALUE};
        Sorting.cocktailSort(arr, comp);

        assertArrayEquals(new Integer[] {Integer.MIN_VALUE, -31, -21, -16, -12, -8, -4}, arr);
        System.out.println("Expected: 20");
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 20);
    }

    @Test
    public void cocktailPositiveElements() {
        Integer[] arr = new Integer[] {3, 7, 9, 2, 15, 12, Integer.MAX_VALUE, 100};
        Sorting.cocktailSort(arr, comp);

        assertArrayEquals(new Integer[] {2, 3, 7, 9, 12, 15, 100, Integer.MAX_VALUE}, arr);
        System.out.println("Expected: 18");
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 18);
    }

    @Test
    public void cocktailMixedSign() {
        Integer[] arr = new Integer[] {-1, 0, 4, -16, 6, 7, 11, -8, 89};
        Sorting.cocktailSort(arr, comp);

        assertArrayEquals(new Integer[] {-16, -8, -1, 0, 4, 6, 7, 11, 89}, arr);
        System.out.println("Expected: 20");
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 20);
    }

    @Test
    public void cocktailStability() {
        Integer one0 = new Integer(1);
        Integer one1 = new Integer(1);
        Integer one2 = new Integer(1);
        Integer max0 = new Integer(Integer.MAX_VALUE);
        Integer max1 = new Integer(Integer.MAX_VALUE);
        Integer[] arr = {max0, 8, 17, 200, 3, one0, max1, 13, 215, one1, 8990, one2};

        Sorting.cocktailSort(arr, comp);

        assertArrayEquals(new Integer[] {1, 1, 1, 3, 8, 13, 17, 200, 215, 8990, max0, max0}, arr);
        assertSame(one0, arr[0]);
        assertSame(one1, arr[1]);
        assertSame(one2, arr[2]);
        assertSame(max0, arr[10]);
        assertSame(max1, arr[11]);
    }

    /**************************************************************************************
     Merge Sort
     ***********************************************************************************/

    @Test
    public void mergeSortWorstCase() {
        // the worst case possibility for mergesort is that both sides of all sub-arrays have to be exhausted
        // one at a time, allowing no en-masse copies because one side was exhausted
        Integer[] arr = new Integer[] {3, 0, 1, 10, 4, 8, 2, 9};

        Sorting.mergeSort(arr, comp);
        assertArrayEquals(new Integer[] {0, 1, 2, 3, 4, 8, 9, 10}, arr);
        System.out.println("Expected: 17");
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 17);
    }

    @Test
    public void mergeSortBestCase() {
        // In the best case, we shed some comparisons free copies over because one side was exhausted early
        Integer[] arr = new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        Sorting.mergeSort(arr, comp);
        assertArrayEquals(new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, arr);
        System.out.println("Expected: " + 15);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 15);
    }

    @Test
    public void mergeSortEmpty() {
        Integer[] arr = new Integer[] {};
        Sorting.mergeSort(arr, comp);

        assertArrayEquals(new Integer[] {}, arr);
        System.out.println("Expected: " + 0);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() == 0);
    }

    @Test
    public void mergeSortOneElement() {
        Integer[] arr = new Integer[] {0};
        Sorting.mergeSort(arr, comp);

        assertArrayEquals(new Integer[] {0}, arr);
        System.out.println("Expected: " + 0);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() == 0);
    }

    @Test
    public void mergeSortTwoElement() {
        Integer[] arr = new Integer[] {1, 0};
        Sorting.mergeSort(arr, comp);

        assertArrayEquals(new Integer[] {0, 1}, arr);
        System.out.println("Expected: " + 1);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 1);
    }

    @Test
    public void mergeSortNegative() {
        Integer[] arr = new Integer[] {-3, -5, -7, -10, -1, -4, -9, Integer.MIN_VALUE};
        Sorting.mergeSort(arr, comp);

        assertArrayEquals(new Integer[] {Integer.MIN_VALUE, -10, -9, -7, -5, -4, -3, -1}, arr);
        System.out.println("Expected: " + 15);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 15);
    }

    @Test
    public void mergeSortPositive() {

        Integer[] arr = new Integer[] {3, 10, 5, 7, 1, 4, 9, 13};
        Sorting.mergeSort(arr, comp);

        assertArrayEquals(new Integer[] {1, 3, 4, 5, 7, 9, 10, 13}, arr);

        System.out.println("Expected: " + 16);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 16);
    }

    @Test
    public void mergeSortMixedSigns() {
        Integer[] arr = new Integer[] {-3, 10, 5, -7, 0, 1, 4, -9, 13};
        Sorting.mergeSort(arr, comp);

        assertArrayEquals(new Integer[] {-9, -7, -3, 0, 1, 4, 5, 10, 13}, arr);

        System.out.println("Expected: " + 20);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 20);
    }

    @Test
    public void mergeSortEvenElements() {
        Integer[] arr = new Integer[] {4, 2, 6, 9, 3, 7, 10, 1};

        Sorting.mergeSort(arr, comp);

        assertArrayEquals(new Integer[] {1, 2, 3, 4, 6, 7, 9, 10}, arr);

        System.out.println("Expected: " + 16);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 16);
    }

    @Test
    public void mergeSortOddElements() {
        Integer[] arr = new Integer[] {4, 2, 6, 9, 3, 7, 0, 10, 1};

        Sorting.mergeSort(arr, comp);

        assertArrayEquals(new Integer[] {0, 1, 2, 3, 4, 6, 7, 9, 10}, arr);

        System.out.println("Expected: " + 19);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 19);
    }

    @Test
    public void mergeSortStability() {
        Integer zero0 = new Integer(0);
        Integer zero1 = new Integer(0);
        Integer zero2 = new Integer(0);
        Integer[] arr = new Integer[] {1, zero0, 5, 9, 10, 21, zero1, 7, 8, zero2};

        Sorting.mergeSort(arr, comp);

        assertArrayEquals(new Integer[] {0, 0, 0, 1, 5, 7, 8, 9, 10, 21}, arr);

        // these end up out in reversed order
        assertSame(arr[0], zero0);
        assertSame(arr[1], zero1);
        assertSame(arr[2], zero2);
    }

    /**************************************************************************************
     QuickSort
     ***********************************************************************************/
    @Test
    public void quicksort() {
        Integer[] arr = new Integer[] {7, 5, 8, 4, 3, 6, 9, 0, 2, 1};
        Sorting.quickSort(arr, comp, new Random());
        assertArrayEquals(new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, arr);
    }

    @Test
    public void quicksortWorstCase() {
        // This tests may not function if you implement quicksort with Random() methods other than nextInt()
        BadRandom rand = new BadRandom();
        rand.load(Arrays.asList(4, 1, 0, 1, 0)); // Have the pivot always select the smallest value
        // which renders this equivalent to selection sort in efficiency

        Integer[] arr = new Integer[] {3, 2, 1, 4, 0, 5};

        // resulting iterations:
        //   s           p  stop
        //  {3, 2, 1, 4, 0, 5}
        //      s  p        stop
        //  {0, 2, 1, 4, 3, 5}
        //         s/p        stop
        //  {0, 1,  2,  4, 3, 5}
        //            s  p  stop
        //  {0, 1, 2, 4, 3, 5}

        // Depending on the implementation, this may be the base case,
        // but I load index 0 for 4 being the pivot for those who use base case of length 1
        //              s/p stop
        //  {0, 1, 2, 3, 4, 5}

        // counting i and j comparisons in each iteration:
        // i always has to compare once, and j has to compare with every element in the sublist
        // hence: (1 + 5) + (1 + 4) + (1 + 3) + (1 + 2) + 1 (if base case == 2)       = 19 total comparisons
        //                                              + (1 + 1) (if base case == 1) = 20 total comparisons

        try {
            Sorting.quickSort(arr, comp, rand);
        } catch (IllegalStateException e) {
            // this will come from nextInt() being called on the BadRandom object
            fail("nextInt() was called too many times. This is an inefficiency indicator.");
        }
        assertArrayEquals(new Integer[] {0, 1, 2, 3, 4, 5}, arr);
        System.out.println("Expected: <" + 20);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 20);
    }

    @Test
    public void quickSortTypicalCase() {
        BadRandom rand = new BadRandom();
        rand.load(Arrays.asList(1, 0, 0)); // choose 2 as the first pivot (for those with base case length == 1,
        // use the first element as the pivot in the length 2 recurses)
        Integer[] arr = new Integer[] {4, 2, 0, 1, 5};

        try {
            Sorting.quickSort(arr, comp, rand);
        } catch (IllegalStateException e) {
            // this will come from nextInt() being called on the BadRandom object
            fail("nextInt() was called too many times. This is an inefficiency indicator.");
        }

        assertArrayEquals(new Integer[] {0, 1, 2, 4, 5}, arr);
        // if your base case is length 2, should be less than 7 comparisons, if it is length 1, should
        // be less than 9 comparisons (depending on implementation, you may have fewer than these numbers)
        System.out.println("Expected: <" + 9);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 9);
    }

    @Test
    public void quicksortEmpty() {
        Integer[] arr = new Integer[] {};
        Sorting.quickSort(arr, comp, new Random());

        assertArrayEquals(new Integer[] {}, arr);
        System.out.println("Expected: " + 0);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() == 0);
    }

    @Test
    public void quicksortOneElement() {
        Integer[] arr = new Integer[] {0};
        Sorting.quickSort(arr, comp, new Random());

        assertArrayEquals(new Integer[] {0}, arr);
        System.out.println("Expected: " + 0);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() == 0);
    }

    @Test
    public void quicksortTwoElement() {
        Integer[] arr = new Integer[] {1, 0};
        Sorting.quickSort(arr, comp, new Random());

        assertArrayEquals(new Integer[] {0, 1}, arr);
        // if length 2 is the base case, should be just 1, if 1 is the base case,
        // it may be 2, depending on implementation
        System.out.println("Expected: <" + 2);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 2);
    }

    @Test
    public void quickSortNegative() {
        Integer[] arr = new Integer[] {-3, -5, -7, -10, -1, -4, -9, -13};
        Sorting.quickSort(arr, comp, new Random(2));

        assertArrayEquals(new Integer[] {-13, -10, -9, -7, -5, -4, -3, -1}, arr);
        // It quickly becomes quite complicated to determine exactly how many comparisons should be performed for a
        // given sequence of pivots if the recursion depth is greater than 1. The reason is that even if we supply
        // a sequence of pivot values, we would have to then account for whether a recursive call at length == 2 will
        // happen, which depends on implementation. If you aren't sure if your exact figure is correct, diagramming out
        // the process by hand according to your implementation algorithm can help you determine.
        //
        // For a base case of 2, should expect ~15 comparisons. A base case of 1 may add as many as log2(n)
        // more comparisons in the worst case, for an upper bound of ~18. More than 22 is probably an error, which
        // will be our threshold here.
        // Because of this, passing these checks cannot guarantee that you have the correct efficiency.
        System.out.println("Expected: <" + 22);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 22);
    }

    @Test
    public void quickSortPositive() {

        Integer[] arr = new Integer[] {3, 10, 5, 7, 1, 4, 9, 13};
        Sorting.quickSort(arr, comp, new Random(0));

        assertArrayEquals(new Integer[] {1, 3, 4, 5, 7, 9, 10, 13}, arr);

        // see quickSortNegative() test for details about the comparison figures.
        System.out.println("Expected: <" + 22);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 22);
    }

    @Test
    public void quickSortMixedSigns() {
        Integer[] arr = new Integer[] {-3, 10, 5, -7, 0, 1, 4, -9, 13};
        Sorting.quickSort(arr, comp, new Random(0));

        assertArrayEquals(new Integer[] {-9, -7, -3, 0, 1, 4, 5, 10, 13}, arr);

        // see quickSortNegative() test for details about the comparison figures.
        System.out.println("Expected: <" + 22);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 22);
    }

    @Test
    public void quickSortEvenElements() {
        Integer[] arr = new Integer[] {4, 2, 6, 9, 3, 7, 10, 1};

        Sorting.quickSort(arr, comp, new Random(0));

        assertArrayEquals(new Integer[] {1, 2, 3, 4, 6, 7, 9, 10}, arr);

        // see quickSortNegative() test for details about the comparison figures.
        System.out.println("Expected: <" + 24);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 24);
    }

    @Test
    public void quickSortOddElements() {
        Integer[] arr = new Integer[] {4, 2, 6, 9, 3, 7, 0, 10, 1};

        Sorting.quickSort(arr, comp, new Random(0));

        assertArrayEquals(new Integer[] {0, 1, 2, 3, 4, 6, 7, 9, 10}, arr);

        // see quickSortNegative() test for details about the comparison figures.
        System.out.println("Expected: <" + 33);
        System.out.println("Actual: " + comp.getCount());
        assertTrue(comp.getCount() <= 33);
    }

    @Test
    public void quickSortInstability() {
        Integer zero0 = new Integer(0);
        Integer zero1 = new Integer(0);
        Integer one0 = new Integer(1);
        Integer one1 = new Integer(1);
        Integer one2 = new Integer(1);
        Integer[] arr = new Integer[] {one0, zero0, one1, zero1, one2};

        BadRandom rand = new BadRandom();
        rand.load(Arrays.asList(0, 0, 0, 0, 0)); // always choose the first element
        Sorting.quickSort(arr, comp, rand);

        assertArrayEquals(new Integer[] {0, 0, 1, 1, 1}, arr);

        // this will give this sequence, regardless of base case
        // {0_0, 0_1, 1_1, 1_2, 1_0}
        assertSame(arr[2], one1);
        assertSame(arr[3], one2);
        assertSame(arr[4], one0);

    }

    /**************************************************************************************
     LSD-Radix Sort
     ***********************************************************************************/
    @Test
    public void radixBestCase() {
        int[] arr = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        Sorting.lsdRadixSort(arr);
        assertArrayEquals(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, arr);
    }

    @Test
    public void radixWorstCase() {
        int[] arr = new int[] {987654321, 10987654, 3210987, 65432, 1098, 765, 43, 2};
        Sorting.lsdRadixSort(arr);
        assertArrayEquals(new int[] {2, 43, 765, 1098, 65432, 3210987, 10987654, 987654321}, arr);
    }

    @Test
    public void radixSortEmpty() {
        int[] arr = new int[] {};
        Sorting.lsdRadixSort(arr);
        assertArrayEquals(new int[] {}, arr);
    }

    @Test
    public void radixOneElement() {
        int[] arr = new int[] {0};
        Sorting.lsdRadixSort(arr);
        assertArrayEquals(new int[] {0}, arr);
    }

    @Test
    public void radixTwoElement() {
        int[] arr = new int[] {1, 0};
        Sorting.lsdRadixSort(arr);
        assertArrayEquals(new int[] {0, 1}, arr);

    }

    @Test
    public void radixEvenLength() {
        int[] arr = new int[] {13, 0, 73, 63, 43, 8345};
        Sorting.lsdRadixSort(arr);
        assertArrayEquals(new int[] {0, 13, 43, 63, 73, 8345}, arr);
    }

    @Test
    public void radixOddLength() {
        int[] arr = new int[] {132, 0, 432, 632, 732, 113, 832};
        Sorting.lsdRadixSort(arr);
        assertArrayEquals(new int[] {0, 113, 132, 432, 632, 732, 832}, arr);
    }

    @Test
    public void radixNegativeElements() {
        int[] arr = new int[] {-21, -16, -8, -31, -4, -12, Integer.MIN_VALUE};
        Sorting.lsdRadixSort(arr);
        assertArrayEquals(new int[] {Integer.MIN_VALUE, -31, -21, -16, -12, -8, -4}, arr);
    }

    @Test
    public void radixPositiveElements() {
        int[] arr = new int[] {3, 7, 9, 2, 15, 12, Integer.MAX_VALUE, 100};
        Sorting.lsdRadixSort(arr);
        assertArrayEquals(new int[] {2, 3, 7, 9, 12, 15, 100, Integer.MAX_VALUE}, arr);
    }

    @Test
    public void radixMixedSign() {
        int[] arr = new int[]  {-1, 0, 4, -16, 6, 7, 11, -8, 89};
        Sorting.lsdRadixSort(arr);
        assertArrayEquals(new int[] {-16, -8, -1, 0, 4, 6, 7, 11, 89}, arr);
    }

    @Test
    public void radixOverflow() {
        // if you fail this test and pass the other radix sort tests, it likely has to do with handling
        // overflow cases
        int[] arr = new int[] {Integer.MIN_VALUE + 5, Integer.MIN_VALUE + 4, Integer.MIN_VALUE + 3,
                Integer.MIN_VALUE + 2, Integer.MIN_VALUE + 1, Integer.MIN_VALUE};
        Sorting.lsdRadixSort(arr);
        assertArrayEquals(new int[] {Integer.MIN_VALUE, Integer.MIN_VALUE + 1, Integer.MIN_VALUE + 2,
                Integer.MIN_VALUE + 3, Integer.MIN_VALUE + 4, Integer.MIN_VALUE + 5}, arr);
    }

    /**************************************************************************************
     Heap-Sort
     ***********************************************************************************/
    @Test
    public void heapSortBestCase() {
        Integer[] arr = new Integer[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        assertArrayEquals(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, Sorting.heapSort(Arrays.asList(arr)));
    }

    @Test
    public void heapSortWorstCase() {
        Integer[] arr = new Integer[] {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};
        assertArrayEquals(new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9}, Sorting.heapSort(Arrays.asList(arr)));
    }

    @Test
    public void heapSortEmpty() {
        Integer[] arr = new Integer[] {};
        assertArrayEquals(new int[] {}, Sorting.heapSort(Arrays.asList(arr)));
    }

    @Test
    public void heapSortOneElement() {
        Integer[] arr = new Integer[] {0};
        assertArrayEquals(new int[] {0}, Sorting.heapSort(Arrays.asList(arr)));
    }

    @Test
    public void heapSortTwoElement() {
        Integer[] arr = new Integer[] {1, 0};
        assertArrayEquals(new int[] {0, 1}, Sorting.heapSort(Arrays.asList(arr)));

    }

    @Test
    public void heapSortEvenLength() {
        Integer[] arr = new Integer[] {1, 0, 4, 16, 6, 7, 11, 8};
        assertArrayEquals(new int[] {0, 1, 4, 6, 7, 8, 11, 16}, Sorting.heapSort(Arrays.asList(arr)));
    }

    @Test
    public void heapSortOddLength() {
        Integer[] arr = new Integer[] {1, 0, 4, 16, 6, 7, 11, 8, 89};
        assertArrayEquals(new int[] {0, 1, 4, 6, 7, 8, 11, 16, 89}, Sorting.heapSort(Arrays.asList(arr)));
    }

    @Test
    public void heapSortNegativeElements() {
        Integer[] arr = new Integer[] {-21, -16, -8, -31, -4, -12, Integer.MIN_VALUE};
        assertArrayEquals(new int[] {Integer.MIN_VALUE, -31, -21, -16, -12, -8, -4},
                Sorting.heapSort(Arrays.asList(arr)));
    }

    @Test
    public void heapSortPositiveElements() {
        Integer[] arr = new Integer[] {3, 7, 9, 2, 15, 12, Integer.MAX_VALUE, 100};
        assertArrayEquals(new int[] {2, 3, 7, 9, 12, 15, 100, Integer.MAX_VALUE},
                Sorting.heapSort(Arrays.asList(arr)));
    }

    @Test
    public void heapSortMixedSign() {
        Integer[] arr = new Integer[] {-1, 0, 4, -16, 6, 7, 11, -8, 89};
        assertArrayEquals(new int[] {-16, -8, -1, 0, 4, 6, 7, 11, 89},
                Sorting.heapSort(Arrays.asList(arr)));
    }

    /**************************************************************************************
     Random Data
     ***********************************************************************************/

    /** The purpose of this test is to check that each algorithm can correctly produce
     * sorted output.
     *
     * This test does not check that the correct algorithm is followed, nor the efficiency thereof.
     *
     * Note, if you timeout on this test and you have a relatively slow system, you can try to increase the global
     * timeout. If it doesn't complete in 20 seconds, an infinite loop is probably more likely than your system
     * being too slow.
     */
    @Test
    public void randTest() {
        for (int i = 0; i < 1000; i++) {
            Integer[] arr = createRandomArray();

            Integer[] exp = Arrays.copyOf(arr, arr.length);
            Arrays.sort(exp);

            Integer[] cocktail = Arrays.copyOf(arr, arr.length);
            Sorting.cocktailSort(cocktail, comp);
            assertArrayEquals(exp, cocktail);

            Integer[] insertion = Arrays.copyOf(arr, arr.length);
            Sorting.insertionSort(insertion, comp);
            assertArrayEquals(exp, insertion);

            List<Integer> heap = List.of(arr);
            int k = 0;
            Integer[] heapOut = new Integer[arr.length];
            for (int j : Sorting.heapSort(heap)) {
                heapOut[k++] = j;
            }
            assertArrayEquals(exp, heapOut);

            Integer[] merge = Arrays.copyOf(arr, arr.length);
            Sorting.mergeSort(merge, comp);
            assertArrayEquals(exp, merge);

            Integer[] quick = Arrays.copyOf(arr, arr.length);
            Sorting.quickSort(quick, comp, new Random());
            assertArrayEquals(exp, quick);

            int[] lsd = new int[arr.length];
            for (int j = 0; j < lsd.length; j++) {
                lsd[j] = arr[j];
            }

            int[] lsdExp = Arrays.copyOf(lsd, lsd.length);
            Arrays.sort(lsdExp);

            Sorting.lsdRadixSort(lsd);
            assertArrayEquals(lsdExp, lsd);

        }
    }

    /**************************************************************************************
     Exceptions
     ***********************************************************************************/
    @Test(expected = IllegalArgumentException.class)
    public void testInsertionSortNullArray() {
        Sorting.insertionSort(null, comp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertionSortNullComparator() {
        Sorting.insertionSort(globalArr, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCocktailSortNullArray() {
        Sorting.cocktailSort(null, comp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCocktailSortNullComparator() {
        Sorting.cocktailSort(globalArr, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMergeSortNullArray() {
        Sorting.mergeSort(null, comp);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMergeSortNullComparator() {
        Sorting.mergeSort(globalArr, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuickSortNullArray() {
        Sorting.quickSort(null, comp, globalRand);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuickSortNullComparator() {
        Sorting.quickSort(globalArr, null, globalRand);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testQuickSortNullRand() {
        Sorting.quickSort(globalArr, comp, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRadixSortNullArray() {
        Sorting.lsdRadixSort(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testHeapSortNullArray() {
        Sorting.heapSort(null);
    }

    /**************************************************************************************
     Helper
     ***********************************************************************************/

    /**
     * Creates a random array
     * @return the random array of Integers
     */
    private Integer[] createRandomArray() {
        Random rand = new Random();
        Integer[] arr = new Integer[rand.nextInt(1000)];

        for (int i = 0; i < arr.length; i++) {
            arr[i] = rand.nextInt();
        }

        return arr;
    }

    /**
     * A "random" number generator that can produce predictable outputs
     */
    private class BadRandom extends Random {
        private ArrayList<Integer> nexts = new ArrayList();

        /**
         * Allows this random number generator to be loaded with the next values to return
         * @param nexts the values to be returned, starting with index 0, on subsequent calls to nextInt()
         */
        public void load(List<Integer> nexts) {
            this.nexts.addAll(nexts);
        }

        @Override
        public int nextInt() {
            if (nexts.size() == 0) {
                throw new IllegalStateException("Should have loaded next value");
            }
            return (int) nexts.remove(0);
        }

        @Override
        public int nextInt(int bound) {
            return nextInt() % bound;
        }
    }

    /**
     * Courtesy of the 1332 TAs
     * @param <T> the data type being compared
     */
    private abstract static class ComparatorPlus<T> implements Comparator<T> {
        private int count;

        /**
         * Get the number of comparisons made.
         *
         * @return number of comparisons made
         */
        public int getCount() {
            return count;
        }

        /**
         * Increment the number of comparisons made by one. Call this method in
         * your compare() implementation.
         */
        public void incrementCount() {
            count++;
        }
    }
}