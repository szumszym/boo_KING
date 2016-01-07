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
public class User implements Serializable {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    @Column
    private Long version;

    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private Long pesel;
    @Column
    private Long nip;
    @Column
    private String email;
    @Column
    private String phoneNumber;
    @Column
    private String password;
    @Column(columnDefinition = "enum('ADMIN','OWNER','EMPLOYEE')")
    @Enumerated(EnumType.STRING)
    private Type type;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "hotel_user",
            joinColumns = {@JoinColumn(name = "user_id", nullable = false, updatable = false)},
            inverseJoinColumns = {@JoinColumn(name = "hotel_id", nullable = false, updatable = false)})
    private List<Hotel> hotels = new LinkedList<Hotel>();
    @OneToOne(cascade = CascadeType.ALL)
    private Address address;
    @Column
    @CreatedDate
    private Date registerDate;
    @Column
    @LastModifiedDate
    private Date updateDate;

    public enum Type {
        ADMIN, OWNER, EMPLOYEE;

        @Override
        public String toString() {
            return this.name();
        }
    }
}
