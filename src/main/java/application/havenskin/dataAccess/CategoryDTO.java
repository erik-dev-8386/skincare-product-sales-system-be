package application.havenskin.dataAccess;

import application.havenskin.models.Products;
import lombok.Data;

import java.util.List;
@Data
public class CategoryDTO {

    private String categoryName;

    private String description;

    private String usageInstruction;

    private List<Products> products;

    private Byte status;
}
