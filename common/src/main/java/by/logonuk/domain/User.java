package by.logonuk.domain;

import by.logonuk.domain.embed.TechnicalDatesAndInfo;
import by.logonuk.domain.embed.user.Credentials;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
//@EqualsAndHashCode(exclude = "roles")
@EqualsAndHashCode(exclude = {
        "roles", "drivingLicence", "deal"
})
@ToString(exclude = {
        "roles", "drivingLicence", "deal"
})
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String userName;

    @Column
    private String surname;

    @JsonIgnore
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "creationDate", column = @Column(name = "creation_date")),
            @AttributeOverride(name = "modificationDate", column = @Column(name = "modification_date")),
            @AttributeOverride(name = "isDeleted", column = @Column(name = "is_deleted"))
    })
    private TechnicalDatesAndInfo technicalDatesAndInfo;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "login", column = @Column(name = "user_login")),
            @AttributeOverride(name = "mail", column = @Column(name = "user_mail")),
            @AttributeOverride(name = "password", column = @Column(name = "user_password"))
    })
    private Credentials credentials;

    @JsonIgnore
    @Column(name = "activation_code")
    private String activationCode;

    @JsonIgnore
    @Column(name = "is_mail_activated")
    private Boolean isMailActivated;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    @JsonIgnoreProperties("user")
    private Deal deal;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private DrivingLicence drivingLicence;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties("users")
    private Set<Role> roles;

}
