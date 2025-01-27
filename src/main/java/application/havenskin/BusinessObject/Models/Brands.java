package application.havenskin.BusinessObject.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    @OneToMany(mappedBy = "brands")
    @JsonIgnore
    private List<Products> products;
}
