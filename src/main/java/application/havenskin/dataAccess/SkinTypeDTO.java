package application.havenskin.dataAccess;

import application.havenskin.models.Products;
import application.havenskin.models.ResultTests;
import application.havenskin.models.SkinCaresPlan;
import application.havenskin.models.SkinTypeImages;
import lombok.Data;

import java.util.List;
@Data
public class SkinTypeDTO {
    private String skinName;

    private String description;

    private double minMark;

    private double maxMark;

    private List<SkinTypeImages> skinTypeImages;

    private List<ResultTests> resultTests;

    private SkinCaresPlan planSkinCare;

    private List<Products> products;
}
