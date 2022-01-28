import java.util.Comparator;
import java.util.Random;
import java.util.List;
import java.util.LinkedList;
import java.util.PriorityQueue;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Rishi Desai
 * @version 1.0
 * @userid rdesai82
 * @GTID 903593663
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class Sorting {

    /**
     * Implement insertion sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or comparator is null.");
        } else {
            for (int n = 1; n < arr.length; n++) {
                int i = n;
                while (i > 0 && comparator.compare(arr[i], arr[i - 1]) < 0) {
                    swap(arr, i, i - 1);
                    i--;
                }
            }
        }
    }

    /**
     * Implement cocktail sort.
     * <p>
     * It should be:
     * in-place
     * stable
     * adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n)
     * <p>
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or comparator is null.");
        } else {
            boolean swapsMade = true;
            int startInd = 0;
            int endInd = arr.length - 1;
            int lastSwapInd = 0;
            while (swapsMade) {
                swapsMade = false;
                for (int i = startInd; i < endInd; i++) {
                    if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                        swap(arr, i, i + 1);
                        swapsMade = true;
                        lastSwapInd = i;
                    }
                }
                endInd = lastSwapInd;
                if (swapsMade) {
                    swapsMade = false;
                    for (int i = endInd; i > startInd; i--) {
                        if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                            swap(arr, i, i - 1);
                            swapsMade = true;
                            lastSwapInd = i;
                        }
                    }
                    startInd = lastSwapInd;
                }
            }
        }
    }

    /**
     * Implement merge sort.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     * <p>
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     * <p>
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or comparator is null.");
        } else if (arr.length <= 1) {
            return;
        } else {
            if (arr.length == 1) {
                return;
            }

            int length = arr.length;
            int midIndex = length / 2;
            T[] left = (T[]) new Object[midIndex];
            T[] right = (T[]) new Object[length - midIndex];

            for (int i = 0; i < midIndex; i++) {
                left[i] = arr[i];
            }
            for (int i = 0; i < length - midIndex; i++) {
                right[i] = arr[midIndex + i];
            }

            mergeSort(left, comparator);
            mergeSort(right, comparator);

            int i = 0;
            int j = 0;

            while (i < left.length && j < right.length) {
                if (comparator.compare(left[i], right[j]) <= 0) {
                    arr[i + j] = left[i];
                    i++;
                } else {
                    arr[i + j] = right[j];
                    j++;
                }
            }

            while (i < left.length) {
                arr[i + j] = left[i];
                i++;
            }
            while (j < right.length) {
                arr[i + j] = right[j];
                j++;
            }
        }
    }

    /**
     * Implement quick sort.
     * <p>
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     * <p>
     * int pivotIndex = rand.nextInt(b - a) + a;
     * <p>
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     * <p>
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     * <p>
     * It should be:
     * in-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n^2)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("Either the array, comparator, and/or rand is null.");
        } else {
            quickSortHelper(arr, comparator, rand, 0, arr.length - 1);
        }

    }

    /**
     * Helper method for quickSort.
     *
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @param start      the start index
     * @param end        the end index
     * @param <T>        data type to sort
     */
    private static <T> void quickSortHelper(T[] arr, Comparator<T> comparator, Random rand, int start, int end) {
        if (end - start < 1) {
            return;
        }

        int pivotIdx = rand.nextInt(end - start + 1) + start;
        T pivotVal = arr[pivotIdx];

        swap(arr, start, pivotIdx);

        int i = start + 1;
        int j = end;

        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivotVal) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivotVal) >= 0) {
                j--;
            }
            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }
        swap(arr, start, j);
        quickSortHelper(arr, comparator, rand, start, j - 1);
        quickSortHelper(arr, comparator, rand, j + 1, end);
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     * <p>
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     * <p>
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     * <p>
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(kn)
     * <p>
     * And a best case running time of:
     * O(kn)
     * <p>
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     * <p>
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     * <p>
     * Refer to the PDF for more information on LSD Radix Sort.
     * <p>
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     * <p>
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array is null.");
        } else if (arr.length <= 1) {
            return;
        } else {
            int k = largestMagnitude(arr);
            int base = 1;
            LinkedList<Integer>[] buckets = new LinkedList[19];
            for (int iteration = 0; iteration < k; iteration++) {
                for (int i = 0; i < arr.length; i++) {
                    int digit = getDigit(arr[i], base);
                    if (buckets[digit + 9] == null) {
                        buckets[digit + 9] = new LinkedList<>();
                    }
                    buckets[digit + 9].addLast(arr[i]);
                }
                int idx = 0;
                for (LinkedList bucket : buckets) {
                    while (bucket != null && !bucket.isEmpty()) {
                        int value = (int) bucket.getFirst();
                        bucket.removeFirst();
                        arr[idx++] = value;
                    }
                }
                base *= 10;
            }
        }
    }

    /**
     * Finds the number of iterations needed by finding the element in the array with the largest magnitude.
     *
     * @param arr the array containing the elements
     * @return the number of digits of the largest element in the array
     */
    private static int largestMagnitude(int[] arr) {
        int max = arr[0];
        for (int element : arr) {
            if (element > max) {
                max = element;
            }
        }
        return String.valueOf(max).length();
    }

    /**
     * Finds the digit at the tenths, hundredths, thousandths,... place of a given value.
     *
     * @param value the value to find the digit in
     * @param base  the base place of the digit (e.g. for 123: base = 1 -> 3, base = 10 -> 2, base = 100 -> 1)
     * @return the digit at the base place of the given value
     */
    private static int getDigit(int value, int base) {
        return (value / base) % 10;
    }

    /**
     * Implement heap sort.
     * <p>
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     * <p>
     * Have a worst case running time of:
     * O(n log n)
     * <p>
     * And a best case running time of:
     * O(n log n)
     * <p>
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     * <p>
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     * <p>
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        } else {
            PriorityQueue<Integer> q = new PriorityQueue<>(data);
            int[] arr = new int[q.size()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = q.remove();
            }
            return arr;
        }
    }

    /**
     * Swap two elements in an array.
     *
     * @param arr    the array where the elements need to be swapped
     * @param idxOne the index of the first element
     * @param idxTwo the index of the second element
     * @param <T>    data type to sort
     */
    private static <T> void swap(T[] arr, int idxOne, int idxTwo) {
        T temp = arr[idxOne];
        arr[idxOne] = arr[idxTwo];
        arr[idxTwo] = temp;
    }
}
