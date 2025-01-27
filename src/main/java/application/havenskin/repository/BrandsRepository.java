package application.havenskin.repository;

import application.havenskin.BusinessObject.Models.Brands;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandsRepository extends JpaRepository<Brands, String> {
    Brands findByBrandName(String name);
    List<Brands> findByCountry(String country);
}
