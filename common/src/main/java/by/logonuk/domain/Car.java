package by.logonuk.domain;

import by.logonuk.domain.embed.TechnicalDatesAndInfo;
import by.logonuk.domain.enums.Transmissions;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;

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

    @Column(name = "year_of_issue")
    private Integer yearOfIssue;

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
    private TechnicalDatesAndInfo technicalDatesAndInfo;

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnoreProperties(value = {"car", "user"})
    private Deal deal;

    @OneToOne(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnoreProperties("car")
    private Library carInfo;

}
