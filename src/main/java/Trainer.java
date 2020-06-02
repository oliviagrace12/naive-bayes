package main.java;

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

        // For each class, calculating probability that each word appears in a document, given the document
        // is of that class.
        for (Integer clazz : classes) {
            probabilities.put(clazz, createProbabilityMap(clazz));
        }

        // Printing out some sample probabilities
        String[] sampleWords = {"program", "includ", "match", "game", "plai", "window", "file", "subject", "write"};
        for (String word : sampleWords) {
            System.out.println("p(\"" + word + "\" | \"Hockey\") = " + probabilities.get(1).get(word));
            System.out.println("p(\"" + word + "\" | \"Microsoft Windows\") = " + probabilities.get(0).get(word));
        }

        return probabilities;
    }

    private HashMap<String, Double> createProbabilityMap(int clazz) {
        // Map of words to the probability that they appear given the document is of class "clazz"
        HashMap<String, Double> probabilities = new HashMap<>();
        // Counting total number of words in all the documents in class "clazz"
        double totalWordsInDocsInClass = getTotalWordsInDocsInClass(clazz);

        for (String word : wordOccurrences.keySet()) {
            double p = getProbabilityOfWordGivenClass(word, clazz, totalWordsInDocsInClass, wordOccurrences.size());
            probabilities.put(word, p);
        }

        return probabilities;
    }

    /**
     * Returns total number of words in all the documents in class "clazz"
     */
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
        // Number of occurrences of word "word" in class "clazz"
        double numWordInClass = getNumWordInClass(word, clazz);

        // Calculating P(wi | ci) = (nij + 1) / (ni + |V|), where wi is "word", ci is "clazz", nij is
        // the number of occurrences of word "word" in class "clazz", ni is the total number of words
        // in all the docs in "clazz", and V is the number of unique words in all documents combined.
        return (numWordInClass + 1) / (totalWordsInDocsInClass + Math.abs(vocabulary));
    }

    /**
     * Returns the number of occurrences of word "word" in class "clazz"
     */
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
