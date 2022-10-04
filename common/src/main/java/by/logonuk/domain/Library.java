package by.logonuk.domain;

import by.logonuk.domain.embed.TechnicalDatesAndInfo;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
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
    private TechnicalDatesAndInfo technicalDatesAndInfo;

    @OneToOne
    @JoinColumn(name = "brand_id")
    @JsonManagedReference
    @JsonIgnoreProperties("library")
    private CarManufactury carManufactury;

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
