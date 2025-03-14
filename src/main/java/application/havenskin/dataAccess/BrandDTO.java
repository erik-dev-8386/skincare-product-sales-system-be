package application.havenskin.dataAccess;

import application.havenskin.enums.BrandEnums;
import application.havenskin.models.Products;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.List;
@Data
public class BrandDTO {
    @Nationalized
    private String brandName;
    @Nationalized
    private String description;
    @Nationalized
    private String country;
    private List<Products> products;
    private Byte status = BrandEnums.ACTIVE.getValue();
}
