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
//    @Query("select b from Brands b where b.status = 1")
//    List<Brands> findAllByBrandName();
    List<Brands> findByStatusOrderByBrandName(byte status);


    List<Brands> findByBrandNameContaining(@Param("brandName") String brandName);

    boolean existsByBrandName(String brandName);
}
