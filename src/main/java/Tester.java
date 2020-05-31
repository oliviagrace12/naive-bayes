package main.java;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

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
        for (int doc : docWords.keySet()) {
            HashMap<Integer, Integer> classTally = getClassTally(doc);
            int clazz = getClassForDoc(classTally);

            if (counter < 20) {
                System.out.println("Predicted class: " + clazz + ", Actual class: " + docClasses.get(doc));
            }

            if (clazz == docClasses.get(doc)) {
                numCorrect++;
            }
            counter++;
        }

        return numCorrect;
    }

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
        HashMap<Integer, Integer> classTally = new HashMap<>();

        for (String word : docWords.get(doc)) {
            double maxProbability = Double.MIN_VALUE;
            Integer clazz = null;
            for (int c : classes) {
                double p = probabilities.get(c).get(word);
                if (p > maxProbability) {
                    maxProbability = p;
                    clazz = c;
                }
            }
            if (!classTally.containsKey(clazz)) {
                classTally.put(clazz, 0);
            }
            classTally.put(clazz, classTally.get(clazz) + 1);
        }

        return classTally;
    }
}
