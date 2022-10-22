package by.logonuk.domain;

import by.logonuk.domain.embed.TechnicalInfo;
import by.logonuk.domain.enums.Transmissions;
import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @Column(name = "is_in_stock")
    private Boolean isInStock;

    @Column(name = "engine_volume")
    private Double engineVolume;

    @Column(name = "date_of_issue")
    private Timestamp dateOfIssue;

    @Column(name = "number_of_seats")
    private Integer numberOfSeats;

    @Column(name = "air_conditioner")
    private Boolean airConditioner;

    @Column(name = "cost_per_day")
    private Double costPerDay;

    @Column(name = "transmission")
    @Enumerated(EnumType.STRING)
    private Transmissions transmission;

    @JsonIgnore
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "creationDate", column = @Column(name = "creation_date")),
            @AttributeOverride(name = "modificationDate", column = @Column(name = "modification_date")),
            @AttributeOverride(name = "isDeleted", column = @Column(name = "is_deleted"))
    })
    private TechnicalInfo technicalInfo;

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonBackReference
    @JsonIgnoreProperties(value = {"car", "user"})
    private Deal deal;

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnoreProperties("car")
    private Library carInfo;
}
