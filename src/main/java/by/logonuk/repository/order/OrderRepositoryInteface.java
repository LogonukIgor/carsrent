package by.logonuk.repository.order;

import by.logonuk.domain.Order;
import by.logonuk.repository.CRUDRepository;

import java.util.Map;

public interface OrderRepositoryInteface extends CRUDRepository<Long, Order> {

    Map<String, Integer> numOfOpenOrder();
}
