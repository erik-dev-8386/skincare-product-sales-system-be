package application.havenskin.dataAccess;

import application.havenskin.models.Products;
import lombok.Data;

import java.util.List;
@Data
public class BrandDTO {
    private String brandName;
    private String description;
    private String country;
    private List<Products> products;
}
