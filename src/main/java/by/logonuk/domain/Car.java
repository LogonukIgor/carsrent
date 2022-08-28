package by.logonuk.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.sql.Timestamp;

@Setter
@Getter
@EqualsAndHashCode
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    private Long carId;

    private String brand;

    private String model;

    private Boolean isInStock;

    private Boolean isDeleted;

    private Double engineVolume;

    private Integer yearOfIssue;

    private Integer numberOfSeats;

    private Boolean airConditioner;

    private Double costPerDay;

    private String transmission;

    private Timestamp creationDate;

    private Timestamp modificationDate;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
