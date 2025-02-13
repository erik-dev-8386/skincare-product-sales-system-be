package application.havenskin.repositories;

import application.havenskin.models.Shipments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentsRepository extends JpaRepository<Shipments, String> {
}
