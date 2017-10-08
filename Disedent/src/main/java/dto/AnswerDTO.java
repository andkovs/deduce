package dto;


public class AnswerDTO {

    private Integer id;
    private Integer questionId;
    private String title;
    private Boolean isCorrect;

    public AnswerDTO(){}

    public AnswerDTO(Integer id, Integer questionId, String title){
        this.setId(id);
        this.setQuestionId(questionId);
        this.setTitle(title);
    }

    public AnswerDTO(Integer id, Integer questionId, String title, Boolean isCorrect){
        this.setId(id);
        this.setQuestionId(questionId);
        this.setTitle(title);
        this.setIsCorrect(isCorrect);
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

    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }
}
