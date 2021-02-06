package simplewebapplication.springwebapplication.domain.comment;

import simplewebapplication.springwebapplication.domain.board.Board;
import simplewebapplication.springwebapplication.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Comment {

    @Id @GeneratedValue
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

    private Long group_depth;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(length = 30)
    private String cId;

    // 기본 생성자
    protected Comment(){};

    public Comment(Long id, Board board, User user, String content, Long group_number, Long group_depth, LocalDateTime dateTime, String cId) {
        this.id = id;
        this.board = board;
        this.user = user;
        this.content = content;
        this.group_number = group_number;
        this.group_depth = group_depth;
        this.dateTime = dateTime;
        this.cId = cId;
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

    public Long getGroup_depth() {
        return group_depth;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getCid() {
        return cId;
    }
}
