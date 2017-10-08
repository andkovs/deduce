package dto;

/**
 * Created by Andrey on 22.05.2017.
 */
public class UserAnswerDTO {

    private String question;
    private String answer;
    private String isCorrect;

    public UserAnswerDTO(String question, String answer, String isCorrect) {
        this.question = question;
        this.answer = answer;
        this.isCorrect = isCorrect;
    }

    public UserAnswerDTO() {
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(String isCorrect) {
        this.isCorrect = isCorrect;
    }
}
