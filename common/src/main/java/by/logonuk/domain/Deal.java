package by.logonuk.domain;

import by.logonuk.domain.embed.TechnicalDatesAndInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "deal")
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receiving_date")
    private Timestamp receivingDate;

    @Column(name = "return_date")
    private Timestamp returnDate;

    @Column
    private Double price;

    @JsonIgnore
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "creationDate", column = @Column(name = "creation_date")),
            @AttributeOverride(name = "modificationDate", column = @Column(name = "modification_date")),
            @AttributeOverride(name = "isDeleted", column = @Column(name = "is_deleted"))
    })
    private TechnicalDatesAndInfo technicalDatesAndInfo;

    @OneToOne
//    @JsonBackReference
    @JsonIgnoreProperties(value = {"deal","car"})
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
//    @JsonBackReference
    @JoinColumn(name = "car_id")
    @JsonIgnoreProperties(value = {"deal", "user"})
    private Car car;

}