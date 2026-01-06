package com.example.quizzbuzz;

import java.util.List;

public class Question {
    private String type;
    private String difficulty;
    private String category;
    private String question;
    private String correct_answer;
    private List<String> incorrect_answers;

    public String getQuestion() {
        return question;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public List<String> getIncorrect_answers() {
        return incorrect_answers;
    }

    public List<String> getAllAnswers() {
        List<String> allAnswers = incorrect_answers;
        allAnswers.add(correct_answer);
        return  allAnswers;
    }

}
