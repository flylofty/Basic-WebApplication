package simplewebapplication.springwebapplication.web.form;

import simplewebapplication.springwebapplication.domain.comment.Comment;

import java.time.format.DateTimeFormatter;

public class CommentLevelOneForm {

    private Long commentId;
    private String writer;
    private String content;
    private Long group;
    private String dateTime;

    public CommentLevelOneForm() {
    }

    public CommentLevelOneForm(Comment comment) {
        this.commentId = comment.getId();
        this.writer = comment.getUser().getId();
        this.content = comment.getContent();
        this.group = comment.getGroup_number();
        this.dateTime = comment.getDateTime().format(DateTimeFormatter.ofPattern("yy-MM-dd HH:mm"));
    }

    public Long getCommentId() {
        return commentId;
    }

    public void setCommentId(Long commentId) {
        this.commentId = commentId;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getGroup() {
        return group;
    }

    public void setGroup(Long group) {
        this.group = group;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
