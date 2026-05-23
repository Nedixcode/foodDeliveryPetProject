package backend.orderservice.repository;

import backend.orderservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

}
