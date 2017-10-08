package dto;

import java.util.ArrayList;

public class CourseDTO {

    private Integer id;
    private String title;
    private String description;
    private ArrayList<ChapterDTO> chapters = new ArrayList<ChapterDTO>();
    private ArrayList<TestShortDTO> tests = new ArrayList<TestShortDTO>();

    public CourseDTO(){}

    public CourseDTO(Integer id, String title, String description) {
        this.id = id;
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

    public ArrayList<ChapterDTO> getChapters() {
        return chapters;
    }

    public void setChapters(ArrayList<ChapterDTO> chapters) {
        this.chapters = chapters;
    }

    public ArrayList<TestShortDTO> getTests() {
        return tests;
    }

    public void setTests(ArrayList<TestShortDTO> tests) {
        this.tests = tests;
    }
}
