import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of various string searching algorithms.
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
public class PatternMatching {

    /**
     * Knuth-Morris-Pratt (KMP) algorithm relies on the failure table (also
     * called failure function). Works better with small alphabets.
     *
     * Make sure to implement the buildFailureTable() method before implementing
     * this method.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> kmp(CharSequence pattern, CharSequence text,
                                    CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is null or has length 0.");
        } else if (text == null || comparator == null) {
            throw new IllegalArgumentException("The text or comparator is null.");
        } else {
            List<Integer> indexList = new ArrayList<>();
            if (pattern.length() > text.length()) {
                return indexList;
            }
            int[] shift = buildFailureTable(pattern, comparator);
            int index = 0;
            int pIndex = 0;
            while (index + pattern.length() <= text.length()) {
                while (pIndex < pattern.length()
                        && comparator.compare(pattern.charAt(pIndex),
                        text.charAt(index + pIndex)) == 0) {
                    pIndex++;
                }
                if (pIndex == 0) {
                    index++;
                } else {
                    if (pIndex == pattern.length()) {
                        indexList.add(index);
                    }
                    index += pIndex - shift[pIndex - 1];
                    pIndex = shift[pIndex - 1];
                }
            }
            return indexList;
        }
    }

    /**
     * Builds failure table that will be used to run the Knuth-Morris-Pratt
     * (KMP) algorithm.
     *
     * The table built should be the length of the input pattern.
     *
     * Note that a given index i will contain the length of the largest prefix
     * of the pattern indices [0..i] that is also a suffix of the pattern
     * indices [1..i]. This means that index 0 of the returned table will always
     * be equal to 0
     *
     * Ex. pattern = ababac
     *
     * table[0] = 0
     * table[1] = 0
     * table[2] = 1
     * table[3] = 2
     * table[4] = 3
     * table[5] = 0
     *
     * If the pattern is empty, return an empty array.
     *
     * @param pattern    a pattern you're building a failure table for
     * @param comparator you MUST use this to check if characters are equal
     * @return integer array holding your failure table
     * @throws java.lang.IllegalArgumentException if the pattern or comparator
     *                                            is null
     */
    public static int[] buildFailureTable(CharSequence pattern,
                                          CharacterComparator comparator) {
        if (pattern == null || comparator == null) {
            throw new IllegalArgumentException("The pattern or comparator is null.");
        } else {
            int[] table = new int[pattern.length()];
            if (pattern.length() != 0) {
                table[0] = 0;
            }
            int i = 0;
            int j = 1;
            while (j < pattern.length()) {
                if (comparator.compare(pattern.charAt(j), pattern.charAt(i)) == 0) {
                    table[j++] = ++i;
                } else {
                    if (i != 0) {
                        i = table[i - 1];
                    } else {
                        table[j++] = i;
                    }
                }
            }
            return table;
        }
    }

    /**
     * Boyer Moore algorithm that relies on last occurrence table. Works better
     * with large alphabets.
     *
     * Make sure to implement the buildLastTable() method before implementing
     * this method.
     *
     * Note: You may find the getOrDefault() method from Java's Map class
     * useful.
     *
     * @param pattern    the pattern you are searching for in a body of text
     * @param text       the body of text where you search for the pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> boyerMoore(CharSequence pattern,
                                           CharSequence text,
                                           CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is null or has length 0.");
        } else if (text == null || comparator == null) {
            throw new IllegalArgumentException("The text or comparator is null.");
        } else {
            List<Integer> indexList = new ArrayList<>();
            Map<Character, Integer> last = buildLastTable(pattern);
            int i = 0;
            while (i <= text.length() - pattern.length()) {
                int j = pattern.length() - 1;
                while (j >= 0 && comparator.compare(text.charAt(i + j), pattern.charAt(j)) == 0) {
                    j--;
                }
                if (j == -1) {
                    indexList.add(i++);
                } else {
                    int shift = last.getOrDefault(text.charAt(i + j), -1);
                    if (shift < j) {
                        i += j - shift;
                    } else {
                        i++;
                    }
                }
            }
            return indexList;
        }
    }

    /**
     * Builds last occurrence table that will be used to run the Boyer Moore
     * algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. pattern = octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     *
     * @param pattern a pattern you are building last table for
     * @return a Map with keys of all of the characters in the pattern mapping
     * to their last occurrence in the pattern
     * @throws java.lang.IllegalArgumentException if the pattern is null
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        if (pattern == null) {
            throw new IllegalArgumentException("The pattern is null.");
        } else {
            int m = pattern.length();
            Map<Character, Integer> table = new HashMap<>();
            for (int i = 0; i < m; i++) {
                table.put(pattern.charAt(i), i);
            }
            return table;
        }
    }

    /**
     * Prime base used for Rabin-Karp hashing.
     * DO NOT EDIT!
     */
    private static final int BASE = 113;

    /**
     * Runs the Rabin-Karp algorithm. This algorithms generates hashes for the
     * pattern and compares this hash to substrings of the text before doing
     * character by character comparisons.
     *
     * When the hashes are equal and you do character comparisons, compare
     * starting from the beginning of the pattern to the end, not from the end
     * to the beginning.
     *
     * You must use the Rabin-Karp Rolling Hash for this implementation. The
     * formula for it is:
     *
     * sum of: c * BASE ^ (pattern.length - 1 - i)
     *   c is the integer value of the current character, and
     *   i is the index of the character
     *
     * We recommend building the hash for the pattern and the first m characters
     * of the text by starting at index (m - 1) to efficiently exponentiate the
     * BASE. This allows you to avoid using Math.pow().
     *
     * Note that if you were dealing with very large numbers here, your hash
     * will likely overflow; you will not need to handle this case.
     * You may assume that all powers and calculations CAN be done without
     * overflow. However, be careful with how you carry out your calculations.
     * For example, if BASE^(m - 1) is a number that fits into an int, it's
     * possible for BASE^m will overflow. So, you would not want to do
     * BASE^m / BASE to calculate BASE^(m - 1).
     *
     * Ex. Hashing "bunn" as a substring of "bunny" with base 113
     * = (b * 113 ^ 3) + (u * 113 ^ 2) + (n * 113 ^ 1) + (n * 113 ^ 0)
     * = (98 * 113 ^ 3) + (117 * 113 ^ 2) + (110 * 113 ^ 1) + (110 * 113 ^ 0)
     * = 142910419
     *
     * Another key point of this algorithm is that updating the hash from
     * one substring to the next substring must be O(1). To update the hash,
     * subtract the oldChar times BASE raised to the length - 1, multiply by
     * BASE, and add the newChar as shown by this formula:
     * (oldHash - oldChar * BASE ^ (pattern.length - 1)) * BASE + newChar
     *
     * Ex. Shifting from "bunn" to "unny" in "bunny" with base 113
     * hash("unny") = (hash("bunn") - b * 113 ^ 3) * 113 + y
     *              = (142910419 - 98 * 113 ^ 3) * 113 + 121
     *              = 170236090
     *
     * Keep in mind that calculating exponents is not O(1) in general, so you'll
     * need to keep track of what BASE^(m - 1) is for updating the hash.
     *
     * Do NOT use Math.pow() in this method.
     *
     * @param pattern    a string you're searching for in a body of text
     * @param text       the body of text where you search for pattern
     * @param comparator you MUST use this to check if characters are equal
     * @return list containing the starting index for each match found
     * @throws java.lang.IllegalArgumentException if the pattern is null or has
     *                                            length 0
     * @throws java.lang.IllegalArgumentException if text or comparator is null
     */
    public static List<Integer> rabinKarp(CharSequence pattern,
                                          CharSequence text,
                                          CharacterComparator comparator) {
        if (pattern == null || pattern.length() == 0) {
            throw new IllegalArgumentException("The pattern is null or has length 0.");
        } else if (text == null || comparator == null) {
            throw new IllegalArgumentException("The text or comparator is null.");
        } else {
            List<Integer> indexList = new ArrayList<>();
            if (pattern.length() > text.length()) {
                return indexList;
            }
            int pHash = hash(pattern);
            int nHash = hash(text.subSequence(0, pattern.length()));

            if (pHash == nHash && check(pattern,
                    text.subSequence(0, pattern.length()), comparator)) {
                indexList.add(0);
            }
            for (int i = 1; i < text.length() - pattern.length() + 1; i++) {
                nHash = (nHash - (text.charAt(i - 1)
                        * power(BASE, pattern.length() - 1)))
                        * BASE + text.charAt(i + pattern.length() - 1);
                if (nHash == pHash && check(pattern,
                        text.subSequence(i, i + pattern.length()), comparator)) {
                    indexList.add(i);
                }
            }
            return indexList;
        }
    }

    /**
     * Calculates hash
     *
     * @param text string that will get calculated
     * @return hash value of a string
     */
    private static int hash(CharSequence text) {
        int tmp = 0;
        for (int i = 0; i < text.length(); i++) {
            tmp += text.charAt(i) * power(BASE, text.length() - 1 - i);
        }
        return tmp;
    }

    /**
     * Alternative to Math.pow()
     *
     * @param a number that will get multiplied
     * @param b times that 'a' gets multiplied
     * @return value of a^b
     */
    private static int power(int a, int b) {
        if (b == 0) {
            return 1;
        } else {
            return a * power(a, b - 1);
        }
    }

    /**
     * Checks if text and pattern matches
     *
     * @param pattern a string you're searching for in a body of text
     * @param text the body of text where you search for pattern
     * @param comparator the comparator to use when checking character equality
     * @return if pattern and text matches
     */
    private static boolean check(CharSequence pattern, CharSequence text,
                                 CharacterComparator comparator) {
        for (int i = 0; i < pattern.length(); i++) {
            if (comparator.compare(pattern.charAt(i), text.charAt(i)) != 0) {
                return false;
            }
        }
        return true;
    }
}
