package application.havenskin.dataAccess;

import application.havenskin.enums.SkinTypeEnums;
import application.havenskin.models.Products;
import application.havenskin.models.ResultTests;
import application.havenskin.models.SkinCaresPlan;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
@Data
public class SkinTypeDTO {
    private String skinName;

    private String description;

    private double minMark;

    private double maxMark;

//    private List<SkinTypeImages> skinTypeImages;

    //private List<MultipartFile> images;

    private List<ResultTests> resultTests;

    private SkinCaresPlan planSkinCare;

    private List<Products> products;

    @NotNull
    @Column(name = "status", length = 20)
    private Byte status = SkinTypeEnums.ACTIVE.getSkin_type_status();
}
