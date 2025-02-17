package application.havenskin.repositories;

import application.havenskin.models.Brands;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandsRepository extends JpaRepository<Brands, String> {
    Brands findByBrandName(String name);
    List<Brands> findByCountry(String country);
    List<Brands> findByStatus(byte status);
    @Query("select brandName  from Brands")
    List<String> findAllByBrandName();
}
