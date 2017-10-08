package dto;

public class TopicDTO {
    private Integer id;
    private Integer chapterId;
    private String title;
    private String description;
    private Boolean isFile;

    public TopicDTO(Integer id, Integer chapterId, String title, String text, Boolean isFile) {
        this.id = id;
        this.chapterId = chapterId;
        this.title = title;
        this.description = text;
        this.isFile = isFile;
    }

    public TopicDTO() {
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

    public Integer getChapterId() {
        return chapterId;
    }

    public void setChapterId(Integer chapterId) {
        this.chapterId = chapterId;
    }

    public Boolean getFile() {
        return isFile;
    }

    public void setFile(Boolean file) {
        isFile = file;
    }
}
