package by.logonuk.repository.order;

import by.logonuk.domain.Order;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import static by.logonuk.repository.order.OrderTableColumns.ID;
import static by.logonuk.repository.order.OrderTableColumns.USER_ID;
import static by.logonuk.repository.order.OrderTableColumns.CAR_ID;
import static by.logonuk.repository.order.OrderTableColumns.MODIFICATION_DATE;
import static by.logonuk.repository.order.OrderTableColumns.RECEIVING_DATE;
import static by.logonuk.repository.order.OrderTableColumns.RETURN_DATE;
import static by.logonuk.repository.order.OrderTableColumns.IS_COMPLETED;
import static by.logonuk.repository.order.OrderTableColumns.PRICE;
import static by.logonuk.repository.order.OrderTableColumns.CREATION_DATE;

public class OrderRowMapper implements RowMapper<Order> {
    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {

        Order order = new Order();

        order.setId(rs.getLong(ID));
        order.setUserId(rs.getInt(USER_ID));
        order.setCarId(rs.getInt(CAR_ID));
        order.setModificationDate(rs.getTimestamp(MODIFICATION_DATE));
        order.setReceivingDate(rs.getTimestamp(RECEIVING_DATE));
        order.setReturnDate(rs.getTimestamp(RETURN_DATE));
        order.setIsCompleted(rs.getBoolean(IS_COMPLETED));
        order.setPrice(rs.getDouble(PRICE));
        order.setCreationDate(rs.getTimestamp(CREATION_DATE));

        return order;
    }
}
