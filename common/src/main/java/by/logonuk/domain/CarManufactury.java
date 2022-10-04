package by.logonuk.domain;

import by.logonuk.domain.embed.TechnicalDatesAndInfo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "cars_manufactury")
public class CarManufactury {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String brand;

    @Column(name = "country_of_origin")
    private String countryOfOrigin;

    @JsonIgnore
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "creationDate", column = @Column(name = "creation_date")),
            @AttributeOverride(name = "modificationDate", column = @Column(name = "modification_date")),
            @AttributeOverride(name = "isDeleted", column = @Column(name = "is_deleted"))
    })
    private TechnicalDatesAndInfo technicalDatesAndInfo;

    @OneToOne(mappedBy = "carManufactury", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonBackReference
    @JsonIgnoreProperties("carManufactury")
    private Library library;
}
