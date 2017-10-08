package dto;

import java.util.ArrayList;
import java.util.List;

public class UserDTO {

    private Integer id;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String middleName;
    private String companyTitle;
    private String position;
    private String phone;
    private String email;
    private Boolean isEnabled;
    private List<String> roles = new ArrayList<String>();

    public UserDTO(){}

    public UserDTO(Integer id, String login, String password, String firstName,
                   String lastName){
        this.setId(id);
        this.setLogin(login);
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
    }

    public UserDTO(Integer id, String login, String password, String firstName,
                   String lastName, String phone, String email){
        this.setId(id);
        this.setLogin(login);
        this.setPassword(password);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setPhone(phone);
        this.setEmail(email);
    }

    public UserDTO(Integer id, String login, String password, String firstName,
                   String lastName, String middleName,
                   String companyTitle, String position, String phone,
                   String email, ArrayList<String> roles) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.companyTitle = companyTitle;
        this.position = position;
        this.phone = phone;
        this.email = email;
        this.roles = roles;
    }

    public UserDTO(Integer id, String login, String password,
                   String firstName, String lastName, String middleName,
                   String companyTitle, String position, String phone,
                   String email, Boolean isEnabled) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.companyTitle = companyTitle;
        this.position = position;
        this.phone = phone;
        this.email = email;
        this.isEnabled = isEnabled;
    }

    public Boolean getEnabled() {
        return isEnabled;
    }

    public void setEnabled(Boolean enabled) {
        isEnabled = enabled;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }
}
