import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Avani Jagdale
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor for the implementation
     * under test and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor for the reference
     * implementation and returns the result.
     *
     * @return the new set
     * @ensures constructorRef = {}
     */
    protected abstract Set<String> constructorRef();

    /**
     * Creates and returns a {@code Set<String>} of the implementation under
     * test type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsTest = [entries in args]
     */
    private Set<String> createFromArgsTest(String... args) {
        Set<String> set = this.constructorTest();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Creates and returns a {@code Set<String>} of the reference implementation
     * type with the given entries.
     *
     * @param args
     *            the entries for the set
     * @return the constructed set
     * @requires [every entry in args is unique]
     * @ensures createFromArgsRef = [entries in args]
     */
    private Set<String> createFromArgsRef(String... args) {
        Set<String> set = this.constructorRef();
        for (String s : args) {
            assert !set.contains(
                    s) : "Violation of: every entry in args is unique";
            set.add(s);
        }
        return set;
    }

    /**
     * Test the no argument constructor.
     */
    @Test
    public void testConstructor() {
        Set<String> test = this.constructorTest();
        Set<String> expected = this.constructorRef();
        assertEquals(test, expected);
    }

    /**
     * Test the add method with a non-empty set.
     */
    @Test
    public void testAdd() {
        Set<String> test = this.createFromArgsTest("a", "b");
        Set<String> expected = this.createFromArgsRef("a", "b");

        test.add("c");
        expected.add("c");

        assertEquals(test, expected);
    }

    /**
     * Test the add method on an empty set.
     */
    @Test
    public void testAddEmpty() {
        Set<String> test = this.createFromArgsTest();
        Set<String> expected = this.createFromArgsRef();

        test.add("c");
        expected.add("c");

        assertEquals(test, expected);
    }

    /**
     * Test the add method on a non-empty set with more than five elements.
     */
    @Test
    public void testAddLarge() {
        Set<String> test = this.createFromArgsTest("c", "a", "b", "d", "f",
                "e");
        Set<String> expected = this.createFromArgsRef("c", "a", "b", "d", "f",
                "e");

        test.add("x");
        expected.add("x");

        assertEquals(test, expected);
    }

    /**
     * Test the remove method.
     */
    @Test
    public void testRemove() {
        Set<String> test = this.createFromArgsTest("a", "b", "c");
        Set<String> expected = this.createFromArgsTest("a", "b", "c");

        String r = test.remove("b");
        String rExpected = expected.remove("b");

        assertEquals(r, rExpected);
        assertEquals(test, expected);
    }

    /**
     * Test the remove method on a larger set.
     */
    @Test
    public void testRemoveLarge() {
        Set<String> test = this.createFromArgsTest("a", "f", "b", "d", "c");
        Set<String> expected = this.createFromArgsTest("a", "f", "b", "d", "c");

        String r = test.remove("f");
        String rExpected = expected.remove("f");

        assertEquals(r, rExpected);
        assertEquals(test, expected);
    }

    /**
     * Test the remove method on a set with one element, leaving it empty after
     * the method call.
     */
    @Test
    public void testRemoveLeftEmpty() {
        Set<String> test = this.createFromArgsTest("a");
        Set<String> expected = this.createFromArgsTest("a");

        String r = test.remove("a");
        String rExpected = expected.remove("a");

        assertEquals(r, rExpected);
        assertEquals(test, expected);
    }

    /**
     * Test the size method.
     */
    @Test
    public void testSize() {
        Set<String> test = this.createFromArgsTest("a", "b", "c");
        Set<String> expected = this.createFromArgsTest("a", "b", "c");

        assertEquals(test.size(), expected.size());
        assertEquals(test, expected);
    }

    /**
     * Test the size method on a set with one element.
     */
    @Test
    public void testSizeOne() {
        Set<String> test = this.createFromArgsTest("a");
        Set<String> expected = this.createFromArgsTest("a");

        assertEquals(test.size(), expected.size());
        assertEquals(test, expected);
    }

    /**
     * Test the size method on a set with no elements.
     */
    @Test
    public void testSizeEmpty() {
        Set<String> test = this.createFromArgsTest();
        Set<String> expected = this.createFromArgsTest();

        assertEquals(test.size(), expected.size());
        assertEquals(test, expected);
    }

    /**
     * Test the contains method with a value that is not in the set.
     */
    @Test
    public void testContainsFalse() {
        Set<String> test = this.createFromArgsTest("a", "b", "c");
        Set<String> expected = this.createFromArgsTest("a", "b", "c");

        boolean contains = test.contains("d");
        boolean containsExpected = expected.contains("d");

        assertEquals(contains, containsExpected);
        assertEquals(test, expected);
    }

    /**
     * Test the contains method with a value that is in the set.
     */
    @Test
    public void testContainsTrue() {
        Set<String> test = this.createFromArgsTest("a", "b", "c");
        Set<String> expected = this.createFromArgsTest("a", "b", "c");

        boolean contains = test.contains("b");
        boolean containsExpected = expected.contains("b");

        assertEquals(contains, containsExpected);
        assertEquals(test, expected);
    }

    /**
     * Test the removeAny method.
     */
    @Test
    public void testRemoveAny() {
        // setup
        Set<String> test = this.createFromArgsTest("a", "b", "c");
        Set<String> expected = this.createFromArgsTest("a", "b", "c");

        // call
        String r = test.removeAny();

        // evaluation
        assertEquals(expected.contains(r), true);
        expected.remove(r);
        assertEquals(test, expected);
    }

    /**
     * Test the removeAny method on a set with one element.
     */
    @Test
    public void testRemoveAnyEmpty() {
        // setup
        Set<String> test = this.createFromArgsTest("a");
        Set<String> expected = this.createFromArgsTest("a");

        // call
        String r = test.removeAny();

        // evaluation
        assertEquals(expected.contains(r), true);
        expected.remove(r);
        assertEquals(test, expected);
    }

}
