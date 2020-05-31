package main.java;

import java.io.*;
import java.util.HashMap;

/**
 * Created by oliviachisman on 5/29/20
 */
public class Application {

    public static void main(String[] args) throws IOException {
        File wordFile = new File("/Users/oliviachisman/dev/depaul/ai1/naive-bayes/src/main/resources/terms.txt");
        File trainingDocClassesFile = new File("/Users/oliviachisman/dev/depaul/ai1/naive-bayes/src/main/resources/trainClasses.txt");
        File trainingWordOccurrencesFile = new File("/Users/oliviachisman/dev/depaul/ai1/naive-bayes/src/main/resources/trainMatrix.txt");
        DataProcessor trainingDataProcessor = new DataProcessor(wordFile, trainingDocClassesFile, trainingWordOccurrencesFile);

        Trainer trainer = new Trainer(trainingDataProcessor.getWordOccurrences(), trainingDataProcessor.getDocClasses(), trainingDataProcessor.getClasses());
        HashMap<Integer, HashMap<String, Double>> probabilities = trainer.train();

        File testDocClasses = new File("/Users/oliviachisman/dev/depaul/ai1/naive-bayes/src/main/resources/testClasses.txt");
        File testWordOccurrences = new File("/Users/oliviachisman/dev/depaul/ai1/naive-bayes/src/main/resources/testMatrix.txt");
        DataProcessor testDataProcessor = new DataProcessor(wordFile, testDocClasses, testWordOccurrences);

        Tester tester = new Tester(testDataProcessor.getDocClasses(), testDataProcessor.getDocWords(), testDataProcessor.getClasses(), probabilities);
        double numDocsCorrectlyClassified = tester.test();
        double totalDocsClassified = testDataProcessor.getDocClasses().size();
        double classificationAccuracy = numDocsCorrectlyClassified / totalDocsClassified;

        System.out.println("Classification Accuracy: " + classificationAccuracy);
    }


}
