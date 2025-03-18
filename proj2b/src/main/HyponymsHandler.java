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
        //gets list of all hyponyms of a word
        List<String> list = wn.getAllHyponyms(q.words());

        //check if k(number of elements in array) is bigger then zero
        if (q.k() > 0) {
            list.sort(new WordComparetor(ngm, q.startYear(), q.endYear()));//sort list with custom comparator

            list = kListElements(list, q.k());// limits list to k elements
            Collections.sort(list);// sorts in normal order
        }

        return list.toString();
    }

    /*
    copies k elements of a list in new one
    @param list of Strings
    @param k number of elements
    @return string list of k elements
     */
    private List<String> kListElements(List<String> list, int k) {
        List<String> newList = new ArrayList<>();
        if (list.size() < k)//if list length less then k, list length is new k
            k = list.size();
        for (int i = 0; i < k; i++) {
            newList.add(list.get(i));
        }
        return newList;
    }
}
