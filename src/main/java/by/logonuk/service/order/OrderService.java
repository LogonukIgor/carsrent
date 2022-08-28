package by.logonuk.service.order;

import by.logonuk.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    List<Order> findAll();

    Order findById(Long id);

    Optional<Order> findOne(Long id);

    List<Order> findAll(int limit, int offset);

    Order create(Order object);

    Order update(Order object);

    Long delete(Long id);
}
