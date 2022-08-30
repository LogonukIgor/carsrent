package by.logonuk.repository.car;

import by.logonuk.domain.Car;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Primary
public class CarRepository implements CarRepositoryInterface {
    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final CarRowMapper carRowMapper;

    @Override
    public Car findById(Long id) {
        return jdbcTemplate.queryForObject("select * from cars_rent.cars where id = " + id, carRowMapper);
    }

    @Override
    public Optional<Car> findOne(Long id) {
        return Optional.of(findById(id));
    }

    @Override
    public List<Car> findAll() {
        return findAll(DEFAULT_FIND_ALL_LIMIT, DEFAULT_FIND_ALL_OFFSET);
    }

    @Override
    public List<Car> findAll(int limit, int offset) {
        return jdbcTemplate.query("select * from cars_rent.cars limit " + limit + " offset " + offset, carRowMapper);
    }

    @Override
    public Car create(Car object) {
        final String insertQuery =
                "insert into cars_rent.cars (brand, model, is_in_stock, is_deleted, engine_volume, year_of_issue, number_of_seats, air_conditioner, cost_per_day, transmission, creation_date, modification_date) " +
                        " values (:brand, :model, :isInStock, :isDeleted, :engineVolume, :yearOfIssue, :numberOfSeats, :airConditioner,:costPerDay,:transmission,:creationDate,:modificationDate);";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("brand", object.getBrand());
        mapSqlParameterSource.addValue("model", object.getModel());
        mapSqlParameterSource.addValue("isInStock", object.getIsInStock());
        mapSqlParameterSource.addValue("isDeleted", object.getIsDeleted());
        mapSqlParameterSource.addValue("engineVolume;", object.getEngineVolume());
        mapSqlParameterSource.addValue("yearOfIssue", object.getYearOfIssue());
        mapSqlParameterSource.addValue("numberOfSeats", object.getNumberOfSeats());
        mapSqlParameterSource.addValue("airConditioner", object.getAirConditioner());
        mapSqlParameterSource.addValue("costPerDay", object.getCostPerDay());
        mapSqlParameterSource.addValue("transmission", object.getTransmission());
        mapSqlParameterSource.addValue("creationDate", object.getCreationDate());
        mapSqlParameterSource.addValue("modificationDate", object.getModificationDate());

        namedParameterJdbcTemplate.update(insertQuery, mapSqlParameterSource);

        Long lastInsertId = namedParameterJdbcTemplate.query("SELECT currval('cars_rent.cars_car_id_seq') as last_id",
                resultSet -> {
                    resultSet.next();
                    return resultSet.getLong("last_id");
                });

        return findById(lastInsertId);
    }

    @Override
    public Car update(Car object) {
        final String insertQuery =
                "update cars_rent.cars " +
                        "set " +
                        "brand = :brand, model = :model, is_in_stock = :is_in_stock, is_deleted = :is_deleted, engine_volume = :engine_volume, year_of_issue = :year_of_issue, number_of_seats = :number_of_seats, air_conditioner = :air_conditioner, cost_per_day = :cost_per_day, transmission = :transmission, creation_date = :creation_date, modification_date = :modification_date" +
                        " where car_id = :car_id";

        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource();
        mapSqlParameterSource.addValue("brand", object.getBrand());
        mapSqlParameterSource.addValue("model", object.getModel());
        mapSqlParameterSource.addValue("isInStock", object.getIsInStock());
        mapSqlParameterSource.addValue("isDeleted", object.getIsDeleted());
        mapSqlParameterSource.addValue("engineVolume;", object.getEngineVolume());
        mapSqlParameterSource.addValue("yearOfIssue", object.getYearOfIssue());
        mapSqlParameterSource.addValue("numberOfSeats", object.getNumberOfSeats());
        mapSqlParameterSource.addValue("airConditioner", object.getAirConditioner());
        mapSqlParameterSource.addValue("costPerDay", object.getCostPerDay());
        mapSqlParameterSource.addValue("transmission", object.getTransmission());
        mapSqlParameterSource.addValue("creationDate", object.getCreationDate());
        mapSqlParameterSource.addValue("modificationDate", object.getModificationDate());
        mapSqlParameterSource.addValue("car_id", object.getCarId());

        namedParameterJdbcTemplate.update(insertQuery, mapSqlParameterSource);

        return findById(object.getCarId());
    }

    @Override
    public Long delete(Long id) {
        jdbcTemplate.update("delete from carshop.cars where id = " + id);
        return id;
    }

    @Override
    public Map<String, Integer> carsInStock() {
        return jdbcTemplate.query("select cars_rent.get_cars_stats_number_of_cars_in_stock()", resultSet->{
            resultSet.next();
            return Collections.singletonMap("Количество машин в наличии", resultSet.getInt(1));
        });
    }
}
