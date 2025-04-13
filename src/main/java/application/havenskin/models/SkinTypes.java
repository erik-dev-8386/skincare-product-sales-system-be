package application.havenskin.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Nationalized;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Entity
@Table(name = "SkinTypes")
@Data
public class SkinTypes {
    @Id
    @Column(name = "skin_type_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String skinTypeId;

    @NotBlank(message = "Tên loại da không được để trống")
    @Pattern(regexp = "^(?![0-9\\s])[\\p{L}0-9 ].*$", message = "Tên loại da không được bắt đầu bằng số hoặc chứa ký tự đặc biệt")
    @NotNull
    @Column(name = "skin_name", length = 50)
    @Nationalized
    private String skinName;

    @Column(name = "description", length = 10000)
    @Nationalized
    private String description;

    @Min(value = 0, message = "Giá trị minMark không được nhỏ hơn 0")
    @Column(name = "min_mark")
    private double minMark;

    @Min(value = 0, message = "Giá trị maxMark không được nhỏ hơn 0")
    @Column(name = "max_mark")
    private double maxMark;

    //@JsonIgnore
    @OneToMany(mappedBy = "skinType")
    private List<SkinTypeImages> skinTypeImages;

//    private List<MultipartFile> images;

    @JsonIgnore
    @OneToMany(mappedBy = "skinType")
    private List<ResultTests> resultTests;


    @ToString.Exclude
//    @JsonIgnore
    @OneToMany(mappedBy = "skinType")
    @JsonIgnoreProperties("skinType")
    private List<SkinCaresPlan> planSkinCare;

    @JsonIgnore
    @OneToMany(mappedBy = "skinTypes")
    private List<Products> products;

    @NotNull
    @Column(name = "status", length = 20)
    private Byte status;
}
