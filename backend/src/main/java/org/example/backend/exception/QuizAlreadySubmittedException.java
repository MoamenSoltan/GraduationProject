package org.example.backend.exception;

public class QuizAlreadySubmittedException extends RuntimeException{
    private final int score;

    public QuizAlreadySubmittedException(int score) {
        super("Quiz already submitted");
        this.score = score;
    }

    public int getScore() {
        return score;
    }
}
