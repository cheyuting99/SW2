import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;

/**
 * JUnit test fixture for {@code NaturalNumber}'s constructors and kernel
 * methods.
 *
 * @author A. Jagdale
 *
 */
public abstract class NaturalNumberTest {

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @return the new number
     * @ensures constructorTest = 0
     */
    protected abstract NaturalNumber constructorTest();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorTest = i
     */
    protected abstract NaturalNumber constructorTest(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorTest)
     */
    protected abstract NaturalNumber constructorTest(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * implementation under test and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorTest = n
     */
    protected abstract NaturalNumber constructorTest(NaturalNumber n);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @return the new number
     * @ensures constructorRef = 0
     */
    protected abstract NaturalNumber constructorRef();

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param i
     *            {@code int} to initialize from
     * @return the new number
     * @requires i >= 0
     * @ensures constructorRef = i
     */
    protected abstract NaturalNumber constructorRef(int i);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param s
     *            {@code String} to initialize from
     * @return the new number
     * @requires there exists n: NATURAL (s = TO_STRING(n))
     * @ensures s = TO_STRING(constructorRef)
     */
    protected abstract NaturalNumber constructorRef(String s);

    /**
     * Invokes the appropriate {@code NaturalNumber} constructor for the
     * reference implementation and returns the result.
     *
     * @param n
     *            {@code NaturalNumber} to initialize from
     * @return the new number
     * @ensures constructorRef = n
     */
    protected abstract NaturalNumber constructorRef(NaturalNumber n);

    // test cases for constructors

    /**
     * Tests the no argument constructor of NaturalNumber3 that gives the
     * NaturalNumber an initial value.
     */
    @Test
    public final void testNoArgumentConstructor() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest();
        NaturalNumber nExpected = this.constructorRef();
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * Tests the constructor from int for NaturalNumber3.
     */
    @Test
    public final void testConstructorFromInt() {
        /*
         * Set up variables and call method under test
         */
        final int i = 20;
        NaturalNumber n = this.constructorTest(i);
        NaturalNumber nExpected = this.constructorRef(i);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * Tests the constructor from int for NaturalNumber3 with a value of zero.
     */
    @Test
    public final void testConstructorFromIntZero() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * Tests the constructor from a String for NaturalNumber3.
     */
    @Test
    public final void testConstructorFromString() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("20");
        NaturalNumber nExpected = this.constructorRef("20");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * Tests the constructor from a String for NaturalNumber3 with a large
     * string that is bigger than the max value of an integer.
     */
    @Test
    public final void testConstructorFromStringLarge() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("1234567891011");
        NaturalNumber nExpected = this.constructorRef("1234567891011");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * Tests the constructor from a String for NaturalNumber3 with zero.
     */
    @Test
    public final void testConstructorFromStringZero() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("0");
        NaturalNumber nExpected = this.constructorRef("0");
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * Tests the constructor from a NaturalNumber for NaturalNumber3.
     */
    @Test
    public final void testConstructorFromNaturalNumber() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber twenty = this.constructorRef(0);
        NaturalNumber n = this.constructorTest(twenty);
        NaturalNumber nExpected = this.constructorRef(twenty);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * Tests the constructor from a NaturalNumber for NaturalNumber3 with a
     * NaturalNumber equal to zero.
     */
    @Test
    public final void testConstructorFromNaturalNumberZero() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber zero = this.constructorRef(0);
        NaturalNumber n = this.constructorTest(zero);
        NaturalNumber nExpected = this.constructorRef(zero);
        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    // tests of multiplyBy10

    /**
     * Tests the multiplyBy10 method on a NaturalNumber3 without adding
     * anything.
     */
    @Test
    public final void testMultiplyBy10WithoutAdding() {
        /*
         * Set up variables and call method under test
         */
        final int i = 102;
        NaturalNumber n = this.constructorTest(i);
        NaturalNumber nExpected = this.constructorRef(i);

        n.multiplyBy10(0);
        nExpected.multiplyBy10(0);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    /**
     * Tests the multiplyBy10 method on a NaturalNumber3 and adding 7.
     */
    @Test
    public final void testMultiplyBy10WithAdding() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("102");
        NaturalNumber nExpected = this.constructorRef("102");

        final int digit = 7;
        n.multiplyBy10(digit);
        nExpected.multiplyBy10(digit);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
    }

    // tests of divideBy10

    /**
     * Tests the divideBy10 method on a large NaturalNumber.
     */
    @Test
    public final void testDivideBy10Large() {
        /*
         * Set up variables and call method under test
         */
        final int i = 1027;
        NaturalNumber n = this.constructorTest(i);
        NaturalNumber nExpected = this.constructorRef(i);

        int r = n.divideBy10();
        int rExpected = nExpected.divideBy10();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(rExpected, r);
    }

    /**
     * Tests the divideBy10 method on a small NaturalNumber that will be reduced
     * to zero after the method is called.
     */
    @Test
    public final void testDivideBy10Small() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("5");
        NaturalNumber nExpected = this.constructorRef("5");

        int r = n.divideBy10();
        int rExpected = nExpected.divideBy10();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(rExpected, r);
    }

    /**
     * Tests the divideBy10 method on a NaturalNumber that will return a
     * remainder of zero.
     */
    @Test
    public final void testDivideBy10NoRemainder() {
        /*
         * Set up variables and call method under test
         */
        final int i = 50;
        NaturalNumber n = this.constructorTest(i);
        NaturalNumber nExpected = this.constructorRef(i);

        int r = n.divideBy10();
        int rExpected = n.divideBy10();

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(rExpected, r);
    }

    // tests of isZero

    /**
     * Tests the isZero method on a NaturalNumber that is equal to zero.
     */
    @Test
    public final void testIsZeroTrue() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest(0);
        NaturalNumber nExpected = this.constructorRef(0);

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(true, n.isZero());
    }

    /**
     * Tests the isZero method on a NaturalNumber that is not equal to zero.
     */
    @Test
    public final void testIsZeroFalse() {
        /*
         * Set up variables and call method under test
         */
        NaturalNumber n = this.constructorTest("27");
        NaturalNumber nExpected = this.constructorRef("27");

        /*
         * Assert that values of variables match expectations
         */
        assertEquals(nExpected, n);
        assertEquals(false, n.isZero());
    }
}
