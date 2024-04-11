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
 * Program to convert a given string from a given txt file into the
 * corresponding HTML output file.
 *
 * @author Yuting Che
 *
 */
public final class TagCloud {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private TagCloud() {
    }

    /**
     * Add Map pairs with the terms and the number that terms appear from the input file.
     *
     * @param in
     *            input from file
     * @param pairs
     *            the map that contains term and appear numbers
     * @update pairs
     */
    public static void processInput(SimpleReader in,
            Map<String, Integer> pairs) {
        String token = "";

        String str = "\t\n\r,-.!?[]';:/()\"    ";
        Set<Character> charSet = new Set1L<>();
        generateElements(str, charSet);

        // Process the file and add terms and count time to the Map.
        while (!in.atEOS()) {
            String line = in.nextLine();

            int position = 0;
            while (position < line.length()) {
                token = nextWordOrSeparator(line, position,
                        charSet);
                boolean isNotSeparator = true;
                if (charSet.contains(token.charAt(0))) {
                	isNotSeparator = false;
                }
                if (isNotSeparator) {
                	boolean exist = pairs.hasKey(token.toLowerCase());
                	if (!exist) {
                		pairs.add(token.toLowerCase(), 1);
                	} else {
                		int time = pairs.value(token.toLowerCase());
                		pairs.replaceValue(token.toLowerCase(), time + 1);
                	}
                }
                position += token.length();
            }
        }

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
    public static void generateElements(String str, Set<Character> charSet) {
        assert str != null : "Violation of: str is not null";
        assert charSet != null : "Violation of: charSet is not null";

        charSet.clear();
        for (int i = 0; i < str.length(); i++) {
            if (!charSet.contains(str.charAt(i))) {
                charSet.add(str.charAt(i));
            }
        }
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

        String result = "";
        result = result.concat(text.substring(position, position + 1));
        int next = position + 1;
        if (!separators.contains(text.charAt(position))) {
            while ((next < text.length())
                    && (!separators.contains(text.charAt(next)))) {
                result = result.concat(text.substring(next, next + 1));
                next++;
            }
        } else {
            while ((next < text.length())
                    && (separators.contains(text.charAt(next)))) {
                result = result.concat(text.substring(next, next + 1));
                next++;
            }
        }
        return result;
    }

    /**
     * Compare {@code String}s in lexicographic order.
     */
    private static class StringLT implements Comparator<String> {
        @Override
        public int compare(String o1, String o2) {
            String o1LowerCase = o1.toLowerCase();
            String o2LowerCase = o2.toLowerCase();
            return o1LowerCase.compareTo(o2LowerCase);
        }
    }

    /**
     * Compare {@code Integer}s in decreasing order.
     */
    private static class IntegerLT implements Comparator<Integer> {
        @Override
        public int compare(Integer o1, Integer o2) {
            return o2 - o1;
        }
    }
    /**
     * Generates an HTML page displaying the top 100 words in a specified heading,
     * along with their corresponding counts. The words are
     * displayed in a tag cloud format, where the size of each word is determined by
     * its count relative to the total counts of all words.
     *
     * @param heading
     *               the heading to be displayed at the top of the HTML page
     * @param output
     *               the output stream to write the HTML content to
     * @param word
     *               a queue containing the words to be displayed in a specific order
     * @param sortedNum
     *               a map containing the counts of each word
     * @param max
     *               the maximum count among all words
     * @param min
     *               the minimum count among all words
     *
     * @updates output.content
     * @requires output.is_open
     * @ensures output.content = #output.content * [the HTML tags]
     */
    public static void processHTML(String heading, SimpleWriter output,
    		  Queue<String> word, Map<String, Integer> sortedNum, Integer max,
    		  Integer min) {
    	output.print("<html>\r\n" + "<head>\r\n" + "<title>Top 100 words in "
    		 + heading);
    	output.print("</title>\r\n");
    	output.print("<link href=\"http://web.cse.ohio-state.edu/soft"
    			+ "ware/2231/web-sw2/assignments/projects/tag-cloud-gen"
    			+ "erator/data/tagcloud.css\" "
    	        + "rel=\"stylesheet\" type=\"text/css\">\r\n");
    	output.print("<link href=\"tagcloud.css\" rel=\"style"
    			+ "sheet\" type=\"text/css\">\r\n"
    			+ "</head>\r\n" + "<body>\r\n" + "<h2>"
    			+ "Top 100 words in " + heading +  "</h2>\r\n");
    	output.print("<hr>\r\n"
    			+ "<div class=\"cdiv\">\r\n"
    			+ "<p class=\"cbox\">\r\n");
    	while (word.length() > 0) {
    		String term = word.dequeue();
    		int count = sortedNum.value(term);
    		int size = 11;
    		int time = 0;
    		int gap = (max - min)/ 38;
    		for (int i = 0; i < 37; i++) {
    			if (count > time + gap) {
    				time = time + gap;
    				size++;
    			}
    		}

    		output.println("<span style=\"cursor:default\" class=\"f"
    	           + size + "\" title=\"count: " + count + "\">"
    	           + term + "</span>");
    	}
    	output.print("</p>\r\n"
    			+ "</div>\r\n"
    			+ "</body>\r\n"
    			+ "</html>");
    }
    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Enter the input file name:");
        String inputFile = in.nextLine();
        out.print("Enter the output file name:");
        String result = in.nextLine();
        SimpleWriter output = new SimpleWriter1L(result);

        Map<String, Integer> unsortedTerms = new Map1L<String, Integer>();

        SimpleReader processFile = new SimpleReader1L(inputFile);
        processInput(processFile, unsortedTerms);

        Comparator<Integer> ci = new IntegerLT();
        SortingMachine<Integer> si = new SortingMachine1L<>(ci);
        for (Map.Pair<String, Integer> p : unsortedTerms) {
        	int num = p.value();
        	//System.out.print(num);
        	si.add(num);
        }
        Map<String, Integer> sortedNum = new Map1L<String, Integer>();
        si.changeToExtractionMode();
        int max = 0;
        int min = 0;
        for (int i = 0; i < 100; i++) {
        	int num = si.removeFirst();
        	System.out.println(num);
        	sortedNum.add(unsortedTerms.key(num), num);
        	unsortedTerms.remove(unsortedTerms.key(num));
        	if (i == 0) {
        		max = num;
        	}
        	if (i == 99) {
        		min = num;
        	}
        }

        Comparator<String> ci2 = new StringLT();
        SortingMachine<String> si2 = new SortingMachine1L<>(ci2);
        for (Map.Pair<String, Integer> p : sortedNum) {
        	String term = p.key();
        	si2.add(term);
        }
        //Map<String, Integer> sortedTerms = new Map1L<String, Integer>();
        Queue<String> word = new Queue1L<>();
        si2.changeToExtractionMode();
        while (si2.size() > 0) {
        	String term = si2.removeFirst();
            word.enqueue(term);
            //System.out.println(term + " " + sortedNum.value(term));
        }

        processHTML(inputFile, output, word, sortedNum, max, min);

        in.close();
        out.close();
        output.close();
    }

}
