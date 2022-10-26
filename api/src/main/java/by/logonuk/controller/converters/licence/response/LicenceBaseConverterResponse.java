package by.logonuk.controller.converters.licence.response;

import by.logonuk.controller.requests.DrivingLicenceResponse;
import by.logonuk.controller.responses.DealResponse;
import by.logonuk.domain.Deal;
import by.logonuk.domain.DrivingLicence;
import org.springframework.core.convert.converter.Converter;

public abstract class LicenceBaseConverterResponse<S, T> implements Converter<S, T> {

    public DrivingLicenceResponse doConvert(DrivingLicenceResponse drivingLicenceResponse, DrivingLicence source) {
        return drivingLicenceResponse;
    }
}