package by.logonuk.domain.embed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.sql.Timestamp;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TechnicalInfo {

    private Timestamp creationDate;

    private Timestamp modificationDate;

    private Boolean isDeleted;
}
