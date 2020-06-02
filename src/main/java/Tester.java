package main.java;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by oliviachisman on 5/30/20
 */
public class Tester {

    private final HashMap<Integer, Integer> docClasses;
    private final HashMap<Integer, List<String>> docWords;
    private final HashSet<Integer> classes;
    private final HashMap<Integer, HashMap<String, Double>> probabilities;

    public Tester(HashMap<Integer, Integer> docClasses, HashMap<Integer, List<String>> docWords, HashSet<Integer> classes,
                  HashMap<Integer, HashMap<String, Double>> probabilities) {
        this.docClasses = docClasses;
        this.docWords = docWords;
        this.classes = classes;
        this.probabilities = probabilities;
    }

    /**
     * Returns number of correctly predicted documents
     */
    public double test() {
        int numCorrect = 0;
        int counter = 0;

        // For every document, find its predicted class
        for (int doc : docWords.keySet()) {
            // Map of class to number of words predicting that document will be in that class
            HashMap<Integer, Integer> classTally = getClassTally(doc);
            // Predicted class for doc based on class tally
            int clazz = getClassForDoc(classTally);

            // Print out first 20 predicted classes and their actual class categorizations.
            if (counter < 20) {
                System.out.println("Predicted class: " + clazz + ", Actual class: " + docClasses.get(doc));
            }

            // Count correctly categorized classes
            if (clazz == docClasses.get(doc)) {
                numCorrect++;
            }
            counter++;
        }

        return numCorrect;
    }

    // The predicted class for the document will be the one with the most tallies -- ie whatever class
    // the most words predicted.
    private int getClassForDoc(HashMap<Integer, Integer> classTally) {
        int max = Integer.MIN_VALUE;
        Integer clazz = null;
        for (int c : classTally.keySet()) {
            if (classTally.get(c) > max) {
                max = classTally.get(c);
                clazz = c;
            }
        }
        return clazz;
    }

    private HashMap<Integer, Integer> getClassTally(int doc) {
        // Map of each word in doc "doc" to the predicted class based on the training model probabilities
        HashMap<Integer, Integer> classTally = new HashMap<>();

        // For each word in document "doc", find the calculated
        // probability that it will be in each class
        for (String word : docWords.get(doc)) {
            double maxProbability = Double.MIN_VALUE;
            Integer clazz = null;
            for (int c : classes) {
                // find p(word|clazz) from the training model
                double p = probabilities.get(c).get(word);
                // Keep track of whichever class has the highest p(word|clazz)
                if (p > maxProbability) {
                    maxProbability = p;
                    clazz = c;
                }
            }
            // Once you've gotten the probability for each class, add one to the tally for
            // the class with the highest probability
            if (!classTally.containsKey(clazz)) {
                classTally.put(clazz, 0);
            }
            classTally.put(clazz, classTally.get(clazz) + 1);
        }

        return classTally;
    }
}
