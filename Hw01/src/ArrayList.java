import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayList.
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
public class ArrayList<T> {

    /**
     * The initial capacity of the ArrayList.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     *
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds the element to the specified index.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    @SuppressWarnings("checkstyle:OperatorWrap")
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > backingArray.length) {
            throw new IndexOutOfBoundsException("The index is less than 0 or greater than "
                    + "the size of the data structure.");
        } else if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data stucture.");
        } else {
            if (backingArray[index] == null) {
                backingArray[index] = data;
            } else if (size == backingArray.length) {
                // creates new array that is double the size of old array
                T[] tempbackingArray = (T[]) new Object[backingArray.length * 2];
                for (int i = 0; i < index; i++) {
                    tempbackingArray[i] = backingArray[i];
                }
                tempbackingArray[index] = data;
                for (int i = index + 1; i < backingArray.length; i++) {
                    tempbackingArray[i] = backingArray[i - 1];
                }
                backingArray = tempbackingArray;
            } else {
                // creates new array that is the same size as the old array
                T[] tempBackingArray = (T[]) new Object[backingArray.length];
                for (int i = 0; i < index; i++) {
                    tempBackingArray[i] = backingArray[i];
                }
                tempBackingArray[index] = data;
                for (int i = index + 1; i < backingArray.length; i++) {
                    tempBackingArray[i] = backingArray[i - 1];
                }
                backingArray = tempBackingArray;
            }
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure.");
        } else {
            if (backingArray[0] == null) {
                backingArray[0] = data;
            } else if (size == backingArray.length) {
                // creates new array that is double the size of old array
                T[] tempBackingArray = (T[]) new Object[backingArray.length * 2];
                tempBackingArray[0] = data;
                for (int i = 0; i < backingArray.length; i++) {
                    tempBackingArray[i + 1] = backingArray[i];
                }
                backingArray = tempBackingArray;
            } else {
                // creates new array that is the same size as the old array
                T[] tempBackingArray = (T[]) new Object[backingArray.length];
                tempBackingArray[0] = data;
                for (int i = 0 + 1; i < backingArray.length; i++) {
                    tempBackingArray[i] = backingArray[i - 1];
                }
                backingArray = tempBackingArray;
            }
            size++;
        }
    }


    /**
     * Adds the element to the back of the list.
     *
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into data structure");
        } else {
            if (size == backingArray.length) {
                // creates an array double the size of the old array
                T[] tempBackingArray = (T[]) new Object[backingArray.length * 2];
                for (int i = 0; i < backingArray.length; i++) {
                    tempBackingArray[i] = backingArray[i];
                }
                tempBackingArray[size] = data;
                backingArray = tempBackingArray;
            } else if (backingArray[size] == null) {
                backingArray[size] = data;
            }
            size++;
        }
    }

    /**
     * Removes and returns the element at the specified index.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        T data;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is less than 0 or greater than or equal to the size of the "
                    + "data structure.");
        } else {
            if (index == size - 1) {
                data = backingArray[index];
                backingArray[index] = null;
            } else {
                data = backingArray[index];
                T[] tempBackingArray = (T[]) new Object[backingArray.length];
                // any items after the removed string are shifted left
                for (int i = 0, j = 0; i < backingArray.length; i++) {
                    if (i != index) {
                        tempBackingArray[j] = backingArray[i];
                        j++;
                    }
                }
                backingArray = tempBackingArray;
            }
            size--;
        }
        return data;
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        T data;
        if (size == 0) {
            throw new NoSuchElementException("The data structure is empty.");
        } else {
            data = backingArray[0];
            T[] tempBackingArray = (T[]) new Object[backingArray.length];
            // any items after the removed string are shifted left
            for (int i = 0, j = 0; i < backingArray.length; i++) {
                if (i != 0) {
                    tempBackingArray[j] = backingArray[i];
                    j++;
                }
            }
            backingArray = tempBackingArray;
            size--;
        }
        return data;
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        T data;
        if (size == 0) {
            throw new NoSuchElementException("The data structure is empty.");
        } else {
            data = backingArray[size - 1];
            backingArray[size - 1] = null;
            size--;
        }
        return data;
    }

    /**
     * Returns the element at the specified index.
     *
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        T data;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is less than 0 or greater than or equal to the size of the "
                    + "data structure.");
        } else {
            data = backingArray[index];
        }
        return data;
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    /**
     * Clears the list.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the list.
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
     * Returns the size of the list.
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
