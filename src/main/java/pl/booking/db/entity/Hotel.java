package pl.booking.db.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@Table
public class Hotel implements Serializable {

    @Column(unique = true)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY/*GenerationType.AUTO*/)
    private Long id;

    @Version
    @Column
    private Long version;

    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "hotel_client",
            joinColumns = @JoinColumn(name = "hotel_id")
    )
    @Column(name = "client_id")
    private List<Long> clientIds = new LinkedList<Long>();
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "hotels")
    private List<User> users = new LinkedList<User>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Room> rooms = new LinkedList<Room>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Addition> additions = new LinkedList<Addition>();
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "hotel", cascade = CascadeType.ALL)
    private List<Status> statuses = new LinkedList<Status>();

}
