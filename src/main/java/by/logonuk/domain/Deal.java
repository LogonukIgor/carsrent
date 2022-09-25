package by.logonuk.domain;

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

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "car_id")
    private Integer carId;

    @Column(name = "modification_date")
    private Timestamp modificationDate;

    @Column(name = "receiving_date")
    private Timestamp receivingDate;

    @Column(name = "return_date")
    private Timestamp returnDate;

    @Column(name = "is_deleted")
    private Boolean isCompleted;

    @Column
    private Double price;

    @Column(name = "creation_date")
    private Timestamp creationDate;

}
