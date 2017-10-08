package dto;

public class ResultDTO {

    private Integer id;
    private Integer orderId;
    private String testTitle;
    private String startTime;
    private String finishTime;
    private Integer amountQuestions;
    private Integer amountCorrectAnswers;

    public ResultDTO(Integer id, String testTitle, String startTime, String finishTime, Integer amountQuestions, Integer amountCorrectAnswers) {
        this.id = id;
        this.testTitle = testTitle;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.amountQuestions = amountQuestions;
        this.amountCorrectAnswers = amountCorrectAnswers;
    }

    public ResultDTO(Integer id, Integer orderId, String testTitle, String startTime,
                     String finishTime, Integer amountQuestions, Integer amountCorrectAnswers) {
        this.id = id;
        this.orderId = orderId;
        this.testTitle = testTitle;
        this.startTime = startTime;
        this.finishTime = finishTime;
        this.amountQuestions = amountQuestions;
        this.amountCorrectAnswers = amountCorrectAnswers;
    }

    public ResultDTO() {
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

    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getAmountQuestions() {
        return amountQuestions;
    }

    public void setAmountQuestions(Integer amountQuestions) {
        this.amountQuestions = amountQuestions;
    }

    public Integer getAmountCorrectAnswers() {
        return amountCorrectAnswers;
    }

    public void setAmountCorrectAnswers(Integer amountCorrectAnswers) {
        this.amountCorrectAnswers = amountCorrectAnswers;
    }
}
