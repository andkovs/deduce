package dto;

public class RoleDTO {

    private Integer id;
    private String title;

    public RoleDTO(){}

    public RoleDTO(Integer id, String title){
        this.setId(id);
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
}
