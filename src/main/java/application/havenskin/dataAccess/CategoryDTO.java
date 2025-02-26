package application.havenskin.dataAccess;

import application.havenskin.enums.CategoryEnums;
import application.havenskin.models.Products;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.List;
@Data
public class CategoryDTO {
    @Nationalized
    private String categoryName;

    @Nationalized
    private String description;

    @Nationalized
    private String usageInstruction;

    private List<Products> products;

    private Byte status = CategoryEnums.ACTIVE.getStatus();
}
