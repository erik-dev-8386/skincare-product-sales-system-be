package application.havenskin.DTORequest;

import application.havenskin.BusinessObject.Models.Products;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
public class BrandDTO {
    private String brandName;
    private String description;
    private String country;
    private List<Products> products;
}
