import static org.junit.Assert.assertEquals;

import java.util.Comparator;

import org.junit.Test;

import components.sortingmachine.SortingMachine;

/**
 * JUnit test fixture for {@code SortingMachine<String>}'s constructor and
 * kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SortingMachineTest {

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * implementation under test and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorTest = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorTest(
            Comparator<String> order);

    /**
     * Invokes the appropriate {@code SortingMachine} constructor for the
     * reference implementation and returns the result.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @return the new {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures constructorRef = (true, order, {})
     */
    protected abstract SortingMachine<String> constructorRef(
            Comparator<String> order);

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the
     * implementation under test type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsTest = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsTest(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorTest(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     *
     * Creates and returns a {@code SortingMachine<String>} of the reference
     * implementation type with the given entries and mode.
     *
     * @param order
     *            the {@code Comparator} defining the order for {@code String}
     * @param insertionMode
     *            flag indicating the machine mode
     * @param args
     *            the entries for the {@code SortingMachine}
     * @return the constructed {@code SortingMachine}
     * @requires IS_TOTAL_PREORDER([relation computed by order.compare method])
     * @ensures <pre>
     * createFromArgsRef = (insertionMode, order, [multiset of entries in args])
     * </pre>
     */
    private SortingMachine<String> createFromArgsRef(Comparator<String> order,
            boolean insertionMode, String... args) {
        SortingMachine<String> sm = this.constructorRef(order);
        for (int i = 0; i < args.length; i++) {
            sm.add(args[i]);
        }
        if (!insertionMode) {
            sm.changeToExtractionMode();
        }
        return sm;
    }

    /**
     * Comparator<String> implementation to be used in all test cases. Compare
     * {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {

        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }

    }

    /**
     * Comparator instance to be used in all test cases.
     */
    private static final StringLT ORDER = new StringLT();

    /*
     * Sample test cases.
     */

    @Test
    public final void testConstructor() {
        SortingMachine<String> m = this.constructorTest(ORDER);
        SortingMachine<String> mExpected = this.constructorRef(ORDER);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green");
        m.add("green");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testAddNotEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red");
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "green", "red", "yellow");
        m.add("yellow");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testchangeToExtractionModeEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        m.changeToExtractionMode();
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false);
        assertEquals(mExpected, m);
    }

    @Test
    public final void testchangeToExtractionModeNoneEmpty() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red", "yellow");
        m.changeToExtractionMode();
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, false,
                "green", "red", "yellow");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testisInInsertionModeTrue() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red");
        assertEquals(true, m.isInInsertionMode());
    }

    @Test
    public final void testisInInsertionModeFalse() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red", "yellow");
        m.changeToExtractionMode();
        assertEquals(false, m.isInInsertionMode());
    }

    @Test
    public final void testOrderForInsertion() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red", "yellow");
        assertEquals(ORDER, m.order());
    }

    @Test
    public final void testOrderForExtraction() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red", "yellow");
        m.changeToExtractionMode();
        assertEquals(ORDER, m.order());
    }

    @Test
    public final void testRemoveFirstEmptyLeft() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true,
                "green");
        String result = m.removeFirst();
        String expected = "green";
        assertEquals(expected, result);
    }

    @Test
    public final void testRemoveFirstNoneEmptyLeft() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red", "yellow");
        String result = m.removeFirst();
        SortingMachine<String> mExpected = this.createFromArgsRef(ORDER, true,
                "red", "yellow");
        assertEquals(mExpected, m);
    }

    @Test
    public final void testSizeZero() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true);
        assertEquals(0, m.size());
    }

    @Test
    public final void testSizeTwo() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "green",
                "red");
        assertEquals(2, m.size());
    }

    @Test
    public final void testSizeTen() {
        SortingMachine<String> m = this.createFromArgsTest(ORDER, true, "1",
                "2", "3", "4", "5", "6", "7", "8", "9", "10");
        assertEquals(10, m.size());
    }

}
