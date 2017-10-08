package dto;

import java.util.ArrayList;

public class QuestionDTO {

    private Integer id;
    private Integer testId;
    private String title;
    private ArrayList<AnswerDTO> answers = new ArrayList<AnswerDTO>();

    public QuestionDTO(){}

    public QuestionDTO(Integer id, Integer testId, String title){
        this.setId(id);
        this.setTestId(testId);
        this.setTitle(title);
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

    public ArrayList<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(ArrayList<AnswerDTO> answers) {
        this.answers = answers;
    }

    public Integer getTestId() {
        return testId;
    }

    public void setTestId(Integer testId) {
        this.testId = testId;
    }
}
