package by.logonuk.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "deal")
public class Deal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Column(name = "modification_date")
    private Timestamp modificationDate;

    @Column(name = "receiving_date")
    private Timestamp receivingDate;

    @Column(name = "return_date")
    private Timestamp returnDate;

    @JsonIgnore
    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column
    private Double price;

    @JsonIgnore
    @Column(name = "creation_date")
    private Timestamp creationDate;

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
