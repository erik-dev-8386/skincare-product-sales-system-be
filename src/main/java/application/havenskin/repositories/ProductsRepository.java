package application.havenskin.repositories;

import application.havenskin.models.Brands;
import application.havenskin.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;


public interface ProductsRepository extends JpaRepository<Products, String> {
    List<Products> findByQuantity(Integer quantiy);

    List<Products> findByCategoryId(String id);

    List<Products> findByBrandId(String id);

    Products findByProductName(String productName);

    //lấy danh sách sản pham qua brandName
    List<Products> findByBrands_BrandName(String brandName);

    //lấy sản phẩm theo tên quốc gia
    @Query("SELECT p FROM Products p JOIN p.brands b WHERE b.country = :country")
    List<Products> findByCountry(@Param("country") String country);

    // Lấy danh sách sản phẩm theo tên loại da (SkinTypes)
    @Query("SELECT p FROM Products p JOIN p.skinTypes s WHERE s.skinName = :skinName")
    List<Products> findBySkinName(@Param("skinName") String skinName);

    //lấy danh sách sản phẩm theo discounts
    List<Products> findProductsByDiscounts_DiscountName(String discountName);

    //lấy danh sách sản phẩm theo categoryName
    List<Products> findProductsByCategories_CategoryName(String categoryName);

    List<Products> findByStatus(byte status);

    @Query("SELECT p FROM Products p WHERE p.status = 1 ORDER BY p.productName ASC")
    List<Brands> findActiveBrandsSortedByName();

    // Lấy id theo tên của sản phẩm
    @Query("SELECT p.productId FROM Products p WHERE p.productName = :name")
    String findProductIDByName(@Param("name") String name);


    @Query("SELECT p FROM Products p ORDER BY p.soldQuantity DESC ")
    List<Products> findTop10ByOrderBySoldQuantityDesc();

    @Query("SELECT p FROM Products p WHERE p.productName LIKE %:productName%")
    List<Products> findByProductNameContaining(@Param("productName") String productName);


    @Query("SELECT p FROM Products p WHERE p.discountPrice BETWEEN :startPrice AND :endPrice")
    List<Products> findByDiscountPriceBetween(@Param("startPrice") Double startPrice, @Param("endPrice") Double endPrice);

    Page<Products> findAll(Pageable pageable);

    boolean existsByProductName(String productName);

    @Query("SELECT p FROM Products p where p.status= 1")
    List<Products> findByStatusProduct();
}
