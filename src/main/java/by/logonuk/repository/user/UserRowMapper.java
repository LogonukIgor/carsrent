package by.logonuk.repository.user;

import by.logonuk.domain.User;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.logonuk.repository.user.UserTableColumns.CHANGED;
import static by.logonuk.repository.user.UserTableColumns.CREATED;
import static by.logonuk.repository.user.UserTableColumns.ID;
import static by.logonuk.repository.user.UserTableColumns.IS_DELETED;
import static by.logonuk.repository.user.UserTableColumns.LICENCE_ID;
import static by.logonuk.repository.user.UserTableColumns.NAME;
import static by.logonuk.repository.user.UserTableColumns.PASSWORD;
import static by.logonuk.repository.user.UserTableColumns.SURNAME;
import static by.logonuk.repository.user.UserTableColumns.LOGIN;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int i) throws SQLException {

        User user = new User();

        user.setId(rs.getLong(ID));
        user.setUserName(rs.getString(NAME));
        user.setSurname(rs.getString(SURNAME));
        user.setIsDeleted(rs.getBoolean(IS_DELETED));
        user.setCreationDate(rs.getTimestamp(CREATED));
        user.setModificationDate(rs.getTimestamp(CHANGED));
        user.setLogin(rs.getString(LOGIN));
        user.setPassword(rs.getString(PASSWORD));
        user.setLicenceId(rs.getLong(LICENCE_ID));

        return user;
    }
}
