package by.logonuk.validation;

import java.sql.Timestamp;

public interface CustomValidator {

    void validDealDate(Timestamp receivingDate, Timestamp returnDate);

    void validLicenceDate(Timestamp dateOfIssue, Timestamp validUntil);
}
