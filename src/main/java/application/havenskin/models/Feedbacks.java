package application.havenskin.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "Feedbacks")
@Data
public class Feedbacks {
    @Id
    @Column(name = "feedback_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String feedbackId;

    @Column(name = "feedback_content", length = 255)
    private String feedbackContent;

    @Column(name = "feedback_date", length = 50)
    private LocalDateTime feedbackDate;

    @NotNull
    @Column(name = "product_id", length = 50)
    private String productId;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    private Products products;

    @NotNull
    @Column(name = "user_id", length = 50)
    private String userId;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", insertable = false, updatable = false)
    private Users users;
}
