package application.havenskin.dataAccess;

import lombok.Data;

@Data
public class UserAnswerDto {
    private String userAnswerId;
    private String questionId;
    private String questionContent; // Hiển thị nội dung câu hỏi
    private String answerId;
    private String answerContent;   // Hiển thị nội dung câu trả lời
    private double mark;
}
