package application.havenskin.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "SkinTypeImages")
@Data
public class SkinTypeImages {

    @Id
    @Column(name = "image_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String imageId;

    @Column(name = "image_url", length = 255)
    private String imageURL;

    @Column(name = "description", length = 500)
    private String description;

    @NotNull
    @Column(name = "skin_type_id", length = 50)
    private String skinTypeId;

    @ManyToOne
    @JoinColumn(name = "skin_type_id", referencedColumnName = "skin_type_id", insertable = false, updatable = false)
    private SkinTypes skinType;

}
