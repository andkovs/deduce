package dto;


public class ResultForExcelListDTO {

    private Integer id;
    private String lastName;
    private String firstName;
    private String middleName;
    private String companyTitle;
    private String position;
    private String courseTitle;
    private String testTitle;
    private String beginDate;
    private String endDate;
    private String finishTime;
    private Integer amountQuestions;
    private Integer amountCorrectAnswers;

    public ResultForExcelListDTO(Integer id, String lastName, String firstName,
                                 String middleName, String companyTitle, String position, String courseTitle,
                                 String testTitle, String beginDate, String endDate, String finishTime,
                                 Integer amountQuestions, Integer amountCorrectAnswers) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.companyTitle = companyTitle;
        this.position = position;
        this.courseTitle = courseTitle;
        this.testTitle = testTitle;
        this.beginDate = beginDate;
        this.endDate = endDate;
        this.finishTime = finishTime;
        this.amountQuestions = amountQuestions;
        this.amountCorrectAnswers = amountCorrectAnswers;
    }

    public ResultForExcelListDTO() {
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getCompanyTitle() {
        return companyTitle;
    }

    public void setCompanyTitle(String companyTitle) {
        this.companyTitle = companyTitle;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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
