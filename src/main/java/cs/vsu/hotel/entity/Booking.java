package cs.vsu.hotel.entity;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "booking")
public class Booking {

    public Booking(Room room, Client client, Payment payment, Date startDate, Date endDate, Integer price) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.payment = payment;
        this.client = client;
        this.room = room;
        this.price = price;
    }

    public Booking() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_date", nullable = false)
    private Date startDate;

    @Column(name = "end_date", nullable = false)
    private Date endDate;

    @Column(name = "price", nullable = false)
    private Integer price;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payment_id")
    private Payment payment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
}
