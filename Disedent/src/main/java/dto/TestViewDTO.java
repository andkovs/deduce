package dto;

import java.util.ArrayList;

public class TestViewDTO {

    private Integer id;
    private Integer resultId;
    private Integer orderId;
    private String title;
    private Integer questionsAmount;
    private Integer timeLimit;
    private ArrayList<QuestionDTO> questions = new ArrayList<QuestionDTO>();

    public TestViewDTO() {
    }

    public TestViewDTO(Integer id, Integer resultId, String title, Integer questionsAmount, Integer timeLimit) {
        this.id = id;
        this.resultId = resultId;
        this.title = title;
        this.questionsAmount = questionsAmount;
        this.timeLimit = timeLimit;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getResultId() {
        return resultId;
    }

    public void setResultId(Integer resultId) {
        this.resultId = resultId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public ArrayList<QuestionDTO> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<QuestionDTO> questions) {
        this.questions = questions;
    }
}
