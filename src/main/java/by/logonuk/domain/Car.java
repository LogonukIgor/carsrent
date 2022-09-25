package by.logonuk.domain;

import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "modification_date")
    private Timestamp modificationDate;

    @Column(name = "transmission_id")
    private String transmissionId;

}
