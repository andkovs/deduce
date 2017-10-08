package dto;

import java.util.ArrayList;

public class ChapterDTO {

    private Integer id;
    private Integer courseId;
    private String title;
    private String description;
    private ArrayList<TopicDTO> topics = new ArrayList<TopicDTO>();

    public ChapterDTO(){}

    public ChapterDTO(Integer id, Integer courseId, String title, String description) {
        this.id = id;
        this.courseId = courseId;
        this.title = title;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public ArrayList<TopicDTO> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<TopicDTO> topics) {
        this.topics = topics;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
