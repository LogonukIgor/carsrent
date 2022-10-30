package by.logonuk.controller.responses;

import by.logonuk.controller.responses.car.CarResponse;
import by.logonuk.controller.responses.deal.DealResponse;
import by.logonuk.controller.responses.licence.DrivingLicenceResponse;
import by.logonuk.controller.responses.user.UserResponse;
import by.logonuk.domain.Car;
import by.logonuk.domain.Deal;
import by.logonuk.domain.DrivingLicence;
import by.logonuk.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ResponseMapper {

    private final ConversionService converter;

    public UserResponse mapUserResponse(User user){
        DrivingLicence drivingLicence = user.getDrivingLicence();

        UserResponse userResponse = converter.convert(user, UserResponse.class);
        userResponse.setDrivingLicence(converter.convert(drivingLicence, DrivingLicenceResponse.class));
        userResponse.setDeal(mapDealResponse(user.getDeal()));
        return userResponse;
    }

    public DealResponse mapDealResponse(Deal deal){
        DealResponse dealResponse = converter.convert(deal, DealResponse.class);
        if(!(deal == null)){
            Car car = deal.getCar();
            dealResponse.setCar(converter.convert(car, CarResponse.class));
        }
        return dealResponse;
    }
}
