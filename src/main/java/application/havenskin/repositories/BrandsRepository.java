package application.havenskin.repositories;

import application.havenskin.models.Brands;
import application.havenskin.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandsRepository extends JpaRepository<Brands, String> {
    Brands findByBrandName(String name);
    List<Brands> findByCountry(String country);
    List<Brands> findByStatus(byte status);
    @Query("select b.brandName from Brands b where b.status = 1")
    List<String> findAllByBrandName();
    @Query("SELECT b FROM Brands b WHERE b.status = 1 ORDER BY b.brandName ASC")
    List<Brands> findActiveBrandsSortedByName();

    @Query("SELECT b FROM Brands b WHERE b.brandName LIKE %:brandName%")
    List<Brands> findByBrandsNameContaining(@Param("brandName") String brandName);

    boolean existsByBrandName(String brandName);
}
