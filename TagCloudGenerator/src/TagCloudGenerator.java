import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.sortingmachine.SortingMachine;
import components.sortingmachine.SortingMachine1L;

/**
 * Creates an HTML page containing a tag cloud of the most used words from an
 * input text file.
 *
 * @author Avani Jagdale
 */
public final class TagCloudGenerator {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private TagCloudGenerator() {

    }

    /**
     * Creates an output HTML file containing a tag cloud.
     *
     * @param input
     *            the name of the input file
     * @param fname
     *            the name of the output file
     * @param words
     *            sorted queue of words
     * @param counts
     *            map of word counts
     * @param fonts
     *            a map of the words and font sizes proportional to their
     *            frequencies
     */
    private static void createOutputFile(String input, String fname,
            Queue<String> words, Map<String, Integer> counts,
            Map<String, Integer> fonts) {

        // create a new output file from the users given file name and output header tags
        SimpleWriter fileOut = new SimpleWriter1L(fname);
        String title = "Top " + fonts.size() + " words in " + input;

        outputHeader(fileOut, title);

        // print each word in the queue at it's corresponding font size to the output file
        for (String w : words) {
            int count = counts.value(w);
            int size = fonts.value(w);
            addOneWord(fileOut, w, count, size);
        }

        // print footer tags and close the output stream
        outputFooter(fileOut);
        fileOut.close();
    }

    /**
     * Outputs the "opening" tags in the HTML file.
     *
     * @param fileOut
     *            the output stream
     * @param title
     *            the title for the HTML page
     * @updates fileOut.content
     * @requires fileOut.is_open
     * @ensures fileOut.content = #fileOut.content * [the HTML "opening" tags]
     */
    private static void outputHeader(SimpleWriter fileOut, String title) {
        assert fileOut != null : "Violation of: out is not null";
        assert fileOut.isOpen() : "Violation of: out.is_open";

        // output header html tags with title
        fileOut.println("<html>");
        fileOut.println("<head>");
        fileOut.println("<title>" + title + "</title>");

        // links to stylesheet
        String link = "\"http://web.cse.ohio-state.edu/software/2231/web-sw2/";
        String link2 = "assignments/projects/tag-cloud-generator/data/tagcloud.css\"";
        fileOut.println("<link href=" + link + link2
                + "rel=\"stylesheet\" type=\"text/css\">");
        fileOut.println(
                "<link href=\"tagcloud.css\" rel=\"stylesheet\" type=\"text/css\">");

        fileOut.println("</head>");
        fileOut.println("<body>");
        fileOut.println("  <h2>" + title + "</h2>");
        fileOut.println("  <hr>");

        fileOut.println("  <div class=\"cdiv\"> ");
        fileOut.println("  <p class=\"cbox\">");
    }

    /**
     * Outputs each word to the tag cloud.
     *
     * @param fileOut
     *            the output stream
     * @param word
     *            a word in the text file
     * @param count
     *            number of times the word appears in the text file
     * @param size
     *            font size that the word should be output in
     * @updates fileOut.contents
     * @requires fileOut.is_open
     * @ensures fileOut.content = #fileOut.content * [a new word in the tag
     *          cloud with correct font size]
     */
    private static void addOneWord(SimpleWriter fileOut, String word, int count,
            int size) {
        assert fileOut != null : "Violation of: out is not null";
        assert fileOut.isOpen() : "Violation of: out.is_open";

        String font = "\"f" + size + "\"";
        fileOut.print("  <span style=\"cursor:default\" class=" + font);
        fileOut.print(" title = \"count: " + count + "\">" + word);
        fileOut.println("</span>");
    }

    /**
     * Outputs the "closing" tags in the HTML file.
     *
     * @param fileOut
     *            the output stream
     * @updates fileOut.contents
     * @requires fileOut.is_open
     * @ensures fileOut.content = #fileOut.content * [the HTML "closing" tags]
     */
    private static void outputFooter(SimpleWriter fileOut) {
        assert fileOut != null : "Violation of: out is not null";
        assert fileOut.isOpen() : "Violation of: out.is_open";

        fileOut.println("  </p>");
        fileOut.println("  </div>");
        fileOut.println(" </body>");
        fileOut.println("</html>");
    }

    /**
     * Returns the first "word" (maximal length string of characters not in
     * {@code separators}) or "separator string" (maximal length string of
     * characters in {@code separators}) in the given {@code text} starting at
     * the given {@code position}.
     *
     * @param text
     *            the {@code String} from which to get the word or separator
     *            string
     * @param position
     *            the starting index
     * @param separators
     *            the {@code Set} of separator characters
     * @return the first word or separator string found in {@code text} starting
     *         at index {@code position}
     * @requires 0 <= position < |text|
     * @ensures <pre>
     * nextWordOrSeparator =
     *   text[position, position + |nextWordOrSeparator|)  and
     * if entries(text[position, position + 1)) intersection separators = {}
     * then
     *   entries(nextWordOrSeparator) intersection separators = {}  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      intersection separators /= {})
     * else
     *   entries(nextWordOrSeparator) is subset of separators  and
     *   (position + |nextWordOrSeparator| = |text|  or
     *    entries(text[position, position + |nextWordOrSeparator| + 1))
     *      is not subset of separators)
     * </pre>
     */
    public static String nextWordOrSeparator(String text, int position,
            Set<Character> separators) {
        assert text != null : "Violation of: text is not null";
        assert separators != null : "Violation of: separators is not null";
        assert 0 <= position : "Violation of: 0 <= position";
        assert position < text.length() : "Violation of: position < |text|";

        // initialize starting index and string for the next word
        int i = position;
        String next = "";

        // check if the character at the starting position is a separator
        boolean isSeparator = separators.contains(text.charAt(position));

        if (!isSeparator) {
            // check each char in text after the starting index until a separator is found
            while (!isSeparator && i < text.length()) {
                Character c = text.charAt(i);
                if (!separators.contains(c)) {
                    next += c;
                } else {
                    isSeparator = true;
                }
                i++;
            }
        } else {
            // if the starting index is a separator, return only the separator
            next = text.substring(position, position + 1);
        }

        return next;

    }

    /**
     * Generates the set of characters in the given {@code String} into the
     * given {@code Set}.
     *
     * @param str
     *            the given {@code String}
     * @param charSet
     *            the {@code Set} to be replaced
     * @replaces charSet
     * @ensures charSet = entries(str)
     */
    public static void generateSeparator(String str, Set<Character> charSet) {
        assert str != null : "Violation of: str is not null";
        assert charSet != null : "Violation of: charSet is not null";

        // get each character in the given string
        for (int i = 0; i < str.length(); i++) {
            Character c = str.charAt(i);
            // add the character to a set of characters if it's not already in the set
            if (!charSet.contains(c)) {
                charSet.add(c);
            }
        }

    }

    /**
     * Populates the given map with each word and the number of times it occurrs
     * in the given text file.
     *
     * @param input
     *            the input stream for the text file
     * @param wordCount
     *            the map that stores word counts
     * @updates wordCount
     * @requires <pre>
     * input.is_open
     * </pre>
     * @ensures [wordCount contains word -> word count mapping from input file]
     */
    private static void countWords(SimpleReader input,
            Map<String, Integer> wordCount) {
        assert input != null : "Violation of: input is not null";
        assert input.isOpen() : "Violation of: input.is_open";

        // create a string of all the text in the file
        String text = "";
        while (!input.atEOS()) {
            text += input.nextLine();
            text += " ";
        }

        // generate a set of common punctuation marks and whitespace characters
        String punct = "\t\n\r,-.!?[]';:/() ";
        Set<Character> separators = new Set1L<>();
        generateSeparator(punct, separators);

        // create a queue containing each word or punctuation mark in the text
        Queue<String> words = new Queue1L<String>();

        // start at the beginning of the text string
        int position = 0;

        // go through every token (either word or separator) in the string
        while (position < text.length()) {
            // add all tokens that are not part of the punctuation string to the queue
            String token = nextWordOrSeparator(text, position, separators);
            if (punct.indexOf(token) < 0) {
                words.enqueue(token);
            }
            // set a new starting point that comes right after the last token
            position += token.length();

        }

        // go through each word in the queue
        for (String w : words) {
            String word = w.toLowerCase();
            // add the word to the map if it's not already in it
            if (!wordCount.hasKey(word)) {
                wordCount.add(word, 1);
            } else {
                // if the word is in the map, increment its value (word count) by 1
                int count = wordCount.value(word);
                count += 1;
                wordCount.remove(word);
                wordCount.add(word, count);
            }
        }
    }

    /**
     * Calculates the font size that a word should be printed on based on it's
     * frequency, and the minimum and maximum frequencies in the tag cloud.
     *
     * @param count
     *            the frequency of a word
     * @param min
     *            the lowest frequency of any word in the tag cloud
     * @param max
     *            the highest frequency of any word in the tag cloud
     * @return the font size for a word based on it's count
     */
    private static int getSize(int count, int min, int max) {
        // max and min font sizes on the stylesheet
        final int biggest = 48;
        final int smallest = 11;

        double interval1 = count - min;
        double interval2 = max - min;
        if (max == min) {
            interval2 = 1;
        }

        double interval = interval1 / interval2 * (biggest - smallest);
        int fontSize = smallest + (int) interval;

        return fontSize;
    }

    /**
     * Compare keys of a pair in a Map<String, Integer>.
     */
    private static class CompKeys
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> p1,
                Map.Pair<String, Integer> p2) {
            return p1.key().compareTo(p2.key());
        }
    }

    /**
     * Compare values of a pair in a Map<String, Integer>.
     */
    private static class CompValues
            implements Comparator<Map.Pair<String, Integer>> {
        @Override
        public int compare(Map.Pair<String, Integer> p1,
                Map.Pair<String, Integer> p2) {
            // inverts result to sort from highest to lowest
            return p1.value().compareTo(p2.value()) * -1;
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        // open input and output streams
        SimpleWriter out = new SimpleWriter1L();
        SimpleReader in = new SimpleReader1L();

        // get input and output files from user
        out.print("enter the name of an input text file: ");
        String inputFile = in.nextLine();
        out.print("enter the name of an output file: ");
        String outputFile = in.nextLine();

        // get user number of most used words
        out.print("enter the number of words to include in your tag cloud: ");
        int n = in.nextInteger();

        // map each word in the file to its word count
        SimpleReader fileIn = new SimpleReader1L(inputFile);
        Map<String, Integer> wordCount = new Map1L<String, Integer>();
        countWords(fileIn, wordCount);
        fileIn.close();

        // make sure user input is valid for size of the file
        while (n > wordCount.size() || n < 0) {
            out.print(
                    "not enough unique words in your file. enter a new number: ");
            n = in.nextInteger();
        }

        /*
         * add all the words from the count map into two sorting machines, one
         * to sort alphabetically, and one to sort by count
         */

        // sorts by keys (alphabetically)
        Comparator<Map.Pair<String, Integer>> ck = new CompKeys();
        SortingMachine<Map.Pair<String, Integer>> sk = new SortingMachine1L<>(
                ck);

        // sorts by values (highest to lowest frequency)
        Comparator<Map.Pair<String, Integer>> cv = new CompValues();
        SortingMachine<Map.Pair<String, Integer>> sv = new SortingMachine1L<>(
                cv);

        // add all the word-frequency pairs to a sorting machine that sorts by frequency
        for (Map.Pair<String, Integer> pair : wordCount) {
            sv.add(pair);
        }
        sv.changeToExtractionMode();

        /*
         * remove the n most frequently used words from the sorting machine that
         * sorts based on word count, and add them to the sorting machine that
         * sorts them alphabetically. remove the first and last elements
         * separately to store their values.
         */

        // max count value is extracted first
        int i = 0;
        Map.Pair<String, Integer> r = sv.removeFirst();
        int max = r.value();
        sk.add(r);

        while (i < n - 2) {
            r = sv.removeFirst();
            sk.add(r);
            i++;
        }

        // min count value is extracted last
        r = sv.removeFirst();
        int min = r.value();
        sk.add(r);
        sk.changeToExtractionMode();

        // add the top n words to a queue in alphabetical order
        Queue<String> words = new Queue1L<String>();
        while (sk.size() > 0) {
            Map.Pair<String, Integer> pair = sk.removeFirst();
            words.enqueue(pair.key());
        }

        // calculate font size for each word, proportional to frequency, and add to a map
        Map<String, Integer> fontMap = new Map1L<>();
        for (String w : words) {
            int count = wordCount.value(w);
            int fontSize = getSize(count, min, max);
            fontMap.add(w, fontSize);
        }

        // create the html page
        createOutputFile(inputFile, outputFile, words, wordCount, fontMap);

        // close input and output streams
        in.close();
        out.close();
    }

}
