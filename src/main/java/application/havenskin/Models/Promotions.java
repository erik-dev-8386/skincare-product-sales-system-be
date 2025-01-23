package application.havenskin.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Promotions")
@Data
public class Promotions {
    @Id
    @Column(name = "promotion_id", length = 50)
    private String promotionId;

    @Column(name = "promotion_name", length = 50)
    private String promotionName;

    @Column(name = "description", length = 250)
    private String description;

    @NotNull
    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "closed_time")
    private LocalDateTime deletedTime;

    @NotNull
    @Column(name = "actual_start_time")
    private LocalDateTime actualStartTime;

    @Column(name = "actual_end_time")
    private LocalDateTime actualEndTime;

    @Column(name = "percent")
    private double percent;

    @Column(name = "status")
    private byte status;

    @OneToMany(mappedBy = "promotions")
    private List<Products> products;
}
