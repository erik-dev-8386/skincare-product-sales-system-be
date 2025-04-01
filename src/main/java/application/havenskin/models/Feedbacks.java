package application.havenskin.models;

import com.google.firebase.database.DatabaseError;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.Nationalized;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "Feedbacks")
@Data
public class Feedbacks {
    @Id
    @Column(name = "feedback_id", length = 50)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String feedbackId;

    @Column(name = "feedback_content", length = 255)
    @Nationalized
    private String feedbackContent;

    @Column(name = "feedback_date", length = 50)
    private Date feedbackDate;

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

    @Column(name = "status", length = 20)
    private byte status;

    @Column(name = "rating")
    private byte rating;
}
