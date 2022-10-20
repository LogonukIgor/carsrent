package by.logonuk.controller.converters.user.request;

import by.logonuk.controller.requests.UserCreateRequest;
import by.logonuk.domain.User;
import by.logonuk.domain.embed.TechnicalInfo;
import by.logonuk.domain.embed.user.Credentials;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;

@Component
public class UserCreateConverter extends UserBaseConverterRequest<UserCreateRequest, User> {

    @Override
    public User convert(UserCreateRequest source) {

        User user = new User();
        Timestamp timestamp = new Timestamp(new Date().getTime());
        TechnicalInfo techDateInf = new TechnicalInfo(timestamp, timestamp, false);
        user.setTechnicalInfo(techDateInf);

        user.setIsMailActivated(false);
        return doConvert(user, source);
    }
}
