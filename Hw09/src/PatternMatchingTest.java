import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/**
 * This week Jack Smalligan and Ruston Shome have again teamed up to create a set of test cases together! We decided to
 * continue with this because we feel it worked quite well the first time. Overall it's been very
 * fun collaborating, and we hope you enjoy the results!
 *
 * As always, we encourage you to run and pass TA provided JUnits.
 *
 * Designed To Test For PM Algorithms:
 * 1. Match at beginning of text
 * 2. Match at end of text
 * 3. Match in the middle of text
 * 4. Multiple Matches
 * 5. Adjacent Repeated Matches
 * 6. Near Matches
 * 7. No Matches
 * 8. Pattern Same as Text
 * 9. Pattern Repeats Internally
 * 10. Pattern is Longer Than Text
 * 11. Text is Empty
 * 12. Pattern and Text Have Same Length
 * 13. Pattern is a single character
 * 14. Text is long
 * 15. Non-Alphanumeric
 * 16. Pattern is long (N/A TO Rabin Karp due to Overflow)
 * 17. No output to console from any method
 *
 *
 * Designed to Test For Tables:
 *  1. For Last Occurrence Table:
 *      a. Empty pattern
 *      b. All distinct characters
 *      c. Repeated characters in longer pattern
 *      d. All the same character
 *      e. Only two characters present
 *      f. Non-standard characters
 *  2. For failure table
 *      a. Empty pattern
 *      b. All distinct characters (no prefixes)
 *      c. Many repeated chars, no prefixes
 *      d. Prefixes present
 *      e. Reset situations to earlier, non-index-0 prefix points
 *      f. All the same character
 *      g. Repetitive patterns
 *      h. Non-standard characters
 *      i. Only one character
 *
 * Not Designed to Test:
 *  1. Efficiency, beyond the mere comparison count
 *  2. Checkstyle, ensuring private visibility of helper methods (all of the things in bullet 2 require reflection,
 *      which we opt not to deal with)
 *
 *  @author Ruston Shome
 *  @author Jack Smalligan
 *  @version 1.0
 *  @link https://github.gatech.edu/gist/jsmalligan3/9942b1b4b617f3ea6cfe01f34a294850
 */
public class PatternMatchingTest {

    private CharacterComparator comparator;
    private Map<Character, Integer> expLastOccTable;
    private int[] expFailureTable;
    private List<Integer> expMatches;
    private CharSequence pattern;
    private CharSequence text;

    private final static PrintStream systemOut = System.out;

    /**
     * If you are getting a time-out error, there is a very high chance your code produces an infinite loop.
     * Check the base/break case and increment on any recursive code or While loops
     * Check the indices and increment on any For loops
     */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    @Before
    public void setup() {
        comparator = new CharacterComparator();
        expLastOccTable = new HashMap<>();
        expMatches = new ArrayList<>();
    }


    // These methods test that no output is sent to console
    private ByteArrayOutputStream outStream;
    @Before
    public void setupConsoleMonitor() {
        outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
    }

    @After
    public void checkConsole() {
        System.setOut(systemOut);
        String flush = outStream.toString();
        // for the sake of those using System.out to debug, flush the buffer
        System.out.print(flush);
        assertEquals("", flush);
    }

    /**************************************************************************************
     KMP
     ***********************************************************************************/

    @Test
    public void kmpBeginMatch() {
        List<Integer> res = PatternMatching.kmp("ACGTAC", "ACGTACGACTGAGGC", comparator);
        assertEquals(res, Arrays.asList(0));

        // A|C|G|T|A|C|G|A|C|T|G|A|G|G|C
        // A|C|G|T|A|C
        // - - - - - -
        //         A|C|G|T|A|C
        //             - -
        //               A|C|G|T|A|C
        //               _ _ _
        //                   A|C|G|T|A|C
        //                   _

        // 12 comp shown above, plus 5 from making the failure table

        assertEquals(17, comparator.getComparisonCount());
    }

    @Test
    public void kmpEndMatch() {
        List<Integer> res = PatternMatching.kmp("GGCGG", "AGGCGTAACTGAGGCGG", comparator);
        assertEquals(res, List.of(12));

        // A|G|G|C|G|T|A|A|C|T|G|A|G|G|C|G|G
        // G|G|C|G|G
        // -
        //   G|G|C|G|G
        //   _ _ _ _ _
        //         G|G|C|G|G
        //           _
        //           G|G|C|G|G
        //           _
        //             G|G|C|G|G
        //             _
        //               G|G|C|G|G
        //               _
        //                 G|G|C|G|G
        //                 _
        //                   G|G|C|G|G
        //                   _
        //                     G|G|C|G|G
        //                     _ _
        //                       G|G|C|G|G
        //                       _
        //                         G|G|C|G|G
        //                         _ _ _ _ _


        // 20 comp shown above, plus 5 from making the failure table

        assertEquals(25, comparator.getComparisonCount());
    }

    @Test
    public void kmpMiddleMatch() {
        List<Integer> res = PatternMatching.kmp("GGCAG", "AGGCGTAGGCAGGACTGA", comparator);
        // A|G|G|C|G|T|A|G|G|C|A|G|G|A|C|T|G|A
        // G|G|C|A|G
        // _
        //   G|G|C|A|G
        //   _ _ _ _
        //         G|G|C|A|G
        //         _ _
        //           G|G|C|A|G
        //           _
        //             G|G|C|A|G
        //             _
        //               G|G|C|A|G
        //               _ _ _ _ _
        //                       G|G|C|A|G
        //                         _ _
        //                         G|G|C|A|G
        //                           _
        //                           G|G|C|A|G
        //                           -
        // -----------------------------------
        // A|G|G|C|G|T|A|G|G|C|A|G|G|A|C|T|G|A       (text repeated for ease)
        assertEquals(res, List.of(7));
        // 18 comp shown, plus 5 for making the table
        assertEquals(23, comparator.getComparisonCount());
    }

    @Test
    public void kmpMultMatches() {
        List<Integer> res = PatternMatching.kmp("GGCCGG", "AGGCCGGTAGGCCGGAGGACTGAGGCGG", comparator);
        // A|G|G|C|C|G|G|T|A|G|G|C|C|G|G|A|G|G|A|C|T|G|A|G|G|C|G|G
        // G|G|C|C|G|G
        // _
        //   G|G|C|C|G|G
        //   _ _ _ _ _ _
        //           G|G|C|C|G|G
        //               _
        //             G|G|C|C|G|G
        //               _
        // -------------------------------------------------------
        // A|G|G|C|C|G|G|T|A|G|G|C|C|G|G|A|G|G|A|C|T|G|A|G|G|C|G|G       (text repeated for ease)
        // -------------------------------------------------------
        //               G|G|C|C|G|G
        //               _
        //                 G|G|C|C|G|G
        //                 _
        //                   G|G|C|C|G|G
        //                   _ _ _ _ _ _
        //                           G|G|C|C|G|G
        //                               _
        //                             G|G|C|C|G|G
        //                               _
        //                               G|G|C|C|G|G
        //                               _
        //                                 G|G|C|C|G|G
        //                                 _ _ _
        // -------------------------------------------------------
        // A|G|G|C|C|G|G|T|A|G|G|C|C|G|G|A|G|G|A|C|T|G|A|G|G|C|G|G       (text repeated for ease)
        // -------------------------------------------------------
        //                                   G|G|C|C|G|G
        //                                     _
        //                                     G|G|C|C|G|G
        //                                     _
        //                                       G|G|C|C|G|G
        //                                       _
        //                                         G|G|C|C|G|G
        //                                         _
        //                                           G|G|C|C|G|G
        //                                           _ _
        //                                             G|G|C|C|G|G
        //                                             _
        // -------------------------------------------------------
        // A|G|G|C|C|G|G|T|A|G|G|C|C|G|G|A|G|G|A|C|T|G|A|G|G|C|G|G       (text repeated for ease)
        assertEquals(Arrays.asList(1, 9), res);
        // 30 comp shown, plus 6 for making the table
        assertEquals(36, comparator.getComparisonCount());
    }

    @Test
    public void kmpAdjacentMatches() {
        List<Integer> res = PatternMatching.kmp("AGGAGGA", "AGGAGGAAGGAGGAAGGAGGA", comparator);
        // A|G|G|A|G|G|A|A|G|G|A|G|G|A|A|G|G|A|G|G|A
        // -------------------------------------------
        // A|G|G|A|G|G|A
        // _ _ _ _ _ _ _
        //       A|G|G|A|G|G|A
        //               _
        //             A|G|G|A|G|G|A
        //               _
        //               A|G|G|A|G|G|A
        //               _ _ _ _ _ _ _
        //                     A|G|G|A|G|G|A
        //                             _
        //                           A|G|G|A|G|G|A
        //                             _
        //                             A|G|G|A|G|G|A
        //                             _ _ _ _ _ _ _
        // -------------------------------------------
        // A|G|G|A|G|G|A|A|G|G|A|G|G|A|A|G|G|A|G|G|A      (text repeated for ease)

        assertEquals(Arrays.asList(0, 7, 14), res);
        // 25 comp shown, plus 6 for making the table
        assertEquals(31, comparator.getComparisonCount());
    }

    @Test
    public void kmpNearMatches() {
        List<Integer> res = PatternMatching.kmp("GCACG", "GCACGCACACGCAGCGCAC", comparator);

        // G|C|A|C|G|C|A|C|A|C|G|C|A|G|C|G|C|A|C
        // G|C|A|C|G
        // _ _ _ _ _
        //         G|C|A|C|G
        //           _ _ _ _
        //                 G|C|A|C|G
        //                 _
        //                   G|C|A|C|G
        //                   _
        //                     G|C|A|C|G
        //                     _ _ _ _
        //                           G|C|A|C|G
        //                           _ _ _
        // --------------------------------------
        // G|C|A|C|G|C|A|C|A|C|G|C|A|G|C|G|C|A|C  (text repeated for ease)

        assertEquals(Arrays.asList(0), res);
        // 18 comp shown, plus 4 for making the table
        assertEquals(22, comparator.getComparisonCount());
    }

    @Test
    public void kmpOverlappingMatches() {
        List<Integer> res = PatternMatching.kmp("AGA", "AGAGAAGAGAGAGGA", comparator);
        // A|G|A|G|A|A|G|A|G|A|G|A|G|G|A
        // ------------------------------
        // A|G|A
        // _ _ _
        //     A|G|A
        //       _ _
        //         A|G|A
        //           _
        //           A|G|A
        //           _ _ _
        //               A|G|A
        //                 _ _
        //                   A|G|A
        //                     _ _
        //                       A|G|A
        //                         _ _
        // ------------------------------
        // A|G|A|G|A|A|G|A|G|A|G|A|G|G|A     (text repeated for ease)


        assertEquals(Arrays.asList(0, 2, 5, 7, 9), res);
        // 15 comp shown, plus 2 for making the table
        assertEquals(17, comparator.getComparisonCount());
    }

    @Test
    public void kmpNoMatch() {
        List<Integer> res = PatternMatching.kmp("AGAC", "AGAGAAGAGAGAGGA", comparator);
        // A|G|A|G|A|A|G|A|G|A|G|A|G|G|A
        // ------------------------------
        // A|G|A|C
        // _ _ _ _
        //     A|G|A|C
        //       _ _ _
        //         A|G|A|C
        //           _
        //           A|G|A|C
        //           _ _ _ _
        //               A|G|A|C
        //                 _ _ _
        //                   A|G|A|C
        //                     _ _ _
        //                       A|G|A|C
        //                         _ _
        // ------------------------------
        // A|G|A|G|A|A|G|A|G|A|G|A|G|G|A     (text repeated for ease)


        assertEquals(Arrays.asList(), res);
        // 20 comp shown, plus 4 for making the table
        assertEquals(24, comparator.getComparisonCount());
    }

    @Test
    public void kmpPatternEqualsText() {
        List<Integer> res = PatternMatching.kmp("AGCAAAGC", "AGCAAAGC", comparator);

        assertEquals(Arrays.asList(0), res);
        assertTrue(8 == comparator.getComparisonCount() /*If table not made*/ ||
                17 == comparator.getComparisonCount() /*If table is made*/);
    }

    @Test
    public void kmpPatternLongerThanText() {
        List<Integer> res = PatternMatching.kmp("AGCAAAG", "AGCAAA", comparator);

        assertEquals(Arrays.asList(), res);
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test
    public void kmpEmptyText() {
        List<Integer> res = PatternMatching.kmp("A", "", comparator);

        assertEquals(Arrays.asList(), res);
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test
    public void kmpPatternAndTextSameLength() {
        List<Integer> res = PatternMatching.kmp("AGC", "ACG", comparator);

        assertEquals(Arrays.asList(), res);
        assertTrue(2 == comparator.getComparisonCount() || // no table made
                4 == comparator.getComparisonCount()
                );
    }

    @Test
    public void kmpSingleCharPattern() {
        List<Integer> res = PatternMatching.kmp("A", "AGCGAATACGTAAGCAC", comparator);

        assertEquals(Arrays.asList(0, 4, 5, 7, 11, 12, 15), res);
        assertEquals(17, comparator.getComparisonCount());
    }

    @Test
    public void kmpLongText() {
        // courtesy https://en.wikipedia.org/wiki/Knuth%E2%80%93Morris%E2%80%93Pratt_algorithm
        CharSequence text = "In computer science, the Knuth–Morris–Pratt string-searching algorithm (or KMP" +
                " algorithm) searches for occurrences of a \"word\" W within a main \"text string\" S by employing" +
                " the observation that when a mismatch occurs, the word itself embodies sufficient information to" +
                " determine where the next match could begin, thus bypassing re-examination of previously matched" +
                " characters.\n" +
                "\n" +
                "The algorithm was conceived by James H. Morris and independently discovered by Donald Knuth \"a few" +
                " weeks later\" from automata theory.[1][2] Morris and Vaughan Pratt published a technical report " +
                "in 1970.[3] The three also published the algorithm jointly in 1977.[1] Independently, in 1969, " +
                "Matiyasevich[4][5] discovered a similar algorithm, coded by a" +
                " two-dimensional Turing machine, while " +
                "studying a string-pattern-matching recognition problem over a binary alphabet. This was the first " +
                "linear-time algorithm for string matching.[6]" +
                "A string-matching algorithm wants to find the starting index m in string S[] that matches the " +
                "search word W[].\n" +
                "\n" +
                "The most straightforward algorithm, known as the \"Brute-force\" or \"Naive\" algorithm, is to look" +
                " for a word match at each index m, i.e. the position in the string being searched that corresponds" +
                " to the character S[m]. At each position m the algorithm first checks for equality of the first" +
                " character in the word being searched, i.e. S[m] =? W[0]. If a match is found, the algorithm tests" +
                " the other characters in the word being searched by checking successive values of the word position" +
                " index, i. The algorithm retrieves the character W[i] in the word being searched and checks for" +
                " equality of the expression S[m+i] =? W[i]. If all successive characters match in W at position" +
                " m, then a match is found at that position in the search string. If the index m reaches the end of" +
                " the string then there is no match, in which case the search is said to \"fail\".\n" +
                "\n" +
                "Usually, the trial check will quickly reject the trial match. If the strings are uniformly" +
                " distributed random letters, then the chance that characters match is 1 in 26. In most cases, the " +
                "trial check will reject the match at the initial letter. The chance that the first two letters will" +
                " match is 1 in 262 (1 in 676). So if the characters are random, then the expected complexity of" +
                " searching string S[] of length n is on the order of n comparisons or O(n). The expected performance" +
                " is very good. If S[] is 1 million characters and W[] is 1000 characters, then the string search" +
                " should complete after about 1.04 million character comparisons.\n" +
                "\n" +
                "That expected performance is not guaranteed. If the strings are not random, then checking a trial m " +
                "may take many character comparisons. The worst case is if the two strings match in all but the last " +
                "letter. Imagine that the string S[] consists of 1 million characters that are all A, and that " +
                "the word W[] is 999 A characters terminating in a final B character. The simple string-matching " +
                "algorithm will now examine 1000 characters at each trial position before rejecting the match and" +
                " advancing the trial position. The simple string search example would now take about 1000 character" +
                " comparisons times 1 million positions for 1 billion character comparisons. If the length of W[] is" +
                " k, then the worst-case performance is O(k⋅n).\n" +
                "\n" +
                "The KMP algorithm has a better worst-case performance than the straightforward algorithm. KMP spends" +
                " a little time precomputing a table (on the order of the size of W[], O(k)), and then it uses that" +
                " table to do an efficient search of the string in O(n).\n" +
                "\n" +
                "The difference is that KMP makes use of previous match" +
                " information that the straightforward algorithm" +
                " does not. In the example above, when KMP sees a trial match fail on the 1000th character (i = 999)" +
                " because S[m+999] ≠ W[999], it will increment m by 1, but it will know that the first 998" +
                " characters at the new position already match. KMP matched 999 A characters before discovering" +
                " a mismatch at the 1000th character (position 999). Advancing the trial match position m by one" +
                " throws away the first A, so KMP knows there are 998 A characters that match W[] and does not retest" +
                " them; that is, KMP sets i to 998. KMP maintains its knowledge in the precomputed table and two" +
                " state variables. When KMP discovers a mismatch, the table determines how much KMP will increase" +
                " (variable m) and where it will resume testing (variable i).";

        assertEquals(Arrays.asList(21, 172, 217, 284, 503, 608, 854, 951, 999, 1066, 1156, 1172, 1218, 1257, 1300,
                1323, 1388, 1408, 1413, 1432, 1489, 1541, 1563, 1614, 1700, 1742, 1764, 1784, 1795, 1806, 1811, 1844,
                1884, 1920, 1940, 1994, 1999, 2059, 2087, 2100, 2136, 2199, 2226, 2231, 2297, 2431, 2436, 2567, 2595,
                2677, 2710, 2740, 2813, 2992, 3016, 3183, 3207, 3212, 3310, 3391, 3404, 3432, 3485, 3578, 3621, 3676,
                3782, 3810, 3904, 3951, 3997, 4023, 4085, 4150, 4228),
                PatternMatching.kmp("the", text, comparator));
        assertEquals(4618, comparator.getComparisonCount());
    }

    @Test
    public void kmpLongPattern() {
        CharSequence text = "To be, or not to be, that is the question,\n" +
                "Whether 'tis nobler in the mind to suffer\n" +
                "The slings and arrows of outrageous fortune,\n" +
                "Or to take arms against a sea of troubles,\n" +
                "And by opposing end them? To die: to sleep;\n" +
                "No more; and by a sleep to say we end\n" +
                "The heart-ache and the thousand natural shocks\n" +
                "That flesh is heir to, 'tis a consummation\n" +
                "Devoutly to be wish'd. To die, to sleep;\n" +
                "To sleep: perchance to dream: ay, there's the rub;\n" +
                "For in that sleep of death what dreams may come\n" +
                "When we have shuffled off this mortal coil,\n" +
                "Must give us pause: there's the respect\n" +
                "That makes calamity of so long life;\n" +
                "For who would bear the whips and scorns of time,\n" +
                "The oppressor's wrong, the proud man's contumely,\n" +
                "The pangs of despised love, the law's delay,\n" +
                "The insolence of office and the spurns\n" +
                "That patient merit of the unworthy takes,\n" +
                "When he himself might his quietus make\n" +
                "With a bare bodkin? who would fardels bear,\n" +
                "To grunt and sweat under a weary life,\n" +
                "But that the dread of something after death,\n" +
                "The undiscover'd country from whose bourn\n" +
                "No traveller returns, puzzles the will\n" +
                "And makes us rather bear those ills we have\n" +
                "Than fly to others that we know not of?\n" +
                "Thus conscience does make cowards of us all;\n" +
                "And thus the native hue of resolution\n" +
                "Is sicklied o'er with the pale cast of thought,\n" +
                "And enterprises of great pith and moment\n" +
                "With this regard their currents turn awry,\n" +
                "And lose the name of action.--Soft you now!\n" +
                "The fair Ophelia! Nymph, in thy orisons\n" +
                "Be all my sins remember'd.";

        assertEquals(Arrays.asList(629),
                PatternMatching.kmp(
                        "whips and scorns of time,\nThe oppressor's wrong, the proud", text, comparator
                ));
        assertEquals(1511, comparator.getComparisonCount());
    }

    @Test
    public void kmpNonStandardCharacters() {
        CharSequence text = "κατέβην χθὲς εἰς Πειραιᾶ μετὰ Γλαύκωνος τοῦ Ἀρίστωνος προσευξόμενός τε τῇ θεῷ καὶ ἅμα τὴν " +
                "ἑορτὴν βουλόμενος θεάσασθαι τίνα τρόπον ποιήσουσιν ἅτε νῦν πρῶτον ἄγοντες. καλὴ μὲν οὖν μοι καὶ ἡ" +
                " τῶν ἐπιχωρίων πομπὴ ἔδοξεν εἶναι, οὐ μέντοι ἧττον ἐφαίνετο πρέπειν ἣν οἱ Θρᾷκες ἔπεμπον." +
                " προσευξάμενοι δὲ καὶ θεωρήσαντες ἀπῇμεν πρὸς τὸ ἄστυ. κατιδὼν οὖν πόρρωθεν ἡμᾶς οἴκαδε ὡρμημένους" +
                " Πολέμαρχος ὁ Κεφάλου ἐκέλευσε δραμόντα τὸν παῖδα περιμεῖναί ἑ κελεῦσαι. καί μου ὄπισθεν ὁ παῖς " +
                "λαβόμενος τοῦ ἱματίου, κελεύει ὑμᾶς, ἔφη, Πολέμαρχος περιμεῖναι. καὶ ἐγὼ μετεστράφην τε καὶ ἠρόμην ὅπου " +
                "αὐτὸς εἴη. οὗτος, ἔφη, ὄπισθεν προσέρχεται: ἀλλὰ περιμένετε. ἀλλὰ περιμενοῦμεν, ἦ δ᾽ ὃς ὁ Γλαύκων.";

        assertEquals(Arrays.asList(78, 182, 294, 535, 558),
                PatternMatching.kmp(
                        "καὶ", text, comparator
                ));
        assertEquals(683, comparator.getComparisonCount());

    }

    /**************************************************************************************
     Build Failure Table
     ***********************************************************************************/

    @Test
    public void buildFailureTableEmptyPattern() {
        expFailureTable = new int[0];

        assertArrayEquals(expFailureTable, PatternMatching.buildFailureTable("", comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test
    public void buildFailureTableSingleCharPattern() {
        expFailureTable = new int[] {0};
        assertArrayEquals(expFailureTable, PatternMatching.buildFailureTable("A", comparator));
        assertEquals(0, comparator.getComparisonCount());
    }

    @Test
    public void buildFailureTableDistinctCharacters() {
        expFailureTable = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        assertArrayEquals(expFailureTable, PatternMatching.buildFailureTable(
                "abcdefghiklmnpoq", comparator)
        );
        assertEquals(15, comparator.getComparisonCount());
    }

    @Test
    public void buildFailureTableRepetitiousNoPrefixes() {
        expFailureTable = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        assertArrayEquals(expFailureTable, PatternMatching.buildFailureTable(
                "abccbddbccddccbbcc", comparator)
        );
        assertEquals(17, comparator.getComparisonCount());
    }

    @Test
    public void buildFailureTableNonNestedPrefixes() {
        expFailureTable = new int[] {0, 1, 0, 0, 1, 0, 0, 1, 2, 0, 0, 1, 2, 3, 4, 0, 1, 2, 3, 4, 5, 6, 0};
        assertArrayEquals(expFailureTable, PatternMatching.buildFailureTable(
                "aabcadcaadeaabcdaabcade", comparator)
        );

        // a a b c a d c a a d e a a b c d a a b c a d e
        //   _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _
        //           _       _           _             _
        //                   _           _
        assertEquals(28, comparator.getComparisonCount());
    }

    @Test
    public void buildFailureTableNestedPrefixes() {
        expFailureTable = new int[] {0, 1, 0, 0, 1, 2, 2, 2, 3, 4, 5, 6, 3, 4, 5, 6, 7, 8, 0};
        assertArrayEquals(expFailureTable, PatternMatching.buildFailureTable(
                "AACGAAAACGAACGAAAAG", comparator)
        );
        // 0 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 17 18
        // A A C G A A A A C G A  A  C  G  A  A  A  A  G
        //   - - - - - - - - - -  -  -  -  -  -  -  -  -
        //     -       - -           -                 -
        //                                             -


        assertEquals(25, comparator.getComparisonCount());
    }

    @Test
    public void buildFailureTableMonochar() {
        expFailureTable = new int[] {0, 1, 2, 3, 4, 5};
        assertArrayEquals(expFailureTable, PatternMatching.buildFailureTable(
                "aaaaaa", comparator)
        );
        // 0 1 2 3 4 5
        // a a a a a a
        //   - - - - -

        assertEquals(5, comparator.getComparisonCount());
    }

    @Test
    public void buildFailureTableRepetitive() {
        expFailureTable = new int[] {0, 0, 0, 0, 0, 1, 2, 3, 4, 1, 2, 3, 1, 2, 3, 1, 0, 0, 1, 2, 3, 1, 2, 1, 2, 3, 4, 1, 2, 1, 2, 3, 1, 1, 2, 1, 1, 1, 2, 3};
        assertArrayEquals(expFailureTable, PatternMatching.buildFailureTable(
                "abbccabbcabbabbaccabbababbcababbaabaaabb", comparator)
        );

        assertEquals(52, comparator.getComparisonCount());
    }

    @Test
    public void buildFailureTableNonStandardCharacters() {
        expFailureTable = new int[] {0, 0, 0, 0, 0, 1, 2, 3, 4, 5, 0, 0, 0, 0};
        assertArrayEquals(expFailureTable, PatternMatching.buildFailureTable(
                "αμην αμην λεγω", comparator)
        );
        assertEquals(14, comparator.getComparisonCount());
    }

    /**************************************************************************************
     Boyer-Moore
     ***********************************************************************************/
    @Test
    public void boyerMooreBeginningMatch() {
        pattern = "moo";
        text = "moowoofmeowribbet";
        //moo (3)
        //oow (1)
        //oof (1)
        //meo (2)
        //owr (1)
        //ibb (1)
        expMatches.add(0);
        Assert.assertEquals(expMatches, PatternMatching.boyerMoore(pattern, text, comparator));
        Assert.assertEquals(9, comparator.getComparisonCount());
    }

    @Test
    public void boyerMooreEndMatch() {
        pattern = "meow";
        text = "moowoofribbetmeow";
        //moow (3)
        //oowo (1)
        //owoo (1)
        //woof (1)
        //ribb (1)
        //etme (1)
        //meow (4)
        expMatches.add(13);
        Assert.assertEquals(expMatches, PatternMatching.boyerMoore(pattern, text, comparator));
        Assert.assertEquals(12, comparator.getComparisonCount());
    }

    @Test
    public void boyerMooreMiddleMatch() {
        pattern = "boo";
        text = "moowoofbooribbetmeow";
        //moo (3)
        //oow (1)
        //oof (1)
        //boo (3)
        //oor (1)
        //ibb (1)
        //bet (1)
        //meo (2)

        expMatches.add(7);
        Assert.assertEquals(expMatches, PatternMatching.boyerMoore(pattern, text, comparator));
        Assert.assertEquals(13, comparator.getComparisonCount());
    }

    @Test
    public void boyerMooreMultiMatch() {
        pattern = "mooo";
        text = "mooowoofmeowmoooomeowmooomeowribbetribbetmoooribbetmooo";
        //mooo (4)
        //ooow (1)
        //oofm (1)
        //meow (1)
        //mooo (4)
        //oooo (4)
        //ooom (1)
        //meow (1)
        //mooo (4)
        //ooom (1)
        //meow (1)
        //ribb (1)
        //etri (1)
        //bbet (1)
        //mooo (4)
        //ooor (1)
        //ibbe (1)
        //tmoo (3)
        //mooo (4)
        expMatches.add(0);
        expMatches.add(12);
        expMatches.add(21);
        expMatches.add(41);
        expMatches.add(51);
        Assert.assertEquals(expMatches, PatternMatching.boyerMoore(pattern, text, comparator));
        Assert.assertEquals(39, comparator.getComparisonCount());
    }

    @Test
    public void boyerMooreRepeatedMatch() {
        pattern = "moo";
        text = "mooomooomooomoomooomoomoomooomooo";
        //moo (3)
        //ooo (3)
        //oom (1)
        //moo (3)
        //ooo (3)
        //oom (1)
        //moo (3)
        //ooo (3)
        //oom (1)
        //moo (3)
        //oom (1)
        //moo (3)
        //ooo (3)
        //oom (1)
        //moo (3)
        //oom (1)
        //moo (3)
        //oom (1)
        //moo (3)
        //ooo (3)
        //oom (1)
        //moo (3)
        //ooo (3)
        expMatches.add(0);
        expMatches.add(4);
        expMatches.add(8);
        expMatches.add(12);
        expMatches.add(15);
        expMatches.add(19);
        expMatches.add(22);
        expMatches.add(25);
        expMatches.add(29);
        Assert.assertEquals(expMatches, PatternMatching.boyerMoore(pattern, text, comparator));
        Assert.assertEquals(53, comparator.getComparisonCount());
    }

    @Test
    public void boyerMooreNearMatch() {
        pattern = "abcd";
        text = "cbcdcbcdabcdcbcdcabcdcbcd";
        //cbcd (4)
        //bcdc (1)
        //cdcb (1)
        //cbcd (4)
        //bcda (1)
        //abcd (4)
        //bcdc (1)
        //cdcb (1)
        //cbcd (4)
        //bcdc (1)
        //cdca (1)
        //abcd (4)
        //bcdc (1)
        //cdcb (1)
        //cbcd (4)
        expMatches.add(8);
        expMatches.add(17);
        Assert.assertEquals(expMatches, PatternMatching.boyerMoore(pattern, text, comparator));
        Assert.assertEquals(33, comparator.getComparisonCount());
    }

    @Test
    public void boyerMooreNoMatch() {
        pattern = "meow";
        text = "The Owl always takes her sleep during the day. Then after sundown, when the rosy light fades from " +
                "the sky and the shadows rise slowly through the wood, out she comes ruffling and blinking from the " +
                "old hollow tree. Now her weird 'hoo-hoo-hoo-oo-oo' echoes through the quiet wood, and she begins " +
                "her hunt for the bugs and beetles, frogs and mice she likes so well to eat.";
        Assert.assertEquals(expMatches, PatternMatching.boyerMoore(pattern, text, comparator));
        Assert.assertEquals(108, comparator.getComparisonCount());
    }

    @Test
    public void boyerMoorePatternSameText() {
        pattern = "woof";
        text = "woof";
        expMatches.add(0);
        Assert.assertEquals(expMatches, PatternMatching.boyerMoore(pattern, text, comparator));
        Assert.assertEquals(4, comparator.getComparisonCount());
    }

    @Test
    public void boyerMoorePeriodicPattern() {
        pattern = "abab";
        text = "ababababababcababab";
        //abab (4)
        //baba (1)
        //abab (4)
        //baba (1)
        //abab (4)
        //baba (1)
        //abab (4)
        //baba (1)
        //abab (4)
        //babc (1)
        //abab (4)
        //baba (1)
        //abab (4)
        expMatches.add(0);
        expMatches.add(2);
        expMatches.add(4);
        expMatches.add(6);
        expMatches.add(8);
        expMatches.add(13);
        expMatches.add(15);
        Assert.assertEquals(expMatches, PatternMatching.boyerMoore(pattern, text, comparator));
        Assert.assertEquals(34, comparator.getComparisonCount());
    }

    @Test
    public void boyerMooreLongerPattern() {
        pattern = "ababababababcababababbababcbabcabcbabcb";
        text = "abab";

        Assert.assertEquals(expMatches, PatternMatching.boyerMoore(pattern, text, comparator));
        Assert.assertEquals(0, comparator.getComparisonCount());
    }

    @Test
    public void boyerMooreEmptyText() {
        pattern = "m";
        text = "";

        Assert.assertEquals(expMatches, PatternMatching.boyerMoore(pattern, text, comparator));
        Assert.assertEquals(0, comparator.getComparisonCount());
    }

    @Test
    public void boyerMooreSameLength() {
        pattern = "cats";
        text = "scat";

        Assert.assertEquals(expMatches, PatternMatching.boyerMoore(pattern, text, comparator));
        assertEquals(1, comparator.getComparisonCount());
    }

    @Test
    public void boyerMooreSingleChar() {
        pattern = "a";
        text = "aFar away and long ago, off in a dark forest, lived a peaceful little witch named 'Ruston'.a";
        expMatches.add(0);
        expMatches.add(2);
        expMatches.add(5);
        expMatches.add(7);
        expMatches.add(10);
        expMatches.add(19);
        expMatches.add(31);
        expMatches.add(34);
        expMatches.add(52);
        expMatches.add(56);
        expMatches.add(77);
        expMatches.add(91);
        Assert.assertEquals(expMatches, PatternMatching.boyerMoore(pattern, text, comparator));
        Assert.assertEquals(92, comparator.getComparisonCount());
    }

    @Test
    public void boyerMooreLongText() {
        pattern = "key";
        text = "Once upon a time all the crows in a town called Mahilaropya made a huge banyan tree their home. The tree\n" +
                "had hundreds of branches. Their king, known as Meghavarna, set up strong fortifications to ensure security\n" +
                "for his brood. Similarly, the owls of the town made a nearby cave their colony. They also had a king, called\n" +
                "Arimardana, who ruled with the help of a strong and cunning army.\n" +
                "The owl king kept a close eye on the banyan tree and on account of previous enmity killed every night any\n" +
                "crow he sighted outside the tree. Slowly, the owl king managed to kill all crows that could be seen outside\n" +
                "the tree. That is why wise men had always said that whoever neglects disease or the enemy perishes in their\n" +
                "hands.\n" +
                "Alarmed at the loss of his flock, Meghavarna assembled his ministers and asked them to prepare a plan to\n" +
                "fight the owls. He placed before them six strategies and asked them to name the best of the six. The first\n" +
                "minister suggested compromise as a tactic because one had first to survive to gather strength and later destroy\n" +
                "the enemy. The elders have said,\n" +
                "“Bend to the enemy when he is strong\n" +
                "Attack him when he is vulnerable.\n" +
                "Don’t wage a war if it doesn’t bring\n" +
                "Power, or wealth or friendship.”\n" +
                "The second minister ruled out compromise and offered trickery as a formula. He cited the example of how\n" +
                "Bheema in the Mahabharata had killed Keechaka in the disguise of a woman. He also quoted elders saying,\n" +
                "“Never accept peace with\n" +
                "An enemy who is not just\n" +
                "For, he will break his word\n" +
                "And stab you in the back.”\n" +
                "The minister referred to the learned as saying that it is easy to defeat an enemy who is a tyrant, a miser, an\n" +
                "idler, a liar, a coward and a fool. Words of peace will only inflame an enemy blinded by anger.\n" +
                "The third minister said, “O lord, our enemy is not only strong but also wicked. Neither compromise nor\n" +
                "trickery will work with him. Exile is the best way. We shall wait and strike when the enemy becomes weak.”\n" +
                "“Neither peace nor bravado\n" +
                "Can subdue a strong enemy\n" +
                "Where these two do not work\n" +
                "Flight is the best alternative.”\n" +
                "The fourth minister opposed all these tactics and suggested the king of crows should stay in his own fort,\n" +
                "mobilize support from friends and then attack the enemy. He quoted the learned as saying,\n" +
                "“A king who flees is like\n" +
                "A cobra without fangs.\n" +
                "A crocodile in water\n" +
                "Can haul an elephant.”\n" +
                "Therefore, the minister said, “An ally is what wind is to fire. The king must stay where he is and gather allies\n" +
                "for support.”\n" +
                "The fifth minister offered a strategy similar to that of the fourth and said, “Stay in your fort and seek the help\n" +
                "of an ally stronger than the enemy. It also pays to form an axis of less strong allies.”\n" +
                "After listening to all the ministers, Meghavarna turned to the wisest and senior most among his counsels,\n" +
                "Sthirajeevi, and asked him for his advice. The wise man told Meghavarna,\n" +
                "“Oh, king of crows, this is the time to use duplicity to finish the enemy. You can thus keep your throne.”\n" +
                "“But learned sir, we have no idea of where Arimardana lives and of what his failings are.”\n" +
                "“That is not difficult. Send your spies and gather information on the key men advising the king of owls. The\n" +
                "next step is to divide them by setting one against the other.”\n" +
                "“Tell me why did the crows and owls fall out in the first place,” asked Meghavarna.\n" +
                "Sthirajeevi said, “That is another story. Long, long ago all the birds in the jungle—swans, parrots, cranes,\n" +
                "nightingales, owls, peacocks, pigeons, pheasants, sparrows, crows etc.—assembled and expressed anguish\n" +
                "that their king Garuda had become indifferent to their welfare and failed to save them from poachers.\n" +
                "Believing that people without a protector were like passengers in a ship without a captain, they decided to\n" +
                "elect a new king. They chose an owl as their king.\n" +
                "As the owl was being crowned, a crow flew into the assembly and asked them why and what they were\n" +
                "celebrating. When the birds told him the details, the crow told them, the owl is a wicked and ugly bird and it\n" +
                "is unwise to choose another leader when Garuda is still alive. To crush enemies it is enough if you mentioned\n" +
                "Garuda’s name or for that matter the name of anyone who is great. That was how the hares managed to live\n" +
                "happily by taking the name of the moon.”\n" +
                "The birds asked the visiting crow, “Tell us how this has happened.”\n" +
                "“I will tell you,” said the crow and began telling them the story of the hares and the elephants.";
        expMatches.add(3126);
        Assert.assertEquals(expMatches, PatternMatching.boyerMoore(pattern, text, comparator));
        Assert.assertEquals(1606, comparator.getComparisonCount());
    }

    @Test
    public void boyerMooreNonAlphaNumeric() {
        pattern = "☮";
        text = "✨✩✪✫✬✭✮✯✰✱✲✳✴✵✶✷✸☮✹✺✻✼✽✾✿❀❁❂❃❄❅❆❇❈❉❊❋❌❍❎❏❐❑❒❓❔❕❖❗❘❙❚❛❜❝❞❟❠❡❢❣❤❥❦❧";
        expMatches.add(17);
        Assert.assertEquals(expMatches, PatternMatching.boyerMoore(pattern, text, comparator));
        Assert.assertEquals(65, comparator.getComparisonCount());
    }

    @Test()
    public void boyerMooreNoBackwardShift() {
        // This test is designed for the situation where
        // we do NOT move the pattern to align with the last occurrence
        // because doing so would cause the pattern to move backwards

        //P: a b a c b a b a d c a b a c a b
        //   a b a c a b
        //             -
        //     a b a c a b                            (don't align LOT of 'b' with the failure here, as doing so would
        //           - - -                             move backward)
        //       a b a c a b                          (instead, move pattern forward only once)
        //                 -
        //         a b a c a b
        //                   -
        //P: a b a c b a b a d c a b a c a b         (repeated so it's not a strain to keep looking up)
        //                     a b a c a b
        //                               -
        //                       a b a c a b
        //                       - - - - - -

        // Count up all the dashes: 13 comparisons

        CharSequence text = "abacbabadcabacab";
        CharSequence pattern = "abacab";
        expMatches.add(10);
        assertEquals(expMatches, PatternMatching.boyerMoore(pattern, text, comparator));
        assertEquals(13, comparator.getComparisonCount());
    }

    @Test
    public void boyerMooreLongPattern() {
        pattern = "key men advising the king of owls. The\n" +
                "next step is to divide them by setting one against the other.”\n" +
                "“Tell me why did the crows and owls fall out in the first place,” asked Meghavarna.\n";

        text = "Once upon a time all the crows in a town called Mahilaropya made a huge banyan tree their home. The tree\n" +
                "had hundreds of branches. Their king, known as Meghavarna, set up strong fortifications to ensure security\n" +
                "for his brood. Similarly, the owls of the town made a nearby cave their colony. They also had a king, called\n" +
                "Arimardana, who ruled with the help of a strong and cunning army.\n" +
                "The owl king kept a close eye on the banyan tree and on account of previous enmity killed every night any\n" +
                "crow he sighted outside the tree. Slowly, the owl king managed to kill all crows that could be seen outside\n" +
                "the tree. That is why wise men had always said that whoever neglects disease or the enemy perishes in their\n" +
                "hands.\n" +
                "Alarmed at the loss of his flock, Meghavarna assembled his ministers and asked them to prepare a plan to\n" +
                "fight the owls. He placed before them six strategies and asked them to name the best of the six. The first\n" +
                "minister suggested compromise as a tactic because one had first to survive to gather strength and later destroy\n" +
                "the enemy. The elders have said,\n" +
                "“Bend to the enemy when he is strong\n" +
                "Attack him when he is vulnerable.\n" +
                "Don’t wage a war if it doesn’t bring\n" +
                "Power, or wealth or friendship.”\n" +
                "The second minister ruled out compromise and offered trickery as a formula. He cited the example of how\n" +
                "Bheema in the Mahabharata had killed Keechaka in the disguise of a woman. He also quoted elders saying,\n" +
                "“Never accept peace with\n" +
                "An enemy who is not just\n" +
                "For, he will break his word\n" +
                "And stab you in the back.”\n" +
                "The minister referred to the learned as saying that it is easy to defeat an enemy who is a tyrant, a miser, an\n" +
                "idler, a liar, a coward and a fool. Words of peace will only inflame an enemy blinded by anger.\n" +
                "The third minister said, “O lord, our enemy is not only strong but also wicked. Neither compromise nor\n" +
                "trickery will work with him. Exile is the best way. We shall wait and strike when the enemy becomes weak.”\n" +
                "“Neither peace nor bravado\n" +
                "Can subdue a strong enemy\n" +
                "Where these two do not work\n" +
                "Flight is the best alternative.”\n" +
                "The fourth minister opposed all these tactics and suggested the king of crows should stay in his own fort,\n" +
                "mobilize support from friends and then attack the enemy. He quoted the learned as saying,\n" +
                "“A king who flees is like\n" +
                "A cobra without fangs.\n" +
                "A crocodile in water\n" +
                "Can haul an elephant.”\n" +
                "Therefore, the minister said, “An ally is what wind is to fire. The king must stay where he is and gather allies\n" +
                "for support.”\n" +
                "The fifth minister offered a strategy similar to that of the fourth and said, “Stay in your fort and seek the help\n" +
                "of an ally stronger than the enemy. It also pays to form an axis of less strong allies.”\n" +
                "After listening to all the ministers, Meghavarna turned to the wisest and senior most among his counsels,\n" +
                "Sthirajeevi, and asked him for his advice. The wise man told Meghavarna,\n" +
                "“Oh, king of crows, this is the time to use duplicity to finish the enemy. You can thus keep your throne.”\n" +
                "“But learned sir, we have no idea of where Arimardana lives and of what his failings are.”\n" +
                "“That is not difficult. Send your spies and gather information on the key men advising the king of owls. The\n" +
                "next step is to divide them by setting one against the other.”\n" +
                "“Tell me why did the crows and owls fall out in the first place,” asked Meghavarna.\n" +
                "Sthirajeevi said, “That is another story. Long, long ago all the birds in the jungle—swans, parrots, cranes,\n" +
                "nightingales, owls, peacocks, pigeons, pheasants, sparrows, crows etc.—assembled and expressed anguish\n" +
                "that their king Garuda had become indifferent to their welfare and failed to save them from poachers.\n" +
                "Believing that people without a protector were like passengers in a ship without a captain, they decided to\n" +
                "elect a new king. They chose an owl as their king.\n" +
                "As the owl was being crowned, a crow flew into the assembly and asked them why and what they were\n" +
                "celebrating. When the birds told him the details, the crow told them, the owl is a wicked and ugly bird and it\n" +
                "is unwise to choose another leader when Garuda is still alive. To crush enemies it is enough if you mentioned\n" +
                "Garuda’s name or for that matter the name of anyone who is great. That was how the hares managed to live\n" +
                "happily by taking the name of the moon.”\n" +
                "The birds asked the visiting crow, “Tell us how this has happened.”\n" +
                "“I will tell you,” said the crow and began telling them the story of the hares and the elephants.";

        expMatches.add(3126);
        Assert.assertEquals(expMatches, PatternMatching.boyerMoore(pattern, text, comparator));
        Assert.assertEquals(393, comparator.getComparisonCount());
    }

    /**************************************************************************************
     Build Last Table
     ***********************************************************************************/
    @Test
    public void buildLastTableEmpty() {
        CharSequence sequence = "";
        Map<Character, Integer> result = PatternMatching.buildLastTable(sequence);
        assertTrue(result.isEmpty());
    }

    @Test
    public void buildLastTableAllDistinct() {
        CharSequence sequence = "xyzab123";
        expLastOccTable.put('x', 0);
        expLastOccTable.put('y', 1);
        expLastOccTable.put('z', 2);
        expLastOccTable.put('a', 3);
        expLastOccTable.put('b', 4);
        expLastOccTable.put('1', 5);
        expLastOccTable.put('2', 6);
        expLastOccTable.put('3', 7);
        assertEquals(expLastOccTable, PatternMatching.buildLastTable(sequence));
    }

    @Test
    public void buildLastTableMonochar() {
        CharSequence sequence = "aaaaaaaa";
        expLastOccTable.put('a', 7);
        Map<Character, Integer> res = PatternMatching.buildLastTable(sequence);
        assertEquals(expLastOccTable, res);
        assertNull(res.get('A'));
        assertNull(res.get('b'));
    }

    @Test
    public void buildLastTableTwoChar() {
        CharSequence sequence = "aaabbaabbabbabbabbba";
        expLastOccTable.put('a', 19);
        expLastOccTable.put('b', 18);
        Map<Character, Integer> res = PatternMatching.buildLastTable(sequence);
        assertEquals(expLastOccTable, res);
        assertNull(res.get('A'));
        assertNull(res.get('B'));
    }

    @Test
    public void buildLastRepetitious() {
        CharSequence sequence = "abcdefgabcdefg";
        expLastOccTable.put('g', 13);
        expLastOccTable.put('f', 12);
        expLastOccTable.put('e', 11);
        expLastOccTable.put('d', 10);
        expLastOccTable.put('c', 9);
        expLastOccTable.put('b', 8);
        expLastOccTable.put('a', 7);
        Map<Character, Integer> res = PatternMatching.buildLastTable(sequence);
        assertEquals(expLastOccTable, res);
        assertNull(res.get('A'));
        assertNull(res.get('B'));
    }

    @Test
    public void buildAllAlphaNumeric() {
        //assumes utf-8 encoding
        //¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþ !"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\]^_`abcdefghijklmnopqrstuvwxyz{|}~¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿĀāĂăĄąĆćĈĉĊċČčĎďĐđĒēĔĕĖėĘęĚěĜĝĞğĠġĢģĤĥĦħĨĩĪīĬĭĮįİıĲĳĴĵĶķĸĹĺĻļĽľĿŀŁłŃńŅņŇňŉŊŋŌōŎŏŐőŒœŔŕŖŗŘřŚśŜŝŞşŠšŢţŤťŦŧŨũŪūŬŭŮůŰűŲųŴŵŶŷŸŹźŻżŽžſƀƁƂƃƄƅƆƇƈƉƊƋƌƍƎƏƐƑƒƓƔƕƖƗƘƙƚƛƜƝƞƟƠơƢƣƤƥƦƧƨƩƪƫƬƭƮƯưƱƲƳƴƵƶƷƸƹƺƻƼƽƾƿǀǁǂǃǄǅǆǇǈǉǊǋǌǍǎǏǐǑǒǓǔǕǖǗǘǙǚǛǜǝǞǟǠǡǢǣǤǥǦǧǨǩǪǫǬǭǮǯǰǱǲǳǴǵǶǷǸǹǺǻǼǽǾǿȀȁȂȃȄȅȆȇȈȉȊȋȌȍȎȏȐȑȒȓȔȕȖȗȘșȚțȜȝȞȟȠȡȢȣȤȥȦȧȨȩȪȫȬȭȮȯȰȱȲȳȴȵȶȷȸȹȺȻȼȽȾȿɀɁɂɃɄɅɆɇɈɉɊɋɌɍɎɏɐɑɒɓɔɕɖɗɘəɚɛɜɝɞɟɠɡɢɣɤɥɦɧɨɩɪɫɬɭɮɯɰɱɲɳɴɵɶɷɸɹɺɻɼɽɾɿʀʁʂʃʄʅʆʇʈʉʊʋʌʍʎʏʐʑʒʓʔʕʖʗʘʙʚʛʜʝʞʟʠʡʢʣʤʥʦʧʨʩʪʫʬʭʮʯʰʱʲʳʴʵʶʷʸʹʺʻʼʽʾʿˀˁ˂˃˄˅ˆˇˈˉˊˋˌˍˎˏːˑ˒˓˔˕˖˗˘˙˚˛˜˝˞˟ˠˡˢˣˤ˥˦˧˨˩˪˫ˬ˭ˮ˯˰˱˲˳˴˵˶˷˸˹˺˻˼˽˾˿̴̵̶̷̸̡̢̧̨̛̖̗̘̙̜̝̞̟̠̣̤̥̦̩̪̫̬̭̮̯̰̱̲̳̹̺̻̼͇͈͉͍͎̀́̂̃̄̅̆̇̈̉̊̋̌̍̎̏̐̑̒̓̔̽̾̿̀́͂̓̈́͆͊͋͌̕̚ͅ͏͓͔͕͖͙͚͐͑͒͗͛ͣͤͥͦͧͨͩͪͫͬͭͮͯ͘͜͟͢͝͞͠͡ͰͱͲͳʹ͵Ͷͷ͸͹ͺͻͼͽ;Ϳ΀΁΂΃΄΅Ά·ΈΉΊ΋Ό΍ΎΏΐΑΒΓΔΕΖΗΘΙΚΛΜΝΞΟΠΡ΢ΣΤΥΦΧΨΩΪΫάέήίΰαβγδεζηθικλμνξοπρςστυφχψωϊϋόύώϏϐϑϒϓϔϕϖϗϘϙϚϛϜϝϞϟϠϡϢϣϤϥϦϧϨϩϪϫϬϭϮϯϰϱϲϳϴϵ϶ϷϸϹϺϻϼϽϾϿЀЁЂЃЄЅІЇЈЉЊЋЌЍЎЏАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюяѐёђѓєѕіїјљњћќѝўџѠѡѢѣѤѥѦѧѨѩѪѫѬѭѮѯѰѱѲѳѴѵѶѷѸѹѺѻѼѽѾѿҀҁ҂҃҄҅҆҇҈҉ҊҋҌҍҎҏҐґҒғҔҕҖҗҘҙҚқҜҝҞҟҠҡҢңҤҥҦҧҨҩҪҫҬҭҮүҰұҲҳҴҵҶҷҸҹҺһҼҽҾҿӀӁӂӃӄӅӆӇӈӉӊӋӌӍӎӏӐӑӒӓӔӕӖӗӘәӚӛӜӝӞӟӠӡӢӣӤӥӦӧӨөӪӫӬӭӮӯӰӱӲӳӴӵӶӷӸӹӺӻӼӽӾӿԀԁԂԃԄԅԆԇԈԉԊԋԌԍԎԏԐԑԒԓԔԕԖԗԘԙԚԛԜԝԞԟԠԡԢԣԤԥԦԧԨԩԪԫԬԭԮԯ԰ԱԲԳԴԵԶԷԸԹԺԻԼԽԾԿՀՁՂՃՄՅՆՇՈՉՊՋՌՍՎՏՐՑՒՓՔՕՖ՗՘ՙ՚՛
        String p = "";
        for (int i = 161; i < 255; i++) {
            //this loop adds an offset
            p += (char) i;
        }

        for (int i = 32; i < 127; i++) {
            p += (char) i;
            expLastOccTable.put((char) i, 62 + i);
        }

        for (int i = 161; i < 1372; i++) {
            p += (char) i;
            expLastOccTable.put((char) i, i + 28);
        }
        Map<Character, Integer> res = PatternMatching.buildLastTable(p);
        assertEquals(expLastOccTable, res);
    }

    @Test
    public void buildAllSymbols() {
        String p = "";
        //☭☮☯☰☱☲☳☴☵☶‐‑‒–—―‖‗‘’‚‛“”„‟†‡•‣․‥…‧  ‪‫‬‭‮ ‰‱′″‴‵‶‷‸‹›※‼‽‾‿⁀⁁⁂⁃⁄⁅⁆⁇⁈⁉⁊⁋⁌⁍⁎⁏⁐⁑⁒⁓⁔⁕⁖⁗⁘⁙⁚⁛⁜⁝⁞ ⁠⁡⁢⁣⁤⁥⁦⁧⁨⁩⁪⁫⁬⁭⁮⁯⁰ⁱ⁲⁳⁴⁵⁶⁷⁸⁹⁺⁻⁼⁽⁾ⁿ₀₁₂₃₄₅₆₇₈₉₊₋₌₍₎₏ₐₑₒₓₔₕₖₗₘₙₚₛₜ₝₞₟₠₡₢₣₤₥₦₧₨₩₪₫€₭₮₯₰₱₲₳₴₵₶₷₸₹₺₻₼₽₾₿⃀⃁⃂⃃⃄⃅⃆⃇⃈⃉⃊⃋⃌⃍⃎⃏⃒⃓⃘⃙⃚⃐⃑⃔⃕⃖⃗⃛⃜⃝⃞⃟⃠⃡⃢⃣⃤⃥⃦⃪⃫⃨⃬⃭⃮⃯⃧⃩⃰⃱⃲⃳⃴⃵⃶⃷⃸⃹⃺⃻⃼⃽⃾⃿℀℁ℂ℃℄℅℆ℇ℈℉ℊℋℌℍℎℏℐℑℒℓ℔ℕ№℗℘ℙℚℛℜℝ℞℟℠℡™℣ℤ℥Ω℧ℨ℩KÅℬℭ℮ℯℰℱℲℳℴℵℶℷℸℹ℺℻ℼℽℾℿ⅀⅁⅂⅃⅄ⅅⅆⅇⅈⅉ⅊⅋⅌⅍ⅎ⅏⅐⅑⅒⅓⅔⅕⅖⅗⅘⅙⅚⅛⅜⅝⅞⅟ⅠⅡⅢⅣⅤⅥⅦⅧⅨⅩⅪⅫⅬⅭⅮⅯⅰⅱⅲⅳⅴⅵⅶⅷⅸⅹⅺⅻⅼⅽⅾⅿↀↁↂↃↄↅↆↇↈ↉↊↋↌↍↎↏←↑→↓↔↕↖↗↘↙↚↛↜↝↞↟↠↡↢↣↤↥↦↧↨↩↪↫↬↭↮↯↰↱↲↳↴↵↶↷↸↹↺↻↼↽↾↿⇀⇁⇂⇃⇄⇅⇆⇇⇈⇉⇊⇋⇌⇍⇎⇏⇐⇑⇒⇓⇔⇕⇖⇗⇘⇙⇚⇛⇜⇝⇞⇟⇠⇡⇢⇣⇤⇥⇦⇧⇨⇩⇪⇫⇬⇭⇮⇯⇰⇱⇲⇳⇴⇵⇶⇷⇸⇹⇺⇻⇼⇽⇾⇿∀∁∂∃∄∅∆∇∈∉∊∋∌∍∎∏∐∑−∓∔∕∖∗∘∙√∛∜∝∞∟∠∡∢∣∤∥∦∧∨∩∪∫∬∭∮∯∰∱∲∳∴∵∶∷∸∹∺∻∼∽∾∿≀≁≂≃≄≅≆≇≈≉≊≋≌≍≎≏≐≑≒≓≔≕≖≗≘≙≚≛≜≝≞≟≠≡≢≣≤≥≦≧≨≩≪≫≬≭≮≯≰≱≲≳≴≵≶≷≸≹≺≻≼≽≾≿⊀⊁⊂⊃⊄⊅⊆⊇⊈⊉⊊⊋⊌⊍⊎⊏⊐⊑⊒⊓⊔⊕⊖⊗⊘⊙⊚⊛⊜⊝⊞⊟⊠⊡⊢⊣⊤⊥⊦⊧⊨⊩⊪⊫⊬⊭⊮⊯⊰⊱⊲⊳⊴⊵⊶⊷⊸⊹⊺⊻⊼⊽⊾⊿⋀⋁⋂⋃⋄⋅⋆⋇⋈⋉⋊⋋⋌⋍⋎⋏⋐⋑⋒⋓⋔⋕⋖⋗⋘⋙⋚⋛⋜⋝⋞⋟⋠⋡⋢⋣⋤⋥⋦⋧⋨⋩⋪⋫⋬⋭⋮⋯⋰⋱⋲⋳⋴⋵⋶⋷⋸⋹⋺⋻⋼⋽⋾⋿⌀⌁⌂⌃⌄⌅⌆⌇⌈⌉⌊⌋⌌⌍⌎⌏⌐⌑⌒⌓⌔⌕⌖⌗⌘⌙⌚⌛⌜⌝⌞⌟⌠⌡⌢⌣⌤⌥⌦⌧⌨〈〉⌫⌬⌭⌮⌯⌰⌱⌲⌳⌴⌵⌶⌷⌸⌹⌺⌻⌼⌽⌾⌿⍀⍁⍂⍃⍄⍅⍆⍇⍈⍉⍊⍋⍌⍍⍎⍏⍐⍑⍒⍓⍔⍕⍖⍗⍘⍙⍚⍛⍜⍝⍞⍟⍠⍡⍢⍣⍤⍥⍦⍧⍨⍩⍪⍫⍬⍭⍮⍯⍰⍱⍲⍳⍴⍵⍶⍷⍸⍹⍺⍻⍼⍽⍾⍿⎀⎁⎂⎃⎄⎅⎆⎇⎈⎉⎊⎋⎌⎍⎎⎏⎐⎑⎒⎓⎔⎕⎖⎗⎘⎙⎚⎛⎜⎝⎞⎟⎠⎡⎢⎣⎤⎥⎦⎧⎨⎩⎪⎫⎬⎭⎮⎯⎰⎱⎲⎳⎴⎵⎶⎷⎸⎹⎺⎻⎼⎽⎾⎿⏀⏁⏂⏃⏄⏅⏆⏇⏈⏉⏊⏋⏌⏍⏎⏏⏐⏑⏒⏓⏔⏕⏖⏗⏘⏙⏚⏛⏜⏝⏞⏟⏠⏡⏢⏣⏤⏥⏦⏧⏨⏩⏪⏫⏬⏭⏮⏯⏰⏱⏲⏳⏴⏵⏶⏷⏸⏹⏺⏻⏼⏽⏾⏿␀␁␂␃␄␅␆␇␈␉␊␋␌␍␎␏␐␑␒␓␔␕␖␗␘␙␚␛␜␝␞␟␠␡␢␣␤␥␦␧␨␩␪␫␬␭␮␯␰␱␲␳␴␵␶␷␸␹␺␻␼␽␾␿⑀⑁⑂⑃⑄⑅⑆⑇⑈⑉⑊⑋⑌⑍⑎⑏⑐⑑⑒⑓⑔⑕⑖⑗⑘⑙⑚⑛⑜⑝⑞⑟①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳⑴⑵⑶⑷⑸⑹⑺⑻⑼⑽⑾⑿⒀⒁⒂⒃⒄⒅⒆⒇⒈⒉⒊⒋⒌⒍⒎⒏⒐⒑⒒⒓⒔⒕⒖⒗⒘⒙⒚⒛⒜⒝⒞⒟⒠⒡⒢⒣⒤⒥⒦⒧⒨⒩⒪⒫⒬⒭⒮⒯⒰⒱⒲⒳⒴⒵ⒶⒷⒸⒹⒺⒻⒼⒽⒾⒿⓀⓁⓂⓃⓄⓅⓆⓇⓈⓉⓊⓋⓌⓍⓎⓏⓐⓑⓒⓓⓔⓕⓖⓗⓘⓙⓚⓛⓜⓝⓞⓟⓠⓡⓢⓣⓤⓥⓦⓧⓨⓩ⓪⓫⓬⓭⓮⓯⓰⓱⓲⓳⓴⓵⓶⓷⓸⓹⓺⓻⓼⓽⓾⓿─━│┃┄┅┆┇┈┉┊┋┌┍┎┏┐┑┒┓└┕┖┗┘┙┚┛├┝┞┟┠┡┢┣┤┥┦┧┨┩┪┫┬┭┮┯┰┱┲┳┴┵┶┷┸┹┺┻┼┽┾┿╀╁╂╃╄╅╆╇╈╉╊╋╌╍╎╏═║╒╓╔╕╖╗╘╙╚╛╜╝╞╟╠╡╢╣╤╥╦╧╨╩╪╫╬╭╮╯╰╱╲╳╴╵╶╷╸╹╺╻╼╽╾╿▀▁▂▃▄▅▆▇█▉▊▋▌▍▎▏▐░▒▓▔▕▖▗▘▙▚▛▜▝▞▟■□▢▣▤▥▦▧▨▩▪▫▬▭▮▯▰▱▲△▴▵▶▷▸▹►▻▼▽▾▿◀◁◂◃◄◅◆◇◈◉◊○◌◍◎●◐◑◒◓◔◕◖◗◘◙◚◛◜◝◞◟◠◡◢◣◤◥◦◧◨◩◪◫◬◭◮◯◰◱◲◳◴◵◶◷◸◹◺◻◼◽◾◿☀☁☂☃☄★☆☇☈☉☊☋☌☍☎☏☐☑☒☓☔☕☖☗☘☙☚☛☜☝☞☟☠☡☢☣☤☥☦☧☨☩☪☫☬☭☮☯☰☱☲☳☴☵☶☷☸☹☺☻☼☽☾☿♀♁♂♃♄♅♆♇♈♉♊♋♌♍♎♏♐♑♒♓♔♕♖♗♘♙♚♛♜♝♞♟♠♡♢♣♤♥♦♧♨♩♪♫♬♭♮♯♰♱♲♳♴♵♶♷♸♹♺♻♼♽♾♿⚀⚁⚂⚃⚄⚅⚆⚇⚈⚉⚊⚋⚌⚍⚎⚏⚐⚑⚒⚓⚔⚕⚖⚗⚘⚙⚚⚛⚜⚝⚞⚟⚠⚡⚢⚣⚤⚥⚦⚧⚨⚩⚪⚫⚬⚭⚮⚯⚰⚱⚲⚳⚴⚵⚶⚷⚸⚹⚺⚻⚼⚽⚾⚿⛀⛁⛂⛃⛄⛅⛆⛇⛈⛉⛊⛋⛌⛍⛎⛏⛐⛑⛒⛓⛔⛕⛖⛗⛘⛙⛚⛛⛜⛝⛞⛟⛠⛡⛢⛣⛤⛥⛦⛧⛨⛩⛪⛫⛬⛭⛮⛯⛰⛱⛲⛳⛴⛵⛶⛷⛸⛹⛺⛻⛼⛽⛾⛿✀✁✂✃✄✅✆✇✈✉✊✋✌✍✎✏✐✑✒✓✔✕✖✗✘✙✚✛✜✝✞✟✠✡✢✣✤✥✦✧✨✩✪✫✬✭✮✯✰✱✲✳✴✵✶✷✸✹✺✻✼✽✾✿❀❁❂❃❄❅❆❇❈❉❊❋❌❍❎❏❐❑❒❓❔❕❖❗❘❙❚❛❜❝❞❟❠❡❢❣❤❥❦❧❨❩❪❫❬❭❮❯❰❱❲❳❴❵❶❷❸❹❺❻❼❽❾❿➀➁➂➃➄➅➆➇➈➉➊➋➌➍➎➏➐➑➒➓➔➕➖➗➘➙➚➛➜➝➞➟➠➡➢➣➤➥➦➧➨➩➪➫➬➭➮➯➰➱➲➳➴➵➶➷➸➹➺➻➼➽➾➿

        //offset loop
        for (int i = 0; i < 10; i++) {
            p += (char) (i + 9773);
            expLastOccTable.put((char) (i + 9773), i);
        }

        for (int i = 8208; i < 10176; i++) {
            p += (char) i;
            expLastOccTable.put((char) i, i  - 8198);
        }
        Map<Character, Integer> res = PatternMatching.buildLastTable(p);
        assertEquals(expLastOccTable, res);
    }

    /**************************************************************************************
     Rabin-Karp
     ***********************************************************************************/
    @Test
    public void rabinKarpBeginningMatch() {
        pattern = "moo";
        text = "moowoofmeowribbet";
        expMatches.add(0);
        Assert.assertEquals(expMatches, PatternMatching.rabinKarp(pattern, text, comparator));
        Assert.assertEquals(3, comparator.getComparisonCount());
    }

    @Test
    public void rabinKarpEndMatch() {
        pattern = "meow";
        text = "moowoofribbetmeow";
        expMatches.add(13);
        Assert.assertEquals(expMatches, PatternMatching.rabinKarp(pattern, text, comparator));
        Assert.assertEquals(4, comparator.getComparisonCount());
    }

    @Test
    public void rabinKarpMiddleMatch() {
        pattern = "boo";
        text = "moowoofbooribbetmeow";
        expMatches.add(7);
        Assert.assertEquals(expMatches, PatternMatching.rabinKarp(pattern, text, comparator));
        Assert.assertEquals(3, comparator.getComparisonCount());
    }

    @Test
    public void rabinKarpMultiMatch() {
        pattern = "mooo";
        text = "mooowoofmeowmoooomeowmooomeowribbetribbetmoooribbetmooo";
        expMatches.add(0);
        expMatches.add(12);
        expMatches.add(21);
        expMatches.add(41);
        expMatches.add(51);
        Assert.assertEquals(expMatches, PatternMatching.rabinKarp(pattern, text, comparator));
        Assert.assertEquals(20, comparator.getComparisonCount());
    }

    @Test
    public void rabinKarpRepeatedMatch() {
        pattern = "moo";
        text = "mooomooomooomoomooomoomoomooomooo";
        expMatches.add(0);
        expMatches.add(4);
        expMatches.add(8);
        expMatches.add(12);
        expMatches.add(15);
        expMatches.add(19);
        expMatches.add(22);
        expMatches.add(25);
        expMatches.add(29);
        Assert.assertEquals(expMatches, PatternMatching.rabinKarp(pattern, text, comparator));
        Assert.assertEquals(27, comparator.getComparisonCount());
    }

    @Test
    public void rabinKarpNearMatch() {
        pattern = "abcd";
        text = "abcabcabcabcdabcabcabcabcdabcdabc";
        expMatches.add(9);
        expMatches.add(22);
        expMatches.add(26);
        Assert.assertEquals(expMatches, PatternMatching.rabinKarp(pattern, text, comparator));
        Assert.assertEquals(12, comparator.getComparisonCount());
    }

    @Test
    public void rabinKarpNoMatch() {
        pattern = "meow";
        text = "The Owl always takes her sleep during the day. Then after sundown, when the rosy light fades from " +
                "the sky and the shadows rise slowly through the wood, out she comes ruffling and blinking from the " +
                "old hollow tree. Now her weird 'hoo-hoo-hoo-oo-oo' echoes through the quiet wood, and she begins " +
                "her hunt for the bugs and beetles, frogs and mice she likes so well to eat.";
        Assert.assertEquals(expMatches, PatternMatching.rabinKarp(pattern, text, comparator));
        Assert.assertEquals(0, comparator.getComparisonCount());
    }

    @Test
    public void rabinKarpPatternSameText() {
        pattern = "woof";
        text = "woof";
        expMatches.add(0);
        Assert.assertEquals(expMatches, PatternMatching.rabinKarp(pattern, text, comparator));
        Assert.assertEquals(4, comparator.getComparisonCount());
    }

    @Test
    public void rabinKarpPeriodicPattern() {
        pattern = "abab";
        text = "ababababababcababab";
        expMatches.add(0);
        expMatches.add(2);
        expMatches.add(4);
        expMatches.add(6);
        expMatches.add(8);
        expMatches.add(13);
        expMatches.add(15);
        Assert.assertEquals(expMatches, PatternMatching.rabinKarp(pattern, text, comparator));
        Assert.assertEquals(28, comparator.getComparisonCount());
    }

    @Test
    public void rabinKarpLongerPattern() {
        pattern = "ababababababcababababbababcbabcabcbabcb";
        text = "abab";

        Assert.assertEquals(expMatches, PatternMatching.rabinKarp(pattern, text, comparator));
        Assert.assertEquals(0, comparator.getComparisonCount());
    }

    @Test
    public void rabinKarpEmptyText() {
        pattern = "m";
        text = "";

        Assert.assertEquals(expMatches, PatternMatching.rabinKarp(pattern, text, comparator));
        Assert.assertEquals(0, comparator.getComparisonCount());
    }

    @Test
    public void rabinKarpSameLength() {
        pattern = "cats";
        text = "scat";

        Assert.assertEquals(expMatches, PatternMatching.rabinKarp(pattern, text, comparator));
        Assert.assertTrue(comparator.getComparisonCount() == 0 || comparator.getComparisonCount() == 1);
    }

    @Test
    public void rabinKarpSingleChar() {
        pattern = "a";
        text = "aFar away and long ago, off in a dark forest, lived a peaceful little witch named 'Ruston'.a";
        expMatches.add(0);
        expMatches.add(2);
        expMatches.add(5);
        expMatches.add(7);
        expMatches.add(10);
        expMatches.add(19);
        expMatches.add(31);
        expMatches.add(34);
        expMatches.add(52);
        expMatches.add(56);
        expMatches.add(77);
        expMatches.add(91);
        Assert.assertEquals(expMatches, PatternMatching.rabinKarp(pattern, text, comparator));
        Assert.assertEquals(12, comparator.getComparisonCount());
    }

    @Test
    public void rabinKarpLongText() {
        pattern = "key";
        text = "Once upon a time all the crows in a town called Mahilaropya made a huge banyan tree their home. The tree\n" +
                "had hundreds of branches. Their king, known as Meghavarna, set up strong fortifications to ensure security\n" +
                "for his brood. Similarly, the owls of the town made a nearby cave their colony. They also had a king, called\n" +
                "Arimardana, who ruled with the help of a strong and cunning army.\n" +
                "The owl king kept a close eye on the banyan tree and on account of previous enmity killed every night any\n" +
                "crow he sighted outside the tree. Slowly, the owl king managed to kill all crows that could be seen outside\n" +
                "the tree. That is why wise men had always said that whoever neglects disease or the enemy perishes in their\n" +
                "hands.\n" +
                "Alarmed at the loss of his flock, Meghavarna assembled his ministers and asked them to prepare a plan to\n" +
                "fight the owls. He placed before them six strategies and asked them to name the best of the six. The first\n" +
                "minister suggested compromise as a tactic because one had first to survive to gather strength and later destroy\n" +
                "the enemy. The elders have said,\n" +
                "“Bend to the enemy when he is strong\n" +
                "Attack him when he is vulnerable.\n" +
                "Don’t wage a war if it doesn’t bring\n" +
                "Power, or wealth or friendship.”\n" +
                "The second minister ruled out compromise and offered trickery as a formula. He cited the example of how\n" +
                "Bheema in the Mahabharata had killed Keechaka in the disguise of a woman. He also quoted elders saying,\n" +
                "“Never accept peace with\n" +
                "An enemy who is not just\n" +
                "For, he will break his word\n" +
                "And stab you in the back.”\n" +
                "The minister referred to the learned as saying that it is easy to defeat an enemy who is a tyrant, a miser, an\n" +
                "idler, a liar, a coward and a fool. Words of peace will only inflame an enemy blinded by anger.\n" +
                "The third minister said, “O lord, our enemy is not only strong but also wicked. Neither compromise nor\n" +
                "trickery will work with him. Exile is the best way. We shall wait and strike when the enemy becomes weak.”\n" +
                "“Neither peace nor bravado\n" +
                "Can subdue a strong enemy\n" +
                "Where these two do not work\n" +
                "Flight is the best alternative.”\n" +
                "The fourth minister opposed all these tactics and suggested the king of crows should stay in his own fort,\n" +
                "mobilize support from friends and then attack the enemy. He quoted the learned as saying,\n" +
                "“A king who flees is like\n" +
                "A cobra without fangs.\n" +
                "A crocodile in water\n" +
                "Can haul an elephant.”\n" +
                "Therefore, the minister said, “An ally is what wind is to fire. The king must stay where he is and gather allies\n" +
                "for support.”\n" +
                "The fifth minister offered a strategy similar to that of the fourth and said, “Stay in your fort and seek the help\n" +
                "of an ally stronger than the enemy. It also pays to form an axis of less strong allies.”\n" +
                "After listening to all the ministers, Meghavarna turned to the wisest and senior most among his counsels,\n" +
                "Sthirajeevi, and asked him for his advice. The wise man told Meghavarna,\n" +
                "“Oh, king of crows, this is the time to use duplicity to finish the enemy. You can thus keep your throne.”\n" +
                "“But learned sir, we have no idea of where Arimardana lives and of what his failings are.”\n" +
                "“That is not difficult. Send your spies and gather information on the key men advising the king of owls. The\n" +
                "next step is to divide them by setting one against the other.”\n" +
                "“Tell me why did the crows and owls fall out in the first place,” asked Meghavarna.\n" +
                "Sthirajeevi said, “That is another story. Long, long ago all the birds in the jungle—swans, parrots, cranes,\n" +
                "nightingales, owls, peacocks, pigeons, pheasants, sparrows, crows etc.—assembled and expressed anguish\n" +
                "that their king Garuda had become indifferent to their welfare and failed to save them from poachers.\n" +
                "Believing that people without a protector were like passengers in a ship without a captain, they decided to\n" +
                "elect a new king. They chose an owl as their king.\n" +
                "As the owl was being crowned, a crow flew into the assembly and asked them why and what they were\n" +
                "celebrating. When the birds told him the details, the crow told them, the owl is a wicked and ugly bird and it\n" +
                "is unwise to choose another leader when Garuda is still alive. To crush enemies it is enough if you mentioned\n" +
                "Garuda’s name or for that matter the name of anyone who is great. That was how the hares managed to live\n" +
                "happily by taking the name of the moon.”\n" +
                "The birds asked the visiting crow, “Tell us how this has happened.”\n" +
                "“I will tell you,” said the crow and began telling them the story of the hares and the elephants.";
        expMatches.add(3126);
        Assert.assertEquals(expMatches, PatternMatching.rabinKarp(pattern, text, comparator));
        Assert.assertEquals(3, comparator.getComparisonCount());
    }

    @Test
    public void rabinKarpNonAlphaNumeric() {
        pattern = "☮";
        text = "✨✩✪✫✬✭✮✯✰✱✲✳✴✵✶✷✸☮✹✺✻✼✽✾✿❀❁❂❃❄❅❆❇❈❉❊❋❌❍❎❏❐❑❒❓❔❕❖❗❘❙❚❛❜❝❞❟❠❡❢❣❤❥❦❧";
        expMatches.add(17);
        Assert.assertEquals(expMatches, PatternMatching.rabinKarp(pattern, text, comparator));
        Assert.assertEquals(1, comparator.getComparisonCount());
    }

    @Test
    public void rabinKarpCollision() {
        CharSequence pattern = "\u0001\u0001"; // hash is 113*1 + 1 = 114

        // note escaped unicode is expressed in hex (0x72 = 114)
        CharSequence text = "\u0000\u0072\u0000\u0001\u0001";
        // should collide in the first instance 0 + 114
        // one extra comparison
        // no match at {0, 1} substring
        // two comparisons for the match


        assertEquals(Arrays.asList(3), PatternMatching.rabinKarp(pattern, text, comparator));
        assertEquals(3, comparator.getComparisonCount());
    }

    /**************************************************************************************
     Expected Exceptions
     ***********************************************************************************/
    @Test(expected = IllegalArgumentException.class)
    public void kmpEmptyPattern() {
        pattern = "";
        text = "a";
        PatternMatching.kmp(pattern, text, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void kmpNullPattern() {
        pattern = "a";
        text = "a";
        PatternMatching.kmp(null, text, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void kmpNullText() {
        pattern = "a";
        text = "a";
        PatternMatching.kmp(pattern, null, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void kmpNullComp() {
        pattern = "a";
        text = "a";
        PatternMatching.kmp(pattern, text, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildFailureTableNullPattern() {
        pattern = "a";
        PatternMatching.buildFailureTable(null, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildFailureTableNullComp() {
        pattern = "a";
        PatternMatching.buildFailureTable(pattern, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void boyerMooreEmptyPattern() {
        pattern = "";
        text = "a";
        PatternMatching.boyerMoore(pattern, text, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void boyerMooreNullPattern() {
        pattern = "a";
        text = "a";
        PatternMatching.boyerMoore(null, text, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void boyerMooreNullText() {
        pattern = "a";
        text = "a";
        PatternMatching.boyerMoore(pattern, null, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void boyerMooreNullComp() {
        pattern = "a";
        text = "a";
        PatternMatching.boyerMoore(pattern, text, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildLastTableNullPattern() {
        PatternMatching.buildLastTable(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rabinKarpEmptyPattern() {
        pattern = "";
        text = "a";
        PatternMatching.rabinKarp(pattern, text, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rabinKarpNullPattern() {
        pattern = "a";
        text = "a";
        PatternMatching.rabinKarp(null, text, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rabinKarpNullText() {
        pattern = "a";
        text = "a";
        PatternMatching.rabinKarp(pattern, null, comparator);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rabinKarpNullComp() {
        pattern = "a";
        text = "a";
        PatternMatching.rabinKarp(pattern, text, null);
    }
}