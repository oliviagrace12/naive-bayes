package main.java;

import javafx.beans.binding.IntegerBinding;

import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by oliviachisman on 5/30/20
 */
public class Trainer {

    private final HashMap<String, HashMap<Integer, Double>> wordOccurrences;
    private final HashMap<Integer, Integer> docClasses;
    private final HashSet<Integer> classes;

    public Trainer(HashMap<String, HashMap<Integer, Double>> wordOccurrences, HashMap<Integer, Integer> docClasses, HashSet<Integer> classes) {
        this.wordOccurrences = wordOccurrences;
        this.docClasses = docClasses;
        this.classes = classes;
    }

    public HashMap<Integer, HashMap<String, Double>> train() {
        // A map of each class to a probability map of each word to its probability
        // that if a word is present, that doc is in the class
        HashMap<Integer, HashMap<String, Double>> probabilities = new HashMap<>();

        for (Integer clazz : classes) {
            probabilities.put(clazz, createProbabilityMap(clazz));
        }

        return probabilities;
    }

    private HashMap<String, Double> createProbabilityMap(int clazz) {
        HashMap<String, Double> probabilities = new HashMap<>();
        double totalWordsInDocsInClass = getTotalWordsInDocsInClass(clazz);

        for (String word : wordOccurrences.keySet()) {
            double p = getProbabilityOfWordGivenClass(word, clazz, totalWordsInDocsInClass, wordOccurrences.size());
            probabilities.put(word, p);
        }

        return probabilities;
    }

    private double getTotalWordsInDocsInClass(int clazz) {
        int num = 0;
        for (String w : wordOccurrences.keySet()) {
            for (int doc : wordOccurrences.get(w).keySet()) {
                double numWordInDoc = wordOccurrences.get(w).get(doc);
                if (clazz == docClasses.get(doc)) {
                    num += numWordInDoc;
                }
            }
        }

        return num;
    }

    private double getProbabilityOfWordGivenClass(String word, int clazz, double totalWordsInDocsInClass, double vocabulary) {
        double numWordInClass = getNumWordInClass(word, clazz);

        return (numWordInClass + 1) / (totalWordsInDocsInClass + vocabulary) ;
    }

    private double getNumWordInClass(String word, int clazz) {
        int num = 0;
        HashMap<Integer, Double> docsContainingWord = wordOccurrences.get(word);
        for (int doc : docsContainingWord.keySet()) {
            if (clazz == docClasses.get(doc)) {
                num += docsContainingWord.get(doc);
            }
        }

        return num;
    }

}
