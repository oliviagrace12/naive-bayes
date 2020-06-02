package main.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by oliviachisman on 5/30/20
 */
public class DataProcessor {

    /**
     * Ordered list of all possible words. Index corresponds to word number in other files.
     */
    private ArrayList<String> words;
    /**
     * Map of all documents (each represented by a number) to their class type number.
     */
    private HashMap<Integer, Integer> docClasses;
    /**
     * Map of all words to their own map of which documents contain them and how many occurrences.
     */
    private HashMap<String, HashMap<Integer, Double>> wordOccurrences;
    /**
     * Map of all documents to the words occurring in that document.
     */
    private HashMap<Integer, List<String>> docWords;
    /**
     * Set of all possible classes.
     */
    private HashSet<Integer> classes;

    public ArrayList<String> getWords() {
        return words;
    }

    public HashMap<Integer, Integer> getDocClasses() {
        return docClasses;
    }

    public HashMap<String, HashMap<Integer, Double>> getWordOccurrences() {
        return wordOccurrences;
    }

    public HashMap<Integer, List<String>> getDocWords() {
        return docWords;
    }

    public HashSet<Integer> getClasses() {
        return classes;
    }

    public DataProcessor(File wordFile, File trainingDocClasses, File wordOccurrences) throws IOException {
        this.words = getWords(wordFile);
        this.docClasses = getClassesOfDocs(trainingDocClasses);
        this.wordOccurrences = generateWordOccurrences(wordOccurrences);
        this.docWords = generateDocWords();
        this.classes = getAllClasses();
    }

    private HashSet<Integer> getAllClasses() {
        HashSet<Integer> classes = new HashSet<>();
        classes.addAll(docClasses.values());

        return classes;
    }

    /**
     * Returns a map of document number to list of words appearing in that document.
     */
    private HashMap<Integer, List<String>> generateDocWords() {
        HashMap<Integer, List<String>> docToWordsMap = new HashMap<>();
        for (String word : wordOccurrences.keySet()) {
            for (Integer doc : wordOccurrences.get(word).keySet()) {
               if (!docToWordsMap.containsKey(doc)) {
                   docToWordsMap.put(doc, new ArrayList<>());
               }
               docToWordsMap.get(doc).add(word);
            }
        }
        return docToWordsMap;
    }

    /**
     * Returns a map of each word to a map of all documents containing that word to the number of times the word
     * appears in the document.
     */
    private HashMap<String, HashMap<Integer, Double>> generateWordOccurrences(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        HashMap<String, HashMap<Integer, Double>> testWordOccurrences = new HashMap<>();
        String row;
        int wordNum = 0;
        while ((row = reader.readLine()) != null) {
            String[] parts = row.split("\t");
            HashMap<Integer, Double> occurrences = new HashMap<>();
            for (int docNumber = 0; docNumber < parts.length; docNumber++) {
                double numOccurrences = Double.parseDouble(parts[docNumber]);
                if (numOccurrences > 0d) {
                    occurrences.put(docNumber, numOccurrences);
                }
            }
            testWordOccurrences.put(words.get(wordNum), occurrences);
            wordNum++;
        }
        return testWordOccurrences;
    }

    /**
     * Returns a map of documents to their class
     */
    private HashMap<Integer, Integer> getClassesOfDocs(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        HashMap<Integer, Integer> classes = new HashMap<>();
        String row;
        while ((row = reader.readLine()) != null) {
            String[] parts = row.split("\t");
            classes.put(Integer.parseInt(parts[0]), Integer.parseInt(parts[1]));
        }

        return classes;
    }

    /**
     * Returns a list of all unique words (the vocabulary)
     */
    private ArrayList<String> getWords(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        ArrayList<String> words = new ArrayList<>();
        String word;
        while ((word = reader.readLine()) != null) {
            words.add(word);
        }

        return words;
    }

}
