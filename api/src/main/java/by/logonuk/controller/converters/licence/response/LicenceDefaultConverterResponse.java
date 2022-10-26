package by.logonuk.controller.converters.licence.response;

import by.logonuk.controller.responses.DrivingLicenceResponse;
import by.logonuk.domain.DrivingLicence;
import org.springframework.stereotype.Component;

@Component
public class LicenceDefaultConverterResponse extends LicenceBaseConverterResponse<DrivingLicence, DrivingLicenceResponse> {

    @Override
    public DrivingLicenceResponse convert(DrivingLicence source) {
        DrivingLicenceResponse drivingLicenceResponse = new DrivingLicenceResponse();

        drivingLicenceResponse.setId(source.getId());
        drivingLicenceResponse.setUserId(source.getUser().getId());
        drivingLicenceResponse.setDateOfIssue(source.getDateOfIssue());
        drivingLicenceResponse.setValidUntil(source.getValidUntil());
        drivingLicenceResponse.setSerialNumber(source.getSerialNumber());

        return doConvert(drivingLicenceResponse, source);
    }
}
