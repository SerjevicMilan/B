package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import ngrams.NGramMap;
import org.eclipse.jetty.server.RequestLog;
import wnet.WordComparetor;
import wnet.WordNet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class HyponymsHandler extends NgordnetQueryHandler {
    WordNet wn;
    NGramMap ngm;

    public HyponymsHandler(WordNet wn, NGramMap n) {
        this.wn = wn;
        ngm = n;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> list = wn.getAllHyponyms(q.words());
        list.sort(new WordComparetor(ngm, q.startYear(), q.endYear()));

        list = kListElements(list, q.k());
        Collections.sort(list);

        return list.toString();
    }

    private List<String> kListElements(List<String> list, int k) {
        List<String> newList = new ArrayList<>();
        if (list.size() < k)
            k = list.size();
        for (int i = 0; i < k; i++) {
            newList.add(list.get(i));
        }
        return newList;
    }
}
