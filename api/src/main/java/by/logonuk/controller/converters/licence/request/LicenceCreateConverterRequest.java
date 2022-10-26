package by.logonuk.controller.converters.licence.request;

import by.logonuk.controller.requests.DrivingLicenceCreateRequest;
import by.logonuk.domain.DrivingLicence;
import by.logonuk.domain.attachments.TechnicalInfo;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class LicenceCreateConverterRequest extends LicenceBaseConverterRequest<DrivingLicenceCreateRequest, DrivingLicence> {
    @Override
    public DrivingLicence convert(DrivingLicenceCreateRequest source) {

        DrivingLicence drivingLicence = new DrivingLicence();

        Timestamp timestamp = new Timestamp(new Date().getTime());
        TechnicalInfo technicalInfo = new TechnicalInfo(timestamp, timestamp, false);

        drivingLicence.setTechnicalInfo(technicalInfo);

        return doConvert(drivingLicence, source);
    }
}
