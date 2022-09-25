package by.logonuk.domain;

import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @Column(name = "modification_date")
    private Timestamp modificationDate;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
