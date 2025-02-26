package application.havenskin.dataAccess;

import application.havenskin.enums.SkinTypeEnums;
import application.havenskin.models.SkinTypeImages;
import lombok.Data;
import org.hibernate.annotations.Nationalized;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Data
public class SkinTypeDTO {
    @Nationalized
    private String skinName;

    private String description;

    private double minMark;

    private double maxMark;

   private List<SkinTypeImages> skinTypeImages;

   // private List<MultipartFile> images;

//    private List<ResultTests> resultTests;
//
//    private SkinCaresPlan planSkinCare;
//
//    private List<Products> products;

    private Byte status = SkinTypeEnums.ACTIVE.getSkin_type_status();
}
