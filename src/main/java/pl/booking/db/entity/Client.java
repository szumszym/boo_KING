package pl.booking.db.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@Table
public class Client implements Serializable {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    @Column
    private Long version;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false, unique = true)
    private Long pesel;
    @Column
    private Long nip;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @Column(nullable = false)
    private Date registerDate;
    @Column
    private Date updateDate;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "client")
    @JsonManagedReference("client")
    private List<Reservation> reservations;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "hotel_client",
            joinColumns = {@JoinColumn(name = "client_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "hotel_id", nullable = false, updatable = false)})
    private List<Hotel> hotels = new LinkedList<Hotel>();

}
