package simplewebapplication.springwebapplication.domain.board;

import simplewebapplication.springwebapplication.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @Column(length = 60)
    private String title;

    @Lob
    private String content;

    /** 외래키가 있는 곳에 참조를 걸고 "N : 1" 연관관계 매핑을 함. 연관관계의 주인
     *  게시글이 만들어질 때 작성자
     * */
    @ManyToOne
    @JoinColumn(name = "user_id")
    //@Column(name = "writer", length = 30) // @Column(s) not allowed on a @ManyToOne property !!!!!!
    private User user;

    private LocalDateTime dateTime;

    private Long views;

    private Long favorite;

    public Board(String title, String content, User user, LocalDateTime dateTime) {
        this.title = title;
        this.content = content;
        this.user = user;
        this.dateTime = dateTime;
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

    public Long getViews() {
        return views;
    }

    public Long getFavorite() {
        return favorite;
    }
}
