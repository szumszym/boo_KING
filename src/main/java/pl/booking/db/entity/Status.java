package pl.booking.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@Table
public class Status implements Serializable {

    @Column(unique = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    @Column
    private Long version;

    @Column
    private String type;
    @Column
    private String description;
    @OneToMany(mappedBy = "status")
    private List<Reservation> reservations = new LinkedList<Reservation>();
    @Column
    private String color;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

}
