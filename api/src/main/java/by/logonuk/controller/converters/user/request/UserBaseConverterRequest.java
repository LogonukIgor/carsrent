package by.logonuk.controller.converters.user.request;

import by.logonuk.controller.requests.UserCreateRequest;
import by.logonuk.domain.User;
import by.logonuk.domain.embed.user.Credentials;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public abstract class UserBaseConverterRequest<S, T> implements Converter<S, T> {

    private final PasswordEncoder passwordEncoder;

    public User doConvert(User user, UserCreateRequest source) {
        user.setUserName(source.getUserName());
        user.setSurname(source.getSurname());

        Credentials credentials = new Credentials(
                source.getLogin(),
                source.getMail(),
                passwordEncoder.encode(source.getPassword())
        );
        user.setCredentials(credentials);
        return user;
    }
}
