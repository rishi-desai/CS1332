import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
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
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index is less than 0 or greater than size of data structure.");
        } else if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into the data structure.");
        } else {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode<>(data);
            if (head == null) {
                head = newNode;
                tail = newNode;
                head.setNext(null);
                head.setPrevious(null);
            } else if (index == 0) {
                newNode.setNext(head);
                head.setPrevious(newNode);
                head = newNode;
            } else if (index == size) {
                newNode.setPrevious(tail);
                tail.setNext(newNode);
                tail = newNode;
            } else {
                if (index < (size >> 1)) {
                    DoublyLinkedListNode<T> curr = head;
                    for (int i = 1; i < index; i++) {
                        curr = curr.getNext();
                    }
                    newNode.setNext(curr.getNext());
                    curr.setNext(newNode);
                    newNode.setPrevious(curr);
                    newNode.getNext().setPrevious(newNode);
                } else {
                    DoublyLinkedListNode<T> curr = tail;
                    for (int i = size - 1; i >= index; i--) {
                        curr = curr.getPrevious();
                    }
                    newNode.setNext(curr.getNext());
                    curr.setNext(newNode);
                    newNode.setPrevious(curr);
                    newNode.getNext().setPrevious(newNode);
                }
            }
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into the data structure.");
        } else {
            addAtIndex(0, data);
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot insert null data into the data structure.");
        } else {
            addAtIndex(size, data);
        }
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is less than 0 or greater than or equal to size.");
        } else {
            T data;
            if (index == 0) {
                DoublyLinkedListNode<T> curr = head;
                data = head.getData();
                curr = curr.getNext();
                head = curr;
                if (size == 1) {
                    tail = curr;
                } else {
                    curr.setPrevious(null);
                }
            } else if (index == size - 1) {
                DoublyLinkedListNode<T> curr = tail;
                data = tail.getData();
                curr = curr.getPrevious();
                tail = curr;
                if (size == 1) {
                    head = curr;
                } else {
                    curr.setNext(null);
                }
            } else {
                if (index < (size >> 1)) {
                    DoublyLinkedListNode<T> curr = head;
                    for (int i = 1; i <= index; i++) {
                        curr = curr.getNext();
                    }
                    data = curr.getData();
                    curr.getNext().setPrevious(curr.getPrevious());
                    curr.getPrevious().setNext(curr.getNext());
                } else {
                    DoublyLinkedListNode<T> curr = tail;
                    for (int i = size - 1; i > index; i--) {
                        curr = curr.getPrevious();
                    }
                    data = curr.getData();
                    curr.getNext().setPrevious(curr.getPrevious());
                    curr.getPrevious().setNext(curr.getNext());
                }
            }
            size--;
            return data;
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty.");
        } else {
            T data;
            DoublyLinkedListNode<T> curr = head;
            data = head.getData();
            curr = curr.getNext();
            head = curr;
            if (size == 1) {
                tail = curr;
            } else {
                curr.setPrevious(null);
            }
            size--;
            return data;
        }
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
        if (size == 0) {
            throw new NoSuchElementException("The list is empty.");
        } else {
            T data;
            DoublyLinkedListNode<T> curr = tail;
            data = tail.getData();
            curr = curr.getPrevious();
            tail = curr;
            if (size == 1) {
                head = curr;
            } else {
                curr.setNext(null);
            }
            size--;
            return data;
        }
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index is less than 0 or greater than or equal to size.");
        } else {
            if (index == 0) {
                return head.getData();
            } else if (index == size - 1) {
                return tail.getData();
            } else {
                if (index < (size >> 1)) {
                    DoublyLinkedListNode<T> curr = head;
                    for (int i = 0; i < index; i++) {
                        curr = curr.getNext();
                    }
                    return curr.getData();
                } else {
                    DoublyLinkedListNode<T> curr = tail;
                    for (int i = size - 1; i > index; i--) {
                        curr = curr.getPrevious();
                    }
                   return curr.getData();
                }
            }
        }
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
        } else {
            return false;
        }
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot remove null data from data structure.");
        } else if (tail == null && head == null) {
            throw new NoSuchElementException("The was not found in the data structure.");
        } else {
            T lastOccurrence = null;
            DoublyLinkedListNode<T> curr = tail;
            if (tail.getData().equals(data)) {
                lastOccurrence = removeFromBack();
            } else {
                for (int i = size - 1; i >= 0; i--) {
                    if (curr.getData().equals(data)) {
                        lastOccurrence = removeAtIndex(i);
                        return lastOccurrence;
                    } else {
                        curr = curr.getPrevious();
                    }
                }
            }
            if (lastOccurrence == null) {
                throw new NoSuchElementException("The was not found in the data structure.");
            }
            return lastOccurrence;
        }
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        Object[] linkedArray = new Object[size];
        if (size == 0) {
            return linkedArray;
        } else {
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < size; i++) {
                linkedArray[i] = curr.getData();
                curr = curr.getNext();
            }
        }
        return linkedArray;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
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
        // DO NOT MODIFY!
        return size;
    }
}
