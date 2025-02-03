package application.havenskin.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Discounts")
@Data
public class Discounts {
    @Id
    @Column(name = "discount_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String discountId;

    @Column(name = "discount_name", length = 50)
    private String discountName;

    @Column(name = "discount_code", length = 50)
    private String discountCode;

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

    @Column(name = "discount_percent")
    private double discountPercent;

    @Column(name = "status")
    private byte status;

    @OneToMany(mappedBy = "discounts")
    private List<Products> products;
}
