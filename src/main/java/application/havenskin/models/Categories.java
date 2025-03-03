package application.havenskin.models;

import application.havenskin.enums.CategoryEnums;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.util.List;

@Entity
@Table(name = "Categories")
@Data
public class Categories {
    @Id
    @Column(name = "category_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String categoryId;

    @Column(name = "category_name", length = 50)
    @Nationalized
    private String categoryName;

    @Column(name = "description", length = 250)
    @Nationalized
    private String description;

//    @Column(name = "usage_instruction", length = 255)
//    @Nationalized
//    private String usageInstruction;

    @JsonIgnore
    @OneToMany(mappedBy = "categories")
    private List<Products> products;

    @NotNull
    @Column(name = "status", length = 20)
    private Byte status;
}
