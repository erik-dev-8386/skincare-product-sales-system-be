package application.havenskin.DTORequest;

import application.havenskin.BusinessObject.Models.Products;
import application.havenskin.BusinessObject.Models.ResultTests;
import application.havenskin.BusinessObject.Models.SkinCaresPlan;
import application.havenskin.BusinessObject.Models.SkinTypeImages;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import java.util.List;

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
