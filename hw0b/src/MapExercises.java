import java.util.*;

public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        Map<Character, Integer> alph = new HashMap<>();
        int val = 1;
        for (char key = 'a'; key <= 'z'; key++) {
            alph.put(key, val);
            val++;
        }
        return alph;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        Map<Integer, Integer> sqr = new HashMap<>();
        for (int n : nums) {
            sqr.put(n, n * n);
        }
        return sqr;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        Map<String, Integer> cw = new TreeMap<>();
        for (String word : words) {
            if (cw.containsKey(word)) {
                cw.put(word, cw.get(word) + 1);
            }
            else {
                cw.put(word, 1);
            }
        }
        return cw;
    }
}
