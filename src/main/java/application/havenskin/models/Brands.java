package application.havenskin.models;

import application.havenskin.enums.BrandEnums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "Brands")
@Data
public class Brands {
    @Id
    @Column(name = "brand_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String brandId;

    @Column(name = "brand_name", length = 50)
    private String brandName;

    @Column(name = "description", length = 250)
    private String description;

    @Column(name = "country", length = 50)
    private String country;

    @JsonIgnore
    @OneToMany(mappedBy = "brands")
    private List<Products> products;

    @NotNull
    @Column(name = "status", length = 20)
    private Byte status = BrandEnums.ACTIVE.getValue();
}
