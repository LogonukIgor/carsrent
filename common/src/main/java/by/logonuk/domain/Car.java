package by.logonuk.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "cars")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String brand;

    @Column
    private String model;

    @Column(name = "is_in_stock")
    private Boolean isInStock;

    @JsonIgnore
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "engine_volume")
    private Double engineVolume;

    @Column(name = "year_of_issue")
    private Integer yearOfIssue;

    @Column(name = "number_of_seats")
    private Integer numberOfSeats;

    @Column(name = "air_conditioner")
    private Boolean airConditioner;

    @Column(name = "cost_per_day")
    private Double costPerDay;

    @JsonIgnore
    @Column(name = "creation_date")
    private Timestamp creationDate;

    @JsonIgnore
    @Column(name = "modification_date")
    private Timestamp modificationDate;

    @Column(name = "transmission_id")
    private Integer transmissionId;

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnoreProperties(value = {"car", "user"})
    private Deal deal;

//    @OneToOne
//    @JoinTable(name = "deal",
//            joinColumns = @JoinColumn(name = "car_id"),
//            inverseJoinColumns = @JoinColumn(name = "user_id")
//    )
//    @JsonIgnoreProperties("car")
//    private User user;

}
