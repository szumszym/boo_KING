package pl.booking.db.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@Table
public class Room implements Serializable {

    @Column(unique = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    @Column
    private Long version;

    @Column
    private Integer roomNo;
    @Column(unique = true)
    private String name;
    @Column
    private String bed;
    @Column
    private Integer capacity;
    @Column
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "room_addition", joinColumns = {
            @JoinColumn(name = "room_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "addition_id",
                    nullable = false, updatable = false)})
    private List<Addition> additions = new LinkedList<Addition>();
    @ManyToMany(mappedBy = "rooms")
    private List<Reservation> reservations = new LinkedList<Reservation>();
    @Column
    private Double price;
    private Double priceAdditions;
    @Column
    private Boolean published;

}

