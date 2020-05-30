package main.java.domain;

/**
 * Created by oliviachisman on 5/29/20
 */
public class WordOccurance {

    private int document;
    private double numOcurrances;

    public WordOccurance(int document, double numOcurrances) {
        this.document = document;
        this.numOcurrances = numOcurrances;
    }

    public int getDocument() {
        return document;
    }

    public void setDocument(int document) {
        this.document = document;
    }

    public double getNumOcurrances() {
        return numOcurrances;
    }

    public void setNumOcurrances(double numOcurrances) {
        this.numOcurrances = numOcurrances;
    }
}
