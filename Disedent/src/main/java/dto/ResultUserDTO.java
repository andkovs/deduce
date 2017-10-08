package dto;

import java.util.ArrayList;

public class ResultUserDTO {

    private OrderUserDTO order;
    private ArrayList<ResultDTO> results;

    public ResultUserDTO(OrderUserDTO order, ArrayList<ResultDTO> results) {
        this.order = order;
        this.results = results;
    }

    public ResultUserDTO(OrderUserDTO order) {
        this.order = order;
    }

    public ResultUserDTO() {
    }

    public OrderUserDTO getOrder() {
        return order;
    }

    public void setOrder(OrderUserDTO order) {
        this.order = order;
    }

    public ArrayList<ResultDTO> getResults() {
        return results;
    }

    public void setResults(ArrayList<ResultDTO> results) {
        this.results = results;
    }
}
