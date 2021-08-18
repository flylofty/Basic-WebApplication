package simplewebapplication.springwebapplication.domain.board;

import simplewebapplication.springwebapplication.domain.comment.Comment;
import simplewebapplication.springwebapplication.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Board {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(length = 60, nullable = false)
    private String title;

    @Lob @Column(nullable = false)
    private String content;

    /** 외래키가 있는 곳에 참조를 걸고 "N : 1" 연관관계 매핑을 함. 연관관계의 주인
     *  게시글이 만들어질 때 작성자
     * */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    //@Column(name = "writer", length = 30) // @Column(s) not allowed on a @ManyToOne property !!!!!!
    private User user;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BoardStatusType status;

    private Long views;

    private Long favorite;

    /**
     * 해당 게시글의 댓글들
     */
    @OneToMany(mappedBy = "board")
    private List<Comment> comments = new ArrayList<>();

    // 기본 생성자 필수!!
    protected Board() {};

    public Board(String title, String content, User user, LocalDateTime dateTime) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.dateTime = dateTime;
        this.status = BoardStatusType.CREATED;
        this.views = 0L;
        this.favorite = 0L;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public User getUser() { return user; }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public BoardStatusType getStatus() {
        return status;
    }

    public Long getViews() {
        return views;
    }

    public Long getFavorite() {
        return favorite;
    }

    public void changeContent(String content) {
        this.content = content;
    }

    public void changeViews(long addValue) {
        this.views += addValue;
    }

    public void deleteBoard() {
        this.status = BoardStatusType.DELETED;
    }
}
