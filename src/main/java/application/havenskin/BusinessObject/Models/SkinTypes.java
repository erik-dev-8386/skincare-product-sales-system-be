package application.havenskin.BusinessObject.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
    private String skinName;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "min_mark")
    private double minMark;

    @Column(name = "max_mark")
    private double maxMark;

    @OneToMany(mappedBy = "skinType")
    private List<SkinTypeImages> skinTypeImages;

    @OneToMany(mappedBy = "skinType")
    private List<ResultTests> resultTests;

    @OneToOne(mappedBy = "skinType")
    private SkinCaresPlan planSkinCare;

    @OneToMany(mappedBy = "skinTypes")
    @JsonIgnore
    private List<Products> products;
}
