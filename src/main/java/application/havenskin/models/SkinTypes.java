package application.havenskin.models;

import application.havenskin.enums.SkinTypeEnums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.List;

@Entity
@Table(name = "SkinTypes")
@Data
public class SkinTypes {
    @Id
    @Column(name = "skin_type_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String skinTypeId;

    @NotNull
    @Column(name = "skin_name", length = 50)
    @Nationalized
    private String skinName;

    @Column(name = "description", length = 500)
    @Nationalized
    private String description;

    @Column(name = "min_mark")
    private double minMark;

    @Column(name = "max_mark")
    private double maxMark;

    @JsonIgnore
    @OneToMany(mappedBy = "skinType")
    private List<SkinTypeImages> skinTypeImages;

    @JsonIgnore
    @OneToMany(mappedBy = "skinType")
    private List<ResultTests> resultTests;

    @JsonIgnore
    @OneToOne(mappedBy = "skinType")
    private SkinCaresPlan planSkinCare;

    @JsonIgnore
    @OneToMany(mappedBy = "skinTypes")
    private List<Products> products;

    @NotNull
    @Column(name = "status", length = 20)
    private Byte status;
}
