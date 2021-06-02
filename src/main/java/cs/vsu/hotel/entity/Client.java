package cs.vsu.hotel.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "client")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "passport_id", nullable = false)
    private Long passportId;

    @Column(name = "age")
    private Integer age;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY)
    private List<Booking> bookings;

    public Client(String firstName, String lastName, Long passportId, Integer age, String gender, String phoneNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.passportId = passportId;
        this.age = age;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Client() {

    }
}
