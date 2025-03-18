import edu.princeton.cs.algs4.In;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import wnet.WordNet;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestWordNet {
    public static final String SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    public static final String BAD_SMALL_HYPONYM_FILE = "data/wordnet/hyponyms16.txt";
    public static final String BAD_SMALL_SYNSET_FILE = "data-1/wordnet/synsets16.txt";

    //create WordNet and test parsing of txt file and filling hashMap
    @Test
    public void TestProcessSynsetFile() {
        WordNet wn = new WordNet(SMALL_SYNSET_FILE, BAD_SMALL_HYPONYM_FILE);//construct WordNet with good input

        List<Integer> expected =
                new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15));

        //getSynset returns keys of HashMap filled with words corresponding to integers(keys)
        assertThat(wn.getSynset()).isEqualTo(expected);

        //construct WordNet with bad input
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                   WordNet wn1 = new WordNet(null, null);

                });

        Assertions.assertEquals("no file name arg", exception.getMessage());

        //construct WordNet with bad input
         exception = assertThrows(
                IllegalArgumentException.class,
                () -> {
                    WordNet wn1 = new WordNet(BAD_SMALL_SYNSET_FILE, BAD_SMALL_HYPONYM_FILE);
                });

        Assertions.assertEquals("Could not open data-1/wordnet/synsets16.txt", exception.getMessage());
    }

    //first word in synonym list is a first word for all synonyms
    //every synonym should return first ford
    @Test
    public void testRestOfSynonymsToFirstWord() {
        WordNet wn = new WordNet(SMALL_SYNSET_FILE, BAD_SMALL_HYPONYM_FILE);//construct WordNet with good input

        //jump leap saltation (all should return jump)
        assertThat(wn.getFirstWord("saltation")).isEqualTo("jump");
        assertThat(wn.getFirstWord("leap")).isEqualTo("jump");
        assertThat(wn.getFirstWord("jump")).isEqualTo("jump");
        //not existing word should return null
        assertThat(wn.getFirstWord("Milan")).isEqualTo(null);
    }

    /*
    getDirectHyponyms should return direct hyponyms(Integers references to words)
    or null if word doesn't  exist
     */
    @Test
    public void TestprocessHyponymFile() {
        WordNet wn = new WordNet(SMALL_SYNSET_FILE, BAD_SMALL_HYPONYM_FILE);//construct WordNet with good input

        List<String> expected = new ArrayList<>(List.of("happening", "act", "transition"));
        assertThat(wn.getDirectHyponyms("event")).isEqualTo(expected);

        expected = new ArrayList<>(List.of("conversion", "mutation"));
        assertThat(wn.getDirectHyponyms("adjustment")).isEqualTo(expected);

        expected = new ArrayList<>(List.of("alteration", "change", "demotion", "increase", "jump", "leap",
                "modification", "saltation", "transition", "variation"));

        assertThat(wn.getAllHyponyms(List.of("change"))).isEqualTo(expected);

        assertThat(wn.getDirectHyponyms("Milan")).isEqualTo(List.of());

    }


}
