package application.havenskin.repository;

import application.havenskin.BusinessObject.Models.Brands;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandsRepository extends JpaRepository<Brands, String> {
    Brands findBybrandName(String brandName);
}
