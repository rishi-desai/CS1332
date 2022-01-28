import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
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
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The ArrayList is null.");
        } else {
            backingArray = (T[]) new Comparable[(2 * data.size()) + 1];
            for (int i = 1; i <= data.size(); i++) {
                if (data.get(i - 1) == null) {
                    throw new IllegalArgumentException("The element at " + (i - 1) + " in the ArrayList is null");
                } else {
                    backingArray[i] = data.get(i - 1);
                    size++;
                }
            }
            for (int i = size; i > 0; i--) {
                downHeap(i);
            }
        }
    }

    /**
     * Returning the position of the left child for the element at pos.
     *
     * @param pos the index
     * @return the left child index
     */
    private int getLeft(int pos) {
        return (2 * pos);
    }

    /**
     * Returning the position of the right child for the element at pos.
     *
     * @param pos the index
     * @return the right child index
     */
    private int getRight(int pos) {
        return (2 * pos) + 1;
    }

    /**
     * Returning the position of the parent for the element at pos.
     *
     * @param pos the index
     * @return the parent index
     */
    private int getParent(int pos) {
        return pos / 2;
    }

    /**
     * Swaps two elements of the backing array.
     *
     * @param elementOne the first index
     * @param elementTwo the second index
     */
    private void swap(int elementOne, int elementTwo) {
        T temp;
        temp = backingArray[elementOne];

        backingArray[elementOne] = backingArray[elementTwo];
        backingArray[elementTwo] = temp;
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data into the Heap.");
        } else {
            if (size >= backingArray.length - 1) {
                T[] newBackingArray = (T[]) new Comparable[backingArray.length * 2];
                for (int i = 1; i < backingArray.length; i++) {
                    newBackingArray[i] = backingArray[i];
                }
                backingArray = newBackingArray;
            }
            backingArray[++size] = data;
            if (size != 1) {
                upHeap(size);
            }
        }
    }

    /**
     * A method to recursively upHeap from pos until order property is satisfied.
     *
     * @param pos the index
     */
    private void upHeap(int pos) {
        if (pos == 1 || backingArray[getParent(pos)].compareTo(backingArray[pos]) < 0) {
            return;
        }
        if (backingArray[pos].compareTo(backingArray[getParent(pos)]) < 0) {
            swap(pos, getParent(pos));
        }
        upHeap(getParent(pos));
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after adding.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty.");
        } else {
            T minItem = backingArray[1];
            T lastItem = backingArray[size];
            backingArray[1] = lastItem;
            backingArray[size] = null;
            size--;
            downHeap(1);
            return minItem;
        }
    }

    /**
     * A method to recursively downHeap from pos until order property is satisfied.
     *
     * @param pos the index
     */
    private void downHeap(int pos) {
        if (backingArray[getLeft(pos)] != null) {
            int leftIndex = getLeft(pos);

            int smallChildIndex = leftIndex;

            if (backingArray[getRight(pos)] != null) {
                int rightIndex = getRight(pos);
                if (backingArray[getLeft(pos)].compareTo(backingArray[getRight(pos)]) > 0) {
                    smallChildIndex = rightIndex;
                }
            }

            if (backingArray[smallChildIndex].compareTo(backingArray[pos]) < 0) {
                swap(pos, smallChildIndex);
                downHeap(smallChildIndex);
            }
        }
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty.");
        } else {
            return backingArray[1];
        }
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
