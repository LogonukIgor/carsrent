package by.logonuk.validation;

import by.logonuk.exception.DateValidationException;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class DateValidator implements CustomValidator {

    public Timestamp getReceivingTimestamp(){
        Timestamp validReceivingTimestamp = new Timestamp(new Date().getTime());
        return validReceivingTimestamp;
    }

    public Timestamp getReturnDate(Timestamp validReceivingTimestamp){
        Long ms = validReceivingTimestamp.getTime() + 86400000L;
        Timestamp validReturnTimestamp = new Timestamp(ms);
        return validReturnTimestamp;
    }

    public void validDealDate(Timestamp receivingDate, Timestamp returnDate){
        Timestamp validReceivingTimestamp = getReceivingTimestamp();
        Timestamp validReturnTimestamp = getReturnDate(validReceivingTimestamp);
        if(!receivingDate.after(validReceivingTimestamp)){
            throw new DateValidationException("Date of receiving must be in the future (at least 1 hour)");
        }
        if(!returnDate.after(validReturnTimestamp)) {
            throw new DateValidationException("Date of returning must be in the future (at least 1 day)");
        }
    }

    @Override
    public void validLicenceDate(Timestamp dateOfIssue, Timestamp validUntil) {
        Timestamp now = getReceivingTimestamp();
        if(dateOfIssue.after(now)){
            throw new DateValidationException("Date of issue must be in the past");
        }
        if(!validUntil.after(now)) {
            throw new DateValidationException("Date (valid until) must be in the future");
        }
    }
}
