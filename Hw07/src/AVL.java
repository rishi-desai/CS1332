import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ArrayList;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Rishi Desai
 * @userid rdesai82
 * @GTID 903593663
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no-argument constructor that should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it appears in the Collection.
     *
     * @throws IllegalArgumentException if data or any element in data is null
     * @param data the data to add to the tree
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The Collection is null.");
        } else {
            for (T element : data) {
                if (element == null) {
                    throw new IllegalArgumentException("An element in data is null.");
                } else {
                    add(element);
                }
            }
        }
    }

    /**
     * Adds the data to the AVL. Start by adding it as a leaf like in a regular
     * BST and then rotate the tree as needed.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors going up the tree,
     * rebalancing if necessary.
     *
     * @throws java.lang.IllegalArgumentException if the data is null
     * @param data the data to be added
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        } else {
            root = rAdd(root, data);
        }
    }

    /**
     * Helper method for add.
     *
     * @param curr the current node
     * @param data the data that is added
     * @return the root node
     */
    private AVLNode<T> rAdd(AVLNode<T> curr, T data) {
        if (curr == null) {
            ++size;
            return new AVLNode<>(data);
        } else if (curr.getData().compareTo(data) < 0) {
            // input data is larger go right
            // set data in right
            curr.setRight(rAdd(curr.getRight(), data));
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(rAdd(curr.getLeft(), data));
        }

        //recalculate height and BF
        update(curr);
        return checkBF(curr);
    }

    /**
     * Updates the height and balance factor of the current node.
     *
     * @param curr Node to calculate the height and branch factor of.
     */
    private void update(AVLNode<T> curr) {
        if (curr != null) {
            if (curr.getLeft() != null) {
                if (curr.getRight() != null) {
                    // do Normal thing
                    curr.setBalanceFactor(curr.getLeft().getHeight()
                            - curr.getRight().getHeight());
                    if (curr.getLeft().getHeight()
                            > curr.getRight().getHeight()) {
                        curr.setHeight(curr.getLeft().getHeight() + 1);
                    } else {
                        curr.setHeight(curr.getRight().getHeight() + 1);
                    }
                } else {
                    // left non null
                    curr.setHeight(curr.getLeft().getHeight() + 1);
                    curr.setBalanceFactor(curr.getLeft().getHeight() + 1);
                }
            } else {
                if (curr.getRight() != null) {
                    // right non null
                    curr.setHeight(curr.getRight().getHeight() + 1);
                    curr.setBalanceFactor(-1 - curr.getRight().getHeight());
                } else {
                    curr.setBalanceFactor(0);
                    curr.setHeight(0);
                }
            }
        }
    }
    /**
     * Check the balance factor of a node in the AVL tree. Balances the
     * node if the absolute value is greater than 1.
     *
     * @param curr The node to check the branch factor of
     * @return The correct node to link after balancing for pointer
     *         reinforcement
     */
    private AVLNode<T> checkBF(AVLNode<T> curr) {
        if (curr.getBalanceFactor() < -1) {
            //right heavy
            if (curr.getRight() != null
                    && curr.getRight().getBalanceFactor() >= 1) {
                // right-left rotation
                curr.setRight(rightRotation(curr.getRight()));
            }
            return leftRotation(curr);

        } else if (curr.getBalanceFactor() > 1) {
            // left heavy -- right rotation
            if (curr.getLeft() != null
                    && curr.getLeft().getBalanceFactor() <= -1) {
                //left-right rotation
                curr.setLeft(leftRotation(curr.getLeft()));
            }
            return rightRotation(curr);
        }
        return curr;
    }

    /**
     * Performs a left rotation on the input node.
     *
     * @param a Node to perform a left rotation on.
     * @return The node to be linked for pointer reinforcement after the left rotation.
     */
    private AVLNode<T> leftRotation(AVLNode<T> a) {
        AVLNode<T> b = a.getRight();
        a.setRight(b.getLeft());
        b.setLeft(a);
        update(a);
        update(b);
        return b;
    }

    /**
     * Performs a right rotation on the input node.
     * @param c Node to perform a right rotation on.
     * @return The node to be linked for pointer reinforcement after a right rotation is performed.
     */
    private AVLNode<T> rightRotation(AVLNode<T> c) {
        AVLNode<T> b = c.getLeft();
        c.setLeft(b.getRight());
        b.setRight(c);
        update(c);
        update(b);
        return b;
    }

    /**
     * Removes the data from the tree. There are 3 cases to consider:
     *
     * 1: the data is a leaf. In this case, simply remove it.
     * 2: the data has one child. In this case, simply replace it with its
     * child.
     * 3: the data has 2 children. Use the successor to replace the data,
     * not the predecessor. As a reminder, rotations can occur after removing
     * the successor node.
     *
     * Remember to recalculate heights going up the tree, rebalancing if
     * necessary.
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to remove from the tree.
     * @return the data removed from the tree. Do not return the same data
     * that was passed in.  Return the data that was stored in the tree.
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data can not be null");
        }
        AVLNode<T> dummy = new AVLNode<>(null);
        root = rRemove(data, root, dummy);
        if (dummy.getData() == null) {
            throw new java.util.NoSuchElementException("Data is not in "
                    + "the AVL tree");
        }
        return dummy.getData();
    }

    /**
     * Helper method for removing a node from the AVL tree.
     *
     * @param data Data value to search for.
     * @param curr Current node in the traversal of the tree.
     * @param dummy Dummy node to store the value of the removed node.
     * @return A node used to link the tree together using pointer reinforcement
     */
    private AVLNode<T> rRemove(T data, AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr == null) {
            // element is not in the AVL
            return curr;
        }
        if (curr.getData().compareTo(data) == 0) {
            --size;
            dummy.setData(curr.getData());
            // data is found check children
            if (curr.getLeft() == null && curr.getRight() == null) {
                // replace leaf
                return null;
            } else if (curr.getLeft() == null) {
                // 1 child - right
                return curr.getRight();
            } else if (curr.getRight() == null) {
                // 1 child - left
                return curr.getLeft();
            } else {
                //2 children
                AVLNode<T> dummy2 = new AVLNode<>(null);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                dummy2.setRight(curr.getRight());
                dummy2.setLeft(curr.getLeft());
                //recalculateHeightBF(replacement);
                curr = dummy2;
                //return checkBF(replacement);
            }
        } else if (curr.getData().compareTo(data) > 0) {
            // go left
            curr.setLeft(rRemove(data, curr.getLeft(), dummy));
        } else {
            // go right
            curr.setRight(rRemove(data, curr.getRight(), dummy));
        }
        // update Height and BF then check the Branch Factor for rotations
        update(curr);
        return checkBF(curr);
    }

    /**
     * Finds a replacement for a removed node using the successor of that node.
     *
     * @param curr Current node in the traversal of the AVL tree.
     * @param dummy Dummy node to store the value of the replacement.
     * @return The node to link the AVL tree together using pointer
     *         reinforcement.
     */
    private AVLNode<T> removeSuccessor(AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy));
        }
        update(curr);
        return checkBF(curr);
    }

    /**
     * Returns the data in the tree matching the parameter passed in (think
     * carefully: should you use value equality or reference equality?).
     *
     * @throws IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException if the data is not found
     * @param data the data to search for in the tree.
     * @return the data in the tree equal to the parameter. Do not return the
     * same data that was passed in.  Return the data that was stored in the
     * tree.
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        } else if (!contains(data)) {
            throw new NoSuchElementException("The data is not in the BST.");
        } else {
            return rGet(root, data);
        }
    }

    /**
     * Helper method to recursively get data in the BST.
     *
     * @param curr the current node
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     */
    private T rGet(AVLNode<T> curr, T data) {
        int comp = data.compareTo(curr.getData());
        if (comp == 0) {
            return curr.getData();
        } else if (comp < 0) {
            return rGet(curr.getLeft(), data);
        } else if (comp > 0) {
            return rGet(curr.getRight(), data);
        } else {
            return curr.getData();
        }
    }

    /**
     * Returns whether or not data equivalent to the given parameter is
     * contained within the tree. The same type of equality should be used as
     * in the get method.
     *
     * @throws IllegalArgumentException if the data is null
     * @param data the data to search for in the tree.
     * @return whether or not the parameter is contained within the tree.
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        } else {
            return rContains(root, data);
        }
    }

    /**
     * Helper method to recursively check if the data is contained in the AVL.
     *
     * @param curr the current node
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     */
    private boolean rContains(AVLNode<T> curr, T data) {
        if (curr == null) {
            return false;
        }
        int comp = data.compareTo(curr.getData());
        if (comp == 0) {
            return true;
        } else if (comp < 0) {
            return rContains(curr.getLeft(), data);
        } else if (comp > 0) {
            return rContains(curr.getRight(), data);
        } else {
            return false;
        }
    }

    /**
     * Returns the data on branches of the tree with the maximum depth. If you
     * encounter multiple branches of maximum depth while traversing, then you
     * should list the remaining data from the left branch first, then the
     * remaining data in the right branch. This is essentially a preorder
     * traversal of the tree, but only of the branches of maximum depth.
     *
     * Your list should not duplicate data, and the data of a branch should be
     * listed in order going from the root to the leaf of that branch.
     *
     * Should run in worst case O(n), but you should not explore branches that
     * do not have maximum depth. You should also not need to traverse branches
     * more than once.
     *
     * Hint: How can you take advantage of the balancing information stored in
     * AVL nodes to discern deep branches?
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * Returns: [10, 5, 2, 1, 0, 7, 8, 9, 15, 20, 25, 30]
     *
     * @return the list of data in branches of maximum depth in preorder
     * traversal order
     */
    public List<T> deepestBranches() {
        List<T> res = new ArrayList<>();
        return rDeepestBranches(root, res);
    }

    /**
     * Helper for deepestBranches method.
     *
     * @param curr the root node
     * @param list the list that the data will be added to
     * @return the list of data in branches of maximum depth in preorder traversal order
     */
    private List<T> rDeepestBranches(AVLNode<T> curr, List<T> list) {
        if (curr != null) {
            list.add(curr.getData());
            if (curr.getLeft() != null && curr.getLeft().getHeight() == curr.getHeight() - 1) {
                rDeepestBranches(curr.getLeft(), list);
            }
            if (curr.getRight() != null && curr.getRight().getHeight() == curr.getHeight() - 1) {
                rDeepestBranches(curr.getRight(), list);
            }
        }
        return list;
    }

    /**
     * Returns a sorted list of data that are within the threshold bounds of
     * data1 and data2. That is, the data should be > data1 and < data2.
     *
     * Should run in worst case O(n), but this is heavily dependent on the
     * threshold data. You should not explore branches of the tree that do not
     * satisfy the threshold.
     *
     * Example Tree:
     *                           10
     *                       /        \
     *                      5          15
     *                    /   \      /    \
     *                   2     7    13    20
     *                  / \   / \     \  / \
     *                 1   4 6   8   14 17  25
     *                /           \          \
     *               0             9         30
     *
     * sortedInBetween(7, 14) returns [8, 9, 10, 13]
     * sortedInBetween(3, 8) returns [4, 5, 6, 7]
     * sortedInBetween(8, 8) returns []
     *
     * @throws java.lang.IllegalArgumentException if data1 or data2 are null
     * @param data1 the smaller data in the threshold
     * @param data2 the larger data in the threshold
     * or if data1 > data2
     * @return a sorted list of data that is > data1 and < data2
     */
    public List<T> sortedInBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Either data1 or data2 is null.");
        } else if (data1.compareTo(data2) > 0) {
            throw new IllegalArgumentException("The value of data1 is greater than the value of data2.");
        }
        List<T> res = new ArrayList<>();
        return rSortedInBetween(root, data1, data2, res);
    }

    /**
     * Helper method for sortedInBetween.
     *
     * @param curr the current node
     * @param lower the smaller data in the threshold
     * @param upper teh larger data in the threshold
     * @param list the list of sorted data
     * @return a sorted list of data that is > data1 and < data2
     */
    private List<T> rSortedInBetween(AVLNode<T> curr, T lower, T upper, List<T> list) {
        if (curr != null) {
            if (lower.compareTo(curr.getData()) >= 0) {
                rSortedInBetween(curr.getRight(), lower, upper, list);
            }
            if (lower.compareTo(curr.getData()) < 0
                    && upper.compareTo(curr.getData()) > 0) {

                rSortedInBetween(curr.getLeft(), lower, upper, list);
                list.add(curr.getData());
                rSortedInBetween(curr.getRight(), lower, upper, list);

            }
            if (upper.compareTo(curr.getData()) <= 0) {
                rSortedInBetween(curr.getLeft(), lower, upper, list);
            }
        }
        return list;
    }

    /**
     * Clears the tree.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * Since this is an AVL, this method does not need to traverse the tree
     * and should be O(1)
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        }
        return root.getHeight();
    }

    /**
     * Returns the size of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return number of items in the AVL tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD
        return size;
    }

    /**
     * Returns the root of the AVL tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the AVL tree
     */
    public AVLNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }
}