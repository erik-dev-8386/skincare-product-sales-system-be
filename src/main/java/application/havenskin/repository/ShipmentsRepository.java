package application.havenskin.repository;

import application.havenskin.BusinessObject.Models.Shipments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShipmentsRepository extends JpaRepository<Shipments, String> {
}
