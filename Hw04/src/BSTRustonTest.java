import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.NoSuchElementException;


/**
 * This set of tests is designed to SUPPLEMENT the TA provided JUnits.
 * I would highly encourage you to run and pass those tests.
 *
 *  Designed to Test:
 *  1. Add Methods
 *  2. Remove Methods
 *  3. Get Method
 *  4. Contains Method
 *  5. Clear Method
 *  6. Height Method
 *  7. All Expected Exception
 *
 *  Not Designed to Test:
 *  1. Constructors (including collections constructor)
 *  2. Traversal Methods
 *  3. kLargest
 *  4. Efficiency
 *  ^^ These are all tested well enough in the TA provided JUnits that it would be redundant
 *
 *  NOTE: These tests check for edge cases if you implemented your methods recursively. If you used look-ahead
 *  instead I truly don't know what edge cases exist, and these tests were not designed for those.
 *
 *  Hope midterm 1 went well for everyone!!
 *
 *  @author Ruston Shome
 *  @version 1.0
 */
public class BSTRustonTest {
    private BST<Integer> tree;
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
     * Check the indices on any For loops
     */
    @Rule
    public Timeout timeout = Timeout.seconds(5);

    @Before
    public void setup() {
        tree = new BST<>();
    }


    /**************************************************************************************
     ADD METHODS TESTS
     ***********************************************************************************/

    @Test
    public void testAddToEmpty() {
        /*
           100
         */
        Integer myInt = 100;
        tree.add(myInt);
        Assert.assertEquals(myInt, tree.getRoot().getData());
        Assert.assertNull(tree.getRoot().getLeft());
        Assert.assertNull(tree.getRoot().getRight());
    }

    @Test
    public void testAddLevelOne() {
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
    }

    @Test
    public void testAddLevelTwo() {
        /*
                4
               / \
              2   6
             / \  / \
            1  3 5  7
         */
        tree.add(four);
        tree.add(two);
        tree.add(three);
        tree.add(six);
        tree.add(five);
        tree.add(one);
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
    }

    @Test
    public void testAddLevelThree() {
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
        tree.add(nFive);
        tree.add(nSix);
        tree.add(nFour);
        tree.add(zero);
        tree.add(nOne);
        tree.add(one);
        tree.add(six);
        tree.add(four);
        tree.add(three);
        tree.add(five);
        tree.add(eight);
        tree.add(seven);
        tree.add(nine);

        Assert.assertEquals(two, tree.getRoot().getData());
        Assert.assertEquals(nTwo, tree.getRoot().getLeft().getData());
        Assert.assertEquals(nFive, tree.getRoot().getLeft().getLeft().getData());
        Assert.assertEquals(nSix, tree.getRoot().getLeft().getLeft().getLeft().getData());
        Assert.assertEquals(nFour, tree.getRoot().getLeft().getLeft().getRight().getData());
        Assert.assertEquals(zero, tree.getRoot().getLeft().getRight().getData());
        Assert.assertEquals(nOne, tree.getRoot().getLeft().getRight().getLeft().getData());
        Assert.assertEquals(one, tree.getRoot().getLeft().getRight().getRight().getData());
        Assert.assertEquals(six, tree.getRoot().getRight().getData());
        Assert.assertEquals(four, tree.getRoot().getRight().getLeft().getData());
        Assert.assertEquals(three, tree.getRoot().getRight().getLeft().getLeft().getData());
        Assert.assertEquals(five, tree.getRoot().getRight().getLeft().getRight().getData());
        Assert.assertEquals(eight, tree.getRoot().getRight().getRight().getData());
        Assert.assertEquals(seven, tree.getRoot().getRight().getRight().getLeft().getData());
        Assert.assertEquals(nine, tree.getRoot().getRight().getRight().getRight().getData());


        Assert.assertNull(tree.getRoot().getLeft().getLeft().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getLeft().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getRight().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getRight().getRight().getLeft());

        Assert.assertNull(tree.getRoot().getLeft().getLeft().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getLeft().getLeft().getRight().getRight());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getRight().getRight());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getRight().getRight());
        Assert.assertNull(tree.getRoot().getRight().getRight().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getRight().getRight().getRight().getRight());
    }

    @Test
    public void testAddJagged() {
        /*
                9
               /
             -7
               \
                8
               /
             -5
            /  \
          -6    7
               /
             -4
               \
                4
               / \
             -3   6
              \   /
               3 5
         */
        tree.add(nine);
        tree.add(nSeven);
        tree.add(eight);
        tree.add(nFive);
        tree.add(nSix);
        tree.add(seven);
        tree.add(nFour);
        tree.add(four);
        tree.add(six);
        tree.add(five);
        tree.add(nThree);
        tree.add(three);

        BSTNode<Integer> curr = tree.getRoot();
        Assert.assertNotNull(curr);
        Assert.assertEquals(nine, curr.getData());
        Assert.assertNull(curr.getRight());
        Assert.assertNotNull(curr.getLeft());

        curr = curr.getLeft();
        Assert.assertEquals(nSeven, curr.getData());
        Assert.assertNull(curr.getLeft());
        Assert.assertNotNull(curr.getRight());

        curr = curr.getRight();
        Assert.assertEquals(eight, curr.getData());
        Assert.assertNull(curr.getRight());
        Assert.assertNotNull(curr.getLeft());

        curr = curr.getLeft();
        Assert.assertEquals(nFive, curr.getData());
        Assert.assertNotNull(curr.getLeft());
        Assert.assertEquals(nSix, curr.getLeft().getData());
        Assert.assertNotNull(curr.getRight());

        curr = curr.getRight();
        Assert.assertEquals(seven, curr.getData());
        Assert.assertNull(curr.getRight());
        Assert.assertNotNull(curr.getLeft());

        curr = curr.getLeft();
        Assert.assertEquals(nFour, curr.getData());
        Assert.assertNull(curr.getLeft());
        Assert.assertNotNull(curr.getRight());

        curr = curr.getRight();
        Assert.assertEquals(four, curr.getData());
        Assert.assertNotNull(curr.getRight());
        Assert.assertEquals(six, curr.getRight().getData());
        Assert.assertNull(curr.getRight().getRight());
        Assert.assertNotNull(curr.getRight().getLeft());
        Assert.assertEquals(five, curr.getRight().getLeft().getData());
        Assert.assertNull(curr.getRight().getLeft().getLeft());
        Assert.assertNull(curr.getRight().getLeft().getRight());
        Assert.assertNotNull(curr.getLeft());

        curr = curr.getLeft();
        Assert.assertEquals(nThree, curr.getData());
        Assert.assertNull(curr.getLeft());
        Assert.assertNotNull(curr.getRight());

        curr = curr.getRight();
        Assert.assertEquals(three, curr.getData());
        Assert.assertNull(curr.getRight());
        Assert.assertNull(curr.getLeft());
    }

    @Test
    public void testAddDuplicates() {
        /*
                4
               / \
              2   6
             / \  / \
            1  3 5  7
         */
        tree.add(four);
        tree.add(two);
        tree.add(three);
        tree.add(six);
        tree.add(five);
        tree.add(one);
        tree.add(seven);

        tree.add(four);
        tree.add(two);
        tree.add(three);
        tree.add(six);
        tree.add(five);
        tree.add(one);
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
    }

    @Test
    public void testAddLeftDegenerate() {
        /*

                     -1
                    /
                   -2
                  /
                 -3
                /
               -4
              /
             -5
            /
           -6
          /
         -7
         */
        tree.add(nOne);
        tree.add(nTwo);
        tree.add(nThree);
        tree.add(nFour);
        tree.add(nFive);
        tree.add(nSix);
        tree.add(nSeven);

        BSTNode<Integer> curr = tree.getRoot();
        Assert.assertNotNull(curr);
        Assert.assertEquals(nOne, curr.getData());
        Assert.assertNull(curr.getRight());
        Assert.assertNotNull(curr.getLeft());
        curr = curr.getLeft();

        Assert.assertEquals(nTwo, curr.getData());
        Assert.assertNull(curr.getRight());
        Assert.assertNotNull(curr.getLeft());
        curr = curr.getLeft();

        Assert.assertEquals(nThree, curr.getData());
        Assert.assertNull(curr.getRight());
        Assert.assertNotNull(curr.getLeft());
        curr = curr.getLeft();

        Assert.assertEquals(nFour, curr.getData());
        Assert.assertNull(curr.getRight());
        Assert.assertNotNull(curr.getLeft());
        curr = curr.getLeft();

        Assert.assertEquals(nFive, curr.getData());
        Assert.assertNull(curr.getRight());
        Assert.assertNotNull(curr.getLeft());
        curr = curr.getLeft();

        Assert.assertEquals(nSix, curr.getData());
        Assert.assertNull(curr.getRight());
        Assert.assertNotNull(curr.getLeft());
        curr = curr.getLeft();

        Assert.assertEquals(nSeven, curr.getData());
        Assert.assertNull(curr.getRight());
        Assert.assertNull(curr.getLeft());
    }

    @Test
    public void testAddRightDegenerate() {
        /*
          1
           \
            2
             \
              3
               \
                4
                 \
                  5
                   \
                    6
                     \
                      7
         */
        tree.add(one);
        tree.add(two);
        tree.add(three);
        tree.add(four);
        tree.add(five);
        tree.add(six);
        tree.add(seven);

        BSTNode<Integer> curr = tree.getRoot();
        Assert.assertNotNull(curr);
        Assert.assertEquals(one, curr.getData());
        Assert.assertNull(curr.getLeft());
        Assert.assertNotNull(curr.getRight());
        curr = curr.getRight();

        Assert.assertEquals(two, curr.getData());
        Assert.assertNull(curr.getLeft());
        Assert.assertNotNull(curr.getRight());
        curr = curr.getRight();

        Assert.assertEquals(three, curr.getData());
        Assert.assertNull(curr.getLeft());
        Assert.assertNotNull(curr.getRight());
        curr = curr.getRight();

        Assert.assertEquals(four, curr.getData());
        Assert.assertNull(curr.getLeft());
        Assert.assertNotNull(curr.getRight());
        curr = curr.getRight();

        Assert.assertEquals(five, curr.getData());
        Assert.assertNull(curr.getLeft());
        Assert.assertNotNull(curr.getRight());
        curr = curr.getRight();

        Assert.assertEquals(six, curr.getData());
        Assert.assertNull(curr.getLeft());
        Assert.assertNotNull(curr.getRight());
        curr = curr.getRight();

        Assert.assertEquals(seven, curr.getData());
        Assert.assertNull(curr.getLeft());
        Assert.assertNull(curr.getRight());
    }


    /**************************************************************************************
     REMOVE METHOD TESTS
     ***********************************************************************************/
    @Test
    public void tesRemoveLeaf() {
        /*
                4                  4
               / \                / \
              2   6    ---->     2   6
             / \  / \
            1  3 5  7
         */
        tree.add(four);
        tree.add(two);
        tree.add(three);
        tree.add(six);
        tree.add(five);
        tree.add(one);
        tree.add(seven);

        Assert.assertSame(three, tree.remove(new Integer(3)));
        Assert.assertSame(five, tree.remove(new Integer(5)));
        Assert.assertSame(one, tree.remove(new Integer(1)));
        Assert.assertSame(seven, tree.remove(new Integer(7)));

        Assert.assertEquals(four, tree.getRoot().getData());
        Assert.assertEquals(two, tree.getRoot().getLeft().getData());
        Assert.assertEquals(six, tree.getRoot().getRight().getData());

        Assert.assertNull(tree.getRoot().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getRight());
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
        tree.add(three);
        tree.add(six);
        tree.add(seven);

        Assert.assertSame(two, tree.remove(new Integer(2)));
        Assert.assertSame(six, tree.remove(new Integer(6)));


        Assert.assertEquals(four, tree.getRoot().getData());
        Assert.assertEquals(three, tree.getRoot().getLeft().getData());
        Assert.assertEquals(seven, tree.getRoot().getRight().getData());

        Assert.assertNull(tree.getRoot().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getRight());
    }

    @Test
    public void testRemoveTwoChild() {
        /*
                    0                                   0
                 ---  ---                            ---  ---
                /        \                          /        \
             -4           4                      -3           7
             / \         /  \    --->            / \         /
           -6   -1      2    7                  -6   -1      2
          / \   /      / \                     / \   /      / \
         -7 -5 -3     1   3                  -7 -5 -2      1   3
                 \
                  -2
         */
        tree.add(zero);
        tree.add(nFour);
        tree.add(nSix);
        tree.add(nSeven);
        tree.add(nFive);
        tree.add(nOne);
        tree.add(nThree);
        tree.add(nTwo);
        tree.add(four);
        tree.add(two);
        tree.add(one);
        tree.add(three);
        tree.add(seven);

        Assert.assertSame(nFour, tree.remove(new Integer(-4)));
        Assert.assertSame(four, tree.remove(new Integer(4)));


        Assert.assertEquals(zero, tree.getRoot().getData());
        Assert.assertEquals(nThree, tree.getRoot().getLeft().getData());
        Assert.assertEquals(nSix, tree.getRoot().getLeft().getLeft().getData());
        Assert.assertEquals(nSeven, tree.getRoot().getLeft().getLeft().getLeft().getData());
        Assert.assertEquals(nFive, tree.getRoot().getLeft().getLeft().getRight().getData());
        Assert.assertEquals(nOne, tree.getRoot().getLeft().getRight().getData());
        Assert.assertEquals(nTwo, tree.getRoot().getLeft().getRight().getLeft().getData());
        Assert.assertEquals(seven, tree.getRoot().getRight().getData());
        Assert.assertEquals(two, tree.getRoot().getRight().getLeft().getData());
        Assert.assertEquals(one, tree.getRoot().getRight().getLeft().getLeft().getData());
        Assert.assertEquals(three, tree.getRoot().getRight().getLeft().getRight().getData());


        Assert.assertNull(tree.getRoot().getLeft().getLeft().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getLeft().getLeft().getRight());

        Assert.assertNull(tree.getRoot().getLeft().getLeft().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getLeft().getRight().getRight());

        Assert.assertNull(tree.getRoot().getLeft().getRight().getRight());

        Assert.assertNull(tree.getRoot().getLeft().getRight().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getLeft().getRight().getLeft().getRight());

        Assert.assertNull(tree.getRoot().getRight().getRight());

        Assert.assertNull(tree.getRoot().getRight().getLeft().getLeft().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getLeft().getRight());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getRight().getLeft());
        Assert.assertNull(tree.getRoot().getRight().getLeft().getRight().getRight());
    }

    @Test
    public void testRemoveJagged() {
        /*
                9
               /
             -7
               \
                8 <- remove
               /
             -5
            /  \
    remove:-6    7
               /
             -4
               \
                4 <-remove
               / \
             -3   6
              \   /
               3 5
         */
        tree.add(nine);
        tree.add(nSeven);
        tree.add(eight);
        tree.add(nFive);
        tree.add(nSix);
        tree.add(seven);
        tree.add(nFour);
        tree.add(four);
        tree.add(six);
        tree.add(five);
        tree.add(nThree);
        tree.add(three);

        tree.remove(eight);
        tree.remove(nSix);
        tree.remove(four);

        BSTNode<Integer> curr = tree.getRoot();
        Assert.assertNotNull(curr);
        Assert.assertEquals(nine, curr.getData());
        Assert.assertNull(curr.getRight());
        Assert.assertNotNull(curr.getLeft());

        curr = curr.getLeft();
        Assert.assertEquals(nSeven, curr.getData());
        Assert.assertNull(curr.getLeft());
        Assert.assertNotNull(curr.getRight());

        curr = curr.getRight();
        Assert.assertEquals(nFive, curr.getData());
        Assert.assertNull(curr.getLeft());
        Assert.assertNotNull(curr.getRight());

        curr = curr.getRight();
        Assert.assertEquals(seven, curr.getData());
        Assert.assertNull(curr.getRight());
        Assert.assertNotNull(curr.getLeft());

        curr = curr.getLeft();
        Assert.assertEquals(nFour, curr.getData());
        Assert.assertNull(curr.getLeft());
        Assert.assertNotNull(curr.getRight());

        curr = curr.getRight();
        Assert.assertEquals(five, curr.getData());
        Assert.assertNotNull(curr.getRight());
        Assert.assertEquals(six, curr.getRight().getData());
        Assert.assertNull(curr.getRight().getRight());
        Assert.assertNull(curr.getRight().getLeft());
        Assert.assertNotNull(curr.getLeft());

        curr = curr.getLeft();
        Assert.assertEquals(nThree, curr.getData());
        Assert.assertNull(curr.getLeft());
        Assert.assertNotNull(curr.getRight());

        curr = curr.getRight();
        Assert.assertEquals(three, curr.getData());
        Assert.assertNull(curr.getRight());
        Assert.assertNull(curr.getLeft());
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
        tree.add(nFive);
        tree.add(nSix);
        tree.add(nFour);
        tree.add(zero);
        tree.add(nOne);
        tree.add(one);
        tree.add(six);
        tree.add(four);
        tree.add(three);
        tree.add(five);
        tree.add(eight);
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
        tree.add(nFive);
        tree.add(nSix);
        tree.add(nFour);
        tree.add(zero);
        tree.add(nOne);
        tree.add(one);
        tree.add(six);
        tree.add(four);
        tree.add(three);
        tree.add(five);
        tree.add(eight);
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
    }

    @Test
    public void testClear() {
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
        tree.add(nFive);
        tree.add(nSix);
        tree.add(nFour);
        tree.add(zero);
        tree.add(nOne);
        tree.add(one);
        tree.add(six);
        tree.add(four);
        tree.add(three);
        tree.add(five);
        tree.add(eight);
        tree.add(seven);
        tree.add(nine);

        Assert.assertEquals(15, tree.size());
        Assert.assertNotNull(tree.getRoot());

        tree.clear();
        Assert.assertEquals(0, tree.size());
        Assert.assertNull(tree.getRoot());
    }

    @Test
    public void testHeight() {
        /*
                    2
                 ---  ---
                /        \
             -2           6
             / \         /
           -5    0      4
            \
            -4
         */
        tree.add(two);
        tree.add(nTwo);
        tree.add(nFive);
        tree.add(nFour);
        tree.add(zero);
        tree.add(six);
        tree.add(four);

        Assert.assertEquals(3, tree.height());

        tree.clear();
        Assert.assertEquals(-1, tree.height());

        tree.add(zero);
        Assert.assertEquals(0, tree.height());
    }

    /**************************************************************************************
     EXPECTED EXCEPTIONS
     ***********************************************************************************/
    @Test(expected = IllegalArgumentException.class)
    public void testConstructorNullException() {
        BST<Integer> diffTree = new BST<>(null);
        //make sure you are also null checking each element you add from a collection!!
        //if you just call your add method it will automatically do this!!
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddException() {
        tree.add(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRemoveNullException() {
        tree.remove(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveNotFoundException() {
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
}
