import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 * This set of tests is designed to SUPPLEMENT the TA provided JUnits.
 * I would highly encourage you to run and pass those tests.
 *
 *  Designed to Test:
 *  1. Basic Add Functionality
 *  2. All Rotations Without Children
 *  3. Basic Remove Functionality
 *  4. All Rotations With Children
 *  5. Get
 *  6. Contains
 *  7. Basic Size Cases
 *  8. Height
 *  9. Clear
 *  10. Collections Constructor
 *  11. Basic Deepest Branches
 *  12. Basic Sorted In Between
 *
 *  Not Designed to Test:
 *  1. Efficiency
 *  2. Checkstyle, ensuring private visibility of helper methods, checking for print statements
 *  (all of the things in bullet 4 require reflection, which I simply do not want to touch)
 *
 *
 *  NOTE: I would recommend debugging each test in the order it appears. In general, each section of test builds on
 *  each other, so failing a test in one section could lead to many failures in the next.
 *
 *  Hope midterm 2 went well for everyone!!
 *
 *  @author Ruston Shome
 *  @version 1.0
 */

public class AVLRustonTest {
    private AVL<Integer> tree;
    Integer zero = 0;
    Integer one = 1;
    Integer two = 2;
    Integer three = 3;
    Integer four = 4;
    Integer five = 5;
    Integer six = 6;
    Integer seven = 7;
    Integer eight = 8;
    Integer nine = 9;
    Integer nOne = -1;
    Integer nTwo = -2;
    Integer nThree = -3;
    Integer nFour = -4;
    Integer nFive = -5;
    Integer nSix = -6;
    Integer nSeven = -7;
    Integer nEight = -8;
    Integer nNine = -9;

    /**
     * If you are getting a time out error, there is a very high chance your code produces an infinite loop.
     * Check the base/break case and increment on any recursive code or While loops
     * Check the indices and increment on any For loops
     *
     * Timeout rules tell you NOTHING about efficiency.
     * In general I would expect the whole suit to run in 30-100ms depending on your machine
     * In general I would expect an individual test to take no more than 20ms depending on your machine
     * But once again these time have NOTHING to do with the efficiency of your code
     */
    @Rule
    public Timeout timeout = Timeout.seconds(5);

    @Before
    public void setup() {
        tree = new AVL<>();
    }

    /**************************************************************************************
     Default Constructor
     ***********************************************************************************/
    public void testDefaultConstructor() {
        Assert.assertNull(tree.getRoot());
        Assert.assertEquals(0, tree.size());
        Assert.assertEquals(-1, tree.height());
    }

    /**************************************************************************************
     ADD
     ***********************************************************************************/
    @Test
    public void testAddToEmpty() {
        /*
           100
         */
        Integer myInt = 100;
        tree.add(myInt);
        Assert.assertSame(myInt, tree.getRoot().getData());
        Assert.assertEquals(1, tree.size());
        Assert.assertEquals(0, tree.getRoot().getHeight());
        Assert.assertEquals(0, tree.getRoot().getBalanceFactor());
        Assert.assertNull(tree.getRoot().getLeft());
        Assert.assertNull(tree.getRoot().getRight());
    }

    @Test
    public void testAddBalancedLevelOne() {
        /*
            1
           / \
          0   2
         */
        tree.add(one);
        tree.add(two);
        tree.add(zero);
        Assert.assertEquals(one, tree.getRoot().getData());
        Assert.assertEquals(zero, tree.getRoot().getLeft().getData());
        Assert.assertEquals(two, tree.getRoot().getRight().getData());
        Assert.assertNull(tree.getRoot().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getRight());

        Assert.assertEquals(3, tree.size());
        Assert.assertEquals(1, tree.getRoot().getHeight());
        Assert.assertEquals(0, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getHeight());
    }

    @Test
    public void testAddBalancedLevelTwo() {
        /*
                4
               / \
              2   6
             / \ / \
            1  3 5  7
         */
        tree.add(four);
        tree.add(two);
        tree.add(six);
        tree.add(one);
        tree.add(three);
        tree.add(five);
        tree.add(seven);

        Assert.assertEquals(four, tree.getRoot().getData());
        Assert.assertEquals(two, tree.getRoot().getLeft().getData());
        Assert.assertEquals(six, tree.getRoot().getRight().getData());
        Assert.assertEquals(one, tree.getRoot().getLeft().getLeft().getData());
        Assert.assertEquals(three, tree.getRoot().getLeft().getRight().getData());
        Assert.assertEquals(five, tree.getRoot().getRight().getLeft().getData());
        Assert.assertEquals(seven, tree.getRoot().getRight().getRight().getData());

        Assert.assertNull(tree.getRoot().getLeft().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getRight());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getRight().getRight().getRight());

        Assert.assertEquals(7, tree.size());
        Assert.assertEquals(2, tree.getRoot().getHeight());
        Assert.assertEquals(1, tree.getRoot().getLeft().getHeight());
        Assert.assertEquals(1, tree.getRoot().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getRight().getHeight());

        Assert.assertEquals(0, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getRight().getBalanceFactor());
    }

    @Test
    public void testAddDuplicates() {
        /*
                4
               / \
              2   6
             / \ / \
            1  3 5  7
         */
        tree.add(four);
        tree.add(two);
        tree.add(six);
        tree.add(one);
        tree.add(three);
        tree.add(five);
        tree.add(seven);

        tree.add(four);
        tree.add(two);
        tree.add(six);
        tree.add(one);
        tree.add(three);
        tree.add(five);
        tree.add(seven);

        Assert.assertEquals(7, tree.size());

        Assert.assertEquals(four, tree.getRoot().getData());
        Assert.assertEquals(two, tree.getRoot().getLeft().getData());
        Assert.assertEquals(six, tree.getRoot().getRight().getData());
        Assert.assertEquals(one, tree.getRoot().getLeft().getLeft().getData());
        Assert.assertEquals(three, tree.getRoot().getLeft().getRight().getData());
        Assert.assertEquals(five, tree.getRoot().getRight().getLeft().getData());
        Assert.assertEquals(seven, tree.getRoot().getRight().getRight().getData());

        Assert.assertNull(tree.getRoot().getLeft().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getRight());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getRight().getRight().getRight());

        Assert.assertEquals(2, tree.getRoot().getHeight());
        Assert.assertEquals(1, tree.getRoot().getLeft().getHeight());
        Assert.assertEquals(1, tree.getRoot().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getRight().getHeight());

        Assert.assertEquals(0, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getRight().getBalanceFactor());
    }


    /**************************************************************************************
     ROTATIONS: NO CHILDREN
     ***********************************************************************************/
    @Test
    public void testBalanceFactorAndHeight() {
        /*
               0
             /   \
           -2     2
           / \   / \
         -3  -1 1   3
         */
        tree.add(zero);
        Assert.assertEquals(1, tree.size());
        Assert.assertEquals(0, tree.getRoot().getHeight());
        Assert.assertEquals(0, tree.getRoot().getBalanceFactor());

        tree.add(two);
        Assert.assertEquals(2, tree.size());
        Assert.assertEquals(1, tree.getRoot().getHeight());
        Assert.assertEquals(-1, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getBalanceFactor());

        tree.add(nTwo);
        Assert.assertEquals(3, tree.size());
        Assert.assertEquals(1, tree.getRoot().getHeight());
        Assert.assertEquals(0, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getBalanceFactor());

        tree.add(nThree);
        Assert.assertEquals(4, tree.size());
        Assert.assertEquals(2, tree.getRoot().getHeight());
        Assert.assertEquals(1, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(1, tree.getRoot().getLeft().getHeight());
        Assert.assertEquals(1, tree.getRoot().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getBalanceFactor());

        tree.add(nOne);
        Assert.assertEquals(5, tree.size());
        Assert.assertEquals(2, tree.getRoot().getHeight());
        Assert.assertEquals(1, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(1, tree.getRoot().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getBalanceFactor());

        tree.add(one);
        Assert.assertEquals(6, tree.size());
        Assert.assertEquals(2, tree.getRoot().getHeight());
        Assert.assertEquals(0, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(1, tree.getRoot().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getBalanceFactor());
        Assert.assertEquals(1, tree.getRoot().getRight().getHeight());
        Assert.assertEquals(1, tree.getRoot().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getBalanceFactor());

        tree.add(three);
        Assert.assertEquals(7, tree.size());
        Assert.assertEquals(2, tree.getRoot().getHeight());
        Assert.assertEquals(0, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(1, tree.getRoot().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getBalanceFactor());
        Assert.assertEquals(1, tree.getRoot().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getRight().getBalanceFactor());
    }

    @Test
    public void testLeftRotateNoChildren() {
        /*
         0
          \
           1
            \
             2
         -------->
           1
          / \
         0   2
         */
        tree.add(one);
        tree.add(two);
        tree.add(zero);
        Assert.assertEquals(one, tree.getRoot().getData());
        Assert.assertEquals(zero, tree.getRoot().getLeft().getData());
        Assert.assertEquals(two, tree.getRoot().getRight().getData());
        Assert.assertNull(tree.getRoot().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getRight());

        Assert.assertEquals(3, tree.size());

        Assert.assertEquals(1, tree.getRoot().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getHeight());

        Assert.assertEquals(0, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getBalanceFactor());
    }

    @Test
    public void testRightRotateNoChildren() {
        /*
            2
           /
          1
         /
        0
         -------->
           1
          / \
         0   2
         */
        tree.add(two);
        tree.add(one);
        tree.add(zero);

        Assert.assertEquals(one, tree.getRoot().getData());
        Assert.assertEquals(zero, tree.getRoot().getLeft().getData());
        Assert.assertEquals(two, tree.getRoot().getRight().getData());
        Assert.assertNull(tree.getRoot().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getRight());

        Assert.assertEquals(3, tree.size());

        Assert.assertEquals(1, tree.getRoot().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getHeight());

        Assert.assertEquals(0, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getBalanceFactor());
    }

    @Test
    public void testLeftRightRotateNoChildren() {
        /*
             2
            /
          0
            \
             1
         -------->
              2
             /
           1
          /
         0
        -------->
            1
           / \
          0   2
         */
        tree.add(two);
        tree.add(zero);
        tree.add(one);

        Assert.assertEquals(one, tree.getRoot().getData());
        Assert.assertEquals(zero, tree.getRoot().getLeft().getData());
        Assert.assertEquals(two, tree.getRoot().getRight().getData());
        Assert.assertNull(tree.getRoot().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getRight());

        Assert.assertEquals(3, tree.size());

        Assert.assertEquals(1, tree.getRoot().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getHeight());

        Assert.assertEquals(0, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getBalanceFactor());
    }

    @Test
    public void testRightLeftRotateNoChildren() {
        /*
         0
          \
           2
           /
          1
         -------->
         0
          \
           1
            \
             2
         -------->
           1
          / \
         0   2
         */
        tree.add(zero);
        tree.add(two);
        tree.add(one);

        Assert.assertEquals(one, tree.getRoot().getData());
        Assert.assertEquals(zero, tree.getRoot().getLeft().getData());
        Assert.assertEquals(two, tree.getRoot().getRight().getData());
        Assert.assertNull(tree.getRoot().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getRight());

        Assert.assertEquals(3, tree.size());

        Assert.assertEquals(1, tree.getRoot().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getHeight());

        Assert.assertEquals(0, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getBalanceFactor());
    }

    /**************************************************************************************
     REMOVE
     ***********************************************************************************/
    @Test
    public void testRemoveLeaf() {
        /*
                4                  4
               / \                / \
              2   6    ---->     2   6
             / \ / \
            1  3 5  7
         */
        tree.add(four);
        tree.add(two);
        tree.add(six);
        tree.add(one);
        tree.add(three);
        tree.add(five);
        tree.add(seven);

        Assert.assertSame(one, tree.remove(new Integer(1)));
        Assert.assertSame(three, tree.remove(new Integer(3)));
        Assert.assertSame(five, tree.remove(new Integer(5)));
        Assert.assertSame(seven, tree.remove(new Integer(7)));

        Assert.assertEquals(3, tree.size());

        Assert.assertSame(four, tree.getRoot().getData());
        Assert.assertSame(two, tree.getRoot().getLeft().getData());
        Assert.assertSame(six, tree.getRoot().getRight().getData());

        Assert.assertNull(tree.getRoot().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getRight());

        Assert.assertEquals(1, tree.getRoot().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getHeight());

        Assert.assertEquals(0, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getBalanceFactor());
    }

    @Test
    public void testRemoveOneChild() {
        /*
                4                  4
               / \                / \
              2   6    ---->     3   7
              \    \
               3    7
         */
        tree.add(four);
        tree.add(two);
        tree.add(six);
        tree.add(three);
        tree.add(seven);

        Assert.assertSame(two, tree.remove(new Integer(2)));
        Assert.assertSame(six, tree.remove(new Integer(6)));


        Assert.assertEquals(3, tree.size());

        Assert.assertEquals(four, tree.getRoot().getData());
        Assert.assertEquals(three, tree.getRoot().getLeft().getData());
        Assert.assertEquals(seven, tree.getRoot().getRight().getData());

        Assert.assertNull(tree.getRoot().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getRight());

        Assert.assertEquals(1, tree.getRoot().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getHeight());

        Assert.assertEquals(0, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getBalanceFactor());
    }

    @Test
    public void testRemoveTwoChild() {
        /*
                    0                                   0
                 ---  ---                            ---  ---
                /        \                          /        \
             -5           4                       -4           7
             / \         /  \    --->             / \         / \
           -7   -2      2    7                  -7   -2      2   8
          / \   / \    / \    \                 / \  / \    / \
         -8 -6 -4  -1 1   3    8              -8 -6 -3  -1  1   3
                 \
                  -3
         */
        tree.add(zero);
        tree.add(nFive);
        tree.add(four);
        tree.add(nSeven);
        tree.add(nTwo);
        tree.add(two);
        tree.add(seven);
        tree.add(nEight);
        tree.add(nSix);
        tree.add(nFour);
        tree.add(nOne);
        tree.add(one);
        tree.add(three);
        tree.add(eight);
        tree.add(nThree);

        Assert.assertSame(nFive, tree.remove(new Integer(-5)));
        Assert.assertSame(four, tree.remove(new Integer(4)));


        Assert.assertEquals(13, tree.size());

        Assert.assertEquals(zero, tree.getRoot().getData());
        Assert.assertEquals(nFour, tree.getRoot().getLeft().getData());
        Assert.assertEquals(nSeven, tree.getRoot().getLeft().getLeft().getData());
        Assert.assertEquals(nEight, tree.getRoot().getLeft().getLeft().getLeft().getData());
        Assert.assertEquals(nSix, tree.getRoot().getLeft().getLeft().getRight().getData());
        Assert.assertEquals(nTwo, tree.getRoot().getLeft().getRight().getData());
        Assert.assertEquals(nThree, tree.getRoot().getLeft().getRight().getLeft().getData());
        Assert.assertEquals(nOne, tree.getRoot().getLeft().getRight().getRight().getData());
        Assert.assertEquals(seven, tree.getRoot().getRight().getData());
        Assert.assertEquals(two, tree.getRoot().getRight().getLeft().getData());
        Assert.assertEquals(one, tree.getRoot().getRight().getLeft().getLeft().getData());
        Assert.assertEquals(three, tree.getRoot().getRight().getLeft().getRight().getData());
        Assert.assertEquals(eight, tree.getRoot().getRight().getRight().getData());


        Assert.assertNull(tree.getRoot().getLeft().getLeft().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getLeft().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getLeft().getLeft().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getLeft().getRight().getRight());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getRight().getRight());

        Assert.assertNull(tree.getRoot().getRight().getLeft().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getRight().getRight());
        Assert.assertNull(tree.getRoot().getRight().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getRight().getRight());


        Assert.assertEquals(3, tree.getRoot().getHeight());
        Assert.assertEquals(2, tree.getRoot().getLeft().getHeight());
        Assert.assertEquals(1, tree.getRoot().getLeft().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getRight().getHeight());
        Assert.assertEquals(1, tree.getRoot().getLeft().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getRight().getHeight());
        Assert.assertEquals(2, tree.getRoot().getRight().getHeight());
        Assert.assertEquals(1, tree.getRoot().getRight().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getRight().getHeight());

        Assert.assertEquals(0, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getRight().getBalanceFactor());
        Assert.assertEquals(1, tree.getRoot().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getRight().getBalanceFactor());
    }


    @Test
    public void testRemoveRoot() {
        /*
           100 <- remove
         */
        Integer myInt = 100;
        tree.add(myInt);
        Assert.assertSame(myInt, tree.remove(new Integer(100)));
        Assert.assertNull(tree.getRoot());
        Assert.assertEquals(0, tree.size());
    }

    @Test
    public void testRemoveRootOneChild() {
        /*
            1 <-remove
           /
          0
         */
        tree.add(one);
        tree.add(zero);

        Assert.assertSame(one, tree.remove(new Integer(one)));
        Assert.assertEquals(zero, tree.getRoot().getData());
        Assert.assertNull(tree.getRoot().getLeft());
        Assert.assertNull(tree.getRoot().getRight());

        Assert.assertEquals(1, tree.size());
        Assert.assertEquals(0, tree.getRoot().getHeight());
        Assert.assertEquals(0, tree.getRoot().getBalanceFactor());
    }

    @Test
    public void testRemoveRootTwoChildren() {
        /*
            1 <-remove
           / \
          0   2
         */
        tree.add(one);
        tree.add(two);
        tree.add(zero);

        Assert.assertSame(one, tree.remove(new Integer(one)));
        Assert.assertEquals(two, tree.getRoot().getData());
        Assert.assertNull(tree.getRoot().getRight());
        Assert.assertEquals(zero, tree.getRoot().getLeft().getData());
        Assert.assertNull(tree.getRoot().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight());

        Assert.assertEquals(2, tree.size());
        Assert.assertEquals(1, tree.getRoot().getHeight());
        Assert.assertEquals(1, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getBalanceFactor());
    }

    /**************************************************************************************
     ROTATIONS WITH CHILDREN
     ***********************************************************************************/
    @Test
    public void testLeftRotate() {
        /*
                1
               /  \
            -1     3
           /      /  \
         -2      2    6
                     / \
                    5   7
        ----> delete(-2)
                1
               /  \
            -1     3
                  /  \
                 2    6
                     / \
                    5   7
        ---->
                    1   3
                  /  \ / \
                -1    2   6
                         / \
                        5   7
        ---->
                      3
                    /   \
                   1     6
                  / \   / \
                -1   2 5   7
        */
        tree.add(one);
        tree.add(nOne);
        tree.add(three);
        tree.add(nTwo);
        tree.add(two);
        tree.add(six);
        tree.add(five);
        tree.add(seven);

        Assert.assertSame(nTwo, tree.remove(new Integer(-2)));

        Assert.assertEquals(three, tree.getRoot().getData());
        Assert.assertEquals(one, tree.getRoot().getLeft().getData());
        Assert.assertEquals(six, tree.getRoot().getRight().getData());
        Assert.assertEquals(nOne, tree.getRoot().getLeft().getLeft().getData());
        Assert.assertEquals(two, tree.getRoot().getLeft().getRight().getData());
        Assert.assertEquals(five, tree.getRoot().getRight().getLeft().getData());
        Assert.assertEquals(seven, tree.getRoot().getRight().getRight().getData());

        Assert.assertNull(tree.getRoot().getLeft().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getRight());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getRight().getRight().getRight());

        Assert.assertEquals(7, tree.size());
        Assert.assertEquals(2, tree.getRoot().getHeight());
        Assert.assertEquals(1, tree.getRoot().getLeft().getHeight());
        Assert.assertEquals(1, tree.getRoot().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getRight().getHeight());

        Assert.assertEquals(0, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getRight().getBalanceFactor());
    }

    @Test
    public void testRightRotate() {
        /*
                4
               / \
              2   5
             / \   \
            0   3   6
           / \
         -1   1
        ----> delete(6)
                4
               / \
              2   5
             / \
            0   3
           / \
         -1   1
        ---->
                  2    4
                 / \  / \
                0   3    5
               / \
             -1   1
        ---->
                 2
               /  \
              0     4
             / \   / \
           -1   1 3   5
         */
        tree.add(four);
        tree.add(two);
        tree.add(five);
        tree.add(zero);
        tree.add(three);
        tree.add(six);
        tree.add(nOne);
        tree.add(one);

        Assert.assertSame(six, tree.remove(new Integer(six)));

        Assert.assertEquals(two, tree.getRoot().getData());
        Assert.assertEquals(zero, tree.getRoot().getLeft().getData());
        Assert.assertEquals(four, tree.getRoot().getRight().getData());
        Assert.assertEquals(nOne, tree.getRoot().getLeft().getLeft().getData());
        Assert.assertEquals(one, tree.getRoot().getLeft().getRight().getData());
        Assert.assertEquals(three, tree.getRoot().getRight().getLeft().getData());
        Assert.assertEquals(five, tree.getRoot().getRight().getRight().getData());

        Assert.assertNull(tree.getRoot().getLeft().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getRight());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getRight().getRight().getRight());

        Assert.assertEquals(7, tree.size());
        Assert.assertEquals(2, tree.getRoot().getHeight());
        Assert.assertEquals(1, tree.getRoot().getLeft().getHeight());
        Assert.assertEquals(1, tree.getRoot().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getRight().getHeight());

        Assert.assertEquals(0, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getRight().getBalanceFactor());
    }

    @Test
    public void testLeftRightRotate() {
        /*
                 4
                / \
               0   5
              / \   \
            -1   2   6
                / \
               1   3
        ----> delete(6)
                 4
                / \
               0   5
              / \
            -1   2
                / \
               1   3
        ---->
                 4
                / \
           0   2   5
          / \ / \
        -1   1   3
        ---->
                4
               / \
              2   5
             / \
            0   3
           / \
         -1   1
        ---->
                  2    4
                 / \  / \
                0   3    5
               / \
             -1   1
        ---->
                 2
               /  \
              0     4
             / \   / \
           -1   1 3   5
         */
        tree.add(four);
        tree.add(zero);
        tree.add(five);
        tree.add(nOne);
        tree.add(two);
        tree.add(six);
        tree.add(one);
        tree.add(three);

        Assert.assertSame(six, tree.remove(new Integer(six)));

        Assert.assertEquals(two, tree.getRoot().getData());
        Assert.assertEquals(zero, tree.getRoot().getLeft().getData());
        Assert.assertEquals(four, tree.getRoot().getRight().getData());
        Assert.assertEquals(nOne, tree.getRoot().getLeft().getLeft().getData());
        Assert.assertEquals(one, tree.getRoot().getLeft().getRight().getData());
        Assert.assertEquals(three, tree.getRoot().getRight().getLeft().getData());
        Assert.assertEquals(five, tree.getRoot().getRight().getRight().getData());

        Assert.assertNull(tree.getRoot().getLeft().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getRight());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getRight().getRight().getRight());

        Assert.assertEquals(7, tree.size());
        Assert.assertEquals(2, tree.getRoot().getHeight());
        Assert.assertEquals(1, tree.getRoot().getLeft().getHeight());
        Assert.assertEquals(1, tree.getRoot().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getRight().getHeight());

        Assert.assertEquals(0, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getRight().getBalanceFactor());
    }

    @Test
    public void testRightLeftRotate() {
        /*
            1
           / \
         -1   6
        /    / \
       -2   3   7
           / \
          2   5
       ----> delete(-2)
            1
           / \
         -1   6
             / \
            3   7
           / \
          2   5
       ---->
            1
           / \
         -1    3   6
              / \ / \
             2   5   7
       ---->
            1
           / \
         -1   3
             / \
            2   6
               / \
              5   7
        ---->
            1   3
          /  \ / \
        -1    2   6
                 / \
                5   7
        ---->
              3
            /   \
           1     6
          / \   / \
        -1   2 5   7
        */
        tree.add(one);
        tree.add(nOne);
        tree.add(six);
        tree.add(nTwo);
        tree.add(three);
        tree.add(seven);
        tree.add(two);
        tree.add(five);


        Assert.assertSame(nTwo, tree.remove(new Integer(-2)));

        Assert.assertEquals(three, tree.getRoot().getData());
        Assert.assertEquals(one, tree.getRoot().getLeft().getData());
        Assert.assertEquals(six, tree.getRoot().getRight().getData());
        Assert.assertEquals(nOne, tree.getRoot().getLeft().getLeft().getData());
        Assert.assertEquals(two, tree.getRoot().getLeft().getRight().getData());
        Assert.assertEquals(five, tree.getRoot().getRight().getLeft().getData());
        Assert.assertEquals(seven, tree.getRoot().getRight().getRight().getData());

        Assert.assertNull(tree.getRoot().getLeft().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getRight());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getRight().getRight().getRight());

        Assert.assertEquals(7, tree.size());
        Assert.assertEquals(2, tree.getRoot().getHeight());
        Assert.assertEquals(1, tree.getRoot().getLeft().getHeight());
        Assert.assertEquals(1, tree.getRoot().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getRight().getHeight());

        Assert.assertEquals(0, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getRight().getBalanceFactor());
    }

    /**************************************************************************************
     OTHER METHODS TESTS
     ***********************************************************************************/
    @Test
    public void testGet() {
        /*
                    2
                 ---  ---
                /        \
             -2           6
             / \         / \
           -5    0      4     8
          / \   / \    / \   / \
         -6 -4 -1  1  3   5 7   9
         */
        tree.add(two);
        tree.add(nTwo);
        tree.add(six);
        tree.add(nFive);
        tree.add(zero);
        tree.add(four);
        tree.add(eight);
        tree.add(nSix);
        tree.add(nFour);
        tree.add(nOne);
        tree.add(one);
        tree.add(three);
        tree.add(five);
        tree.add(seven);
        tree.add(nine);

        Assert.assertSame(two, tree.get(new Integer(2)));
        Assert.assertSame(nTwo, tree.get(new Integer(-2)));
        Assert.assertSame(nFive, tree.get(new Integer(-5)));
        Assert.assertSame(nSix, tree.get(new Integer(-6)));
        Assert.assertSame(nFour, tree.get(new Integer(-4)));
        Assert.assertSame(zero, tree.get(new Integer(0)));
        Assert.assertSame(nOne, tree.get(new Integer(-1)));
        Assert.assertSame(one, tree.get(new Integer(1)));
        Assert.assertSame(six, tree.get(new Integer(6)));
        Assert.assertSame(four, tree.get(new Integer(4)));
        Assert.assertSame(three, tree.get(new Integer(3)));
        Assert.assertSame(five, tree.get(new Integer(5)));
        Assert.assertSame(eight, tree.get(new Integer(8)));
        Assert.assertSame(seven, tree.get(new Integer(7)));
        Assert.assertSame(nine, tree.get(new Integer(9)));
    }

    @Test
    public void testContains() {
        /*
                    2
                 ---  ---
                /        \
             -2           6
             / \         / \
           -5    0      4     8
          / \   / \    / \   / \
         -6 -4 -1  1  3   5 7   9
         */
        tree.add(two);
        tree.add(nTwo);
        tree.add(six);
        tree.add(nFive);
        tree.add(zero);
        tree.add(four);
        tree.add(eight);
        tree.add(nSix);
        tree.add(nFour);
        tree.add(nOne);
        tree.add(one);
        tree.add(three);
        tree.add(five);
        tree.add(seven);
        tree.add(nine);

        Assert.assertTrue(tree.contains(new Integer(2)));
        Assert.assertTrue(tree.contains(new Integer(-2)));
        Assert.assertTrue(tree.contains(new Integer(-5)));
        Assert.assertTrue(tree.contains(new Integer(-6)));
        Assert.assertTrue(tree.contains(new Integer(-4)));
        Assert.assertTrue(tree.contains(new Integer(0)));
        Assert.assertTrue(tree.contains(new Integer(-1)));
        Assert.assertTrue(tree.contains(new Integer(6)));
        Assert.assertTrue(tree.contains(new Integer(4)));
        Assert.assertTrue(tree.contains(new Integer(3)));
        Assert.assertTrue(tree.contains(new Integer(5)));
        Assert.assertTrue(tree.contains(new Integer(8)));
        Assert.assertTrue(tree.contains(new Integer(7)));
        Assert.assertTrue(tree.contains(new Integer(9)));

        Assert.assertFalse(tree.contains(new Integer(-10)));
        Assert.assertFalse(tree.contains(new Integer(-12)));
        Assert.assertFalse(tree.contains(new Integer(-15)));
        Assert.assertFalse(tree.contains(new Integer(16)));
        Assert.assertFalse(tree.contains(new Integer(14)));
        Assert.assertFalse(tree.contains(new Integer(12)));
        Assert.assertFalse(tree.contains(new Integer(12)));
    }

    @Test
    public void testSize() {
        for (int i = 0; i < 100; i++) {
            tree.add(Integer.valueOf(i * (int) Math.pow(-1, i)));
            Assert.assertEquals(i + 1, tree.size());
        }
    }

    @Test
    public void testHeight() {
        //remember height should be an O(1) operation!! think about where it might be stored
        /*
                    0
                 ---  ---
                /        \
             -5           4
             / \         /  \
           -7   -2      2    7
          / \   / \    / \    \
         -8 -6 -4  -1 1   3    8
                 \
                  -3
         */
        Assert.assertEquals(-1, tree.height());
        tree.add(zero);
        Assert.assertEquals(0, tree.height());
        tree.add(nFive);
        Assert.assertEquals(1, tree.height());
        tree.add(four);
        Assert.assertEquals(1, tree.height());
        tree.add(nSeven);
        Assert.assertEquals(2, tree.height());
        tree.add(nTwo);
        Assert.assertEquals(2, tree.height());
        tree.add(two);
        Assert.assertEquals(2, tree.height());
        tree.add(seven);
        Assert.assertEquals(2, tree.height());
        tree.add(nEight);
        Assert.assertEquals(3, tree.height());
        tree.add(nSix);
        Assert.assertEquals(3, tree.height());
        tree.add(nFour);
        Assert.assertEquals(3, tree.height());
        tree.add(nOne);
        Assert.assertEquals(3, tree.height());
        tree.add(one);
        Assert.assertEquals(3, tree.height());
        tree.add(three);
        Assert.assertEquals(3, tree.height());
        tree.add(eight);
        Assert.assertEquals(3, tree.height());
        tree.add(nThree);
        Assert.assertEquals(4, tree.height());
    }

    @Test
    public void testClear() {
        //remember this should be O(1)
        tree.add(two);
        tree.add(nTwo);
        tree.add(six);
        tree.add(nFive);
        tree.add(zero);
        tree.add(four);
        tree.add(eight);
        tree.add(nSix);
        tree.add(nFour);
        tree.add(nOne);
        tree.add(one);
        tree.add(three);
        tree.add(five);
        tree.add(seven);
        tree.add(nine);

        tree.clear();
        Assert.assertNull(tree.getRoot());
        Assert.assertEquals(0, tree.size());
        Assert.assertEquals(-1, tree.height());
    }

    @Test
    public void testCollectionConstructor() {
        /*
                4
               / \
              2   6
             / \ / \
            1  3 5  7
         */
        ArrayList<Integer> myAL = new ArrayList<Integer>();
        myAL.add(four);
        myAL.add(two);
        myAL.add(six);
        myAL.add(one);
        myAL.add(three);
        myAL.add(five);
        myAL.add(seven);

        tree = new AVL<Integer>(myAL);

        Assert.assertEquals(four, tree.getRoot().getData());
        Assert.assertEquals(two, tree.getRoot().getLeft().getData());
        Assert.assertEquals(six, tree.getRoot().getRight().getData());
        Assert.assertEquals(one, tree.getRoot().getLeft().getLeft().getData());
        Assert.assertEquals(three, tree.getRoot().getLeft().getRight().getData());
        Assert.assertEquals(five, tree.getRoot().getRight().getLeft().getData());
        Assert.assertEquals(seven, tree.getRoot().getRight().getRight().getData());

        Assert.assertNull(tree.getRoot().getLeft().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getRight());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getRight().getRight().getRight());

        Assert.assertEquals(7, tree.size());
        Assert.assertEquals(2, tree.getRoot().getHeight());
        Assert.assertEquals(1, tree.getRoot().getLeft().getHeight());
        Assert.assertEquals(1, tree.getRoot().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getHeight());
        Assert.assertEquals(0, tree.getRoot().getRight().getRight().getHeight());

        Assert.assertEquals(0, tree.getRoot().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getLeft().getRight().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getLeft().getBalanceFactor());
        Assert.assertEquals(0, tree.getRoot().getRight().getRight().getBalanceFactor());

    }
    /**************************************************************************************
     DEEPEST BRANCHES + SORTED IN BETWEEN
     ***********************************************************************************/
    //TA Provided tests do a pretty good job on these, so I'm just grabbing the small edge cases
    @Test
    public void testDeepestBranchesEmpty() {
        Assert.assertArrayEquals(new Integer[0], tree.deepestBranches().toArray());
    }

    @Test
    public void testDeepestBranchesRoot() {
        tree.add(one);
        Integer[] expected = {one};
        Assert.assertArrayEquals(expected, tree.deepestBranches().toArray());
    }

    @Test
    public void testDeepestBranchesFull() {
        tree.add(one);
        tree.add(zero);
        tree.add(two);
        Integer[] expected = {one, zero, two};
        Assert.assertArrayEquals(expected, tree.deepestBranches().toArray());
    }

    /**************************************************************************************
     SORTED IN BETWEEN
     ***********************************************************************************/
    //TA Provided tests do a pretty good job on these, so I'm just grabbing the small edge cases
    @Test
    public void testSortedInBetweenEmpty() {
        Assert.assertArrayEquals(new Integer[0], tree.sortedInBetween(0, 100).toArray());
    }

    @Test
    public void testSortedInBetweenEqualBounds() {
        tree.add(one);
        Assert.assertArrayEquals(new Integer[0], tree.sortedInBetween(1, 1).toArray());
    }

    @Test
    public void testSortedInBetweenLowerBound() {
        tree.add(one);
        Assert.assertArrayEquals(new Integer[0], tree.sortedInBetween(1, 2).toArray());
    }

    @Test
    public void testSortedInBetweenUpperBound() {
        tree.add(one);
        Assert.assertArrayEquals(new Integer[0], tree.sortedInBetween(0, 1).toArray());
    }

    @Test
    public void testSortedInBetweenLow() {
        tree.add(zero);
        tree.add(nSix);
        tree.add(six);
        tree.add(nNine);
        tree.add(nThree);
        tree.add(three);
        tree.add(nine);
        Assert.assertArrayEquals(new Integer[0], tree.sortedInBetween(-299, -10).toArray());
    }

    @Test
    public void testSortedInBetweenHigh() {
        tree.add(zero);
        tree.add(nSix);
        tree.add(six);
        tree.add(nNine);
        tree.add(nThree);
        tree.add(three);
        tree.add(nine);
        Assert.assertArrayEquals(new Integer[0], tree.sortedInBetween(10, 299).toArray());
    }

    @Test
    public void testSortedInBetweenRoot() {
        tree.add(one);
        Integer[] expected = {one};
        Assert.assertArrayEquals(expected, tree.sortedInBetween(0, 2).toArray());
    }

    @Test
    public void testSortedInBetweenAll() {
        tree.add(zero);
        tree.add(nSix);
        tree.add(six);
        tree.add(nNine);
        tree.add(nThree);
        tree.add(three);
        tree.add(nine);
        Integer[] expected = {nNine, nSix, nThree, zero, three, six, nine};
        Assert.assertArrayEquals(expected, tree.sortedInBetween(-10, 10).toArray());
    }

    @Test
    public void sortedInBetweenExampleCases() {
        tree.add(10);
        tree.add(5);
        tree.add(15);
        tree.add(2);
        tree.add(7);
        tree.add(13);
        tree.add(20);
        tree.add(1);
        tree.add(4);
        tree.add(6);
        tree.add(8);
        tree.add(14);
        tree.add(17);
        tree.add(25);
        tree.add(0);
        tree.add(9);
        tree.add(30);

        List<Integer> expected = new ArrayList<>();
        expected.add(8);
        expected.add(9);
        expected.add(10);
        expected.add(13);

        Assert.assertEquals(expected, tree.sortedInBetween(new Integer(7), new Integer(14)));

        expected.clear();
        expected.add(4);
        expected.add(5);
        expected.add(6);
        expected.add(7);
        Assert.assertEquals(expected, tree.sortedInBetween(new Integer(3), new Integer(8)));

        expected.clear();
        Assert.assertEquals(expected, tree.sortedInBetween(new Integer(8), new Integer(8)));
    }

    /**************************************************************************************
     EXPECTED EXCEPTIONS
     ***********************************************************************************/
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullException() {
        AVL<Integer> diffTree = new AVL<>(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullContainedException() {
        LinkedList<Integer> myLL = new LinkedList<Integer>();
        myLL.add(2);
        myLL.add(15);
        myLL.add(-24354);
        myLL.add(23);
        myLL.add(null);
        myLL.add(123);
        AVL<Integer> diffTree = new AVL<>(myLL);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testAddException() {
        try {
            tree.add(null);
        } catch (Exception e) {
            Assert.assertEquals(0, tree.size());
        }
        tree.add(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNullException() {
        tree.add(one);
        tree.add(two);
        tree.add(three);
        try {
            tree.remove(null);
        } catch (Exception e) {
            Assert.assertEquals(3, tree.size());
        }
        tree.remove(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveNotFoundException() {
        tree.add(one);
        tree.add(two);
        tree.add(three);
        try {
            tree.remove(zero);
        } catch (Exception e) {
            Assert.assertEquals(3, tree.size());
        }
        tree.remove(zero);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetNullException() {
        tree.get(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetNotFoundException() {
        tree.get(zero);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testContainsException() {
        tree.contains(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSortedLeftNullException() {
        tree.sortedInBetween(null, zero);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSortedRightNullException() {
        tree.sortedInBetween(zero, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSortedBothNullException() {
        tree.sortedInBetween(null, null);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testSortedIllegalArgumentOrder() {
        tree.sortedInBetween(1, -1);
    }
}