package application.havenskin.repositories;

import application.havenskin.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface ProductsRepository extends JpaRepository<Products, String> {
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
    List<Products> findByQuantity(Integer quantiy);
}
