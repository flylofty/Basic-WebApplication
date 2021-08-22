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

    private Long group;

    private Long level;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    // 기본 생성자
    protected Comment(){};

    public Comment(Long id, Board board, User user, String content, Long group, Long level, LocalDateTime dateTime, String cId) {
        this.id = id;
        this.board = board;
        this.user = user;
        this.content = content;
        this.group = group;
        this.level = level;
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

    public Long getGroup() {
        return group;
    }

    public Long getLevel() {
        return level;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }
}
