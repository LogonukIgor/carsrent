package by.logonuk.controller.converters.user.request;

import by.logonuk.controller.requests.UserUpdateRequest;
import by.logonuk.domain.User;
import by.logonuk.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserUpdateConverterRequest extends UserBaseConverterRequest<UserUpdateRequest, User> {

    @Override
    public User convert(UserUpdateRequest source) {
        User user = new User();
        user.setId(Long.parseLong(source.getUserId()));
        return doConvert(user, source);
    }
}
