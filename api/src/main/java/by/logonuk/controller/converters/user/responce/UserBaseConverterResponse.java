package by.logonuk.controller.converters.user.responce;

import by.logonuk.controller.responses.DealResponse;
import by.logonuk.controller.responses.DrivingLicenceResponse;
import by.logonuk.controller.responses.UserResponse;
import by.logonuk.domain.Role;
import by.logonuk.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;

public abstract class UserBaseConverterResponse<S, T> implements Converter<S, T> {

    public UserResponse doConvert(UserResponse userResponse, User source){
        userResponse.setUserName(source.getUserName());
        userResponse.setSurname(source.getSurname());
        userResponse.setId(source.getId());
        userResponse.setCredentials(source.getCredentials());
        Set<Role> roles = source.getRoles();
        userResponse.setRoles(roles);
        return userResponse;
    }
}
