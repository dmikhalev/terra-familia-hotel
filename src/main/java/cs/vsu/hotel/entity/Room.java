package cs.vsu.hotel.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "cost", nullable = false)
    private Integer cost;

    @Column(name = "discount")
    private Integer discount;

    @Column(name = "adult_count", nullable = false)
    private Integer adultCount;

    @Column(name = "child_count", nullable = false)
    private Integer childCount;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @Column(name = "double_beds_count", nullable = false)
    private Integer doubleBedsCount;

    @Column(name = "single_beds_count", nullable = false)
    private Integer singleBedsCount;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "room_amenities",
            joinColumns = @JoinColumn(name = "room_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "amenities_id", referencedColumnName = "id")
    )
    private List<Amenities> amenities;

    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY)
    private List<Feedback> feedbacks;

    public Room() {
    }

    public Room(Long id, Integer number, Integer cost, Integer discount, Integer adultCount, Integer childCount, String description, Integer doubleBedsCount, Integer singleBedsCount) {
        this.id = id;
        this.number = number;
        this.cost = cost;
        this.discount = discount;
        this.adultCount = adultCount;
        this.childCount = childCount;
        this.description = description;
        this.doubleBedsCount = doubleBedsCount;
        this.singleBedsCount = singleBedsCount;
    }
}
