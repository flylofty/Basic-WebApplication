package simplewebapplication.springwebapplication.domain.comment;

import simplewebapplication.springwebapplication.domain.board.Board;
import simplewebapplication.springwebapplication.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Lob @Column(nullable = false)
    private String content;

    private Long group_number;

    private Integer group_depth;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    // 기본 생성자
    protected Comment(){};

    public Comment(Board board, User user, String content, Long group_number, Integer group_depth, LocalDateTime dateTime) {
        this.board = board;
        this.user = user;
        this.content = content;
        this.group_number = group_number;
        this.group_depth = group_depth;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public Board getBoard() {
        return board;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public Long getGroup_number() {
        return group_number;
    }

    public Integer getGroup_depth() {
        return group_depth;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
