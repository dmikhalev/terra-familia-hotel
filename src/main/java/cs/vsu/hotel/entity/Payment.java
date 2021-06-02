package cs.vsu.hotel.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sum", nullable = false)
    private Integer sum;

    @CreatedDate
    @Column(name = "payment_date", nullable = false)
    private Date paymentDate = new Date();

    @OneToOne(mappedBy = "payment")
    private Booking booking;

    public Payment() {

    }

    public Payment(Integer sum) {
        this.sum = sum;
    }
}
