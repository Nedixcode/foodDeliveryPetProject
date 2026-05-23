package backend.orderservice.repository;

import backend.orderservice.entity.OutboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface OutboxMessageRepository extends JpaRepository<OutboxMessage, UUID> {

}
