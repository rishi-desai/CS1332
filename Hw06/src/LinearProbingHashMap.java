import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.HashSet;
import java.util.ArrayList;

/**
 * Your implementation of a LinearProbingHashMap.
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
public class LinearProbingHashMap<K, V> {

    /**
     * The initial capacity of the LinearProbingHashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * The max load factor of the LinearProbingHashMap
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    // Do not add new instance variables or modify existing ones.
    private LinearProbingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new LinearProbingHashMap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     *
     * Use constructor chaining.
     */
    public LinearProbingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new LinearProbingHashMap.
     *
     * The backing array should have an initial capacity of initialCapacity.
     *
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public LinearProbingHashMap(int initialCapacity) {
        table = (LinearProbingMapEntry<K, V>[]) new LinearProbingMapEntry[initialCapacity];
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     *
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. For example, let's say the array is of length 5 and the current
     * size is 3 (LF = 0.6). For this example, assume that no elements are
     * removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. Be
     * careful to consider the differences between integer and double
     * division when calculating load factor.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("The key or value is null.");
        }
        if (getNextLoadFactor() > MAX_LOAD_FACTOR) {
            resizeBackingTable((2 * table.length) + 1);
        }
        return addToMap(key, value);
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    private V addToMap(K key, V value) {
        int index = getIndex(key);
        LinearProbingMapEntry<K, V> entry = table[index];
        V result = null;
        if (entry == null) {
            table[index] = new LinearProbingMapEntry<>(key, value);
        } else if (entry.getKey().equals(key)) {
            if (!entry.isRemoved()) {
                result = entry.getValue();
            }
            entry.setValue(value);
            entry.setRemoved(false);
        } else {
            entry.setKey(key);
            entry.setValue(value);
            entry.setRemoved(false);
        }
        if (result == null) {
            size++;
        }
        return result;
    }

    /**
     * Removes the entry with a matching key from map by marking the entry as
     * removed.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Argument cannot be null.");
        }
        int index = this.getIndex(key);
        LinearProbingMapEntry<K, V> entry = table[index];
        if (entry != null && entry.getKey().equals(key) && !entry.isRemoved()) {
            entry.setRemoved(true);
            size--;
            return entry.getValue();
        }
        throw new NoSuchElementException("The key is not in the map.");
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null.");
        } else {
            int index = getIndex(key);
            LinearProbingMapEntry<K, V> entry = table[index];
            if (entry != null && !entry.isRemoved() && entry.getKey().equals(key)) {
                return entry.getValue();
            } else {
                throw new NoSuchElementException("The key is not in the map.");
            }
        }
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key is null.");
        }
        int index = getIndex(key);
        LinearProbingMapEntry<K, V> entry = table[index];
        return (entry != null && !entry.isRemoved() && entry.getKey().equals(key));
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        HashSet<K> result = new HashSet<K>(size);
        if (result.size() == size) {
            return result;
        }
        for (int i = 0; i < table.length; i++) {
            LinearProbingMapEntry<K, V> entry = table[i];
            if (entry != null && !entry.isRemoved()) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use java.util.ArrayList or java.util.LinkedList.
     *
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        ArrayList<V> result = new ArrayList<V>(size);
        for (int i = 0; i < table.length; i++) {
            LinearProbingMapEntry<K, V> entry = table[i];
            if (result.size() == size) {
                return result;
            }
            if (entry != null && !entry.isRemoved()) {
                result.add(entry.getValue());
            }
        }
        return result;
    }

    /**
     * Resize the backing table to length.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed. 
     * You should NOT copy over removed elements to the resized backing table.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("length is less than the number of items in the HashMap");
        } else {
            LinearProbingMapEntry<K, V>[] temp = table;
            if (table.size() == size) {
                return table;
            }
            this.table = (LinearProbingMapEntry<K, V>[]) new LinearProbingMapEntry[length];
            size = 0;
            for (int i = 0; i < temp.length; i++) {
                LinearProbingMapEntry<K, V> entry = temp[i];
                if (entry != null && !entry.isRemoved()) {
                    addToMap(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    /**
     * Clears the map.
     *
     * Resets the table to a new array of the INITIAL_CAPACITY and resets the
     * size.
     *
     * Must be O(1).
     */
    public void clear() {
        table = (LinearProbingMapEntry<K, V>[]) new LinearProbingMapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public LinearProbingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     *  Calculates the load factor if another element is added to the HashMap.fa
     *
     * @return the load factor
     */
    private double getNextLoadFactor() {
        return (double) (size + 1) / (double) table.length;
    }

    /**
     * Gets the index of a given array-backed HashMap to add the given key.
     *
     * @param key the key to add
     * @return the index to add at
     */
    private int getIndex(K key) {
        int index = Math.abs(key.hashCode() % table.length);

        LinearProbingMapEntry<K, V> currEntry = table[index];
        int firstRemoved = -1;
        int currIndex = index;
        boolean looped = false;

        while (currEntry != null && !currEntry.getKey().equals(key) && (!looped || currIndex != index)) {
            if (firstRemoved == -1 && currEntry.isRemoved()) {
                firstRemoved = currIndex;
            }
            if (currEntry.getKey().equals(key) && firstRemoved == index && currEntry.isRemoved()) {
                return firstRemoved;
            }
            currIndex++;
            if (currIndex >= table.length) {
                currIndex = 0;
                looped = true;
            }
            currEntry = table[currIndex];
        }
        if (currEntry != null && currEntry.getKey().equals(key)) {
            return currIndex;
        } else {
            return (firstRemoved == -1 ? currIndex : firstRemoved);
        }
    }
}
