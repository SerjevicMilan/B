import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import wnet.WordNet;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestWordNet {
    private static final String VERY_SHORT_WORDS_FILE = "data/ngrams/very_short.csv";
    public static final String SMALL_SYNSET_FILE = "data/wordnet/synsets16.txt";
    private static final String BAD_VERY_SHORT_WORDS_FILE = "data-1/ngrams/very_short.csv";
    public static final String BAD_SMALL_SYNSET_FILE = "data-1/wordnet/synsets16.txt";

    //create WordNet and test parsing of txt file and filling hashMap
    @Test
    public void TestProcessSynsetFile() {
        WordNet wn = new WordNet(SMALL_SYNSET_FILE, VERY_SHORT_WORDS_FILE);//construct WordNet with good input

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
                    WordNet wn1 = new WordNet(BAD_SMALL_SYNSET_FILE, BAD_VERY_SHORT_WORDS_FILE);
                });

        Assertions.assertEquals("Could not open data-1/wordnet/synsets16.txt", exception.getMessage());
    }
}
