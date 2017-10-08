package dto;

import java.util.ArrayList;

public class TestDTO {

    private Integer id;
    private String title;
    private String description;
    private Integer questionsAmount;
    private Integer timeLimit;
    private Integer attempts;
    private ArrayList<QuestionDTO> questions = new ArrayList<QuestionDTO>();

    public TestDTO(){}

    public TestDTO(Integer id, String title, String description, Integer questionsAmount,
                   Integer timeLimit, Integer attempts) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.questionsAmount = questionsAmount;
        this.timeLimit = timeLimit;
        this.attempts = attempts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public void setAttempts(Integer attempts) {
        this.attempts = attempts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<QuestionDTO> questions) {
        this.questions = questions;
    }

    public Integer getQuestionsAmount() {
        return questionsAmount;
    }

    public void setQuestionsAmount(Integer questionsAmount) {
        this.questionsAmount = questionsAmount;
    }

    public Integer getTimeLimit() {
        return timeLimit;
    }

    public void setTimeLimit(Integer timeLimit) {
        this.timeLimit = timeLimit;
    }
}
