package pl.booking.db.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@Table
public class Addition implements Serializable {

    @Column(unique = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    @Column
    private Long version;

    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private Boolean published;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "additions")
    private List<Room> rooms = new LinkedList<Room>();
}

