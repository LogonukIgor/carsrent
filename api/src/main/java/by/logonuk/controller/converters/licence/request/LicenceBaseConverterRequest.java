package by.logonuk.controller.converters.licence.request;

import by.logonuk.controller.requests.licence.DrivingLicenceCreateRequest;
import by.logonuk.domain.DrivingLicence;
import org.springframework.core.convert.converter.Converter;

public abstract class LicenceBaseConverterRequest<S, T> implements Converter<S, T> {

    public DrivingLicence doConvert(DrivingLicence drivingLicence, DrivingLicenceCreateRequest source) {

        drivingLicence.setDateOfIssue(source.getDateOfIssue());
        drivingLicence.setValidUntil(source.getValidUntil());
        drivingLicence.setCategoryB(source.getCategoryB());
        drivingLicence.setSerialNumber(source.getSerialNumber());

        return drivingLicence;
    }
}
