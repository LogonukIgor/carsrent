package by.logonuk.controller.converters.licence.request;

import by.logonuk.controller.requests.licence.DrivingLicenceUpdateRequest;
import by.logonuk.domain.DrivingLicence;
import org.springframework.stereotype.Component;

@Component
public class LicenceUpdateConverterRequest extends LicenceBaseConverterRequest<DrivingLicenceUpdateRequest, DrivingLicence> {

    @Override
    public DrivingLicence convert(DrivingLicenceUpdateRequest source) {

        DrivingLicence drivingLicence = new DrivingLicence();
        drivingLicence.setId(Long.parseLong(source.getUserId()));

        return doConvert(drivingLicence, source);
    }
}
