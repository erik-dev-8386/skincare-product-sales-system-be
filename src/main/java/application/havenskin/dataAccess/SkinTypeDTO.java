package application.havenskin.dataAccess;

import application.havenskin.enums.SkinTypeEnums;
import application.havenskin.models.SkinTypeImages;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.annotations.Nationalized;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Data
public class SkinTypeDTO {
    @NotBlank(message = "Tên loại da không được để trống")
    @Pattern(regexp = "^[^\\d\\W][\\p{L} ]*$", message = "Tên loại da không được bắt đầu bằng số hoặc chứa ký tự đặc biệt")
    @Nationalized
    private String skinName;

    private String description;

    @Min(value = 0, message = "Giá trị minMark không được nhỏ hơn 0")
    private double minMark;

    @Min(value = 0, message = "Giá trị maxMark không được nhỏ hơn 0")
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
