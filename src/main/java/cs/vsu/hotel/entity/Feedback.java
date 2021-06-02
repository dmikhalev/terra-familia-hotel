package cs.vsu.hotel.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "feedback")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "is_like", nullable = false)
    private Boolean isLike;

    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "username", nullable = false)
    private String username;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    public Feedback(Boolean isLike, String comment, String username, Room room) {
        this.isLike = isLike;
        this.comment = comment;
        this.username = username;
        this.room = room;
    }

    public Feedback() {
    }
}
