import ngrams.NGramMap;
import org.junit.jupiter.api.Test;
import wnet.WordComparetor;
import wnet.WordNet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.TreeSet;

import static com.google.common.truth.Truth.assertThat;

public class TestWordComperator {
    public static final String TOTAL_COUNTS_FILE = "data/ngrams/total_counts.csv";
    private static final String FREQUENCY_EECS_FILE = "data/ngrams/frequency-EECS.csv";
    private static final String HYPONYMS_EECS_FILE = "data/wordnet/hyponyms-EECS.txt";
    private static final String SYNSETS_EECS_FILE = "data/wordnet/synsets-EECS.txt";

    private List<String> kListElements(List<String> list, int k) {
        List<String> newList = new ArrayList<>();
        if (list.size() < k)
            k = list.size();
        for (int i = 0; i < k; i++) {
            newList.add(list.get(i));
        }
        return newList;
    }

    @Test
    public void TestComperatorBasic() {
        NGramMap ngm = new NGramMap(FREQUENCY_EECS_FILE, TOTAL_COUNTS_FILE);
        WordNet wn = new WordNet(SYNSETS_EECS_FILE, HYPONYMS_EECS_FILE);

        List<String> list = wn.getAllHyponyms(List.of("CS61A"));
        list.sort(new WordComparetor(ngm, 2010, 2020));
        list = kListElements(list, 4);
        Collections.sort(list);

        assertThat(list).isEqualTo(List.of("CS170", "CS61A", "CS61B", "CS61C"));
    }
}
