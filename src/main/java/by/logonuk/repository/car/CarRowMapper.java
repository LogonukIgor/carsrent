package by.logonuk.repository.car;

import by.logonuk.domain.Car;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.logonuk.repository.car.CarTableColumns.CAR_ID;
import static by.logonuk.repository.car.CarTableColumns.BRAND;
import static by.logonuk.repository.car.CarTableColumns.MODEL;
import static by.logonuk.repository.car.CarTableColumns.IS_IN_STOCK;
import static by.logonuk.repository.car.CarTableColumns.IS_DELETED;
import static by.logonuk.repository.car.CarTableColumns.ENGINE_VOLUME;
import static by.logonuk.repository.car.CarTableColumns.YEAR_OF_ISSUE;
import static by.logonuk.repository.car.CarTableColumns.NUMBER_OF_SEATS;
import static by.logonuk.repository.car.CarTableColumns.AIR_CONDITIONER;
import static by.logonuk.repository.car.CarTableColumns.COST_PER_DAY;
import static by.logonuk.repository.car.CarTableColumns.TRANSMISSION;
import static by.logonuk.repository.car.CarTableColumns.CREATION_DATE;
import static by.logonuk.repository.car.CarTableColumns.MODIFICATION_DATE;

@Component
public class CarRowMapper implements RowMapper<Car> {
    @Override
    public Car mapRow(ResultSet rs, int rowNum) throws SQLException {
        Car car  = new Car();

        car.setCarId(rs.getLong(CAR_ID));
        car.setBrand(rs.getString(BRAND));
        car.setModel(rs.getString(MODEL));
        car.setIsInStock(rs.getBoolean(IS_IN_STOCK));
        car.setIsDeleted(rs.getBoolean(IS_DELETED));
        car.setEngineVolume(rs.getDouble(ENGINE_VOLUME));
        car.setYearOfIssue(rs.getInt(YEAR_OF_ISSUE));
        car.setNumberOfSeats(rs.getInt(NUMBER_OF_SEATS));
        car.setAirConditioner(rs.getBoolean(AIR_CONDITIONER));
        car.setCostPerDay(rs.getDouble(COST_PER_DAY));
        car.setTransmission(rs.getString(TRANSMISSION));
        car.setCreationDate(rs.getTimestamp(CREATION_DATE));
        car.setModificationDate(rs.getTimestamp(MODIFICATION_DATE));

        return car;
    }
}
