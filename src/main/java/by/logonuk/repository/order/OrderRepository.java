package by.logonuk.repository.order;

import by.logonuk.domain.Order;
import by.logonuk.repository.user.UserRowMapper;
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
public class OrderRepository implements OrderRepositoryInteface{

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final OrderRowMapper orderRowMapper;
    @Override
    public Order findById(Long id) {
        return jdbcTemplate.queryForObject("select * from cars_rent.orders where id = " + id, orderRowMapper);
    }

    @Override
    public Optional<Order> findOne(Long id) {
        return Optional.of(findById(id));
    }

    @Override
    public List<Order> findAll() {
        return findAll(DEFAULT_FIND_ALL_LIMIT, DEFAULT_FIND_ALL_OFFSET);
    }

    @Override
    public List<Order> findAll(int limit, int offset) {
        return jdbcTemplate.query("select * from cars_rent.orders limit " + limit + " offset " + offset, orderRowMapper);
    }

    @Override
    public Order create(Order object) {
        final String insertQuery =
                "insert into cars_rent.orders (user_id, car_id, modification_date, receiving_date, return_date, is_completed, price, creation_date) " +
                        " values (:userId, :carId, :modificationDate, :receivingDate, :returnDate, :isCompleted, :price, :creationDate);";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", object.getUserId());
        mapSqlParameterSource.addValue("carId", object.getCarId());
        mapSqlParameterSource.addValue("modificationDate", object.getModificationDate());
        mapSqlParameterSource.addValue("receivingDate", object.getReceivingDate());
        mapSqlParameterSource.addValue("returnDate", object.getReturnDate());
        mapSqlParameterSource.addValue("isCompleted", object.getIsCompleted());
        mapSqlParameterSource.addValue("price", object.getPrice());
        mapSqlParameterSource.addValue("creationDate", object.getCreationDate());

        namedParameterJdbcTemplate.update(insertQuery, mapSqlParameterSource);

        Long lastInsertId = namedParameterJdbcTemplate.query("SELECT currval('cars_rent.users_id_seq') as last_id",
                resultSet -> {
                    resultSet.next();
                    return resultSet.getLong("last_id");
                });

        return findById(lastInsertId);
    }

    @Override
    public Order update(Order object) {
        final String insertQuery =
                "update cars_rent.users " +
                        "set " +
                        "user_id = :userId, car_id = :carId, modification_date = :modificationDate, receiving_date = :receivingDate, return_date = :returnDate, is_completed = :isCompleted, price = :price, creation_date = :creationDate" +
                        " where id = :id";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("userId", object.getUserId());
        mapSqlParameterSource.addValue("carId", object.getCarId());
        mapSqlParameterSource.addValue("modificationDate", object.getModificationDate());
        mapSqlParameterSource.addValue("receivingDate", object.getReceivingDate());
        mapSqlParameterSource.addValue("returnDate", object.getReturnDate());
        mapSqlParameterSource.addValue("isCompleted", object.getIsCompleted());
        mapSqlParameterSource.addValue("price", object.getPrice());
        mapSqlParameterSource.addValue("creationDate", object.getCreationDate());
        mapSqlParameterSource.addValue("id", object.getId());

        namedParameterJdbcTemplate.update(insertQuery, mapSqlParameterSource);

        return findById(object.getId());
    }

    @Override
    public Long delete(Long id) {
        jdbcTemplate.update("delete from carshop.orders where id = " + id);
        return id;
    }

    @Override
    public Map<String, Integer> numOfOpenOrder() {
        return jdbcTemplate.query("select cars_rent.get_orders_stats_number_of_open_order()", resultSet->{
           resultSet.next();
           return Collections.singletonMap("Количество открытых заказов", resultSet.getInt(1));
        });
    }
}
