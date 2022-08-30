package by.logonuk.repository.user;

import by.logonuk.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Primary
public class UserRepository implements UserRepositoryInterface {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final UserRowMapper userRowMapper;

    @Override
    public User findById(Long id) {
        return jdbcTemplate.queryForObject("select * from cars_rent.users where id = " + id, userRowMapper);
    }

    @Override
    public Optional<User> findOne(Long id) {
        return Optional.of(findById(id));
    }

    @Override
    public List<User> findAll() {
        return findAll(DEFAULT_FIND_ALL_LIMIT, DEFAULT_FIND_ALL_OFFSET);
    }

    @Override
    public List<User> findAll(int limit, int offset) {
        return jdbcTemplate.query("select * from cars_rent.users limit " + limit + " offset " + offset, userRowMapper);
    }

    @Override
    public User create(User object) {
        final String insertQuery =
                "insert into cars_rent.users (user_name, surname, is_deleted, creation_date, modification_date, login, password, licence_id) " +
                        " values (:userName, :surname, :isDeleted, :creationDate, :modificationDate, :login, :password, :licenceId);";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userName", object.getUserName());
        mapSqlParameterSource.addValue("surname", object.getSurname());
        mapSqlParameterSource.addValue("isDeleted", object.getIsDeleted());
        mapSqlParameterSource.addValue("creationDate", object.getCreationDate());
        mapSqlParameterSource.addValue("modificationDate", object.getModificationDate());
        mapSqlParameterSource.addValue("login", object.getLogin());
        mapSqlParameterSource.addValue("password", object.getPassword());
        mapSqlParameterSource.addValue("licenceId", object.getLicenceId());

        namedParameterJdbcTemplate.update(insertQuery, mapSqlParameterSource);

        Long lastInsertId = namedParameterJdbcTemplate.query("SELECT currval('cars_rent.users_id_seq') as last_id",
                resultSet -> {
                    resultSet.next();
                    return resultSet.getLong("last_id");
                });

        return findById(lastInsertId);
    }

    @Override
    public User update(User object) {
        final String insertQuery =
                "update cars_rent.users " +
                        "set " +
                        "user_name = :userName, surname = :surname, is_deleted = :isDeleted, creation_date = :creationDate, modification_date = :modificationDate, login = :login, password = :password, licence_id = :licenceId" +
                        " where id = :id";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userName", object.getUserName());
        mapSqlParameterSource.addValue("surname", object.getSurname());
        mapSqlParameterSource.addValue("isDeleted", object.getIsDeleted());
        mapSqlParameterSource.addValue("creationDate", object.getCreationDate());
        mapSqlParameterSource.addValue("modificationDate", object.getModificationDate());
        mapSqlParameterSource.addValue("login", object.getLogin());
        mapSqlParameterSource.addValue("password", object.getPassword());
        mapSqlParameterSource.addValue("licenceId", object.getLicenceId());
        mapSqlParameterSource.addValue("id", object.getId());

        namedParameterJdbcTemplate.update(insertQuery, mapSqlParameterSource);

        return findById(object.getId());
    }

    @Override
    public Long delete(Long id) {
        jdbcTemplate.update("delete from carshop.users where id = " + id);
        return id;
    }

    @Override
    public Map<String, Integer> numOfUsers() {
        return jdbcTemplate.query("select cars_rent.get_users_stats_number_of_users(false)", resultSet -> {
            resultSet.next();
            return Collections.singletonMap("Количество пользователей", resultSet.getInt(1));
        });
    }
}
