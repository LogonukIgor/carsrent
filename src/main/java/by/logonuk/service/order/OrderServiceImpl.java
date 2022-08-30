package by.logonuk.service.order;

import by.logonuk.domain.Order;
import by.logonuk.repository.order.OrderRepositoryInteface;
import by.logonuk.repository.user.UserRepositoryInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepositoryInteface orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Order findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Optional<Order> findOne(Long id) {
        return orderRepository.findOne(id);
    }

    @Override
    public List<Order> findAll(int limit, int offset) {
        return orderRepository.findAll(limit, offset);
    }

    @Override
    public Order create(Order object) {
        return orderRepository.create(object);
    }

    @Override
    public Order update(Order object) {
        return orderRepository.update(object);
    }

    @Override
    public Long delete(Long id) {
        return orderRepository.delete(id);
    }

    @Override
    public Map<String, Integer> numOfOpenOrder() {
        return orderRepository.numOfOpenOrder();
    }
}
