package dto;

public class ResultForListDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String testTitle;
    private String finishTime;
    private Integer amountQuestions;
    private Integer amountCorrectAnswers;

    public ResultForListDTO() {
    }

    public ResultForListDTO(Integer id, String firstName,
                            String lastName, String middleName,
                            String testTitle, String finishTime,
                            Integer amountQuestions, Integer amountCorrectAnswers) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.testTitle = testTitle;
        this.finishTime = finishTime;
        this.amountQuestions = amountQuestions;
        this.amountCorrectAnswers = amountCorrectAnswers;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
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
