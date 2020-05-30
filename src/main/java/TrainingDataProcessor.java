package main.java;

import main.java.domain.WordOccurance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by oliviachisman on 5/30/20
 */
public class TrainingDataProcessor {

    private ArrayList<String> words;
    private HashMap<Integer, Integer> classes;
    private HashMap<Integer, List<WordOccurance>> testWordOccurrences;

    public TrainingDataProcessor(File wordFile, File trainingDocClasses, File trainingWordOccurrences) throws IOException {
        this.words = getWords(wordFile);
        this.classes = getClassesOfDocs(trainingDocClasses);
        this.testWordOccurrences = getWordOccurrences(trainingWordOccurrences);
    }

    private HashMap<Integer, List<WordOccurance>> getWordOccurrences(File file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
        HashMap<Integer, List<WordOccurance>> testWordOccurrences = new HashMap<>();
        String row;
        int wordNum = 0;
        while ((row = reader.readLine()) != null) {
            String[] parts = row.split("\t");
            List<WordOccurance> occurrences = new ArrayList<>();
            for (int i = 0; i < parts.length; i++) {
                occurrences.add(new WordOccurance(i, Double.parseDouble(parts[i])));
            }
            testWordOccurrences.put(wordNum, occurrences);
            wordNum++;
        }
        return testWordOccurrences;
    }

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
