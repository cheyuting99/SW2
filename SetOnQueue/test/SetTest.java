import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;

/**
 * JUnit test fixture for {@code Set<String>}'s constructor and kernel methods.
 *
 * @author Put your name here
 *
 */
public abstract class SetTest {

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
     *
     * @return the new set
     * @ensures constructorTest = {}
     */
    protected abstract Set<String> constructorTest();

    /**
     * Invokes the appropriate {@code Set} constructor and returns the result.
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

    // TODO - add test cases for constructor, add, remove, removeAny, contains, and size
    /**
     * test constructor.
     */
    @Test
    public void testConstructor() {
        Set<String> test = this.constructorTest();
        Set<String> expected = this.constructorRef();
        assertEquals(test, expected);
    }

    /**
     * test add method.
     */
    @Test
    public void addTest() {
        Set<String> test = this.createFromArgsTest("apple", "ball");
        Set<String> expected = this.createFromArgsRef("apple", "ball", "cat");
        expected.add(test);
        test.add("cat");
        assertEquals(test, expected);
    }

    /**
     * test remove method.
     */
    @Test
    public void removeTest() {
        Set<String> test = this.createFromArgsTest("apple", "ball", "cat");
        Set<String> expected = this.createFromArgsRef("apple", "cat");
        String x = test.remove("ball");
        assertEquals(x, "ball");
        assertEquals(test, expected);
    }

    /**
     * test size method.
     */
    @Test
    public void sizeTest() {
        Set<String> test = this.createFromArgsTest("apple", "ball", "cat");
        Set<String> expected = this.createFromArgsRef("apple", "ball", "cat");
        assertEquals(test.size(), 3);
        assertEquals(test, expected);
    }

    /**
     * test contains method.
     */
    @Test
    public void containsTrueTest() {
        Set<String> test = this.createFromArgsTest("apple", "ball", "cat");
        Set<String> expected = this.createFromArgsTest("apple", "ball", "cat");
        boolean contains = test.contains("cat");
        assertEquals(contains, true);
        assertEquals(test, expected);
    }

    @Test
    public void containsFalseTest() {
        Set<String> test = this.createFromArgsTest("apple", "ball", "cat");
        Set<String> expected = this.createFromArgsTest("apple", "ball", "cat");
        boolean contains = test.contains("bread");
        assertEquals(contains, false);
        assertEquals(test, expected);
    }

    /**
     * test removeAny method.
     */
    @Test
    public void removeAnyTest() {
        // setup
        Set<String> test = this.createFromArgsTest("apple", "ball", "cat");
        Set<String> expected = this.createFromArgsTest("apple", "ball", "cat");
        // call
        String r = test.removeAny();
        // evaluation
        assertEquals(expected.contains(r), true);
        expected.remove(r);
        assertEquals(test, expected);
    }

}
