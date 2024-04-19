import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Creates an HTML page containing a tag cloud of the most used words from an
 * input text file.
 *
 * @author Avani Jagdale
 * @author Yuting Che
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
            List<String> words, Map<String, Integer> counts,
            Map<String, Integer> fonts) {

        // create a new file output stream from the users given file name
        PrintWriter fileOut;
        try {
            fileOut = new PrintWriter(
                    new BufferedWriter(new FileWriter(fname)));
        } catch (IOException e) {
            System.err.println("Error creating ouput file");
            return;
        }

        // print header tags with a title containing the input filename to the html output
        String title = "Top " + fonts.size() + " words in " + input;
        outputHeader(fileOut, title);

        // print each word at it's corresponding font size to the output
        for (String w : words) {
            int count = counts.get(w);
            int size = fonts.get(w);
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
     * @requires fileOut is not null
     * @ensures fileOut.content = #fileOut.content * [the HTML "opening" tags]
     */
    private static void outputHeader(PrintWriter fileOut, String title) {
        assert fileOut != null : "Violation of: out is not null";

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
     * Outputs a word to the tag cloud.
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
     * @requires fileOut is not null
     * @ensures fileOut.content = #fileOut.content * [a new word in the tag
     *          cloud with correct font size]
     */
    private static void addOneWord(PrintWriter fileOut, String word, int count,
            int size) {
        assert fileOut != null : "Violation of: out is not null";

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
    private static void outputFooter(PrintWriter fileOut) {
        assert fileOut != null : "Violation of: out is not null";

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
     * in the given text string.
     *
     * @param input
     *            the input file
     * @param wordCount
     *            the map that stores word counts
     * @updates wordCount
     * @requires text is not null
     * @ensures [wordCount contains word -> word count mapping from input
     *          string]
     */
    private static void countWords(BufferedReader input,
            Map<String, Integer> wordCount) {
        assert input != null : "Violation of: text is not null";

        // generate a set of common punctuation marks and whitespace characters
        String punct = "\t\n\r,-.!?[]';:/() ";
        Set<Character> separators = new HashSet<>();
        generateSeparator(punct, separators);

        // create a string of all the text in the file
        String text = "";
        try {
            String s = input.readLine();
            while (s != null) {
                text += s + " ";
                s = input.readLine();
            }
        } catch (IOException e) {
            System.err.println("Error reading file");
        }

        // a list of each word or punctuation mark in the text
        List<String> words = new ArrayList<String>();

        // start at the beginning of the text string
        int position = 0;

        // go through every token (either word or separator) in the string
        while (position < text.length()) {
            // add all tokens that are not separators to the word list
            String token = nextWordOrSeparator(text, position, separators);
            if (punct.indexOf(token) < 0) {
                words.add(token);
            }
            // set a new starting point that comes right after the last token
            position += token.length();
        }

        // go through each word in the list
        for (String w : words) {
            String word = w.toLowerCase();
            // add the word to the map if it's not already in it
            if (!wordCount.containsKey(word)) {
                wordCount.put(word, 1);
            } else {
                // if the word is in the map, increment its value (word count) by 1
                int count = wordCount.get(word);
                count += 1;
                wordCount.remove(word);
                wordCount.put(word, count);
            }
        }
    }

    /**
     * Calculates the font size that a word should be printed at based on it's
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
     * A method to determine if a string input by a user is a valid integer
     * value that is positive and smaller than the number of unique words
     * available in the file for the tag cloud.
     *
     * @param s
     *            user input
     * @param maxN
     *            number of unique words in the file
     * @return whether or not the user input is a valid integer value within
     *         range
     */
    private static boolean checkInput(String s, int maxN) {
        boolean goodInput = true;
        // check if input string can be parsed into an integer value
        try {
            int n = Integer.parseInt(s);
            // check if n is positive and less than the number of words in the input file
            goodInput = n > 0 && n < maxN;
        } catch (NumberFormatException e) {
            // set the return value to false if string input is not a valid integer
            goodInput = false;
        }
        return goodInput;
    }

    /**
     * Compare keys of two Map.Entry<String, Integer> objects.
     */
    private static class CompKeys
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> p1,
                Map.Entry<String, Integer> p2) {
            return p1.getKey().compareTo(p2.getKey());
        }
    }

    /**
     * Compare values of two Map.Entry<String, Integer> objects.
     */
    private static class CompValues
            implements Comparator<Map.Entry<String, Integer>> {
        @Override
        public int compare(Map.Entry<String, Integer> p1,
                Map.Entry<String, Integer> p2) {
            // inverts result to sort from highest to lowest
            return p1.getValue().compareTo(p2.getValue()) * -1;
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        // get input and output files from user
        Scanner in = new Scanner(System.in);

        System.out.print("enter the name of an input text file: ");
        String fname = in.nextLine();

        System.out.print("enter the name of an output file: ");
        String fout = in.nextLine();

        // create input stream to read file
        BufferedReader input;
        try {
            input = new BufferedReader(new FileReader(fname));
        } catch (IOException e) {
            System.err.println("error accessing file");
            in.close();
            return;
        }

        // read the file and create a map of word counts
        Map<String, Integer> wordCount = new HashMap<String, Integer>();
        countWords(input, wordCount);

        // close input stream
        try {
            input.close();
        } catch (IOException e) {
            System.err.println("error closing file");
            in.close();
            return;
        }

        // get the number of words for the tag cloud
        System.out.print("number of words to include in your tag cloud: ");
        String s = in.nextLine();

        // continually check for valid user input
        boolean validInt = checkInput(s, wordCount.size());
        while (!validInt) {
            System.out.println("please enter a valid positive integer.");
            System.out.print(
                    "must be smaller than the number of unique words in the file: ");
            s = in.nextLine();
            validInt = checkInput(s, wordCount.size());
        }
        // once valid input is received, store it as an integer value
        int n = Integer.parseInt(s);
        in.close();

        /*
         * add all the words from the count map into two lists. sort one
         * alphabetically, and the other by count
         */

        // sort by keys (alphabetically)
        Comparator<Map.Entry<String, Integer>> ck = new CompKeys();
        List<Map.Entry<String, Integer>> sk = new ArrayList<>();

        // sort by values (highest to lowest frequency)
        Comparator<Map.Entry<String, Integer>> cv = new CompValues();
        List<Map.Entry<String, Integer>> sv = new ArrayList<>();

        // add all the word-frequency pairs to the list being sorted by frequency
        for (Map.Entry<String, Integer> pair : wordCount.entrySet()) {
            sv.add(pair);
        }

        // sort pairs according to the order provided by the value-based comparator
        sv.sort(cv);

        // get max and min counts within the specified range (first and nth entry in list)
        int max = sv.get(0).getValue();
        int min = sv.get(n - 1).getValue();

        // add the n most frequently used words to the list being sorted alphabetically
        int i = 0;
        while (i < n) {
            sk.add(sv.get(i));
            i++;
        }

        // sort pairs alphabetically, according to the key-based comparator's order
        sk.sort(ck);

        // get all of the words (keys from the list of pairs)
        List<String> words = new ArrayList<String>();
        i = 0;
        while (sk.size() > i) {
            Map.Entry<String, Integer> pair = sk.get(i);
            // add each word to the word list in alphabeticla order
            words.add(pair.getKey());
            i++;
        }

        // calculate font size for each word, proportional to frequency, and add to a map
        Map<String, Integer> fontMap = new HashMap<>();
        for (String w : words) {
            int count = wordCount.get(w);
            int fontSize = getSize(count, min, max);
            fontMap.put(w, fontSize);
        }

        // create the html page
        createOutputFile(fname, fout, words, wordCount, fontMap);
    }
}
