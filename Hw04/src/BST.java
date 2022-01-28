import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
 *
 * @author Rishi Desai
 * @version 1.0
 * @userid rdesai82
 * @GTID 903593663
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
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
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        } else {
            root = rAdd(root, data);
        }
    }

    /**
     * Helper method to recursively add data to the BST.
     *
     * @param curr the current node
     * @param data the data to add
     * @return the node that is added
     */
    private BSTNode<T> rAdd(BSTNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new BSTNode<>(data);
        } else if (data.compareTo(curr.getData()) == 0) {
            return curr;
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rAdd(curr.getLeft(), data));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rAdd(curr.getRight(), data));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        } else if (!contains(data)) {
            throw new NoSuchElementException("The data is not in the BST.");
        } else {
            BSTNode<T> dummy = new BSTNode<>(null);
            root = rRemove(root, data, dummy);
            return dummy.getData();
        }
    }

    /**
     * Helper method to recursively remove the given data.
     *
     * @param curr the current node
     * @param data the data that was removed
     * @param dummy a node to store the removed data
     * @return the node that was removed
     */
    private BSTNode<T> rRemove(BSTNode<T> curr, T data, BSTNode<T> dummy) {
        if (curr == null) {
            return curr;
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rRemove(curr.getLeft(), data, dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rRemove(curr.getRight(), data, dummy));
        } else {
            dummy.setData(curr.getData());
            size--;
            if (curr.getLeft() == null && curr.getRight() == null) {
                return null;
            } else if (curr.getLeft() != null && curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getRight() != null && curr.getLeft() == null) {
                return curr.getRight();
            } else {
                BSTNode<T> dummy2 = new BSTNode<>(null);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }
        }
        return curr;
    }

    /**
     * Helper method to recursively remove the successor node.
     *
     * @param curr the current node
     * @param dummy a node to store the successor
     * @return the successor node
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr.getLeft() == null) {
            dummy.setData(curr.getData());
            return curr.getRight();
        } else {
            curr.setLeft(removeSuccessor(curr.getLeft(), dummy));
        }
        return curr;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
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
    private T rGet(BSTNode<T> curr, T data) {
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
     * Returns whether or not data matching the given parameter is contained
     * within the tree.

     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        } else {
            return rContains(root, data);
        }
    }

    /**
     * Helper method to recursively check if the data is contained in the BST.
     *
     * @param curr the current node
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     */
    private boolean rContains(BSTNode<T> curr, T data) {
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
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> res = new ArrayList<>();
        return rPreorder(root, res);
    }

    /**
     * Helper method to recursively preorder traversal the BST.
     * @param curr the current node
     * @param list the list storing the preorder traversal
     * @return the list containing the preorder traversal
     */
    private List<T> rPreorder(BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            list.add(curr.getData());
            rPreorder(curr.getLeft(), list);
            rPreorder(curr.getRight(), list);
        }
        return list;
    }

    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> res = new ArrayList<>();
        return rInorder(root, res);
    }

    /**
     * Helper method to recursively inorder traversal the BST.
     * @param curr the current node
     * @param list the list storing the inorder traversal
     * @return the list containing the inorder traversal
     */
    private List<T> rInorder(BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            rInorder(curr.getLeft(), list);
            list.add(curr.getData());
            rInorder(curr.getRight(), list);
        }
        return list;
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> res = new ArrayList<>();
        return rPostorder(root, res);
    }

    /**
     * Helper method to recursively postorder traversal the BST.
     *
     * @param curr the current node
     * @param list the list storing the postorder traversal
     * @return the list storing the postorder traversal
     */
    private List<T> rPostorder(BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            rPostorder(curr.getLeft(), list);
            rPostorder(curr.getRight(), list);
            list.add(curr.getData());
        }
        return list;
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> q = new LinkedList<>();
        List<T> data = new ArrayList<>();
        q.add(root);
        while (!q.isEmpty()) {
            BSTNode<T> curr = q.poll();
            data.add(curr.getData());
            if (curr.getLeft() != null) {
                q.add(curr.getLeft());
            }
            if (curr.getRight() != null) {
                q.add(curr.getRight());
            }
        }
        return data;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return rHeight(root);
    }

    /**
     * Helper method to recursively find the height of the root.
     *
     * @param curr the current node
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    private int rHeight(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        }
        return 1 + Math.max(rHeight(curr.getLeft()), rHeight(curr.getRight()));
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     *                                            in the BST
     */
    public List<T> kLargest(int k) {
        if (k > size) {
            throw new IllegalArgumentException("k is greater than the number of data in the BST.");
        } else {
            List<T> res = new ArrayList<>();
            return rkLargest(root, k, res);
        }
    }

    /**
     * Helper method to recursively find the k largest elements in the BST.
     *
     * @param curr the current node
     * @param list the list storing the k largest elements smallest to largest
     * @param k the number of largest elements to find
     * @return the list containing a sorted list of the k largest elements
     */
    private List<T> rkLargest(BSTNode<T> curr, int k, List<T> list) {
        if (curr != null) {
            rkLargest(curr.getRight(), k, list);
            if (list.size() < k) {
                list.add(0, curr.getData());
            }
            rkLargest(curr.getLeft(), k, list);
        }
        return list;
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
