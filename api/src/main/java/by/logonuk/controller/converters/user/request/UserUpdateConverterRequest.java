package by.logonuk.controller.converters.user.request;

import by.logonuk.controller.requests.UserUpdateRequest;
import by.logonuk.domain.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserUpdateConverterRequest extends UserBaseConverterRequest<UserUpdateRequest, User> {

    public UserUpdateConverterRequest(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }

    @Override
    public User convert(UserUpdateRequest source) {
        User user = new User();
        user.setId(Long.parseLong(source.getUserId()));
        return doConvert(user, source);
    }
}
