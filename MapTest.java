import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;

/**
 * JUnit test fixture for {@code Map<String, String>}'s constructor and kernel
 * methods.
 *
 * @author Put your name here
 *
 */
public abstract class MapTest {

    /**
     * Invokes the appropriate {@code Map} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new map
     * @ensures constructorTest = {}
     */
    protected abstract Map<String, String> constructorTest();

    /**
     * Invokes the appropriate {@code Map} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new map
     * @ensures constructorRef = {}
     */
    protected abstract Map<String, String> constructorRef();

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the implementation
     * under test type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsTest = [pairs in args]
     */
    private Map<String, String> createFromArgsTest(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorTest();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    /**
     *
     * Creates and returns a {@code Map<String, String>} of the reference
     * implementation type with the given entries.
     *
     * @param args
     *            the (key, value) pairs for the map
     * @return the constructed map
     * @requires <pre>
     * [args.length is even]  and
     * [the 'key' entries in args are unique]
     * </pre>
     * @ensures createFromArgsRef = [pairs in args]
     */
    private Map<String, String> createFromArgsRef(String... args) {
        assert args.length % 2 == 0 : "Violation of: args.length is even";
        Map<String, String> map = this.constructorRef();
        for (int i = 0; i < args.length; i += 2) {
            assert !map.hasKey(args[i]) : ""
                    + "Violation of: the 'key' entries in args are unique";
            map.add(args[i], args[i + 1]);
        }
        return map;
    }

    // TODO - add test cases for constructor, add, remove, removeAny, value,
    // hasKey, and size

    /**
     * Tests the no argument constructor of Map4 that gives the Map an initial
     * value.
     */
    @Test
    public final void testNoArgumentConstructor() {
        // Set up variables and call method under test
        Map<String, String> n = this.constructorTest();
        Map<String, String> nExpected = this.constructorRef();

        //Assert that values of variables match expectations
        assertEquals(nExpected, n);
    }

    /**
     * First Test the Add for Map4.
     */
    @Test
    public final void testAddToEmpty() {
        // Set up variables and call method under test
        Map<String, String> pair = this.createFromArgsTest();
        Map<String, String> result = this.createFromArgsRef("A", "a");

        //Add pair "A","a" to the Map pair.
        pair.add("A", "a");
        assertEquals(pair, result);
    }

    /**
     * Second test for the Add for Map4.
     */
    @Test
    public final void testAddToNonEmptyMap() {
        // Set up variables and call method under test
        Map<String, String> pair = this.createFromArgsTest("A", "a", "B", "b");
        Map<String, String> result = this.createFromArgsRef("A", "a", "B", "b",
                "C", "c");

        //Add pairs to the Map pair.
        pair.add("C", "c");
        assertEquals(pair, result);
    }

    /**
     * First test for the Remove for Map4.
     */
    @Test
    public final void testRemoveleftEmpty() {
        // Set up variables and call method under test
        Map<String, String> pair = this.createFromArgsRef();
        Map<String, String> test = this.createFromArgsTest("A", "a");

        //Remove the only pair in Map test, and this map should be empty.
        test.remove("A");
        assertEquals(pair, test);
    }

    /**
     * Second test for the Remove for Map4.
     */
    @Test
    public final void testRemoveA() {
        // Set up variables and call method under test
        Map<String, String> pair = this.createFromArgsRef("B", "b", "C", "c");
        Map<String, String> test = this.createFromArgsTest("A", "a", "B", "b",
                "C", "c");

        //Remove the A pair in Map test, and B and C should be left.
        test.remove("A");
        assertEquals(pair, test);
    }

    /**
     * First test for the RemoveAny for Map4.
     */
    @Test
    public final void testRemoveAnyLeftEmpty() {
        // Set up variables and call method under test
        Map<String, String> pair = this.createFromArgsRef();
        Map<String, String> test = this.createFromArgsTest("A", "a");

        //Remove any pair in Map test. There's only one pair here, so it will
        //remove the only pair and after that it should be empty
        test.removeAny();
        assertEquals(pair, test);
    }

    /**
     * Second test for the RemoveAny for Map4.
     */
    @Test
    public final void testRemoveAnyLeft2() {
        // Set up variables and call method under test
        Map<String, String> test = this.createFromArgsTest("A", "a", "B", "b",
                "C", "c");

        //Remove any pair in Map test. There're three pairs here,
        //so after there should be two pairs left
        test.removeAny();
        assertEquals(2, test.size());
    }

    /**
     * First test for the Value for Map4.
     */
    @Test
    public final void testValueExist() {
        // Set up variables and call method under test
        String expect = "d";
        Map<String, String> pair = this.createFromArgsTest("A", "a", "B", "b",
                "C", "c", "D", "d", "E", "e");

        //We call the Value method to find the value with key "D".
        String result = pair.value("D");
        assertEquals(expect, result);
    }

    /**
     * Second test for the Value for Map4.
     */
    @Test
    public final void testValueA() {
        // Set up variables and call method under test
        String expect = "a";
        Map<String, String> pair = this.createFromArgsTest("A", "a");

        //We call the Value method to find the value with key "A".
        String result = pair.value("A");
        assertEquals(expect, result);
    }

    /**
     * Tests the HasKey for Map4 with a true example.
     */
    @Test
    public final void testHasKeyTrue() {
        // Set up variables and call method under test
        boolean expect = true;
        Map<String, String> pair = this.createFromArgsTest("A", "a");

        //We call the hasKey to find if we have key "A". The result should be true.
        boolean result = pair.hasKey("A");
        assertEquals(expect, result);
    }

    /**
     * Tests the HasKey for Map4 with a false example.
     */
    @Test
    public final void testHasKeyFalse() {
        // Set up variables and call method under test
        boolean expect = false;
        Map<String, String> pair = this.createFromArgsTest("A", "a");

        //We call the hasKey to find if we have key "D". The result should be false.
        boolean result = pair.hasKey("D");
        assertEquals(expect, result);
    }

    /**
     * Tests the Size for Map4 for an empty Map.
     */
    @Test
    public void testSizeZero() {
        // Set up variables and call method under test
        Map<String, String> pair = this.createFromArgsTest();

        // Find size of an empty Map calling the method Size, and it should be 0.
        assertEquals(0, pair.size());
    }

    /**
     * Tests the Size for Map4 for a non-empty Map.
     */
    @Test
    public void testSizeNonZero() {
        // Set up variables and call method under test
        Map<String, String> pair = this.createFromArgsTest("A", "a");

        // Find size of a non-empty Map calling the method Size, and it should be 1.
        assertEquals(1, pair.size());
    }

}
