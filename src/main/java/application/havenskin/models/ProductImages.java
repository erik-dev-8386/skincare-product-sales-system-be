package application.havenskin.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "ProductImages")
@Data
public class ProductImages {
    @Id
    @Column(name = "image_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String imageId;

    @Column(name = "image_url", length = 1000)
    private String imageURL;

//    @Column(name = "description", length = 500)
  //  private String description;

    @NotNull
    @Column(name = "product_id", length = 50)
    private String productId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    private Products products;
}
