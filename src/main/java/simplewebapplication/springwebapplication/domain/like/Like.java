package simplewebapplication.springwebapplication.domain.like;

import simplewebapplication.springwebapplication.domain.board.Board;
import simplewebapplication.springwebapplication.domain.user.User;

import javax.persistence.*;

@Entity
@Table(name = "LIKES")
public class Like {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    public Like() {}

    public Like(User user, Board board) {
        this.user = user;
        this.board = board;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Board getBoard() {
        return board;
    }
}
