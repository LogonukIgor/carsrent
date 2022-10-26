package by.logonuk.controller.converters.user.responce;

import by.logonuk.controller.responses.UserResponse;
import by.logonuk.domain.User;
import org.springframework.stereotype.Component;

@Component
public class UserDefaultConverterResponse extends UserBaseConverterResponse<User, UserResponse> {

    @Override
    public UserResponse convert(User source) {
        UserResponse userResponse = new UserResponse();
        return doConvert(userResponse, source);
    }
}
