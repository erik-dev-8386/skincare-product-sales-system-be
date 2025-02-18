package application.havenskin.dataAccess;

import application.havenskin.enums.SkinTypeEnums;
import application.havenskin.models.Products;
import application.havenskin.models.ResultTests;
import application.havenskin.models.SkinCaresPlan;
import application.havenskin.models.SkinTypeImages;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
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
    private Byte status = SkinTypeEnums.ACTIVE.getSkin_type_status();
}
