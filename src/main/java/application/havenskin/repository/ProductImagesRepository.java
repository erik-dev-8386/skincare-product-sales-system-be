package application.havenskin.repository;

import application.havenskin.BusinessObject.Models.ProductImages;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductImagesRepository extends JpaRepository<ProductImages, String> {
}
