package application.havenskin.repositories;

import application.havenskin.models.Shipments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipments, String>
{
}
