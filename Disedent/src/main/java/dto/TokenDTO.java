package dto;

import java.util.Date;

/**
 * Created by Andrey on 04.06.2017.
 */
public class TokenDTO {
    private String token;
    private String date;

    public TokenDTO(String token, String date) {
        this.token = token;
        this.date = date;
    }

    public TokenDTO() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
