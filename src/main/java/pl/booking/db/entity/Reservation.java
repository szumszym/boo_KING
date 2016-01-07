package pl.booking.db.entity;


import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@Table
public class Reservation implements Serializable {

    @Column(unique = true)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    @Column
    private Long version;

    @Column
    private String name;
    @Column
    private Date dateFrom;
    @Column
    private Date dateTo;
    @Column
    private Integer personCount;
    @Column
    @CreatedDate
    private Date entryDate;
    @Column
    @LastModifiedDate
    private Date updateDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "status_id", referencedColumnName = "id")
    private Status status;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "room_reservation",
            joinColumns = {@JoinColumn(name = "reservation_id", nullable = false)},
            inverseJoinColumns = {@JoinColumn(name = "room_id")})
    private List<Room> rooms = new LinkedList<Room>();
    @Column
    private Double price;
}

