package by.logonuk.domain;

import by.logonuk.domain.embed.TechnicalDatesAndInfo;
import by.logonuk.domain.enums.ClassificationLetter;
import by.logonuk.domain.enums.Transmissions;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "classification")
public class Classification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "classification_letter")
    @Enumerated(EnumType.STRING)
    private ClassificationLetter classificationLetter;

    @Column
    private String description;

    @JsonIgnore
    @Embedded
    @AttributeOverrides( {
            @AttributeOverride(name = "creationDate", column = @Column(name = "creation_date")),
            @AttributeOverride(name = "modificationDate", column = @Column(name = "modification_date")),
            @AttributeOverride(name = "isDeleted", column = @Column(name = "is_deleted"))
    })
    private TechnicalDatesAndInfo technicalDatesAndInfo;

    @OneToOne(mappedBy = "classification", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonBackReference
    @JsonIgnoreProperties("classification")
    private Library library;
}
