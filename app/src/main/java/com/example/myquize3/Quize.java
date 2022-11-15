package com.example.myquize3;

import java.util.ArrayList;
import java.util.List;

public class Quize {
    private String question;
    private ArrayList<String> selections;
    private int correctNumber;

    public Quize(String question, String[] selections, int correctNumber) {
        this.question = question;
        this.selections = new ArrayList<>();
        for (String s : selections) {
            this.selections.add(s);
        }
        this.correctNumber = correctNumber;
    }
    public String getQuestion() { return question; }
    public ArrayList<String> getSelections() { return selections; }
    public int getCorrectNumber() { return correctNumber; }
}
