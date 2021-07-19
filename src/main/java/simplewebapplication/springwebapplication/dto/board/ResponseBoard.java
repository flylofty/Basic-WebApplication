package simplewebapplication.springwebapplication.dto.board;

public class ResponseBoard {

    private String id;
    private String title;
    private String user;
    private String dateTime;
    private String views;
    private String favorite;
    private String content;

    public ResponseBoard() {
    }

    public ResponseBoard(String id, String title, String user, String dateTime, String views, String favorite, String content) {
        this.id = id;
        this.title = title;
        this.user = user;
        this.dateTime = dateTime;
        this.views = views;
        this.favorite = favorite;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
