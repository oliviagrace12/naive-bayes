package main.java;

import java.io.*;

/**
 * Created by oliviachisman on 5/29/20
 */
public class Application {

    public static void main(String[] args) throws IOException {
        File wordFile = new File("/Users/oliviachisman/dev/depaul/ai1/naive-bayes/src/main/resources/terms.txt");
        File trainingDocClasses = new File("/Users/oliviachisman/dev/depaul/ai1/naive-bayes/src/main/resources/trainClasses.txt");
        File trainingWordOccurrences = new File("/Users/oliviachisman/dev/depaul/ai1/naive-bayes/src/main/resources/trainMatrix.txt");
        TrainingDataProcessor trainingDataProcessor = new TrainingDataProcessor(wordFile, trainingDocClasses, trainingWordOccurrences);

        System.out.println("Done");
    }


}
