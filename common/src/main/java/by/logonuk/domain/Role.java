package by.logonuk.domain;

import by.logonuk.domain.embed.TechnicalDatesAndInfo;
import by.logonuk.domain.enums.SystemRoles;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@EqualsAndHashCode(exclude = "users")
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "role_name")
    @Enumerated(EnumType.STRING)
    private SystemRoles roleName;

    @JsonIgnore
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "creationDate", column = @Column(name = "creation_date")),
            @AttributeOverride(name = "modificationDate", column = @Column(name = "modification_date")),
            @AttributeOverride(name = "isDeleted", column = @Column(name = "is_deleted"))
    })
    private TechnicalDatesAndInfo technicalDatesAndInfo;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "l_role_user",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    @JsonIgnoreProperties("roles")
    private Set<User> users;
}
