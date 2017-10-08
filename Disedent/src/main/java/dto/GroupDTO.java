package dto;

import java.util.ArrayList;

public class GroupDTO {

    private Integer id;
    private String title;
    private Integer count;
    private String creationDate;
    ArrayList<OrderForListDTO> orders = new ArrayList<OrderForListDTO>();

    public GroupDTO() {
    }

    public GroupDTO(Integer id, String title, Integer count, String creationDate) {
        this.id = id;
        this.title = title;
        this.count = count;
        this.creationDate = creationDate;
    }

    public Integer getId() {
        return id;
    }

    public ArrayList<OrderForListDTO> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<OrderForListDTO> orders) {
        this.orders = orders;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }
}
