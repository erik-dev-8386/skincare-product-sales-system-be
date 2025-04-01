package application.havenskin.dataAccess;

import lombok.Data;
import org.hibernate.annotations.Nationalized;

@Data
public class UserAnswerDto {
    private String userAnswerId;
    private String questionId;
    @Nationalized
    private String questionContent; // Hiển thị nội dung câu hỏi
    private String answerId;
    @Nationalized
    private String answerContent;   // Hiển thị nội dung câu trả lời
    private double mark;
}
