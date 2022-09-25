package by.logonuk.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "driving_licence")
public class DrivingLicence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_of_issue")
    private Date dateOfIssue;

    @Column(name = "valid_until")
    private Date validUntil;

    @Column(name = "category_b")
    private Boolean categoryB;

    @Column(name = "serial_number")
    private String serialNumber;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "modification_date")
    private Timestamp modificationDate;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @Column(name = "user_id")
    private Long userId;
}
