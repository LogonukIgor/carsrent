package by.logonuk.domain;

import by.logonuk.domain.attachments.TechnicalInfo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "l_brand_model_class")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Cacheable
/*
    The library is an entity
    for finding a suitable car for the user
    according to the appropriate parameters:
    1) CarManufacturer
    2) Classification
    3) Model
 */
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "creationDate", column = @Column(name = "creation_date")),
            @AttributeOverride(name = "modificationDate", column = @Column(name = "modification_date")),
            @AttributeOverride(name = "isDeleted", column = @Column(name = "is_deleted"))
    })
    private TechnicalInfo technicalInfo;

    @OneToOne
    @JoinColumn(name = "brand_id")
    @JsonManagedReference
    @JsonIgnoreProperties("library")
    private CarManufacture carManufacture;

    @OneToOne
    @JoinColumn(name = "classification_id")
    @JsonManagedReference
    @JsonIgnoreProperties("library")
    private Classification classification;

    @OneToOne
    @JoinColumn(name = "model_id")
    @JsonManagedReference
    @JsonIgnoreProperties("library")
    private Model model;


    @OneToOne
    @JoinColumn(name = "car_id")
    @JsonBackReference
    @JsonIgnoreProperties("library")
    private Car car;
}


