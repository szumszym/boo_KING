package pl.booking.db.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: thx-
 * Date: 12.12.13
 * Time: 19:15
 * To change this template use File | Settings | File Templates.
 */

@Data
@Entity
@Table
public class Address implements Serializable {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Version
    @Column
    private Long version;

    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String street;
    @Column(nullable = false)
    private String buildingNo;
    @Column
    private Integer apartmentNo;
    @Column(nullable = false)
    private String postcode;
    @Column(nullable = false)
    private String country;

}
